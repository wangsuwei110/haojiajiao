package com.education.hjj.bz.service.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.education.hjj.bz.entity.ComplaintSuggestionPo;
import com.education.hjj.bz.entity.vo.ComplaintSuggestionVo;
import com.education.hjj.bz.formBean.ComplaintSuggestionForm;
import com.education.hjj.bz.mapper.ComplaintSuggestionMapper;
import com.education.hjj.bz.service.ComplaintSuggestionService;

@Service
@Transactional
public class ComplaintSuggestionServiceImpl implements ComplaintSuggestionService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ComplaintSuggestionMapper complaintSuggestionMapper;

	@Override
	public int addComplaintSuggestion(ComplaintSuggestionForm complaintSuggestionForm) {
		
		
		String telephone = complaintSuggestionForm.getTelephone();
		
		logger.info("telephone={}",telephone);
		
		ComplaintSuggestionPo complaintSuggestionPo = new ComplaintSuggestionPo();
		
		complaintSuggestionPo.setPersonId(Integer.valueOf(complaintSuggestionForm.getPersonId()));
		complaintSuggestionPo.setType(1);
		complaintSuggestionPo.setContent(complaintSuggestionForm.getContent());
		complaintSuggestionPo.setTelephone(complaintSuggestionForm.getTelephone());
		complaintSuggestionPo.setStatus(1);
		complaintSuggestionPo.setCreateTime(new Date());
		complaintSuggestionPo.setCreateUser(complaintSuggestionForm.getPersonId());
		complaintSuggestionPo.setUpdateTime(new Date());
		complaintSuggestionPo.setUpdateUser(complaintSuggestionForm.getPersonId());
		
		int i = complaintSuggestionMapper.addComplaintSuggestion(complaintSuggestionPo);
		
		return i;
	}

	@Override
	public int updateComplaintSuggestion(ComplaintSuggestionForm complaintSuggestionForm) {
		
		ComplaintSuggestionPo complaintSuggestionPo = new ComplaintSuggestionPo();
		
		complaintSuggestionPo.setReply(complaintSuggestionForm.getReply());
		complaintSuggestionPo.setStatus(0);
		complaintSuggestionPo.setUpdateTime(new Date());
		complaintSuggestionPo.setUpdateUser(complaintSuggestionForm.getPersonId());
		
		int i = complaintSuggestionMapper.updateComplaintSuggestion(complaintSuggestionPo);
		return i;
	}

	@Override
	public ComplaintSuggestionVo queryComplaintSuggestionById(int complaintSuggestionId) {
		
		ComplaintSuggestionVo  ComplaintSuggestionVo  = complaintSuggestionMapper.queryComplaintSuggestionById(complaintSuggestionId);
		
		return ComplaintSuggestionVo;
	}

	@Override
	public List<ComplaintSuggestionVo> queryAllComplaintSuggestion() {
		
		List<ComplaintSuggestionVo>  list = complaintSuggestionMapper.queryAllComplaintSuggestion();
		
		return list;
	}

	@Override
	public List<ComplaintSuggestionVo> queryAllComplaintSuggestionByEducational(
			ComplaintSuggestionForm complaintSuggestionForm) {
		List<ComplaintSuggestionVo> list = complaintSuggestionMapper.queryAllComplaintSuggestionByEducational(complaintSuggestionForm);
		return list;
	}

	@Override
	public int updateComplaintSuggestionByEducational(ComplaintSuggestionForm complaintSuggestionForm) {
		complaintSuggestionForm.setUpdateTime(new Date());
		int i = complaintSuggestionMapper.updateComplaintSuggestionByEducational(complaintSuggestionForm);
		return i;
	}

}
