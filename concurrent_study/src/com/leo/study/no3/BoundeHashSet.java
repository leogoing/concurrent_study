package com.leo.study.no3;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Semaphore;

/**
 * 使用信号量Semaphore为容器设置边界<br>
 * 控制了访问的操作次数而不是控制不同的线程数量
 * @author leo
 *
 * @param <T>
 */
public class BoundeHashSet<T> {

	private final Set<T> set;
	private final Semaphore sem;
	
	public BoundeHashSet(int bound){
		this.set=Collections.synchronizedSet(new HashSet<T>());
		this.sem=new Semaphore(bound);
	}
	
	public boolean add(T o)throws InterruptedException{
		sem.acquire();
		boolean wasAdded=false;
		try {
			wasAdded=set.add(o);
			return wasAdded;
		} finally {
			if(!wasAdded){
				sem.release();
			}
		}
	}
	
	public boolean remove(T o){
		boolean wasRemoved=set.remove(o);
		if(wasRemoved)
			sem.release();
		return wasRemoved;
	}
	
	public static void main(String[] args) throws InterruptedException {
		BoundeHashSet<String> bh=new BoundeHashSet<String>(2);
		boolean l=bh.add("x");
		System.out.println("..."+l);
		boolean b=bh.add("x");
		System.out.println("..."+b);
	}
	
}
