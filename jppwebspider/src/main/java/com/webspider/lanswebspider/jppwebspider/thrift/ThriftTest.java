package com.webspider.lanswebspider.jppwebspider.thrift;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

import thrift.ThriftService;

public class ThriftTest {

	/**
	 * @param args
	 * @throws TException 
	 */
	public static void main(String[] args) throws TException {
		
		// TODO Auto-generated method stub
		 TTransport transport = new TSocket("localhost", 7911);  
	     transport.open();  
	     TProtocol protocol = new TBinaryProtocol(transport);  
	     ThriftService.Client client = new ThriftService.Client(protocol);  
	     System.out.println(client.add(700, 55));  
	     transport.close();  
	}

}
