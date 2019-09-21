package com.education.hjj.bz.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class StudentOrderPo extends BasePo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3049611039145660815L;
	
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


	public String getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
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

	public String getDemandAddress() {
		return demandAddress;
	}

	public void setDemandAddress(String demandAddress) {
		this.demandAddress = demandAddress;
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

	public Integer getTeachCount() {
		return teachCount;
	}

	public void setTeachCount(Integer teachCount) {
		this.teachCount = teachCount;
	}

	public String getDemandDesc() {
		return demandDesc;
	}

	public void setDemandDesc(String demandDesc) {
		this.demandDesc = demandDesc;
	}

	public String getTeachTime() {
		return teachTime;
	}

	public void setTeachTime(String teachTime) {
		this.teachTime = teachTime;
	}

	public BigDecimal getOrderMoney() {
		return orderMoney;
	}

	public void setOrderMoney(BigDecimal orderMoney) {
		this.orderMoney = orderMoney;
	}

	public Integer getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getServiceEvaluationDesc() {
		return serviceEvaluationDesc;
	}

	public void setServiceEvaluationDesc(String serviceEvaluationDesc) {
		this.serviceEvaluationDesc = serviceEvaluationDesc;
	}

	public String getTrialTime() {
		return trialTime;
	}

	public void setTrialTime(String trialTime) {
		this.trialTime = trialTime;
	}

	public Date getPayTime() {
		return payTime;
	}

	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
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

	public Integer getPersonNum() {
		return personNum;
	}

	public void setPersonNum(Integer personNum) {
		this.personNum = personNum;
	}

	public Date getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}

	public Integer getIsResumption() {
		return isResumption;
	}

	public void setIsResumption(Integer isResumption) {
		this.isResumption = isResumption;
	}
	
	
}
