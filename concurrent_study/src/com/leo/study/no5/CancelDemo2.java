package com.leo.study.no5;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * 通过Future来取消任务
 * @author leo
 *
 */
public class CancelDemo2 {
	private static ScheduledExecutorService cancelExecutor=Executors.newScheduledThreadPool(10);
	
	public static void timeRun(final Runnable r,long timeout,TimeUnit unit)throws InterruptedException{
		Future<?> task=cancelExecutor.submit(r);
		try {
			task.get(timeout,unit);
		} catch (TimeoutException e) {
			//任务会被取消
		}catch(ExecutionException e){
			throw new RuntimeException(e.getCause());//任务中抛出异常则重新抛出去
		}finally{
			task.cancel(true);//如果任务已经结束，执行取消也不会带来影响，如果任务正在运行，那么将被中断
		}
	}

}
