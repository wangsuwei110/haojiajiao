package com.education.hjj.bz.formBean;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;
/**
 * 需求日志表Form
 *
 * @创建者：sys
 * @创建时间：2019-11-10 23:32:09
 */
public class DemandLogForm implements Serializable {


    @ApiModelProperty(value = "主键id",required = true)
    private Long sid;

    @ApiModelProperty(value = "需求ID",required = true)
    private Integer demandId;

    @ApiModelProperty(value = "修改前的需求详情",required = true)
    private String mark;

    @ApiModelProperty(value = "创建时间",required = true)
    private Date createTime;

    @ApiModelProperty(value = "创建人",required = true)
    private String createUser;

    public Integer getDemandId() {
        return demandId;
    }

    public void setDemandId(Integer demandId) {
        this.demandId = demandId;
    }

    public Long getSid() {
        return sid;
    }

    public void setSid(Long sid) {
        this.sid = sid;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
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



}
