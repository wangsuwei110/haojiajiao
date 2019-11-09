package com.education.hjj.bz.entity.vo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
/**
 * 学员发布需求表Vo
 *
 * @创建者：sys
 * @创建时间：2019-9-26 0:51:18
 */
@ApiModel(value = "StudentDemand")
public class StudentDemandVo {


    @ApiModelProperty(value = "需求主键id")
    private Integer sid;

    @ApiModelProperty(value = "学员ID")
    private Long studentId;

    @ApiModelProperty(value = "学员姓名")
    private String studentName;

    @ApiModelProperty(value = "上课地址")
    private String demandAddress;

    @ApiModelProperty(value = "补习年级")
    private Integer demandGrade;

    @ApiModelProperty(value = "辅导科目id")
    private Integer subjectId;

    @ApiModelProperty(value = "年级和科目")
    private String gradeSubject;

    @ApiModelProperty(value = "付费订单开始时间")
    private Date orderStart;

    @ApiModelProperty(value = "订单周数")
    private Integer weekNum;

    @ApiModelProperty(value = "每周上课次数")
    private Integer classNum;

    @ApiModelProperty(value = "每周上课时间范围")
    private String timeRange;

    @ApiModelProperty(value = "订单类型,1:试讲订单,2:付费订单")
    private Integer orderType;

    @ApiModelProperty(value = "订单金额")
    private Float orderMoney;

    @ApiModelProperty(value = "具体需求")
    private String demandDesc;

    @ApiModelProperty(value = "状态 0:未发布，1:发布中;2:已接单;3:结单")
    private Boolean status;

    //订单报名状态
    private Integer demandSignStatus;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "创建人")
    private String createUser;

    @ApiModelProperty(value = "修改时间")
    private Date updateTime;

    @ApiModelProperty(value = "修改人")
    private String updateUser;

    @ApiModelProperty(value = "预约试讲时间")
    private Date orderTeachTime;

    @ApiModelProperty(value = "预约试讲教员的数量")
    private Integer orderTeachCount;

    @ApiModelProperty(value = "预约教员的姓名")
    private String teachName;

    @ApiModelProperty(value = "预约教员通过的评价")
    private String appraise;

    @ApiModelProperty(value = "试讲状态:0:未试讲，1:试讲中;2:试讲通过;3:试讲未通过")
    private Integer subscribeStatus;

    @ApiModelProperty(value = "订单类型：1:单独预约，2:快速请家教")
    private Integer demandType;

    @ApiModelProperty(value = "订单当天周几")
    private Integer currentWeekDay;

    //教学年级名称
    private String teachGradeName;
    //教学科目名称
    private String teachBranchName;
    //报名时间
    private String signTime;
    //联系手机号
    private String parentPhoneNum;

    //订单的报名总人数
    private Integer demandSignUpNum;

    //订单的开始日期
    private String orderStartDate;

    //订单的结束日期
    private String orderEndDate;

    @ApiModelProperty(value = "区域id",required = true)
    private Integer parameterId;

    public Integer getCurrentWeekDay() {
        return currentWeekDay;
    }

    public void setCurrentWeekDay(Integer currentWeekDay) {
        this.currentWeekDay = currentWeekDay;
    }

    public Integer getParameterId() {
        return parameterId;
    }

    public void setParameterId(Integer parameterId) {
        this.parameterId = parameterId;
    }

    public Integer getDemandType() {
        return demandType;
    }

    public void setDemandType(Integer demandType) {
        this.demandType = demandType;
    }

    public String getAppraise() {
        return appraise;
    }

    public void setAppraise(String appraise) {
        this.appraise = appraise;
    }

    public Integer getSubscribeStatus() {
        return subscribeStatus;
    }

    public void setSubscribeStatus(Integer subscribeStatus) {
        this.subscribeStatus = subscribeStatus;
    }

    public String getTeachName() {
        return teachName;
    }

    public void setTeachName(String teachName) {
        this.teachName = teachName;
    }

    public String getGradeSubject() {
        return gradeSubject;
    }

    public void setGradeSubject(String gradeSubject) {
        this.gradeSubject = gradeSubject;
    }

    public Integer getOrderTeachCount() {
        return orderTeachCount;
    }

    public void setOrderTeachCount(Integer orderTeachCount) {
        this.orderTeachCount = orderTeachCount;
    }

    public Date getOrderTeachTime() {
        return orderTeachTime;
    }

    public void setOrderTeachTime(Date orderTeachTime) {
        this.orderTeachTime = orderTeachTime;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public Integer getSid() {
        return sid;
    }

    public void setSid(Integer sid) {
        this.sid = sid;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }
    public String getDemandAddress() {
        return demandAddress;
    }

    public void setDemandAddress(String demandAddress) {
        this.demandAddress = demandAddress;
    }

    public Integer getDemandGrade() {
        return demandGrade;
    }

    public void setDemandGrade(Integer demandGrade) {
        this.demandGrade = demandGrade;
    }

    public Integer getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Integer subjectId) {
        this.subjectId = subjectId;
    }

    public Date getOrderStart() {
        return orderStart;
    }

    public void setOrderStart(Date orderStart) {
        this.orderStart = orderStart;
    }

    public Integer getWeekNum() {
        return weekNum;
    }

    public void setWeekNum(Integer weekNum) {
        this.weekNum = weekNum;
    }

    public Integer getClassNum() {
        return classNum;
    }

    public void setClassNum(Integer classNum) {
        this.classNum = classNum;
    }

    public String getTimeRange() {
        return timeRange;
    }

    public void setTimeRange(String timeRange) {
        this.timeRange = timeRange;
    }
    public Integer getOrderType() {
        return orderType;
    }

    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }
    public Float getOrderMoney() {
        return orderMoney;
    }

    public void setOrderMoney(Float orderMoney) {
        this.orderMoney = orderMoney;
    }
    public String getDemandDesc() {
        return demandDesc;
    }

    public void setDemandDesc(String demandDesc) {
        this.demandDesc = demandDesc;
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

	public String getTeachGradeName() {
		return teachGradeName;
	}

	public void setTeachGradeName(String teachGradeName) {
		this.teachGradeName = teachGradeName;
	}

	public String getTeachBranchName() {
		return teachBranchName;
	}

	public void setTeachBranchName(String teachBranchName) {
		this.teachBranchName = teachBranchName;
	}

	public String getSignTime() {
		return signTime;
	}

	public void setSignTime(String signTime) {
		this.signTime = signTime;
	}

	public String getParentPhoneNum() {
		return parentPhoneNum;
	}

	public void setParentPhoneNum(String parentPhoneNum) {
		this.parentPhoneNum = parentPhoneNum;
	}

	public Integer getDemandSignUpNum() {
		return demandSignUpNum;
	}

	public void setDemandSignUpNum(Integer demandSignUpNum) {
		this.demandSignUpNum = demandSignUpNum;
	}

	public Integer getDemandSignStatus() {
		return demandSignStatus;
	}

	public void setDemandSignStatus(Integer demandSignStatus) {
		this.demandSignStatus = demandSignStatus;
	}

	public String getOrderStartDate() {
		return orderStartDate;
	}

	public void setOrderStartDate(String orderStartDate) {
		this.orderStartDate = orderStartDate;
	}

	public String getOrderEndDate() {
		return orderEndDate;
	}

	public void setOrderEndDate(String orderEndDate) {
		this.orderEndDate = orderEndDate;
	}

}
