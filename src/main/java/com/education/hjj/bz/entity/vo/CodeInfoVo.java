package com.education.hjj.bz.entity.vo;

import java.io.Serializable;

public class CodeInfoVo implements Serializable{

	/**
	 *
	 */
	private static final long serialVersionUID = 5220997101943042864L;

	private String key;

	private String value;

	private String pictureName;

	public String getPictureName() {
		return pictureName;
	}

	public void setPictureName(String pictureName) {
		this.pictureName = pictureName;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public CodeInfoVo() {

	}

	public CodeInfoVo(String key, String val) {
		this.key = key;
		this.value = val;
	}
}
