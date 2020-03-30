package com.education.hjj.bz.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.education.hjj.bz.entity.PointsLogPo;
import com.education.hjj.bz.entity.StudentDemandPo;
import com.education.hjj.bz.entity.StudentLogPo;
import com.education.hjj.bz.entity.TeachTimePo;
import com.education.hjj.bz.entity.TeacherAccountOperateLogPo;
import com.education.hjj.bz.entity.TeacherAccountPo;
import com.education.hjj.bz.entity.TeacherPo;
import com.education.hjj.bz.entity.vo.CodeInfoVo;
import com.education.hjj.bz.entity.vo.DemandCourseInfoVo;
import com.education.hjj.bz.entity.vo.OrderDemandTimeVo;
import com.education.hjj.bz.entity.vo.PageVo;
import com.education.hjj.bz.entity.vo.StudentDemandConnectVo;
import com.education.hjj.bz.entity.vo.StudentDemandVo;
import com.education.hjj.bz.entity.vo.StudentVo;
import com.education.hjj.bz.entity.vo.TeachBranchVo;
import com.education.hjj.bz.entity.vo.TeacherAccountOperateLogVo;
import com.education.hjj.bz.entity.vo.TeacherAccountVo;
import com.education.hjj.bz.entity.vo.TeacherVo;
import com.education.hjj.bz.entity.vo.WeekTimeVo;
import com.education.hjj.bz.enums.MainSubjectEnum;
import com.education.hjj.bz.enums.SubjectPictureEnum;
import com.education.hjj.bz.formBean.DemandCourseInfoForm;
import com.education.hjj.bz.formBean.StudentDemandConnectForm;
import com.education.hjj.bz.formBean.StudentDemandForm;
import com.education.hjj.bz.formBean.StudentForm;
import com.education.hjj.bz.mapper.DemandCourseInfoMapper;
import com.education.hjj.bz.mapper.DemandLogMapper;
import com.education.hjj.bz.mapper.PointsLogMapper;
import com.education.hjj.bz.mapper.StudentDemandConnectMapper;
import com.education.hjj.bz.mapper.StudentDemandMapper;
import com.education.hjj.bz.mapper.StudentMapper;
import com.education.hjj.bz.mapper.TeachBranchMapper;
import com.education.hjj.bz.mapper.TeacherMapper;
import com.education.hjj.bz.mapper.UserAccountLogMapper;
import com.education.hjj.bz.mapper.UserAccountMapper;
import com.education.hjj.bz.mapper.UserInfoMapper;
import com.education.hjj.bz.service.StudentDemandsService;
import com.education.hjj.bz.service.StudentLogService;
import com.education.hjj.bz.util.ApiResponse;
import com.education.hjj.bz.util.DateUtil;
import com.education.hjj.bz.util.RegUtils;
import com.education.hjj.bz.util.SendWXMessageUtils;
import com.education.hjj.bz.util.common.CommonUtil;
import com.education.hjj.bz.util.common.StringUtil;
import com.education.hjj.bz.util.weixinUtil.config.Constant;

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

	@Autowired
	private PointsLogMapper pointsLogMapper;

	@Override
	public StudentDemandVo queryStudentDemandDetail(Integer demandId) {

		return studentDemandMapper.queryStudentDemands(demandId);
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

		studentDemandMapper.addStudentDemandByTeacher(form);
		
		TeacherVo teacherVo = new TeacherVo();

		// 如果有教员ID，则插入一条关联教员的表数据
		if (form.getTeacherId() != null) {
			// 判断改教员是否曾经试讲过同等年级同等科目的订单
			StudentDemandConnectForm alreadyForm = new StudentDemandConnectForm();
			alreadyForm.setSubjectId(form.getSubjectId());
			alreadyForm.setTeacherId(form.getTeacherId());
			alreadyForm.setStudentId(form.getStudentId());
			alreadyForm.setDemandGrade(form.getDemandGrade());
			Integer count = connectMapper.countAlreadyDemand(alreadyForm);

			Integer newOrderId = form.getSid();
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

			teacherVo = teacherMapper.load(form.getTeacherId());
			// 插入一条预约教员的日志信息
			StudentLogPo logPo = new StudentLogPo();
			logPo.setStudentId(Integer.valueOf(studentVo.getSid().toString()));
			logPo.setLogType(3); // 登录
			logPo.setLogContent("最近预约了" + teacherVo.getName() + "教员");
			logPo.setStudentName(studentVo.getStudentName());
			logPo.setStatus(1);
			logPo.setCreateTime(new Date());
			logPo.setCreateUser(studentVo.getSid().toString());
			logPo.setUpdateTime(new Date());
			logPo.setUpdateUser(studentVo.getSid().toString());
			studentLogService.addStudentLog(logPo);
		}
		
		// 直接指定教员的情形下,更新教员的被选中当试讲员数和被聘用数

		if (form.getSid() != null) {
			
			Integer  demandId = form.getSid();
			
			StudentDemandVo sdv = studentDemandMapper.queryStudentDemandDetailBySid(demandId);
			
			//教员的报名被学员选中并确定试讲时间后发送订阅消息
			JSONObject data2 = new JSONObject();

			Map<String, Object> keyMap1 = new HashMap<String, Object>();
			keyMap1.put("value", "学员： "+sdv.getStudentName()+" 科目： "+sdv.getTeachBranchName());
			// 授课老师
			data2.put("thing1", keyMap1);

			Map<String, Object> keyMap2 = new HashMap<String, Object>();
			keyMap2.put("value", "尽快前往小程序确定试讲时间，按时上门试讲");
			// 授课时间
			data2.put("thing2", keyMap2);

			logger.info("教员ID = {} , 订单id = {} , 学员id = {} ,课程内容 = {}", form.getTeacherId(),
					demandId, sdv.getStudentId(), sdv.getTeachBranchName());

			JSONObject sendRsult2 = SendWXMessageUtils.sendSubscribeMessage(teacherVo.getOpenId(),
					Constant.CLASS_CONTENT_MESSAGE, data2);

			logger.info("发出正式上课提醒给学生的结果：  " + sendRsult2.getString("errcode") + " " 
			+ sendRsult2.getString("errmsg"));
			
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
            Optional<StudentDemandConnectVo> op = null;

			// 每个订单关联的教员的状态优先级：已支付 > 待支付 >
            op = connectVos.stream()
                    .filter(s -> s.getStatus() != null &&  s.getStatus() == 4).findFirst();
            if (!op.isPresent()) {
                // 待支付
                op = connectVos.stream()
                        .filter(s -> s.getStatus() != null &&  s.getStatus() == 7).findFirst();

                if (!op.isPresent()) {
                    // 判断是否已经有了预约(排除已经预约过，但是未通过的)
                    op = connectVos.stream()
                            .filter(s -> s.getStatus() != null && s.getStatus() != 0 && s.getStatus() != 3).findFirst();
                }
            }

			if (op.isPresent()) {
				f.setSubscribeStatus(op.get().getStatus());
				f.setTeachName(op.get().getTeacherName());
				f.setAppraise(op.get().getAppraise());
				f.setChargesStandard(op.get().getChargesStandard());
				f.setOrderTeachTime(op.get().getOrderTeachTime());
				f.setTeacherId(op.get().getTeacherId());
				f.setAppraiseLevel(op.get().getAppraiseLevel());
				f.setAppraiseTime(op.get().getAppraiseTime());
				f.setTeacherPhone(op.get().getTelephone());

				if (StringUtil.isNotBlank(op.get().getChargesStandard())) {
					f.setOrderMoney(new BigDecimal(op.get().getChargesStandard().split("元")[0]));
				}

				// 大前提条件：试讲订单 如果已经过了试讲的试讲时间，则赋值6，前端判断显示试讲通过和试讲不通过
				if (f.getOrderType() != null && f.getOrderType() == 1 && op.get().getOrderTeachTime() != null
						&& new Date().after(DateUtil.addMinute(op.get().getOrderTeachTime(), 5))) {
					f.setSubscribeStatus(6);
				}
			} else {
//				// 判断是否已经有了预约(排除已经预约过，但是未通过的)
				Optional<StudentDemandConnectVo> noPassop = connectVos.stream()
						.filter(s -> s.getStatus() != null && s.getStatus() != 0 && s.getStatus() == 3).findFirst();
				// 确认订单只是有教员报名或者没有报名，没有其它状态
				boolean allStatusIsZero = connectVos.stream()
						.allMatch(s -> s.getStatus() == null || s.getStatus() == 0);
				if (f.getDemandType() == 1 && !allStatusIsZero) {
					f.setSubscribeStatus(3);
					f.setTeachName(noPassop.get().getTeacherName());
					f.setAppraise(noPassop.get().getAppraise());
					f.setChargesStandard(noPassop.get().getChargesStandard());
					f.setOrderTeachTime(noPassop.get().getOrderTeachTime());
					f.setTeacherId(noPassop.get().getTeacherId());
					f.setAppraiseLevel(noPassop.get().getAppraiseLevel());
					f.setAppraiseTime(noPassop.get().getAppraiseTime());
					f.setTeacherPhone(noPassop.get().getTelephone());
				} else {
					f.setEndDemandFlag(true);
					// 没有预约成功的，显示预约教员的数量
					f.setOrderTeachCount(
							org.apache.shiro.util.CollectionUtils.isEmpty(connectVos) ? 0 : connectVos.size());
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

		// 查询当前订单的同年级同科目的曾经预约通过的教员信息
		List<Integer> teacherList = studentDemandMapper.listTeacherByOldInfo(demandForm.getDemandId());

		if (!CollectionUtils.isEmpty(teacherList)) {
			list.forEach(f -> {
				f.setUnitPrice(Double.valueOf(f.getChargesStandard().split("元")[0]));
				if (teacherList.contains(f.getTeacherId())) {
					f.setPassFlag(Boolean.TRUE);
				}
			});
		} else {
			list.forEach(f -> {
				f.setUnitPrice(Double.valueOf(f.getChargesStandard().split("元")[0]));
			});
		}
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
				timeVo.setDate(DateUtil.addDay(vo.getCreateTime(), 7 + w.getWeek() - weekDay));
			} else {
				timeVo.setDate(DateUtil.addDay(vo.getCreateTime(), w.getWeek() - weekDay));
			}
			orderDemandTimeVos.add(timeVo);
		});

		// 确定试讲试讲不能小于今天或以前的时间
		List<OrderDemandTimeVo> orderDemandTimeVoList = orderDemandTimeVos.stream()
				.filter(f -> DateUtil.getDayStart(f.getDate()).compareTo(DateUtil.getDayStart(new Date())) > 0)
				.collect(Collectors.toList());

		orderDemandTimeVoList.sort((a, b) -> a.getTime().compareTo(b.getTime()));
		orderDemandTimeVoList.sort((a, b) -> a.getDate().compareTo(b.getDate()));
		map.put("orderTime", orderDemandTimeVoList);
		logger.info("caohuan********teacherList:{}", JSON.toJSONString(orderDemandTimeVoList));
		return ApiResponse.success(map);
	}

	@Override
	@Transactional
	public ApiResponse confirmTeacher(StudentDemandConnectForm demandForm) {

		Integer teacherId = demandForm.getTeacherId();

		TeacherVo teacherVo = userInfoMapper.queryTeacherHomeInfos(teacherId);

		int i = 0;

		// 更新教员对所有报名订单的数量
		TeacherPo teacherPo = new TeacherPo();

		teacherPo.setChooseCount(teacherVo.getChooseCount() + 1);
		teacherPo.setTeacherId(teacherId);
		teacherPo.setUpdateTime(new Date());

		logger.info("teacherId = {} , ChooseCountBefore={} , ChooseCountAfter={}", teacherId,
				teacherVo.getChooseCount(), teacherVo.getChooseCount() + 1);

		// 单独预约的需求，确定教员时，订单变成试讲中
		if (demandForm.getDemandType() == null) {

			return ApiResponse.error("必须确定单独试讲或者快速请家教");

		} else if (demandForm.getDemandType() == 1) {
			// 单独预约
			demandForm.setStatus(1);

		} else {
			demandForm.setStatus(2);

			logger.info("快速请家教的时候： teacherId = {} , ChooseCountBefore={} , ChooseCountAfter={}", teacherId,
					teacherVo.getChooseCount(), teacherVo.getChooseCount() + 1);
		}

		i = userInfoMapper.updateUserInfo(teacherPo);

		Long sid = connectMapper.confirmTeacher(demandForm);

		if (sid != null && i > 0) {
			
			int demandId = demandForm.getDemandId();
			
			logger.info("需求ID：  " + demandId + " 教员ID： "+ teacherId);
			
			StudentDemandPo studentDemandPo = new StudentDemandPo();
			studentDemandPo.setTeacherId(teacherId);
			studentDemandPo.setDemandId(demandId);
			
			StudentDemandVo studentDemandVo = studentDemandMapper.findStudentDemandDetail(studentDemandPo);
			
			JSONObject data = new JSONObject();
			
			Map<String, Object> keyMap1 = new HashMap<String, Object>();
			keyMap1.put("value", "学员 ：" +studentDemandVo.getStudentName()+" 科目： "+studentDemandVo.getTeachBranchName());
			// 课程名称
			data.put("thing1", keyMap1);
			
			Map<String, Object> keyMap2 = new HashMap<String, Object>();
			keyMap2.put("value", demandForm.getConfirmDate().substring(0, 16)+"上门试讲");
			// 上课时间
			data.put("thing5", keyMap2);
			
			logger.info("教员ID = {} , 订单id = {} , 学员id = {} , 试讲时间  =  {}  , 课程内容 = {}", teacherId,
					demandId, studentDemandVo.getStudentId(), demandForm.getConfirmDate(), 
					studentDemandVo.getTeachBranchName());
			
			JSONObject sendRedPackRsult = SendWXMessageUtils.sendSubscribeMessage(
					teacherVo.getOpenId(), Constant.CHANGE_SIGN_STATUS_RESULT_MESSAGE, data);
			
			logger.info("教员的报名被学员选中并确定试讲时间后发送订阅消息发送的结果： " + sendRedPackRsult.getString("errcode") + " "
					+ sendRedPackRsult.getString("errmsg"));
			
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

		logger.info("检索订单" + courseInfoForm.getSid());
		// 检索订单
		StudentDemandVo vo = studentDemandMapper.findDemandByCourseId(courseInfoForm.getSid());

		// 结课时候，修改教员的总收入金额 和可提现金额
		TeacherAccountVo teacherAccountVo = userAccountMapper.queryTeacherAccount(courseInfoForm.getTeacherId());
		TeacherVo teacherVo = teacherMapper.load(courseInfoForm.getTeacherId());

		TeacherAccountPo po = new TeacherAccountPo();
		po.setTeacherId(courseInfoForm.getTeacherId());
		if (teacherAccountVo == null) {
			logger.info("订单初始的价格：" + JSON.toJSONString(vo));
			po.setStatus(1);
			po.setCreateTime(date);
			po.setTeacherName(teacherVo.getName());
			po.setAccountMoney(new BigDecimal(vo.getChargesStandard().split("元")[0].toString()));
			po.setSurplusMoney(new BigDecimal(vo.getChargesStandard().split("元")[0].toString()));
			po.setTeacherPhone(teacherVo.getTelephone());
			po.setCreateUser(teacherVo.getTeacherId().toString());
			po.setUpdateUser(teacherVo.getTeacherId().toString());
			po.setUpdateTime(date);
			po.setCreateTime(date);

			// 如果没有数据，则插入一条教员收支数据
			userAccountMapper.insertTeacherAccount(po);

		} else {
			po.setUpdateTime(date);
			po.setUpdateUser(courseInfoForm.getTeacherId().toString());
			po.setSurplusMoney(teacherAccountVo.getSurplusMoney()
					.add(new BigDecimal(vo.getChargesStandard().split("元")[0].toString())));
			po.setAccountMoney(teacherAccountVo.getAccountMoney()
					.add(new BigDecimal(vo.getChargesStandard().split("元")[0].toString())));
			userAccountMapper.updateTeacherAccountMoney(po);
		}

		// 插入一条日志信息，记录结课/支付记录
		TeacherAccountOperateLogPo paymentLog = new TeacherAccountOperateLogPo();
		paymentLog.setPaymentStreamId(vo.getPaymentStreamId());
		paymentLog.setPaymentPersonId(courseInfoForm.getTeacherId());
		paymentLog.setPaymentPersonName(teacherVo.getName());
		paymentLog.setOrderId(courseInfoForm.getSid().toString());
		paymentLog.setDemandId(vo.getSid());
		paymentLog.setPaymentType(3);
		paymentLog.setPaymentDesc("结课时收入");
		paymentLog.setStatus(1);
		paymentLog.setCreateTime(date);
		paymentLog.setCreateUser(vo.getStudentName());
		paymentLog.setUpdateTime(date);
		paymentLog.setUpdateUser(vo.getStudentName());
		paymentLog.setPaymentAccount(new BigDecimal(vo.getChargesStandard().split("元")[0].toString()));
		userAccountLogMapper.insertUserAccountLog(paymentLog);

		// 结课，教员+5分
		PointsLogPo pointsLogPo = new PointsLogPo();
		pointsLogPo.setTeacherId(courseInfoForm.getTeacherId());
		pointsLogPo.setGetPointsCounts(5);
		pointsLogPo.setGetPointsType(3);
		pointsLogPo.setGetPointsDesc("结课");
		pointsLogPo.setStatus(1);
		pointsLogPo.setCreateTime(new Date());
		pointsLogPo.setCreateUser(String.valueOf(courseInfoForm.getTeacherId()));
		pointsLogPo.setUpdateTime(new Date());
		pointsLogPo.setUpdateUser(String.valueOf(courseInfoForm.getTeacherId()));

		pointsLogMapper.addTeacherPointsLog(pointsLogPo);

		return ApiResponse.success("结课成功");
	}

	/**
	 * 支付或续课
	 **/
	@Override
	@Transactional
	public ApiResponse payDemand(StudentDemandForm demandForm) {

//		Integer isResumption = demandForm.getIsResumption();
//		logger.info("是否是续课订单：{}" , isResumption == 1?"是":"否");
//
//		Integer teacherId = demandForm.getTeacherId();
//		logger.info("教员的id：{}" , teacherId);
//
//		// 如果是试讲订单，要将试讲订单修改成付费订单
//		StudentDemandVo demandVo = studentDemandMapper.findStudentDemandInfo(demandForm.getDemandId());
//
//		if (demandVo == null) {
//			return ApiResponse.error("订单不符合要求");
//		}
//
//		// 记录上个订单的信息
//		Date date = new Date();
//		DemandLogForm logForm = new DemandLogForm();
//		logForm.setCreateTime(date);
//		logForm.setMark(JSON.toJSONString(demandVo));
//		logForm.setDemandId(demandForm.getDemandId());
//		logForm.setCreateUser(demandVo.getStudentId().toString());
//
//		demandLogMapper.insert(logForm);
//
//		Integer weekDay = DateUtil.getWeekOfDate(date);
//		demandForm.setCurrentWeekDay(weekDay);
//
//		// 修改当前订单成新订单
//		demandForm.setOrderType(2);
//		demandForm.setOrderStart(date);
//		demandForm.setUpdateTime(date);
//		Long sid = studentDemandMapper.updateOldDemandToNew(demandForm);
//
//		if(isResumption ==1 ) {
//
//			StudentDemandVo  studentDemandVo  = studentDemandMapper.queryStudentDemandDetailBySid(demandForm.getDemandId());
//
//			StudentDemandPo studentDemandPo = new StudentDemandPo();
//
//			int resumption = studentDemandVo.getIsResumption();
//			//判断该订单是否已经续课，0未续课，1已续课
//			if(resumption == 0) {
//				studentDemandPo.setIsResumption(1);
//			}
//			studentDemandPo.setDemandId(demandForm.getDemandId());
//			studentDemandPo.setUpdateTime(new Date());
//
//			studentDemandMapper.updateDemandIsResumption(studentDemandPo);
//
//
//
//			TeacherVo teacherVo = userInfoMapper.queryTeacherHomeInfos(teacherId);
//
//			TeacherPo teacher =new TeacherPo ();
//
//			teacher.setTeacherId(teacherId);
//
//			//如果当前订单没有续过课,则本次续课时将该教员的所记录的续课的总数+1,
//			//如果续过课,则续课总数不变。
//			if(isResumption == 0) {
//				teacher.setResumptionCount(teacherVo.getResumptionCount()+1);
//			}
//
//			int employCount = teacherVo.getEmployCount();
//
//			int resumptionCount = teacherVo.getResumptionCount();
//
//			double newRate = resumptionCount / employCount;
//
//			logger.info(" employCount={} , resumptionCount={} , newRate={}", employCount,
//					resumptionCount, newRate);
//
//			BigDecimal bg = new BigDecimal(newRate).setScale(2, RoundingMode.DOWN);
//			logger.info("employRate = {}", bg);
//
//			teacher.setResumptionRate(bg);
//
//			// 更新该教员的续课率
//			int j = userInfoMapper.updateUserInfo(teacher);
//		}
//
//		List<DemandCourseInfoForm> courseInfoFormList = new ArrayList<>();
//
//		// 根据订单插入每个节课时
//		for (int i = 0; i < demandForm.getWeekNum(); i++) {
//			List<WeekTimeVo> list = JSON.parseArray(demandForm.getTimeRange(), WeekTimeVo.class);
//
//			final Integer weekNum = i;
//			list.forEach(w -> {
//				DemandCourseInfoForm courseInfoForm = new DemandCourseInfoForm();
//				if (weekDay >= w.getWeek()) {
//					courseInfoForm.setOrderTeachTime(DateUtil.addDay(date, 7 + 7*weekNum));
//				} else {
//					courseInfoForm.setOrderTeachTime(DateUtil.addDay(date, w.getWeek() - weekDay + (7*weekNum)));
//				}
//
//				courseInfoForm.setStatus(0);
//				courseInfoForm.setDeleteStatus(0);
//				courseInfoForm.setCreateTime(date);
//				courseInfoForm.setUpdateTime(date);
//				courseInfoForm.setWeekNum(w.getWeek());
//				courseInfoForm.setTimeNum(w.getTime());
//				courseInfoForm.setDemandId(demandForm.getDemandId());
//				courseInfoForm.setStudentId(demandVo.getStudentId());
//				courseInfoForm.setTeacherId(demandVo.getTeacherId());
//				courseInfoForm.setCreateUser(demandVo.getStudentId().toString());
//
//				courseInfoFormList.add(courseInfoForm);
//			});
//		}
//
//		demandCourseInfoMapper.insert(courseInfoFormList);

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

		if (demandForm.getAppraise() != null && StringUtils.isNoneBlank(demandForm.getAppraise())) {
			logger.info("评价描述：{}", demandForm.getAppraise());
			demandForm.setAppraiseTime(new Date());
		}

		logger.info("教员：{} 试讲状态：{}", demandForm.getTeacherId(), demandForm.getStatus());
		// 试讲通过
		Long sid = connectMapper.updateStatus(demandForm);
		if (sid != null) {
			// 试讲不通过，返回三个形态信息
			if (demandForm.getStatus() == 3) {

				// 更新教员的聘用率
				Integer teacherId = demandForm.getTeacherId();
				TeacherVo teacherVo = userInfoMapper.queryTeacherHomeInfos(teacherId);

				TeacherPo teacherPo = new TeacherPo();
				teacherPo.setTeacherId(teacherId);

				double chooseCount = teacherVo.getChooseCount();

				double newRate = 0;
				if (chooseCount != 0) {
					newRate = teacherVo.getEmployCount() / chooseCount;
				}

				logger.info("employCount={} , chooseCount={} , newRate={}", teacherVo.getEmployCount(), chooseCount,
						newRate);

				BigDecimal bg = new BigDecimal(newRate).setScale(5, RoundingMode.UP);
				logger.info("employRate = {}", RegUtils.doubleToPersent().format(bg));

				teacherPo.setEmployRate(RegUtils.doubleToPersent().format(bg));

				userInfoMapper.updateUserInfo(teacherPo);

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
		if (demandForm.getDemandId() == null) {
			return ApiResponse.error("订单号不能为空");
		}
		TeacherAccountOperateLogPo po = new TeacherAccountOperateLogPo();
		po.setDemandId(demandForm.getDemandId());

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
	public ApiResponse homepageInfo() {
		// 检索科目信息
		List<TeachBranchVo> branchVos = teachBranchMapper.queryAllTeachBranchs();
		List<CodeInfoVo> codeInfoVos = branchVos.stream()
				.map(m -> new CodeInfoVo(m.getTeachBranchId().toString(), m.getTeachBranchName()))
				.collect(Collectors.toList());

		List<String> mainList = Arrays.stream(MainSubjectEnum.values()).map(mv -> mv.getValue())
				.collect(Collectors.toList());

		List<CodeInfoVo> collects = codeInfoVos.stream()
				.filter(f -> !mainList.stream().anyMatch(s -> f.getValue().contains(s))).collect(Collectors.toList());

		mainList.forEach(m -> {
			CodeInfoVo vo = new CodeInfoVo();
			String code = codeInfoVos.stream().filter(f -> f.getValue().contains(m)).map(p -> p.getKey())
					.collect(Collectors.joining(","));
			vo.setKey(code);
			vo.setValue(m);
			collects.add(vo);
		});

		// 科目赋值图标
		Supplier<Stream<SubjectPictureEnum>> supplier =  () -> Arrays.stream(SubjectPictureEnum.values());
		collects.forEach(f -> {
			Optional<SubjectPictureEnum> op
					= supplier.get().filter(s -> s.getCode().equalsIgnoreCase(f.getValue())).findFirst();

			if (op.isPresent()) {
				f.setPictureName(op.get().getValue());
			}
		});


		return ApiResponse.success(collects);
	}

	/**
	 * 主页信息
	 **/
	@Override
	@Transactional
	public ApiResponse appraise(StudentDemandConnectForm demandForm) {

		demandForm.setUpdateTime(new Date());
		// 检索科目信息
		studentDemandMapper.updateAppraise(demandForm);

		// 涉及到减分，需要先检索教员的总分数
//		long point = pointsLogMapper.selectSumPointByTeacherId(demandForm.getTeacherId());
		TeacherVo vo = teacherMapper.load(demandForm.getTeacherId());

		Integer points = vo.getTeacherPoints();
		// 结课，教员+5分
		PointsLogPo pointsLogPo = new PointsLogPo();
		pointsLogPo.setTeacherId(demandForm.getTeacherId());
		// 中评，减20分
		if (demandForm.getAppraiseLevel() == 2) {
			if (vo.getTeacherPoints() >= 20) {
				pointsLogPo.setGetPointsCounts(-20);
				points = points -20;
			} else {
				pointsLogPo.setGetPointsCounts((int) - vo.getTeacherPoints());
				points = 0;
			}
			pointsLogPo.setGetPointsType(6);
			pointsLogPo.setGetPointsDesc("中评");
			// 差评
		} else if (demandForm.getAppraiseLevel() == 3){
			if (vo.getTeacherPoints() >= 30) {
				pointsLogPo.setGetPointsCounts(-30);
				points = points -30;
			} else {
				pointsLogPo.setGetPointsCounts((int) - vo.getTeacherPoints());
				points = 0;
			}
			pointsLogPo.setGetPointsType(5);
			pointsLogPo.setGetPointsDesc("差评");
		}
		// 不是好评才插入日志
		if (demandForm.getAppraiseLevel() > 1) {
			pointsLogPo.setStatus(1);
			pointsLogPo.setCreateTime(new Date());
			pointsLogPo.setCreateUser(String.valueOf(demandForm.getTeacherId()));
			pointsLogPo.setUpdateTime(new Date());
			pointsLogPo.setUpdateUser(String.valueOf(demandForm.getTeacherId()));

			pointsLogMapper.addTeacherPointsLog(pointsLogPo);

			// 更新用户分数
			TeacherVo updateVo = new TeacherVo();
			updateVo.setTeacherId(demandForm.getTeacherId());
			updateVo.setTeacherPoints(points);
			teacherMapper.updateInfoByTeacherId(updateVo);
		}
		return ApiResponse.success("评价成功");
	}

	/**
	 *
	 * 最新发布的好评
	 **/
	@Override
	public ApiResponse queryGoodApprise() {

		Map<String, Object> result = new HashMap<>();
		List<StudentDemandConnectVo> connectVos = connectMapper.listGoodApprise();
		result.put("goodApprise", connectVos);

		// 好评率= 好评数/总数
		List<Integer> list = connectMapper.findAllApprise();
		Long count = list.stream().filter(f -> f != null && f == 1).count();

		if (!CollectionUtils.isEmpty(list) &&
				new BigDecimal(count).divide(new BigDecimal(list.size()), 2, BigDecimal.ROUND_HALF_UP).compareTo(new BigDecimal("0.9")) > 0) {
			result.put("rate", new BigDecimal(count).multiply(new BigDecimal(100)).divide(new BigDecimal(list.size()),
					1, BigDecimal.ROUND_HALF_UP) + "%");
		} else {
			result.put("rate", "90.0%");
		}

		return ApiResponse.success(result);
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

		if (list != null && list.size() > 0) {

			for (TeacherVo t : list) {
				if (t.getTeacherId() == teacherId) {

					flag = true;

					break;
				}
			}

			map.put("signUpTeacherInfo", list);

		} else {
			map.put("signUpTeacherInfo", "");
		}

		if (studentDemandDetail.getDemandType() == 1) {

			flag = true;
			map.put("studentDemandDetail", studentDemandDetail);
			map.put("singUpStatus", flag);// singUpStatus为false的可以报名
		}

		List<StudentDemandVo> sdcList = studentDemandMapper.queryStudentDemandDetailSignStatusBySid(sid);

		if (sdcList.size() > 0 && list.size() > 0) {

			for (StudentDemandVo sd : sdcList) {

				if (sd.getTeacherId() == teacherId) {
					flag = true;

					break;
				}

				if (sd.getStatus() != null && StringUtils.isNoneBlank(sd.getStatus().toString())) {

					if (sd.getStatus() != 0 && sd.getStatus() != 3) {
						flag = true;

						break;
					}
				}
			}

			logger.info("订单id:{} , 教员id:{} , 报名人数：{} ,是否报过名:{}", sid, teacherId, list.size(), flag);

		}

		map.put("studentDemandDetail", studentDemandDetail);
		map.put("singUpStatus", flag);// singUpStatus为false的可以报名

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

						int day = Integer.valueOf(tp.getWeek()) - Integer.valueOf(weekDay);

						Date afterDay = DateUtil.addDay(date, day);

						lastDateTime = afterDay;
					}

					if (Integer.valueOf(weekDay) >= Integer.valueOf(tp.getWeek())) {

						int day = Integer.valueOf(weekDay) - Integer.valueOf(tp.getWeek());

						Date beforeDay = DateUtil.subDay(date, -day);

						lastDateTime = DateUtil.addDay(beforeDay, 7);
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

					studentDemandVo.setOrderStartDate(orderStartDate);
					studentDemandVo.setOrderEndDate(orderEndDate);

				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}
		
		int demandId = studentDemandVo.getSid();
		
		TeacherAccountOperateLogPo teacherAccountOperateLogPo = new TeacherAccountOperateLogPo();
		teacherAccountOperateLogPo.setDemandId(demandId);
		
		List<TeacherAccountOperateLogVo> list = userAccountLogMapper.queryStudentDemandAccountLogList(teacherAccountOperateLogPo);
		studentDemandVo.setOrderMoneyList(JSON.toJSONString(list));

		return studentDemandVo;
	}

	@Override
	public List<StudentDemandVo> queryFitTeacherOrderList() {
		List<StudentDemandVo> list = studentDemandMapper.queryFitTeacherOrderList();
		return list;
	}

	@Override
	@Transactional
	public int updateNewTrialDemand(StudentDemandConnectForm demandForm) {

		String orderTeachTime = demandForm.getOrderTeachTime();
		Integer demandId = demandForm.getDemandId();

		logger.info("教员ID = {} ,订单id = {} , 确定的试讲时间 = {}", demandForm.getTeacherId(), demandId, orderTeachTime);

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

		Integer teacherId = demandForm.getTeacherId();

		PointsLogPo pointsLogPo = new PointsLogPo();
		pointsLogPo.setTeacherId(teacherId);
		pointsLogPo.setGetPointsCounts(10);
		pointsLogPo.setGetPointsType(1);
		pointsLogPo.setGetPointsDesc("确定试讲时间");
		pointsLogPo.setStatus(1);
		pointsLogPo.setCreateTime(new Date());
		pointsLogPo.setCreateUser(String.valueOf(teacherId));
		pointsLogPo.setUpdateTime(new Date());
		pointsLogPo.setUpdateUser(String.valueOf(teacherId));

		int k = pointsLogMapper.addTeacherPointsLog(pointsLogPo);

		TeacherVo teacherVo = userInfoMapper.queryTeacherHomeInfos(teacherId);
		TeacherPo teacher = new TeacherPo();
		teacher.setTeacherId(teacherId);
		teacher.setTeacherPoints(teacherVo.getTeacherPoints() + 10);
		teacher.setUpdateTime(new Date());
		teacher.setUpdateUser(teacherVo.getName());
		logger.info("教员ID = {} , beforeTeacherPoints = {} , afterTeacherPoints = {}", demandForm.getTeacherId(),
				teacherVo.getTeacherPoints(), teacherVo.getTeacherPoints() + 10);

		// 更新教员对所有报名订单的数量
		Integer beforeChooseCount = teacherVo.getChooseCount();
		Integer afterChooseCount = teacherVo.getChooseCount() + 1;
		Integer beforeEmployCount = teacherVo.getEmployCount();
		Integer afterEmployCount = teacherVo.getEmployCount() + 1;

		teacher.setChooseCount(afterChooseCount);
		teacher.setTeacherId(teacherId);
		teacher.setUpdateTime(new Date());

		logger.info("teacherId = {} , ChooseCountBefore={} , ChooseCountAfter={}", teacherId, beforeChooseCount,
				afterChooseCount);

// 		teacher.setEmployCount(afterEmployCount);

//		double newRate = 0;
//		
//		if (afterChooseCount != 0) {
//			newRate = (afterEmployCount) / afterChooseCount;
//		}
//
//		logger.info("teacherId = {} ,beforeEmployCount = {} , afterEmployCount={} , afterChooseCount={} , EmployRate={}", 
//				teacherId , beforeEmployCount ,	afterEmployCount, afterChooseCount, newRate);
//
//		BigDecimal bg = new BigDecimal(newRate).setScale(2, RoundingMode.DOWN);
//		logger.info("employRate = {}", RegUtils.doubleToPersent().format(bg));
//		// 更新该教员的聘用率
//		teacher.setEmployRate(RegUtils.doubleToPersent().format(bg));

		int m = userInfoMapper.updateUserInfo(teacher);

		if (i >= 0 && j >= 0 && k > 0 && m > 0) {

			StudentDemandVo sdv = studentDemandMapper.queryStudentDemandDetailBySid(demandId);
			String studentOpenId = sdv.getOpenId();

			JSONObject data = new JSONObject();

			Map<String, Object> keyMap1 = new HashMap<String, Object>();
			keyMap1.put("value", teacherVo.getName());
			// 授课老师
			data.put("name1", keyMap1);

			Map<String, Object> keyMap2 = new HashMap<String, Object>();
			keyMap2.put("value", orderTeachTime);
			// 授课时间
			data.put("date3", keyMap2);

			Map<String, Object> keyMap3 = new HashMap<String, Object>();
			keyMap3.put("value", sdv.getTeachBranchName());
			// 课程内容
			data.put("thing4", keyMap3);

			logger.info("教员ID = {} , 订单id = {} , 学员id = {} , 试讲时间  =  {}  , 课程内容 = {}", demandForm.getTeacherId(),
					demandId, sdv.getStudentId(), orderTeachTime, sdv.getTeachBranchName());

			JSONObject sendRsult = SendWXMessageUtils.sendSubscribeMessage(studentOpenId,
					Constant.CLASS_SUBSCRIBE_MESSAGE, data);

			logger.info("预约成功消息发送的结果： " + sendRsult.getString("errcode") + " " + sendRsult.getString("errmsg"));
			
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
			studentDemandPo.setOrderStartDate(orderStartDate + " 00:00:00");
			studentDemandPo.setOrderEndDate(orderEndDate + " 23:59:59");

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
	@Transactional
	public int updateTimeTableByTeacherId(StudentDemandPo studentDemandPo) {

		studentDemandPo.setUpdateTime(new Date());

		Integer teacherId = studentDemandPo.getTeacherId();

		Integer studentId = studentDemandPo.getStudentId();

		// 结课
		if (teacherId == null && studentId != null) {
			studentDemandPo.setStatus(2);
		}

		int k = 0;
		int l = 0;

		// 打卡
		if (teacherId != null && studentId == null) {
			studentDemandPo.setStatus(1);

			PointsLogPo pointsLogPo = new PointsLogPo();
			pointsLogPo.setTeacherId(teacherId);
			pointsLogPo.setGetPointsCounts(5);
			pointsLogPo.setGetPointsType(2);
			pointsLogPo.setGetPointsDesc("打卡");
			pointsLogPo.setStatus(1);
			pointsLogPo.setCreateTime(new Date());
			pointsLogPo.setCreateUser(String.valueOf(teacherId));
			pointsLogPo.setUpdateTime(new Date());
			pointsLogPo.setUpdateUser(String.valueOf(teacherId));

			k = pointsLogMapper.addTeacherPointsLog(pointsLogPo);

			TeacherVo teacherVo = userInfoMapper.queryTeacherHomeInfos(teacherId);

			// 增加教员积分
			TeacherPo teacher = new TeacherPo();
			teacher.setTeacherId(teacherId);
			teacher.setTeacherPoints(teacherVo.getTeacherPoints() + 5);
			teacher.setUpdateTime(new Date());
			teacher.setUpdateUser(teacherVo.getName());

			userInfoMapper.updateUserInfo(teacher);
		}

		int i = studentDemandMapper.updateTimeTableByTeacherId(studentDemandPo);

		if (i >= 0 && k > 0 && l > 0) {
			return 1;
		}

		return -1;
	}

	@Override
	@Transactional
	public int insert(StudentDemandConnectForm studentDemandConnect) {

		Integer teacherId = studentDemandConnect.getTeacherId();

		TeacherVo teacherVo = userInfoMapper.queryTeacherHomeInfos(teacherId);

		int auditStatus = teacherVo.getAuditStatus();

		logger.info("教员id:{},教员的简历审核状态:{}", teacherId, auditStatus);

		if (auditStatus == 0 || auditStatus == 2) {
			logger.info("您的身份信息还未审核通过，请至“我的”-“简历信息”中完善信息");
			return -5;
		}

		int count = connectMapper.getCount(studentDemandConnect);

		if (count > 0) {
			logger.info("已报名该订单，请不要重复报名该订单！");
			return -3;
		}

		int num = connectMapper.querySignUpPersonByDemandId(studentDemandConnect);

		if (num > 0) {
			logger.info("该订单已经被锁定，请稍后尝试报名该订单！");
			return -4;
		}
		
		int demandId = studentDemandConnect.getDemandId();
		
		StudentDemandForm sd = new StudentDemandForm();
		sd.setSid(demandId);
		sd.setStatus(2);
		
		int demandSignCount = studentDemandMapper.querySignUpPersonBystatus(sd);
		
		if(demandSignCount > 0 ) {
			logger.info("该订单已接单，请报名其他订单！");
			return -2;
		}

		studentDemandConnect.setStatus(0);
		studentDemandConnect.setDeleteStatus(0);
		studentDemandConnect.setCreateTime(new Date());
		studentDemandConnect.setCreateUser(studentDemandConnect.getTeacherId());

		int i = connectMapper.insert(studentDemandConnect);


		StudentDemandConnectForm sdc = new StudentDemandConnectForm();
		sdc.setDemandId(demandId);

		StudentDemandVo studentDemandVo = studentDemandMapper.queryStudentDemandDetailBySid(demandId);

		int demandPersonCount = studentDemandVo.getDemandSignUpNum();

		StudentDemandPo studentDemandPo = new StudentDemandPo();
		studentDemandPo.setDemandId(demandId);
		studentDemandPo.setDemandSignUpNum(demandPersonCount + 1);
		studentDemandPo.setUpdateTime(new Date());

		int j = studentDemandMapper.updateDemandSignNum(studentDemandPo);

		if (i > 0 && j > 0) {

			String openId = studentDemandVo.getOpenId();

			int demandSignUpNum = studentDemandVo.getDemandSignUpNum();

			logger.info("订单号：{}，之前的报名人数：{}，之后的报名人数：{}", demandId, demandSignUpNum, demandSignUpNum + 1);

			JSONObject data = new JSONObject();

			Map<String, Object> keyMap1 = new HashMap<String, Object>();
			keyMap1.put("value", demandSignUpNum + 1);
			// 报名人数
			data.put("number4", keyMap1);

			Map<String, Object> keyMap2 = new HashMap<String, Object>();
			keyMap2.put("value", DateUtil.covertFromDateToShortString(new Date()));
			// 截止时间
			data.put("date5", keyMap2);

			JSONObject sendRedPackRsult = SendWXMessageUtils.sendSubscribeMessage(openId,
					Constant.CHANGE_SIGN_NUM_RESULT_MESSAGE, data);

			logger.info(
					"提现消息发送的结果： " + sendRedPackRsult.getString("errcode") + " " + sendRedPackRsult.getString("errmsg"));

			return 1;
		}

		return -1;
	}

	@Override
	public Map<String, Object> validateSignParameters(StudentDemandConnectForm demandForm) {

		logger.info("报名的教员id:{},订单id:{},前端传值--授课科目id:{} , 授课年级id: {} , 授课区域id{} , 授课时间{}", demandForm.getTeacherId(),
				demandForm.getDemandId(), demandForm.getTeachBranchId(), demandForm.getTeachGradeId(),
				demandForm.getParameterId(), JSON.toJSONString(demandForm.getTimeList()));

		boolean flag = true;

		boolean timeFlag = false;

		Map<String, Object> map = new HashMap<String, Object>();

		int teacherId = demandForm.getTeacherId();

		TeacherVo t = userInfoMapper.queryTeacherHomeInfos(teacherId);

		String teachBranchIdDB = t.getTeachBrance() + "," + t.getTeachBranchSlave();

		int teachBranchId = demandForm.getTeachBranchId();

		logger.info("订单需求授课科目id:{} , 报名教员的授课科目id: {}", teachBranchId, teachBranchIdDB);

		if (teachBranchIdDB.contains(String.valueOf(teachBranchId)) == false) {
			flag = false;

			map.put("validateCode", flag);
			map.put("validateResult", "该需求的科目不在您的授课范围内，请谨慎报名。");

			return map;
		}

		int teachGradeId = demandForm.getTeachGradeId();
		String teachGradeIdDB = t.getTeachGrade();

		logger.info("订单需求授课年级id:{} , 报名教员的授课年级id: {}", teachGradeId, teachGradeIdDB);

		if (teachGradeIdDB.contains(String.valueOf(teachGradeId)) == false) {
			flag = false;

			map.put("validateCode", flag);
			map.put("validateResult", "该需求的年级不在您的授课范围内，请谨慎报名。");

			return map;
		}

		int areaId = demandForm.getParameterId();

		String teachAddressDB = t.getTeachAddress();

		logger.info("订单需求授课区域id:{} , 报名教员的授课区域id: {}", areaId, teachAddressDB);

		if (teachAddressDB.contains(String.valueOf(areaId)) == false) {

			flag = false;

			map.put("validateCode", flag);
			map.put("validateResult", "该需求的上课区域不在您的授课区域内，请谨慎报名。");

			return map;
		}

		String teachTimeDB = t.getTeachTime();

		List<TeachTimePo> teachTimePoa = demandForm.getTimeList();

		List<TeachTimePo> teachTimePob = JSON.parseArray(teachTimeDB, TeachTimePo.class);

		logger.info("订单需求授课时间:{} , 报名教员的授课时间: {}", JSON.toJSONString(demandForm.getTimeList()), teachTimeDB);

		for (TeachTimePo ttpa : teachTimePoa) {

			for (TeachTimePo ttpb : teachTimePob) {

				if (ttpa.getTime().equalsIgnoreCase(ttpb.getTime())
						&& ttpa.getWeek().equalsIgnoreCase(ttpb.getWeek())) {
					timeFlag = true;
				}
			}

		}

		if (timeFlag == false) {

			flag = false;

			map.put("validateCode", flag);
			map.put("validateResult", "该需求的预计上课时段不在您的授课时间内，请谨慎报名。");

			return map;
		}

		map.put("validateCode", true);
		map.put("validateResult", "可以报名。");

		return map;

	}

	@Override
	public List<StudentDemandVo> queryAllWaitForTrailTimeDemandOrderList(
			StudentDemandConnectForm studentDemandConnectForm) {

		Date createTime = DateUtil.addHour(new Date(), -1);
		studentDemandConnectForm.setCreateTime(createTime);

		List<StudentDemandVo> list = connectMapper.queryAllWaitForTrailTimeDemandOrderList(studentDemandConnectForm);

		return list;
	}

	@Override
	public int updateStudentDemandConnectByStatus(StudentDemandConnectForm studentDemandConnectForm) {
		studentDemandConnectForm.setUpdateTime(new Date());
		int i = connectMapper.updateStudentDemandConnectByStatus(studentDemandConnectForm);
		return i;
	}
}
