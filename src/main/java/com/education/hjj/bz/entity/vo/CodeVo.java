package com.education.hjj.bz.entity.vo;

import java.io.Serializable;

public class CodeVo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5220997101943042864L;

	private Integer key;
	
	private String value;

	public Integer getKey() {
		return key;
	}

	public void setKey(Integer key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}


	public CodeVo(Integer key, String val) {
		this.key = key;
		this.value = val;
	}
}
