package com.education.hjj.bz.formBean;

import java.io.Serializable;
import java.util.List;

public class StudentTeacherInfoForm extends PageForm implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 9147674499346990621L;
	
	private Integer teachBrance;
	
	private Integer teachAddress;
	
	private String school;
	
	private Integer isGraduate;

	private Integer teacherPoints;
	
	private String createTime;
	
	private Integer sex;

	private Long studentId;

	private String branchs;

	private List<String> branchList;
	
	private Integer teacherId;

	public List<String> getBranchList() {
		return branchList;
	}

	public void setBranchList(List<String> branchList) {
		this.branchList = branchList;
	}

	public String getBranchs() {

		return branchs;
	}

	public void setBranchs(String branchs) {
		this.branchs = branchs;
	}

	public Long getStudentId() {
		return studentId;
	}

	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}

	public Integer getTeachBrance() {
		return teachBrance;
	}

	public void setTeachBrance(Integer teachBrance) {
		this.teachBrance = teachBrance;
	}

	public Integer getTeachAddress() {
		return teachAddress;
	}

	public void setTeachAddress(Integer teachAddress) {
		this.teachAddress = teachAddress;
	}

	public String getSchool() {
		return school;
	}

	public void setSchool(String school) {
		this.school = school;
	}

	public Integer getIsGraduate() {
		return isGraduate;
	}

	public void setIsGraduate(Integer isGraduate) {
		this.isGraduate = isGraduate;
	}

	public Integer getTeacherPoints() {
		return teacherPoints;
	}

	public void setTeacherPoints(Integer teacherPoints) {
		this.teacherPoints = teacherPoints;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public Integer getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(Integer teacherId) {
		this.teacherId = teacherId;
	}
	
}
