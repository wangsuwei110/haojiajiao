package com.education.hjj;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.education.hjj.bz.util.UUIDUtils;

public class Test {

	public static void main(String[] args) {

		double d = formatDouble2(0.8934);
		System.out.println(d);
		
		String b = String.format("%.2f", d);
		System.out.println(b);
		
		BigDecimal cc = new BigDecimal(12.63).setScale(2, RoundingMode.DOWN);
		
		System.out.println("cc = "+cc.toString());
		
		System.out.println(UUIDUtils.getRandomNumBySub());;
	}
	
	public static void method_add()
	{
		StringBuffer sb=new StringBuffer();
		String s="123456789123456";
		sb.append(s);
		if(s.length()==15)
		{
		sb.insert(6, "19");
		sb.append("X");
			
		}
		sop(sb.toString());
	}
	public static void sop(Object obj)
	{
		System.out.println(obj);
	}
	
	public static double formatDouble2(double d) {
        // 旧方法，已经不再推荐使用
//        BigDecimal bg = new BigDecimal(d).setScale(2, BigDecimal.ROUND_HALF_UP);


        // 新方法，如果不需要四舍五入，可以使用RoundingMode.DOWN
        BigDecimal bg = new BigDecimal(d).setScale(2, RoundingMode.DOWN);

      //  bg.setScale(2,   BigDecimal.ROUND_HALF_UP).doubleValue();
        return bg.doubleValue();
    }
	
	
	
	public static Map<String,Integer> findByRepate(){
		
		List<Integer> list = new ArrayList<Integer>();
		list.add(2);
		list.add(2);
		list.add(6);
		list.add(5);
		list.add(6);
		list.add(8);
		
		Map<String,Integer> map = new HashMap<String, Integer>();
		
		for(int i=0;i<list.size();i++) {
			
		}
		
		return map;
	}
	
	

}
