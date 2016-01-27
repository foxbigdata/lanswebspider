package com.webspider.lanswebspider.jppwebspider.thrift;

import org.apache.thrift.TException;

import thrift.ThriftService.Iface;

public class ThriftServiceImpl implements Iface{

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public int add(int a, int b) throws TException {
		// TODO Auto-generated method stub
		return a+b;
	}

}
