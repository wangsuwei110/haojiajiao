package com.education.hjj.bz.entity;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;

public class BasePo implements Serializable{

/**
	 * 
	 */
	private static final long serialVersionUID = -4852009126821444728L;

	private Date createTime;
	
	private String createUser;
	
	private Date updateTime;
	
	private String updateUser;
	
	private Integer status;
	
	@ApiModelProperty(value = "每页记录数")
    @NotNull(message = "每页记录数不能为空")
    private Integer pageSize = 20;
    @ApiModelProperty(value = "页码，从1开始")
    @NotNull(message = "页码")
    private Integer pageIndex = 1;

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }

    public Integer getOffset() {
        return (pageSize != null && pageIndex != null) ? (pageIndex - 1) * pageSize :null;
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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
}
