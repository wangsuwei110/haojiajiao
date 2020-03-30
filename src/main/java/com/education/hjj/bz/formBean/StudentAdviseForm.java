package com.education.hjj.bz.formBean;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;
import java.util.Date;
/**
 * 校长邮箱(家长建议)表Form
 *
 * @创建者：sys
 * @创建时间：2019-10-8 22:03:47
 */
public class StudentAdviseForm implements Serializable{
	
	

    /**
	 * 
	 */
	private static final long serialVersionUID = 864947199388530457L;

	@ApiModelProperty(value = "学员id",required = true)
    @NotBlank(message = "学员id不能为空")
    private String studentId;

    @ApiModelProperty(value = "家长建议",required = true)
    private String adviseDesc;

    @ApiModelProperty(value = "创建时间",required = true)
    private Date createTime;

    @ApiModelProperty(value = "创建人",required = true)
    @NotBlank(message = "创建人不能为空")
    private String createUser;
    
    private Integer isContact;
    
    private String parentPhoneNum;
    
    private Integer type;


    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }
    public String getAdviseDesc() {
        return adviseDesc;
    }

    public void setAdviseDesc(String adviseDesc) {
        this.adviseDesc = adviseDesc;
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

	public Integer getIsContact() {
		return isContact;
	}

	public void setIsContact(Integer isContact) {
		this.isContact = isContact;
	}

	public String getParentPhoneNum() {
		return parentPhoneNum;
	}

	public void setParentPhoneNum(String parentPhoneNum) {
		this.parentPhoneNum = parentPhoneNum;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
    
}
