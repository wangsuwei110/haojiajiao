package com.education.hjj.bz.entity.vo;

import java.io.Serializable;
import java.util.Date;

public class OrderDemandTimeVo implements Serializable{

	/**
	 *
	 */
	private static final long serialVersionUID = 5220997101943042864L;

	// 周几
	private Integer weekDay;

	// 预约的上课时间
	private Date date;

	// 1：上午，2：中午，下午：3
	private Integer time;

	public Integer getWeekDay() {
		return weekDay;
	}

	public void setWeekDay(Integer weekDay) {
		this.weekDay = weekDay;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Integer getTime() {
		return time;
	}

	public void setTime(Integer time) {
		this.time = time;
	}
}
