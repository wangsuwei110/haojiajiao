package com.education.hjj.bz.formBean;

import java.io.Serializable;
import java.math.BigDecimal;

import io.swagger.annotations.ApiModel;

@ApiModel("教员用户提现")
public class TeacherAccountForm implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1662310275930833844L;

	//登录手机号
	private String loginPhone;
	
	//code
	private String code;
	
	//提现金额
	private String  CashOut;
	
	//教员id
	private Integer teacherId;

	public String getLoginPhone() {
		return loginPhone;
	}

	public void setLoginPhone(String loginPhone) {
		this.loginPhone = loginPhone;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCashOut() {
		return CashOut;
	}

	public void setCashOut(String cashOut) {
		CashOut = cashOut;
	}

	public Integer getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(Integer teacherId) {
		this.teacherId = teacherId;
	}
		
}
