package com.education.hjj.bz.formBean;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;
import java.util.Date;
/**
 * 教学学段表Form
 *
 * @创建者：sys
 * @创建时间：2019-10-13 13:31:41
 */
public class TeachLevelForm extends PageForm implements Serializable{


    /**
	 * 
	 */
	private static final long serialVersionUID = -3526095026006391246L;

	@ApiModelProperty(value = "教学学段表主键id",required = true)
    private Long teachLevelId;

    @ApiModelProperty(value = "教学学段名称",required = true)
    private String teachLevelName;

    @ApiModelProperty(value = "状态",required = true)
    private Boolean status;

    @ApiModelProperty(value = "创建时间",required = true)
    private Date createTime;

    @ApiModelProperty(value = "创建人",required = true)
    private String createUser;

    @ApiModelProperty(value = "修改时间",required = true)
    private Date updateTime;

    @ApiModelProperty(value = "修改人",required = true)
    private String updateUser;
    
    private Integer teacherId;


    public Long getTeachLevelId() {
        return teachLevelId;
    }

    public void setTeachLevelId(Long teachLevelId) {
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

	public Integer getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(Integer teacherId) {
		this.teacherId = teacherId;
	}

}
