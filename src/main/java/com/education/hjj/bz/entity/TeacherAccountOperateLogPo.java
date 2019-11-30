package com.education.hjj.bz.entity;

import java.io.Serializable;
import java.math.BigDecimal;

public class TeacherAccountOperateLogPo extends BasePo implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5826890993157196018L;

	private Integer paymentId;
	
	private String paymentStreamId;
	
	private Integer paymentPersonId;
	
	private String paymentPersonName;
	
	private Integer paymentType;
	
	private BigDecimal paymentAccount;
	
	private String paymentDesc;

	private String orderId;


	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
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

	public String getPaymentStreamId() {
		return paymentStreamId;
	}

	public void setPaymentStreamId(String paymentStreamId) {
		this.paymentStreamId = paymentStreamId;
	}
	
}
