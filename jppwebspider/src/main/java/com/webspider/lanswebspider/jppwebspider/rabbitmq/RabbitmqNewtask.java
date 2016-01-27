package com.webspider.lanswebspider.jppwebspider.rabbitmq;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

public class RabbitmqNewtask {
	private static final String TASK_QUEUE_NAME="task_queue";
	private static final String HOST_NAME="localhost";
	public static void SendMessage(String[] argv) throws IOException, TimeoutException
	{
		ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(HOST_NAME);
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        // 指定队列持久化
        channel.queueDeclare(TASK_QUEUE_NAME, true, false, false, null);
        String message = getMessage(argv);
        // 指定消息持久化
        channel.basicPublish("", TASK_QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
        System.out.println("发送数据 '" + message + "'");
        channel.close();
        connection.close();
		
	}
	
	private static String getMessage(String[] strings) {
        if (strings.length < 1)
            return " 数据为空!";
        return joinStrings(strings, " ");
    }
	
	 private static String joinStrings(String[] strings, String delimiter) {
	        int length = strings.length;
	        if (length == 0)
	            return "";
	        StringBuilder words = new StringBuilder(strings[0]);
	        for (int i = 1; i < length; i++) {
	            words.append(delimiter).append(strings[i]);
	        }
	        return words.toString();
	    }
	

}