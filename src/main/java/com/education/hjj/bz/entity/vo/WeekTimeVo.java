package com.education.hjj.bz.entity.vo;

import java.io.Serializable;

public class WeekTimeVo implements Serializable{

	/**
	 *
	 */
	private static final long serialVersionUID = 5220997101943042864L;

	private Integer week;

	private Integer time;


	public Integer getWeek() {
		return week;
	}

	public void setWeek(Integer week) {
		this.week = week;
	}

	public Integer getTime() {
		return time;
	}

	public void setTime(Integer time) {
		this.time = time;
	}
}
