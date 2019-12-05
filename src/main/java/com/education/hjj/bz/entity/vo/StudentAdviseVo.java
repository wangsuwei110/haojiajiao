package com.education.hjj.bz.entity.vo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
/**
 * 校长邮箱(家长建议)表Vo
 *
 * @创建者：sys
 * @创建时间：2019-10-8 22:03:47
 */
@ApiModel(value = "StudentAdvise")
public class StudentAdviseVo {


    @ApiModelProperty(value = "主键id")
    private Long sid;

    @ApiModelProperty(value = "学员id")
    private String studentId;

    @ApiModelProperty(value = "收支说明")
    private String adviseDesc;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "创建人")
    private String createUser;
    
    //学员名称
    private String studentName;
    
    //父母手机号
    private String parentPhoneNum;
    //身份
    private String identity;


    public Long getSid() {
        return sid;
    }

    public void setSid(Long sid) {
        this.sid = sid;
    }
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

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public String getParentPhoneNum() {
		return parentPhoneNum;
	}

	public void setParentPhoneNum(String parentPhoneNum) {
		this.parentPhoneNum = parentPhoneNum;
	}

	public String getIdentity() {
		return identity;
	}

	public void setIdentity(String identity) {
		this.identity = identity;
	}



}
