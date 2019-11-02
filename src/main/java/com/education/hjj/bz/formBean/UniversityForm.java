package com.education.hjj.bz.formBean;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;

import java.util.Date;
/**
 * 大学表Form
 *
 * @创建者：sys
 * @创建时间：2019-11-2 10:31:18
 */
public class UniversityForm extends PageForm{


    @ApiModelProperty(value = "主键id",required = true)
    @NotBlank(message = "主键id不能为空")
    private Long sid;

    @ApiModelProperty(value = "大学名称",required = true)
    @NotBlank(message = "大学名称不能为空")
    private String universityName;

    @ApiModelProperty(value = "所属省份",required = true)
    @NotBlank(message = "所属省份不能为空")
    private String province;

    @ApiModelProperty(value = "创建时间",required = true)
    private Date createTime;

    @ApiModelProperty(value = "创建人",required = true)
    private String createUser;

    @ApiModelProperty(value = "修改时间",required = true)
    private Date updateTime;

    @ApiModelProperty(value = "修改人",required = true)
    private String updateUser;


    public Long getSid() {
        return sid;
    }

    public void setSid(Long sid) {
        this.sid = sid;
    }
    public String getUniversityName() {
        return universityName;
    }

    public void setUniversityName(String universityName) {
        this.universityName = universityName;
    }
    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
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
