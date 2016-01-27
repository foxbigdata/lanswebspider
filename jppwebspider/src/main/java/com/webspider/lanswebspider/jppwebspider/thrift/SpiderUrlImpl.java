package com.webspider.lanswebspider.jppwebspider.thrift;

import org.apache.thrift.TException;

import com.webspider.lanswebspider.jppwebspider.CqVipWebProcessor;
import com.webspider.lanswebspider.jppwebspider.WanFangWebProcessor;

import thrift.SpiderUrl.Iface;
import us.codecraft.webmagic.Spider;

public class SpiderUrlImpl implements Iface{

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public String spiderUrlString(String word) throws TException {
		// TODO Auto-generated method stub
		 System.out.println("spider word = " + word); 
		 if(word.contains("wanfangdata.com.cn/")){
			 Spider.create(new WanFangWebProcessor()).addUrl(word).thread(5).run();
		 }else if(word.contains("cqvip.com/")){
			 Spider.create(new CqVipWebProcessor()).addUrl(word).thread(1).run();
		 }
		 
	     return "spider word = " + word;  
	}

}
