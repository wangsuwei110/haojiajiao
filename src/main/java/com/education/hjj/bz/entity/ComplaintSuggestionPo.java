package com.education.hjj.bz.entity;

import java.io.Serializable;

public class ComplaintSuggestionPo extends BasePo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8463949658855089611L;

	private Integer complaintSuggestionId;
	
	//投诉或建议人ID
	private Integer personId;
	
	//投诉类型(0投诉，1建议)
	private Integer type;
	
	//投诉或建议内容
	private String content;
	
	//投诉或建议人电话号码
	private String telephone;
	
	//内容回复
	private String reply;
	
	public String getReply() {
		return reply;
	}
	public void setReply(String reply) {
		this.reply = reply;
	}
	public Integer getComplaintSuggestionId() {
		return complaintSuggestionId;
	}
	public void setComplaintSuggestionId(Integer complaintSuggestionId) {
		this.complaintSuggestionId = complaintSuggestionId;
	}
	public Integer getPersonId() {
		return personId;
	}
	public void setPersonId(Integer personId) {
		this.personId = personId;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
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
	
}
