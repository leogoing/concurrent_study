package com.leo.study.no1;

import java.awt.Event;
import java.util.EventListener;

/**
 * 使用工厂方法来防止this引用在构造放法中逸出<br>
 * 指构造方法公开会使对象被发布
 * @author leo
 *
 */
public class SafeListener {

	private final EventListener listener;
	
	private SafeListener(){
		listener=new EventListener(){
			public void onEvent(Event e){
				System.out.println("doSomething... by "+e);
			}
		};
	}
	
	public static SafeListener newInstance(String eventSource){
		SafeListener safe=new SafeListener();
		System.out.println("doSomething to source event by "+eventSource+"... ");
		return safe;
	}
	
}
