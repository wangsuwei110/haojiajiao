package com.education.hjj.bz.service;

import java.util.List;

import com.education.hjj.bz.entity.TeacherAccountOperateLogPo;
import com.education.hjj.bz.entity.vo.TeacherAccountOperateLogVo;
import com.education.hjj.bz.formBean.TeacherAccountLogForm;

public interface UserAccountLogService {

	int insertUserAccountLog(TeacherAccountOperateLogPo teacherAccountOperateLogPo);
	
	List<TeacherAccountOperateLogVo> queryUserAccountLogList(TeacherAccountLogForm teacherAccountLogForm);
	
	TeacherAccountOperateLogVo queryUserAccountLogDetail(Integer paymentId);
	
	List<TeacherAccountOperateLogVo> queryUserAccountLogListByEducational(TeacherAccountLogForm teacherAccountLogForm);
}
