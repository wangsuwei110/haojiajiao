package com.education.hjj.bz.service;

import com.education.hjj.bz.entity.vo.PageVo;
import com.education.hjj.bz.entity.vo.StudentVo;
import com.education.hjj.bz.formBean.StudentForm;

/**
 * 学员表业务接口
 *
 * @创建者：sys
 * @创建时间：2019-9-29 0:30:25
 */
public interface StudentService{
	
	/**
     * 根据ID查询
     * @param id
     * @return
     */
    StudentVo findById(Long id);

    /**
     * 根据手机号查询
     * @param phoneNum
     * @return
     */
    StudentVo findByPhone(String phoneNum);
	
	/**
     * 添加
     * @param student
     * @return 新增对象ID
     */
    void add(StudentForm student);

    /**
     * 更新不为空的属性
     * @param student
     * @return
     */
    void updateNotNull(StudentForm student);
    
    /**
     * 根据ID逻辑删除
     * @param id
     * @return
     */
    void deleteById(Long id);


    /**
    * 分页查询
    * @param form
    * @return
    */
    PageVo<StudentVo> listPage(StudentForm form);

}
