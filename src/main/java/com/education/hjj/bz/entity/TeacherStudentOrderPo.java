package com.education.hjj.bz.entity;

public class TeacherStudentOrderPo extends StudentOrderPo{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8088002053834853392L;
	
	private Integer chooseStatus;
	
	private Integer teacherId;
	
	private String teacherName;
	
	private String teacherPhone;
	
	

	public Integer getChooseStatus() {
		return chooseStatus;
	}

	public void setChooseStatus(Integer chooseStatus) {
		this.chooseStatus = chooseStatus;
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

	public String getTeacherPhone() {
		return teacherPhone;
	}

	public void setTeacherPhone(String teacherPhone) {
		this.teacherPhone = teacherPhone;
	}
	
	
}
