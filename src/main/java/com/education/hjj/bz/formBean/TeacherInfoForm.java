package com.education.hjj.bz.formBean;

import java.io.Serializable;
import java.util.List;

import com.education.hjj.bz.entity.TeachTimePo;
import com.fasterxml.jackson.annotation.JsonProperty;

public class TeacherInfoForm extends PageForm implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2669352398665546802L;
	
	private String teacherId;

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
	
	private String teachLevel;
	
	private String teachGrade;
	
	private String teachBrance;
	
	private String teachBranchSlave;
	
	private String teachAddress;
	
	@JsonProperty("teachTime")
	private List<TeachTimePo> timeList;
	
	private String logonStatus;
	
	private Integer auditStatus;
	
	private String auditDesc;
	
	private String teacherProfile;
	
	private String teacherCertificate;
	
	private String vacationStatus;
	
	private String teachStyle;
	
	private String teachType;
	
	private String chargesStandard;
	
	private String createTime;
	
	private String createUser;
	
	private String updateTime;
	
	private String updateUser;
	
	private String beginSchoolTime;
	
	private String status;
	
	private Integer isGraduate;

	private Integer studentId;
	
	private Integer teacherPoints;

	public Integer getStudentId() {
		return studentId;
	}

	public void setStudentId(Integer studentId) {
		this.studentId = studentId;
	}

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

	public String getTeachGrade() {
		return teachGrade;
	}

	public void setTeachGrade(String teachGrade) {
		this.teachGrade = teachGrade;
	}

	public String getTeachBrance() {
		return teachBrance;
	}

	public void setTeachBrance(String teachBrance) {
		this.teachBrance = teachBrance;
	}

	public String getTeachAddress() {
		return teachAddress;
	}

	public void setTeachAddress(String teachAddress) {
		this.teachAddress = teachAddress;
	}

	public List<TeachTimePo> getTimeList() {
		return timeList;
	}

	public void setTimeList(List<TeachTimePo> timeList) {
		this.timeList = timeList;
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

	public String getChargesStandard() {
		return chargesStandard;
	}

	public void setChargesStandard(String chargesStandard) {
		this.chargesStandard = chargesStandard;
	}


	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}


	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	public String getBeginSchoolTime() {
		return beginSchoolTime;
	}

	public void setBeginSchoolTime(String beginSchoolTime) {
		this.beginSchoolTime = beginSchoolTime;
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

	public String getLogonStatus() {
		return logonStatus;
	}

	public void setLogonStatus(String logonStatus) {
		this.logonStatus = logonStatus;
	}

	public String getVacationStatus() {
		return vacationStatus;
	}

	public void setVacationStatus(String vacationStatus) {
		this.vacationStatus = vacationStatus;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public String getTeachLevel() {
		return teachLevel;
	}

	public void setTeachLevel(String teachLevel) {
		this.teachLevel = teachLevel;
	}

	public Integer getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(Integer auditStatus) {
		this.auditStatus = auditStatus;
	}

	public String getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(String teacherId) {
		this.teacherId = teacherId;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public Integer getTeacherPoints() {
		return teacherPoints;
	}

	public void setTeacherPoints(Integer teacherPoints) {
		this.teacherPoints = teacherPoints;
	}

	public String getAuditDesc() {
		return auditDesc;
	}

	public void setAuditDesc(String auditDesc) {
		this.auditDesc = auditDesc;
	}
	
}
