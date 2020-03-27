package com.education.hjj.bz.formBean;

import java.io.Serializable;

public class TeacherAccountLogForm extends PageForm implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3717730629773809028L;

	//教员id
	private Integer teacherId;
		
	private String paymentType;
	
	private Integer paymentId;
	
	private Integer paymentPersonId;

	public Integer getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(Integer teacherId) {
		this.teacherId = teacherId;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public Integer getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(Integer paymentId) {
		this.paymentId = paymentId;
	}

	public Integer getPaymentPersonId() {
		return paymentPersonId;
	}

	public void setPaymentPersonId(Integer paymentPersonId) {
		this.paymentPersonId = paymentPersonId;
	}
	
}
