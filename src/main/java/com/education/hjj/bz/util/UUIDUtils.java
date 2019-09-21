package com.education.hjj.bz.util;

import java.util.UUID;

public class UUIDUtils {
	
	public static String getRandomNum() {
		String str = UUID.randomUUID().toString();
		return str;
	}
	
	public static String getRandomNumBySub() {
		String str = UUID.randomUUID().toString();
		return str.replaceAll("-", "");
	}

}
