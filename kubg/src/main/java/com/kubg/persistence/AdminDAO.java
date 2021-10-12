package com.kubg.persistence;

import java.util.List;

import com.kubg.domain.CategoryVO;

public interface AdminDAO {

	// 카테고리
	public List<CategoryVO> category() throws Exception;
}
