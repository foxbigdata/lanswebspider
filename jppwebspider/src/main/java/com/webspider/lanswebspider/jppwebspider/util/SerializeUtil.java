package com.webspider.lanswebspider.jppwebspider.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SerializeUtil {
	public static byte[] serialize(Object object){
		ObjectOutputStream oos=null;
		ByteArrayOutputStream baos=null;
		
		try {
			//序列化
			baos=new ByteArrayOutputStream();
			oos=new ObjectOutputStream(baos);
			oos.writeObject(object);
			byte[] bytes =baos.toByteArray();
			return bytes;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static Object unserialize(byte[] bytes) throws ClassNotFoundException
	{
		ByteArrayInputStream bais=null;
		//反序列化
		
		try {
			bais= new ByteArrayInputStream(bytes);
			ObjectInputStream ois;
			ois = new ObjectInputStream(bais);
			return ois.readObject();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		
		
		
		return null;
		
	}

}
