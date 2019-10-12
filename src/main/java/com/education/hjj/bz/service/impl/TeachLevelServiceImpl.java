package com.education.hjj.bz.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.education.hjj.bz.entity.vo.TeachLevelVo;
import com.education.hjj.bz.mapper.TeachLevelMapper;
import com.education.hjj.bz.service.TeachLevelService;

@Service
public class TeachLevelServiceImpl implements TeachLevelService{
	
	@Autowired
	private TeachLevelMapper teachLevelMapper;

	@Override
	public List<TeachLevelVo> queryAllTeachLevel() {
		
		List<TeachLevelVo> list = teachLevelMapper.queryAllTeachLevel();
		
		return list;
		
	}

}
