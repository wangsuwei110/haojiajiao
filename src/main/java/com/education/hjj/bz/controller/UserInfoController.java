package com.education.hjj.bz.controller;

import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

import com.education.hjj.bz.mapper.TeachBranchMapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.education.hjj.bz.entity.PicturePo;
import com.education.hjj.bz.entity.StudentLogPo;
import com.education.hjj.bz.entity.TeacherPo;
import com.education.hjj.bz.entity.vo.PageVo;
import com.education.hjj.bz.entity.vo.ParameterVo;
import com.education.hjj.bz.entity.vo.PictureVo;
import com.education.hjj.bz.entity.vo.StudentDemandConnectVo;
import com.education.hjj.bz.entity.vo.StudentDemandVo;
import com.education.hjj.bz.entity.vo.StudentVo;
import com.education.hjj.bz.entity.vo.TeachBranchVo;
import com.education.hjj.bz.entity.vo.TeacherAccountOperateLogVo;
import com.education.hjj.bz.entity.vo.TeacherVo;
import com.education.hjj.bz.entity.vo.UniversityVo;
import com.education.hjj.bz.formBean.PictureForm;
import com.education.hjj.bz.formBean.StudentConnectTeacherForm;
import com.education.hjj.bz.formBean.StudentTeacherInfoForm;
import com.education.hjj.bz.formBean.TeacherInfoForm;
import com.education.hjj.bz.formBean.TeacherInfoReplenishForm;
import com.education.hjj.bz.formBean.UserInfoForm;
import com.education.hjj.bz.mapper.StudentConnectTeacherMapper;
import com.education.hjj.bz.mapper.StudentMapper;
import com.education.hjj.bz.service.DemandCourseInfoService;
import com.education.hjj.bz.service.ParameterService;
import com.education.hjj.bz.service.StudentDemandConnectService;
import com.education.hjj.bz.service.StudentLogService;
import com.education.hjj.bz.service.TeachBranchService;
import com.education.hjj.bz.service.UserInfoService;
import com.education.hjj.bz.service.UserPictureInfoService;
import com.education.hjj.bz.util.ApiResponse;
import com.education.hjj.bz.util.DateUtil;
import com.education.hjj.bz.util.UtilTools;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = { "教员信息" })
@RestController
@RequestMapping(value = "/userInfo")
public class UserInfoController {
	
	private static Logger logger = LoggerFactory.getLogger(UserInfoController.class);

	@Autowired
	private UserInfoService userInfoService;
	
	@Autowired
	private UserPictureInfoService userPictureInfoService;
	
	@Autowired
	private ParameterService parameterService;
	
	@Autowired
	private TeachBranchService teachBranchService;

	@Autowired
	private StudentConnectTeacherMapper connectTeacherMapper;
	
	@Autowired
	private StudentDemandConnectService studentDemandConnectService;
	
	@Autowired
	private DemandCourseInfoService demandCourseInfoService;

	@Autowired
	private StudentMapper studentMapper;

	@Autowired
	private StudentLogService studentLogService;

	@Autowired
	private TeachBranchMapper teachBranchMapper;

	@ApiOperation("用户信息更新(带图片)")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public ApiResponse updateUserInfos(@RequestBody  UserInfoForm userInfoForm) {
		
		int i = userInfoService.updateUserInfo(userInfoForm);

		if(i > 0) {
			return ApiResponse.success("保存成功!");
		}
		return ApiResponse.error("保存失败!");

	}
	
	@ApiOperation("用户信息更新")
	@RequestMapping(value = "/updateUserInfoByParameter", method = RequestMethod.POST)
	public ApiResponse updateUserInfoByParameter(@RequestBody TeacherInfoForm teacherInfoForm) {

		int i = userInfoService.updateUserInfoByParameter(teacherInfoForm);
		Map<String , Object> map = new HashMap<String, Object>(1);
		
		if(i > 0) {
			
			TeacherVo teacherVo = userInfoService.queryTeacherHomeInfos(teacherInfoForm.getTeacherId());
			
			if(teacherVo != null ) {
				map.put("teacherName", teacherVo.getName());
				map.put("vacationStatus", teacherVo.getVacationStatus());
			}
			
			return ApiResponse.success("操作成功" , UtilTools.mapToJson(map));
			
		}
        
        return ApiResponse.error("操作失败!");

	}
	
	@ApiOperation("用户基本信息补全")
	@RequestMapping(value = "/updateUserInfo", method = RequestMethod.POST)
	public ApiResponse updateUserInfos(@RequestBody TeacherInfoReplenishForm teacherInfoReplenishForm) {
		
		int i = userInfoService.updateUserInfos(teacherInfoReplenishForm);
		Map<String , Object> map = new HashMap<String, Object>(1);
		
		if(i > 0) {
			
			TeacherVo teacherVo = userInfoService.queryTeacherHomeInfos(teacherInfoReplenishForm.getTeacherId());
			
			if(teacherVo != null ) {
				map.put("teacherName", teacherVo.getName());
				map.put("school", teacherVo.getSchool());
			}
			
			return ApiResponse.success("操作成功" , UtilTools.mapToJson(map));
		}
        
        return ApiResponse.error("操作失败!");

	}

	@ApiOperation("通过手机号查找用户信息详情")
	@RequestMapping(value = "/queryTeacherInfoByTelephone", method = RequestMethod.GET)
	@RequiresPermissions(logical = Logical.AND, value = {"teacher:edit" , "teacher:view"})
	public ApiResponse queryTeacherInfoByTelephone(String phoneNum) {

		Map<String,Object> map = userInfoService.queryTeacherInfoByTelephone(phoneNum);

		return ApiResponse.success(map);
	}
	
	@ApiOperation("用户收入查询")
	@RequestMapping(value = "/queryTeacherAccountByID", method = RequestMethod.GET)
	public ApiResponse queryUserAccount(@RequestParam("userId") String userId) {

		Map<String,Object> map = userInfoService.queryTeacherAccount(userId);

		return ApiResponse.success(map);
	}
	
	@ApiOperation("用户钱款支出明细查询")
	@RequestMapping(value = "/queryTeacherAccountOpreateLog", method = RequestMethod.GET)
	public ApiResponse queryUserAccountOperateLog(@RequestParam("userId") String userId , @RequestParam("type")Integer type) {

		TeacherAccountOperateLogVo teacherAccount = userInfoService.queryUserAccountOperateLog(userId , type);

		return ApiResponse.success(teacherAccount);
	}
	
	@ApiOperation("查询我的简历基本信息")
	@RequestMapping(value = "/queryTeacherInfo", method = RequestMethod.POST)
	public ApiResponse queryTeacherInfo(@RequestBody TeacherInfoForm teacherInfoForm) {
		
		logger.info("teacherId() = {}, 访问路径/userInfo/queryTeacherInfo" , teacherInfoForm.getTeacherId());
		
		String teacherId = teacherInfoForm.getTeacherId();
		
		TeacherVo teacherVo = null;
		
		if(teacherId != null && StringUtils.isNoneBlank(teacherId)) {
			
			teacherVo = userInfoService.queryTeacherHomeInfos(teacherId);
		}

		Map<String , Object> map = new HashMap<String, Object>(1);
		map.put("teacherName", teacherVo.getName());
		map.put("teacherLevel", teacherVo.getTeacherLevel());
		//map.put("telephone", teacherVo.getTelephone().replace(teacherVo.getTelephone().subSequence(3, 7), "****"));
		map.put("telephone", teacherVo.getTelephone());
		
		logger.info("telephone = {}" , map.get("telephone"));
		
		map.put("headPicture", teacherVo.getPicture());
		map.put("sex", teacherVo.getSex());
		map.put("auditStatus", teacherVo.getAuditStatus());
		map.put("logonStatus", teacherVo.getLogonStatus());
		map.put("home", teacherVo.getHome());
		map.put("weiChar", teacherVo.getWeiChar());
		map.put("QQ", teacherVo.getQQ());
		map.put("resumeComplete", teacherVo.getResumeComplete());
		map.put("address", teacherVo.getAddress());
		
		map.put("certificate", teacherVo.getTeacherCertificate() == null?"0":teacherVo.getTeacherCertificate());
		map.put("experience", teacherVo.getExperience() == null ? "0":teacherVo.getExperience());
		
		map.put("major", teacherVo.getMajor());
		map.put("teacherTag", teacherVo.getTeacherTag());
		map.put("teachTime", teacherVo.getTeachTime());
		
		
		return ApiResponse.success("操作成功" , UtilTools.mapToJson(map));
	}
	
	@ApiOperation("查询带图片的用户信息")
	@RequestMapping(value = "/queryTeacherInfoByType", method = RequestMethod.POST)
	@ResponseBody
	public ApiResponse queryTeacherInfoByType(@RequestBody PictureForm pictureForm) {
		
		PicturePo picturePo =new PicturePo();
		
		if(pictureForm != null) {
			
			if(pictureForm.getTeacherId() != null && StringUtils.isNoneBlank(pictureForm.getTeacherId())) {
				picturePo.setTeacherId(Integer.valueOf(pictureForm.getTeacherId()));
			}
			
			if(pictureForm.getPictureType() != null) {
				picturePo.setPictureType(pictureForm.getPictureType());
			}
			
		}
		
		Map<String, Object>  map = userInfoService.queryTeacherInfoByType(picturePo);
		
		if(map != null && map.size()>0) {
			return ApiResponse.success("操作成功" , UtilTools.mapToJson(map));
		}
		
		return ApiResponse.error("暂无数据！");
		
	}
	
	@ApiOperation("查询用户图片信息")
	@RequestMapping(value = "/queryUserPicturesByType", method = RequestMethod.POST)
	@ResponseBody
	public ApiResponse queryUserPicturesByType(@RequestBody PictureForm pictureForm) {
		
		List<PictureVo> list = userPictureInfoService.queryUserPicturesByType(pictureForm);
		
		if(list != null && list.size()>0) {
			return ApiResponse.success("操作成功" , JSONObject.toJSON(list));
		}
		
		return ApiResponse.error("暂无数据！");
	}
	
	@ApiOperation("查询用户图片详情")
	@RequestMapping(value = "/queryUserPicturesDetail", method = RequestMethod.POST)
	@ResponseBody
	public ApiResponse queryUserPicturesDetail(@RequestBody PictureForm pictureForm) {
		
		PictureVo pictureVo = userPictureInfoService.queryUserPicturesDetail(pictureForm);
		
		if(pictureVo != null ) {
			return ApiResponse.success("操作成功" , JSONObject.toJSON(pictureVo));
		}
		
		return ApiResponse.error("暂无数据！");
	}
	
	@ApiOperation("查询用户简历详情")
	@RequestMapping(value = "/queryUserInfosDetail", method = RequestMethod.POST)
	@ResponseBody
	public ApiResponse queryTeacherInfosDetail(@RequestBody PictureForm pictureForm) {
		
		
		
		if(pictureForm != null) {
			Map<String , Object> map = new HashMap<String , Object>();
			
			String teacherId = pictureForm.getTeacherId();
			
			logger.info("teacherId = {}" , teacherId);
			
			TeacherVo  teacherVo  = null;
			if(teacherId != null && StringUtils.isNoneBlank(teacherId)) {
				teacherVo  = userInfoService.queryTeacherHomeInfos(teacherId);

				if (pictureForm.getStudentId() != null) {
					StudentVo studentVo = studentMapper.load(Long.valueOf(pictureForm.getStudentId()));

					// 插入一条浏览教员的日志信息
					StudentLogPo logPo = new StudentLogPo();
					logPo.setStudentId(Integer.valueOf(studentVo.getSid().toString()));
					logPo.setLogType(2); // 登录
					logPo.setLogContent("最近浏览了" +teacherVo.getName() + "教员的信息");
					logPo.setStudentName(studentVo.getStudentName());
					logPo.setStatus(1);
					logPo.setCreateTime(new Date());
					logPo.setCreateUser(studentVo.getSid().toString());
					logPo.setUpdateTime(new Date());
					logPo.setUpdateUser(studentVo.getSid().toString());
					studentLogService.addStudentLog(logPo);
				}
			}
			
			String date = teacherVo.getBeginSchoolTime();
			
			try {
				teacherVo.setBeginSchoolTime(DateUtil.caculDegree(date));
			} catch (ParseException e) {
				logger.info("转换入学日期到年级失败......");
				e.printStackTrace();
			}
			
//暂时去掉路径替换   teacherVo.setPicture(teacherVo.getPicture().substring(teacherVo.getPicture().lastIndexOf('/')+1));
			
			//基本信息
			map.put("baseInfo", teacherVo);
			
			if(teacherVo != null) {
				String tags = teacherVo.getTeacherTag();
				logger.info("tags = {}" , tags);
				List<ParameterVo>  pVoList = null;
				
				if( tags !=null && StringUtils.isNoneBlank(tags)) {
					pVoList = parameterService.queryParameterListsByTypes(tags);
				}
				//个人标签
				map.put("chooseTags", pVoList);
				
				List<PictureVo> degreePictureList = new ArrayList<PictureVo>();
				List<PictureVo> certificatePictureList = new ArrayList<PictureVo>();
				List<PictureVo> expirencePictureList = new ArrayList<PictureVo>();
				
				if(teacherId != null && StringUtils.isNoneBlank(teacherId)) {
					
					List<PictureVo> pictureList  = userPictureInfoService.queryUserPicturesByteacherId(Integer.valueOf(teacherId));
					
					for(PictureVo p :pictureList) {
						
//						String[] picUrls = p.getPictureUrl().split(",");
//						
//						List<String> urls = new ArrayList<String>();
//						
//						for(String url:picUrls) {
//							
//							String subUrl = url.substring(url.lastIndexOf('/')+1);
//							urls.add(subUrl);
//						}
						
//去除替换路径为空			String urls = p.getPictureUrl().replaceAll("/haojiajiao/share/IMG/", "");
						
						String urls = p.getPictureUrl();
						
						if(p.getPictureType() == 3) {
							p.setPictureUrl(urls);
							degreePictureList.add(p);
						}
						
						if(p.getPictureType() == 4) {
							p.setPictureUrl(urls);
							certificatePictureList.add(p);						
						}
						
						if(p.getPictureType() == 5) {
							p.setPictureUrl(urls);
							expirencePictureList.add(p);
						}
						
					}
				}
				
				//个人学历图片
				map.put("degreePictureList", degreePictureList);
				//个人证书图片
				map.put("certificatePictureList", certificatePictureList);
				//个人经验图片
				map.put("expirencePictureList", expirencePictureList);
				
				
				String parameterIds = teacherVo.getTeachBrance()+","+teacherVo.getTeachBranchSlave();
				
				logger.info("parameterIds = {}" , parameterIds);
				
				List<TeachBranchVo> parametersList = null;
				if(teacherVo.getTeachBrance() != null && teacherVo.getTeachBranchSlave() != null ) {
					parametersList = teachBranchService.queryCheckedTeachBranchs(parameterIds);
				}
				
				//可教科目
				map.put("teachBranchs", parametersList);
				
				String address = teacherVo.getTeachAddress();
				logger.info("address = {}" , address);
				
				List<ParameterVo> addressList = null;
				if(address != null && StringUtils.isNoneBlank(address)) {
					addressList =parameterService.queryParameterListsByTypes(address);
				}
				
				//可教区域
				map.put("teachAddress", addressList);
				
				//教学时间
				map.put("teachTime", teacherVo.getTeachTime());

				if (pictureForm.getStudentId() != null) {
					// 查看是否被收藏
					StudentConnectTeacherForm connectTeacherForm = new StudentConnectTeacherForm();
					connectTeacherForm.setStudentId(pictureForm.getStudentId());
					connectTeacherForm.setTeacherId(Integer.valueOf(pictureForm.getTeacherId()));
					Integer count = connectTeacherMapper.getConnectCount(connectTeacherForm);
					if (count != null && count > 0) {
						map.put("collectFlag", true);
					} else {
						map.put("collectFlag", false);
					}
				} else {
					map.put("collectFlag", false);
				}
				
				List<StudentDemandVo> teacherForStudentServiceList = studentDemandConnectService.queryServiceForStudentByTeacherId(Integer.valueOf(teacherId));
				
				//教员服务过的学员最近5条记录
				map.put("teacherForStudentServiceList", teacherForStudentServiceList);
				
				List<StudentDemandConnectVo>  StudentAppraiseForTeacherList = studentDemandConnectService.queryStudentAppraiseForTeacher(Integer.valueOf(teacherId));

				//学员对教员的评价的最近5条记录
				map.put("StudentAppraiseForTeacherList", StudentAppraiseForTeacherList);
				
				//服务的学员人数
				int num = studentDemandConnectService.queryServiceForStudentSuccess(Integer.valueOf(teacherId));
				
				map.put("servicePersonNum", num);
				
				int serviceHours = demandCourseInfoService.queryServiceForHours(Integer.valueOf(teacherId));
				
				map.put("serviceHours", serviceHours);
				
				return ApiResponse.success("操作成功" , UtilTools.mapToJson(map));
			} 
				
			return ApiResponse.error("暂无数据！");
		}
		
		return ApiResponse.error("暂无数据！");
	}

	
	@ApiOperation("学员端查询所有教员信息")
	@RequestMapping(value = "/queryAllTeacherInfosByStudents", method = RequestMethod.POST)
	@ResponseBody
	public ApiResponse queryAllTeacherInfosByStudents(@RequestBody StudentTeacherInfoForm studentTeacherInfoForm) {
		Map<String, Object> map = new HashMap<>();
		Integer teacherCount = 0;
		logger.info("caohuan*********" + JSON.toJSONString(studentTeacherInfoForm));
		// 判断是否有学员id，如果有，则修改branchs的内容
		if (studentTeacherInfoForm.getStudentId() != null
                && StringUtils.isNotEmpty(studentTeacherInfoForm.getBranchs())) {

			StudentVo vo = studentMapper.load(studentTeacherInfoForm.getStudentId());
			TeachBranchVo teachBranchVo = teachBranchMapper.queryByBranchId(vo.getSubjectId());

			// 回显用
			map.put("studentGrade", vo.getGrade());
			map.put("studentLevel", teachBranchVo.getTeachLevelId());
			map.put("studentSubjectId", vo.getSubjectId());

			List<String> brancheList = Arrays.asList(studentTeacherInfoForm.getBranchs().split(","));

			// 如果用户id不为空，则拿用户的年级id去匹配选择的科目
			List<Integer> list = teachBranchMapper.queryListByBranchId(vo.getSubjectId());
			list = list.stream().filter(f -> f != null && brancheList.contains(f.toString())).collect(Collectors.toList());

			if (!CollectionUtils.isEmpty(list)) {
				studentTeacherInfoForm.setBranchs(StringUtils.join(list, ","));
			}
            studentTeacherInfoForm.setBranchList(Arrays.asList(studentTeacherInfoForm.getBranchs().split(",")));

			teacherCount = userInfoService.queryAllTeacherCount(studentTeacherInfoForm);
		} else {

			// 游客则显示20条教员信息
			if (studentTeacherInfoForm.getStudentId() == null && studentTeacherInfoForm.getPageIndex() > 1) {
				return ApiResponse.error("游客不能查看更能多教员");
			}

			teacherCount = userInfoService.queryAllTeacherCount(studentTeacherInfoForm);
		}

		if(teacherCount != null && teacherCount > 0) {
			List<TeacherVo> list = userInfoService.queryAllTeacherInfosByStudent(studentTeacherInfoForm);

			for(TeacherVo t:list) {
				
				String tags = t.getTeacherTag();
				logger.info("tags = {}" , tags);
				List<ParameterVo>  pVoList = null;
				
				if( tags !=null && StringUtils.isNoneBlank(tags)) {
					pVoList = parameterService.queryParameterListsByTypes(tags);
				}
				//个人标签
	//			map.put("chooseTags", pVoList);
				t.setTeacherTag(JSON.toJSONString(pVoList));
	
	
				String parameterIds = t.getTeachBrance()+","+t.getTeachBranchSlave();
				
				logger.info("parameterIds = {}" , parameterIds);
				
				List<TeachBranchVo> teachBranchs = null;
				if(t.getTeachBrance() != null && t.getTeachBranchSlave() != null ) {
					teachBranchs = teachBranchService.queryCheckedTeachBranchs(parameterIds);
				}
				
				//可教科目
	//			map.put("teachBranchs", parametersList);
				
				t.setTeachBrance(JSON.toJSONString(teachBranchs));
				//基本信息
				//map.put("baseInfo", t);
				
				String date = t.getBeginSchoolTime();
				
				try {
					t.setBeginSchoolTime(DateUtil.caculDegree(date));
				} catch (ParseException e) {
					logger.info("转换入学日期到年级失败......");
					e.printStackTrace();
				}
			}
		
		
			PageVo pageVo = new PageVo();
		
			pageVo.setDataList(list);
			pageVo.setTotal(teacherCount);

            map.put("pageVo", pageVo);
			
			return ApiResponse.success("操作成功！",pageVo);
		}
		
		return ApiResponse.error("暂无数据！");
		
	}
	
	@ApiOperation("查询所有学校信息")
	@RequestMapping(value = "/queryAllSchools", method = RequestMethod.GET)
	@ResponseBody
	public ApiResponse queryAllSchools() {
		
		List<UniversityVo> list = userInfoService.queryAllSchools();
		
		return ApiResponse.success("操作成功！" , JSONObject.toJSON(list));
		
	}
	
	@ApiOperation("教务端查询所有待审核教员信息")
	@RequestMapping(value = "/queryAllTeacherInfosByEducational", method = RequestMethod.POST)
	@ResponseBody
	public ApiResponse queryAllTeacherInfosByEducational(@RequestBody StudentTeacherInfoForm studentTeacherInfoForm) {
		
		List<Map<String, Object>>  list = userInfoService.queryAllTeacherInfosByEducational(studentTeacherInfoForm);
		
		return ApiResponse.success("操作成功！" , JSONObject.toJSON(list));
	}
	
	@ApiOperation("教务端审核员审核待审核教员信息")
	@RequestMapping(value = "/auditTeacherInfosByEducational", method = RequestMethod.POST)
	@ResponseBody
	public ApiResponse auditTeacherInfosByEducational(@RequestBody TeacherInfoForm teacherInfoForm) {
		
		TeacherPo teacher = new TeacherPo();
		teacher.setTeacherId(Integer.valueOf(teacherInfoForm.getTeacherId()));
		teacher.setAuditStatus(1);
		teacher.setAuditDesc(teacherInfoForm.getAuditDesc());
		teacher.setUpdateTime(new Date());
		teacher.setUpdateUser(teacherInfoForm.getTeacherId());
		
		int i = userInfoService.updateUserInfo(teacher);
		
		if(i > 0) {
			return ApiResponse.success("操作成功！");
		}
		
		return ApiResponse.error("操作失败！");
	}
	
}
