package com.education.hjj.bz.entity.vo;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

/**
 * 课程信息表
 * @author dolyw.com
 * @date 2018/8/30 10:34
 */
public class SubjectVo extends BaseVo implements Serializable{
    private static final long serialVersionUID = 3342723124953988436L;
    /**
     * 科目id
     */
    @ApiModelProperty(name = "课程id")
    private Long sid;

    /**
     * 科目英文名
     */
    @ApiModelProperty(name = "科目英文名")
    private String nameEg;

    /**
     * 科目中文名
     */
    @ApiModelProperty(name = "科目中文名")
    private String nameCn;

    /**
     * 科目描述
     */
    @ApiModelProperty(name = "科目描述")
    private String detail;

    public Long getSid() {
        return sid;
    }

    public void setSid(Long sid) {
        this.sid = sid;
    }

    public String getNameEg() {
        return nameEg;
    }

    public void setNameEg(String nameEg) {
        this.nameEg = nameEg;
    }

    public String getNameCn() {
        return nameCn;
    }

    public void setNameCn(String nameCn) {
        this.nameCn = nameCn;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 创建时间
     */
    private Date createTime;
    

}
