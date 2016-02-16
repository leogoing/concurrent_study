package com.leo.study.no10;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 有界队列实现的基类
 * 数组队列的结构顺序不会随取出而整体前移只是指针（第一个）在移动
 * @author leo
 *
 */
public class BaseBoundedBuffer<V> {

	private final V[] buff;
	private int tail;
	private int head;
	private int count;
	
	public BaseBoundedBuffer(int capacity) {
		this.buff=(V[])new Object[capacity];
	}
	
	protected synchronized final void doPut(V v){
		buff[tail]=v;
		if(++tail==buff.length)
			tail=0;
		++count;
	}
	
	protected synchronized final V doTake() {
		V v =buff[head];
		buff[head] =null;
		if(++head==buff.length)
			head=0;
		--count;
		return v;
	}
	
	public synchronized final boolean isFull(){
		return count==buff.length;
	}
	
	public synchronized final boolean isEmpty(){
		return count==0;
	}
	
	public static void main(String[] args) throws InterruptedException {
		final BoundedBuffer b=new BoundedBuffer(4);
		for(int i=0;i<20;i++){
			final int a=i;
			new Thread(new Runnable() {
				public void run() {
					try {
						b.put(a);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}).start();
		}
		synchronized (b) {
		try {
			b.wait(3000);	
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		}		
		b.take();
	}
}

class BoundedBuffer<V> extends BaseBoundedBuffer<V>{

	public BoundedBuffer(int capacity) {
		super(capacity);
	}
	
	public synchronized void put(V v) throws InterruptedException{
		System.out.println("in put");
		while(isFull())
			wait();
		System.out.println("ready put");
		boolean empty=isEmpty();
		doPut(v);
		if(empty)		//使用条件通知避免不必要的唤醒尽量减少notifyAll的性能开销
			notifyAll();
	}
	
	public synchronized V take() throws InterruptedException{
		System.out.println("in take");
		while(isEmpty())
			wait();
		System.out.println("ready take");
		V v=doTake();
		notifyAll();
		return v;
	}
	
}