package com.education.hjj.bz.service.impl;

import com.education.hjj.bz.entity.vo.PageVo;
import com.education.hjj.bz.mapper.SubjectMapper;
import com.education.hjj.bz.service.SubjectService;
import com.education.hjj.bz.util.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author dolyw.com
 * @date 2018/8/9 15:45
 */
@Service
public class SubjectServiceImpl implements SubjectService {
	
	@Autowired
	private SubjectMapper subjectMapper;

	@Override
	public ApiResponse findSubjects() {

		Integer count = subjectMapper.countSubject();

		if (count < 1) {
			return ApiResponse.error("没有课程信息");
		}

		return  ApiResponse.success(new PageVo(count, subjectMapper.listSubject()));
	}
}
