package com.education.hjj.bz.formBean;

import com.sun.istack.NotNull;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
/**
 * 学员教员关系表Form
 *
 * @创建者：sys
 * @创建时间：2019-10-12 0:00:01
 */
public class StudentConnectTeacherForm {

    @ApiModelProperty(value = "学员id",required = true)
    @NotNull
    private Integer studentId;

    @ApiModelProperty(value = "教员id",required = true)
    @NotNull
    private Integer teacherId;

    @ApiModelProperty(value = "状态1:删除，0：有效",required = true)
    private Integer deleteStatus;

    @ApiModelProperty(value = "创建时间",required = true)
    private Date createTime;

    @ApiModelProperty(value = "创建人",required = true)
    private Integer createUser;

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public Integer getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Integer teacherId) {
        this.teacherId = teacherId;
    }

    public Integer getDeleteStatus() {
        return deleteStatus;
    }

    public void setDeleteStatus(Integer deleteStatus) {
        this.deleteStatus = deleteStatus;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Integer createUser) {
        this.createUser = createUser;
    }
}
