package com.kubg.domain;

public class CategoryVO {

	/*
	 * create table goods_category ( 
	 * cateName varchar2(20) not null, 
	 * cateCode varchar2(30) not null, 
	 * cateCodeRef varchar2(30) null, 

	 * primary key(cateCode), 
	 * foreign key(cateCodeRef) 
	 * references goods_category(cateCode) );
	 */

	private String cateName;
	private String cateCode;
	private String cateCodeRef;

	private int level; //카테고리 상/하위 분류로 인해 추가
	
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public String getCateName() {
		return cateName;
	}
	public void setCateName(String cateName) {
		this.cateName = cateName;
	}
	public String getCateCode() {
		return cateCode;
	}
	public void setCateCode(String cateCode) {
		this.cateCode = cateCode;
	}
	public String getCateCodeRef() {
		return cateCodeRef;
	}
	public void setCateCodeRef(String cateCodeRef) {
		this.cateCodeRef = cateCodeRef;
	}
	
	
}
	
