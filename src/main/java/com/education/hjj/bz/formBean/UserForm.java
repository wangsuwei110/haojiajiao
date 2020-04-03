package com.education.hjj.bz.formBean;

import java.io.Serializable;

public class UserForm implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2959472032685353330L;
	
	private String account;
	
	private String password;
	
	private String userName;

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
}
