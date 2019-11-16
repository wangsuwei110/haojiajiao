package com.education.hjj.bz.entity.vo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;

/**
 * 课程详情表Vo
 *
 * @创建者：sys
 * @创建时间：2019-11-10 2:02:14
 */
@ApiModel(value = "DemandCourseInfo")
public class DemandCourseInfoVo {


    @ApiModelProperty(value = "主键id")
    private Long sid;

    @ApiModelProperty(value = "需求ID")
    private Long demandId;

    @ApiModelProperty(value = "教员id")
    private Long teacherId;

    @ApiModelProperty(value = "学员id")
    private Long studentId;

    @ApiModelProperty(value = "上课状态:未结课:1, 结课:2")
    private Boolean status;

    @ApiModelProperty(value = "上课时间")
    private Date orderTeachTime;

    @ApiModelProperty(value = "周几")
    private Long weekNum;

    @ApiModelProperty(value = "时段，1:早上，2：下午，3：晚上")
    private Long timeNum;

    @ApiModelProperty(value = "状态")
    private Boolean deleteStatus;

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

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public Long getSid() {
        return sid;
    }

    public void setSid(Long sid) {
        this.sid = sid;
    }
    public Long getDemandId() {
        return demandId;
    }

    public void setDemandId(Long demandId) {
        this.demandId = demandId;
    }
    public Long getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Long teacherId) {
        this.teacherId = teacherId;
    }
    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }
    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
    public Date getOrderTeachTime() {
        return orderTeachTime;
    }

    public void setOrderTeachTime(Date orderTeachTime) {
        this.orderTeachTime = orderTeachTime;
    }
    public Long getWeekNum() {
        return weekNum;
    }

    public void setWeekNum(Long weekNum) {
        this.weekNum = weekNum;
    }
    public Long getTimeNum() {
        return timeNum;
    }

    public void setTimeNum(Long timeNum) {
        this.timeNum = timeNum;
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