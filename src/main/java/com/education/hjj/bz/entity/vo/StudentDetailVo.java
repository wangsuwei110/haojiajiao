package com.education.hjj.bz.entity.vo;

import com.education.hjj.bz.entity.BasePo;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class StudentDetailVo extends BasePo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8187654387169395608L;

	private Long sid;

	private String studentName;

	private String telphone;

	/**
	* 性别：1：男  2：女
	**/
	private Integer sex;

	/**
	 * 年级：根据数字显示年级 （例如：1：代表一年级）
	 **/
	private Integer grade;

	private String studyAddress;

	/**
	 * 家长id
	 **/
	private Long parentId;


	public Long getSid() {
		return sid;
	}

	public void setSid(Long sid) {
		this.sid = sid;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public String getTelphone() {
		return telphone;
	}

	public void setTelphone(String telphone) {
		this.telphone = telphone;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public Integer getGrade() {
		return grade;
	}

	public void setGrade(Integer grade) {
		this.grade = grade;
	}

	public String getStudyAddress() {
		return studyAddress;
	}

	public void setStudyAddress(String studyAddress) {
		this.studyAddress = studyAddress;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
}
