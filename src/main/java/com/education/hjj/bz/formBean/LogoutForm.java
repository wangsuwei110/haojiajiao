package com.education.hjj.bz.formBean;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;

@ApiModel("用户登出信息")
public class LogoutForm implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7383091642255012278L;
	
	private String loginPhone;

	public String getLoginPhone() {
		return loginPhone;
	}

	public void setLoginPhone(String loginPhone) {
		this.loginPhone = loginPhone;
	}
	
	
}
