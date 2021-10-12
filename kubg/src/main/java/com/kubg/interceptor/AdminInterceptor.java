package com.kubg.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.kubg.domain.MemberVO;

public class AdminInterceptor extends HandlerInterceptorAdapter {
//HandlerInterceptorAdapter 를 상속받고 컨트롤러 실행 전에 실행되는 
//preHandel 메서드와, 컨트롤러 실행 후 실행되는 postHandle로 나뉨

//본 프로젝트는 컨트롤러 실행전에 관리자 여부를 확인하기위해 preHandle을 오버라이드함	
	@Override
	 public boolean preHandle(HttpServletRequest req,
	    HttpServletResponse res, Object obj) throws Exception {
	  
	  HttpSession session = req.getSession();
	  MemberVO member = (MemberVO)session.getAttribute("member");	  
// 현재 세션을 불러와 session에 저장한뒤 그중 member라는 명칭의 세션을 불러와
// MemberVO의 형태로 변환뒤, MemberVO형태의 변수인 member에 저장
	  if(member == null) {
		  res.sendRedirect("/member/signin");
		  return false;
	  }
// 만약 member의 값이 없을 때(null)로그인하지 않은 상황이므로,
// 로그인 화면인 /member/signin으로 이동	  
	  
	  if(member == null || member.getVerify() != 9) {
	   res.sendRedirect("/");
	   return false;
// 조건문을 사용해 member에 값이 없는(null) 비 로그인 상태와 member.verify 의 값이 9가 아닐 경우 조건문의 내부가 실행됨 
// 조건문 내부는 가장처음 화면으로 되돌리는 역할을 하며, false를 반환, 
// preHandle은 반환값이 true이면 컨트롤러로 진행, false면 진행이 멈춤
	  }
	  return true;
	 }
// 프로젝트가 실행되는 웹환경과 관련된 설정은 root-context가 아닌 servlet-context에서 설정
	
	}