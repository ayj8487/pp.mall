package com.kubg.controller;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.kubg.domain.GoodsVO;
import com.kubg.service.ShopService;


@Controller
public class HomeController {
	
	 private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	 @Inject
	ShopService service;
	 
	// 전체 상품 리스트
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) throws Exception {
		 logger.info("get home");
		
		 List<GoodsVO> main = service.main();
			
		 	model.addAttribute("main", main);
		 
		return "home";
	}
	
}
