package com.education.hjj.bz.service;

import java.util.List;

import com.education.hjj.bz.entity.vo.PageVo;
import com.education.hjj.bz.entity.vo.PointsLogVo;
import com.education.hjj.bz.formBean.PointsLogForm;

public interface PointsLogService {

	PageVo<List<PointsLogVo>> queryAllPointsLogByTeacherId(PointsLogForm pointsLogForm);
}
