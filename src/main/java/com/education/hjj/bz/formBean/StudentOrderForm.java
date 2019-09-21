package com.education.hjj.bz.formBean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class StudentOrderForm extends PageForm implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6735000057547053198L;

	private String orderId;
	
	private String orderNum;
	
	private String studnetId;
	
	private String studentName;
	
	private String studnetArea;
	
	private String demandAddress;
	
	private String demandGrade;
	
	private String demandBranch;
	
	private String demandPhone;
	
	private String teachCount;
	
	private String demandDesc;
	
	private String teacherSex;
	
	private String personNum;
	//授课时间
	private String teachTime;
	//试讲时间
	private String trialTime;
	//支付时间
	private Date payTime;
	
	private Date orderTime;
	
	private BigDecimal orderMoney;
	
	private String orderStatus;
	
	private String serviceEvaluationDesc;
	
	private String teacherId;
	
	private String teacherName;
	
	public String getDemandBranch() {
		return demandBranch;
	}

	public void setDemandBranch(String demandBranch) {
		this.demandBranch = demandBranch;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public String getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}

	public String getDemandAddress() {
		return demandAddress;
	}

	public void setDemandAddress(String demandAddress) {
		this.demandAddress = demandAddress;
	}

	public String getTeachTime() {
		return teachTime;
	}

	public void setTeachTime(String teachTime) {
		this.teachTime = teachTime;
	}

	public String getTeachCount() {
		return teachCount;
	}

	public void setTeachCount(String teachCount) {
		this.teachCount = teachCount;
	}

	public String getDemandDesc() {
		return demandDesc;
	}

	public void setDemandDesc(String demandDesc) {
		this.demandDesc = demandDesc;
	}

	public String getTrialTime() {
		return trialTime;
	}

	public void setTrialTime(String trialTime) {
		this.trialTime = trialTime;
	}

	public String getDemandPhone() {
		return demandPhone;
	}

	public void setDemandPhone(String demandPhone) {
		this.demandPhone = demandPhone;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getStudnetId() {
		return studnetId;
	}

	public void setStudnetId(String studnetId) {
		this.studnetId = studnetId;
	}

	public String getStudnetArea() {
		return studnetArea;
	}

	public void setStudnetArea(String studnetArea) {
		this.studnetArea = studnetArea;
	}

	public String getDemandGrade() {
		return demandGrade;
	}

	public void setDemandGrade(String demandGrade) {
		this.demandGrade = demandGrade;
	}

	public String getTeacherSex() {
		return teacherSex;
	}

	public void setTeacherSex(String teacherSex) {
		this.teacherSex = teacherSex;
	}

	public String getPersonNum() {
		return personNum;
	}

	public void setPersonNum(String personNum) {
		this.personNum = personNum;
	}

	public Date getPayTime() {
		return payTime;
	}

	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}

	public Date getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}

	public BigDecimal getOrderMoney() {
		return orderMoney;
	}

	public void setOrderMoney(BigDecimal orderMoney) {
		this.orderMoney = orderMoney;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getServiceEvaluationDesc() {
		return serviceEvaluationDesc;
	}

	public void setServiceEvaluationDesc(String serviceEvaluationDesc) {
		this.serviceEvaluationDesc = serviceEvaluationDesc;
	}

	public String getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(String teacherId) {
		this.teacherId = teacherId;
	}

	public String getTeacherName() {
		return teacherName;
	}

	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}
}
