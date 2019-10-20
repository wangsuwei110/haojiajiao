package com.education.hjj.bz.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.education.hjj.bz.entity.vo.PageVo;
import com.education.hjj.bz.entity.vo.PointsLogVo;
import com.education.hjj.bz.entity.vo.TeacherVo;
import com.education.hjj.bz.formBean.PointsLogForm;
import com.education.hjj.bz.service.PointsLogService;
import com.education.hjj.bz.service.UserInfoService;
import com.education.hjj.bz.util.ApiResponse;
import com.education.hjj.bz.util.UtilTools;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = { "用户积分" })
@RestController
@RequestMapping(value = "/order")
public class PointsLogController {

	@Autowired
	private PointsLogService pointsLogService;
	
	@Autowired
	private UserInfoService userInfoService;


	@ApiOperation("用户积分查询")
	@RequestMapping(value = "/queryAllPointsLogByTeacherId", method = RequestMethod.POST)
	public ApiResponse queryAllPointsLogByTeacherId(@RequestBody PointsLogForm pointsLogForm) {
		
		Integer teacherId = pointsLogForm.getTeacherId();
		
		Map<String , Object> map = new HashMap<String , Object>(2);

		PageVo<List<PointsLogVo>> list = pointsLogService.queryAllPointsLogByTeacherId(pointsLogForm);
		
		TeacherVo tv = userInfoService.queryTeacherHomeInfos(String.valueOf(teacherId));
		map.put("totalPoints", tv.getTeacherPoints());
		map.put("pointsLog", list);


		return ApiResponse.success("查询成功" , UtilTools.mapToJson(map));
	}
	
}
