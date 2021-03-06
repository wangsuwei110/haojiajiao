package com.education.hjj.bz.entity;

import java.io.Serializable;
import java.math.BigDecimal; 

public class TeacherAccountPo extends BasePo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5265596710782558322L;

	private Integer teacherId;
	
	private String teacherName;
	
	private String teacherPhone;
	
	private BigDecimal accountMoney;
	
	private BigDecimal surplusMoney;
	

	public Integer getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(Integer teacherId) {
		this.teacherId = teacherId;
	}

	public String getTeacherName() {
		return teacherName;
	}

	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}

	public String getTeacherPhone() {
		return teacherPhone;
	}

	public void setTeacherPhone(String teacherPhone) {
		this.teacherPhone = teacherPhone;
	}

	public BigDecimal getAccountMoney() {
		return accountMoney;
	}

	public void setAccountMoney(BigDecimal accountMoney) {
		this.accountMoney = accountMoney;
	}

	public BigDecimal getSurplusMoney() {
		return surplusMoney;
	}

	public void setSurplusMoney(BigDecimal surplusMoney) {
		this.surplusMoney = surplusMoney;
	}

}
