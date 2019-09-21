package com.education.hjj.bz.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.education.hjj.bz.entity.ParameterPo;
import com.education.hjj.bz.entity.vo.ParameterVo;

@Mapper
public interface ParameterMapper {

	List<ParameterVo> queryParameterLists();
	
	List<ParameterVo> queryParameterListsByType(Integer parameterType);
	
	List<ParameterVo> queryParameterListsByTypes(List<ParameterPo> parameterTypelist);
	
	List<ParameterVo> queryParameterListsByParentId(Integer parentId);
	
	ParameterVo queryParameterById(Integer parameterId);
}
