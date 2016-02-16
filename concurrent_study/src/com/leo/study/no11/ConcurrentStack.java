package com.leo.study.no11;

import java.util.concurrent.atomic.AtomicReference;

/**
 * 使用Treiber算法构造非阻塞栈
 * @author leo
 *
 */
public class ConcurrentStack<E> {

	AtomicReference<Node<E>> top=new AtomicReference<Node<E>>();
	
	public void push(E item){
		Node<E> newHead=new Node<E>(item);
		Node<E> oldHead;
		do{
			oldHead=top.get();
			newHead.next=oldHead;
		}while(!top.compareAndSet(oldHead, newHead));
	}
	
	public E pop(){
		Node<E> newHead;
		Node<E> oldHead;
		do{
			oldHead=top.get();
			if(oldHead==null)
				return null;
			newHead=oldHead.next;
		}while(!top.compareAndSet(oldHead, newHead));
		return oldHead.item;
	}
	
	
	private static class Node<E>{
		public final E item;
		public Node<E> next;
		
		public Node(E item) {
			this.item=item;
		}
	}
	
}
