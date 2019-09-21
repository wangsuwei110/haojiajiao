package com.education.hjj.bz.entity.vo;

public class TeacherInfoPicturesVo extends PictureVo{

	/**
	 * 
	 */
	private static final long serialVersionUID = -388432421604807987L;

	private String idCard;
	
	private String school;
	
	private String major;
	
	private Integer isGraduate;
	
	private String degree;
	
	private String beginSchoolTime;
	
	private String teacherTag;
	
	private Integer logonStatus;
	
	private String pictureTitle;
	
	private String pictureDesc;

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getSchool() {
		return school;
	}

	public void setSchool(String school) {
		this.school = school;
	}

	public String getMajor() {
		return major;
	}

	public void setMajor(String major) {
		this.major = major;
	}

	public Integer getIsGraduate() {
		return isGraduate;
	}

	public void setIsGraduate(Integer isGraduate) {
		this.isGraduate = isGraduate;
	}

	public String getDegree() {
		return degree;
	}

	public void setDegree(String degree) {
		this.degree = degree;
	}

	public String getBeginSchoolTime() {
		return beginSchoolTime;
	}

	public void setBeginSchoolTime(String beginSchoolTime) {
		this.beginSchoolTime = beginSchoolTime;
	}

	public String getTeacherTag() {
		return teacherTag;
	}

	public void setTeacherTag(String teacherTag) {
		this.teacherTag = teacherTag;
	}

	public Integer getLogonStatus() {
		return logonStatus;
	}

	public void setLogonStatus(Integer logonStatus) {
		this.logonStatus = logonStatus;
	}

	public String getPictureTitle() {
		return pictureTitle;
	}

	public void setPictureTitle(String pictureTitle) {
		this.pictureTitle = pictureTitle;
	}

	public String getPictureDesc() {
		return pictureDesc;
	}

	public void setPictureDesc(String pictureDesc) {
		this.pictureDesc = pictureDesc;
	}

	
}
