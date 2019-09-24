package com.education.hjj.bz.service.impl;

import com.education.hjj.bz.entity.vo.PageVo;
import com.education.hjj.bz.entity.vo.StudentDetailVo;
import com.education.hjj.bz.entity.vo.TeacherVo;
import com.education.hjj.bz.formBean.StudentDemandForm;
import com.education.hjj.bz.formBean.StudentDetailForm;
import com.education.hjj.bz.mapper.StudentDetailMapper;
import com.education.hjj.bz.mapper.SubjectMapper;
import com.education.hjj.bz.service.StudentDetailService;
import com.education.hjj.bz.service.SubjectService;
import com.education.hjj.bz.util.ApiResponse;
import com.github.pagehelper.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 * @author dolyw.com
 * @date 2018/8/9 15:45
 */
@Service
public class StudentDetailServiceImpl implements StudentDetailService {
	
	@Autowired
	private StudentDetailMapper studentDetailMapper;


	@Override
	public PageVo<List<StudentDetailVo>> queryStudentDetail(StudentDetailForm form) {
		PageVo pageVo = new PageVo();
		Integer count = studentDetailMapper.countStudent(form);
		if (count != null && count > 0) {
			pageVo.setDataList(studentDetailMapper.listStudentDetail(form));
			pageVo.setTotal(count);
		} else {
			pageVo.setTotal(0);
		}

		return pageVo;
	}
}
