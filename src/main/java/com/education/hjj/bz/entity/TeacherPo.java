package com.education.hjj.bz.entity;

import java.io.Serializable;
import java.math.BigDecimal;

public class TeacherPo extends BasePo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2812346581427175907L;
	
	private Integer teacherId;

	private String num;
	
	private String name;
	
	private String openId;
	
	private Integer sex;
	
	private String telephone;
	
	private String weiChar;
	
	private String QQ;
	
	private String picture;
	
	private String birthday;
	
	private String home;
	
	private String idCard;
	
	private String school;
	
	private String major;
	
	private String address;
	
	private String degree;
	
	private String account;
	
	private String password;
	
	private String teacherLevel;
	
	private String experience;
	
	private String teacherTag;
	
	private Integer teachLevel;
	
	private String teachGrade;
	
	private Integer teachBrance;
	
	private String teachBranchSlave;
	
	private String teachAddress;
	
	private String teachTime;
	
	private Integer logonStatus;
	
	private Integer auditStatus;
	
	private Integer resumeComplete;
	
	private String teacherProfile;
	
	private String teacherCertificate;
	
	private Integer vacationStatus;
	
	private String teachStyle;
	
	private String teachType;
	
	private String chargesStandard;
	
	private String beginSchoolTime;
	
	private Integer isGraduate;
	
	private String teacherPoints;
	
	private BigDecimal employRate;
	
	private BigDecimal resumptionRate;	
	
	private Integer singUpCount;
	
	private Integer chooseCount;
	
	private Integer resumptionCount;
	
	private Integer employCount;

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
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

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getWeiChar() {
		return weiChar;
	}

	public void setWeiChar(String weiChar) {
		this.weiChar = weiChar;
	}

	public String getQQ() {
		return QQ;
	}

	public void setQQ(String qQ) {
		QQ = qQ;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getHome() {
		return home;
	}

	public void setHome(String home) {
		this.home = home;
	}


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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getDegree() {
		return degree;
	}

	public void setDegree(String degree) {
		this.degree = degree;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getTeacherLevel() {
		return teacherLevel;
	}

	public void setTeacherLevel(String teacherLevel) {
		this.teacherLevel = teacherLevel;
	}

	public String getExperience() {
		return experience;
	}

	public void setExperience(String experience) {
		this.experience = experience;
	}

	public String getTeacherTag() {
		return teacherTag;
	}

	public void setTeacherTag(String teacherTag) {
		this.teacherTag = teacherTag;
	}

	public String getTeachAddress() {
		return teachAddress;
	}

	public void setTeachAddress(String teachAddress) {
		this.teachAddress = teachAddress;
	}

	public String getTeachTime() {
		return teachTime;
	}

	public void setTeachTime(String teachTime) {
		this.teachTime = teachTime;
	}

	public Integer getLogonStatus() {
		return logonStatus;
	}

	public void setLogonStatus(Integer logonStatus) {
		this.logonStatus = logonStatus;
	}

	public String getTeacherProfile() {
		return teacherProfile;
	}

	public void setTeacherProfile(String teacherProfile) {
		this.teacherProfile = teacherProfile;
	}

	public String getTeacherCertificate() {
		return teacherCertificate;
	}

	public void setTeacherCertificate(String teacherCertificate) {
		this.teacherCertificate = teacherCertificate;
	}

	public Integer getVacationStatus() {
		return vacationStatus;
	}

	public void setVacationStatus(Integer vacationStatus) {
		this.vacationStatus = vacationStatus;
	}

	public String getTeachStyle() {
		return teachStyle;
	}

	public void setTeachStyle(String teachStyle) {
		this.teachStyle = teachStyle;
	}

	public String getTeachType() {
		return teachType;
	}

	public void setTeachType(String teachType) {
		this.teachType = teachType;
	}

	public String getBeginSchoolTime() {
		return beginSchoolTime;
	}

	public void setBeginSchoolTime(String beginSchoolTime) {
		this.beginSchoolTime = beginSchoolTime;
	}


	public Integer getIsGraduate() {
		return isGraduate;
	}

	public void setIsGraduate(Integer isGraduate) {
		this.isGraduate = isGraduate;
	}

	public String getTeachBranchSlave() {
		return teachBranchSlave;
	}

	public void setTeachBranchSlave(String teachBranchSlave) {
		this.teachBranchSlave = teachBranchSlave;
	}

	public Integer getTeachLevel() {
		return teachLevel;
	}

	public void setTeachLevel(Integer teachLevel) {
		this.teachLevel = teachLevel;
	}

	public String getTeachGrade() {
		return teachGrade;
	}

	public void setTeachGrade(String teachGrade) {
		this.teachGrade = teachGrade;
	}

	public Integer getTeachBrance() {
		return teachBrance;
	}

	public void setTeachBrance(Integer teachBrance) {
		this.teachBrance = teachBrance;
	}

	public String getChargesStandard() {
		return chargesStandard;
	}

	public void setChargesStandard(String chargesStandard) {
		this.chargesStandard = chargesStandard;
	}

	public Integer getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(Integer auditStatus) {
		this.auditStatus = auditStatus;
	}

	public Integer getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(Integer teacherId) {
		this.teacherId = teacherId;
	}

	public String getTeacherPoints() {
		return teacherPoints;
	}

	public void setTeacherPoints(String teacherPoints) {
		this.teacherPoints = teacherPoints;
	}

	public BigDecimal getEmployRate() {
		return employRate;
	}

	public void setEmployRate(BigDecimal employRate) {
		this.employRate = employRate;
	}

	public BigDecimal getResumptionRate() {
		return resumptionRate;
	}

	public void setResumptionRate(BigDecimal resumptionRate) {
		this.resumptionRate = resumptionRate;
	}

	public Integer getSingUpCount() {
		return singUpCount;
	}

	public void setSingUpCount(Integer singUpCount) {
		this.singUpCount = singUpCount;
	}

	public Integer getChooseCount() {
		return chooseCount;
	}

	public void setChooseCount(Integer chooseCount) {
		this.chooseCount = chooseCount;
	}

	public Integer getResumptionCount() {
		return resumptionCount;
	}

	public void setResumptionCount(Integer resumptionCount) {
		this.resumptionCount = resumptionCount;
	}

	public Integer getEmployCount() {
		return employCount;
	}

	public void setEmployCount(Integer employCount) {
		this.employCount = employCount;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public Integer getResumeComplete() {
		return resumeComplete;
	}

	public void setResumeComplete(Integer resumeComplete) {
		this.resumeComplete = resumeComplete;
	}

	
}
