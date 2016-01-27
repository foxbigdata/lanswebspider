package com.webspider.lanswebspider.jppwebspider;

import java.io.IOException;
import java.io.Reader;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;
import java.util.regex.Matcher;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.apache.thrift.TException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.rabbitmq.client.ConsumerCancelledException;
import com.rabbitmq.client.ShutdownSignalException;
import com.webspider.lanswebspider.jppwebspider.dao.WanFangProductDao;
import com.webspider.lanswebspider.jppwebspider.pojo.WanFangProduct;
import com.webspider.lanswebspider.jppwebspider.rabbitmq.RabbitmqNewtask;
import com.webspider.lanswebspider.jppwebspider.rabbitmq.RabbitmqWorker;
import com.webspider.lanswebspider.jppwebspider.util.WanFangWebUtil;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

public class WanFangWebProcessor implements PageProcessor{
	public static Logger log = Logger.getLogger(WanFangWebProcessor.class.getName());
	private static SqlSessionFactory sqlSessionFactory;
	private static Reader reader;
	public static final String wanFangViewUrl="(http://d.wanfangdata.com.cn/.*)";
	
	private Site site=Site.me().setRetryTimes(4).setSleepTime(3000);
	
	public void process(Page page) {
		
		// TODO Auto-generated method stub
		System.out.println("This is precess function!");
		boolean goodPage=false;
		WanFangWebUtil util=new WanFangWebUtil();
		String docUrl=page.getUrl().toString();
		if(util.isHomePage(docUrl)){
			try {
				goodPage=pubSearchHome(page);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (TimeoutException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ShutdownSignalException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ConsumerCancelledException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (TException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if(util.isHubPage(docUrl)){
			try {
				goodPage=pubSearchHub(page);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (TimeoutException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if(util.isViewPage(docUrl)){
			goodPage=pubSearchView(page);
		}
		if(goodPage){
			System.out.println("页面获取失败!"+page.getUrl());
		}
	}
	
	private boolean pubSearchHome(Page page) throws IOException, TimeoutException, ShutdownSignalException, ConsumerCancelledException, InterruptedException, TException{
		System.out.println("This is pubSearchHome function!");
		boolean isGoodPage=false;
		ArrayList<String> viewUrlList=new ArrayList<String>();
		//获取所有符合规则的详情页面
		String totalRecord="";
		viewUrlList= (ArrayList<String>) page.getHtml().links().regex(wanFangViewUrl).all();
		System.out.println("wanFangViewUrl.size() = "+ viewUrlList.size());
		for(String tmp : page.getHtml().links().regex(wanFangViewUrl).all()){
			
			System.out.println("wanFangViewUrl = "+ tmp);
		}
		System.out.println("doc url :"+ page.getUrl());
		String totalRecordXpath="//div[@class='content content-search clear']/div[@class='left']/div[@class='total-records']/span/text()";
		totalRecord=page.getHtml().xpath(totalRecordXpath).toString();
		
		WanFangWebUtil util=new WanFangWebUtil();
		if(!totalRecord.isEmpty()){
			Matcher m = util.floatDigitPattern.matcher(totalRecord);
			while(m.find()) {
				totalRecord= m.group();
				break;
			}
		}
		if(Integer.parseInt(totalRecord)>100){totalRecord="100";}//added by guosp 限制爬虫的条数
		System.out.println("totalRecord = "+ totalRecord);
		ArrayList<String> hubUrlList=new ArrayList<String>();
		
		int pageNum=0;
		String pageEvery="";
		if(Integer.parseInt(totalRecord)>10){
			pageNum=(int) Math.ceil(Integer.parseInt(totalRecord)/10);
			for(int i=2; i<=(pageNum);i++){
				pageEvery=page.getUrl()+"&p="+i;
				hubUrlList.add(pageEvery);
			}
			
		}
		for (String tmp: hubUrlList ){
			System.out.println("hubUrlList :"+ tmp);
		}
		
		//将抽取的链接丢回待抓取的队列
		if(hubUrlList.size()>0)
		{
			//这里是将新抽取到的url会丢到rabbitmq
			//page.addTargetRequests(hubUrlList);
			RabbitmqNewtask newTask= new RabbitmqNewtask();
			String msg="";
			for(String tmp:hubUrlList)
			{
				msg=msg+"_"+tmp;
			}
			String[] tt=msg.split("_");
			newTask.SendMessage(tt);
		}
		if(viewUrlList.size()>0){
			//这里是将新抽取到的url会丢到rabbitmq
			//page.addTargetRequests(viewUrlList);
			RabbitmqNewtask newTask= new RabbitmqNewtask();
			String msg="";
			for(String tmp:viewUrlList)
			{
				msg=msg+"_"+tmp;
			}
			String[] tt=msg.split("_");
			newTask.SendMessage(tt);
		}
		
		RabbitmqWorker worker=new RabbitmqWorker();
		String url="1111";
		worker.ReceiveMsg(url);
		
		return isGoodPage;
		
	}
	
	private  boolean pubSearchHub(Page page) throws IOException, TimeoutException{
		System.out.println("This is pubSearchHub function!");
		boolean isGoodPage=false;
		ArrayList<String> viewUrlList=new ArrayList<String>();
		//获取所有符合规则的详情页面
		String totalRecord="";
		viewUrlList=(ArrayList<String>) page.getHtml().links().regex(wanFangViewUrl).all();
		System.out.println("wanFangViewUrl.size() = "+ viewUrlList.size());
		
		for(String tmp : viewUrlList){
			
			System.out.println("wanFangViewUrl = "+ tmp);
		}
		System.out.println("doc url :"+ page.getUrl());
		//将等待抓取的详情页面放回待抓取队列
		if(page.getHtml().links().regex(wanFangViewUrl).all().size()>0){
			page.addTargetRequests(viewUrlList);
			RabbitmqNewtask newTask= new RabbitmqNewtask();
			String msg="";
			for(String tmp:viewUrlList)
			{
				msg=msg+"_"+tmp;
			}
			String[] tt=msg.split("_");
			newTask.SendMessage(tt);
		}
		
		return isGoodPage;
	}
	private boolean pubSearchView(Page page){
		System.out.println("This is pubSearchView function!");
		//System.out.println(page.getHtml());
		boolean isGoodPage=false;
		String articleTitle="";
		String articleTags="";
		String articleInfo="";
		String docUrl=page.getUrl().toString();
		String articleTitleXpath="//div[@class='fixed-width baseinfo clear']/div[@class='section-baseinfo']/h1/text()";
		String articleInfoXpath="//div[@class='fixed-width baseinfo clear']/div[@class='section-baseinfo']/div[@class='baseinfo-feild abstract']/div[@class='row clear zh']/div[@class='text']/text()";
		articleTitle=page.getHtml().xpath(articleTitleXpath).toString();
		articleInfo=page.getHtml().xpath(articleInfoXpath).toString();
		Document src=Jsoup.parse(page.getHtml().toString());
		Elements titleArea=src.select("div.fixed-width-wrap.fixed-width-wrap-feild div.fixed-width.baseinfo-feild div.row.row-keyword span.text a");
		if(!titleArea.isEmpty()){
			for(int i=0;i<titleArea.size();i++){
				articleTags=articleTags+"===="+titleArea.get(i).text();
			}
		}
		
		
		if(articleInfo==null){
			Elements infoArea=src.select("div.baseinfo-feild.abstract.abstract-short div.row.clear.fl div.text");
			if(!infoArea.isEmpty()){
				articleInfo=infoArea.text();
			}else{
				articleInfo="页面未找到";
			}
		}
		System.out.println("articleTags= "+articleTags);
		System.out.println("articleTitle = "+articleTitle);
		System.out.println("docUrl = "+docUrl);
		System.out.println("articleInfo = "+articleInfo);
		if(!articleTitle.isEmpty()){
			WanFangProduct product=new WanFangProduct();
			Timestamp current = new Timestamp(System.currentTimeMillis());
			product.setArticle_info(articleInfo);
			product.setArticle_tags(articleTags);
			product.setArticle_title(articleTitle);
			product.setArticle_url(page.getUrl().toString());
			product.setCreate_time(current);
			product.setUpdate_time(current);
			System.out.println("product = "+product.toString());
			addWanFangProduct( product);
			//addWanFangSolr(product);
		}
		
		return isGoodPage;
	}
	
	public void addWanFangProduct(WanFangProduct product){
		 System.out.println("This is addWanFangProduct function!");
		 try {
			 reader = Resources.getResourceAsReader("com/webspider/lanswebspider/jppwebspider/map/MyBatisConfig.xml");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
	        SqlSession session = sqlSessionFactory.openSession();
	        try {
	        	WanFangProductDao addProduct = session.getMapper(WanFangProductDao.class);
	        	addProduct.addWanFangProduct(product);
	            session.commit();
	            System.out.println("新增用户ID：" + product.getId());
	        } finally {
	            session.close();
	        }
	    }
	public  void addWanFangSolr(WanFangProduct product){			
		String baseUrl="http://139.129.117.93:8983/solr";			
		HttpSolrServer solrServer =new HttpSolrServer(baseUrl);			
		SolrInputDocument doc= new SolrInputDocument();
		String id=product.getArticle_url();
		String title=product.getArticle_title();
		String content=product.getArticle_info();
		String url=product.getArticle_url();
		String segment="";
		String category="wanfang";
		doc.setField("id", id);
		doc.setField("title", title);
		doc.setField("content", content);
		doc.setField("url", url);
		doc.setField("segment", segment);
		doc.setField("category", category);
		try {
			solrServer.add(doc);
		} catch (SolrServerException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			solrServer.commit();
		} catch (SolrServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("success");
	}

	public Site getSite() {
		// TODO Auto-generated method stub
		return site;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("知网测试函数主函数");
		String searchWord="汽车发动机转速控制";
		//等待抓取的任务的首页
		String url="http://s.wanfangdata.com.cn/Paper.aspx?q="+searchWord+"&f=top";
		//等待抓取页面的列表页
		//String url="http://s.wanfangdata.com.cn/Paper.aspx?q="+searchWord+"&f=top&p=1";
		//等待下载页面的详情页
		//String url="http://d.wanfangdata.com.cn/Thesis/Y1140137";
		Spider.create(new WanFangWebProcessor()).addUrl(url).thread(5).run();
	}
}
