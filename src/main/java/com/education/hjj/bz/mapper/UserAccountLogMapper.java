package com.education.hjj.bz.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.education.hjj.bz.entity.TeacherAccountOperateLogPo;
import com.education.hjj.bz.entity.vo.TeacherAccountOperateLogVo;

@Mapper
public interface UserAccountLogMapper {

	int insertUserAccountLog(TeacherAccountOperateLogPo teacherAccountOperateLogPo);
	
	List<TeacherAccountOperateLogVo> queryUserAccountLogList(TeacherAccountOperateLogPo teacherAccountOperateLogPo);
	
	TeacherAccountOperateLogVo queryUserAccountLogDetail(Integer paymentId);
}
