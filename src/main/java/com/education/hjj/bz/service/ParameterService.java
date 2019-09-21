package com.education.hjj.bz.service;

import java.util.List;
import java.util.Map;

import com.education.hjj.bz.entity.ParameterPo;
import com.education.hjj.bz.entity.vo.ParameterVo;

public interface ParameterService {

	List<ParameterVo> queryParameterListsByParentId(String parentId);
	
	List<ParameterVo> queryParameterListsByTypes(String parameterIds);
}
