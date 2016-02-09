package com.leo.study.no3;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

/**
 * 使用HashMap和同步机制来构造缓存
 * @author leo
 *
 * @param <A>
 * @param <V>
 */
public class Memoizer1<A, V> implements Computable<A,V>{

	private final Map<A,V> cache=new HashMap<A,V>();
	private final Computable<A,V> c;
	
	public Memoizer1(Computable<A,V> c){
		this.c=c;
	}
	
	@Override
	public synchronized V compute(A arg) throws InterruptedException {
		V result=cache.get(arg);
		if(result == null){
			result=c.compute(arg);
			cache.put(arg, result);
		}
		return result;
	}
}

/**
 * 计算接口
 * @author leo
 *
 * @param <A>
 * @param <V>
 */
 interface Computable<A,V>{
	V compute(A arg)throws InterruptedException;
}

 /**
  * 计算实现类
  * @author leo
  *
  */
 class ExpensiveFunction implements Computable<String,BigInteger>{

	@Override
	public BigInteger compute(String arg) throws InterruptedException {
		return new BigInteger(arg);
	}
 }
 
 /**
  * 使用ConcurrentHashMap替换HashMap
  * @author leo
  *
  * @param <A>
  * @param <V>
  */
 class Memoizer2<A,V> implements Computable<A,V>{

	 private final Map<A,V> cache=new ConcurrentHashMap<A,V>();
	 private final Computable<A,V> c;
	 
	 public Memoizer2(Computable<A,V> c){this.c=c;}
	 
	@Override
	public V compute(A arg) throws InterruptedException {
		V result=cache.get(arg);
		if(result == null){
			result=c.compute(arg);
			cache.put(arg, result);
		}
		return result;
	}
 }
 
 /**
  * 基于FutureTask的Memoizing封装器<br>
  * 最终实现
  * @author leo
  *
  * @param <A>
  * @param <V>
  */
 class Memoizer<A,V> implements Computable<A,V>{

	 private final ConcurrentHashMap<A,Future<V>> cache=new ConcurrentHashMap<A,Future<V>>();
	 private final Computable<A,V> c;
	 
	 public Memoizer(Computable<A,V> c) {
		this.c=c;
	}
	 
	@Override
	public V compute(A arg) throws InterruptedException {
		while(true){
			Future<V> f=cache.get(arg);
			if(f==null){
				Callable<V> eval=new Callable<V>(){
					public V call() throws InterruptedException{
						return c.compute(arg);
					}
				};
				FutureTask<V> ft=new FutureTask<V>(eval);
				f=cache.putIfAbsent(arg, ft);//原子方法
				if(f==null){f=ft; ft.run();}
			}
			try {
				return f.get();
			} catch (CancellationException e) {
				cache.remove(arg);//取消计算任务
			}catch(ExecutionException e){
				//抛出执行期异常
			}
		}
	}
	 
 }