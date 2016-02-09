package com.leo.study.no5;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 中断任务  工作线程与取消线程分开
 * @author leo
 *
 */
public class CancelDemo1 {
	private static ScheduledExecutorService cancelExecutor=Executors.newScheduledThreadPool(10);

	public static void timeRun(final Runnable r,long timeout,TimeUnit unit)throws InterruptedException{
		class RethrowableTask implements Runnable{
			private volatile Throwable t;
			@Override
			public void run() {
				try {
					r.run();
				} catch (Throwable t) {
					this.t=t;
				}
			}
			void rethrow(){
				if(t!=null){
					//throw t;对异常进行细分等
				}
			}
		}
		
		RethrowableTask task=new RethrowableTask();
		final Thread taskThread=new Thread(task);
		taskThread.start();
		cancelExecutor.schedule(new Runnable(){
			public void run(){
				taskThread.interrupt();
			}
		}, timeout, unit);
		taskThread.join(unit.toMillis(timeout));//等待线程结束或操时则通过
		task.rethrow();
	}
	
}
