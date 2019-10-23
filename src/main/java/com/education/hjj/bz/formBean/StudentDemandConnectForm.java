package com.education.hjj.bz.formBean;

import io.swagger.annotations.Api;
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
}
