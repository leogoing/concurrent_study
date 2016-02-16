package com.leo.study.no6;

import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.Semaphore;

/**
 * 使用Semaphore信号量来控制任务的提交速率
 * 
 * @author leo
 *
 */
public class BoundedExecutor {

	private final Executor exec;
	private final Semaphore semaphore;
	
	public BoundedExecutor(Executor exec,Semaphore semaphore) {
		this.exec=exec;
		this.semaphore=semaphore;
	}
	
	public void submitTask(final Runnable command)throws InterruptedException{
		semaphore.acquire();//获取一个许可，没有则等待
		try {
			exec.execute(new Runnable() {
				@Override
				public void run() {
					try {
						command.run();
					} finally {
						semaphore.release();
					}
				}
			});
		} catch (RejectedExecutionException e) {
			semaphore.release();//释放许可
		}
	}
	
}
