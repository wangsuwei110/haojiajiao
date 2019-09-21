package com.education.hjj.bz.entity;

import java.io.Serializable;
import java.math.BigDecimal;

public class TeacherLevelRulePo extends BasePo implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4638631540823330101L;

	private Integer teacherLevelId;
	
	private String teacherLevelName;
	
	private String teacherPointsSection;
	
	private String resumptionRate;
	
	private BigDecimal classWage;

	public Integer getTeacherLevelId() {
		return teacherLevelId;
	}

	public void setTeacherLevelId(Integer teacherLevelId) {
		this.teacherLevelId = teacherLevelId;
	}

	public String getTeacherLevelName() {
		return teacherLevelName;
	}

	public void setTeacherLevelName(String teacherLevelName) {
		this.teacherLevelName = teacherLevelName;
	}

	public String getTeacherPointsSection() {
		return teacherPointsSection;
	}

	public void setTeacherPointsSection(String teacherPointsSection) {
		this.teacherPointsSection = teacherPointsSection;
	}

	public String getResumptionRate() {
		return resumptionRate;
	}

	public void setResumptionRate(String resumptionRate) {
		this.resumptionRate = resumptionRate;
	}

	public BigDecimal getClassWage() {
		return classWage;
	}

	public void setClassWage(BigDecimal classWage) {
		this.classWage = classWage;
	}
	
	
}
