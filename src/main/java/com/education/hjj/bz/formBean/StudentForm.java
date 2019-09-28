package com.education.hjj.bz.formBean;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;
import java.util.Date;
/**
 * 学员表Form
 *
 * @创建者：sys
 * @创建时间：2019-9-29 0:30:25
 */
public class StudentForm implements Serializable {

    @ApiModelProperty(value = "学员姓名",required = true)
    @NotBlank(message = "学员姓名不能为空")
    private String studentName;

    @ApiModelProperty(value = "性别（0未知，1男，2女）",required = true)
    @NotBlank(message = "性别（0未知，1男，2女）不能为空")
    private Boolean sex;

    @ApiModelProperty(value = "学员年级",required = true)
    @NotBlank(message = "学员年级不能为空")
    private Long grade;

    @ApiModelProperty(value = "微信openid",required = true)
    @NotBlank(message = "微信openid不能为空")
    private String openid;

    @ApiModelProperty(value = "头像",required = true)
    @NotBlank(message = "头像不能为空")
    private String picture;

    @ApiModelProperty(value = "家长姓名",required = true)
    @NotBlank(message = "家长姓名不能为空")
    private String parentName;

    @ApiModelProperty(value = "家长手机号",required = true)
    @NotBlank(message = "家长手机号不能为空")
    private String parentPhoneNum;

    @ApiModelProperty(value = "学员头像",required = true)
    @NotBlank(message = "学员头像不能为空")
    private String url;

    @ApiModelProperty(value = "学员账号",required = true)
    @NotBlank(message = "学员账号不能为空")
    private String studentAccount;

    @ApiModelProperty(value = "学员密码",required = true)
    @NotBlank(message = "学员密码不能为空")
    private String studentPassword;

    @ApiModelProperty(value = "状态",required = true)
    @NotBlank(message = "状态不能为空")
    private Boolean deleteStatus;

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


    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }
    public Boolean getSex() {
        return sex;
    }

    public void setSex(Boolean sex) {
        this.sex = sex;
    }
    public Long getGrade() {
        return grade;
    }

    public void setGrade(Long grade) {
        this.grade = grade;
    }
    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }
    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }
    public String getParentPhoneNum() {
        return parentPhoneNum;
    }

    public void setParentPhoneNum(String parentPhoneNum) {
        this.parentPhoneNum = parentPhoneNum;
    }
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    public String getStudentAccount() {
        return studentAccount;
    }

    public void setStudentAccount(String studentAccount) {
        this.studentAccount = studentAccount;
    }
    public String getStudentPassword() {
        return studentPassword;
    }

    public void setStudentPassword(String studentPassword) {
        this.studentPassword = studentPassword;
    }
    public Boolean getDeleteStatus() {
        return deleteStatus;
    }

    public void setDeleteStatus(Boolean deleteStatus) {
        this.deleteStatus = deleteStatus;
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
