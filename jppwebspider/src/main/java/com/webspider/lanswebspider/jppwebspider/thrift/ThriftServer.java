package com.webspider.lanswebspider.jppwebspider.thrift;

import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TBinaryProtocol.Factory;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TTransportException;

import thrift.ThriftService;

public class ThriftServer {

	/**
	 * @param args
	 * @throws TTransportException 
	 */
	public static void main(String[] args) throws TTransportException {
		// TODO Auto-generated method stub
		TServerSocket serverTransport = new TServerSocket(7911);  
		  
        Factory proFactory = new TBinaryProtocol.Factory();  

        TProcessor processor = new ThriftService.Processor<ThriftService.Iface>(  
                new ThriftServiceImpl());  
        TServer.Args tArgs = new TServer.Args(serverTransport);  
        tArgs.processor(processor);  
        tArgs.protocolFactory(proFactory);  

        TServer server = new TSimpleServer(tArgs);  
        System.out.println("Start server on port 7911....");  
        server.serve();  

	}

}
