package com.education.hjj.bz.entity.vo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
/**
 * 学员需求关联表Vo
 *
 * @创建者：sys
 * @创建时间：2019-10-16 0:00:03
 */
@ApiModel(value = "StudentDemandConnect")
public class StudentDemandConnectVo {


    @ApiModelProperty(value = "主键id")
    private Long sid;

    @ApiModelProperty(value = "需求ID")
    private Integer demandId;

    @ApiModelProperty(value = "教员id")
    private Integer teacherId;

    @ApiModelProperty(value = "状态 0:未试讲，1:试讲中;2:试讲通过;3:试讲未通过")
    private Integer status;

    @ApiModelProperty(value = "状态")
    private Integer deleteStatus;

    @ApiModelProperty(value = "预约时间")
    private Date orderTeachTime;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "创建人")
    private String createUser;

    @ApiModelProperty(value = "修改时间")
    private Date updateTime;

    @ApiModelProperty(value = "修改人")
    private String updateUser;

    @ApiModelProperty(value = "教员姓名")
    private String teacherName;
    @ApiModelProperty(value = "收费标准")
    private String chargesStandard;
    @ApiModelProperty(value = "评价")
    private String appraise;

    public String getAppraise() {
        return appraise;
    }

    public void setAppraise(String appraise) {
        this.appraise = appraise;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getChargesStandard() {
        return chargesStandard;
    }

    public void setChargesStandard(String chargesStandard) {
        this.chargesStandard = chargesStandard;
    }

    public Integer getDemandId() {
        return demandId;
    }

    public void setDemandId(Integer demandId) {
        this.demandId = demandId;
    }

    public Integer getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Integer teacherId) {
        this.teacherId = teacherId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getDeleteStatus() {
        return deleteStatus;
    }

    public void setDeleteStatus(Integer deleteStatus) {
        this.deleteStatus = deleteStatus;
    }

    public Date getOrderTeachTime() {
        return orderTeachTime;
    }

    public void setOrderTeachTime(Date orderTeachTime) {
        this.orderTeachTime = orderTeachTime;
    }

    public Long getSid() {
        return sid;
    }

    public void setSid(Long sid) {
        this.sid = sid;
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
