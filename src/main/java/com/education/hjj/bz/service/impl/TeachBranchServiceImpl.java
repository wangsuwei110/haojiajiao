package com.education.hjj.bz.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.education.hjj.bz.entity.vo.TeachBranchVo;
import com.education.hjj.bz.entity.vo.TeachGradeVo;
import com.education.hjj.bz.entity.vo.TeachLevelVo;
import com.education.hjj.bz.mapper.TeachBranchMapper;
import com.education.hjj.bz.service.TeachBranchService;

@Service
public class TeachBranchServiceImpl implements TeachBranchService{
	
	@Autowired
	private TeachBranchMapper teachBranchMapper;

	@Override
	public List<TeachLevelVo> queryTeachBranchListByTeachLevel() {
		
		return null;
	}

	@Override
	public List<TeachGradeVo> queryTeachBranchListByTeachGrade() {
		
		return null;
		
	}

	@Override
	public List<TeachBranchVo> queryAllTeachBranchs() {
		
		List<TeachBranchVo> list = teachBranchMapper.queryAllTeachBranchs();
		
		return list;
	}

	
}
