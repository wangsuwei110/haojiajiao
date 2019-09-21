package com.education.hjj.bz.formBean;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;

@ApiModel("参数查询传递信息")
public class ParameterForm implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -836039994861871588L;
	
	private String parentId;
	
	private String teacherId;

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(String teacherId) {
		this.teacherId = teacherId;
	}

	
}
