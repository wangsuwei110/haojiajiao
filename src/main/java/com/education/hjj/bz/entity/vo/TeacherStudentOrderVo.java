package com.education.hjj.bz.entity.vo;

import java.io.Serializable;

public class TeacherStudentOrderVo extends StudentOrderVo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -235510117757204870L;
	
	private Integer orderId;
	
	private Integer teacherId;
	
	private String teacherPhone;
	
	private String teacherName;
	
	private Integer chooseStatus;

	
	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public Integer getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(Integer teacherId) {
		this.teacherId = teacherId;
	}

	public String getTeacherPhone() {
		return teacherPhone;
	}

	public void setTeacherPhone(String teacherPhone) {
		this.teacherPhone = teacherPhone;
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
	
}
