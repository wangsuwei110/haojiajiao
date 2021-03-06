package com.education.hjj.bz.formBean;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;
import java.util.Date;

/**
 * 学员表Form
 *
 * @author caohuan
 *
 * @创建者：sys
 * @创建时间：2019-9-29 0:30:25
 */
public class TeacherScreenForm extends PageForm implements Serializable {


    @ApiModelProperty(value = "学员Id",required = true)
    private Integer studentId;

    @ApiModelProperty(value = "科目Id",required = true)
    private Integer subjectId;

    @ApiModelProperty(value = "区域Id",required = true)
    private Integer addressId;

    @ApiModelProperty(value = "大学名称id",required = true)
    private Integer schoolId;

    @ApiModelProperty(value = "是否在校, 0:未毕业,1:已毕业",required = true)
    private Integer type;

    @ApiModelProperty(value = "性别, 0未知，1男，2女",required = true)
    private Integer sex;

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public Integer getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Integer subjectId) {
        this.subjectId = subjectId;
    }

    public Integer getAddressId() {
        return addressId;
    }

    public void setAddressId(Integer addressId) {
        this.addressId = addressId;
    }

    public Integer getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(Integer schoolId) {
        this.schoolId = schoolId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }
}
