package com.education.hjj.bz.entity.vo;

import java.io.Serializable;



public class ParameterVo extends BaseVo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4296304740013811997L;
	
	private Integer parameterId;
	
	private Integer parentId;
	
	private String name;
	
	private String englishName;

	public Integer getParameterId() {
		return parameterId;
	}

	public void setParameterId(Integer parameterId) {
		this.parameterId = parameterId;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEnglishName() {
		return englishName;
	}

	public void setEnglishName(String englishName) {
		this.englishName = englishName;
	}
	
}
