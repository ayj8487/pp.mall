package com.kubg.persistence;

import java.util.List;

import com.kubg.domain.GoodsViewVO;

public interface ShopDAO {

	//카테고리별 상품 리스트 : 1차분류
	public List<GoodsViewVO> list(int cateCode , int cateCodeRef) throws Exception;

	//카테고리별 상품 리스트 : 2차분류
	public List<GoodsViewVO> list(int cateCode) throws Exception;
}
