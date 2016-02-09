package com.leo.study.no3;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;


/**
 * 使用栅栏CyclicBarrier协调细胞自动衍生系统中的计算
 * @author leo
 *
 */
public class CellularAutomata {

	private final Board mainBoard;
	private final CyclicBarrier barrier;
	private final Worker[] workers;
	
	public CellularAutomata(Board board){
		this.mainBoard=board;
		/*查询系统处理器数量*/
		int count=Runtime.getRuntime().availableProcessors();
		this.barrier=new CyclicBarrier(count,new Runnable(){//可能通过栅栏后执行
			public void run(){
				mainBoard.commitNewValues();
			}
		});
		this.workers=new Worker[count];
		for(int i=0;i<count;i++)
			workers[i]=new Worker(mainBoard.getSubBoard(count, i));
	}
	
	public void start(){
		for(int i=0;i<workers.length;i++){
			new Thread(workers[i]).start();
		}
		mainBoard.waitForConvergence();
	}

	private class Worker implements Runnable{
		private final Board board;
		
		public Worker(Board board){
			this.board=board;
		}
		
		@Override
		public void run() {
			while(!board.hasConverged()){
				for(int x=0;x<board.getMaxX();x++){
					for(int y=0;y<board.getMaxY();y++){
//						board.setNewValue(...);
					}
				}
				try {
					barrier.await();
				} catch (InterruptedException e) {
					return;
				} catch (BrokenBarrierException e) {
					return;
				}
			}
		}
		
	}
	
}

class Board{
	public void commitNewValues(){
		
	}
	public Board getSubBoard(int s,int e){
		return null;
	}
	public boolean hasConverged(){
		return false;
	}
	public int getMaxX(){
		return 0;
	}
	public int getMaxY(){
		return 0;
	}
	public void waitForConvergence(){
		
	}
}
