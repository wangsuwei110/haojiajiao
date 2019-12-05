package com.education.hjj.bz.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.education.hjj.bz.entity.ComplaintSuggestionPo;
import com.education.hjj.bz.entity.vo.ComplaintSuggestionVo;
import com.education.hjj.bz.formBean.ComplaintSuggestionForm;

@Mapper
public interface ComplaintSuggestionMapper {
	
	int addComplaintSuggestion(ComplaintSuggestionPo complaintSuggestionPo);

	int updateComplaintSuggestion(ComplaintSuggestionPo complaintSuggestionPo);
	
	ComplaintSuggestionVo queryComplaintSuggestionById(int complaintSuggestionId);
	
	List<ComplaintSuggestionVo> queryAllComplaintSuggestion();
	
	List<ComplaintSuggestionVo> queryAllComplaintSuggestionByEducational(ComplaintSuggestionForm complaintSuggestionForm);
}
