package com.education.hjj.bz.entity.vo;

import java.io.Serializable;

public class TeacherPointsRuleVo extends BaseVo implements Serializable{

	
/**
	 * 
	 */
	private static final long serialVersionUID = -4424450732129190112L;

	private Integer pointsRuleId;
	
	private Integer pointsScore;
	
	private String pointsUnit;
	
	private String pointsRuleName;
	

	public Integer getPointsRuleId() {
		return pointsRuleId;
	}

	public void setPointsRuleId(Integer pointsRuleId) {
		this.pointsRuleId = pointsRuleId;
	}

	public Integer getPointsScore() {
		return pointsScore;
	}

	public void setPointsScore(Integer pointsScore) {
		this.pointsScore = pointsScore;
	}

	public String getPointsRuleName() {
		return pointsRuleName;
	}

	public void setPointsRuleName(String pointsRuleName) {
		this.pointsRuleName = pointsRuleName;
	}

	public String getPointsUnit() {
		return pointsUnit;
	}

	public void setPointsUnit(String pointsUnit) {
		this.pointsUnit = pointsUnit;
	}
	
}
