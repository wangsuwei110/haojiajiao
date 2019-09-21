package com.education.hjj.bz.service.impl;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.education.hjj.bz.entity.StudentDemandPo;
import com.education.hjj.bz.entity.TeacherAndStudentDemandPo;
import com.education.hjj.bz.entity.vo.PageVo;
import com.education.hjj.bz.entity.vo.StudentDemandVo;
import com.education.hjj.bz.entity.vo.TeacherVo;
import com.education.hjj.bz.formBean.StudentDemandForm;
import com.education.hjj.bz.mapper.StudentDemandMapper;
import com.education.hjj.bz.service.StudentDemandsService;
import com.education.hjj.bz.util.DateUtil;

@Service
@Transactional
public class StudentDemandsServiceImpl implements StudentDemandsService {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private StudentDemandMapper studentDemandMapper;
	

	@Override
	public PageVo<List<StudentDemandVo>> queryStudentDemands(StudentDemandForm studentDemandForm) {
		
		PageVo pageVo = new PageVo();
		
		StudentDemandPo studentDemandPo = new StudentDemandPo();
		studentDemandPo.setDemandId(Integer.valueOf(studentDemandForm.getDemandId()));
		studentDemandPo.setDemandGrade(studentDemandForm.getDemandGrade());
		studentDemandPo.setDemandBranch(Integer.valueOf(studentDemandForm.getDemandBranch()));
		studentDemandPo.setStudnetArea(studentDemandForm.getStudnetArea());
		
		List<StudentDemandVo> list=studentDemandMapper.queryStudentDemands(studentDemandPo);
		
		pageVo.setDataList(list);
		
		return pageVo;
	}

	@Override
	public Map<String, Object> queryStudentDemandDetail(String demandId) {
		Map<String, Object> map=new HashMap<String, Object>();
		StudentDemandVo studentDemand = studentDemandMapper.queryStudentDemandDetail(Integer.valueOf(demandId));
		map.put("createTime", DateUtil.covertFromDateToString(studentDemand.getCreateTime()));
		map.put("demandAddress", studentDemand.getDemandAddress());
		map.put("demandBranch", studentDemand.getDemandBranch());
		//上课时段以日期|时间段,日期|时间段拼接
		String demandTeachTimes = studentDemand.getTeachTime();
		List<String> list = Arrays.asList(demandTeachTimes.split(","));
		map.put("demandTeachTime", list);
		
		
		//根据需求ID查询选定教员的信息
		TeacherVo teacher = studentDemandMapper.queryTeacherStudentDemands(Integer.valueOf(demandId));
		map.put("teacherName", teacher.getName());
		map.put("teacherLevel", teacher.getTeacherLevel());
		map.put("teacherPicture", teacher.getPicture());
		
		return map;
	}

	@Override
	public int addStudentDemandByTeacher(String demandId , TeacherVo teacher) {
		
		TeacherAndStudentDemandPo teacherAndStudentDemand = new TeacherAndStudentDemandPo();
		teacherAndStudentDemand.setDemandId(Integer.valueOf(demandId));
		teacherAndStudentDemand.setTeacherId(teacher.getTeacherId());
		teacherAndStudentDemand.setTeacherName(teacher.getName());
		teacherAndStudentDemand.setTeacherPhone(teacher.getTelephone());
		teacherAndStudentDemand.setChooseStatus(0);
		teacherAndStudentDemand.setStatus(1);
		teacherAndStudentDemand.setCreateTime(new Date());
		teacherAndStudentDemand.setCreateUser("admin");
		teacherAndStudentDemand.setUpdateTime(new Date());
		teacherAndStudentDemand.setUpdateUser("admin");

		int i = studentDemandMapper.addStudentDemandByTeacher(teacherAndStudentDemand);
		return i;
	}
	

	

}
