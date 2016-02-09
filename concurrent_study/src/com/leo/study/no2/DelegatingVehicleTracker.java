package com.leo.study.no2;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * 发布车辆追踪器  将线程安全委托给ConcurrentHashMap<br>
 * 使用两个变量一个用来发布不可修改的Map集
 * @author leo
 *
 */
public class DelegatingVehicleTracker {

	private final ConcurrentMap<String,Point> locations;
	private final Map<String,Point> unmodifiableMap;
	
	public DelegatingVehicleTracker(Map<String,Point> points){
		locations=new ConcurrentHashMap<String,Point>(points);
		unmodifiableMap=Collections.unmodifiableMap(locations);
	}
	
	public Map<String,Point> getLocations(){
		return unmodifiableMap;
	}
	
	public Point getLocation(String id){
		return locations.get(id);
	}
	
	public void setLocations(String id,int x,int y){
		if(locations.replace(id, new Point(x,y))==null){
			throw new IllegalArgumentException("invalid vehicle name: "+id);
		}
	}
	
}

/**
 * 不可变的Point类
 * @author leo
 *
 */
class Point{

	public final int x,y;
	
	public Point(int x,int y){
		this.x=x;
		this.y=y;
	}
	
}