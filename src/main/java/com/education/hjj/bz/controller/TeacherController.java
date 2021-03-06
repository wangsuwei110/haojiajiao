package com.education.hjj.bz.controller;

import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.education.hjj.bz.entity.vo.PageVo;
import com.education.hjj.bz.entity.vo.StudentDemandVo;
import com.education.hjj.bz.formBean.StudentConnectTeacherForm;
import com.education.hjj.bz.formBean.StudentDemandConnectForm;
import com.education.hjj.bz.formBean.TeachScreenForm;
import com.education.hjj.bz.formBean.TeachUniversityForm;
import com.education.hjj.bz.formBean.TeacherScreenForm;
import com.education.hjj.bz.service.StudentDemandsService;
import com.education.hjj.bz.service.TeacherService;
import com.education.hjj.bz.util.ApiResponse;
import com.education.hjj.bz.util.UtilTools;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 教师表控制类
 *
 * @创建者：sys
 * @创建时间：2019-10-9 21:50:41
 */
@Api(tags = "学员端-教员列表")
@RestController
@RequestMapping("teacher")
public class TeacherController {


    @Autowired
    private TeacherService teacherService;
    
    @Autowired
    private StudentDemandsService studentDemandsService;

    /**
     * 检索教员的信息
     * */
    @ApiOperation("检索教员的信息")
    @RequestMapping(value = "/queryTeacherInfo", method = RequestMethod.GET)
    public ApiResponse queryStudentDemandDetail(@RequestParam("teacherId") Integer teacherId) {

        return ApiResponse.success(teacherService.findById(teacherId));
    }

    // 详情页
	@PostMapping("/list")
    @ResponseBody
	public ApiResponse list(@RequestBody TeacherScreenForm form) {
        return teacherService.listPage(form);
    }

    // 教师筛选下拉框
    @PostMapping("/selectList")
    @ResponseBody
    public ApiResponse selectList() {
        return teacherService.selectList();
    }


    // 科目刷选的三级联动 学段/年级/科目
    @ApiOperation("三级联动，科目表")
    @PostMapping("/listSubject")
    @ResponseBody
    public ApiResponse listSubject(@RequestBody TeachScreenForm form) {
        return teacherService.listSubject(form);
    }

    // 科目刷选的二级联动 学段/年级/科目
    @ApiOperation("二级联动，科目表")
    @PostMapping("/findAllSubject")
    @ResponseBody
    public ApiResponse findAllSubject(@RequestBody TeachScreenForm form) {
        return teacherService.findAllSubject(form);
    }

    // 科目回显接口
    @ApiOperation("回显科目")
    @PostMapping("/findSubject")
    @ResponseBody
    public ApiResponse findSubject(@RequestBody TeachScreenForm form) {
        return teacherService.findSubject(form);
    }

    // 筛选教员大学
    @ApiOperation("大学列表")
    @PostMapping("/listUniversity")
    @ResponseBody
    public ApiResponse listUniversity(@RequestBody TeachUniversityForm form) {
        return teacherService.listUniversity(form);
    }

    // 收藏教员
    @PostMapping("/connect")
    @ResponseBody
    public ApiResponse connect(@RequestBody StudentConnectTeacherForm form) {
        return teacherService.connect(form);
    }

    // 收藏教员列表
    @PostMapping("/connectList")
    @ResponseBody
    public ApiResponse connectList(@RequestBody StudentConnectTeacherForm form) {
        return teacherService.connectList(form);
    }

    // 取消收藏教员
    @PostMapping("/cancelConnect")
    @ResponseBody
    public ApiResponse cancelConnect(@RequestBody StudentConnectTeacherForm form) {
        return teacherService.cancelConnect(form);
    }
    
	@ApiOperation("我的订单")
	@RequestMapping(value = "/queryDemandsByTeacher", method = RequestMethod.POST)
	@ResponseBody
	public ApiResponse queryUserDemandsList(@RequestBody StudentDemandConnectForm demandForm) {
		
		PageVo pageVo = new PageVo();
		
		List<StudentDemandVo>  list = studentDemandsService.queryUserDemandsList(demandForm);
		pageVo.setDataList(list);
		pageVo.setTotal(list.size());
		
		return ApiResponse.success(list);
	}
	
	@ApiOperation("订单详情")
	@RequestMapping(value = "/queryStudemtDemandDetail", method = RequestMethod.POST)
	@ResponseBody
	public ApiResponse queryStudemtDemandDetail(@RequestBody StudentDemandConnectForm demandForm) {
		
		StudentDemandVo  studentDemandVo = studentDemandsService.queryStudemtDemandDetail(demandForm);
		
		return ApiResponse.success(studentDemandVo);
	}
	
	@ApiOperation("校验报名参数")
	@RequestMapping(value = "/validateSignParameters", method = RequestMethod.POST)
	@ResponseBody
	public ApiResponse validateSignParameters(@RequestBody StudentDemandConnectForm demandForm) {
		
		Map<String , Object> map = studentDemandsService.validateSignParameters(demandForm);
		
		return ApiResponse.success(UtilTools.mapToJson(map));
	}
	
	
	@ApiOperation("确定订单试讲时间和状态")
	@RequestMapping(value = "/updateNewTrialDemand", method = RequestMethod.POST)
	@ResponseBody
	@Transactional
	public ApiResponse updateNewTrialDemand(@RequestBody StudentDemandConnectForm demandForm) {
		
		int i = studentDemandsService.updateNewTrialDemand(demandForm);
		
		if(i > 0) {
			return ApiResponse.success("保存成功！");
		}
		
		return ApiResponse.success("保存失败！");
	}
	
	@ApiOperation("报名学员发布的需求订单")
	@RequestMapping(value = "/signUpStudentDemand", method = RequestMethod.POST)
	@ResponseBody
	@Transactional
	public ApiResponse signUpStudentDemand(@RequestBody StudentDemandConnectForm demandForm) {
		
		int i = studentDemandsService.insert(demandForm);
		
		if(i > 0) {
			return ApiResponse.success("订单报名成功！");
		}
		
		if(i == -2) {
			return ApiResponse.error("该订单已接单，请报名其他订单！");
		}
		
		if(i == -3) {
			return ApiResponse.error("已报名该订单，请不要重复报名该订单！");
		}
		
		if(i == -4) {
			return ApiResponse.error("该订单已经被锁定，请稍后尝试报名该订单！");
		}
		
		if(i == -5) {
			return ApiResponse.error("您的身份信息还未审核通过，请至“我的”-“简历信息”中完善信息！");
		}
		
		return ApiResponse.error("订单报名失败！");
	}
	
}
