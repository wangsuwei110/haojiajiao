package com.education.hjj.bz.entity.vo;

import java.io.Serializable;

public class LoginLogVo extends BaseVo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8973555597480600791L;
	
	private String phoneNum;
	
	private String teacherName;
	
	private String StudentName;

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public String getTeacherName() {
		return teacherName;
	}

	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}

	public String getStudentName() {
		return StudentName;
	}

	public void setStudentName(String studentName) {
		StudentName = studentName;
	}
	
}
