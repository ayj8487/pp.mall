package com.kubg.controller;

import java.text.DecimalFormat;
import java.util.Calendar;
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

import com.kubg.domain.CartListVO;
import com.kubg.domain.CartVO;
import com.kubg.domain.GoodsViewVO;
import com.kubg.domain.MemberVO;
import com.kubg.domain.OrderDetailVO;
import com.kubg.domain.OrderListVO;
import com.kubg.domain.OrderVO;
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
	
	// 카트 담기 (수정 전)
//	@ResponseBody
//	@RequestMapping(value = "/view/addCart", method = RequestMethod.POST)
//	public void addCart(CartVO cart, HttpSession session) throws Exception {
//	 
//	 MemberVO member = (MemberVO)session.getAttribute("member");
//	 cart.setUserId(member.getUserId());
//
//	 service.addCart(cart);	 
//	}
	
	// 카트 담기 (수정) -- ajax사용
	@ResponseBody
	@RequestMapping(value = "/view/addCart", method = RequestMethod.POST)
	public int addCart(CartVO cart, HttpSession session) throws Exception {
	 
	 int result = 0;
	 
	 MemberVO member = (MemberVO)session.getAttribute("member");
	 
	 if(member != null) {
	  cart.setUserId(member.getUserId());
	  service.addCart(cart);
	  result = 1;
	 }
	 
	 return result;
	}
	
	// 카트 리스트
	@RequestMapping(value = "/cartList", method = RequestMethod.GET)
	public void getCartList(HttpSession session, Model model) throws Exception {
	 logger.info("get cart list");
	 
	 MemberVO member = (MemberVO)session.getAttribute("member");
	 String userId = member.getUserId();
	 
	 List<CartListVO> cartList = service.cartList(userId);
	 
	 model.addAttribute("cartList", cartList);
	}
	
	// 카트 삭제
	
	//Ajax에서 전송받은 배열 chbox를 리스트형 변수 chArr로 받은뒤, for문을 이용해 chArr이 가지고있는 
	//값의 갯수만큼 반복하며 result 를 이용해 0또는 1을 반납(로그인이 되어있는지 여부확인)
	//에이젝스의 error를 이용해 구분할수 있지만, 컨트롤러보다 더 깊은 service와 dao를 거쳐 쿼리문이 실행되는 걸 막을 수 있음.
	@ResponseBody
	@RequestMapping(value = "/deleteCart", method = RequestMethod.POST)
	public int deleteCart(HttpSession session,
	     @RequestParam(value = "chbox[]") List<String> chArr, CartVO cart) throws Exception {
	 logger.info("delete cart");
	 
	 MemberVO member = (MemberVO)session.getAttribute("member");
	 String userId = member.getUserId();
	 
	 int result = 0;
	 int cartNum = 0;
	 
	 
	 if(member != null) {
	  cart.setUserId(userId);
	  
	  for(String i : chArr) {   
	   cartNum = Integer.parseInt(i);
	   cart.setCartNum(cartNum);
	   service.deleteCart(cart);
	  }   
	  result = 1;
	 }  
	 return result;  
	}
	
	// 주문 하기
	
	// 달력 메서드(Calendar)이용하여 연/월/일을 추출하고 
	// 6자리의 랜덤 숫자로 만들어진 subNum을 더하여 
	// 날짜_랜덤숫자 로 이루어진, 최대한 중복되지 않는 고유한 문자열을 생성. -- UUID로 대체 사용해도 무관
	
	@RequestMapping(value = "/cartList", method = RequestMethod.POST)
	public String order(HttpSession session, OrderVO order, OrderDetailVO orderDetail) throws Exception {
	 logger.info("order");
	 
	 MemberVO member = (MemberVO)session.getAttribute("member");  
	 String userId = member.getUserId();
	 
	 Calendar cal = Calendar.getInstance();
	 int year = cal.get(Calendar.YEAR);
	 String ym = year + new DecimalFormat("00").format(cal.get(Calendar.MONTH) + 1);
	 String ymd = ym +  new DecimalFormat("00").format(cal.get(Calendar.DATE));
	 String subNum = "";
	 
	 for(int i = 1; i <= 6; i ++) {
	  subNum += (int)(Math.random() * 10);
	 }
	 
	 String orderId = ymd + "_" + subNum;
	 
	 order.setOrderId(orderId);
	 order.setUserId(userId);
	  
	 service.orderInfo(order);
	 
	 orderDetail.setOrderId(orderId);   
	 service.orderInfo_Details(orderDetail);
	 
	 // 주문 완료후 카트 비우기 
	 // 주문 테이블과 주문상세 테이블을 생성뒤 카트테이블의 데이터를 모두 삭제
	 service.cartAllDelete(userId);
	 
	 return "redirect:/shop/orderList";  
	}
	
	// 주문 목록 리스트
	@RequestMapping(value = "/orderList", method = RequestMethod.GET)
	public void getOrderList(HttpSession session, OrderVO order, Model model) throws Exception {
	 logger.info("get order list");
	 
	 MemberVO member = (MemberVO)session.getAttribute("member");
	 String userId = member.getUserId();
	 
	 order.setUserId(userId);
	 
	 List<OrderVO> orderList = service.orderList(order);
	 
	 model.addAttribute("orderList", orderList);
	}
	
	// 주문 목록 리스트(상세)
	// URL에서 'n'에 지정된 값을 받아와서 orderId로 사용
	@RequestMapping(value = "/orderView", method = RequestMethod.GET)
	public void getOrderList(HttpSession session,
	      @RequestParam("n") String orderId,
	      OrderVO order, Model model) throws Exception {
	 logger.info("get order view");
	 
	 MemberVO member = (MemberVO)session.getAttribute("member");
	 String userId = member.getUserId();
	 
	 order.setUserId(userId);
	 order.setOrderId(orderId);
	 
	 List<OrderListVO> orderView = service.orderView(order);
	 
	 model.addAttribute("orderView", orderView);
	}
	
	
}