package com.education.hjj.bz.entity.vo;

import java.io.Serializable;
import java.math.BigDecimal;

public class TeacherAccountOperateLogVo extends BaseVo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1580660963478826658L;
	
	private Integer paymentId;
	
	private String paymentStreamId;
	
	private Integer paymentPersonId;
	
	private String paymentPersonName;
	
	private Integer paymentType;
	
	private BigDecimal paymentAccount;
	
	private String paymentDesc;
	
	private String teachBranchName;
	
	private String studentName;
	
	private String telephone;

	public Integer getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(Integer paymentId) {
		this.paymentId = paymentId;
	}

	public String getPaymentStreamId() {
		return paymentStreamId;
	}

	public void setPaymentStreamId(String paymentStreamId) {
		this.paymentStreamId = paymentStreamId;
	}

	public Integer getPaymentPersonId() {
		return paymentPersonId;
	}

	public void setPaymentPersonId(Integer paymentPersonId) {
		this.paymentPersonId = paymentPersonId;
	}

	public String getPaymentPersonName() {
		return paymentPersonName;
	}

	public void setPaymentPersonName(String paymentPersonName) {
		this.paymentPersonName = paymentPersonName;
	}

	public Integer getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(Integer paymentType) {
		this.paymentType = paymentType;
	}

	public BigDecimal getPaymentAccount() {
		return paymentAccount;
	}

	public void setPaymentAccount(BigDecimal paymentAccount) {
		this.paymentAccount = paymentAccount;
	}

	public String getPaymentDesc() {
		return paymentDesc;
	}

	public void setPaymentDesc(String paymentDesc) {
		this.paymentDesc = paymentDesc;
	}

	public String getTeachBranchName() {
		return teachBranchName;
	}

	public void setTeachBranchName(String teachBranchName) {
		this.teachBranchName = teachBranchName;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	
}
