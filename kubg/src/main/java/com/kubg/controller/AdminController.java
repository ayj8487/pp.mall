package com.kubg.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ImportResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.kubg.domain.CategoryVO;
import com.kubg.domain.GoodsVO;
import com.kubg.domain.GoodsViewVO;
import com.kubg.domain.MemberVO;
import com.kubg.domain.OrderListVO;
import com.kubg.domain.OrderVO;
import com.kubg.domain.ReplyListVO;
import com.kubg.domain.ReplyVO;
import com.kubg.service.AdminService;
import com.kubg.utils.UploadFileUtils;

import net.sf.json.JSONArray;

@Controller
@RequestMapping("/admin/*")
public class AdminController {

	private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

	@Inject
	AdminService adminService;
	
	@Resource(name = "uploadPath")
	private String uploadPath;
	
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
	
	
	//상품 등록 (파일 이미지포함)	
	/*
	 * 파일용 인풋박스에 등록된 파일의 정보를 가져오고, 
	 * UploadFileUtils.java를 통해 폴더를 생성한 후 
	 * 원본 파일과 썸네일을 저장한 뒤, 이 경로를 데이터 베이스에 전하기 위해 
	 * GoodsVO에 입력(set)함.
	 */
	
	// 상품 등록
	@RequestMapping(value = "/goods/register", method = RequestMethod.POST)
	public String postGoodsRegister(GoodsVO vo, MultipartFile file) throws Exception {
	 
	 String imgUploadPath = uploadPath + File.separator + "imgUpload";  // 이미지를 업로드할 폴더를 설정 = /uploadPath/imgUpload
	 String ymdPath = UploadFileUtils.calcPath(imgUploadPath);  // 위의 폴더를 기준으로 연월일 폴더를 생성
	 String fileName = null;  // 기본 경로와 별개로 작성되는 경로 + 파일이름
	   
	 if(file.getOriginalFilename() != null && file.getOriginalFilename() != "") {
	  // 파일 인풋박스에 첨부된 파일이 없다면(=첨부된 파일이 이름이 없다면)
	  
	  fileName =  UploadFileUtils.fileUpload(imgUploadPath, file.getOriginalFilename(), file.getBytes(), ymdPath);

	  // gdsImg에 원본 파일 경로 + 파일명 저장
	  vo.setGdsImg(File.separator + "imgUpload" + ymdPath + File.separator + fileName);
	  // gdsThumbImg에 썸네일 파일 경로 + 썸네일 파일명 저장
	  vo.setGdsThumbImg(File.separator + "imgUpload" + ymdPath + File.separator + "s" + File.separator + "s_" + fileName);
	  
	 } else {  // 첨부된 파일이 없으면
	  fileName = File.separator + "images" + File.separator + "none.png";
	  // 미리 준비된 none.png파일을 대신 출력함
	  
	  vo.setGdsImg(fileName);
	  vo.setGdsThumbImg(fileName);
	 }
	      
	 adminService.register(vo);
	 
	 return "redirect:/admin/index";
	}
	//상품 목록 리스트 (이지윅 에디터 추가 후 목록리스트(List) GoodsVO => GoodsViewVO)
	@RequestMapping(value = "/goods/list", method = RequestMethod.GET)
	public void getGoodsList(Model model) throws Exception{
		logger.info("get goods list");
		
		List<GoodsViewVO> list = adminService.goodslist();
		
		model.addAttribute("list",list); // 변수 list의 값을 list 세션에 부여
	}
	//상품 조회
	@RequestMapping(value = "/goods/view", method = RequestMethod.GET)
	public void getGoodsview(@RequestParam("n") int gdsNum, Model model) throws Exception {
	 logger.info("get goods view");
	 
	 GoodsViewVO goods = adminService.goodsView(gdsNum);
	 
	 model.addAttribute("goods", goods);
	}
	/* 
	 * 매개변수 @RequestParam("n") int gdsNum 는, URL주소에서 "n"의 값을 찾아서 
	 * int gdsNum에 전달. 목록에서 링크 주소를 /admin/goods/view?n=[상품번호] 
	 * 형식으로 했기 떄문에 n를 찾는것이며, 만약 다른 문자로 했다면 그 문자로 해야함
	 */
	
	//상품 수정 페이지 + 카테고리
	@RequestMapping(value = "/goods/modify", method = RequestMethod.GET)
	public void getGoodsModify(@RequestParam("n")int gdsNum, Model model) throws Exception {
		logger.info("get goods modify");
		
		GoodsViewVO goods = adminService.goodsView(gdsNum);
		model.addAttribute("goods",goods);
		
		List<CategoryVO> category = null;
		category = adminService.category();
		model.addAttribute("category", JSONArray.fromObject(category));
	}
	// 상품 수정
	
	// 상품등록과 거의 같은 코드이지만 새로운 이미지 파일이 등록되었는지 확인하여 
	// 등록되지 않았다면 기존이미지, 새로운 이미지라면 이미지를 삭제한 후 새로운 이미지 등록
	@RequestMapping(value = "/goods/modify", method = RequestMethod.POST)
	public String postGoodsModify(GoodsVO vo, MultipartFile file, HttpServletRequest req) throws Exception {
	 logger.info("post goods modify");

	 // 새로운 파일이 등록되었는지 확인
	 if(file.getOriginalFilename() != null && file.getOriginalFilename() != "") {
	  // 기존 파일을 삭제
	  new File(uploadPath + req.getParameter("gdsImg")).delete();
	  new File(uploadPath + req.getParameter("gdsThumbImg")).delete();
	  
	  // 새로 첨부한 파일을 등록
	  String imgUploadPath = uploadPath + File.separator + "imgUpload";
	  String ymdPath = UploadFileUtils.calcPath(imgUploadPath);
	  String fileName = UploadFileUtils.fileUpload(imgUploadPath, file.getOriginalFilename(), file.getBytes(), ymdPath);
	  
	  vo.setGdsImg(File.separator + "imgUpload" + ymdPath + File.separator + fileName);
	  vo.setGdsThumbImg(File.separator + "imgUpload" + ymdPath + File.separator + "s" + File.separator + "s_" + fileName);
	  
	 } else {  // 새로운 파일이 등록되지 않았다면
	  // 기존 이미지를 그대로 사용
	  vo.setGdsImg(req.getParameter("gdsImg"));
	  vo.setGdsThumbImg(req.getParameter("gdsThumbImg"));
	  
	 }
	 
	 adminService.goodsModify(vo);
	 
	 return "redirect:/admin/index";
	}
	// 상품 삭제
	@RequestMapping(value = "/goods/delete", method = RequestMethod.POST)
	public String postGoodsDelete(@RequestParam("n") int gdsNum) throws Exception {
	 logger.info("post goods delete");

	 adminService.goodsDelete(gdsNum);
	 
	 return "redirect:/admin/index";
	}
	
	// ck 에디터에서 파일 업로드
/*
 * 이미지 첨부 기능 구현과 비슷하지만, 저장하는 폴더가 연/월/일로 나누어지지 않고, 
 * ckUpload 폴더에 모두 들어가게됨. 모든 이미지가 한 폴더에 들어가게될 경우 
 * 파일명이 똑같아 덮어쓰기가 될 가능성이 있으니, UUID를 이용하여 중복되지 않도록 수정. 
 */
	@RequestMapping(value = "/goods/ckUpload", method = RequestMethod.POST)
	public void postCKEditorImgUpload(HttpServletRequest req,
	          HttpServletResponse res,
	          @RequestParam MultipartFile upload) throws Exception {
	 logger.info("post CKEditor img upload");
	 
	 // 랜덤 문자 생성
	 UUID uid = UUID.randomUUID();
	 
	 OutputStream out = null;
	 PrintWriter printWriter = null;
	   
	 // 인코딩
	 res.setCharacterEncoding("utf-8");
	 res.setContentType("text/html;charset=utf-8");
	 
	 try {
	  
	  String fileName = upload.getOriginalFilename();  // 파일 이름 가져오기
	  byte[] bytes = upload.getBytes();
	  
	  // 업로드 경로
	  String ckUploadPath = uploadPath + File.separator + "ckUpload" + File.separator + uid + "_" + fileName;
	  
	  out = new FileOutputStream(new File(ckUploadPath));
	  out.write(bytes);
	  out.flush();  // out에 저장된 데이터를 전송하고 초기화
	  
//	  String callback = req.getParameter("CKEditorFuncNum");
	  printWriter = res.getWriter();
	  String fileUrl = "/ckUpload/" + uid + "_" + fileName;  // 작성화면
	  
//	  ck에디터 업데이트후 업로드(Json형식) +  업로드시 메시지 출력 
	  printWriter.println("{\"filename\" : \""+fileName+"\", \"uploaded\" : 1, \"url\":\""+fileUrl+"\"}");
	  
//		ck에디터 업데이트전 업로드시 메시지 출력	  
//	 	 printWriter.println("<script type='text/javascript'>"
//	     + "window.parent.CKEDITOR.tools.callFunction("
//	     + callback+",'"+ fileUrl+"','이미지를 업로드하였습니다.')"
//	     +"</script>");
	  
	  
	  printWriter.flush();
	  
	 } catch (IOException e) { e.printStackTrace();
	 } finally {
	  try {
	   if(out != null) { out.close(); }
	   if(printWriter != null) { printWriter.close(); }
	  } catch(IOException e) { e.printStackTrace(); }
	 }
	 
	 return; 
	}
	
	// 주문 목록
	@RequestMapping(value = "/shop/orderList", method = RequestMethod.GET)
	public void getOrderList(Model model) throws Exception {
	 logger.info("get order list");
	   
	 List<OrderVO> orderList = adminService.orderList();
	 
	 model.addAttribute("orderList", orderList);
	}

	// 주문 상세 목록
	@RequestMapping(value = "/shop/orderView", method = RequestMethod.GET)
	public void getOrderList(@RequestParam("n") String orderId,
	      OrderVO order, Model model) throws Exception {
	 logger.info("get order view");
	 
	 order.setOrderId(orderId);  
	 List<OrderListVO> orderView = adminService.orderView(order);
	 
	 model.addAttribute("orderView", orderView);
	}
	
	// 주문 상세 목록 - 배송 상태 변경, 반복문으로 주문완료시 수량감소 추가
	@RequestMapping(value = "/shop/orderView", method = RequestMethod.POST)
	public String delivery(OrderVO order) throws Exception {
	 logger.info("post order view");
	   
	 adminService.delivery(order);

	 List<OrderListVO> orderView = adminService.orderView(order); 

	 GoodsVO goods = new GoodsVO();
	   
	 for(OrderListVO i : orderView) {
	  goods.setGdsNum(i.getGdsNum());
	  goods.setGdsStock(i.getCartStock());
	  adminService.changeStock(goods);
	 }
	 
	 return "redirect:/admin/shop/orderView?n=" + order.getOrderId();
	}
	
	// 전체 댓글
	@RequestMapping(value = "/shop/allReply", method = RequestMethod.GET)
	public void getAllReply(Model model) throws Exception {
	 logger.info("get all reply");
	   
	 List<ReplyListVO> reply = adminService.allReply();
	 
	 model.addAttribute("reply", reply);
	}
	
	// 댓글 삭제후 전체 댓글
	@RequestMapping(value = "/shop/allReply", method = RequestMethod.POST)
	public String postAllReply(ReplyVO reply) throws Exception{
	logger.info("post all reply");
		
		adminService.deleteReply(reply.getRepNum());
		
		return "redirect:/admin/shop/allReply";
	}
	
	// 회원 조회
	@RequestMapping(value = "/shop/memberList", method = RequestMethod.GET)
	public void getMemberList(Model model) throws Exception{
		
		List<MemberVO> member = adminService.memberList();
		
		model.addAttribute("member", member);
	}
}
