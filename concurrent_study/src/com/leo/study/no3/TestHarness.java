package com.leo.study.no3;

import java.util.concurrent.CountDownLatch;

/**
 * 在计时测试中使用CountDownLatch来启动和停止线程<br>
 * 用来测试并发效果
 * @author leo
 *
 */
public class TestHarness {

	public long timeTasks(int nThreads,final Runnable task)throws InterruptedException{
		final CountDownLatch startGate=new CountDownLatch(1);
		final CountDownLatch endGate=new CountDownLatch(nThreads);
		for(int i=0;i<nThreads;i++){
			Thread t=new Thread(){
				public void run(){
					try {
						startGate.await();
						try {
							task.run();
						} finally {
							endGate.countDown();
						}
					} catch (InterruptedException e) {	}
				}
			};
			System.out.println(t.getName()+" 已经被创建...");
		t.start();
		}
		long start=System.nanoTime();
		startGate.countDown();
		endGate.await();
		long end=System.nanoTime();
		return end-start;
	}
	
	public static void main(String[] args) throws InterruptedException {
		TestHarness t=new TestHarness();
		long time=t.timeTasks(10, new Runnable(){
			public void run(){
				System.out.println(Thread.currentThread().getName()+" is running...");
				
			}
		});
		System.out.println("执行共用时 "+time);
	}
	
}
