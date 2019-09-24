package com.education.hjj.bz.entity.vo;

import java.io.Serializable;
import java.math.BigDecimal;

public class TeacherAccountVo extends BaseVo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 361796394838828678L;

	private Integer teacherId;
	
	private String teacherName;
	
	private String teacherPhone;
	
	//账户总入账金额
	private BigDecimal accountMoney;
	
	//账户余额
	private BigDecimal surplusMoney;
	
	//该账户创建了多久
	private String stayTime;

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

	public String getStayTime() {
		return stayTime;
	}

	public void setStayTime(String stayTime) {
		this.stayTime = stayTime;
	}
	
}
