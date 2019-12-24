package com.education.hjj.bz.service;

import java.util.List;

import com.education.hjj.bz.entity.vo.StudentDemandConnectVo;
import com.education.hjj.bz.entity.vo.StudentDemandVo;

public interface StudentDemandConnectService {
	
	int queryServiceForStudentSuccess(Integer teacherId);
	
    List<StudentDemandConnectVo> queryStudentAppraiseForTeacher(Integer teacherId);
    
    List<StudentDemandVo> queryServiceForStudentByTeacherId(Integer teacherId);
    
    String queryAppraiseCountByTeacherId(String teacherId);
}
