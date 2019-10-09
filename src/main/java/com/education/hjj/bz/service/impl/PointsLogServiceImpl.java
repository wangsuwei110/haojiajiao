package com.education.hjj.bz.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.education.hjj.bz.entity.vo.PageVo;
import com.education.hjj.bz.entity.vo.PointsLogVo;
import com.education.hjj.bz.formBean.PointsLogForm;
import com.education.hjj.bz.mapper.PointsLogMapper;
import com.education.hjj.bz.service.PointsLogService;

@Service
public class PointsLogServiceImpl implements PointsLogService{
	
	@Autowired
	private PointsLogMapper pointsLogMapper;

	@Override
	public PageVo<List<PointsLogVo>> queryAllPointsLogByTeacherId(PointsLogForm pointsLogForm) {
		
		PageVo pageVo = new PageVo();
		
		List<PointsLogVo> list = pointsLogMapper.queryAllPointsLogByTeacherId(pointsLogForm);
		
		pageVo.setDataList(list);
		pageVo.setTotal(list.size());
		return pageVo;
	}

}
