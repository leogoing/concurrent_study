package com.leo.study.no5;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.concurrent.RunnableFuture;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 通过newTaskFor将非标准的取消操作封装在一个任务中
 * @author leo
 *
 */
public class CancellingExecutor extends ThreadPoolExecutor{

	public CancellingExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit,
			BlockingQueue<Runnable> workQueue) {
		super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
		// TODO Auto-generated constructor stub
	}

	protected <T> RunnableFuture<T> newTaskFor(Callable<T> callable){
		if(callable instanceof CancellableTask)
			return ((CancellableTask<T>)callable).newTask();
		else
			return super.newTaskFor(callable);
	}
}

interface CancellableTask<T> extends Callable<T>{
	void cancel();
	RunnableFuture<T> newTask();
}

abstract class SocketUsingTask<T> implements CancellableTask<T>{
	private Socket socket;
	
	protected synchronized void setSoket(Socket s){socket=s;}
	
	public synchronized void cancel(){
		try {
			if(socket!=null)
				socket.close();
		} catch (IOException e) {
		}
	}
	
	public RunnableFuture<T> newTask(){
		return new FutureTask<T>(this){
			/*包装了cancel方法先关闭流*/
			public boolean cancel(boolean mayInterruptIfRunning){
				try {
					SocketUsingTask.this.call();
				} finally {
					return super.cancel(mayInterruptIfRunning);
				}
			}
		};
	}
	
}