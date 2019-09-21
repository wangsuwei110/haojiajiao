package com.education.hjj.bz.entity;

import java.io.Serializable;

public class TeacherPointsRulePo extends BasePo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3725438184810619465L;

	private Integer pointsRuleId;
	
	private Integer pointsScore;
	
	private Integer pointsRuleType;
	
	private String pointsRuleName;
	
	private Integer pointsScoreType;

	public Integer getPointsScore() {
		return pointsScore;
	}

	public void setPointsScore(Integer pointsScore) {
		this.pointsScore = pointsScore;
	}

	public Integer getPointsRuleType() {
		return pointsRuleType;
	}

	public void setPointsRuleType(Integer pointsRuleType) {
		this.pointsRuleType = pointsRuleType;
	}

	public String getPointsRuleName() {
		return pointsRuleName;
	}

	public void setPointsRuleName(String pointsRuleName) {
		this.pointsRuleName = pointsRuleName;
	}

	public Integer getPointsScoreType() {
		return pointsScoreType;
	}

	public void setPointsScoreType(Integer pointsScoreType) {
		this.pointsScoreType = pointsScoreType;
	}

	public Integer getPointsRuleId() {
		return pointsRuleId;
	}

	public void setPointsRuleId(Integer pointsRuleId) {
		this.pointsRuleId = pointsRuleId;
	}
	
}
