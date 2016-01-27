package com.webspider.lanswebspider.jppwebspider.thrift;

import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TBinaryProtocol.Factory;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TTransportException;

import thrift.SpiderUrl;

public class SpiderUrlServer {
	
	public void startServer() throws TTransportException{
		 TServerSocket serverTransport = new TServerSocket(11236);  
		 Factory proFactory =new TBinaryProtocol.Factory();
		 TProcessor processor = new SpiderUrl.Processor<SpiderUrl.Iface>(new SpiderUrlImpl());
		 TServer.Args tArgs= new TServer.Args(serverTransport);
		 tArgs.processor(processor);
		 tArgs.protocolFactory(proFactory);
		 TServer server= new TSimpleServer(tArgs);
		 System.out.println("Start server on port 11236....");
		 server.serve();
	}
	

	/**
	 * @param args
	 * @throws TTransportException 
	 */
	public static void main(String[] args) throws TTransportException {
		// TODO Auto-generated method stub
		SpiderUrlServer server=new SpiderUrlServer();
		server.startServer();
	}

}
