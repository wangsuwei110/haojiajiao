package com.education.hjj.bz.formBean;

import java.io.Serializable;

public class TeacherAccountLogForm implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3717730629773809028L;

	//教员id
	private Integer teacherId;
		
	private Integer paymentType;
	
	private Integer paymentId;

	public Integer getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(Integer teacherId) {
		this.teacherId = teacherId;
	}

	public Integer getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(Integer paymentType) {
		this.paymentType = paymentType;
	}

	public Integer getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(Integer paymentId) {
		this.paymentId = paymentId;
	}
	
}
