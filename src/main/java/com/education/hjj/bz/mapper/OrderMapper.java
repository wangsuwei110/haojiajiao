package com.education.hjj.bz.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.education.hjj.bz.entity.StudentOrderPo;
import com.education.hjj.bz.entity.TeacherStudentOrderPo;
import com.education.hjj.bz.entity.vo.StudentOrderVo;
import com.education.hjj.bz.entity.vo.TeacherStudentOrderVo;

@Mapper
public interface OrderMapper {
	
	//查询适合我的订单需求列表
	List<StudentOrderVo> queryAllOrdersListBySuit();
	
	//订单列表页，查询所有的订单，不包含已经付款完成的订单 
	List<StudentOrderVo> queryAllOrders(StudentOrderPo studentOrderPo);
	
	//试讲订单列表 
	List<StudentOrderVo> queryTrailOrdersList(TeacherStudentOrderPo t);
	
	//订单详情
	StudentOrderVo queryOneOrderInfosById(StudentOrderPo studentOrderPo);
	
	TeacherStudentOrderVo queryOneOrderDetailInfosById(StudentOrderPo studentOrderPo);
	
	//添加新的需求订单
	int addOrderInfo(StudentOrderPo studentOrderPo);
	
	//教员被选中，准备试讲，更新教员学生订单关系表
	int updateTSOrder(TeacherStudentOrderPo teacherStudentOrderPo);
	
	//教员报名需求订单时，对教员学生订单关系表新增
	int addTSOrderInfo(TeacherStudentOrderPo teacherStudentOrderPo);
	
	//确认时将时间
	int updateTrialTimeById(StudentOrderPo studentOrderPo);
	
	//试讲通过创建订单
	int updateOneOrderStatusInfoById(StudentOrderPo studentOrderPo);
	
	
	int updateOneOrderPersonNumInfoById(StudentOrderPo studentOrderPo);
	
}
