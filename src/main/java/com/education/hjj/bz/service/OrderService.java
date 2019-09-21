package com.education.hjj.bz.service;

import java.util.List;

import com.education.hjj.bz.entity.vo.StudentOrderVo;
import com.education.hjj.bz.formBean.StudentOrderForm;
import com.education.hjj.bz.formBean.TeacherStudentOrderForm;

public interface OrderService {
	
	//查询适合我的订单需求列表
	List<StudentOrderVo> queryAllOrdersListBySuit();
	
	//订单列表页，查询所有的订单，不包含已经付款完成的订单 
	List<StudentOrderVo> queryAllOrders(StudentOrderForm studentOrderForm);
	
	//试讲订单列表 
	List<StudentOrderVo> queryTrailOrdersList(StudentOrderForm studentOrderForm);
	
	//订单详情
	StudentOrderVo queryOneOrderInfosById(StudentOrderForm studentOrderForm);
	
	//添加新的需求订单
	int addOrderInfo(StudentOrderForm studentOrderForm);
	
	//确认时将时间
	int updateTrialTimeById(TeacherStudentOrderForm teacherStudentOrderForm);
	
	//教员报名学生需求
	int singUpStudentOrder(TeacherStudentOrderForm teacherStudentOrderForm);
	
	//试讲通过创建订单
	int createNewOrder(TeacherStudentOrderForm teacherStudentOrderForm);

	//教员被选中，准备试讲，更新教员学生订单关系表
	int addNewTrailOrder(TeacherStudentOrderForm teacherStudentOrderForm);
	
	//试讲通过创建订单
	int updateOrderForPay(TeacherStudentOrderForm teacherStudentOrderForm);
	
	//试讲不通过更新orderStatus状态
	int updateOrderForTrailLost(TeacherStudentOrderForm teacherStudentOrderForm);
	
	//点击续课并付款
	int UpdateOrderForResumption(TeacherStudentOrderForm teacherStudentOrderForm);
}
