package com.education.hjj.bz.entity;

import java.io.Serializable;

public class TeachBranchPo extends BasePo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5370144906788337824L;
	
	private Integer teachBranchId;

	private Integer teachGradeId;
	
	private Integer teachLevelId;

	public Integer getTeachBranchId() {
		return teachBranchId;
	}

	public void setTeachBranchId(Integer teachBranchId) {
		this.teachBranchId = teachBranchId;
	}

	public Integer getTeachGradeId() {
		return teachGradeId;
	}

	public void setTeachGradeId(Integer teachGradeId) {
		this.teachGradeId = teachGradeId;
	}

	public Integer getTeachLevelId() {
		return teachLevelId;
	}

	public void setTeachLevelId(Integer teachLevelId) {
		this.teachLevelId = teachLevelId;
	}
	
}
