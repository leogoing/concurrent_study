package com.leo.study.no6;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 定制Thread基类,包含了生命周期的日志记录
 * @author leo
 *
 */
public class MyAppThread extends Thread{

	public static final String DEFAULT_NAME="MyAppThread";
	private static volatile boolean debugLifecycle=false;
	private static final AtomicInteger created=new AtomicInteger();
	private static final AtomicInteger alive=new AtomicInteger();
	private static final Logger log=Logger.getAnonymousLogger();
	
	public MyAppThread(Runnable runnable,String name) {
		super(runnable, name+"-"+created.incrementAndGet());
		setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
			@Override
			public void uncaughtException(Thread t, Throwable e) {
				log.log(Level.SEVERE, "UNCAUGHT in thread "+t.getName(), e);
			}
		});
	}
	
	public void run (){
		//复制debug标志以确保一致性
		boolean debug=debugLifecycle;
		if(debug){
			log.log(Level.FINE, "CREATED "+getName());
		}
		try {
			alive.incrementAndGet();
			super.run();
		} finally{
			alive.decrementAndGet();
			if(debug)log.log(Level.FINE, "Exiting "+getName());
		}
	}
	
}
