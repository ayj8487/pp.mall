<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<h3>카테고리</h3>

<!-- 

/shop/list 는 기본 경로이며, 뒤의 ?c=[번호1]&l=[번호2] 는 구분자이다.

번호1은 cateCode와 같은 숫자를, 번호2는 카테고리의 레벨이며 
카테고리의 레벨이 높을수록(숫자가 클수록) 하위 카테고리이다.
 
 -->

<ul>
	<li><a href="/shop/list?c=100&l=1">여성의류</a>
		<ul class="low">
			<li><a href="/shop/list?c=101&l=2">티셔츠</a></li>			
			<li><a href="/shop/list?c=103&l=2">바지</a></li>			
			<li><a href="/shop/list?c=104&l=2">치마</a></li>			
			<li><a href="/shop/list?c=105&l=2">트레이닝</a></li>			
			<li><a href="/shop/list?c=106&l=2">블라우스</a></li>			
			<li><a href="/shop/list?c=107&l=2">가디건/니트</a></li>			
			<li><a href="/shop/list?c=108&l=2">기타</a></li>			
		</ul>
	</li>

	<li><a href="/shop/list?c=200&l=1">남성의류</a>
		<ul class="low">
			<li><a href="/shop/list?c=201&l=2">티셔츠</a></li>
			<li><a href="/shop/list?c=202&l=2">바지</a></li>
		</ul>
		
	<li><a href="/shop/list?c=300&l=1">시계/쥬얼리</a>
		<ul class="low">
			<li><a href="/shop/list?c=301&l=2">시계</a></li>
			<li><a href="/shop/list?c=302&l=2">쥬얼리</a></li>
		</ul>
	</li>

	<li><a href="/shop/list?c=400&l=1">패션/액세서리</a>
		<ul class="low">
			<li><a href="/shop/list?c=401&l=2">지갑/벨트/모자</a></li>
			<li><a href="/shop/list?c=402&l=2">기타액세서리</a></li>
		</ul>
	</li>

</ul>