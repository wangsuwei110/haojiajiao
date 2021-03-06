package com.education.hjj.bz.entity.vo;

import java.io.Serializable;
import java.util.List;

public class TeacherDisplayVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2019341256024529297L;

	private Integer sid;

	// 教员id
	private Integer teacherId;

	// 教员名称
	private String teacherName;

	// 学校名字
	private String schoolName;

	// 当前状态：例如：大二在校
	private String currentStatus;

	// 教学方向
	private List<String> teachBranchSlave;

	// 个人标签
	private List<String> teacherTag;

	// 教员等级
	private String teacherLevel;

	// 收费标准
	private String chargesStandard;

	// 图像
	private String picture;

	// 性别（0未知，1男，2女）
	private Integer sex;

	// 是否收藏：true：收藏， false：未收藏
	private boolean collectFlag;

	public boolean isCollectFlag() {
		return collectFlag;
	}

	public void setCollectFlag(boolean collectFlag) {
		this.collectFlag = collectFlag;
	}

	public Integer getSid() {
		return sid;
	}

	public void setSid(Integer sid) {
		this.sid = sid;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
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

	public String getTeacherName() {
		return teacherName;
	}

	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}

	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	public String getCurrentStatus() {
		return currentStatus;
	}

	public void setCurrentStatus(String currentStatus) {
		this.currentStatus = currentStatus;
	}

	public List<String> getTeachBranchSlave() {
		return teachBranchSlave;
	}

	public void setTeachBranchSlave(List<String> teachBranchSlave) {
		this.teachBranchSlave = teachBranchSlave;
	}

	public List<String> getTeacherTag() {
		return teacherTag;
	}

	public void setTeacherTag(List<String> teacherTag) {
		this.teacherTag = teacherTag;
	}

	public String getTeacherLevel() {
		return teacherLevel;
	}

	public void setTeacherLevel(String teacherLevel) {
		this.teacherLevel = teacherLevel;
	}

	public String getChargesStandard() {
		return chargesStandard;
	}

	public void setChargesStandard(String chargesStandard) {
		this.chargesStandard = chargesStandard;
	}
}
