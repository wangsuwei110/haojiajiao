package com.education.hjj.bz.entity;

import java.io.Serializable;

public class PointsLogPo extends BasePo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3214422893122973639L;

	private Integer teacherId;
	
	private Integer getPointsCounts;
	
	private String getPointsDesc;
	

	public Integer getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(Integer teacherId) {
		this.teacherId = teacherId;
	}

	public Integer getGetPointsCounts() {
		return getPointsCounts;
	}

	public void setGetPointsCounts(Integer getPointsCounts) {
		this.getPointsCounts = getPointsCounts;
	}

	public String getGetPointsDesc() {
		return getPointsDesc;
	}

	public void setGetPointsDesc(String getPointsDesc) {
		this.getPointsDesc = getPointsDesc;
	}

}
