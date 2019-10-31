package com.education.hjj.bz.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.education.hjj.bz.entity.TeachBranchPo;
import com.education.hjj.bz.entity.vo.TeachBranchVo;

@Mapper
public interface TeachBranchMapper {

	List<TeachBranchVo> queryTeachBranchListByTeachLevel();
	
	List<TeachBranchVo> queryTeachBranchListByTeachGrade();
	
	List<TeachBranchVo> queryAllTeachBranchs();
	
	List<TeachBranchVo> queryCheckedTeachBranchs(List<TeachBranchPo> parameterIds);
}
