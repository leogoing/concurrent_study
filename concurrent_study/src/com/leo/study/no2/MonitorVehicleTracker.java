package com.leo.study.no2;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * 基于监视器模式发布的车辆追踪<br>
 * 封闭了不安全的MutablePoint，损失了部分性能(复制)
 * @author leo
 *
 */
public class MonitorVehicleTracker {

	private final Map<String,MutablePoint> locations;
	
	public MonitorVehicleTracker(Map<String,MutablePoint> locations){
		this.locations=deepCopy(locations);
	}
	
	public synchronized Map<String,MutablePoint> getLocations(){
		return deepCopy(locations);
	}
	
	public synchronized MutablePoint getLocation(String id){
		MutablePoint loc=locations.get(id);
		return loc==null?null:new MutablePoint(loc);
	}
	
	public synchronized void setLocation(String id,int x,int y){
		MutablePoint loc=locations.get(id);
		if(loc==null)
			throw new IllegalArgumentException("No such ID: "+id);
		loc.x=x;
		loc.y=y;
	}
	
	private static Map<String,MutablePoint> deepCopy(Map<String,MutablePoint> locations){
		Map<String,MutablePoint> result=new HashMap<String,MutablePoint>();
		for(String id:locations.keySet()){
			result.put(id, new MutablePoint(locations.get(id)));
		}
		return Collections.unmodifiableMap(result);
	}
	
}

/**
 * 非线程安全的 <br>
 * 可变的Point类
 * @author leo
 *
 */
class MutablePoint{
	public int x,y;
	
	public MutablePoint(){
		x=0;y=0;
	}
	
	public MutablePoint(MutablePoint p){
		x=p.x;
		y=p.y;
	}
	
}