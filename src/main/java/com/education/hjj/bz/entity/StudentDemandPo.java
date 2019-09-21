package com.education.hjj.bz.entity;

import java.io.Serializable;

public class StudentDemandPo extends BasePo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4351685526919605839L;
	
	private Integer demandId;

	private Integer studentId;
	
	private String studentName;
	
	private String demandNum;
	
	private String studnetArea;
	
	private String demandAddress;
	
	private String demandGrade;
	
	private Integer demandBranch;
	
	private String demandPhone;
	
	private String teacherSex;
	
	private String teachTime;
	
	private Integer teachCount;
	
	private String demandDesc;
	
	private Integer personNum;
	

	public Integer getStudentId() {
		return studentId;
	}

	public void setStudentId(Integer studentId) {
		this.studentId = studentId;
	}

	public String getDemandNum() {
		return demandNum;
	}

	public void setDemandNum(String demandNum) {
		this.demandNum = demandNum;
	}

	public String getStudnetArea() {
		return studnetArea;
	}

	public void setStudnetArea(String studnetArea) {
		this.studnetArea = studnetArea;
	}

	public String getDemandAddress() {
		return demandAddress;
	}

	public void setDemandAddress(String demandAddress) {
		this.demandAddress = demandAddress;
	}

	public Integer getDemandBranch() {
		return demandBranch;
	}

	public void setDemandBranch(Integer demandBranch) {
		this.demandBranch = demandBranch;
	}

	public String getDemandPhone() {
		return demandPhone;
	}

	public void setDemandPhone(String demandPhone) {
		this.demandPhone = demandPhone;
	}

	public String getTeacherSex() {
		return teacherSex;
	}

	public void setTeacherSex(String teacherSex) {
		this.teacherSex = teacherSex;
	}

	public String getTeachTime() {
		return teachTime;
	}

	public void setTeachTime(String teachTime) {
		this.teachTime = teachTime;
	}

	public Integer getTeachCount() {
		return teachCount;
	}

	public void setTeachCount(Integer teachCount) {
		this.teachCount = teachCount;
	}

	public String getDemandDesc() {
		return demandDesc;
	}

	public void setDemandDesc(String demandDesc) {
		this.demandDesc = demandDesc;
	}

	public Integer getPersonNum() {
		return personNum;
	}

	public void setPersonNum(Integer personNum) {
		this.personNum = personNum;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public Integer getDemandId() {
		return demandId;
	}

	public void setDemandId(Integer demandId) {
		this.demandId = demandId;
	}

	public String getDemandGrade() {
		return demandGrade;
	}

	public void setDemandGrade(String demandGrade) {
		this.demandGrade = demandGrade;
	}
	
}
