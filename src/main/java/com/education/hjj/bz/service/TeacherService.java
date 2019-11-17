package com.education.hjj.bz.service;

import com.education.hjj.bz.entity.vo.TeacherVo;
import com.education.hjj.bz.formBean.StudentConnectTeacherForm;
import com.education.hjj.bz.formBean.TeachScreenForm;
import com.education.hjj.bz.formBean.TeachUniversityForm;
import com.education.hjj.bz.formBean.TeacherScreenForm;
import com.education.hjj.bz.util.ApiResponse;

/**
 * 教师表业务接口
 *
 * @创建者：sys
 * @创建时间：2019-10-9 21:50:41
 */
public interface TeacherService{
	
	/**
     * 根据ID查询
     * @param id
     * @return
     */
    TeacherVo findById(Long id);
	
    /**
    * 分页查询
    * @param form
    * @return
    */
    ApiResponse listPage(TeacherScreenForm form);

    /**
     * 教员下拉框集合
     * @return
     */
    ApiResponse selectList();

    /**
     * 科目下拉框集合
     * @return
     */
    ApiResponse listSubject(TeachScreenForm form);

    /**
     * 科目二级联动
     * @return
     */
    ApiResponse findAllSubject(TeachScreenForm form);

    /**
     * 回显科目信息
     * @return
     */
    ApiResponse findSubject(TeachScreenForm form);


    /**
     * 教员大学筛选
     * @return
     */
    ApiResponse listUniversity(TeachUniversityForm form);

    /**
     * 关联学员和教员信息
     * @return
     */
    ApiResponse connect(StudentConnectTeacherForm form);

    /**
     * 收藏教员列表信息
     * @return
     */
    ApiResponse connectList(StudentConnectTeacherForm form);

    /**
     * 取消收藏教员
     * @return
     */
    ApiResponse cancelConnect(StudentConnectTeacherForm form);
}
