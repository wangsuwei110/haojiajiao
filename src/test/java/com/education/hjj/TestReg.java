package com.education.hjj;

import java.util.HashMap;
import java.util.Map;

public class TestReg {

	public static void main(String[] args) {
		
		
		String aa="khsdbku1234hg)(*";
		
		char[] ch=aa.toCharArray();

		Map<String,Integer>	map =sum(ch);
		
		System.out.println("字母个数"+map.get("str")+"数字个数"+map.get("nu"));
	}
	
	
	public static Map<String,Integer> sum(char[] ch) {
		
		Integer c=0;
		Integer d=0;
		String reg="^[a-zA-Z]";
		String reg1="^[0-9]";
		
		for(int i=0;i<ch.length;i++) {
			
			boolean a=String.valueOf(ch[i]).matches(reg);
			boolean b=String.valueOf(ch[i]).matches(reg1);
			if(a) {
				c++;
			}
			
			if(b) {
				d++;
			}
		}
		
		Map<String,Integer> map =new HashMap<String, Integer>();
		
		map.put("nu", d);
		map.put("str", c);
		
		return map;
	}
	
	
	public static boolean matchStr(char num) {
		String reg="^[a-zA-Z]";
		
		boolean b=String.valueOf(num).matches(reg);
		
		return b;
	} 
	
	public static boolean matchInt(char num) {
		String reg1="^[0-9]";
		
		boolean b=String.valueOf(num).matches(reg1);
		
		return b;
	} 
}
