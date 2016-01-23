package com.webspider.lanswebspider.jppwebspider.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class StockcodeUtil {
	public static final String baseUrl="http://quote.eastmoney.com/stocklist.html";
	public static final String itemUrl="http://quote.eastmoney.com/s(h|z)\\d+.html";
	public static final Pattern itemPattern=Pattern.compile(itemUrl);
	
	private Document src = null;
	
	public void setDocument(Document doc) {
		src = doc;
	} 
	
	
	public boolean isListPage(String url)
	{
		boolean bool=false;
		if(url.equals(baseUrl))
		{
			bool=true;
		}
		return bool;
	}
	
	public boolean isItemPage(String url)
	{
		boolean bool=false;
		Matcher m=itemPattern.matcher(url);
		if(m.matches())
		{
			bool=true;
		}
		return bool;
	}
	
	public String getStockName()
	{
		String stock_name=src.select("h2#name").text();
		return stock_name;
	}
	
	public String getStockCode()
	{
		String stock_code=src.select("b#code").text();
		return stock_code;
	}
	
	public String getTodayOpen()
	{
		String today_open=src.select("table.yfw td#gt1").text();
		return today_open;
	}
	
	public String getYesterdayClose()
	{
		String yesterday_close=src.select("table.yfw td#gt8").text();
		return yesterday_close;
	}
	
	public String getHighest()
	{
		String highest=src.select("table.yfw td#gt2").text();
		return highest;
	}
	
	public String getLowest()
	{
		String lowest=src.select("table.yfw td#gt9").text();
		return lowest;
	}
	
	public String getLimitUp()
	{
		String limit_up=src.select("table.yfw td#gt3").text();
		return limit_up;
	}
	
	public String getLimitDown()
	{
		String limit_down=src.select("table.yfw td#gt10").text();
		return limit_down;
	}
	
	public String getChangeRate()
	{
		String change_rate=src.select("table.yfw td#gt4").text();
		return change_rate;
	}
	
	public String getRelativeRate()
	{
		String relative_reta=src.select("table.yfw td#gt4").text();
		return relative_reta;
	}
	
	public String getVolume()
	{
		String volume=src.select("table.yfw td#gt5").text();
		return volume;
	}
	
	public String getTurnover()
	{
		String turnover=src.select("table.yfw td#gt12").text();
		return turnover;
	}
	
	public String getPeRatio()
	{
		String pe_ratio=src.select("table.yfw td#gt6").text();
		return pe_ratio;
	}
	public String getPbRatio()
	{
		String pb_ratio=src.select("table.yfw td#gt13").text();
		return pb_ratio;
	}
	
	public String getTotalValue()
	{
		String total_value=src.select("table.yfw td#gt7").text();
		return total_value;
	}
	public String getCurrentValue()
	{
		String current_value=src.select("table.yfw td#gt14").text();
		return current_value;
	}
	
	public String getCoreData()
	{
		String core_data="";
		Elements coreDataArea=src.select("div.pad5 table#rtp2 tr td");
		if(!coreDataArea.isEmpty())
		{
			for(int mm=0;mm<coreDataArea.size();mm++)
			{
				core_data=core_data+">>>>>"+coreDataArea.get(mm).text();
			}
		}
		//core_data=src.select("div.pad5 table#rtp2 tr td").text();
		//System.out.println("core_data = " + core_data);
		return core_data;
	}

}
