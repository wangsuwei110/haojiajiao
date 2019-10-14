package com.education.hjj.bz.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.education.hjj.bz.entity.PointsLogPo;
import com.education.hjj.bz.entity.vo.PointsLogVo;
import com.education.hjj.bz.formBean.PointsLogForm;

@Mapper
public interface PointsLogMapper {

	List<PointsLogVo> queryAllPointsLogByTeacherId(PointsLogForm pointsLogForm);
	
	int addTeacherPointsLog(PointsLogPo pointsLogPo);
}
