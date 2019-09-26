package com.education.hjj.bz.formBean;

import java.io.Serializable;

public class TeacherInfoReplenishForm implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3549140689466707070L;

	private String teacherId;
	
	private String telephone;
	
	private String name;
	
	private Integer sex;
	
	private String school;
	
	private String teachGrade;
	
	private String teachBrance;
	
	private String teachBranchSlave;
	
	private String address;

	

	public String getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(String teacherId) {
		this.teacherId = teacherId;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public String getSchool() {
		return school;
	}

	public void setSchool(String school) {
		this.school = school;
	}

	public String getTeachGrade() {
		return teachGrade;
	}

	public void setTeachGrade(String teachGrade) {
		this.teachGrade = teachGrade;
	}

	public String getTeachBrance() {
		return teachBrance;
	}

	public void setTeachBrance(String teachBrance) {
		this.teachBrance = teachBrance;
	}

	public String getTeachBranchSlave() {
		return teachBranchSlave;
	}

	public void setTeachBranchSlave(String teachBranchSlave) {
		this.teachBranchSlave = teachBranchSlave;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
}
