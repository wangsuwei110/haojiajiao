package com.education.hjj.bz.service.impl;

import com.education.hjj.bz.entity.vo.StudentAdviseVo;
import com.education.hjj.bz.formBean.StudentAdviseForm;
import com.education.hjj.bz.mapper.StudentAdviseMapper;
import com.education.hjj.bz.service.StudentAdviseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 校长邮箱(家长建议)表业务实现
 *
 * @创建者：sys
 * @创建时间：2019-10-8 22:03:47
 */
@Service("studentAdviseBiz")
public class StudentAdviseServiceImpl implements StudentAdviseService {

	@Autowired
    private StudentAdviseMapper studentAdviseMapper;

    @Override
    public void add(StudentAdviseForm studentAdvise) {
        studentAdvise.setCreateTime(new Date());
        studentAdvise.setCreateUser(studentAdvise.getStudentId());
        studentAdviseMapper.insert(studentAdvise);
    }

    @Override
    public List<StudentAdviseVo> listPage(StudentAdviseForm form) {
        List<StudentAdviseVo> list = studentAdviseMapper.list(form);
        return list;
    }

	@Override
	public List<StudentAdviseVo> queryAllStudentAdviseByEducational(StudentAdviseForm studentAdviseForm) {
		List<StudentAdviseVo> list = studentAdviseMapper.queryAllStudentAdviseByEducational(studentAdviseForm);
		return list;
	}

	@Override
	public int updateNotNull(StudentAdviseForm studentAdviseForm) {
		int i = studentAdviseMapper.updateNotNull(studentAdviseForm);
		return i;
	}

 }
