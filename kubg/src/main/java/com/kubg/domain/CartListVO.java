package com.kubg.domain;

import java.util.Date;

public class CartListVO {

	/*
	 *  create	 table tbl_cart (
	 *  cartNum 	number not null, 
	 *  userId	 varchar2(50) not null, 
	 *  gdsNum	 number not null, 
	 *  cartStock	 number not null, 
	 *  addDate 	date default sysdate, 
	 *  primary 	key(cartNum, userId) 
	 *  );
	 */
	
	private int cartNum;
	private String userId;
	private int gdsNum;
	private int cartStock;
	private Date addDate;
	
	// 카트리스트 : 상품정보와 카트정보를 조인(Join)하여 가져와야 하기때문에 생성
	private int num;
	private String gdsName;
	private int gdsPrice;
	private String gdsThumbImg; 
	
	
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public String getGdsName() {
		return gdsName;
	}
	public void setGdsName(String gdsName) {
		this.gdsName = gdsName;
	}
	public int getGdsPrice() {
		return gdsPrice;
	}
	public void setGdsPrice(int gdsPrice) {
		this.gdsPrice = gdsPrice;
	}
	public String getGdsThumbImg() {
		return gdsThumbImg;
	}
	public void setGdsThumbImg(String gdsThumbImg) {
		this.gdsThumbImg = gdsThumbImg;
	}
	public int getCartNum() {
		return cartNum;
	}
	public void setCartNum(int cartNum) {
		this.cartNum = cartNum;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public int getGdsNum() {
		return gdsNum;
	}
	public void setGdsNum(int gdsNum) {
		this.gdsNum = gdsNum;
	}
	public int getCartStock() {
		return cartStock;
	}
	public void setCartStock(int cartStock) {
		this.cartStock = cartStock;
	}
	public Date getAddDate() {
		return addDate;
	}
	public void setAddDate(Date addDate) {
		this.addDate = addDate;
	}
	
}
