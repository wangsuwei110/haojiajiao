package com.education.hjj.bz.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.education.hjj.bz.entity.ComplaintSuggestionPo;

@Mapper
public interface ComplaintSuggestionMapper {
	
	int addComplaintSuggestion(ComplaintSuggestionPo complaintSuggestionPo);

}
