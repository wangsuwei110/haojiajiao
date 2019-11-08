package com.education.hjj.bz.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.education.hjj.bz.entity.StudentLogPo;
import com.education.hjj.bz.entity.vo.StudentLogVo;

@Mapper
public interface StudentLogMapper {
	
	//教员端首页查看学员日志
	List<StudentLogVo> queryStudentLogByHome10();
	
	//新增学员日志
	int addStudentLog(StudentLogPo studentLogPo);

}
