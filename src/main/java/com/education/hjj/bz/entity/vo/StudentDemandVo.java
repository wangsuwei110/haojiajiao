package com.education.hjj.bz.entity.vo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
/**
 * 学员发布需求表Vo
 *
 * @创建者：sys
 * @创建时间：2019-9-26 0:51:18
 */
@ApiModel(value = "StudentDemand")
public class StudentDemandVo {


    @ApiModelProperty(value = "需求主键id")
    private Long sid;

    @ApiModelProperty(value = "学员ID")
    private Long studentId;

    @ApiModelProperty(value = "上课地址")
    private String demandAddress;

    @ApiModelProperty(value = "补习年级")
    private Long demandGrade;

    @ApiModelProperty(value = "辅导科目id")
    private Long subjectId;

    @ApiModelProperty(value = "付费订单开始时间")
    private Date orderStart;

    @ApiModelProperty(value = "订单周数")
    private Date weekNum;

    @ApiModelProperty(value = "每周上课次数")
    private Long classNum;

    @ApiModelProperty(value = "每周上课时间范围")
    private String timeRange;

    @ApiModelProperty(value = "订单类型,1:试讲订单,2:付费订单")
    private Integer orderType;

    @ApiModelProperty(value = "订单金额")
    private Float orderMoney;

    @ApiModelProperty(value = "具体需求")
    private String demandDesc;

    @ApiModelProperty(value = "状态 0:未发布，1:发布中;2:已接单;3:结单")
    private Boolean status;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "创建人")
    private String createUser;

    @ApiModelProperty(value = "修改时间")
    private Date updateTime;

    @ApiModelProperty(value = "修改人")
    private String updateUser;


    public Long getSid() {
        return sid;
    }

    public void setSid(Long sid) {
        this.sid = sid;
    }
    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }
    public String getDemandAddress() {
        return demandAddress;
    }

    public void setDemandAddress(String demandAddress) {
        this.demandAddress = demandAddress;
    }
    public Long getDemandGrade() {
        return demandGrade;
    }

    public void setDemandGrade(Long demandGrade) {
        this.demandGrade = demandGrade;
    }
    public Long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Long subjectId) {
        this.subjectId = subjectId;
    }
    public Date getOrderStart() {
        return orderStart;
    }

    public void setOrderStart(Date orderStart) {
        this.orderStart = orderStart;
    }
    public Date getWeekNum() {
        return weekNum;
    }

    public void setWeekNum(Date weekNum) {
        this.weekNum = weekNum;
    }
    public Long getClassNum() {
        return classNum;
    }

    public void setClassNum(Long classNum) {
        this.classNum = classNum;
    }
    public String getTimeRange() {
        return timeRange;
    }

    public void setTimeRange(String timeRange) {
        this.timeRange = timeRange;
    }
    public Integer getOrderType() {
        return orderType;
    }

    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }
    public Float getOrderMoney() {
        return orderMoney;
    }

    public void setOrderMoney(Float orderMoney) {
        this.orderMoney = orderMoney;
    }
    public String getDemandDesc() {
        return demandDesc;
    }

    public void setDemandDesc(String demandDesc) {
        this.demandDesc = demandDesc;
    }
    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
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
    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }



}
