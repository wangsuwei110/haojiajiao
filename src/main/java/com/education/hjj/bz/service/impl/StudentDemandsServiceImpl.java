package com.education.hjj.bz.service.impl;

import com.education.hjj.bz.entity.StudentDemandPo;
import com.education.hjj.bz.entity.vo.*;
import com.education.hjj.bz.formBean.StudentConnectTeacherForm;
import com.education.hjj.bz.formBean.StudentDemandConnectForm;
import com.education.hjj.bz.formBean.StudentDemandForm;
import com.education.hjj.bz.formBean.StudentForm;
import com.education.hjj.bz.mapper.*;
import com.education.hjj.bz.service.StudentDemandsService;
import com.education.hjj.bz.util.ApiResponse;
import com.education.hjj.bz.util.DateUtil;
import com.education.hjj.bz.util.common.CommonUtil;
import com.education.hjj.bz.util.common.StringUtil;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Stream;

@Service
@Transactional
public class StudentDemandsServiceImpl implements StudentDemandsService {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private StudentDemandMapper studentDemandMapper;

	@Autowired
	private StudentDemandConnectMapper connectMapper;

	@Autowired
	private TeachBranchMapper teachBranchMapper;

	@Autowired
	private TeacherMapper teacherMapper;

	@Autowired
	private StudentMapper studentMapper;


	@Override
	public PageVo<List<StudentDemandVo>> queryStudentDemands(StudentDemandForm studentDemandForm) {
		
		PageVo pageVo = new PageVo();

		// 订单列表信息


		StudentDemandPo studentDemandPo = new StudentDemandPo();

//		List<StudentDemandVo> list=studentDemandMapper.queryStudentDemands(studentDemandPo);
		
//		pageVo.setDataList(list);
		
		return pageVo;
	}

	@Override
	public Map<String, Object> queryStudentDemandDetail(String demandId) {
		Map<String, Object> map=new HashMap<String, Object>();
//		StudentDemandVo studentDemand = studentDemandMapper.queryStudentDemandDetail(Integer.valueOf(demandId));
//		map.put("createTime", DateUtil.covertFromDateToString(studentDemand.getCreateTime()));
//		map.put("demandAddress", studentDemand.getDemandAddress());
//		map.put("demandBranch", studentDemand.getDemandBranch());
//		//上课时段以日期|时间段,日期|时间段拼接
//		String demandTeachTimes = studentDemand.getTeachTime();
//		List<String> list = Arrays.asList(demandTeachTimes.split(","));
//		map.put("demandTeachTime", list);
//
//
//		//根据需求ID查询选定教员的信息
//		TeacherVo teacher = studentDemandMapper.queryTeacherStudentDemands(Integer.valueOf(demandId));
//		map.put("teacherName", teacher.getName());
//		map.put("teacherLevel", teacher.getTeacherLevel());
//		map.put("teacherPicture", teacher.getPicture());
		
		return map;
	}

	@Override
	@Transactional
	public ApiResponse addStudentDemandByTeacher(StudentDemandForm form) {
		Date date = new Date();
		form.setCreateTime(date);
		form.setCreateUser(form.getStudentId().toString());
		form.setStatus(0);
		// 首先判断学员id和对应姓名是否相同，不同则插入一条学员信息
		StudentVo studentVo = studentMapper.load((long)form.getStudentId());

		if (StringUtil.isNotBlank(form.getStudentName())
				&& !studentVo.getStudentName().trim().equals(form.getStudentName())) {
			StudentForm student = new StudentForm();
			student.setCreateTime(date);
			student.setDeleteStatus(0);
			student.setSex(form.getSex());
			student.setOpenId(studentVo.getOpenId());
			student.setSubjectId(form.getSubjectId());
			student.setGrade(form.getDemandGrade());
			student.setPicture(studentVo.getPicture());
			student.setStudentName(form.getStudentName());
			student.setCreateUser(studentVo.getParentPhoneNum());
			student.setParentPhoneNum(studentVo.getParentPhoneNum());
			// 学员姓名不一致，插入一条新数据
			Long sid = studentMapper.insert(student);
			if (sid == null) {
				return ApiResponse.success("新学员信息添加失败");
			}
			Long newStudentId = studentMapper.findMaxSid();
			form.setStudentId(Integer.valueOf(newStudentId.toString()));
		}

		// 插入需求，返回需求id
		Long orderId = studentDemandMapper.addStudentDemandByTeacher(form);

		// 如果有教员ID，则插入一条关联教员的表数据
		if (form.getTeacherId() != null) {
			Long newOrderId = studentDemandMapper.findMaxSid();
			StudentDemandConnectForm connectForm = new StudentDemandConnectForm();
			connectForm.setCreateTime(date);
			connectForm.setStatus(1);
			connectForm.setDeleteStatus(0);
			connectForm.setCreateUser(form.getStudentId());
			connectForm.setDemandId(newOrderId.intValue());
			connectForm.setTeacherId(form.getTeacherId());
			connectMapper.insert(connectForm);
		}

		if (orderId != null) {
			return ApiResponse.success("订单发布成功，一个工作日内处理");
		}
		return ApiResponse.error("订单发布失败，请重新发布");
	}
	
	@Override
	public ApiResponse listDemand(StudentDemandConnectForm demandForm) {
		// 优先检索需求列表
		List<StudentDemandVo> list = studentDemandMapper.listDemand(demandForm);
		List<TeachBranchVo> branchVos = teachBranchMapper.queryAllTeachBranchs();
		Supplier<Stream<TeachBranchVo>> supplier = () -> branchVos.stream();
		// 然后根据列表区判断需求的状态类型
		// 检索需求报名的教员信息
		list.forEach(f -> {
			// 编辑中文上班时间段
			f.setTimeRange(CommonUtil.getWeekDesc(f.getTimeRange()));
			// 编辑年级和科目
			Optional<TeachBranchVo> opBranch = supplier.get().filter(b -> b.getTeachLevelId() == f.getDemandGrade()
						&& b.getTeachGradeId() == f.getSubjectId()).findFirst();
			f.setGradeSubject(opBranch.isPresent() ? opBranch.get().getTeachBranchName() : "");

			// 判断需求订单的详情，是否有教员报名，是否已经预约，是否已经过了试讲时间
			List<StudentDemandConnectVo> connectVos = connectMapper.listConnectInfo(f.getSid());
			//判断是否已经有了预约(排除已经预约过，但是未通过的)
			Optional<StudentDemandConnectVo> op = connectVos.stream().filter(s -> s.getOrderTeachTime() != null
				&& s.getStatus() != null && s.getStatus() != 4).findFirst();
			if (op.isPresent()) {
				f.setSubscribeStatus(op.get().getStatus());
				f.setTeachName(op.get().getTeacherName());
				f.setAppraise(op.get().getAppraise());

				if (!DateUtil.getDayStart(new Date()).after(DateUtil.getDayStart(op.get().getOrderTeachTime()))) {
					f.setOrderTeachTime(op.get().getOrderTeachTime());
				}
			} else {
				// 没有预约成功的，显示预约教员的数量
				f.setOrderTeachCount(connectVos.size());
			}

		});
		return ApiResponse.success(list);
	}
	

	@Override
	public ApiResponse listTeacher(StudentDemandConnectForm demandForm) {
		// 根据学员的id查找预约的教员列表信息
		return ApiResponse.success(teacherMapper.listTeacherByStudentId(demandForm.getDemandId()));
	}

	@Override
	@Transactional
	public ApiResponse confirmTeacher(StudentDemandConnectForm demandForm) {
		Long sid = connectMapper.confirmTeacher(demandForm);

		if (sid != null) {
			return ApiResponse.success("预约教员成功");
		}

		return ApiResponse.success("预约教员失败");
	}

	@Override
	@Transactional
	public ApiResponse updateAdoptStatus(StudentDemandConnectForm demandForm) {
		// 判断是否通过或不通过
		if (demandForm.getStatus() == null) {
			return ApiResponse.success("订单状态不能为空");
		}
		// 试讲通过
		Long sid = connectMapper.updateStatus(demandForm);
		if (sid != null) {
			// 试讲不通过，返回三个形态信息
			if (demandForm.getStatus() == 3) {
				// 首先查下订单类型，区分是快速请家教或者单独预约，如果是快速请家教，再区分当前试讲未通过是不是唯一报名的教员
				List<StudentDemandVo> studentDemandVos = studentDemandMapper.listDemandAndTeacher(demandForm);
				if (CollectionUtils.isEmpty(studentDemandVos)) {
					return ApiResponse.success("状态修改失败");
				} else if (studentDemandVos.size() > 1) {
					return ApiResponse.success("感谢您的配合，是否再预约之前报名的其他教员进行试讲? 或再等等可能会有更多优秀的教员来报名。", false);
				} else {
					Integer demandType = studentDemandVos.get(0).getDemandType();
					// 单独预约的订单
					if (demandType == 1) {
						return ApiResponse.success("感谢您的配合，是否开放本需求让更多教员来报名?", true);
					} else {
						return ApiResponse.success("感谢您的配合，再等等可能会有更多优秀的教员来报名。", false);
					}
				}
				// 1.单独预约型

			}
			return ApiResponse.success("状态修改成功");
		}
		return ApiResponse.success("状态修改失败");
	}

	/**
	 * 开放订单
	 **/
	@Override
	@Transactional
	public ApiResponse openDemand(StudentDemandConnectForm demandForm) {
		demandForm.setUpdateTime(new Date());
		Long sid = studentDemandMapper.openDemand(demandForm);
		if (sid != null) {
			return ApiResponse.success("订单开放成功");
		}
		return ApiResponse.success("订单开放失败");
	}
}
