package com.education.hjj.bz.formBean;

import java.io.Serializable;
import java.util.Date;

public class ComplaintSuggestionForm extends PageForm implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2370496543057316766L;
	
	private Integer complaintSuggestionId;
	
	// 投诉或建议人ID
	private String personId;
	
	// 投诉类型(0投诉，1建议)
	private String type;
	
	// 投诉或建议内容
	private String content;
	
	// 投诉或建议人电话号码
	private String telephone;
	
	//内容回复
	private String reply;
	
	//身份
    private String identity;
    
    private Integer isContact;
    
    private Date updateTime;

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

	public Integer getComplaintSuggestionId() {
		return complaintSuggestionId;
	}

	public void setComplaintSuggestionId(Integer complaintSuggestionId) {
		this.complaintSuggestionId = complaintSuggestionId;
	}

	public String getIdentity() {
		return identity;
	}

	public void setIdentity(String identity) {
		this.identity = identity;
	}

	public Integer getIsContact() {
		return isContact;
	}

	public void setIsContact(Integer isContact) {
		this.isContact = isContact;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

}
