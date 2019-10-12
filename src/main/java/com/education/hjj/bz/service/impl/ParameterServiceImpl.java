package com.education.hjj.bz.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.education.hjj.bz.entity.ParameterPo;
import com.education.hjj.bz.entity.vo.ParameterVo;
import com.education.hjj.bz.mapper.ParameterMapper;
import com.education.hjj.bz.service.ParameterService;
import com.education.hjj.bz.util.JedisUtil;
import com.education.hjj.bz.util.UtilTools;

@Service
public class ParameterServiceImpl implements ParameterService{

	@Autowired
	private ParameterMapper parameterMapper;

	@Override
	@Transactional
	public List<ParameterVo> queryParameterListsByParentId(String parent_Ids) {
		// TODO Auto-generated method stub
		
		
		if(parent_Ids != null && StringUtils.isNotBlank(parent_Ids)) {
			
			String[] parentIds = parent_Ids.split(",");

			for(String s : parentIds) {
				
				//ParameterVo parameter = parameterMapper.queryParameterById(Integer.valueOf(s));
				
				//String parameterKey = parameter.getEnglishName();
				
				String parameters = null;
				
				try {
					parameters = JedisUtil.getJson("parameter_"+s);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				List<ParameterVo>  list = null;
				
				if(parameters == null || StringUtils.isBlank(parameters)) {
					list = parameterMapper.queryParameterListsByParentId(Integer.valueOf(s));
					
					if(list !=null && list.size() > 0) {
						try {
							//存进redis
							JedisUtil.setJson("parameter_"+s, JSON.toJSONString(list) , 60*60*24);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					}
					
					return list;
					
				}else {
					
					list = JSON.parseArray(parameters, ParameterVo.class);
					return list;
				}
				
			}
		}
		
		return null;
	}

	@Override
	public List<ParameterVo> queryParameterListsByTypes(String parameterIds) {
		
		if(parameterIds != null && StringUtils.isNoneBlank(parameterIds)) {
			String[] pIds = parameterIds.split(",");
			
			List<ParameterPo> list = new ArrayList<ParameterPo>();
			
			for(String pid:pIds) {
				
				ParameterPo po = new ParameterPo();
				po.setParameterId(Integer.valueOf(pid));
				
				list.add(po);
			}
			
			List<ParameterVo> pVoList = parameterMapper.queryParameterListsByTypes(list);
			// TODO Auto-generated method stub
			return pVoList;
		}
		
		return null;
		
	}
	
	
}
