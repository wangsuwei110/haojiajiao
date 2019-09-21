package com.education.hjj.bz.formBean;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

/**
 * Created by leo on 2017/9/4.
 */
public class PageForm {

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
}
