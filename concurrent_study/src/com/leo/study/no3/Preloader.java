package com.leo.study.no3;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * 使用FutureTask来提前加载稍后需要的数据
 * @author leo
 *
 */
public class Preloader {

	private final FutureTask<ProductInfo> future=new FutureTask<ProductInfo>(new Callable<ProductInfo>(){
					public ProductInfo call() throws Exception{
						return loadProductInfo();
					}
				}
			);
	
	private ProductInfo loadProductInfo(){
		return null;
	}
	
	private final Thread thread=new Thread(future);
	
	public void start(){
		thread.start();
	}
	
	public ProductInfo get() throws InterruptedException,Exception{
		try {
			return future.get();
		} catch (ExecutionException e) {
			Throwable cause=e.getCause();
			throw launderThrowable(cause);
		}
	}
	
	public static RuntimeException launderThrowable(Throwable cause){
		if(cause instanceof RuntimeException){
			return (RuntimeException)cause;
		}else if(cause instanceof Error){
			throw (Error)cause;
		}else{
			throw  new IllegalArgumentException("Not Unchecked ",cause);
		}
	}
	
	
}

class ProductInfo{
	
}