package com.leo.study.no1;

/**
 * 在没有同步的情况下共享变量（不要这么做）<br>
 * 会在很小的几率下出现各种不同结果<br>
 * 涉及到变量可见性问题
 * @author leo
 *
 */
public class NoVisibility {

	private static boolean ready;
	private static int number;
	
	private static class ReaderThread extends Thread{
		public void run(){
			System.out.println("进入run...");
			while(!ready){
				Thread.yield();
			}
			System.out.println(number);
		}
	}
	
	public static void main(String[] args) {
		new ReaderThread().start();
		number=42;
		ready=true;
	}
}
