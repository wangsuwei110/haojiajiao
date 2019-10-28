package com.education.hjj.bz.formBean;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 教学学段表Form
 *
 * @创建者：sys
 * @创建时间：2019-10-13 13:31:41
 */
public class TeachScreenForm {


    @ApiModelProperty(value = "教学学段等级",required = true)
    private Integer teachLevel;

    @ApiModelProperty(value = "教学学段下的年级",required = true)
    private Integer teachGrade;

    public Integer getTeachLevel() {
        return teachLevel;
    }

    public void setTeachLevel(Integer teachLevel) {
        this.teachLevel = teachLevel;
    }

    public Integer getTeachGrade() {
        return teachGrade;
    }

    public void setTeachGrade(Integer teachGrade) {
        this.teachGrade = teachGrade;
    }
}
