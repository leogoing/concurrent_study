package com.leo.study.no9;

import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 用读-写锁来包装Map
 * 只是做了轻包装实现entrySet、values等方法非常困难
 * @author leo
 *
 */
public class ReadWriteMap<K,V> {

	private final Map<K,V> map;
	private final ReadWriteLock lock=new ReentrantReadWriteLock();
	private final Lock r=lock.readLock();
	private final Lock w=lock.writeLock();
	
	public ReadWriteMap(Map<K,V> map){
		this.map=map;
	}
	
	public V put(K key,V value){
		w.lock();
		try {
			return map.put(key, value);
		} finally {
			w.unlock();
		}
	}
	
	public V get(Object key){
		r.lock();
		try {
			return map.get(key);
		} finally {
			r.unlock();
		}
	}
	
}
