package com.education.hjj.bz.formBean;

import io.swagger.annotations.ApiModelProperty;

/**
 * 教学学段表Form
 *
 * @创建者：sys
 * @创建时间：2019-10-13 13:31:41
 */
public class TeachUniversityForm {


    @ApiModelProperty(value = "学校名或者省份信息",required = true)
    private String schoolAndProvince;


    public String getSchoolAndProvince() {
        return schoolAndProvince;
    }

    public void setSchoolAndProvince(String schoolAndProvince) {
        this.schoolAndProvince = schoolAndProvince;
    }
}
