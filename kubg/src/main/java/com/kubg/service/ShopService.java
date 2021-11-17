package com.kubg.service;

import java.util.List;

import com.kubg.domain.CartListVO;
import com.kubg.domain.CartVO;
import com.kubg.domain.GoodsVO;
import com.kubg.domain.GoodsViewVO;
import com.kubg.domain.OrderDetailVO;
import com.kubg.domain.OrderListVO;
import com.kubg.domain.OrderVO;
import com.kubg.domain.ReplyListVO;
import com.kubg.domain.ReplyVO;

public interface ShopService {

	//카테고리별 상품리스트
	public List<GoodsViewVO> list(int cateCode, int level) throws Exception;

	//상품조회
	public GoodsViewVO goodsView(int gdsNum) throws Exception;

	//상품소감(댓글) 작성
	public void registReply(ReplyVO reply) throws Exception;

	//상품소감(댓글) 조회 리스트
	public List<ReplyListVO> replyList(int gdsNum) throws Exception;
	
	//상품소감(댓글) 삭제
	public void deleteReply(ReplyVO reply) throws Exception;

	//아이디체크
	public String idCheck(int repNum) throws Exception;
	
	//상품소감(댓글) 수정
	public void modifyReply(ReplyVO reply) throws Exception;

	//카트 담기
	public void addCart(CartVO cart) throws Exception;

	//카트리스트
	public List<CartListVO> cartList(String userId) throws Exception;

	//카트식제
	public void deleteCart(CartVO cart) throws Exception;
	
	//주문정보
	public void orderInfo(OrderVO order) throws Exception;
	
	//주문 상세 정보
	public void orderInfo_Details(OrderDetailVO orderDetail) throws Exception;
	
	//주문 완료후 카트비우기
	public void cartAllDelete(String userId) throws Exception;

	//주문 목록 리스트
	public List<OrderVO> orderList(OrderVO order) throws Exception;

	//주문 목록 리스트(상세)
	public List<OrderListVO> orderView(OrderVO order) throws Exception;

}
