package com.webspider.lanswebspider.jppwebspider;


import java.sql.Timestamp;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.webspider.lanswebspider.jppwebspider.pojo.StockCodeProduct;
import com.webspider.lanswebspider.jppwebspider.util.StockcodeUtil;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.downloader.selenium.SeleniumDownloader;
import us.codecraft.webmagic.processor.PageProcessor;

public class StockcodeListProcessor implements PageProcessor{
	private Site site=Site.me().setRetryTimes(3).setSleepTime(300);
	static String driverPath="/Users/apple/software/chromedriver/chromedriver";
	static String baseUrl="http://quote.eastmoney.com/stocklist.html";
	//测试用的item详情页面
	static String itemUrl="http://quote.eastmoney.com/sh600086.html";

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("This is StockcodelistProcessor main function!");
		Spider.create(new StockcodeListProcessor()).thread(5).
		//抓取列表页
		//setDownloader(new SeleniumDownloader(driverPath)).addUrl(baseUrl).runAsync();
		//抓取item页面
		setDownloader(new SeleniumDownloader(driverPath)).addUrl(itemUrl).runAsync();
	}

	public void process(Page page) {
		// TODO Auto-generated method stub
		System.out.println("This is StockcodeListProcessor process function!");
		//System.out.println("html sources code = "+page.getHtml());	
		StockcodeUtil util=new StockcodeUtil();
		boolean status;
		if(util.isListPage(page.getUrl().toString())){
			status=hubList(page);
		}else if(util.isItemPage(page.getUrl().toString()))
		{
			status=viewPage(page,util);
		}
	}
	
	public  boolean  hubList(Page page)
	{
		boolean pageBool=false;
		ArrayList<String> viewList= new ArrayList<String>();
		ArrayList<String> titlelist =new ArrayList<String>();
		Document src=Jsoup.parse(page.getHtml().toString());
		Elements listArea=src.select("div#quotesearch ul li");
		System.out.println("listArea.size() = " + listArea.size());
		if(!listArea.isEmpty())
		{
			for( int i=0; i< listArea.size();i++)
			{
				String href=listArea.get(i).select("a").attr("href");
				String title=listArea.get(i).text();
				if(!href.isEmpty())
				{
					viewList.add(href);
				}
				if(!title.isEmpty())
				{
					titlelist.add(title);
				}
			}
		}
		System.out.println("viewList.size() = "+ viewList.size());
		System.out.println("titleList.size() = "+ titlelist.size());
		return pageBool;
	}
	
	public boolean viewPage(Page page,StockcodeUtil util)
	{
		System.out.println("This is StockcodeView page!");
		boolean bool =false;
		//System.out.println("html sources code = "+ page.getHtml());
		Document src=Jsoup.parse(page.getHtml().toString());
		util.setDocument(src);
		if(!util.getStockName().isEmpty())
		{
			StockCodeProduct product=  new StockCodeProduct();
			Timestamp current = new Timestamp(System.currentTimeMillis());
			product.setCreate_time(current);
			product.setUpdate_time(current);
			product.setStock_name(util.getStockName());
			product.setStock_code(util.getStockCode());
			product.setToday_open(util.getTodayOpen());
			product.setYesterday_close(util.getYesterdayClose());
			product.setHighest(util.getHighest());
			product.setLowest(util.getLowest());
			product.setLimit_up(util.getLimitUp());
			product.setLimit_down(util.getLimitDown());
			product.setChange_rate(util.getChangeRate());
			product.setRelative_rate(util.getRelativeRate());
			product.setVolume(util.getVolume());
			product.setTurnover(util.getTurnover());
			product.setPe_ratio(util.getPeRatio());
			product.setPb_ratio(util.getPbRatio());
			product.setTotal_value(util.getTotalValue());
			product.setCurrent_value(util.getCurrentValue());
			product.setCore_data(util.getCoreData());
			
			System.out.println("stock_view_info = "+product.toString());
			
		}
		
		
		return bool;
		
	}
	
	

	public Site getSite() {
		// TODO Auto-generated method stub
		return site;
	}

}
