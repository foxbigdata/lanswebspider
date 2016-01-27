package com.webspider.lanswebspider.jppwebspider.thrift;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

import thrift.SpiderUrl;

public class SpiderUrlClient {
	public static void thriftClient(String url) throws TException
	{
		TTransport transport =new TSocket("10.105.129.250",11236);
		transport.open();
		TProtocol protocol=new TBinaryProtocol(transport);
		SpiderUrl.Client client=new SpiderUrl.Client(protocol);
		client.spiderUrlString(url);
		//System.out.println(client.spiderUrlString(url));
		transport.close();
	}
	
	
	/**
	 * @param args
	 * @throws TException 
	 */
	public static void main(String[] args) throws TException {
		// TODO Auto-generated method stub
		//String testCqVipUrl="http://www.cqvip.com/main/search.aspx?k=汽车发动机转速控制";
		//String testWanFangUrl="http://s.wanfangdata.com.cn/Paper.aspx?q=汽车发动机转速控制&f=top";
		//http://d.wanfangdata.com.cn/Thesis/Y795191
		String testWanFangUrl="http://d.wanfangdata.com.cn/Thesis/Y795191";
		thriftClient(testWanFangUrl);
	}
}
