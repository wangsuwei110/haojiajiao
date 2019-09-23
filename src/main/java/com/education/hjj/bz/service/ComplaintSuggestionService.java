package com.education.hjj.bz.service;

import java.util.List;

import com.education.hjj.bz.entity.vo.ComplaintSuggestionVo;
import com.education.hjj.bz.formBean.ComplaintSuggestionForm;

public interface ComplaintSuggestionService {

	int addComplaintSuggestion(ComplaintSuggestionForm complaintSuggestionForm);
	
	int updateComplaintSuggestion(ComplaintSuggestionForm complaintSuggestionForm);
	
	ComplaintSuggestionVo queryComplaintSuggestionById(int complaintSuggestionId);
	
	List<ComplaintSuggestionVo> queryAllComplaintSuggestion();
}
