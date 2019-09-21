package com.education.hjj.bz.entity;

import java.io.Serializable;

public class LoginLogPo extends BasePo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8178011304028314019L;

	private Integer logId;
	
	private String phoneNum;
	
	private String studentName;
	
	private String teacherName;

	public Integer getLogId() {
		return logId;
	}

	public void setLogId(Integer logId) {
		this.logId = logId;
	}

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public String getTeacherName() {
		return teacherName;
	}

	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}
	
}
