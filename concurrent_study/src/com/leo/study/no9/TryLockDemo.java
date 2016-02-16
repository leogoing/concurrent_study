package com.leo.study.no9;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

/**
 * 通过tryLock来避免锁顺序死锁
 * @author leo
 *
 */
public class TryLockDemo {

		//模拟账户转账
		public boolean transferMoney(final Account fromAcct,final Account toAcct,final DollarAmount amount,
									long timeOut,TimeUnit unit)
			throws InterruptedException,Exception
		{
			long stopTime=System.nanoTime()+unit.toNanos(timeOut);
			while(true){
				if(fromAcct.lock.tryLock()){
					try{
						if(toAcct.lock.tryLock()){
							try{
								if(fromAcct.getBalance().compareTo(amount)<0)
									throw new Exception();
								else{
									//账户转账操作
									return true;
								}
							}finally{
								toAcct.lock.unlock();
							}
						}
					}finally{
						fromAcct.lock.unlock();
					}
				}
				if(System.nanoTime()<stopTime)
					return false;
				Thread.currentThread().sleep(0);//休眠某段时间
			}
		}
		
	}
	class Account{
		Lock lock;
		DollarAmount getBalance(){
			return null;
		}
	}//账户
	class DollarAmount implements Comparable{
		@Override
		public int compareTo(Object o) {
			// TODO Auto-generated method stub
			return 0;
		}}//总额
