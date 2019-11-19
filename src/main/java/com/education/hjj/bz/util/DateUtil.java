package com.education.hjj.bz.util;

import com.education.hjj.bz.formBean.TimeRangeForm;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

/**
 * 
 */
public final class DateUtil {
	/**
	 * 英文简写（默认）如：2010-12-01
	 */
	public static final String FORMAT_SHORT = "yyyy-MM-dd";
	
	/**
	 * 英文简写（默认）如：2010-12-01
	 */
	public static final String FORMAT_SHORT_NUM = "yyyyMMdd";

	/**
	 * 英文简写（默认）如：2010-12
	 */
	public static final String FORMAT_SHORT_01 = "yyyy-MM";
	/**
	 * 英文全称 如：2010-12-01 23:15:06
	 */
	public static final String FORMAT_LONG = "yyyy-MM-dd HH:mm:ss";
	/**
	 * 精确到毫秒的完整时间 如：yyyy-MM-dd HH:mm:ss.S
	 */
	public static final String FORMAT_FULL = "yyyy-MM-dd HH:mm:ss.S";
	/**
	 * 中文简写 如：2010年12月01日
	 */
	public static final String FORMAT_SHORT_CN = "yyyy年MM月dd";
	/**
	 * 中文全称 如：2010年12月01日 23时15分06秒
	 */
	public static final String FORMAT_LONG_CN = "yyyy年MM月dd日  HH时mm分ss秒";
	/**
	 * 精确到毫秒的完整中文时间
	 */
	public static final String FORMAT_FULL_CN = "yyyy年MM月dd日  HH时mm分ss秒SSS毫秒";
	/**
	 * 一天的开始
	 */
	public static final String DAY_START = " 00:00:00";

	/**
	 * 小时的开始
	 */
	public static final String HOUR_START = ":00:00";

	/**
	 * 小时的结束
	 */
	public static final String HOUR_END = ":59:59";

	/**
	 * 分钟的开始
	 */
	public static final String MINUTE_START = ":00";

	/**
	 * 分钟的结束
	 */
	public static final String MINUTE_END = ":59";

	/**
	 * 一月的开始
	 */
	public static final String MON_START = "-01 00:00:00";
	/**
	 * 一天的结束
	 */
	public static final String DAY_END = " 23:59:59";

	/**
	 * 日期作为命名用，如：2019-02-27 10:26:30 转成： 20190227102630
	 */
	public static final String DAY_YYYYMMDDHHMMSS = "yyyyMMddHHmmss";

	/**
	 * 英文全称 如：2010-12-01 23
	 */
	public static final String FORMAT_YYYY_MM_DD_HH = "yyyy-MM-dd HH";

	/**
	 * 英文全称 如：2010-12-01 23:20
	 */
	public static final String FORMAT_YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";

	public static final List<String> patterns;

	private static Calendar calS = Calendar.getInstance();

	private static Pattern p = Pattern.compile("\\d{4}-\\d{2}-\\d{2}");// 定义整则表达式

	static {
		patterns = new ArrayList<String>() {
			{
				add(FORMAT_FULL);
				add(FORMAT_LONG);
				add("yyyy-MM-dd HH:mm");
				add("yyyy-MM-dd HH");
				add(FORMAT_SHORT);
			}
		};
	}

	/**
	 * 获得默认的 date pattern
	 */
	public static String getDatePattern() {
		return FORMAT_LONG;
	}

	/**
	 * 根据预设格式返回当前日期
	 *
	 * @return
	 */
	public static String getNow() {
		return format(new Date());
	}

	/**
	 * 获取当天开始时间
	 *
	 * @return
	 */
	public static Date getDayStart(Date date) {
		return parse(format(date, FORMAT_SHORT) + DAY_START);
	}

	/**
	 * 获取时间的小时
	 *
	 * @return
	 */
	public static String getDayHour(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("HH");
		return format.format(date);
	}

	/**
	 * 获取时间yyyy-MM-dd
	 *
	 * @return
	 */
	public static String getStandardDay(Date date) {
		SimpleDateFormat df = new SimpleDateFormat(FORMAT_SHORT);
		return df.format(date);
	}
	
	/**
	 * 获取时间yyyyMMdd
	 *
	 * @return
	 */
	public static String getStandardDayByNum(Date date) {
		SimpleDateFormat df = new SimpleDateFormat(FORMAT_SHORT_NUM);
		return df.format(date);
	}

	/**
	 * 获取当天结束时间
	 *
	 * @return
	 */
	public static Date getDayEnd(Date date) {
		return parse(format(date, FORMAT_SHORT) + DAY_END);
	}

	/**
	 * 获取今天开始时间
	 *
	 * @return
	 */
	public static Date getTodayStart() {
		return parse(getNow(DateUtil.FORMAT_SHORT) + DAY_START);
	}

	/**
	 * 获取本月开始时间
	 *
	 * @return
	 */
	public static Date getMonStart() {
		return parse(getNow(DateUtil.FORMAT_SHORT_01) + MON_START);
	}

	/**
	 * 获取今天结束时间
	 *
	 * @return
	 */
	public static Date getTodayEnd() {
		return parse(getNow(DateUtil.FORMAT_SHORT) + DAY_END);
	}

	/**
	 * 根据用户格式返回当前日期
	 *
	 * @param format
	 * @return
	 */
	public static String getNow(String format) {
		return format(new Date(), format);
	}

	/**
	 * 使用预设格式格式化日期
	 *
	 * @param date
	 * @return
	 */
	public static String format(Date date) {
		return format(date, getDatePattern());
	}

	/**
	 * 使用用户格式格式化日期
	 *
	 * @param date    日期
	 * @param pattern 日期格式
	 * @return
	 */
	public static String format(Date date, String pattern) {
		String returnValue = "";
		if (date != null) {
			SimpleDateFormat df = new SimpleDateFormat(pattern);
			returnValue = df.format(date);
		}
		return returnValue;
	}

	/**
	 * 使用预设格式提取字符串日期
	 *
	 * @param strDate 日期字符串
	 * @return
	 */
	public static Date parse(String strDate) {
		return parse(strDate, getDatePattern());
	}

	/**
	 * 使用用户格式提取字符串日期
	 *
	 * @param strDate 日期字符串
	 * @param pattern 日期格式
	 * @return
	 */
	public static Date parse(String strDate, String pattern) {
		SimpleDateFormat df = new SimpleDateFormat(pattern);
		try {
			return df.parse(strDate);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 转换合适的格式的日期
	 *
	 * @param text
	 * @return
	 */
	public static Date tryConvert(String text) {
		if (text == null) {
			return null;
		}
		for (String pattern : patterns) {
			Date date = tryConvert(text, pattern);
			if (date != null) {
				return date;
			}
		}

		return null;
	}

	/**
	 * @param text
	 * @param pattern
	 * @return
	 */
	public static Date tryConvert(String text, String pattern) {
		DateFormat dateFormat = new SimpleDateFormat(pattern);
		dateFormat.setLenient(false);
		try {
			return dateFormat.parse(text);
		} catch (ParseException ex) {
		}

		return null;
	}

	/**
	 * 在日期上增加数个整年
	 *
	 * @param date 日期
	 * @param n    要增加的月数
	 * @return
	 */
	public static Date addYear(Date date, int n) {
		return dateAdd(date, Calendar.YEAR, n);
	}

	/**
	 * 在日期上增加数个整月
	 *
	 * @param date 日期
	 * @param n    要增加的月数
	 * @return
	 */
	public static Date addMonth(Date date, int n) {
		return dateAdd(date, Calendar.MONTH, n);
	}

	/**
	 * 在日期上增加天数
	 *
	 * @param date 日期
	 * @param n    要增加的天数
	 * @return
	 */
	public static Date addDay(Date date, int n) {
		return dateAdd(date, Calendar.DATE, n);
	}

	/**
	 * 在日期上增加小时
	 *
	 * @param n
	 * @return
	 */
	public static Date addHour(Date date, int n) {
		return dateAdd(date, Calendar.HOUR, n);
	}

	/**
	 * 在日期上增加分钟
	 *
	 * @param n
	 * @return
	 */
	public static Date addMinute(Date date, int n) {
		return dateAdd(date, Calendar.MINUTE, n);
	}

	/**
	 * 在日期上增加秒
	 *
	 * @param n
	 * @return
	 */
	public static Date addSecond(Date date, int n) {
		return dateAdd(date, Calendar.SECOND, n);
	}

	/**
	 * @param date
	 * @param t
	 * @param n
	 * @return
	 */
	public static Date dateAdd(Date date, int t, int n) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(t, n);
		return cal.getTime();
	}

	/**
	 * 计算两个日期相差秒数的绝对值
	 *
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static long absDiffSec(Date date1, Date date2) {
		return Math.abs(dateDiff(date1, date2) / 1000);
	}

	/**
	 * 计算两个日期相差秒数
	 *
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static long dateDiff(Date date1, Date date2) {
		Assert.notNull(date1, "date1 must not be null");
		Assert.notNull(date2, "date2 must not be null");
		SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_LONG);
		try {
			date1 = sdf.parse(sdf.format(date1));
			date2 = sdf.parse(sdf.format(date2));
			return (date1.getTime() - date2.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return 0;
	}

	/**
	 * 计算两个日期相差秒数的绝对值
	 *
	 * @param date1Str
	 * @param date2Str
	 * @return
	 */
	public static long absDiffSec(String date1Str, String date2Str) {
		return Math.abs(dateDiff(date1Str, date2Str) / 1000);
	}

	/**
	 * 计算两个日期相差秒数
	 *
	 * @param date1Str
	 * @param date2Str
	 * @return
	 */
	public static long dateDiff(String date1Str, String date2Str) {
		Assert.notNull(date1Str, "date1Str must not be null");
		Assert.notNull(date2Str, "date2Str must not be null");
		Date date1 = tryConvert(date1Str);
		Date date2 = tryConvert(date2Str);
		return (date1.getTime() - date2.getTime());
	}

	public static <T> String showDateDiff(T date1, T date2) {
		Assert.notNull(date1, "date1 must not be null");
		Assert.notNull(date2, "date2 must not be null");
		long differ;
		if (date1 instanceof String) {
			differ = absDiffSec((String) date1, (String) date2);
		} else if (date1 instanceof Date) {
			differ = absDiffSec((Date) date1, (Date) date2);
		} else {
			throw new IllegalStateException("param not is a String or Date");
		}

		return showDateDiff(differ);
	}

	public static String showDateDiff(long differ) {
		StringBuffer differStr = new StringBuffer();
		if (differ < 60) {
			differStr.append(differ).append("秒");
		} else if (differ < 3600) {
			differStr.append(differ / 60).append("分").append(differ % 60).append("秒");
		} else if (differ < 86400) {
			differStr.append(differ / 3600).append("小时").append(differ % 3600 / 60).append("分")
					.append(differ % 3600 % 60).append("秒");
		} else {
			differStr.append(differ / 86400).append("天").append(differ % 86400 / 3600).append("小时")
					.append(differ % 86400 % 3600 / 60).append("分").append(differ % 86400 % 3600 % 60).append("秒");
		}

		return differStr.toString();
	}

	public static String showDateToM(long differ) {
		StringBuffer differStr = new StringBuffer();
		if (differ < 60) {
			differStr.append("1分");
		} else if (differ < 3600) {
			differStr.append(differ / 60).append("分");
		} else if (differ < 86400) {
			differStr.append(differ / 3600).append("小时").append(differ % 3600 / 60).append("分");
		} else {
			differStr.append(differ / 86400).append("天").append(differ % 86400 / 3600).append("小时")
					.append(differ % 86400 % 3600 / 60).append("分");
		}

		return differStr.toString();
	}

	/**
	 * 获取时间戳
	 */
	public static String getTimeString() {
		SimpleDateFormat df = new SimpleDateFormat(FORMAT_FULL);
		Calendar calendar = Calendar.getInstance();
		return df.format(calendar.getTime());
	}

	/**
	 * 获取日期年份
	 *
	 * @param date 日期
	 * @return
	 */
	public static String getYear(Date date) {
		return format(date).substring(0, 4);
	}

	/**
	 * 按默认格式的字符串距离今天的天数
	 *
	 * @param date 日期字符串
	 * @return
	 */
	public static int countDays(String date) {
		long t = Calendar.getInstance().getTime().getTime();
		Calendar c = Calendar.getInstance();
		c.setTime(parse(date));
		long t1 = c.getTime().getTime();
		return (int) (t / 1000 - t1 / 1000) / 3600 / 24;
	}

	/**
	 * 按用户格式字符串距离今天的天数
	 *
	 * @param date   日期字符串
	 * @param format 日期格式
	 * @return
	 */
	public static int countDays(String date, String format) {
		long t = Calendar.getInstance().getTime().getTime();
		Calendar c = Calendar.getInstance();
		c.setTime(parse(date, format));
		long t1 = c.getTime().getTime();
		return (int) (t / 1000 - t1 / 1000) / 3600 / 24;
	}

	public final static String SHORT_DATE_FORMAT = "yyyy-MM-dd";

	/**
	 * CD-008 获得两个日期之间的天数日期格式为yyyy-MM-dd
	 *
	 * @param startTime 起始日期
	 * @param endTime   结束日期
	 * @return int
	 */
	public static int getDateFromTime1ToTime2(String startTime, String endTime) {
		SimpleDateFormat formatter = new SimpleDateFormat(SHORT_DATE_FORMAT);
		Date dt1 = null;
		Date dt2 = null;
		int checkDays = 0;
		try {
			dt1 = formatter.parse(endTime);
			dt2 = formatter.parse(startTime);
			long l = dt1.getTime() - dt2.getTime();
			l = (long) (l / (1000 * 60 * 60 * 24) + 0.5);
			checkDays = Integer.parseInt(String.valueOf(l));
		} catch (ParseException e) {
			return checkDays;
		}
		return checkDays;
	}

	/**
	 * CD-008 获得两个日期之间的天数日期格式为时间戳
	 *
	 * @param startTime 起始日期
	 * @param endTime   结束日期
	 * @return int
	 */
	public static int getNumFromTime1ToTime2(Long startTime, Long endTime) {
		int checkDays = 0;
		long l = endTime - startTime;
		l = (long) (l / (1000 * 60 * 60 * 24) + 0.5);
		checkDays = Integer.parseInt(String.valueOf(l));

		return checkDays;
	}

	/**
	 * 比较日期
	 *
	 * @param startDate 日期1
	 * @param endDate   日期2
	 * @return 日期2 >= 日期1 返回 true，其他false
	 */
	public static boolean compareTwoDate(String startDate, String endDate) {
		try {
			SimpleDateFormat formatter = new SimpleDateFormat(SHORT_DATE_FORMAT);
			Date date1 = formatter.parse(startDate);
			Date date2 = formatter.parse(endDate);
			if (date2.after(date1) || date2.equals(date1)) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	/**
	 * 和当前日期比较
	 *
	 * @param date
	 * @return 小于当前日期时 返回true
	 */
	public static boolean compareWithToday(String date) {
		try {
			SimpleDateFormat formatter = new SimpleDateFormat(SHORT_DATE_FORMAT);
			Date date1 = formatter.parse(date);
			Calendar calendar = Calendar.getInstance();
			Date date2 = calendar.getTime();
			date2 = formatter.parse(formatter.format(date2));

			if (date2.after(date1)) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	/**
	 * 获取备份日期
	 *
	 * @return
	 */
	public static String getBackupDate(int num) {
		if (num < 1) {
			num = 1;
		}
		String result = "";
		try {
			SimpleDateFormat formatter = new SimpleDateFormat(SHORT_DATE_FORMAT);
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.DAY_OF_MONTH, -1 * num);
			result = formatter.format(calendar.getTime());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	private static final SimpleDateFormat CST = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.UK);

	/**
	 * 参考文档https://docs.oracle.com/javase/6/docs/api/java/text/SimpleDateFormat.html
	 * 将时间字符串"Wed Sep 12 22:09:54 CST 2018"转换成Date 此函数目的是为了后面从CSV文件读取GPS时，处理时间的转换
	 *
	 * @param date "Wed Sep 12 22:09:54 CST 2018"
	 * @return
	 */
	public static Date cstTransferToDte(String date) {
		Date result = null;
		try {
			result = CST.parse(date);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	/**
	 * 设定过期时间为凌晨失效
	 *
	 * @param ext  过期时长
	 * @param unit 时间单位 0-day，1-hour，2-minute
	 * @return
	 */
	public static long getExtTime(int ext, int unit) {
		if (ext < 0) {
			ext = 7;
		}

		if (unit < 0) {
			unit = 0;
		}

		Calendar calendar = Calendar.getInstance();
		if (unit == 0) { // 天
			calendar.add(Calendar.DAY_OF_MONTH, ext);
			calendar.set(Calendar.HOUR_OF_DAY, 23);
			calendar.set(Calendar.MINUTE, 59);
			calendar.set(Calendar.SECOND, 59);
		}

		if (unit == 1) { // 小时
			calendar.add(Calendar.HOUR_OF_DAY, ext);
		}

		if (unit == 2) { // 分钟
			calendar.add(Calendar.MINUTE, ext);
		}

		if (unit == 3) { // 秒
			calendar.add(Calendar.SECOND, ext);
		}

		return calendar.getTime().getTime();
	}

	/**
	 * 获取时间的字符串，纯数字格式，如：20190227
	 **/
	public static String getDateString(Date date) {
		SimpleDateFormat format = new SimpleDateFormat(DAY_YYYYMMDDHHMMSS);
		return format.format(date);
	}

	/**
	 * 取日期的年月日 yyyy-mm-dd
	 *
	 */
	public static String getYearMonthDay(String date) {
		return date.substring(0, 10);
	}

	/**
	 * 获取两个时间差的秒数
	 */
	public static Long getSeconds(String date1, String date2) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return (sdf.parse(date1).getTime() - sdf.parse(date2).getTime()) / 1000;
	}

	/**
	 * 获取日期的小时整起始时间
	 */
	public static Date getDateForHourStart(Date date) {
		return parse(format(date, FORMAT_YYYY_MM_DD_HH) + HOUR_START);
	}

	/**
	 * 获取日期的小时整终了时间
	 */
	public static Date getDateForHourEnd(Date date) {
		return parse(format(date, FORMAT_YYYY_MM_DD_HH) + HOUR_END);
	}

	/**
	 * 获取日期的分钟整起始时间
	 */
	public static Date getDateForMinuteStart(Date date) {
		return parse(format(date, FORMAT_YYYY_MM_DD_HH_MM) + MINUTE_START);
	}

	/**
	 * 获取日期的分钟整结束时间
	 */
	public static Date getDateForMinuteEnd(Date date) {
		return parse(format(date, FORMAT_YYYY_MM_DD_HH_MM) + MINUTE_END);
	}

	public static Date getDateFromStr() {
		try {

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取日期（参数是字符串的LONG型数据）
	 */
	public static Date getDateByLong(String date) {
		if (StringUtils.isEmpty(date)) {
			return null;
		}
		return new Date(Long.valueOf(date));
	}

	/**
	 * 传值为字符串，计算当前一个小时之前的时间
	 *
	 * @param endTime
	 * @return
	 * @throws Exception
	 */
	public static String getBeforeByHourtime(String endTime) {
		SimpleDateFormat df = new SimpleDateFormat(FORMAT_LONG);
		Calendar calendar = Calendar.getInstance();
		Date date;
		try {
			date = df.parse(endTime);
			calendar.setTime(date);
			calendar.add(Calendar.HOUR, -1);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return df.format(calendar.getTime());
	}

	/**
	 * 传值为时间Date，计算当前一个小时之前的时间
	 *
	 * @param endTime
	 * @return
	 * @throws Exception
	 */
	public static Date getBeforeByHourtimes(Date endTime) {
		SimpleDateFormat df = new SimpleDateFormat(FORMAT_LONG);
		Calendar calendar = Calendar.getInstance();
		Date date = null;
		try {
			calendar.setTime(endTime);
			calendar.add(Calendar.HOUR, -1);
			String startTime = df.format(calendar.getTime());
			date = df.parse(startTime);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * 在日期上减少n天,获取n天前的开始和结束时间
	 *
	 * @param date 日期
	 * @param n    要增加的月数
	 * @return
	 */
	public static Map<String, Date> subtractDay(Date date, int n) {
		Calendar calendar = Calendar.getInstance(); // 得到日历
		calendar.setTime(date);// 把当前时间赋给日历
		calendar.add(Calendar.DAY_OF_MONTH, n); // 设置为前一天
		SimpleDateFormat df = new SimpleDateFormat(FORMAT_LONG);
		Map<String, Date> map = new HashMap<String, Date>(2);
		try {
			Date startDatetime = df.parse(df.format(calendar.getTime()).substring(0, 10) + " 00:00:00");
			Date endDatetime = df.parse(df.format(calendar.getTime()).substring(0, 10) + " 23:59:59");

			map.put("startDatetime", startDatetime);
			map.put("endDatetime", endDatetime);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return map;
	}

	/**
	 * 将传进来的String参数转换成日期类型返回
	 *
	 * @param date
	 * @return
	 */
	public static Date covertFromStringToDate(String date) {
		SimpleDateFormat df = new SimpleDateFormat(FORMAT_LONG);
		Date dateTime = null;
		if (date != null && !"".equals(date)) {
			try {
				dateTime = df.parse(date);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return dateTime;
	}

	/**
	 * 将传进来的Date参数转换成String日期类型返回
	 *
	 * @param date
	 * @return
	 */
	public static String covertFromDateToString(Date date) {
		SimpleDateFormat df = new SimpleDateFormat(FORMAT_LONG);
		String newDate = "";
		if (date != null && !"".equals(date)) {
			try {
				newDate = df.format(date);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return newDate;
	}

	/**
	 * 计算剩余时间
	 *
	 * @param startDateStr yyyy-MM-dd
	 * @param endDateStr   yyyy-MM-dd
	 * @return ？年？个月？天
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
		// 处理2011-01-10到2011-01-10，认为服务为一天
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
		// 处理天数问题，如：2011-01-01 到 2013-12-31 2年11个月31天 实际上就是3年
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
			sBuilder.append(lday - 1 + "天");
		}
		return sBuilder.toString();
	}

	/**
	 * 根据入学时间计算现在的年级
	 *
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static String caculDegree(String date) throws ParseException {

		String beginDate = date + "-" + "09" + "-" + "01";

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

		long now = format.parse(format.format(new Date())).getTime();

		long begin = format.parse(beginDate).getTime();

		String grade = null;

		int cal = (int) ((now - begin) / (24 * 60 * 60 * 1000));

		if (cal > 365 && cal < 730) {
			grade = "大二在校";
		} else if (cal >= 730 && cal < 1095) {
			grade = "大三在校";
		} else if (cal > 1095 && cal < 1460) {
			grade = "大四在校";
		} else {
			grade = "已毕业";
		}

		return grade;
	}

	/**
	 * 计算两个时间之间是多少年多少月多少天
	 *
	 * @param beginTime
	 * @return
	 * @throws ParseException
	 */
	public static String calStayTimeByString(String beginTime) throws ParseException {

		SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_LONG);

		Date begin = sdf.parse(beginTime);
		Date end = new Date();
		long between = (end.getTime() - begin.getTime()) / 1000;// 除以1000是为了转换成秒

		long year = between / (12 * 30 * 24 * 3600);
		long month = between / (30 * 24 * 3600);
		long day = between / (24 * 3600 * 30);

		StringBuffer ab = new StringBuffer();

		if (year > 0) {
			ab.append(year + " 年 ");
		}

		if (month > 0) {
			ab.append(month + " 个月 ");
		}

		if (day > 0) {
			ab.append(day + " 天");
		} else {
			ab.append("0 天");
		}

		return ab.toString();
	}

	/**
	 * 计算两个时间之间是多少年多少月多少天
	 *
	 * @param  beginTime
	 * @return
	 * @throws ParseException
	 */
	public static String calStayTimeByDate(Date beginTime) throws ParseException {

		Date begin = beginTime;
		Date end = new Date();
		long between = (end.getTime() - begin.getTime()) / 1000;// 除以1000是为了转换成秒

		long year = between / (12 * 30 * 24 * 3600);
		long month = between / (30 * 24 * 3600);
		long day = between / (24 * 3600 * 30);

		StringBuffer ab = new StringBuffer();

		if (year > 0) {
			ab.append(year + " 年 ");
		}

		if (month > 0) {
			ab.append(month + " 个月 ");
		}

		if (day > 0) {
			ab.append(day + " 天");
		} else {
			ab.append("0 天");
		}

		return ab.toString();
	}

	// 获得指定日期所在的周日 的日期
	public static String getSunday(String d) throws ParseException {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Calendar cal = Calendar.getInstance();

		Date time = sdf.parse(d);

		cal.setTime(time);

		int dayWeek = cal.get(Calendar.DAY_OF_WEEK);

		if (1 == dayWeek) {
			return d;
		}

		cal.add(Calendar.DATE, 8 - dayWeek);

		return sdf.format(cal.getTime());
	}

	// 获得指定日期所在的周一的日期
	public static String getMonday(String d) throws ParseException {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Calendar cal = Calendar.getInstance();

		Date time = sdf.parse(d);

		cal.setTime(time);

		int dayWeek = cal.get(Calendar.DAY_OF_WEEK);

		if (1 == dayWeek) {
			dayWeek = 8;
		}

		cal.add(Calendar.DATE, 2 - dayWeek);

		return sdf.format(cal.getTime());
	}

	/**
	 * 指定日期后几天
	 *
	 * @param startDay 指定日期
	 * @param count    天数
	 * @return
	 */
	public static String getAfterDay(String startDay, int count) {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		try {
			Date date = sdf.parse(startDay);

			Calendar cl = Calendar.getInstance();

			cl.setTime(date);

			cl.add(Calendar.DATE, count);

			return sdf.format(cl.getTime());

		} catch (ParseException e) {
			e.printStackTrace();
		}

		return "";
	}
    //获取当前天周几(******返回的是根据周日为开始计算日***)
    public static int getWeekOfDate(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int weekDate = c.get(Calendar.DAY_OF_WEEK) - 1;
        if (weekDate == 0) {
            return 7;
        }
        return weekDate;
    }

    //获取本周的结束时间
    public static Date getEndDayOfWeek(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(getBeginDayOfWeek(date));
        cal.add(Calendar.DAY_OF_WEEK, 6);
        Date weekEndSta = cal.getTime();
        return getDayStart(weekEndSta);
    }

    //获取本周的开始时间
    public static Date getBeginDayOfWeek(Date date) {
        if (date == null) {
            return null;
        }

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int dayofweek = cal.get(Calendar.DAY_OF_WEEK);
        if (dayofweek == 1) {
            dayofweek += 7;
        }

        cal.add(Calendar.DATE, 2 - dayofweek);
        return getDayStart(cal.getTime());
    }
    
    /**
     * 日期转星期
     * 
     * @param datetime
     * @return
     */
    public static String dateToWeek(String datetime) {
    	
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        
        String[] weekDays = { "7", "1", "2", "3", "4", "5", "6" };
        
        Calendar cal = Calendar.getInstance(); // 获得一个日历
        
        Date datet = null;
        
        try {
        	
            datet = f.parse(datetime);
            
            cal.setTime(datet);
            
        } catch (ParseException e) {
            e.printStackTrace();
        }
        
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1; // 指示一个星期中的某天。
        
        if (w < 0)
            w = 0;
        
        return weekDays[w];
    }

	/**
	 * 当前日期取当前一周范围
	 *
	 * @param date
	 * @return
	 */
	public static TimeRangeForm getWeekTime(Date date) {
		Integer currentWeekDay = getWeekOfDate(date);
		TimeRangeForm rangeForm = new TimeRangeForm();
		rangeForm.setStartTime(DateUtil.getDayStart(DateUtil.addDay(date, -currentWeekDay + 1)));
		rangeForm.setEndTime(DateUtil.getDayEnd(DateUtil.addDay(date, 7-currentWeekDay)));

		return rangeForm;
	}
}

