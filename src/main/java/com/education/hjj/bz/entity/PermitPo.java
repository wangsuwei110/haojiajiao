package com.education.hjj.bz.entity;

/**
 * Copyright: Copyright (c) 2018
 *
 * @Description: 该类的功能描述
 * @version: v1.0.0
 * @author: 汝毅
 * @date: 2018/11/7 12:52
 * <p>
 * Modification History:
 * Date         Author          Version            Description
 * ---------------------------------------------------------*
 * 2018/11/7     汝毅           v1.0.0               修改原因
 */
public class PermitPo extends BasePo {
    // 资源ID
    private Long sid;
    // 所属父类
    private Long pId;
    // 资源代码
    private String code;
    // 资源名称
    private String name;
    // 资源URL
    private String urls;
    // 权限级别  1菜单  2按钮
    private int level;

    public Long getSid() {
        return sid;
    }

    public void setSid(Long sid) {
        this.sid = sid;
    }

    public Long getpId() {
        return pId;
    }

    public void setpId(Long pId) {
        this.pId = pId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrls() {
        return urls;
    }

    public void setUrls(String urls) {
        this.urls = urls;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
