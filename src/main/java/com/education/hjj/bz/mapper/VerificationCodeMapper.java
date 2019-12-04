package com.education.hjj.bz.mapper;

import com.education.hjj.bz.formBean.VerificationCodeForm;
import org.apache.ibatis.annotations.Mapper;

/**
 * 年级常量表数据访问接口
 *
 * @创建者：sys
 * @创建时间：2019-10-6 0:20:11
 */
@Mapper
public interface VerificationCodeMapper {

	int insertCode(VerificationCodeForm form);
}
