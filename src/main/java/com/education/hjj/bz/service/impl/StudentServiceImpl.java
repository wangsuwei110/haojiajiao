package com.education.hjj.bz.service.impl;

import com.education.hjj.bz.entity.vo.StudentVo;
import com.education.hjj.bz.formBean.LoginForm;
import com.education.hjj.bz.formBean.StudentForm;
import com.education.hjj.bz.mapper.StudentMapper;
import com.education.hjj.bz.service.StudentService;
import com.education.hjj.bz.util.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 学员表业务实现
 *
 * @创建者：sys
 * @创建时间：2019-9-29 0:30:25
 */
@Service("studentBiz")
public class StudentServiceImpl implements StudentService {

	@Autowired
    private StudentMapper studentMapper;

 	@Override
    public StudentVo findById(Long id) {
        return studentMapper.load(id);
    }

    @Override
    public StudentVo findByPhone(String phoneNum) {
        return studentMapper.findByPhone(phoneNum);
    }

    @Override
    public Integer add(LoginForm loginForm) {

 	    StudentForm studentForm = new StudentForm();

        studentForm.setParentPhoneNum(loginForm.getLoginPhone());
        studentForm.setSex(loginForm.getGender());
        studentForm.setPicture(loginForm.getHeadPicture());
 	    studentForm.setCreateTime(new Date());
 	    studentForm.setUpdateTime(new Date());
 	    studentForm.setCreateUser("admin");
 	    studentForm.setCreateUser("admin");
 	    studentForm.setDeleteStatus(0);

 	    return studentMapper.insert(studentForm);
    }

	@Override
    @Transactional(rollbackFor = Exception.class)
    public void updateNotNull(StudentForm student) {
 	    student.setUpdateTime(new Date());
 	    student.setUpdateUser(student.getSid().toString());
        studentMapper.updateNotNull(student);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateOpenIdByStudentId(String openId, Long studentId) {

        StudentForm studentForm = new StudentForm();
        studentForm.setOpenId(openId);
        studentForm.setSid(studentId);
        studentForm.setUpdateTime(new Date());
        studentForm.setUpdateUser(studentId.toString());

        studentMapper.updateNotNull(studentForm);
    }

    @Override
    public void deleteById(Long id) {
        studentMapper.delete(id);
    }

    @Override
    public ApiResponse findBySid(StudentForm form) {
        List<StudentVo> list = new ArrayList<>();
 	    if (form.getSid() == null) {
            return ApiResponse.error("当前员工不存在");
        }

        // 根据查询类型区分检索的结果,type 为1，则检索自己所有孩子的信息
        if (form.getFindType() != null && form.getFindType() == 1) {
            list =  studentMapper.loadAll(form.getSid());
        } else {
            StudentVo vo = studentMapper.load(form.getSid());
            list.add(vo);
        }
        return ApiResponse.success(list);
    }
 }
