package com.education.hjj.bz.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.education.hjj.bz.entity.ParameterPo;
import com.education.hjj.bz.entity.TeachBranchPo;
import com.education.hjj.bz.entity.vo.TeachBranchVo;
import com.education.hjj.bz.entity.vo.TeachGradeVo;
import com.education.hjj.bz.entity.vo.TeachLevelVo;
import com.education.hjj.bz.mapper.TeachBranchMapper;
import com.education.hjj.bz.service.TeachBranchService;

@Service
public class TeachBranchServiceImpl implements TeachBranchService {

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

	@Override
	public List<TeachBranchVo> queryCheckedTeachBranchs(String parameterIds) {

		if (parameterIds != null && StringUtils.isNoneBlank(parameterIds)) {
			String[] pIds = parameterIds.split(",");

			List<TeachBranchPo> list = new ArrayList<TeachBranchPo>();

			for (String pid : pIds) {

				TeachBranchPo po = new TeachBranchPo();
				po.setTeachBranchId((Integer.valueOf(pid)));

				list.add(po);
			}

			List<TeachBranchVo> teachBranchVoList = teachBranchMapper.queryCheckedTeachBranchs(list);
			
			return teachBranchVoList;
		}

		return null;
	}
}
