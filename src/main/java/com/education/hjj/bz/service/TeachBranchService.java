package com.education.hjj.bz.service;

import java.util.List;

import com.education.hjj.bz.entity.vo.TeachBranchVo;
import com.education.hjj.bz.entity.vo.TeachGradeVo;
import com.education.hjj.bz.entity.vo.TeachLevelVo;

public interface TeachBranchService {
	
	List<TeachLevelVo> queryTeachBranchListByTeachLevel();

	List<TeachGradeVo> queryTeachBranchListByTeachGrade();
	
	List<TeachBranchVo> queryAllTeachBranchs();
	
	List<TeachBranchVo> queryCheckedTeachBranchs(String parameterIds);

}
