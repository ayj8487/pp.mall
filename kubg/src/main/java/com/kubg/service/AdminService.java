package com.kubg.service;

import java.util.List;

import com.kubg.domain.CategoryVO;
import com.kubg.domain.GoodsVO;
import com.kubg.domain.GoodsViewVO;
import com.kubg.domain.OrderListVO;
import com.kubg.domain.OrderVO;

public interface AdminService {

	//카테고리
	public List<CategoryVO> category() throws Exception;

	//상품등록
	public void register(GoodsVO vo) throws Exception;

	//상품목록 리스트
	public List<GoodsViewVO> goodslist() throws Exception;

	//상품 조회 + 카테고리 조인
	public GoodsViewVO goodsView(int gdsNum) throws Exception;

	//상품 수정
	public void goodsModify(GoodsVO vo)throws Exception;
	
	//상품 삭제
	public void goodsDelete(int gdsNum) throws Exception;

	//주문 목록 리스트
	public List<OrderVO> orderList() throws Exception;

	//주문 목록 리스트(상세)
	public List<OrderListVO> orderView(OrderVO order) throws Exception;

	//배송 사태
	public void delivery(OrderVO order) throws Exception;

	//상품 수량 조절
	public void changeStock(GoodsVO goods) throws Exception;
	
}
