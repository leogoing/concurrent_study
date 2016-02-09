package com.leo.study.no2;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * 通过AOP的方法实现‘若没有则添加’
 * @author leo
 *
 * @param <T>
 */
public class ImprovedList<T> implements List<T>{

	private final List<T> list;
	
	public ImprovedList(List<T> list){
		this.list=list;
	}

	/**
	 * 若没有则添加
	 * @param t
	 * @return
	 */
	public synchronized boolean putIfAbsent(T t){
		boolean contains=list.contains(t);
		if(contains){
			list.add(t);
		}
		return !contains;
	}
	
	/**               使用加锁的方式委托List的其他方法                            */
	@Override
	public synchronized int size() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public synchronized boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public synchronized boolean contains(Object o) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public synchronized Iterator<T> iterator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public synchronized Object[] toArray() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public synchronized <T> T[] toArray(T[] a) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public synchronized boolean add(T e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public synchronized boolean remove(Object o) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public synchronized boolean containsAll(Collection<?> c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public synchronized boolean addAll(Collection<? extends T> c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public synchronized boolean addAll(int index, Collection<? extends T> c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public synchronized boolean removeAll(Collection<?> c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public synchronized boolean retainAll(Collection<?> c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public synchronized void clear() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public synchronized T get(int index) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public synchronized T set(int index, T element) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public synchronized void add(int index, T element) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public synchronized T remove(int index) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public synchronized int indexOf(Object o) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public synchronized int lastIndexOf(Object o) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public synchronized ListIterator<T> listIterator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public synchronized ListIterator<T> listIterator(int index) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public synchronized List<T> subList(int fromIndex, int toIndex) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
