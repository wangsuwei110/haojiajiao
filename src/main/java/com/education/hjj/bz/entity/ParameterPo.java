package com.education.hjj.bz.entity;

import java.io.Serializable;

public class ParameterPo extends BasePo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1183286661028762430L;
	
	private Integer parameterId;
	
	private Integer parentId;
	
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public Integer getParameterId() {
		return parameterId;
	}

	public void setParameterId(Integer parameterId) {
		this.parameterId = parameterId;
	}

}
