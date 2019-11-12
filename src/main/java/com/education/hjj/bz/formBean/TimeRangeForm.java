package com.education.hjj.bz.formBean;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created by leo on 2017/9/4.
 */
public class TimeRangeForm {

    @ApiModelProperty(value = "起始时间")
    private Date startTime;

    @ApiModelProperty(value = "起始时间")
    private Date endTime;

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}
