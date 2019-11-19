package com.education.hjj.bz.service;

import java.util.List;

import com.education.hjj.bz.entity.TeacherAccountOperateLogPo;
import com.education.hjj.bz.entity.vo.TeacherAccountOperateLogVo;

public interface UserAccountLogService {

	int insertUserAccountLog(TeacherAccountOperateLogPo teacherAccountOperateLogPo);
	
	List<TeacherAccountOperateLogVo> queryUserAccountLogList(TeacherAccountOperateLogPo teacherAccountOperateLogPo);
	
	TeacherAccountOperateLogVo queryUserAccountLogDetail(Integer paymentId);
}
