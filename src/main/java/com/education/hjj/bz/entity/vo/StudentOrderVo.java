package com.education.hjj.bz.entity.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class StudentOrderVo extends BaseVo implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7121276364624845999L;

	private Integer orderId;
	
	private String orderNum;
	
	private Integer studnetId;
	
	private String studentName;
	
	private Integer studnetArea;
	
	private String demandAddress;
	
	private String demandGrade;
	
	private String demandBranch;
	
	private String demandPhone;
	
	private Integer teachCount;
	
	private String demandDesc;
	
	private String teacherSex;
	
	private Integer personNum;
	//授课时间
	private String teachTime;
	//试讲时间
	private String trialTime;
	//支付时间
	private Date payTime;
	
	private Date orderTime;
	
	private BigDecimal orderMoney;
	
	private Integer orderStatus;
	
	private String serviceEvaluationDesc;
	
	//是否续课0未续课1已续课
	private Integer isResumption;
	
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

	public BigDecimal getOrderMoney() {
		return orderMoney;
	}

	public void setOrderMoney(BigDecimal orderMoney) {
		this.orderMoney = orderMoney;
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

	public String getDemandGrade() {
		return demandGrade;
	}

	public void setDemandGrade(String demandGrade) {
		this.demandGrade = demandGrade;
	}

	public String getDemandBranch() {
		return demandBranch;
	}

	public void setDemandBranch(String demandBranch) {
		this.demandBranch = demandBranch;
	}

	public String getDemandPhone() {
		return demandPhone;
	}

	public void setDemandPhone(String demandPhone) {
		this.demandPhone = demandPhone;
	}

	public String getTeacherSex() {
		return teacherSex;
	}

	public void setTeacherSex(String teacherSex) {
		this.teacherSex = teacherSex;
	}

	public String getTeachTime() {
		return teachTime;
	}

	public void setTeachTime(String teachTime) {
		this.teachTime = teachTime;
	}

	public String getServiceEvaluationDesc() {
		return serviceEvaluationDesc;
	}

	public void setServiceEvaluationDesc(String serviceEvaluationDesc) {
		this.serviceEvaluationDesc = serviceEvaluationDesc;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public Integer getStudnetId() {
		return studnetId;
	}

	public void setStudnetId(Integer studnetId) {
		this.studnetId = studnetId;
	}

	public Integer getStudnetArea() {
		return studnetArea;
	}

	public void setStudnetArea(Integer studnetArea) {
		this.studnetArea = studnetArea;
	}

	public Integer getTeachCount() {
		return teachCount;
	}

	public void setTeachCount(Integer teachCount) {
		this.teachCount = teachCount;
	}

	public Integer getPersonNum() {
		return personNum;
	}

	public void setPersonNum(Integer personNum) {
		this.personNum = personNum;
	}

	public Integer getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
	}

	public Integer getIsResumption() {
		return isResumption;
	}

	public void setIsResumption(Integer isResumption) {
		this.isResumption = isResumption;
	}
	
}
