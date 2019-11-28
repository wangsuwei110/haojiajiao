package com.education.hjj.bz.service.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Supplier;
import java.util.stream.Stream;

import com.alibaba.fastjson.JSON;
import com.education.hjj.bz.entity.*;
import com.education.hjj.bz.entity.vo.*;
import com.education.hjj.bz.mapper.*;
import com.education.hjj.bz.service.StudentLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.education.hjj.bz.formBean.DemandCourseInfoForm;
import com.education.hjj.bz.formBean.DemandLogForm;
import com.education.hjj.bz.formBean.StudentDemandConnectForm;
import com.education.hjj.bz.formBean.StudentDemandForm;
import com.education.hjj.bz.formBean.StudentForm;
import com.education.hjj.bz.mapper.DemandCourseInfoMapper;
import com.education.hjj.bz.mapper.DemandLogMapper;
import com.education.hjj.bz.mapper.StudentDemandConnectMapper;
import com.education.hjj.bz.mapper.StudentDemandMapper;
import com.education.hjj.bz.mapper.StudentMapper;
import com.education.hjj.bz.mapper.TeachBranchMapper;
import com.education.hjj.bz.mapper.TeacherMapper;
import com.education.hjj.bz.mapper.UserInfoMapper;
import com.education.hjj.bz.service.StudentDemandsService;
import com.education.hjj.bz.util.ApiResponse;
import com.education.hjj.bz.util.DateUtil;
import com.education.hjj.bz.util.common.CommonUtil;
import com.education.hjj.bz.util.common.StringUtil;

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

	@Autowired
	private UserInfoMapper userInfoMapper;

	@Autowired
	private DemandLogMapper demandLogMapper;

	@Autowired
	private DemandCourseInfoMapper demandCourseInfoMapper;

	@Autowired
	private UserAccountMapper userAccountMapper;

	@Autowired
	private UserAccountLogMapper userAccountLogMapper;

	@Autowired
	private StudentLogService studentLogService;

	@Override
	public Map<String, Object> queryStudentDemandDetail(String demandId) {
		Map<String, Object> map = new HashMap<String, Object>();
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
		StudentVo studentVo = studentMapper.load((long) form.getStudentId());

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
		form.setCurrentWeekDay(DateUtil.getWeekOfDate(date));
		form.setPaymentStreamId(UUID.randomUUID().toString().replaceAll("-", ""));
		studentDemandMapper.addStudentDemandByTeacher(form);

		// 如果有教员ID，则插入一条关联教员的表数据
		if (form.getTeacherId() != null) {
			// 判断改教员是否曾经试讲过同等年级同等科目的订单
			StudentDemandConnectForm alreadyForm = new StudentDemandConnectForm();
			alreadyForm.setStudentId(form.getStudentId());
			alreadyForm.setTeacherId(form.getTeacherId());
			alreadyForm.setStudentId(form.getStudentId());
			alreadyForm.setSubjectId(form.getSubjectId());
			alreadyForm.setDemandGrade(form.getDemandGrade());
			Integer count = connectMapper.countAlreadyDemand(alreadyForm);

			Long newOrderId = form.getSid();
			StudentDemandConnectForm connectForm = new StudentDemandConnectForm();
			connectForm.setCreateTime(date);
			if (count != null && count > 0) {
				connectForm.setStatus(7);
			} else {
				connectForm.setStatus(1);
			}
			connectForm.setDeleteStatus(0);
			connectForm.setCreateUser(form.getStudentId());
			connectForm.setDemandId(newOrderId.intValue());
			connectForm.setTeacherId(form.getTeacherId());
			connectMapper.insert(connectForm);

			TeacherVo teacherVo = teacherMapper.load(form.getTeacherId());
			// 插入一条预约教员的日志信息
			StudentLogPo logPo = new StudentLogPo();
			logPo.setStudentId(Integer.valueOf(studentVo.getSid().toString()));
			logPo.setLogType(3); // 登录
			logPo.setLogContent("最近预约了" +teacherVo.getName() + "教员的信息");
			logPo.setStudentName(studentVo.getStudentName());
			logPo.setStatus(1);
			logPo.setCreateTime(new Date());
			logPo.setCreateUser(studentVo.getSid().toString());
			logPo.setUpdateTime(new Date());
			logPo.setUpdateUser(studentVo.getSid().toString());
			studentLogService.addStudentLog(logPo);
		}

		if (form.getSid() != null) {
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

		    // 编辑创建时间的格式
            f.setCreateTimeString(DateUtil.format(f.getCreateTime(), "yyyy-MM-dd hh:mm:ss"));

			// 编辑中文上班时间段
			f.setTimeRange(CommonUtil.getWeekDesc(f.getTimeRange()));
			// 编辑年级和科目
			Optional<TeachBranchVo> opBranch = supplier.get()
					.filter(b -> b.getTeachBranchId() == f.getSubjectId() && b.getTeachGradeId() == f.getDemandGrade())
					.findFirst();
			f.setGradeSubject(opBranch.isPresent() ? opBranch.get().getTeachBranchName() : "");

			// 判断需求订单的详情，是否有教员报名，是否已经预约，是否已经过了试讲时间
			List<StudentDemandConnectVo> connectVos = connectMapper.listConnectInfo(f.getSid());
			// 判断是否已经有了预约(排除已经预约过，但是未通过的)
			Optional<StudentDemandConnectVo> op = connectVos.stream()
					.filter(s ->  s.getStatus() != null && s.getStatus() != 0 && s.getStatus() != 3)
					.findFirst();
			if (op.isPresent()) {
				f.setSubscribeStatus(op.get().getStatus());
				f.setTeachName(op.get().getTeacherName());
				f.setAppraise(op.get().getAppraise());
				f.setChargesStandard(op.get().getChargesStandard());
				f.setOrderTeachTime(op.get().getOrderTeachTime());
				f.setTeacherId(op.get().getTeacherId());

				if (StringUtil.isNotBlank(op.get().getChargesStandard())) {
                    f.setOrderMoney(new BigDecimal(op.get().getChargesStandard().split("元")[0]));
                }

				// 如果已经过了试讲的试讲时间，则赋值6，前端判断显示试讲通过和试讲不通过
				if (op.get().getOrderTeachTime() != null && new Date().after(DateUtil.addMinute(op.get().getOrderTeachTime(), 5))) {
					f.setSubscribeStatus(6);
				}
			} else {
//				// 判断是否已经有了预约(排除已经预约过，但是未通过的)
//				List<StudentDemandConnectVo> noPass = connectVos.stream()
//						.filter(s ->  s.getStatus() != null && s.getStatus() == 3).collect(Collectors.toList()).stream()
//						.sorted((a, b) -> a.getOrderTeachTime().compareTo(b.getOrderTeachTime())).collect(Collectors.toList());
				// 确认订单只是有教员报名或者没有报名，没有其它状态
				boolean allStatusIsZero = connectVos.stream()
						.allMatch(s ->  s.getStatus() == null || s.getStatus() == 0);
				if (f.getDemandType() == 1 && !allStatusIsZero) {
					f.setSubscribeStatus(3);
				} else {
					f.setEndDemandFlag(true);
					// 没有预约成功的，显示预约教员的数量
					f.setOrderTeachCount(org.apache.shiro.util.CollectionUtils.isEmpty(connectVos) ? 0 : connectVos.size());
				}
			}

		});
		return ApiResponse.success(list);
	}

	@Override
	public ApiResponse listTeacher(StudentDemandConnectForm demandForm) {
		// 根据学员的id查找预约的教员列表信息
		Map<String, Object> map = new HashMap<>();
		List<TeacherVo> list = teacherMapper.listTeacherByStudentId(demandForm.getDemandId());
		list.forEach(f -> {
			f.setUnitPrice(Double.valueOf(f.getChargesStandard().split("元")[0]));
		});
		map.put("teacherList", list);

		// 根据发布的需求，拿出具体的试讲时间
		StudentDemandVo vo = studentDemandMapper.queryStudentDemandDetailBySid(demandForm.getDemandId());

		Integer weekDay = vo.getCurrentWeekDay();
		List<OrderDemandTimeVo> orderDemandTimeVos = new ArrayList<>();

		List<WeekTimeVo> weekTimeVoList = JSON.parseArray(vo.getTimeRange(), WeekTimeVo.class);
		weekTimeVoList.forEach(w -> {
			OrderDemandTimeVo timeVo = new OrderDemandTimeVo();
			timeVo.setWeekDay(w.getWeek());
			timeVo.setTime(w.getTime());
			if (weekDay >= w.getWeek()) {
				timeVo.setDate(DateUtil.addDay(vo.getCreateTime(), 7));
			} else {
				timeVo.setDate(DateUtil.addDay(vo.getCreateTime(), w.getWeek() - weekDay));
			}
			orderDemandTimeVos.add(timeVo);
		});
		orderDemandTimeVos.sort((a, b) -> a.getTime().compareTo(b.getTime()));
		orderDemandTimeVos.sort((a, b) -> a.getDate().compareTo(b.getDate()));
		map.put("orderTime", orderDemandTimeVos);

		return ApiResponse.success(map);
	}

	@Override
	@Transactional
	public ApiResponse confirmTeacher(StudentDemandConnectForm demandForm) {

		// 单独预约的需求，确定教员时，订单变成试讲中
		if (demandForm.getDemandType() == null) {

			return ApiResponse.error("必须确定单独试讲或者快速请家教");
		} else if (demandForm.getDemandType() == 1) {
			// 单独预约
			demandForm.setStatus(1);
		} else {
			demandForm.setStatus(2);
		}
		Long sid = connectMapper.confirmTeacher(demandForm);

		if (sid != null) {
			return ApiResponse.success("预约教员成功");
		}

		return ApiResponse.success("预约教员失败");
	}

	/**
	 * 查询该学员的当前周的课程
	 **/
	@Override
	@Transactional
	public ApiResponse listMyCourse(DemandCourseInfoForm demandForm) {
		demandForm.setRangeForm(DateUtil.getWeekTime(demandForm.getOrderTeachTime()));

        List<DemandCourseInfoVo> list = demandCourseInfoMapper.listMyCourseList(demandForm);
        list.forEach(f -> {
            f.setUnitPrice(Double.valueOf(f.getChargesStandard().split("元")[0].toString()));
        });

		return ApiResponse.success(list);
	}

	/**
	 * 结课
	 **/
	public ApiResponse conclusion(DemandCourseInfoForm courseInfoForm) {
		Date date = new Date();

		courseInfoForm.setStatus(2); // 2:结课状态
		courseInfoForm.setUpdateTime(date);
		demandCourseInfoMapper.updateNotNull(courseInfoForm);

		logger.info("检索订单"+ courseInfoForm.getSid());
		// 检索订单
		StudentDemandVo vo = studentDemandMapper.findDemandByCourseId(courseInfoForm.getSid());

		// 结课时候，修改教员的总收入金额
		TeacherAccountVo teacherAccountVo = userAccountMapper.queryTeacherAccount(courseInfoForm.getTeacherId());
		TeacherVo teacherVo = teacherMapper.load(courseInfoForm.getTeacherId());

		TeacherAccountPo po = new TeacherAccountPo();
		po.setTeacherId(courseInfoForm.getTeacherId());
		if (teacherAccountVo == null) {

			po.setStatus(0);
			po.setCreateTime(date);
			po.setTeacherName(teacherVo.getName());
			po.setAccountMoney(new BigDecimal(teacherVo.getChargesStandard().split("元")[0].toString()));
			po.setTeacherPhone(teacherVo.getTelephone());
			po.setCreateUser(teacherVo.getTeacherId().toString());

			// 如果没有数据，则插入一条教员收支数据
			userAccountMapper.insertTeacherAccount(po);

		} else {
			po.setUpdateTime(date);
			po.setUpdateUser(courseInfoForm.getTeacherId().toString());
			po.setAccountMoney(teacherAccountVo.getAccountMoney().add(new BigDecimal(teacherVo.getChargesStandard().split("元")[0].toString())));
			userAccountMapper.updateTeacherAccountMoney(po);
		}

		// 插入一条日志信息，记录结课/支付记录
		TeacherAccountOperateLogPo paymentLog = new TeacherAccountOperateLogPo();
		paymentLog.setPaymentStreamId(vo.getPaymentStreamId());
		paymentLog.setPaymentPersonId(vo.getStudentId());
		paymentLog.setPaymentPersonName(vo.getStudentName());
		paymentLog.setPaymentType(3);
		paymentLog.setPaymentDesc("结课时支付");
		paymentLog.setStatus(0);
		paymentLog.setCreateTime(date);
		paymentLog.setCreateUser(vo.getStudentName());
		paymentLog.setUpdateTime(date);
		paymentLog.setUpdateUser(vo.getStudentName());
		paymentLog.setPaymentAccount(new BigDecimal(teacherVo.getChargesStandard().split("元")[0].toString()));
		userAccountLogMapper.insertUserAccountLog(paymentLog);

		return ApiResponse.success("结课成功");
	}

	/**
	 * 支付或续课
	 **/
	@Override
	@Transactional
	public ApiResponse payDemand(StudentDemandForm demandForm) {

		// 如果是试讲订单，要将试讲订单修改成付费订单
		StudentDemandVo demandVo = studentDemandMapper.findStudentDemandInfo(demandForm.getDemandId());

		if (demandVo == null) {
			return ApiResponse.error("订单不符合要求");
		}
		// 记录上个订单的信息
		Date date = new Date();
		DemandLogForm logForm = new DemandLogForm();
		logForm.setCreateTime(date);
		logForm.setMark(JSON.toJSONString(demandVo));
		logForm.setDemandId(demandForm.getDemandId());
		logForm.setCreateUser(demandVo.getStudentId().toString());

		demandLogMapper.insert(logForm);

		Integer weekDay = DateUtil.getWeekOfDate(date);
		demandForm.setCurrentWeekDay(weekDay);

		// 修改当前订单成新订单
		demandForm.setOrderType(2);
		demandForm.setOrderStart(date);
		demandForm.setUpdateTime(date);
		Long sid = studentDemandMapper.updateOldDemandToNew(demandForm);

		List<DemandCourseInfoForm> courseInfoFormList = new ArrayList<>();

		// 根据订单插入每个节课时
		for (int i = 0; i < demandForm.getWeekNum(); i++) {
			List<WeekTimeVo> list = JSON.parseArray(demandForm.getTimeRange(), WeekTimeVo.class);

			final Integer weekNum = i;
			list.forEach(w -> {
				DemandCourseInfoForm courseInfoForm = new DemandCourseInfoForm();
				if (weekDay >= w.getWeek()) {
					courseInfoForm.setOrderTeachTime(DateUtil.addDay(date, 7 + 7*weekNum));
				} else {
					courseInfoForm.setOrderTeachTime(DateUtil.addDay(date, w.getWeek() - weekDay + (7*weekNum)));
				}

				courseInfoForm.setStatus(0);
				courseInfoForm.setDeleteStatus(0);
				courseInfoForm.setCreateTime(date);
				courseInfoForm.setUpdateTime(date);
				courseInfoForm.setWeekNum(w.getWeek());
				courseInfoForm.setTimeNum(w.getTime());
				courseInfoForm.setDemandId(demandForm.getDemandId());
				courseInfoForm.setStudentId(demandVo.getStudentId());
				courseInfoForm.setTeacherId(demandVo.getTeacherId());
				courseInfoForm.setCreateUser(demandVo.getStudentId().toString());

				courseInfoFormList.add(courseInfoForm);
			});
		}

		demandCourseInfoMapper.insert(courseInfoFormList);

		return ApiResponse.success("支付课时成功");
	}

	@Override
	@Transactional
	public ApiResponse updateAdoptStatus(StudentDemandConnectForm demandForm) {
		// 判断是否通过或不通过
		if (demandForm.getStatus() == null) {
			return ApiResponse.success("订单状态不能为空");
		}
        demandForm.setUpdateTime(new Date());
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
			}
			// 试讲通过，则将其它报名的订单全部改成5
			demandForm.setStatus(5);
			connectMapper.updateStatusAndPass(demandForm);

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

	/**
	 * 查询支付记录
	 **/
	@Override
	public ApiResponse payLog(StudentDemandConnectForm demandForm) {
		if (demandForm.getPaymentStreamId() == null) {
			return ApiResponse.error("订单号不能为空");
		}
		TeacherAccountOperateLogPo po = new TeacherAccountOperateLogPo();
		po.setPaymentStreamId(demandForm.getPaymentStreamId());
		po.setPaymentType(3);

		return ApiResponse.success(userAccountLogMapper.listPayLog(po));
	}

	/**
	 * 查询支付记录
	 **/
	@Override
	public ApiResponse endDemand(StudentDemandConnectForm demandForm) {
		studentDemandMapper.endDemand(demandForm.getDemandId());

		return ApiResponse.success("已结束订单");
	}

	/**
	 * 主页信息
	 **/
	@Override
	public ApiResponse homepageInfo(StudentDemandConnectForm demandForm) {
		// 检索科目信息
        teachBranchMapper.queryAllTeachBranchs();
		return ApiResponse.success("已结束订单");
	}

	@Override
	public PageVo<List<StudentDemandVo>> queryAllStudentDemandList(StudentDemandForm form) {

		PageVo pageVo = new PageVo();

		List<StudentDemandVo> list = studentDemandMapper.queryAllStudentDemandList(form);

		pageVo.setTotal(list.size());
		pageVo.setDataList(list);

		return pageVo;
	}

	@Override
	public Map<String, Object> queryStudentDemandDetailBySid(Integer sid, Integer teacherId) {

		Map<String, Object> map = new HashMap<String, Object>(2);

		StudentDemandVo studentDemandDetail = studentDemandMapper.queryStudentDemandDetailBySid(sid);

		List<TeacherVo> list = userInfoMapper.queryStudentDemandSignUpTeacher(sid);
		

		boolean flag = false;

		if ( list != null && list.size() > 0 ) {

			for (TeacherVo t : list) {
				if (t.getTeacherId() == teacherId) {

					flag = true;

					break;
				}
			}

			map.put("signUpTeacherInfo", list);
			
		}else {
			map.put("signUpTeacherInfo", "");
		}
		
		logger.info("订单id:{} , 教员id:{} , 报名人数：{} ,是否报过名:{}" , sid , teacherId , list.size() ,flag);

		map.put("studentDemandDetail", studentDemandDetail);
		map.put("singUpStatus", flag);

		return map;
	}

	@Override
	public List<StudentDemandVo> queryNewTrialOrderList(Integer teacherId) {

		List<StudentDemandVo> newTrialStudentDemandList = studentDemandMapper.queryNewTrialOrderList(teacherId);

		return newTrialStudentDemandList;
	}

	@Override
	public List<StudentDemandVo> queryUserDemandsList(StudentDemandConnectForm demandForm) {

		String demandStatus = demandForm.getDemandSignStatus();

		Integer teacherId = demandForm.getTeacherId();

		logger.info("teacherId = {} , demandStatus = {}", teacherId, demandStatus);

		Map<String, Object> map = new HashMap<String, Object>();

		map.put("teacherId", teacherId);

		if (demandStatus != null && demandStatus.length() > 0) {

			String[] demandStatusList = demandStatus.split(",");

			List<Integer> demandStatusIntList = new ArrayList<Integer>();

			for (String s : demandStatusList) {

				demandStatusIntList.add(Integer.valueOf(s));
			}

			map.put("list", demandStatusIntList);

		} else {
			map.put("list", null);
		}

		List<StudentDemandVo> list = studentDemandMapper.queryUserDemandsList(map);

		return list;

	}

	@Override
	public StudentDemandVo queryStudemtDemandDetail(StudentDemandConnectForm demandForm) {

		StudentDemandVo studentDemandVo = studentDemandMapper.queryStudemtDemandDetail(demandForm);

		if (studentDemandVo != null) {

			Integer demandSignStatus = demandForm.getStatus();

			String timeRange = studentDemandVo.getTimeRange();
			logger.info("订单所选的讲课时间范围：{}", timeRange);

			// 新的试讲订单,未确认试讲时间的
			if (demandSignStatus == 1) {

				List<TeachTimePo> list = JSON.parseArray(timeRange, TeachTimePo.class);

				List<Map<String, Object>> teachTimePolist = new ArrayList<>();

				for (TeachTimePo tp : list) {

					Date date = new Date();
					// 今天日期对应的周几
					String weekDay = DateUtil.dateToWeek(DateUtil.getStandardDay(date));

					Date lastDateTime = new Date();

					// 学员所选的授课时段中,存在比当前日期(四)靠后的时间段(一、三、五)
					if (Integer.valueOf(weekDay) < Integer.valueOf(tp.getWeek())) {
						lastDateTime = DateUtil.addDay(date,
								(Integer.valueOf(tp.getWeek()) - Integer.valueOf(weekDay)));
					}

					if (Integer.valueOf(weekDay) > Integer.valueOf(tp.getWeek())) {
						lastDateTime = DateUtil.addDay(date, (Integer.valueOf(tp.getWeek()) + 7));
					}

					Map<String, Object> map = new HashMap<>();
					map.put("week", tp.getWeek());
					map.put("time", tp.getTime());
					map.put("weekDayTime", lastDateTime);

					teachTimePolist.add(map);

				}

				studentDemandVo.setTimeRange(JSON.toJSONString(teachTimePolist));
			}

			// 已支付订单详情
			if (demandSignStatus == 4) {

				// 订单
				Date orderStartTime = studentDemandVo.getOrderStart();
				//
				String weekDayString = DateUtil.getStandardDay(orderStartTime);

				// 订单支付时间所在星期几
				int weekDay = Integer.valueOf(DateUtil.dateToWeek(DateUtil.getStandardDay(orderStartTime)));

				logger.info("订单支付时的日期:{},所在当前周的星期{}", weekDayString, weekDay);

				List<TeachTimePo> list = JSON.parseArray(timeRange, TeachTimePo.class);

				// 订单一共报名几周
				int weekNum = studentDemandVo.getWeekNum();

				for (TeachTimePo tp : list) {

					if (Integer.valueOf(tp.getWeek()) < weekDay) {

						logger.info("订单第一次讲课开始的日期所在的星期：{} ，所选讲课时间范围内存在小于订单第一次开始讲课所在当天的课程，订单课程第一天所在周的周：{}", weekDay,
								Integer.valueOf(tp.getWeek()));

						weekNum = weekNum + 1;
					}
				}

				try {
					// 订单开始时所在的周的周一的日期
					String orderStartDate = DateUtil.getMonday(DateUtil.getStandardDay(orderStartTime));

					// 订单结束时所在的周的周日的日期
					String orderEndDate = DateUtil.getAfterDay(
							DateUtil.getSunday(DateUtil.getStandardDay(orderStartTime)), (weekNum - 1) * 7);

//					System.out.println(DateUtil.getMonday(DateUtil.getStandardDay(orderStartTime)));
//					System.out.println(DateUtil.getSunday(DateUtil.getStandardDay(orderStartTime)));
//					System.out.println("----------");
//					System.out.println(DateUtil.getAfterDay(DateUtil.getMonday(DateUtil.getStandardDay(orderStartTime)),
//							(weekNum - 1) * 7));
//					System.out.println(DateUtil.getAfterDay(DateUtil.getSunday(DateUtil.getStandardDay(orderStartTime)),
//							(weekNum - 1) * 7));

					studentDemandVo.setOrderStartDate(orderStartDate);
					studentDemandVo.setOrderEndDate(orderEndDate);

				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}

		return studentDemandVo;
	}

	@Override
	public List<StudentDemandVo> queryFitTeacherOrderList(Integer teacherId) {
		List<StudentDemandVo> list = studentDemandMapper.queryFitTeacherOrderList(teacherId);
		return list;
	}

	@Override
	@Transactional
	public int updateNewTrialDemand(StudentDemandConnectForm demandForm) {

		String orderTeachTime = demandForm.getOrderTeachTime();
		
		logger.info("教员ID = {} , 确定的试讲时间 = {}" , demandForm.getTeacherId() , orderTeachTime);

//		String nowDate = DateUtil.getStandardDay(new Date());
//
//		//确定的试讲时间在当前点击时间之后
//		boolean flag = DateUtil.compareTwoDate(nowDate, orderTeachTime);
//
//		logger.info("预约的订单试讲时间：{},现在的时间：{}", orderTeachTime, nowDate);
//
//		if (flag == true) {
//			demandForm.setStatus(2);
//		}
		demandForm.setStatus(2);
		demandForm.setUpdateTime(new Date());

		int j = studentDemandMapper.updateNewTrialDemandTime(demandForm);

		StudentDemandForm studentDemandForm = new StudentDemandForm();
		studentDemandForm.setStatus(2);
		studentDemandForm.setUpdateTime(new Date());
		studentDemandForm.setDemandId(demandForm.getDemandId());

		int i = studentDemandMapper.updateNewTrialDemandStatus(studentDemandForm);

		if (i >= 0 && j >= 0) {
			return 1;
		}

		return -1;
	}

	@Override
	public List<StudentDemandVo> queryAllStudentDemandListBy10(StudentDemandForm form) {
		List<StudentDemandVo> list = studentDemandMapper.queryAllStudentDemandListBy10(form);
		return list;
	}

	public List<StudentDemandVo> queryTimeTableByTeacherId(StudentDemandConnectForm demandForm) {

		// 查询给定日期的课表
		String orderTeachTime = demandForm.getOrderTeachTime();

		logger.info("查询给定日期课程表的时间：{}", orderTeachTime);
		
		List<StudentDemandVo> studentDemandlist = new ArrayList<>();
		try {
			// 订单开始时所在的周的周一的日期
			String orderStartDate = DateUtil.getMonday(orderTeachTime);

			// 订单结束时所在的周的周日的日期
			String orderEndDate = DateUtil.getSunday(orderTeachTime);


			StudentDemandPo studentDemandPo = new StudentDemandPo();
			studentDemandPo.setTeacherId(demandForm.getTeacherId());
			studentDemandPo.setOrderStartDate(orderStartDate);
			studentDemandPo.setOrderEndDate(orderEndDate);

			studentDemandlist = studentDemandMapper.queryTimeTableByTeacherId(studentDemandPo);

			return studentDemandlist;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

//		List<StudentDemandVo> studentDemandlistBetween = new ArrayList<StudentDemandVo>();
//
//		for (StudentDemandVo sdv : studentDemandlist) {
//
//			int weekNum = sdv.getWeekNum();
//
//			String timeRange = sdv.getTimeRange();
//
//			logger.info("订单持续周数：{}, 订单每周上课时间范围： {}", orderTeachTime, timeRange);
//
//			try {
//				// 订单结束时所在的周的周一的日期
//				String orderStartDate = DateUtil.getAfterDay(DateUtil.getMonday(orderTeachTime), (weekNum - 1) * 7);
//
//				Date date = DateUtil.tryConvert(orderStartDate);
//
//				List<TeachTimePo> teachTimelist = JSON.parseArray(timeRange, TeachTimePo.class);
//
//				// 最后一节课所在的日期大于当前传值所在周的周一,即将结果存入返回前端所需要的列表内
//				// 将数字转换为日期
//				for (TeachTimePo ttp : teachTimelist) {
//
//					Date lastDateTime = DateUtil.addDay(date, Integer.valueOf(ttp.getWeek()));
//
//					String lastDate = DateUtil.getStandardDay(lastDateTime);
//
//					boolean flag = DateUtil.compareTwoDate(orderTeachTime, lastDate);
//
//					StudentDemandVo sdvNew = new StudentDemandVo();
//
//					Map<String, Object> map = new HashMap<>();
//
//
//					if (flag == true) {
//						sdvNew.setSid(sdv.getSid());
//						sdvNew.setTeachName(sdv.getTeachName());
//						sdvNew.setStudentName(sdv.getStudentName());
//						sdvNew.setTeachBranchName(sdv.getTeachBranchName());
//
//						map.put("week", ttp.getWeek());
//						map.put("time", ttp.getTime());
//
//						sdvNew.setTimeRange(JSON.toJSONString(map));
//						sdvNew.setWeekNum(sdv.getWeekNum());
//						sdvNew.setDemandSignStatus(sdv.getDemandSignStatus());
//						sdvNew.setStatus(sdv.getStatus());
//						sdvNew.setOrderStart(sdv.getOrderStart());
//					}
//
//					studentDemandlistBetween.add(sdvNew);
//				}
//
//			} catch (ParseException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}

		return studentDemandlist;
	}

	@Override
	public int updateTimeTableByTeacherId(StudentDemandPo studentDemandPo) {


		studentDemandPo.setUpdateTime(new Date());

		Integer teacherId = studentDemandPo.getTeacherId();

		Integer studentId = studentDemandPo.getStudentId();

		//结课
		if(teacherId == null && studentId!= null) {
			studentDemandPo.setStatus(2);
		}

		//打卡
		if(teacherId != null && studentId == null) {
			studentDemandPo.setStatus(1);
		}

		int i = studentDemandMapper.updateTimeTableByTeacherId(studentDemandPo);

		return i;
	}

	@Override
	public int insert(StudentDemandConnectForm studentDemandConnect) {
		
		int count = connectMapper.getCount(studentDemandConnect);
		
		if(count > 0) {
			logger.info("已报名该订单，请不要重复报名该订单！");
			return -3;
		}
		
		int num = connectMapper.querySignUpPersonByDemandId(studentDemandConnect);
		
		if(num > 0) {
			logger.info("该订单已经被锁定，请稍后尝试报名该订单！");
			return -4;
		}
		
		studentDemandConnect.setStatus(0);
		studentDemandConnect.setDeleteStatus(0);
		studentDemandConnect.setCreateTime(new Date());
		studentDemandConnect.setCreateUser(studentDemandConnect.getTeacherId());
		
		int i = connectMapper.insert(studentDemandConnect);
		
		return i;
	}
	
	@Override
	public Map<String , Object> validateSignParameters(StudentDemandConnectForm demandForm) {
		
		boolean flag = true;
		
		boolean timeFlag = false;
		
		Map<String , Object> map = new HashMap<String , Object>();
		
		int teacherId = demandForm.getTeacherId();

		TeacherVo t = userInfoMapper.queryTeacherHomeInfos(teacherId);
		
		String teachBranchIdDB = t.getTeachBrance()+","+t.getTeachBranchSlave();
		
		int teachBranchId =demandForm.getTeachBranchId();
		
		logger.info("订单需求授课科目id:{} , 报名教员的授课科目id: {}" , teachBranchId , teachBranchIdDB);
		
			
		if(!teachBranchIdDB.contains(String.valueOf(teachBranchId))) {
			flag = false;
			
			map.put("validateCode", flag);
			map.put("validateResult", "该需求的科目不在您的授课范围内，请谨慎报名。");
			
			return map;
		}
		
		int teachGradeId = demandForm.getTeachGradeId();
		String teachGradeIdDB = t.getTeachGrade();
		
		logger.info("订单需求授课年级id:{} , 报名教员的授课年级id: {}" , teachGradeId , teachGradeIdDB);
		
		if(!teachGradeIdDB.contains(String.valueOf(teachGradeId))) {
			flag = false;
			
			map.put("validateCode", flag);
			map.put("validateResult", "该需求的年级不在您的授课范围内，请谨慎报名。");
			
			return map;
		}
		
		int areaId = demandForm.getParameterId();
		
		String teachAddressDB = t.getTeachAddress();
		
		logger.info("订单需求授课区域id:{} , 报名教员的授课区域id: {}" , areaId , teachAddressDB);
		
		if(teachAddressDB.contains(String.valueOf(areaId))) {
			
			flag = false;
			
			map.put("validateCode", flag);
			map.put("validateResult", "该需求的上课区域不在您的授课区域内，请谨慎报名。");
			
			return map;
		}
		
		String teachTimeDB = t.getTeachTime();
		
		List<TeachTimePo> teachTimePoa = demandForm.getTimeList();
		
		List<TeachTimePo> teachTimePob = JSON.parseArray(teachTimeDB, TeachTimePo.class);
		
		logger.info("订单需求授课时间:{} , 报名教员的授课时间: {}" , JSON.toJSONString(demandForm.getTimeList()) , teachTimeDB);
		
		for(TeachTimePo ttpa:teachTimePoa) {
			
			for(TeachTimePo ttpb:teachTimePob) {
				
				if(ttpa.getTime().equalsIgnoreCase(ttpb.getTime()) && ttpa.getWeek().equalsIgnoreCase(ttpb.getWeek())) {
					timeFlag = true;
				}
			}
			
		}
		
		if(timeFlag == false) {
			
			flag = false;
			
			map.put("validateCode", flag);
			map.put("validateResult", "该需求的预计上课时段不在您的授课时间内，请谨慎报名。");
			
			return map;
		}
		
		
		map.put("validateCode", true);
		map.put("validateResult", "可以报名。");
		
		return map;
		
	}
}
