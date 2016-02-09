package com.leo.study.no3;

import java.io.File;
import java.io.FileFilter;
import java.util.concurrent.BlockingQueue;

/**
 * 搜索文件中的生产者和消费者任务
 * @author leo
 *
 */
public class FileCrawler implements Runnable{

	private final BlockingQueue<File> fileQueue;
	private final FileFilter fileFilter;
	private final File root;
	
	public FileCrawler(BlockingQueue<File> fileQueue,FileFilter fileFilter,File root){
		this.fileFilter=fileFilter;
		this.fileQueue=fileQueue;
		this.root=root;
	}

	@Override
	public void run() {
		try {
			crawl(root);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
		
	}
	
	/**
	 * 查找文件
	 * @param root
	 * @throws InterruptedException
	 */
	private void crawl(File root) throws InterruptedException{
		File[] entries=root.listFiles(fileFilter);
		if(entries!=null){
			for(File entry: entries){
				if(entry.isDirectory()){
					crawl(entry);
				}else if(!alreadyIndexed(entry)){
					fileQueue.put(entry);
				}
			}
		}
	}
	
	/**
	 * 是否已经建立好索引
	 * @param f
	 * @return
	 */
	private boolean alreadyIndexed(File f){
		return false;
	}
	
}

/**
 * 消费者 可根据消费者数量创建多个线程建立索引效率高
 * @author leo
 *
 */
class Indexer implements Runnable{

	private final BlockingQueue<File> queue;
	
	public Indexer(BlockingQueue<File> queue){
		this.queue=queue;
	}
	
	@Override
	public void run() {
		try {
			while(true){
				indexFile(queue.take());
			}
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}
	
	/**
	 * 建立索引
	 * @param f
	 */
	private void indexFile(File f){
		
	}
	
}
