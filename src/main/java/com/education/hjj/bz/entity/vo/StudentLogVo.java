package com.education.hjj.bz.entity.vo;

import java.io.Serializable;

public class StudentLogVo extends BaseVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4638877104955102448L;

	//学员日志表主键
	private Integer studentLogId;
	//学员姓名
	private String studentName;
	//学员id
	private Integer studentId;
	//日志类型
	private Integer logType;
	//日志内容
	private String logContent;
	
	//学员头像
	private String picture;
	
	
	public Integer getStudentLogId() {
		return studentLogId;
	}
	public void setStudentLogId(Integer studentLogId) {
		this.studentLogId = studentLogId;
	}
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	public Integer getStudentId() {
		return studentId;
	}
	public void setStudentId(Integer studentId) {
		this.studentId = studentId;
	}
	public Integer getLogType() {
		return logType;
	}
	public void setLogType(Integer logType) {
		this.logType = logType;
	}
	public String getLogContent() {
		return logContent;
	}
	public void setLogContent(String logContent) {
		this.logContent = logContent;
	}
	public String getPicture() {
		return picture;
	}
	public void setPicture(String picture) {
		this.picture = picture;
	}
	
}
