package com.education.hjj.bz.service;

import com.education.hjj.bz.entity.TeacherAccountPo;
import com.education.hjj.bz.entity.vo.TeacherAccountVo;

public interface UserAccountService {

	TeacherAccountVo queryTeacherAccount(Integer teacherId);
	
	int updateTeacherAccount(TeacherAccountPo teacherAccountPo);
	
}
