package com.education.hjj.bz.entity.vo;

import java.io.Serializable;

public class TeachGradeVo implements Serializable{



	/**
	 * 
	 */
	private static final long serialVersionUID = -7626879265103445800L;

	private Integer teachGradeId;
	
	private String teachGradeName;
	
	//是否被选中
	private boolean flag ;

	public Integer getTeachGradeId() {
		return teachGradeId;
	}

	public void setTeachGradeId(Integer teachGradeId) {
		this.teachGradeId = teachGradeId;
	}

	public String getTeachGradeName() {
		return teachGradeName;
	}

	public void setTeachGradeName(String teachGradeName) {
		this.teachGradeName = teachGradeName;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

}
