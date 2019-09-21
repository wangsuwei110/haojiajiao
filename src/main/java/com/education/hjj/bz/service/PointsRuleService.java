package com.education.hjj.bz.service;

import java.util.List;

import com.education.hjj.bz.entity.vo.PageVo;
import com.education.hjj.bz.entity.vo.TeacherPointsRuleVo;

public interface PointsRuleService {

	PageVo<List<TeacherPointsRuleVo>> queryAllPointsRules();
}
