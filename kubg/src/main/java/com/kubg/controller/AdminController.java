package com.kubg.controller;

import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.kubg.domain.CategoryVO;
import com.kubg.domain.GoodsVO;
import com.kubg.service.AdminService;

import net.sf.json.JSONArray;

@Controller
@RequestMapping("/admin/*")
public class AdminController {

	private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

	@Inject
	AdminService adminService;
	
	// 관리자 화면
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public void getIndex() throws Exception{
	logger.info("get index");
	}
	
	// 상품 등록 (카테고리호출과 등록페이지)
	@RequestMapping(value = "/goods/register", method = RequestMethod.GET)
	public void getGoodsRegister(Model model) throws Exception {
	 logger.info("get goods register");
	 
	 List<CategoryVO> category = null;
	 category = adminService.category();
	 model.addAttribute("category", JSONArray.fromObject(category));
	}
	// CategoryVO형태의 List변수 category를 선언하고 adminService.category() 호출한 뒤 결과값을 
	// category에 입력, JSONArray를 이용해 category를 JSON타입으로 변경한 뒤, category라는 명칭으로 모델에 추가함. 
	// 이 메서드(getGoodsRegister)가 호출 될 때 모델을 jsp에 넘겨서 사용할 수 있다.	
	
	//상품 등록
	@RequestMapping(value = "/goods/register", method = RequestMethod.POST)
	public String postGoodsRegister(GoodsVO vo) throws Exception{
		adminService.register(vo);
		
		return "redirect:/admin/index";
	}
	//상품 목록 리스트
	@RequestMapping(value = "/goods/list", method = RequestMethod.GET)
	public void getGoodsList(Model model) throws Exception{
		logger.info("get goods list");
		
		List<GoodsVO> list = adminService.goodslist();
		
		model.addAttribute("list",list);
	}
	// 상품 조회
	@RequestMapping(value = "/goods/view", method = RequestMethod.GET)
	public void getGoodsview(@RequestParam("n") int gdsNum, Model model) throws Exception {
	 logger.info("get goods view");
	 
	 GoodsVO goods = adminService.goodsView(gdsNum);
	 
	 model.addAttribute("goods", goods);
	}
	/* 
	 * 매개변수 @RequestParam("n") int gdsNum 는, URL주소에서 "n"의 값을 찾아서 
	 * int gdsNum에 전달. 목록에서 링크 주소를 /admin/goods/view?n=[상품번호] 
	 * 형식으로 했기 떄문에 n를 찾는것이며, 만약 다른 문자로 했다면 그 문자로 해야함
	 */
	
}
