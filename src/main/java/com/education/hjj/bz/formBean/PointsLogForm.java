package com.education.hjj.bz.formBean;

import java.io.Serializable;

public class PointsLogForm extends PageForm implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -483032926664294757L;

	private Integer teacherId;
	
	private Integer getPointsType;
	
	private String createTime;

	public Integer getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(Integer teacherId) {
		this.teacherId = teacherId;
	}

	public Integer getGetPointsType() {
		return getPointsType;
	}

	public void setGetPointsType(Integer getPointsType) {
		this.getPointsType = getPointsType;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

}
