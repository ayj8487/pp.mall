package com.kubg.controller;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.kubg.domain.GoodsViewVO;
import com.kubg.domain.MemberVO;
import com.kubg.domain.ReplyListVO;
import com.kubg.domain.ReplyVO;
import com.kubg.service.ShopService;

@Controller
@RequestMapping("/shop/*")
public class ShopController {

	private static final Logger logger = LoggerFactory.getLogger(ShopController.class);

	@Inject
	ShopService service;
	
	 // 카테고리별 상품 리스트
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	 public void getList(@RequestParam("c") int cateCode,
		      @RequestParam("l") int level, Model model) throws Exception {
	  logger.info("get llist");
	  
	  List<GoodsViewVO> list = null;
	  list = service.list(cateCode, level);
	  
	  model.addAttribute("list", list);
	}
	
	// 상품 조회
	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public void getView(@RequestParam("n") int gdsNum, Model model) throws Exception {
	 logger.info("get view");
	 
	 GoodsViewVO view = service.goodsView(gdsNum);
	 model.addAttribute("view", view);
	 
	 // 댓글조회 추가 후 밑의 코드 추가 
	 List<ReplyListVO> reply = service.replyList(gdsNum);
	 model.addAttribute("reply", reply);
	}
	
	// 상품조회 - 소감(댓글) 작성
	// HttpSession 을 이용해 member세션에 저장되어있는 유저 아이디를 가져옴
	@RequestMapping(value = "/view", method = RequestMethod.POST)
	public String registReply(ReplyVO reply, HttpSession session) throws Exception{
	 logger.info("regist reply");
		 
	 MemberVO member = (MemberVO)session.getAttribute("member");
	 reply.setUserId(member.getUserId());
	 
	 service.registReply(reply);
		
	 return "redirect:/shop/view?n=" + reply.getGdsNum();
	}
	
	
	
}