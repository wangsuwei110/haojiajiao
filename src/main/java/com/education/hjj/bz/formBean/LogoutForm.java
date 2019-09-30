package com.education.hjj.bz.formBean;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;

@ApiModel("用户登出信息")
public class LogoutForm implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7383091642255012278L;
	
	private String userId;
	
	private Integer type;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	
	
}
