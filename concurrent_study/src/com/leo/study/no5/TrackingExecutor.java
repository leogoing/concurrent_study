package com.leo.study.no5;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.AbstractExecutorService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 实现在shutdownNow时记录正在执行的任务<br>
 * 在程序执行的最后完成时线程池关闭可能产出“误报”，已执行完的程序可能会被记录下
 * @author leo
 *
 */
public class TrackingExecutor extends AbstractExecutorService{

	private final ExecutorService exec;
	private final Set<Runnable> tasksCancelledAtShutdown=Collections.synchronizedSet(new HashSet<Runnable>());
	
	public TrackingExecutor(ExecutorService exec) {
		this.exec=exec;
	}
	
	public List<Runnable> getCancelledTasks(){
		if(!exec.isTerminated())
			throw new IllegalStateException("任务未关闭！");
		return new ArrayList<Runnable>(tasksCancelledAtShutdown);
	}
	
	public void execute(final Runnable runnable){
		exec.execute(new Runnable(){
			public void run(){
				try {
					runnable.run();
				}finally{
					if(isShutdown() && Thread.currentThread().isInterrupted())
						tasksCancelledAtShutdown.add(runnable);
				}
			}
		});
	}
	
	@Override
	public void shutdown() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Runnable> shutdownNow() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isShutdown() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isTerminated() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
		// TODO Auto-generated method stub
		return false;
	}

}
