package com.education.hjj.bz.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 学员发布需求表Vo
 *
 * @创建者：sys
 * @创建时间：2019-9-26 0:51:18
 */
@ApiModel(value = "OrderDemandVo")
public class OrderDemandVo {

    @ApiModelProperty(value = "需求主键id")
    private Long sid;

    @ApiModelProperty(value = "教员id")
    private Long teacherId;

    @ApiModelProperty(value = "学员姓名")
    private String studentName;

    @ApiModelProperty(value = "预计上课时段")
    private String studyRangeTime;

    @ApiModelProperty(value = "每周上课次数")
    private Integer classNum;

    @ApiModelProperty(value = "补习年级")
    private String grade;

    @ApiModelProperty(value = "具体需求")
    private String demandDesc;

    @ApiModelProperty(value = "每周上课时间范围")
    private String timeRange;

    @ApiModelProperty(value = "状态 0: 预约状态，1：确定状态, 2:结束状态")
    private Integer status;

    @ApiModelProperty(value = "订单创建时间")
    private Date createTime;

    @ApiModelProperty(value = "创建人")
    private String createUser;

    @ApiModelProperty(value = "预计试讲时间")
    private Date expectedTrialTime;

    @ApiModelProperty(value = "教员授课费用")
    private BigDecimal teachMoney;


    public Long getSid() {
        return sid;
    }

    public void setSid(Long sid) {
        this.sid = sid;
    }

    public Long getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Long teacherId) {
        this.teacherId = teacherId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudyRangeTime() {
        return studyRangeTime;
    }

    public void setStudyRangeTime(String studyRangeTime) {
        this.studyRangeTime = studyRangeTime;
    }

    public Integer getClassNum() {
        return classNum;
    }

    public void setClassNum(Integer classNum) {
        this.classNum = classNum;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getDemandDesc() {
        return demandDesc;
    }

    public void setDemandDesc(String demandDesc) {
        this.demandDesc = demandDesc;
    }

    public String getTimeRange() {
        return timeRange;
    }

    public void setTimeRange(String timeRange) {
        this.timeRange = timeRange;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public Date getExpectedTrialTime() {
        return expectedTrialTime;
    }

    public void setExpectedTrialTime(Date expectedTrialTime) {
        this.expectedTrialTime = expectedTrialTime;
    }

    public BigDecimal getTeachMoney() {
        return teachMoney;
    }

    public void setTeachMoney(BigDecimal teachMoney) {
        this.teachMoney = teachMoney;
    }
}
