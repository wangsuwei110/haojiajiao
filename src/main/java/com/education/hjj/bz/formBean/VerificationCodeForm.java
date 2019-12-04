package com.education.hjj.bz.formBean;

import java.io.Serializable;
import java.util.Date;

/**
 * 需求日志表Form
 *
 * @创建者：sys
 * @创建时间：2019-11-10 23:32:09
 */
public class VerificationCodeForm implements Serializable {


   private String phone;
   private String code;
   private Date createTime;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
