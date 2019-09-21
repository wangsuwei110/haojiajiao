package com.education.hjj.bz.service;

import java.util.List;

import com.education.hjj.bz.entity.vo.ParameterVo;

public interface QueryParameterService {
	
	List<ParameterVo> queryParameterLists();
	
	List<ParameterVo> queryParameterListsByType(Integer parameterType);
	
	List<ParameterVo> queryParameterListsByTypes(List<ParameterVo> parameterTypelist);
}
