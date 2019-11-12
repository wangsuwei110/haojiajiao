package com.education.hjj.bz.formBean;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;

import java.util.Date;
/**
 * 课程详情表Form
 *
 * @创建者：sys
 * @创建时间：2019-11-10 2:02:14
 */
public class DemandCourseInfoForm extends PageForm {

    private TimeRangeForm rangeForm;

    @ApiModelProperty(value = "主键id",required = true)
    private Integer sid;

    @ApiModelProperty(value = "需求ID",required = true)
    @NotBlank(message = "需求ID不能为空")
    private Integer demandId;

    @ApiModelProperty(value = "教员id",required = true)
    private Integer teacherId;

    @ApiModelProperty(value = "学员id",required = true)
    @NotBlank(message = "学员id不能为空")
    private Integer studentId;

    @ApiModelProperty(value = "上课状态:未结课:1, 结课:2",required = true)
    private Integer status;

    @ApiModelProperty(value = "上课时间",required = true)
    private Date orderTeachTime;

    @ApiModelProperty(value = "周几",required = true)
    private Integer weekNum;

    @ApiModelProperty(value = "时段，1:早上，2：下午，3：晚上",required = true)
    private Integer timeNum;

    @ApiModelProperty(value = "状态",required = true)
    private Integer deleteStatus;

    @ApiModelProperty(value = "创建时间",required = true)
    private Date createTime;

    @ApiModelProperty(value = "创建人",required = true)
    private String createUser;

    @ApiModelProperty(value = "修改时间",required = true)
    private Date updateTime;

    @ApiModelProperty(value = "修改人",required = true)
    private String updateUser;

    public TimeRangeForm getRangeForm() {
        return rangeForm;
    }

    public void setRangeForm(TimeRangeForm rangeForm) {
        this.rangeForm = rangeForm;
    }

    public Integer getSid() {
        return sid;
    }

    public void setSid(Integer sid) {
        this.sid = sid;
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

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getOrderTeachTime() {
        return orderTeachTime;
    }

    public void setOrderTeachTime(Date orderTeachTime) {
        this.orderTeachTime = orderTeachTime;
    }

    public Integer getWeekNum() {
        return weekNum;
    }

    public void setWeekNum(Integer weekNum) {
        this.weekNum = weekNum;
    }

    public Integer getTimeNum() {
        return timeNum;
    }

    public void setTimeNum(Integer timeNum) {
        this.timeNum = timeNum;
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
