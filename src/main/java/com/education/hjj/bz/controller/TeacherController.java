package com.education.hjj.bz.controller;

import com.education.hjj.bz.formBean.StudentConnectTeacherForm;
import com.education.hjj.bz.formBean.TeachScreenForm;
import com.education.hjj.bz.formBean.TeacherScreenForm;
import com.education.hjj.bz.service.TeacherService;
import com.education.hjj.bz.util.ApiResponse;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    @PostMapping("/listSubject")
    @ResponseBody
    public ApiResponse listSubject(@RequestBody TeachScreenForm form) {
        return teacherService.listSubject(form);
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
}
