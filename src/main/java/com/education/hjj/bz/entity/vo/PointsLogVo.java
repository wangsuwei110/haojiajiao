package com.education.hjj.bz.entity.vo;

import java.io.Serializable;
import java.util.Date;

public class PointsLogVo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 146146069230434574L;

	private Integer teacherId;
	
	private Integer getPointsCounts;
	
	private Integer getPointsType;
	
	private String getPointsDesc;
	
	private Date createTime;

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

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getGetPointsType() {
		return getPointsType;
	}

	public void setGetPointsType(Integer getPointsType) {
		this.getPointsType = getPointsType;
	}


}
