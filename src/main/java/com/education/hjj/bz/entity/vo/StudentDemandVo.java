package com.education.hjj.bz.entity.vo;

import java.io.Serializable;

import com.education.hjj.bz.entity.BasePo;

public class StudentDemandVo extends BasePo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8187654387169395608L;

	private String demandId;

	private String studentId;
	
	private String studentName;
	
	private String demandNum;
	
	private String studnetArea;
	
	private String demandAddress;
	
	private String demandGrade;
	
	private String demandBranch;
	
	private String demandPhone;
	
	private String teacherSex;
	
	private String teachTime;
	
	private String teachCount;
	
	private String demandDesc;
	
	private String personNum;

	public String getDemandId() {
		return demandId;
	}

	public void setDemandId(String demandId) {
		this.demandId = demandId;
	}

	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
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

	public String getDemandGrade() {
		return demandGrade;
	}

	public void setDemandGrade(String demandGrade) {
		this.demandGrade = demandGrade;
	}

	public String getDemandBranch() {
		return demandBranch;
	}

	public void setDemandBranch(String demandBranch) {
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

	public String getTeachCount() {
		return teachCount;
	}

	public void setTeachCount(String teachCount) {
		this.teachCount = teachCount;
	}

	public String getDemandDesc() {
		return demandDesc;
	}

	public void setDemandDesc(String demandDesc) {
		this.demandDesc = demandDesc;
	}

	public String getPersonNum() {
		return personNum;
	}

	public void setPersonNum(String personNum) {
		this.personNum = personNum;
	}
	
}
