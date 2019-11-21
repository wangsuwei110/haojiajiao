package com.education.hjj.bz.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.education.hjj.bz.entity.TeacherAccountPo;
import com.education.hjj.bz.entity.vo.TeacherAccountVo;

@Mapper
public interface UserAccountMapper {

	int insertTeacherAccount(TeacherAccountPo teacherAccountPo);

	TeacherAccountVo queryTeacherAccount(Integer teacherId);
	
	int updateTeacherAccount(TeacherAccountPo teacherAccountPo);

	int updateTeacherAccountMoney(TeacherAccountPo teacherAccountPo);

}
