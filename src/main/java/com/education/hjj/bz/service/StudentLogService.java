package com.education.hjj.bz.service;

import java.util.List;

import com.education.hjj.bz.entity.StudentLogPo;
import com.education.hjj.bz.entity.vo.StudentLogVo;

public interface StudentLogService {
	
	//教员端首页查看学员日志
	List<StudentLogVo> queryStudentLogByHome10();
	
	//新增学员日志
	int addStudentLog(StudentLogPo studentLogPo);

}
