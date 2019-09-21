package com.education.hjj.bz.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;


public final class ServiceResult<T> {

	private static final long serialVersionUID = 6977558218691386450L;
    private boolean succeed = true;
    private int code;
    private int subCode;
    private String msg;
    private T data;
    private Map<String, Object> additionalProperties;
 
    public ServiceResult() {
        this.code = Constant.SUCCESS;
        this.subCode = Constant.SUCCESS;
        this.additionalProperties = new HashMap();
    }
 
    public static ServiceResult<Boolean> error() {
        return error(500, "未知异常，请联系管理员");
    }
 
    public static ServiceResult<Boolean> error(String msg) {
        return error(500, msg);
    }
 
    public static ServiceResult<Boolean> error(int subCode, String msg) {
        ServiceResult result = new ServiceResult();
        result.setCode(Constant.FAILED);
        result.setSubCode(subCode);
        result.setSucceed(false);
        result.setMsg(msg);
        return result;
    }
 
    public static ServiceResult ok() {
        return ok(Constant.SUCCESS, "成功");
    }
 
    public static ServiceResult ok(int code, String msg) {
        ServiceResult result = new ServiceResult();
        result.setCode(code);
        result.setSucceed(true);
        result.setMsg(msg);
        return result;
    }
 
    public static ServiceResult ok(Object data) {
        ServiceResult d = new ServiceResult();
        d.setSucceed(true);
        d.setData(data);
        d.setCode(Constant.SUCCESS);
        d.setMsg("成功");
        return d;
    }
 
    public static ServiceResult ok(Object data, Map<String, Object> additionalProperties) {
        ServiceResult d = new ServiceResult();
        d.setSucceed(true);
        d.setData(data);
        d.setCode(Constant.SUCCESS);
        d.setMsg("成功");
        d.additionalProperties.putAll(additionalProperties);
        return d;
    }
 
    public ServiceResult(T data) {
        this.code = Constant.SUCCESS;
        this.subCode = Constant.SUCCESS;
        this.additionalProperties = new HashMap();
        this.data = data;
    }
 
    public ServiceResult(boolean succeed, int code, String msg) {
        this.code = Constant.SUCCESS;
        this.subCode = Constant.SUCCESS;
        this.additionalProperties = new HashMap();
        this.succeed = succeed;
        this.code = code;
        this.msg = msg;
    }
 
    public ServiceResult(boolean succeed, T data, String msg) {
        this.code = Constant.SUCCESS;
        this.subCode = Constant.SUCCESS;
        this.additionalProperties = new HashMap();
        this.succeed = succeed;
        this.data = data;
        this.msg = msg;
    }
 
    public ServiceResult(boolean succeed, T data, int code, String msg) {
        this.code = Constant.SUCCESS;
        this.subCode = Constant.SUCCESS;
        this.additionalProperties = new HashMap();
        this.succeed = succeed;
        this.data = data;
        this.code = code;
        this.msg = msg;
    }
 
    public ServiceResult(boolean succeed, String msg) {
        this.code = Constant.SUCCESS;
        this.subCode = Constant.SUCCESS;
        this.additionalProperties = new HashMap();
        this.succeed = succeed;
        this.msg = msg;
    }
 
    public boolean isSucceed() {
        return this.succeed;
    }
 
    public void setSucceed(boolean succeed) {
        this.succeed = succeed;
    }
 
    public T getData() {
        return this.data;
    }
 
    public void setData(T data) {
        this.data = data;
    }
 
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
 
    public String getMsg() {
        return this.msg;
    }
 
    public void setMsg(String msg) {
        this.msg = msg;
    }
 
    public int getCode() {
        return this.code;
    }
 
    public void setCode(int code) {
        this.code = code;
    }
 
    public int getSubCode() {
        return this.subCode;
    }
 
    public void setSubCode(int subCode) {
        this.subCode = subCode;
    }
 
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }
 
    public void setAdditionalProperties(String name, Object value) {
        this.additionalProperties.put(name, value);
    }
 
    public Object getAdditionalProperties(String name) {
        return this.additionalProperties.get(name);
    }
}
