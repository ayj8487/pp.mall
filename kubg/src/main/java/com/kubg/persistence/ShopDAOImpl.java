package com.kubg.persistence;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.kubg.domain.CartListVO;
import com.kubg.domain.CartVO;
import com.kubg.domain.GoodsViewVO;
import com.kubg.domain.OrderDetailVO;
import com.kubg.domain.OrderListVO;
import com.kubg.domain.OrderVO;
import com.kubg.domain.ReplyListVO;
import com.kubg.domain.ReplyVO;

@Repository
public class ShopDAOImpl implements ShopDAO {

	@Inject SqlSession sql;
	
	//매퍼
	private static String namespace = "com.kubg.mappers.shopMapper";

	// 카테고리별 상품 리스트 : 1차 분류
	@Override
	public List<GoodsViewVO> list(int cateCode, int cateCodeRef) throws Exception {
	 
	 HashMap<String, Object> map = new HashMap<String, Object>();
	 
	 map.put("cateCode", cateCode);
	 map.put("cateCodeRef", cateCodeRef);
	 
	 return sql.selectList(namespace + ".list_1", map);
	}

	// 카테고리별 상품 리스트 : 2차 분류
	@Override
	public List<GoodsViewVO> list(int cateCode) throws Exception {
	 
	 return sql.selectList(namespace + ".list_2", cateCode);
	}
	/*
	2차 분류는 기존 코드와 차이점이 없으나 1차 분류는 카테고리 코드(cateCode)와 
	카테고리 참조 코드(cateCodeRef) 2개의 매개변수를 받아온뒤, 
	해시맵을 이용해 두 값을 하나의 맵으로 합쳐서 매퍼로 보냄.

	1차 분류와 2차 분류의 메서드명이 list로 똑같은데,이 둘은 매개변수가 다르기 때문에 
	오버로딩할 수 있으니 에러는 발생하지 않음.
	 */
	
	// 상품 조회
	@Override
	public GoodsViewVO goodsView(int gdsNum) throws Exception {
		return sql.selectOne("com.kubg.mappers.adminMapper" + ".goodsView", gdsNum);
		// 기존 관리자모드 상품조회매퍼를 그대로 쓸것이기 때문에 namespace가 아닌 매퍼의 경로를 입력
	}

	//상품소감(댓글) 작성
	@Override
	public void registReply(ReplyVO reply) throws Exception {
		sql.insert(namespace +".registReply", reply);
	}

	//상품소감(댓글) 조회 리스트
	@Override
	public List<ReplyListVO> replyList(int gdsNum) throws Exception {
		return sql.selectList(namespace + ".replyList", gdsNum);
	}

	//상품소감(댓글) 삭제
	@Override
	public void deleteReply(ReplyVO reply) throws Exception {
		sql.delete(namespace + ".deleteReply" ,reply);
	}

	//아이디 체크
	@Override
	public String idCheck(int repNum) throws Exception {
		return sql.selectOne(namespace + ".replyUserIdCheck", repNum);
	}

	//상품소감(댓글) 수정
	@Override
	public void modifyReply(ReplyVO reply) throws Exception {
		sql.update(namespace + ".modifyReply", reply);
	}
	
	//카트 담기
	@Override
	public void addCart(CartVO cart) throws Exception {
		sql.insert(namespace + ".addCart", cart);
	}

	//카트리스트
	@Override
	public List<CartListVO> cartList(String userId) throws Exception {
		return sql.selectList(namespace + ".cartList", userId);
	}

	//카트삭제
	@Override
	public void deleteCart(CartVO cart) throws Exception {
		sql.delete(namespace +".deleteCart",cart);
	}

	//주문정보
	@Override
	public void orderInfo(OrderVO order) throws Exception {
		sql.insert(namespace + ".orderInfo", order);
	}

	//주문 상세 정보
	@Override
	public void orderInfo_Details(OrderDetailVO orderDetail) throws Exception {
		sql.insert(namespace + ".orderInfo_Details", orderDetail);
	}

	//주문 완료후 카트비우기
	@Override
	public void cartAllDelete(String userId) throws Exception {
		sql.delete(namespace + ".cartAllDelete", userId);
	}

	//주문 목록 리스트
	@Override
	public List<OrderVO> orderList(OrderVO order) throws Exception {
		return sql.selectList(namespace + ".orderList", order);
	}

	//주문 목록 리스트(상세)
	@Override
	public List<OrderListVO> orderView(OrderVO order) throws Exception {
		return sql.selectList(namespace + ".orderView", order);
	}



}
