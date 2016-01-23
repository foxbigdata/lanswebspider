package com.webspider.lanswebspider.jppwebspider.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StockcodeUtil {
	public static final String baseUrl="http://quote.eastmoney.com/stocklist.html";
	public static final String itemUrl="http://quote.eastmoney.com/s(h|z)\\d+.html";
	public static final Pattern itemPattern=Pattern.compile(itemUrl);
	
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

}
