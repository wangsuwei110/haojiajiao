package com.education.hjj.bz.service;

import com.education.hjj.bz.entity.vo.PageVo;
import com.education.hjj.bz.entity.vo.StudentDemandVo;
import com.education.hjj.bz.entity.vo.StudentDetailVo;
import com.education.hjj.bz.entity.vo.TeacherVo;
import com.education.hjj.bz.formBean.StudentDemandForm;
import com.education.hjj.bz.formBean.StudentDetailForm;

import java.util.List;
import java.util.Map;

public interface StudentDetailService {

	/**
	 * 查询学生信息
	 **/
	PageVo<List<StudentDetailVo>> queryStudentDetail(StudentDetailForm form);
}
