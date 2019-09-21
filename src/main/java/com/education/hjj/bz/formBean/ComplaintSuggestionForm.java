package com.education.hjj.bz.formBean;

import java.io.Serializable;

public class ComplaintSuggestionForm implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2370496543057316766L;
	// 投诉或建议人ID
	private String personId;
	// 投诉类型(0投诉，1建议)
	private String type;
	// 投诉或建议内容
	private String content;
	// 投诉或建议人电话号码
	private String telephone;
	// 投诉人上传照片地址,多张照片以的主键以“|”拼接
	private String pictures;

	public String getPersonId() {
		return personId;
	}

	public void setPersonId(String personId) {
		this.personId = personId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getPictures() {
		return pictures;
	}

	public void setPictures(String pictures) {
		this.pictures = pictures;
	}

}
