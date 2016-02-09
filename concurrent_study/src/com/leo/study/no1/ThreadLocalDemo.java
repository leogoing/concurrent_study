package com.leo.study.no1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * 使用threadLocal类来维持线程封闭<br>
 * 实质相当于一个Map为每一个线程保存其独享的值
 * @author leo
 *
 */
public class ThreadLocalDemo {
	
	private static final String DB_URL="Some URl";

	private static ThreadLocal<Connection> connectionHolder=
				new ThreadLocal<Connection>(){
		public Connection initialValue(){
			Connection conn=null;
			try {
				conn= DriverManager.getConnection(DB_URL);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return conn;
		}
	};
	
	public static Connection getConnection(){
		return connectionHolder.get();
	}
	
}
