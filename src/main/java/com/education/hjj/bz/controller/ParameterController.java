package com.education.hjj.bz.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.education.hjj.bz.entity.vo.ParameterVo;
import com.education.hjj.bz.entity.vo.TeachBranchVo;
import com.education.hjj.bz.entity.vo.TeachGradeVo;
import com.education.hjj.bz.entity.vo.TeachLevelVo;
import com.education.hjj.bz.entity.vo.TeacherVo;
import com.education.hjj.bz.formBean.ParameterForm;
import com.education.hjj.bz.service.ParameterService;
import com.education.hjj.bz.service.TeachBranchService;
import com.education.hjj.bz.service.TeachGradeService;
import com.education.hjj.bz.service.TeachLevelService;
import com.education.hjj.bz.service.UserInfoService;
import com.education.hjj.bz.util.ApiResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = { "参数表查询" })
@RestController
@RequestMapping(value = "/parameter")
public class ParameterController {
	
	private static Logger logger = LoggerFactory.getLogger(ParameterController.class);

	@Autowired
	private ParameterService parameterService;
	
	@Autowired
	private UserInfoService userInfoService;
	
	@Autowired
	private TeachBranchService teachBranchService;
	
	@Autowired
	private TeachGradeService teachGradeService;
	
	@Autowired
	private TeachLevelService teachLevelService;
	
	
	@ApiOperation("授课资料信息补充参数查询")
	@RequestMapping(value = "/queryParameters", method = RequestMethod.POST)
	@Transactional
	public ApiResponse queryParametersByParentId(@RequestBody ParameterForm parameterForm) {
		
		
		
		String teacherId = parameterForm.getTeacherId();
		
		String parent_Ids = parameterForm.getParentId();
		
		if(parent_Ids == null || StringUtils.isBlank(parent_Ids)) {
			return ApiResponse.error("参数错误！");
		}
		
		logger.info("parameters={} , teacherId={}" , parent_Ids , teacherId);
		
		TeacherVo  tv = userInfoService.queryTeacherHomeInfos(teacherId);
		
		
		List<ParameterVo> pVoBranchSlaveList = null;
		
		
		String[] parentIds = parent_Ids.split(",");
		
		List<Map<String , Object>> compareResult = new ArrayList<Map<String , Object>>();
		
		Map<String , Object> map = new HashMap<String, Object>();
		
		for(String parentId:parentIds) {
			
			List<ParameterVo>  pVoList = new ArrayList<ParameterVo>();
			
			
			
			if(tv != null && Integer.valueOf(parentId) == 62 ) {
				if(tv.getTeacherTag() !=null && StringUtils.isNoneBlank(tv.getTeacherTag())) {
					pVoList = parameterService.queryParameterListsByTypes(tv.getTeacherTag());
				}
			}
			
			if(tv != null && Integer.valueOf(parentId) == 78 ) {
				if(tv.getTeachAddress() !=null && StringUtils.isNoneBlank(tv.getTeachAddress())) {
					pVoList = parameterService.queryParameterListsByTypes(tv.getTeachAddress());
				}
			}
			
			List<ParameterVo>  list = parameterService.queryParameterListsByParentId(parentId);
			
			
			for(int i=0 ; i< list.size();i++) {
				for(int j=0; j<pVoList.size();j++) {
					
					if(list.get(i).getParameterId() == pVoList.get(j).getParameterId()) {
						list.get(i).setFlag(true);
						list.get(i).setBranchType("master");
					}
					
				}
				
			}
			
			
			

			if(Integer.valueOf(parentId) == 62) {
				map.put("teacherTag", list);
			}
			if(Integer.valueOf(parentId) == 78) {
				map.put("teachAddress", list);
			}
			
			
		}
		
		//开始讲教学年级进行比较，并将比较后的结果赋值
		String[] teachGrade = null;
		List<TeachGradeVo> teachGradeVoList =  teachGradeService.queryAllTeachGrade();
		
		if(tv.getTeachGrade() != null && StringUtils.isNoneBlank(tv.getTeachGrade())) {
			teachGrade = tv.getTeachGrade().split(",");
			
			List<TeachGradeVo> teachGradeVoLists = new ArrayList<TeachGradeVo>();
					
			for(TeachGradeVo tgv:teachGradeVoList) {
				
				TeachGradeVo tgvs = new TeachGradeVo();
				
				tgvs.setTeachGradeId(tgv.getTeachGradeId());
				tgvs.setTeachGradeName(tgv.getTeachGradeName());
				
				for(String t:teachGrade) {
					
					if(tgv.getTeachGradeId() == Integer.valueOf(t)) {
						
						tgvs.setFlag(true);
						
						break;
					}
					
					
				}
				teachGradeVoLists.add(tgvs);
			}
			
			map.put("teachGrade", teachGradeVoLists);
		}else {
			map.put("teachGrade", teachGradeVoList);
		}
		
		
		

		
		
		
		//开始讲教学学段进行比较，并将比较后的结果赋值
		List<TeachLevelVo> teachLevelVoList = teachLevelService.queryAllTeachLevel();
		
		List<TeachLevelVo> teachLevelVoLists = new ArrayList<TeachLevelVo>();
		
		String[] teachLevel = null;
		if(tv.getTeachLevel() != null && StringUtils.isNoneBlank(tv.getTeachLevel())) {
			teachLevel = tv.getTeachLevel().split(",");
			
			for(TeachLevelVo tlv:teachLevelVoList) {
				TeachLevelVo tlvs = new TeachLevelVo();
				for(String s:teachLevel) {
					
					
					tlvs.setTeachLevelId(tlv.getTeachLevelId());
					tlvs.setTeachLevelName(tlv.getTeachLevelName());
					
					if(tlv.getTeachLevelId() == Integer.valueOf(s)) {
						tlvs.setFlag(true);
						break;
					}
					
					
				}
				teachLevelVoLists.add(tlvs);
			}
			
			map.put("teachLevel", teachLevelVoLists);
		}else {
			map.put("teachLevel", teachLevelVoList);
		}
		
		
		

		
		
		List<TeachBranchVo> teachBranchVoList = teachBranchService.queryAllTeachBranchs();
		List<TeachBranchVo> teachBranchMasterVoLists = new ArrayList<TeachBranchVo>();
		
		String teachBrance = tv.getTeachBrance();
		if(teachBrance !=null && StringUtils.isNotBlank(teachBrance)) {
			for(TeachBranchVo tbv:teachBranchVoList) {
				
				TeachBranchVo tbvs= new TeachBranchVo();
				
				tbvs.setTeachBranchId(tbv.getTeachBranchId());
				tbvs.setTeachBranchName(tbv.getTeachBranchName());
				
				if(tbv.getTeachBranchId() == Integer.valueOf(teachBrance)) {
					
					tbvs.setFlag(true);
					tbvs.setBranchType("master");
				}
				
				teachBranchMasterVoLists.add(tbvs);
			}
			
			map.put("teachBranchMaster", teachBranchMasterVoLists);
		}else {
			map.put("teachBranchMaster", teachBranchVoList);
		}
		
		
		
		List<TeachBranchVo> teachBranchVoLists = new ArrayList<TeachBranchVo>();
		
		String[] teachBranceSlave = null;
		if(tv.getTeachBranchSlave() != null && StringUtils.isNoneBlank(tv.getTeachBranchSlave())) {
			teachBranceSlave = tv.getTeachBranchSlave().split(",");
			
			for(TeachBranchVo tbv:teachBranchVoList) {
				
				TeachBranchVo tbvs= new TeachBranchVo();
				
				tbvs.setTeachBranchId(tbv.getTeachBranchId());
				tbvs.setTeachBranchName(tbv.getTeachBranchName());
				
				for(String s:teachBranceSlave) {
					
					//辅授课目
					
					tbvs.setBranchType("slave");
					if(tbv.getTeachBranchId() == Integer.valueOf(s)) {
						tbvs.setFlag(true);
						break;
					}
					
					//主授课目
//					if(tbv.getTeachBranchId() == Integer.valueOf(teachBrance)) {
//
//						tbvs.setFlag(true);
//						tbvs.setBranchType("master");
//						break;
//					}
					
					
				}
				
				teachBranchVoLists.add(tbvs);
				
			}
			
			map.put("teachBranchSlave", teachBranchVoLists);
		}else {
			map.put("teachBranchSlave", teachBranchVoList);
		}
		
		
		
		map.put("teachTime", JSON.toJSON(tv.getTeachTime()));
		compareResult.add(map);
		
		return ApiResponse.success("操作成功" ,JSON.toJSON(compareResult));
	}
	
	@ApiOperation("通过父ID查询指定类型参数")
	@RequestMapping(value = "/queryParametersByType", method = RequestMethod.POST)
	@ResponseBody
	public ApiResponse queryParametersByType(@RequestBody ParameterForm parameterForm) {
		
		String parentId  = parameterForm.getParentId();
		
		List<ParameterVo> list = parameterService.queryParameterListsByParentId(parentId);
		
		return ApiResponse.success(list);
	}
}
