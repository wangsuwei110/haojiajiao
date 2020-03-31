package com.education.hjj.bz.entity.vo;

import java.io.Serializable;

public class ComplaintSuggestionVo extends BaseVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8080482242846334998L;

	private Integer complaintSuggestionId;

	// 投诉或建议人ID
	private String personId;

	// 投诉或建议人员类型（1：教员，2：学员）
	private Integer personType;

	// 投诉类型(0投诉，1建议)
	private String type;

	// 投诉或建议内容
	private String content;

	// 投诉或建议人电话号码
	private String telephone;

	// 内容回复
	private String reply;
	
	//投诉人员姓名
  	private String name;

	public Integer getComplaintSuggestionId() {
		return complaintSuggestionId;
	}

	public void setComplaintSuggestionId(Integer complaintSuggestionId) {
		this.complaintSuggestionId = complaintSuggestionId;
	}

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

	public String getReply() {
		return reply;
	}

	public void setReply(String reply) {
		this.reply = reply;
	}

	public Integer getPersonType() {
		return personType;
	}

	public void setPersonType(Integer personType) {
		this.personType = personType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
