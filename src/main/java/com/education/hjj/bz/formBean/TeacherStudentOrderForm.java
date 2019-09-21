package com.education.hjj.bz.formBean;

public class TeacherStudentOrderForm extends StudentOrderForm{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5160159468788664418L;
	
	private String chooseStatus;
	
	private String teacherPhone;
	
	public String getChooseStatus() {
		return chooseStatus;
	}

	public void setChooseStatus(String chooseStatus) {
		this.chooseStatus = chooseStatus;
	}

	public String getTeacherPhone() {
		return teacherPhone;
	}

	public void setTeacherPhone(String teacherPhone) {
		this.teacherPhone = teacherPhone;
	}
	
}
