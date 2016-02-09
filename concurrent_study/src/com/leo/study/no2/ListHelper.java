package com.leo.study.no2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 通过客户端加锁来实现List集合‘若没有则添加’
 * @author leo
 *
 * @param <E>
 */
public class ListHelper<E> {

	public List<E> list=Collections.synchronizedList(new ArrayList<E>());
	
	public boolean putIfAbsent(E e){
		/*此时锁对象必须与list集合锁一致*/
		synchronized (list) {
			boolean absent=!list.contains(e);
			if(absent){
				list.add(e);
			}
			return absent;
		}
	}
	
}
