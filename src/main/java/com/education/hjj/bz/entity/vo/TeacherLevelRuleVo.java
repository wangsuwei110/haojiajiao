package com.education.hjj.bz.entity.vo;

import java.io.Serializable;
import java.math.BigDecimal;

public class TeacherLevelRuleVo extends BaseVo implements Serializable {

	/**
		 * 
		 */
	private static final long serialVersionUID = -7089445624665023628L;

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
