package com.kubg.service;

import java.util.List;

import com.kubg.domain.GoodsViewVO;

public interface ShopService {

	//카테고리별 상품리스트
	public List<GoodsViewVO> list(int cateCode, int level) throws Exception;

	//상품조회
	public GoodsViewVO goodsView(int gdsNum) throws Exception;
}
