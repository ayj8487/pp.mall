package com.kubg.service;

import java.util.List;

import com.kubg.domain.CategoryVO;

public interface AdminService {

	//카테고리
	public List<CategoryVO> category() throws Exception;
}
