package com.webspider.lanswebspider.jppwebspider.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CqVipWebUtil {
	public static final String cqVipViewUrl="(.*./QK/.*)";
	public static final Pattern cqVipViewPattern=Pattern.compile(cqVipViewUrl);
	public boolean isHomePage(String url){
		boolean target=false;
		if(url.contains("http://www.cqvip.com/main/search.aspx?") &&  !url.contains("p=")){
			target=true;
		}
		return target;
		
	}
	public boolean isHubPage(String url){
		boolean target=false;
		if(url.contains("http://www.cqvip.com/main/search.aspx?") && url.contains("p=")){
			target=true;
		}
		return target;
		
	}
	
	public boolean isViewPage(String url){
		boolean target=false;
		Matcher m =cqVipViewPattern.matcher(url);
		if(m.matches()){
			target=true;
		}
		return target;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
