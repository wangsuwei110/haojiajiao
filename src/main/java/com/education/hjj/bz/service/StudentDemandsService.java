package com.education.hjj.bz.service;

import java.util.List;
import java.util.Map;

import com.education.hjj.bz.entity.vo.PageVo;
import com.education.hjj.bz.entity.vo.StudentDemandVo;
import com.education.hjj.bz.entity.vo.TeacherVo;
import com.education.hjj.bz.formBean.StudentDemandForm;
import com.education.hjj.bz.util.ApiResponse;

public interface StudentDemandsService {

	PageVo<List<StudentDemandVo>> queryStudentDemands(StudentDemandForm studentDemandForm);
	
	Map<String,Object> queryStudentDemandDetail(String demandId);

	ApiResponse addStudentDemandByTeacher(StudentDemandForm form);
}
