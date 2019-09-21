package com.education.hjj.bz.service;

import org.springframework.web.multipart.MultipartFile;

import com.education.hjj.bz.formBean.ComplaintSuggestionForm;
import com.education.hjj.bz.formBean.PictureForm;

public interface ComplaintSuggestionService {

	int addComplaintSuggestion(ComplaintSuggestionForm complaintSuggestionForm, MultipartFile files, Integer type,
			PictureForm pictureForm);
}
