package com.webspider.lanswebspider.jppwebspider.rabbitmq;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import org.apache.thrift.TException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.ConsumerCancelledException;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.ShutdownSignalException;
import com.webspider.lanswebspider.jppwebspider.thrift.SpiderUrlClient;

public class RabbitmqWorker {
	private static final String TASK_QUEUE_NAME="task_queue";
	private static final String HOST_NAME="localhost";
	private static final int sleeptime=100;
	public static void ReceiveMsg(String url) throws IOException, TimeoutException, ShutdownSignalException, ConsumerCancelledException, InterruptedException, TException
	{
		ConnectionFactory factory= new ConnectionFactory();
		factory.setHost(HOST_NAME);
		Connection connection=factory.newConnection();
		Channel channel = connection.createChannel();
		//指定队列持久化
		channel.queueDeclare(TASK_QUEUE_NAME,true,false,false,null);
		System.out.println("等待接收消息。请按CRTL+C键退出！");
		//指定该消费者同时只接收一条消息
		channel.basicQos(1);
		QueueingConsumer consumer= new QueueingConsumer(channel);
		//打开消息应答机制
		channel.basicConsume(TASK_QUEUE_NAME, false,consumer);
		while(true)
		{
			QueueingConsumer.Delivery delivery = consumer.nextDelivery();
			String message= new String(delivery.getBody());
			System.out.println("接收到的消息为:"+message+"");
			doWork(message);
			String[] tmp=message.split(" ");
			for(int mm=1;mm<tmp.length;mm++)
			{
				SpiderUrlClient spiderclinet=new SpiderUrlClient();
				System.out.println("tmp[mm] = "+tmp[mm]);
				spiderclinet.thriftClient(tmp[mm]);
			}
			
			System.out.println("接收消息成功！");
			//返回接收到消息的确认消息
			channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
		}
		
	}
	
	private static void doWork(String task) throws InterruptedException{
		for(char ch: task.toCharArray()){
			if(ch == '.')
				Thread.sleep(sleeptime);
		}
	}
	
	public static void main(String[] args) throws TException, ShutdownSignalException, ConsumerCancelledException, IOException, TimeoutException, InterruptedException {
		String url="111";
		ReceiveMsg(url);
	}
	
	

}
