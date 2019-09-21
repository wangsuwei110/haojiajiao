package com.education.hjj.bz.entity;

import java.io.Serializable;

public class TeacherAndStudentDemandPo extends BasePo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5409735855662769088L;

	private Integer demandId;
	
	private Integer teacherId;
	
	private String teacherPhone;
	
	private String teacherName;
	
	private Integer chooseStatus;

	public Integer getDemandId() {
		return demandId;
	}

	public void setDemandId(Integer demandId) {
		this.demandId = demandId;
	}

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

	public Integer getChooseStatus() {
		return chooseStatus;
	}

	public void setChooseStatus(Integer chooseStatus) {
		this.chooseStatus = chooseStatus;
	}

	public String getTeacherPhone() {
		return teacherPhone;
	}

	public void setTeacherPhone(String teacherPhone) {
		this.teacherPhone = teacherPhone;
	}
	
	
}
