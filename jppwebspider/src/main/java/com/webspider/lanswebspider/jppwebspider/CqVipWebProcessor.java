package com.webspider.lanswebspider.jppwebspider;

import java.io.IOException;
import java.io.Reader;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ArrayList;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.alibaba.fastjson.JSONObject;
import com.webspider.lanswebspider.jppwebspider.dao.CqVipProductDao;
import com.webspider.lanswebspider.jppwebspider.pojo.CqVipProduct;
import com.webspider.lanswebspider.jppwebspider.util.CqVipWebUtil;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.downloader.selenium.SeleniumDownloader;
import us.codecraft.webmagic.processor.PageProcessor;

public class CqVipWebProcessor implements PageProcessor{
	static Logger log = Logger.getLogger(CqVipWebProcessor.class.getName());
	private static SqlSessionFactory sqlSessionFactory;
	private static Reader reader;
	private Site site=Site.me().addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/28.0.1500.71 Safari/537.36").setRetryTimes(4).setSleepTime(5000).setTimeOut(60000);
	
	public void process(Page page) {
		// TODO Auto-generated method stub
		System.out.println("This is precess function!");
		boolean goodPage=false;
		CqVipWebUtil util=new CqVipWebUtil();
		String docUrl=page.getUrl().toString();
		if(util.isHomePage(docUrl)){
			try {
				goodPage=pubSearchHome(page);
			} catch (SolrServerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	private boolean pubSearchHome(Page page) throws SolrServerException, IOException{
		System.out.println("This is pubSearchHome function!");
		boolean isGoodPage=false;
		ArrayList<String> viewUrlList=new ArrayList<String>();
		ArrayList<String> titleUrlList=new ArrayList<String>();
		ArrayList<String> infoUrlList=new ArrayList<String>();
		CqVipWebUtil util=new CqVipWebUtil();
		String docUrl=page.getUrl().toString();
		System.out.println("docUrl = "+docUrl);
		String word="";
		String[] searchWord=docUrl.split("\\?k=");
		if(searchWord.length>1){
			word=searchWord[1];
		}
		System.out.println("word = "+word);
		String jsonUrl="http://www.cqvip.com/data/main/search.aspx?action=so&k="+word;
		String result=getReturnData(jsonUrl);
		//System.out.println("result = "+result);
		int totalNum=Integer.parseInt(JSONObject.parseObject(result).getString("recordcount"));
		if(totalNum>100){totalNum=100;}
		System.out.println("totalNum = "+totalNum);
		for(int j=1;j<=(int)(Math.ceil(totalNum/20)+1);j++){
			if(j==1){
				jsonUrl="http://www.cqvip.com/data/main/search.aspx?action=so&k="+word;
			}else{
				jsonUrl="http://www.cqvip.com/data/main/search.aspx?action=so&k="+word+"&curpage="+j;
			}
			//System.out.println("jsonUrl = "+jsonUrl);
			result=getReturnData(jsonUrl);
			//
			Document src=Jsoup.parse(JSONObject.parseObject(result).getString("message"));
			
			Elements titleArea=src.select("li table tr th a");
			//System.out.println("titleArea = "+titleArea);
			if(!titleArea.isEmpty()){
				for(int m=0;m<titleArea.size();m++){
					viewUrlList.add("http://www.cqvip.com"+titleArea.attr("href"));
					titleUrlList.add(titleArea.get(m).text());
				}
			}
			Elements infoArea=src.select("li table tr td.info");
			if(!infoArea.isEmpty()){
				for(int n=0;n<infoArea.size();n++){
					infoUrlList.add(infoArea.get(n).text());
				}
			}
			
			
		}
		System.out.println("viewUrlList.size() = "+viewUrlList.size());
		for(String tmp : viewUrlList){
			System.out.println("tmp = "+ tmp);
		}
		
		System.out.println("titleUrlList.size() = "+titleUrlList.size());
		for(String tmp : titleUrlList){
			System.out.println("tmp = "+ tmp);
		}
		
		System.out.println("infoUrlList.size() = "+infoUrlList.size());
		for(String tmp : infoUrlList){
			System.out.println("tmp = "+ tmp);
		}
		
		for(int k=0;k<viewUrlList.size();k++){
			CqVipProduct product=new CqVipProduct();
			Timestamp current = new Timestamp(System.currentTimeMillis());
			product.setArticle_info(infoUrlList.get(k));
			product.setArticle_tags("无");
			product.setArticle_title(titleUrlList.get(k));
			product.setArticle_url(viewUrlList.get(k));
			product.setCreate_time(current);
			product.setUpdate_time(current);
			System.out.println("product = "+product.toString());
			//
			//addCqVipSolr(product);
			
			//这里改成异步入库方式
			
			//addCqVipProduct( product);
			//DBMsg msg = ParserModel.getDBMsgProduct(String.valueOf("1234"),CqVipRabbitMQDao.class, product, ParserModel.getDBAddress(), ExtracterConstants.DB_OPERATION_INSERT);
			//MsgDBPutterBroker.putDBMsg(dbQueueName, msg);
			
			
			
			
		}
		
		return isGoodPage;
	}
	
	 public void addCqVipProduct(CqVipProduct product){
		 System.out.println("This is addCqVipProduct function!");
		 try {
			 reader = Resources.getResourceAsReader("com/webspider/lanswebspider/jppwebspider/map/MyBatisConfig.xml");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
	        SqlSession session = sqlSessionFactory.openSession();
	        try {
	        	CqVipProductDao addProduct = session.getMapper(CqVipProductDao.class);
	        	addProduct.addCqVipProduct(product);
	            session.commit();
	            System.out.println("插入数据成功" + product.getId());
	        } finally {
	            session.close();
	        }
	    }
	 public  void addCqVipSolr(CqVipProduct product) throws SolrServerException, IOException{			
			String baseUrl="http://139.129.117.93:8983/solr";			
			@SuppressWarnings("deprecation")
			HttpSolrServer solrServer =new HttpSolrServer(baseUrl);			
			SolrInputDocument doc= new SolrInputDocument();
			String id="CqVip"+product.getId();
			String title=product.getArticle_title();
			String content=product.getArticle_info();
			String url=product.getArticle_url();
			String segment="";
			String category="cqvip";
			doc.setField("id", id);
			doc.setField("title", title);
			doc.setField("content", content);
			doc.setField("url", url);
			doc.setField("segment", segment);
			doc.setField("category", category);
			solrServer.add(doc);
			solrServer.commit();
			System.out.println("success");
		}
	
	 
	/* 
	private boolean pubSearchView(Page page ){
		boolean goodPage=false;
		System.out.println("This is pubSearchView function!");
		//System.out.println("page =="+page.getHtml());
		String title="";
		String articleInfo="";
		String articleTitleXpath="//div[@class='lay']/div[@class='contains']/span[@class='detailtitle']/h1/text()";
		title=page.getHtml().xpath(articleTitleXpath).toString();
		Document src=Jsoup.parse(page.getHtml().toString());
		Elements infoArea=src.select("div.detailinfo table.datainfo.f14 tr td.sum");
		if(!infoArea.isEmpty()){
			articleInfo=infoArea.text();
		}
		System.out.println("article title = "+title);
		System.out.println("article info="+articleInfo);
		
		if(!title.isEmpty()){
			CqVipProduct product=new CqVipProduct();
			Timestamp current = new Timestamp(System.currentTimeMillis());
			product.setArticle_info(articleInfo);
			product.setArticle_tags("无");
			product.setArticle_title(title);
			product.setArticle_url(page.getUrl().toString());
			product.setCreate_time(current);
			product.setUpdate_time(current);
			System.out.println("product = "+product.toString());
			addCqVipProduct( product);
		}
		return goodPage;
	}
	*/
	
	
	public static String getReturnData(String urlString){  
        String res = "";   
        try {   
            URL url = new URL(urlString);  
            java.net.HttpURLConnection conn = (java.net.HttpURLConnection)url.openConnection();  
            conn.setDoOutput(true);  
            conn.setRequestMethod("GET");   
            java.io.BufferedReader in = new java.io.BufferedReader(new java.io.InputStreamReader(conn.getInputStream(),"utf-8")); 
            //java.io.BufferedReader in = new java.io.BufferedReader(new java.io.InputStreamReader(conn.getInputStream()));  
            String line;  
            while ((line = in.readLine()) != null) {  
                res += line;  
            }  
            in.close();  
        } catch (Exception e) {  
           System.out.println("error in wapaction,and e is " + e.getMessage());  
        }  
        return res;  
    }
	
	public static void cqVipProductSelect() throws IOException{
		reader = Resources.getResourceAsReader("com/nutch/webmagic/nutchwebmagic/map/MyBatisConfig.xml");
		sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
		SqlSession session = sqlSessionFactory.openSession();
		try {
			CqVipProductDao productDao=session.getMapper(CqVipProductDao.class);
			CqVipProduct product=  productDao.getCqVipProduct(1003);
			if( product !=null){
				String userInfo = "article_title："+product.getArticle_title()+", article_info："+product.getArticle_info()+", article_url："+product.getArticle_url()+", article_tags："+product.getArticle_tags();
				System.out.println(userInfo);
			}
		} finally {
			session.close();
		}
	}
	
	public static SqlSessionFactory getSession() {
		return sqlSessionFactory;
	}
	
	
	
	public Site getSite() {
		// TODO Auto-generated method stub
		return site;
	}
	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		System.out.println("Test CqVipWebProcessor!");
		String searchWord="汽车发动机转速控制";
		//维普搜索的首页
		String url="http://www.cqvip.com/main/search.aspx?k="+searchWord;
		//维普搜索的详情页面
		//String driverPath="C://TDDownload//chromedriver_win32(1)//chromedriver.exe";
		//String url="http://www.cqvip.com/QK/91421X/201404/49712637.html";
		System.out.println("this is main function!");
		Spider.create(new CqVipWebProcessor()).addUrl(url).thread(5).run();
		//Spider.create(new CqVipWebProcessor()).thread(5).setDownloader(new SeleniumDownloader(driverPath)).addUrl(url).runAsync();
	}
}
