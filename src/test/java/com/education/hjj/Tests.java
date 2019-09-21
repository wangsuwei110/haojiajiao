package com.education.hjj;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Tests {

	public static void main(String[] args) throws Exception {
//		 // TODO 自动生成方法存根 
//		  //日期相减算出秒的算法 
//		  Date date1 = new SimpleDateFormat("yyyy-mm-dd").parse("2018-09-01"); 
//		  Date date2 = new SimpleDateFormat("yyyy-mm-dd").parse("2019-06-01"); 
//		  
//		  long l = date1.getTime()-date2.getTime()>0 ? date1.getTime()-date2.getTime(): 
//		   date2.getTime()-date1.getTime(); 
//		  
//		  System.out.println(l/1000+"秒"); 
//		  
//		  //日期相减得到相差的日期 
//		  long day = (date1.getTime()-date2.getTime())/(24*60*60*1000)>0 ? (date1.getTime()-date2.getTime())/(24*60*60*1000): 
//		   (date2.getTime()-date1.getTime())/(24*60*60*1000); 
//		  
//		  System.out.println("相差的日期: " +day); 
		  
		  
		  String aa="2018-09-01";
		  
		  String bb="2019-09-20";
		  
		  
		  SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		  
		  long b = format.parse(bb).getTime();
		  long a = format.parse(aa).getTime();
		  
		  long c = (b-a)/(24 * 60 * 60 * 1000);
		  
		  System.out.println(c);
		  
		  
		  String abc = "/haojiajiao/share/IMG/20190916233129-70f02952818c49c5adb5257bc5e8534d.jpg,/haojiajiao/share/IMG/20190916233134-09ed352f740547eda5dc053299cd2cb5.jpg";
		  
		  System.out.println(abc.replaceAll("/haojiajiao/share/IMG/", ""));
	} 
}
