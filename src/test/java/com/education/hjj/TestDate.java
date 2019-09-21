package com.education.hjj;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.time.DurationFormatUtils;

public class TestDate {
	
	public static void main(String[] args) {
		
		String a="2018-03-23";
		String b="2019-08-09";
		
		String cc=remainDateToString(a,b);
		System.out.println(cc);
	}
	
	private static Calendar calS = Calendar.getInstance();
    private static Pattern p = Pattern.compile("\\d{4}-\\d{2}-\\d{2}");//定义整则表达式
 
 
    /**
     * 计算剩余时间
     *
     * @param startDateStr yyyy-MM-dd
     * @param endDateStr yyyy-MM-dd
     * @return      ？年？个月？天
     */
    public static String remainDateToString(String startDateStr, String endDateStr) {
        java.util.Date startDate = null;
        java.util.Date endDate = null;
        try {
            startDate = new SimpleDateFormat("yyyy-MM-dd").parse(startDateStr);
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
        try {
            endDate = new SimpleDateFormat("yyyy-MM-dd").parse(endDateStr);
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
        calS.setTime(startDate);
        int startY = calS.get(Calendar.YEAR);
        int startM = calS.get(Calendar.MONTH);
        int startD = calS.get(Calendar.DATE);
        int startDayOfMonth = calS.getActualMaximum(Calendar.DAY_OF_MONTH);
 
        calS.setTime(endDate);
        int endY = calS.get(Calendar.YEAR);
        int endM = calS.get(Calendar.MONTH);
        //处理2011-01-10到2011-01-10，认为服务为一天
        int endD = calS.get(Calendar.DATE) + 1;
        int endDayOfMonth = calS.getActualMaximum(Calendar.DAY_OF_MONTH);
 
        StringBuilder sBuilder = new StringBuilder();
        if (endDate.compareTo(startDate) < 0) {
            return sBuilder.append("过期").toString();
        }
        int lday = endD - startD;
        if (lday < 0) {
            endM = endM - 1;
            lday = startDayOfMonth + lday;
        }
        //处理天数问题，如：2011-01-01 到 2013-12-31  2年11个月31天     实际上就是3年
        if (lday == endDayOfMonth) {
            endM = endM + 1;
            lday = 0;
        }
        int mos = (endY - startY) * 12 + (endM - startM);
        int lyear = mos / 12;
        int lmonth = mos % 12;
        if (lyear > 0) {
            sBuilder.append(lyear + "年");
        }
        if (lmonth > 0) {
            sBuilder.append(lmonth + "个月");
        }
       // if(lyear==0)//满足项目需求 满一年不显示天数
        if (lday > 0) {
            sBuilder.append(lday-1 + "天");
        }
        return sBuilder.toString();
    }
 
    /*
     * 转换 dataAndTime 2013-12-31 23:59:59 到
     * date 2013-12-31
     */
    public static String getDate(String dateAndTime) {
        if (dateAndTime != null && !"".equals(dateAndTime.trim())) {
            Matcher m = p.matcher(dateAndTime);
            if (m.find()) {
                return dateAndTime.subSequence(m.start(), m.end()).toString();
            }
        }
        return "data error";
    }

}
