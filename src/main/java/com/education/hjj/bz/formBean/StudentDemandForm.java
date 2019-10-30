package com.education.hjj.bz.formBean;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;
import java.util.Date;
/**
 * 学员发布需求表Form
 *
 * @创建者：sys
 * @创建时间：2019-9-26 0:51:18
 */
public class StudentDemandForm extends PageForm implements Serializable {

    private Long sid;

    @ApiModelProperty(value = "学员ID",required = true)
    @NotBlank(message = "学员ID不能为空")
    private Integer studentId;

    @ApiModelProperty(value = "学员姓名（发布需求时，姓名改变，插入新学员）",required = true)
    private String studentName;

    @ApiModelProperty(value = "学员性别",required = true)
    private Integer sex;

    @ApiModelProperty(value = "上课地址",required = true)
    @NotBlank(message = "上课地址不能为空")
    private String demandAddress;

    @ApiModelProperty(value = "补习年级",required = true)
    @NotBlank(message = "补习年级不能为空")
    private Integer demandGrade;

    @ApiModelProperty(value = "辅导科目id",required = true)
    @NotBlank(message = "辅导科目id不能为空")
    private Integer subjectId;

    @ApiModelProperty(value = "付费订单开始时间",required = true)
    @NotBlank(message = "付费订单开始时间不能为空")
    private Date orderStart;

    @ApiModelProperty(value = "订单周数",required = true)
    @NotBlank(message = "订单周数不能为空")
    private Integer weekNum;

    @ApiModelProperty(value = "每周上课次数",required = true)
    @NotBlank(message = "每周上课次数不能为空")
    private Integer classNum;

    @ApiModelProperty(value = "每周上课时间范围",required = true)
    @NotBlank(message = "每周上课时间范围不能为空")
    private String timeRange;

    @ApiModelProperty(value = "订单类型,1:试讲订单,2:付费订单",required = true)
    @NotBlank(message = "订单类型,1:试讲订单,2:付费订单不能为空")
    private Integer orderType;

    @ApiModelProperty(value = "订单金额",required = true)
    @NotBlank(message = "订单金额不能为空")
    private Float orderMoney;

    @ApiModelProperty(value = "具体需求",required = true)
    @NotBlank(message = "具体需求不能为空")
    private String demandDesc;

    @ApiModelProperty(value = "状态 0:未发布，1:发布中;2:已接单;3:结单",required = true)
    @NotBlank(message = "状态 0:未发布，1:发布中;2:已接单;3:结单不能为空")
    private Integer status;

    @ApiModelProperty(value = "创建时间",required = true)
    @NotBlank(message = "创建时间不能为空")
    private Date createTime;

    @ApiModelProperty(value = "创建人",required = true)
    @NotBlank(message = "创建人不能为空")
    private String createUser;

    @ApiModelProperty(value = "修改时间",required = true)
    @NotBlank(message = "修改时间不能为空")
    private Date updateTime;

    @ApiModelProperty(value = "修改人",required = true)
    @NotBlank(message = "修改人不能为空")
    private String updateUser;

    @ApiModelProperty(value = "订单类型",required = true)
    private Integer demandType;

    @ApiModelProperty(value = "区域id",required = true)
    private Integer parameterId;


    // *******************单独预约********************
    @ApiModelProperty(value = "教员ID",required = true)
    private Integer teacherId;

    public Integer getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Integer teacherId) {
        this.teacherId = teacherId;
    }

    public Integer getParameterId() {
        return parameterId;
    }

    public void setParameterId(Integer parameterId) {
        this.parameterId = parameterId;
    }

    public Integer getDemandType() {
        return demandType;
    }

    public void setDemandType(Integer demandType) {
        this.demandType = demandType;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Long getSid() {
        return sid;
    }

    public void setSid(Long sid) {
        this.sid = sid;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public String getDemandAddress() {
        return demandAddress;
    }

    public void setDemandAddress(String demandAddress) {
        this.demandAddress = demandAddress;
    }

    public Integer getDemandGrade() {
        return demandGrade;
    }

    public void setDemandGrade(Integer demandGrade) {
        this.demandGrade = demandGrade;
    }

    public Integer getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Integer subjectId) {
        this.subjectId = subjectId;
    }

    public Date getOrderStart() {
        return orderStart;
    }

    public void setOrderStart(Date orderStart) {
        this.orderStart = orderStart;
    }

    public Integer getWeekNum() {
        return weekNum;
    }

    public void setWeekNum(Integer weekNum) {
        this.weekNum = weekNum;
    }

    public Integer getClassNum() {
        return classNum;
    }

    public void setClassNum(Integer classNum) {
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
