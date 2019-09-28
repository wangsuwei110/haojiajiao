package com.education.hjj.bz.service.impl;

import com.education.hjj.bz.entity.vo.PageVo;
import com.education.hjj.bz.entity.vo.StudentVo;
import com.education.hjj.bz.formBean.StudentForm;
import com.education.hjj.bz.mapper.StudentMapper;
import com.education.hjj.bz.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    public void add(StudentForm student) {
        studentMapper.insert(student);
    }

	@Override
    public void updateNotNull(StudentForm student) {
        studentMapper.updateNotNull(student);
    }

    @Override
    public void deleteById(Long id) {
        studentMapper.delete(id);
    }

    @Override
    public PageVo<StudentVo> listPage(StudentForm form) {
        int total = studentMapper.getCount(form);
        if (total == 0) {
            return new PageVo<>(total, new ArrayList<StudentVo>());
        }
        List<StudentVo> list = studentMapper.list(form);
        return new PageVo<>(total, list);
    }

 }
