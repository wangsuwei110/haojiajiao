package com.education.hjj.bz.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.education.hjj.bz.entity.ParameterPo;
import com.education.hjj.bz.entity.vo.PageVo;
import com.education.hjj.bz.entity.vo.ParameterVo;
import com.education.hjj.bz.mapper.ParameterMapper;
import com.education.hjj.bz.service.QueryParameterService;

@Service
public class QueryParameterServiceImpl implements QueryParameterService{

	@Autowired
	private ParameterMapper parameterMapper;
	
	
	public List<ParameterVo> queryParameterLists(){
		
		List<ParameterVo> list=parameterMapper.queryParameterLists();
		
		
		return list;
	}

	@Override
	public List<ParameterVo> queryParameterListsByType(Integer parameterType) {
		List<ParameterVo> list=parameterMapper.queryParameterListsByType(parameterType);
		return list;
	}

	@Override
	public List<ParameterVo> queryParameterListsByTypes(List<ParameterVo> parameterTypelist) {
		
		List<ParameterPo> polists=new ArrayList<ParameterPo>();
		
		for(ParameterVo pv:parameterTypelist) {
			ParameterPo p=new ParameterPo();
			p.setParameterId(pv.getParameterId());
			polists.add(p);
		}
		
		
		List<ParameterVo> list=parameterMapper.queryParameterListsByTypes(polists);
		
		return list;
	}
	
}
