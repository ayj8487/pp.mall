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
import org.springframework.web.bind.annotation.ResponseBody;

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
	 
	 // 1. 댓글조회 추가 후 밑의 코드 추가 , 2. Ajax사용시 주석
//	 List<ReplyListVO> reply = service.replyList(gdsNum);
//	 model.addAttribute("reply", reply);
	}
	
	// 상품조회 - 소감(댓글) 작성 -- Ajax 미사용시 
	// HttpSession 을 이용해 member세션에 저장되어있는 유저 아이디를 가져옴
//	@RequestMapping(value = "/view", method = RequestMethod.POST)
//	public String registReply(ReplyVO reply, HttpSession session) throws Exception{
//	 logger.info("regist reply");
//		 
//	 MemberVO member = (MemberVO)session.getAttribute("member");
//	 reply.setUserId(member.getUserId());
//	 
//	 service.registReply(reply);
//		
//	 return "redirect:/shop/view?n=" + reply.getGdsNum();
//	}
	
	// 상품조회 - 소감(댓글) 목록(리스트)  -- Ajax사용시 
	@ResponseBody
	@RequestMapping(value = "/view/replyList", method = RequestMethod.GET)
	public List<ReplyListVO> getReplyList(@RequestParam("n") int gdsNum) throws Exception {
	 logger.info("get reply list");
	   
	 List<ReplyListVO> reply = service.replyList(gdsNum);
	 
	 return reply;
	} 
	// 상품 소감(댓글) 작성 -- Ajax사용시
	@ResponseBody
	@RequestMapping(value = "/view/registReply", method = RequestMethod.POST)
	public void registReply(ReplyVO reply,  HttpSession session) throws Exception {
	 logger.info("regist reply");
	 
	 MemberVO member = (MemberVO)session.getAttribute("member");
	 reply.setUserId(member.getUserId());
	 
	 service.registReply(reply);
	} 
	// 상품 소감(댓글) 삭제
	
//	1. 현재 세션을 가져와 변수 member에 저장, 
//	아이디 체크용 쿼리의 결과를 가져와서 변수 userId에 저장
//	2. 변수 member에서 userId부분만 추출한 값과, 변수 userId의 값을 비교후 
//	비교식은 이퀄(=) 부호 2개가 아닌 이퀄 메서드 .equals() 를 사용.
//	3. 이렇게 현재 로그인한 사용자의 아이디와 소감을 작성한 사용자의 아이디를 비교했을 때, 
//	결과가 참(true)이라면 삭제 작업을 진행한 뒤 변수 result에 1을 저장하고, 거짓(false)이라면 아무 작업을 하지 않고 끝냄
//	4. 변수 result의 결과가 0이라면 아이디가 다르기에 삭제 작업이 진행되지 않으며, 
//	변수 result의 결과가 1이라면 아이디가 같아서 삭제 작업이 진행.
	
	@ResponseBody
	@RequestMapping(value = "/view/deleteReply", method = RequestMethod.POST)
	public int getReplyList(ReplyVO reply,  HttpSession session) throws Exception {
	 logger.info("post delete reply");

	 int result = 0;
	 
	 MemberVO member = (MemberVO)session.getAttribute("member");
	 String userId = service.idCheck(reply.getRepNum());
	   
	 if(member.getUserId().equals(userId)) {
	  
	  reply.setUserId(member.getUserId());
	  service.deleteReply(reply);
	  
	  result = 1;
	 }
	 
	 return result; 
	}
	
	// 상품 소감(댓글) 수정 ,삭제 메서드와 크게다르지않음
	
	// 댓글을 작성한 사용자의 아이디와 현재 로그인한 아이디를 비교한뒤
	// 같다면 수정작업을하고 result에 1을 저장, 같지 않다면 종료
	@ResponseBody
	@RequestMapping(value = "/view/modifyReply", method = RequestMethod.POST)
	public int modifyReply(ReplyVO reply, HttpSession session) throws Exception {
	 logger.info("modify reply");
	 
	 int result = 0;
	 
	 MemberVO member = (MemberVO)session.getAttribute("member");
	 String userId = service.idCheck(reply.getRepNum());
	 
	 if(member.getUserId().equals(userId)) {
	  
	  reply.setUserId(member.getUserId());
	  service.modifyReply(reply);
	  result = 1;
	 }
	 
	 return result;
	} 
	
}