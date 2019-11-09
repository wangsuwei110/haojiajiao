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
public class StudentDemandConnectForm extends PageForm implements Serializable {

    private Long sid;

    @ApiModelProperty(value = "需求ID", required = true)
    private Integer demandId;

    @ApiModelProperty(value = "确定预约时间", required = true)
    private Date confirmDate;

    @ApiModelProperty(value = "学员ID", required = true)
    @NotBlank(message = "学员ID不能为空")
    private Integer studentId;

    @ApiModelProperty(value = "教员ID", required = true)
    private Integer teacherId;

    @ApiModelProperty(value = "试讲是否通过，2:试讲通过，3:试讲未通过")
    private Integer status;

    @ApiModelProperty(value = "试讲不通过的原因")
    private String appraise;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    @ApiModelProperty(value = "家长手机号")
    private String parentPhoneNum;

    @ApiModelProperty(value = "更新时间")
    private Date createTime;

    @ApiModelProperty(value = "创建用户")
    private Integer createUser;

    @ApiModelProperty(value = "订单类型：1:单独预约，2:快速请家教")
    private Integer demandType;

    private Integer deleteStatus;
    
    //订单的状态
    private String demandSignStatus;
    
    private Date orderTeachTime;

    public Integer getDemandType() {
        return demandType;
    }

    public void setDemandType(Integer demandType) {
        this.demandType = demandType;
    }

    public Integer getDeleteStatus() {
        return deleteStatus;
    }

    public void setDeleteStatus(Integer deleteStatus) {
        this.deleteStatus = deleteStatus;
    }

    public Integer getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Integer createUser) {
        this.createUser = createUser;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getSid() {
        return sid;
    }

    public void setSid(Long sid) {
        this.sid = sid;
    }

    public String getParentPhoneNum() {
        return parentPhoneNum;
    }

    public void setParentPhoneNum(String parentPhoneNum) {
        this.parentPhoneNum = parentPhoneNum;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getAppraise() {
        return appraise;
    }

    public void setAppraise(String appraise) {
        this.appraise = appraise;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Integer teacherId) {
        this.teacherId = teacherId;
    }

    public Date getConfirmDate() {
        return confirmDate;
    }

    public void setConfirmDate(Date confirmDate) {
        this.confirmDate = confirmDate;
    }

    public Integer getDemandId() {
        return demandId;
    }

    public void setDemandId(Integer demandId) {
        this.demandId = demandId;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }
    public String getDemandSignStatus() {
		return demandSignStatus;
	}

	public void setDemandSignStatus(String demandSignStatus) {
		this.demandSignStatus = demandSignStatus;
	}

	public Date getOrderTeachTime() {
		return orderTeachTime;
	}

	public void setOrderTeachTime(Date orderTeachTime) {
		this.orderTeachTime = orderTeachTime;
	}
	
	
    
}
