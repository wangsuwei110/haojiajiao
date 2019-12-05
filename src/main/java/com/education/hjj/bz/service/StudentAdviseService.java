package com.education.hjj.bz.service;

import com.education.hjj.bz.entity.vo.StudentAdviseVo;
import com.education.hjj.bz.formBean.StudentAdviseForm;

import java.util.List;

/**
 * 校长邮箱(家长建议)表业务接口
 *
 * @创建者：sys
 * @创建时间：2019-10-8 22:03:47
 */
public interface StudentAdviseService{
	
	/**
     * 添加
     * @param studentAdvise
     * @return 新增对象ID
     */
    void add(StudentAdviseForm studentAdvise);

    /**
    * 分页查询
    * @param form
    * @return
    */
    List<StudentAdviseVo> listPage(StudentAdviseForm form);
    
    List<StudentAdviseVo> queryAllStudentAdviseByEducational(StudentAdviseForm studentAdviseForm);

}
