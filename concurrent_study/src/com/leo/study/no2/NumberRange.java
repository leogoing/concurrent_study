package com.leo.study.no2;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 保证不变性规则的数字操作<br>
 * 非线程安全类
 * @author leo
 *
 */
public class NumberRange {

	/*不变性条件 ： lower<=upper*/
	private final AtomicInteger lower=new AtomicInteger(0);
	private final AtomicInteger upper=new AtomicInteger(0);
	
	/*不安全的先检查后执行*/
	public void setLower(int i){
		if(i<upper.get()){
			throw new IllegalArgumentException("can't set lower to "+i+" > upper");
		}
		lower.set(i);
	}
	
	/*不安全的先检查后执行*/
	public void setUpper(int i){
		if(i<lower.get()){
			throw new IllegalArgumentException("can't set upper to "+i+" < lower");
		}
		upper.set(i);
	}
	
	public boolean isInRange(int i){
		return (i>=lower.get() && i<=upper.get());
	}
	
}
