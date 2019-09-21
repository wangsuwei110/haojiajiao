package com.education.hjj.bz.formBean;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;

@ApiModel("用户登录信息")
public class LoginForm implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3992895886090173382L;

	//登录手机号
	private String loginPhone;
	
	//微信openId
	private String openId;
	
	//性别
	private Integer gender;
	
	//登录类型
	private Integer loginType;
	
	//验证码
	private String identifyCode;
	
	//code
	private String code;
	
	//用户头像
	private String headPicture;
	
	public String getLoginPhone() {
		return loginPhone;
	}

	public void setLoginPhone(String loginPhone) {
		this.loginPhone = loginPhone;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public Integer getGender() {
		return gender;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}

	public Integer getLoginType() {
		return loginType;
	}

	public void setLoginType(Integer loginType) {
		this.loginType = loginType;
	}

	public String getIdentifyCode() {
		return identifyCode;
	}

	public void setIdentifyCode(String identifyCode) {
		this.identifyCode = identifyCode;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getHeadPicture() {
		return headPicture;
	}

	public void setHeadPicture(String headPicture) {
		this.headPicture = headPicture;
	}

}
