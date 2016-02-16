package com.leo.study.no10;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.naming.InterruptedNamingException;

/**
 * 使用Lock及Condition来实现Semaphore
 * @author leo
 *
 */
public class SemaphoreOnLock {

	private final Lock lock=new ReentrantLock();
	private final Condition permitsAvailable=lock.newCondition();
	private int permits;
	
	SemaphoreOnLock(int permits) {
		lock.lock();
		try {
			this.permits=permits;
		} finally {
			lock.unlock();
		}
	}
	
	public void acquire() throws InterruptedException{
		lock.lock();
		try {
			while(permits<=0)
				permitsAvailable.await();
			--permits;
		} finally {
			lock.unlock();
		}
	}
	
	public void release(){
		lock.lock();
		try {
			++permits;
			permitsAvailable.signal();
		} finally {
			lock.unlock();
		}
	}
	
}
