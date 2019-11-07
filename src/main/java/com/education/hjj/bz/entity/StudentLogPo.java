package com.education.hjj.bz.entity;

import java.io.Serializable;

public class StudentLogPo extends BasePo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5682209066064654549L;
	/**
	 * 
	 */
	

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
	
}
