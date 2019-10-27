package com.education.hjj.bz.formBean;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;
import java.util.Date;
/**
 * 学员表Form
 *
 * @author caohuan
 *
 * @创建者：sys
 * @创建时间：2019-9-29 0:30:25
 */
public class StudentForm implements Serializable {


    private Long sid;

    @ApiModelProperty(value = "查询类型，1：代表查询所有",required = true)
    private Integer findType;

    @ApiModelProperty(value = "学员姓名",required = true)
    @NotBlank(message = "学员姓名不能为空")
    private String studentName;

    @ApiModelProperty(value = "性别（0未知，1男，2女）",required = true)
    @NotBlank(message = "性别（0未知，1男，2女）不能为空")
    private Integer sex;

    @ApiModelProperty(value = "学员年级",required = true)
    @NotBlank(message = "学员年级不能为空")
    private Integer grade;

    @ApiModelProperty(value = "科目",required = true)
    private Integer subjectId;

    @ApiModelProperty(value = "微信openid",required = true)
    @NotBlank(message = "微信openid不能为空")
    private String openId;

    @ApiModelProperty(value = "头像",required = true)
    @NotBlank(message = "头像不能为空")
    private String picture;

    @ApiModelProperty(value = "家长手机号",required = true)
    @NotBlank(message = "家长手机号不能为空")
    private String parentPhoneNum;

    @ApiModelProperty(value = "状态",required = true)
    @NotBlank(message = "状态不能为空")
    private Integer deleteStatus;

    @ApiModelProperty(value = "创建时间",required = true)
    @NotBlank(message = "创建时间不能为空")
    private Date createTime;

    @ApiModelProperty(value = "创建人",required = true)
    @NotBlank(message = "创建人不能为空")
    private String createUser;

    @ApiModelProperty(value = "修改时间",required = true)
    private Date updateTime;

    @ApiModelProperty(value = "修改人",required = true)
    private String updateUser;

    public Integer getFindType() {
        return findType;
    }

    public void setFindType(Integer findType) {
        this.findType = findType;
    }

    public Integer getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Integer subjectId) {
        this.subjectId = subjectId;
    }

    public Long getSid() {
        return sid;
    }

    public void setSid(Long sid) {
        this.sid = sid;
    }

    public Integer getDeleteStatus() {
        return deleteStatus;
    }

    public void setDeleteStatus(Integer deleteStatus) {
        this.deleteStatus = deleteStatus;
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

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getParentPhoneNum() {
        return parentPhoneNum;
    }

    public void setParentPhoneNum(String parentPhoneNum) {
        this.parentPhoneNum = parentPhoneNum;
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
