package com.education.hjj.bz.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.education.hjj.bz.entity.StudentOrderPo;
import com.education.hjj.bz.entity.TeacherPo;
import com.education.hjj.bz.entity.TeacherStudentOrderPo;
import com.education.hjj.bz.entity.vo.StudentOrderVo;
import com.education.hjj.bz.entity.vo.TeacherStudentOrderVo;
import com.education.hjj.bz.entity.vo.TeacherVo;
import com.education.hjj.bz.enums.OrderStatusEnum;
import com.education.hjj.bz.formBean.StudentOrderForm;
import com.education.hjj.bz.formBean.TeacherStudentOrderForm;
import com.education.hjj.bz.mapper.OrderMapper;
import com.education.hjj.bz.mapper.UserInfoMapper;
import com.education.hjj.bz.service.OrderService;
import com.education.hjj.bz.util.RegUtils;
import com.education.hjj.bz.util.UUIDUtils;

@Service
public class OrderServiceImpl implements OrderService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private OrderMapper orderMapper;

	@Autowired
	private UserInfoMapper userInfoMapper;

	@Override
	public List<StudentOrderVo> queryAllOrdersListBySuit() {
		List<StudentOrderVo> list = orderMapper.queryAllOrdersListBySuit();
		return list;
	}

	@Override
	public List<StudentOrderVo> queryAllOrders(StudentOrderForm studentOrderForm) {

		String orderStatus = studentOrderForm.getOrderStatus();

		logger.info("orderStatus={}", orderStatus);

		StudentOrderPo studentOrderPo = new StudentOrderPo();

		if (orderStatus != null && StringUtils.isNotBlank(orderStatus)) {
			studentOrderPo.setOrderStatus(Integer.valueOf(orderStatus));
		}

		List<StudentOrderVo> list = orderMapper.queryAllOrders(studentOrderPo);

		return list;
	}

	@Override
	public List<StudentOrderVo> queryTrailOrdersList(StudentOrderForm studentOrderForm) {

		String teacherId = studentOrderForm.getTeacherId();
		String orderStatus = studentOrderForm.getOrderStatus();
		logger.info("teacherId={} , orderStatus={}", teacherId, orderStatus);

		TeacherStudentOrderPo t = new TeacherStudentOrderPo();
		t.setTeacherId(Integer.valueOf(teacherId));
		t.setOrderStatus(Integer.valueOf(orderStatus));

		List<StudentOrderVo> list = orderMapper.queryTrailOrdersList(t);

		return list;
	}

	@Override
	public StudentOrderVo queryOneOrderInfosById(StudentOrderForm studentOrderForm) {

		String orderId = studentOrderForm.getOrderId();

		logger.info("orderId={}", orderId);
		StudentOrderVo studentOrderVo = null;
		
		StudentOrderPo studentOrderPo = new StudentOrderPo();
		
		studentOrderPo.setOrderId(Integer.valueOf(orderId));
		studentOrderPo.setOrderStatus(Integer.valueOf(studentOrderForm.getOrderStatus()));
		
		if (orderId != null && StringUtils.isNotBlank(orderId)) {
			studentOrderVo = orderMapper.queryOneOrderInfosById(studentOrderPo);
		}
		return studentOrderVo;
	}

	/**
	 * 学生创建新的需求订单
	 */
	@Override
	@Transactional
	public int addOrderInfo(StudentOrderForm studentOrderForm) {

		StudentOrderPo studentOrderPo = new StudentOrderPo();
		String orderNum = UUIDUtils.getRandomNumBySub();
		studentOrderPo.setOrderNum(orderNum);

		studentOrderPo.setStudnetId(Integer.valueOf(studentOrderForm.getStudnetId()));
		logger.info("StudnetId={}", studentOrderForm.getStudnetId());
		studentOrderPo.setStudentName(studentOrderForm.getStudentName());
		logger.info("StudentName={}", studentOrderForm.getStudentName());
		studentOrderPo.setStudnetArea(Integer.valueOf(studentOrderForm.getStudnetArea()));
		logger.info("StudnetArea={}", studentOrderForm.getStudnetArea());
		studentOrderPo.setDemandAddress(studentOrderForm.getDemandAddress());
		logger.info("DemandAddress={}", studentOrderForm.getDemandAddress());
		studentOrderPo.setDemandGrade(studentOrderForm.getDemandGrade());
		logger.info("DemandGrade={}", studentOrderForm.getDemandGrade());
		studentOrderPo.setDemandBranch(studentOrderForm.getDemandBranch());
		logger.info("DemandBranch={}", studentOrderForm.getDemandBranch());
		studentOrderPo.setDemandPhone(studentOrderForm.getDemandPhone());
		logger.info("DemandPhone={}", studentOrderForm.getDemandPhone());
		studentOrderPo.setDemandDesc(studentOrderForm.getDemandDesc());
		logger.info("DemandDesc={}", studentOrderForm.getDemandDesc());
		studentOrderPo.setTeachCount(Integer.valueOf(studentOrderForm.getTeachCount()));
		logger.info("TeachCount={}", studentOrderForm.getTeachCount());
		studentOrderPo.setTeachTime(studentOrderForm.getTeachTime());
		studentOrderPo.setStatus(1);
		studentOrderPo.setCreateTime(new Date());
		studentOrderPo.setCreateUser(studentOrderForm.getStudentName());
		logger.info("TeacherName={}", studentOrderForm.getStudentName());
		studentOrderPo.setUpdateTime(new Date());
		studentOrderPo.setUpdateUser(studentOrderForm.getStudentName());

		int i = orderMapper.addOrderInfo(studentOrderPo);
		return i;
	}

	/**
	 * 确认试讲时间
	 */
	@Override
	@Transactional
	public int updateTrialTimeById(TeacherStudentOrderForm teacherStudentOrderForm) {

		StudentOrderPo studentOrderPo = new StudentOrderPo();

		logger.info("trailTime={}", teacherStudentOrderForm.getTrialTime());

		studentOrderPo.setTrialTime(teacherStudentOrderForm.getTrialTime());
		studentOrderPo.setOrderId(Integer.valueOf(teacherStudentOrderForm.getOrderId()));
		studentOrderPo.setOrderStatus(OrderStatusEnum.TRIAL.getStatus());
		studentOrderPo.setUpdateTime(new Date());
		studentOrderPo.setUpdateUser(teacherStudentOrderForm.getTeacherName());

		int i = orderMapper.updateTrialTimeById(studentOrderPo);

		Integer teacherId = null;
		
		if(teacherStudentOrderForm.getTeacherId() != null && StringUtils.isNotBlank(teacherStudentOrderForm.getTeacherId())) {
			teacherId = Integer.valueOf(teacherStudentOrderForm.getTeacherId());
		}
		
		
		TeacherVo teacherVo = userInfoMapper.queryTeacherHomeInfos(teacherId);

		// 更新教员对所有报名订单被选作试讲的数量
		TeacherPo teacherPo = new TeacherPo();

		teacherPo.setTelephone(teacherVo.getTelephone());
		teacherPo.setChooseCount(teacherVo.getChooseCount() + 1);

		int j = userInfoMapper.updateUserInfo(teacherPo);
		if (i > 0 && j > 0) {
			return 1;
		}
		return -i;
	}

	/**
	 * 教员报名试讲
	 */
	@Override
	@Transactional
	public int singUpStudentOrder(TeacherStudentOrderForm teacherStudentOrderForm) {

		int orderId = Integer.valueOf(teacherStudentOrderForm.getOrderId());

		// 增加教员学生订单表关系表
		TeacherStudentOrderPo teacherStudentOrderPo = new TeacherStudentOrderPo();
		teacherStudentOrderPo.setOrderId(orderId);
		teacherStudentOrderPo.setTeacherId(Integer.valueOf(teacherStudentOrderForm.getTeacherId()));
		teacherStudentOrderPo.setTeacherName(teacherStudentOrderForm.getTeacherName());
		teacherStudentOrderPo.setTeacherPhone(teacherStudentOrderForm.getTeacherPhone());
		teacherStudentOrderPo.setChooseStatus(0);
		teacherStudentOrderPo.setStatus(1);
		teacherStudentOrderPo.setCreateTime(new Date());
		teacherStudentOrderPo.setCreateUser(teacherStudentOrderForm.getTeacherName());
		teacherStudentOrderPo.setUpdateTime(new Date());
		teacherStudentOrderPo.setUpdateUser(teacherStudentOrderForm.getTeacherName());

		int i = orderMapper.addTSOrderInfo(teacherStudentOrderPo);

		// 更新学生需求报名人数
		StudentOrderPo studentOrderPo = new StudentOrderPo();
		studentOrderPo.setOrderId(orderId);

		StudentOrderVo studentOrderVo = orderMapper.queryOneOrderInfosById(studentOrderPo);

		studentOrderPo.setPersonNum(studentOrderVo.getPersonNum() + 1);
		studentOrderPo.setOrderStatus(OrderStatusEnum.SIGNUP.getStatus());
		studentOrderPo.setUpdateTime(new Date());
		studentOrderPo.setUpdateUser(teacherStudentOrderForm.getTeacherName());

		int j = orderMapper.updateOneOrderStatusInfoById(studentOrderPo);
		
		Integer teacherId = null;
		
		if(teacherStudentOrderForm.getTeacherId() != null && StringUtils.isNotBlank(teacherStudentOrderForm.getTeacherId())) {
			teacherId = Integer.valueOf(teacherStudentOrderForm.getTeacherId());
		}

		TeacherVo teacherVo = userInfoMapper.queryTeacherHomeInfos(teacherId);

		// 更新教员对所有报名订单的数量
		TeacherPo teacherPo = new TeacherPo();

		teacherPo.setTelephone(teacherVo.getTelephone());
		teacherPo.setSingUpCount(teacherVo.getSingUpCount() + 1);

		int k = userInfoMapper.updateUserInfo(teacherPo);

		if (i > 0 && j > 0 && k > 0) {
			return 1;
		}

		return 0;
	}

	/**
	 * 试讲通过，未付款
	 */
	@Override
	@Transactional
	public int createNewOrder(TeacherStudentOrderForm teacherStudentOrderForm) {

		// 试讲通过，更新学生需求订单表
		StudentOrderPo studentOrderPo = new StudentOrderPo();

		studentOrderPo.setOrderTime(new Date());

		// 订单状态根据付款状态进行更新，此处的值不准
		studentOrderPo.setOrderStatus(OrderStatusEnum.UNPAY.getStatus());
		studentOrderPo.setUpdateTime(new Date());
		studentOrderPo.setUpdateUser(teacherStudentOrderForm.getStudentName());
		int i = orderMapper.updateOneOrderStatusInfoById(studentOrderPo);
		
		Integer teacherId = null;
		
		if(teacherStudentOrderForm.getTeacherId() != null && StringUtils.isNotBlank(teacherStudentOrderForm.getTeacherId())) {
			teacherId = Integer.valueOf(teacherStudentOrderForm.getTeacherId());
		}

		TeacherVo teacherVo = userInfoMapper.queryTeacherHomeInfos(teacherId);
		// 更新教员对所有报名订单的数量
		TeacherPo teacherPo = new TeacherPo();

		teacherPo.setTelephone(teacherVo.getTelephone());
		// 该教员被聘用的订单数量+1
		teacherPo.setEmployCount(teacherVo.getEmployCount() + 1);

		int k = userInfoMapper.updateUserInfo(teacherPo);

		if (i > 0 && k > 0) {
			return 1;
		}

		return -1;
	}

	/**
	 * 教员被选中，准备试讲，更新教员学生订单关系表
	 */
	@Override
	@Transactional
	public int addNewTrailOrder(TeacherStudentOrderForm teacherStudentOrderForm) {
		// 更新被选中的状态
		TeacherStudentOrderPo teacherStudentOrderPo = new TeacherStudentOrderPo();

		teacherStudentOrderPo.setOrderId(Integer.valueOf(teacherStudentOrderForm.getOrderId()));
		teacherStudentOrderPo.setTeacherId(Integer.valueOf(teacherStudentOrderForm.getTeacherId()));
		teacherStudentOrderPo.setChooseStatus(1);
		teacherStudentOrderPo.setUpdateTime(new Date());
		teacherStudentOrderPo.setUpdateUser(teacherStudentOrderForm.getStudentName());

		int i = orderMapper.updateTSOrder(teacherStudentOrderPo);

		// 更新被选中的状态
		StudentOrderPo studentOrderPo = new StudentOrderPo();

		// 订单状态根据付款状态进行更新，此处的值不准
		studentOrderPo.setOrderStatus(OrderStatusEnum.CHOOSE.getStatus());
		studentOrderPo.setUpdateTime(new Date());
		studentOrderPo.setUpdateUser(teacherStudentOrderForm.getStudentName());
		int j = orderMapper.updateOneOrderStatusInfoById(studentOrderPo);

		if (i > 0 && j > 0) {
			return 1;
		}

		return -1;
	}

	/**
	 * 试讲通过，付款，并计算聘用率（付款完成的订单数量/试讲的订单+试讲不通过的订单+试讲通过的订单+支付未完成的订单+支付完成的订单）
	 */
	@Override
	@Transactional
	public int updateOrderForPay(TeacherStudentOrderForm teacherStudentOrderForm) {
		// 试讲通过，更新学生需求订单表
		StudentOrderPo studentOrderPo = new StudentOrderPo();
		// TODO Auto-generated method stub
		// 付款的这个地方需要重写
		studentOrderPo.setOrderMoney(teacherStudentOrderForm.getOrderMoney());

		// 付款完成
		studentOrderPo.setOrderStatus(OrderStatusEnum.PAY.getStatus());
		studentOrderPo.setPayTime(new Date());
		studentOrderPo.setUpdateTime(new Date());
		studentOrderPo.setUpdateUser(teacherStudentOrderForm.getStudentName());
		int i = orderMapper.updateOneOrderStatusInfoById(studentOrderPo);
		
		Integer teacherId = null;
		
		if(teacherStudentOrderForm.getTeacherId() != null && StringUtils.isNotBlank(teacherStudentOrderForm.getTeacherId())) {
			teacherId = Integer.valueOf(teacherStudentOrderForm.getTeacherId());
		}

		TeacherVo teacherVo = userInfoMapper.queryTeacherHomeInfos(teacherId);
		// 更新教员对所有报名订单的数量
		TeacherPo teacherPo = new TeacherPo();

		teacherPo.setTelephone(teacherVo.getTelephone());
		teacherPo.setEmployCount(teacherVo.getEmployCount() + 1);

		int employCount = teacherVo.getEmployCount();

		int chooseCount = teacherVo.getChooseCount();

		double newRate = employCount / chooseCount;
		logger.info("employCount={} , chooseCount={} , newRate={}",  employCount,
				chooseCount, newRate);

		BigDecimal bg = new BigDecimal(newRate).setScale(2, RoundingMode.DOWN);
		logger.info("employRate = {}", RegUtils.doubleToPersent().format(bg));
		// 更新该教员的聘用率
		teacherPo.setEmployRate(RegUtils.doubleToPersent().format(bg));

		int k = userInfoMapper.updateUserInfo(teacherPo);

		if (i > 0 && k > 0) {
			return 1;
		}

		return -1;
	}

	@Override
	@Transactional
	public int updateOrderForTrailLost(TeacherStudentOrderForm teacherStudentOrderForm) {

		// 试讲未通过，更新学生需求订单表
		StudentOrderPo studentOrderPo = new StudentOrderPo();
		studentOrderPo.setOrderStatus(OrderStatusEnum.TRIALLOST.getStatus());
		studentOrderPo.setUpdateTime(new Date());
		studentOrderPo.setUpdateUser(teacherStudentOrderForm.getStudentName());
		int i = orderMapper.updateOneOrderStatusInfoById(studentOrderPo);

		return i;
	}
	
	
	/**
	 * 点击续课并付款
	 * @return
	 */
	@Override
	@Transactional
	public int UpdateOrderForResumption(TeacherStudentOrderForm teacherStudentOrderForm) {
		
		StudentOrderPo studentOP = new StudentOrderPo();
		studentOP.setOrderId(Integer.valueOf(teacherStudentOrderForm.getOrderId()));
		studentOP.setOrderStatus(OrderStatusEnum.PAY.getStatus());
		
		TeacherStudentOrderVo  teacherStudentOrderVo = orderMapper.queryOneOrderDetailInfosById(studentOP);
		
		StudentOrderPo studentOrderPo = new StudentOrderPo();
		int isResumption = teacherStudentOrderVo.getIsResumption();
		//判断该订单是否已经续课，0未续课，1已续课
		if(isResumption == 0) {			
			studentOrderPo.setIsResumption(1);
		}
		
		//TODO 付款
		
		studentOrderPo.setOrderMoney(teacherStudentOrderForm.getOrderMoney());
		studentOrderPo.setPayTime(new Date());
		
		studentOrderPo.setUpdateTime(new Date());
		studentOrderPo.setUpdateUser(teacherStudentOrderForm.getStudentName());
		
		int i = orderMapper.updateOneOrderStatusInfoById(studentOrderPo);
		
		Integer teacherId = null;
		
		if(teacherStudentOrderForm.getTeacherId() != null && StringUtils.isNotBlank(teacherStudentOrderForm.getTeacherId())) {
			teacherId = Integer.valueOf(teacherStudentOrderForm.getTeacherId());
		}
		
		TeacherVo teacherVo = userInfoMapper.queryTeacherHomeInfos(teacherId);
		
		TeacherPo teacher =new TeacherPo ();

		teacher.setTeacherId(teacherStudentOrderVo.getTeacherId());
		teacher.setTelephone(teacherStudentOrderVo.getTeacherPhone());
		
		//如果当前订单没有续过课,则本次续课时将该教员的所记录的续课的总数+1,
		//如果续过课,则续课总数不变。
		if(isResumption == 0) {
			teacher.setResumptionCount(teacherVo.getResumptionCount()+1);
		}
		
		int employCount = teacherVo.getEmployCount();
		
		int resumptionCount = teacherVo.getResumptionCount();
		
		double newRate = resumptionCount / employCount;
		
		logger.info(" employCount={} , resumptionCount={} , newRate={}", employCount,
				resumptionCount, newRate);

		BigDecimal bg = new BigDecimal(newRate).setScale(2, RoundingMode.DOWN);
		logger.info("employRate = {}", bg);
		
		teacher.setResumptionRate(RegUtils.doubleToPersent().format(bg));
		
		// 更新该教员的续课率
		int j = userInfoMapper.updateUserInfo(teacher);
		
		if(i > 0 && j > 0) {
			return 1;
		}
		
		return -1;
	}

}
