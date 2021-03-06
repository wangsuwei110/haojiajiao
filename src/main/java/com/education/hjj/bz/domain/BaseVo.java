package com.education.hjj.bz.domain;

import java.io.Serializable;
import java.util.Date;

public class BaseVo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5220997101943042862L;

	private Date createTime;
	
	private String createUser;
	
	private Date updateTime;
	
	private String updateUser;
	
	private Integer status;

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	
}
