package com.education.hjj.bz.entity.vo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
/**
 * 教学学段表Vo
 *
 * @创建者：sys
 * @创建时间：2019-10-13 13:31:41
 */
@ApiModel(value = "TeachLevel")
public class TeachLevelVo {


    @ApiModelProperty(value = "教学学段表主键id")
    private Integer teachLevelId;

    @ApiModelProperty(value = "教学学段名称")
    private String teachLevelName;

    @ApiModelProperty(value = "状态")
    private Boolean status;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "创建人")
    private String createUser;

    @ApiModelProperty(value = "修改时间")
    private Date updateTime;

    @ApiModelProperty(value = "修改人")
    private String updateUser;


    public Integer getTeachLevelId() {
        return teachLevelId;
    }

    public void setTeachLevelId(Integer teachLevelId) {
        this.teachLevelId = teachLevelId;
    }

    public String getTeachLevelName() {
        return teachLevelName;
    }

    public void setTeachLevelName(String teachLevelName) {
        this.teachLevelName = teachLevelName;
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
