package com.education.hjj.bz.entity.vo;

import java.io.Serializable;

public class TeachBranchVo implements Serializable{




	/**
	 * 
	 */
	private static final long serialVersionUID = -5307306475368053576L;

	private Integer teachBranchId;
	private Integer teachGradeId;
	private Integer teachLevelId;

	private String teachBranchName;
	
	private boolean flag;
	
	private String branchType;

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

	public Integer getTeachBranchId() {
		return teachBranchId;
	}

	public void setTeachBranchId(Integer teachBranchId) {
		this.teachBranchId = teachBranchId;
	}

	public String getTeachBranchName() {
		return teachBranchName;
	}

	public void setTeachBranchName(String teachBranchName) {
		this.teachBranchName = teachBranchName;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public String getBranchType() {
		return branchType;
	}

	public void setBranchType(String branchType) {
		this.branchType = branchType;
	}

}
