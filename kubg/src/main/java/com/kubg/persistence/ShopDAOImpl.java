package com.kubg.persistence;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.kubg.domain.GoodsViewVO;

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



}
