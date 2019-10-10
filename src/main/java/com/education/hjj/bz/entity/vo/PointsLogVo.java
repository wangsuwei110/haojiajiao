package com.education.hjj.bz.entity.vo;

import java.io.Serializable;
import java.util.Date;

public class PointsLogVo extends BaseVo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 146146069230434574L;

	private Integer pointsId;
	
	private Integer teacherId;
	
	private Integer getPointsCounts;
	
	private String getPointsDesc;
	//剩余积分
	private Integer surplusPoints;
	
	public Integer getPointsId() {
		return pointsId;
	}

	public void setPointsId(Integer pointsId) {
		this.pointsId = pointsId;
	}

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

	public Integer getSurplusPoints() {
		return surplusPoints;
	}

	public void setSurplusPoints(Integer surplusPoints) {
		this.surplusPoints = surplusPoints;
	}
	
}
