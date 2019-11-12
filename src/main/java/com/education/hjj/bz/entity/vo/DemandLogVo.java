package com.education.hjj.bz.entity.vo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
/**
 * 需求日志表Vo
 *
 * @创建者：sys
 * @创建时间：2019-11-10 23:32:09
 */
@ApiModel(value = "DemandLog")
public class DemandLogVo {


    @ApiModelProperty(value = "主键id")
    private Long sid;

    @ApiModelProperty(value = "需求ID")
    private Long demandId;

    @ApiModelProperty(value = "修改前的需求详情")
    private String mark;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "创建人")
    private String createUser;


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
