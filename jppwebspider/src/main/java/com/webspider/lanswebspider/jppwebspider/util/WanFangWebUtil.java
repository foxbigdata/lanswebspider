package com.webspider.lanswebspider.jppwebspider.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WanFangWebUtil {
	public static final String floatDigitStr = "\\d+(\\.\\d+)?";
	public static final Pattern floatDigitPattern = Pattern.compile(floatDigitStr);
	public static final String wanFangViewUrl="(http://d.wanfangdata.com.cn/.*)";
	public static final Pattern wanFangViewPattern=Pattern.compile(wanFangViewUrl);
	public boolean isHomePage(String url){
		boolean target=false;
		if(url.contains("http://s.wanfangdata.com.cn/Paper.aspx?q=") &&  !url.contains("&p=")){
			target=true;
		}
		return target;
		
	}
	
	public boolean isHubPage(String url){
		boolean target=false;
		if(url.contains("http://s.wanfangdata.com.cn/Paper.aspx?q=") && url.contains("&p=")){
			target=true;
		}
		return target;
		
	}
	
	public boolean isViewPage(String url){
		boolean target=false;
		Matcher m =wanFangViewPattern.matcher(url);
		if(m.matches()){
			target=true;
		}
		return target;
	}
	
	
	
	

}
