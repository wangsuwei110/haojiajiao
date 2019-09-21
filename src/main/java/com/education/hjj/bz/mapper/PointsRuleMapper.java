package com.education.hjj.bz.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.education.hjj.bz.entity.vo.TeacherPointsRuleVo;

@Mapper
public interface PointsRuleMapper {

	List<TeacherPointsRuleVo> queryAllPointsRules();
}
