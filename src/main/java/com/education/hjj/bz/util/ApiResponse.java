package com.education.hjj.bz.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.education.hjj.bz.enums.ErrorEnum;

import io.swagger.annotations.ApiModelProperty;

public class ApiResponse<T> {

	private static Logger logger = LoggerFactory.getLogger(ApiResponse.class);
    @ApiModelProperty("CODE")
    private String code;
    @ApiModelProperty("信息描述")
    private String msg;
    @ApiModelProperty("数据内容")
    private T data;

    public ApiResponse(){}

    public ApiResponse(String code, String msg) {
        this.code = code;
        this.msg = msg;
        if (logger.isInfoEnabled()) {
            logger.info("<===      返回数据 code: {}, msg: {}\n", code, msg);
        }
    }

    public ApiResponse(String code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
        if (logger.isInfoEnabled()) {
            logger.info("<===      返回数据 code: {}, msg: {}\n", code, msg);
        }
    }

    public static ApiResponse success() {
        return new ApiResponse(ErrorEnum.SUC_MSG.getCode(), ErrorEnum.SUC_MSG.getMsg());
    }

    public static ApiResponse success(String msg) {
        return new ApiResponse(ErrorEnum.SUC_MSG.getCode(), msg);
    }
    
    public static ApiResponse success(String msg , Object object) {
        return new ApiResponse(ErrorEnum.SUC_MSG.getCode(), msg , object);
    }

    public static ApiResponse success(Object object) {
        return new ApiResponse(ErrorEnum.SUC_MSG.getCode(), ErrorEnum.SUC_MSG.getMsg(), object);
    }

    public static ApiResponse error() {
        return new ApiResponse(ErrorEnum.SERVICE_MSG.getCode(), ErrorEnum.SERVICE_MSG.getMsg());
    }

    public static ApiResponse error(String message) {
        return new ApiResponse(ErrorEnum.PARAM_MSG.getCode(), message);
    }

    public static ApiResponse errorData(String message, Object object) {
        return new ApiResponse(ErrorEnum.DATA_ERR_MSG.getCode(), message, object);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
