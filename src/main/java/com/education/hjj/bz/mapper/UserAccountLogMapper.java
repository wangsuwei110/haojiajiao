package com.education.hjj.bz.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.education.hjj.bz.entity.TeacherAccountOperateLogPo;
import com.education.hjj.bz.entity.vo.TeacherAccountOperateLogVo;
import com.education.hjj.bz.formBean.TeacherAccountLogForm;

import org.springframework.transaction.annotation.Transactional;

@Mapper
public interface UserAccountLogMapper {

	@Transactional
	int insertUserAccountLog(TeacherAccountOperateLogPo teacherAccountOperateLogPo);
	
	List<TeacherAccountOperateLogVo> queryUserAccountLogList(TeacherAccountOperateLogPo teacherAccountOperateLogPo);

	List<TeacherAccountOperateLogVo> listPayLog(TeacherAccountOperateLogPo paymentStreamId);

	TeacherAccountOperateLogVo queryUserAccountLogDetail(Integer paymentId);
	
	List<TeacherAccountOperateLogVo> queryUserAccountLogListByEducational(TeacherAccountLogForm teacherAccountLogForm);
	
	List<TeacherAccountOperateLogVo> queryStudentDemandAccountLogList(TeacherAccountOperateLogPo teacherAccountOperateLogPo);
}
