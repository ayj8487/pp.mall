package com.kubg.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.kubg.domain.GoodsViewVO;
import com.kubg.persistence.ShopDAO;

@Service
public class ShopServiceImpl implements ShopService {
	
	@Inject
	private ShopDAO dao;

	// 카테고리별 상품 리스트
	@Override
	public List<GoodsViewVO> list(int cateCode, int level) throws Exception {
	 
	 int cateCodeRef = 0;  // 카테고리 참조 코드. 없어도 무관
	 
	 if(level == 1) {  // lavel 1 = 1차 분류.
	  
	  cateCodeRef = cateCode;
	  return dao.list(cateCode, cateCodeRef);
	  // 두가지 모두 cateCode로 해도 무관
	  
	 } else {  // lavel 2 = 2차 분류
	  
	  return dao.list(cateCode);
	  
	 }
	}

	//상품 조회
	@Override
	public GoodsViewVO goodsView(int gdsNum) throws Exception {
		return dao.goodsView(gdsNum);
	}
}


