package com.education.hjj.bz.except;

import com.education.hjj.bz.enums.ErrorEnum;

/**
 * Copyright: Copyright (c) 2018
 *
 * @Description: 共通异常处理
 * @version: v1.0.0
 * @author: my
 * @date: 2018/11/12 9:53
 * <p>
 * Modification History:
 * Date         Author          Version            Description
 * ---------------------------------------------------------*
 * 2018/11/12     my           v1.0.0               修改原因
 */
public class ApiException extends RuntimeException {
    private String errCode;
    private String errMsg;

    public String getErrCode() {
        return errCode;
    }

    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public ApiException() {
        super();
    }

    public ApiException(String errCode, String errMsg){
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    public ApiException(ErrorEnum responseEnum) {
        this.errCode = responseEnum.getCode();
        this.errMsg = responseEnum.getMsg();
    }

    public ApiException(String code, String msg, Throwable cause) {
        super(null, cause);
        this.errCode = code;
        this.errMsg = msg;
    }

    @Override
    public String getMessage() {
        return getErrMsg()
                + "\n[errCode]: " + getErrCode()
                + "\n[errMsg]: " + getErrMsg();
    }
}
