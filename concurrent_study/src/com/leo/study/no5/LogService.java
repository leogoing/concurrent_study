package com.leo.study.no5;

import java.io.PrintWriter;
import java.util.concurrent.BlockingQueue;

/**
 * 向LogWriter日志记录添加可靠的取消操作
 * @author leo
 *
 */
public class LogService {

	private final BlockingQueue<String> queue;
	private final LoggerThread loggerThread;
	private final PrintWriter writer;
	private boolean isShutdown;
	private int reservations;
	
	public LogService(BlockingQueue<String> queue,LoggerThread loggerThread,PrintWriter writer){
		this.loggerThread=loggerThread;
		this.queue=queue;
		this.writer=writer;
	}
	
	public void start(){loggerThread.start();}
	
	public void stop(){
		synchronized (this) {
			isShutdown=true;
		}
		loggerThread.interrupt();
	}
	
	public void log(String mes)throws InterruptedException{
		synchronized (this) {
			if(isShutdown)
				throw new IllegalStateException("日志服务已被停止...");
			++reservations;
		}
		queue.put(mes);
	}
	
	private class LoggerThread extends Thread{
		public void run(){
			try {
				while(true){
					try {
						synchronized (LogService.this) {
							if(isShutdown && reservations==0)
								break;
						}
						String msg=queue.take();
						synchronized (LogService.this) {
							--reservations;
						}
						writer.println(msg);
					} catch (InterruptedException e) {
						// TODO: handle exception
					}
				}
			}finally{
				writer.close();
			}
		}
	}
	
}
