package com.leo.study.no4;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

/**
 * 使用ComplitionService使页面元素在下载完成后立即显示出来
 * @author leo
 *
 */
public class Renderer {

	private final ExecutorService executor;
	
	Renderer(ExecutorService executor){this.executor=executor;}
	
	void renderPage(CharSequence source){
		List<ImageInfo> info=scanForImageInfo(source);//获取要下载的图片信息
		CompletionService<ImageData> completionService=
				new ExecutorCompletionService<ImageData>(executor);
		for(final ImageInfo imageInfo:info){
			completionService.submit(new Callable<ImageData>(){
				public ImageData call(){
					return null;
				}
			});
		}
		renderText(source);
		
		try {
			for(int t=0,n=info.size();t<n;t++){
				Future<ImageData> f=completionService.take();
				ImageData imageData=f.get();
				renderImage(imageData);
			}
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}catch(ExecutionException e){
			//执行时异常
		}
	}
	
	private List<ImageInfo> scanForImageInfo(CharSequence source){
		return null;
	}
	private void renderText(CharSequence source){
	}
	private void renderImage(ImageData imageData){
	}
}

class ImageInfo{
}
class ImageData{
}