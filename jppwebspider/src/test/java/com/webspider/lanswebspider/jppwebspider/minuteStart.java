package com.webspider.lanswebspider.jppwebspider;

public class minuteStart implements Runnable{
	public void run()
	{
		boolean bool=true;
		
		while(bool)
		{
			System.out.println("执行了一次，执行了一次！");
			try {
				Thread.sleep(60000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		minuteStart start= new minuteStart();
		Thread t=new Thread(start);
		t.start();
	}

}
