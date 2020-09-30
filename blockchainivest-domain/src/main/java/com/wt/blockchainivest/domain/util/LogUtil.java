package com.wt.blockchainivest.domain.util;

public class LogUtil {

	public static void print(String log) {
		System.out.println(log);
	}

	public static void print(String log, Exception e) {
		System.out.println(log);
		e.printStackTrace();
	}
}
