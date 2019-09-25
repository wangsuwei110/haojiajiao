package com.education.hjj.bz.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.education.hjj.bz.entity.vo.TeacherAccountVo;

@Mapper
public interface UserAccountMapper {

	TeacherAccountVo queryTeacherAccount(Integer teacherId);
}
