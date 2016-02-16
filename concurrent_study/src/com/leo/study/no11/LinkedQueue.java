package com.leo.study.no11;

import java.util.concurrent.atomic.AtomicReference;

/**
 * Michael-Scott非阻塞链表算法中的插入算法
 * 在插入新元素之前会首先检查是否有另一个元素正在插入元素，此时当前线程不会
 * 等待其他线程执行完成，而是帮助他完成
 * @author leo
 *
 */
public class LinkedQueue<E> {

	private static class Node<E>{
		final E item;
		final AtomicReference<Node<E>> next;
		public Node(E item,Node<E> next){
			this.item=item;
			this.next=new AtomicReference<Node<E>>(next);
		}
	}
	
	private final Node<E> dummy=new Node<E>(null,null);
	private final AtomicReference<Node<E>> head=new AtomicReference<Node<E>>(dummy);
	private final AtomicReference<Node<E>> tail=new AtomicReference<Node<E>>(dummy);
	
	public boolean put(E item){
		Node<E> newNode=new Node<E>(item,null);
		while(true){
			Node<E> curTail=tail.get();
			Node<E> tailNext=curTail.next.get();
			if(curTail==tail.get()){
				if(tailNext!=null){
					//队列处于中间状态，推进尾节点
					tail.compareAndSet(curTail, tailNext);
				}else{
					//处于稳定状态，尝试插入新节点
					if(curTail.next.compareAndSet(null, newNode)){
						//插入操作成功，尝试推进尾节点
						tail.compareAndSet(curTail, newNode);
						return true;
					}
				}
			}
		}
	}
	
}
