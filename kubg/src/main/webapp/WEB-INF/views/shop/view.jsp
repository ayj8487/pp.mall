<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<html>
<head>
	<title>Home</title>
<link rel="shortcut icon" href="#">

<!-- 제이쿼리-->
<script src='https://code.jquery.com/jquery-3.3.1.min.js'></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
	
<style>
 body { margin:0; padding:0; font-family:'맑은 고딕', verdana; }
 a { color:#05f; text-decoration:none; }
 a:hover { text-decoration:underline; }
 
 h1, h2, h3, h4, h5, h6 { margin:0; padding:0; }
 ul, lo, li { margin:0; padding:0; list-style:none; }

 /* ---------- */
 
 div#root { width:900px; margin:0 auto; }
 header#header { }
 nav#nav { }
 section#container { }
  section#content { float:right; width:700px; }
  aside#aside { float:left; width:180px; }
  section#container::after { content:""; display:block; clear:both; } 
 footer#footer { background:#eee; padding:20px; }
 
 /* ---------- */
 
 header#header div#header_box { text-align:center; padding:30px 0; }
 header#header div#header_box h1 { font-size:50px; }
 header#header div#header_box h1 a { color:#000; }
 
 nav#nav div#nav_box { font-size:14px; padding:10px; text-align:right; }
 nav#nav div#nav_box li { display:inline-block; margin:0 10px; }
 nav#nav div#nav_box li a { color:#333; }
 
 section#container { }
 
 aside#aside h3 { font-size:22px; margin-bottom:20px; text-align:center; }
 aside#aside li { font-size:16px; text-align:center; }
 aside#aside li a { color:#000; display:block; padding:10px 0; }
 aside#aside li a:hover { text-decoration:none; background:#eee; }

/* 사이드바 드롭다운메뉴 */ 
aside#aside li { position:relative; }
aside#aside li:hover { background:#eee; }   
aside#aside li > ul.low { display:none; position:absolute; top:0; left:180px;  }
aside#aside li:hover > ul.low { display:block; }
aside#aside li:hover > ul.low li a { background:#eee; border:1px solid #eee; }
aside#aside li:hover > ul.low li a:hover { background:#fff;}
aside#aside li > ul.low li { width:180px; }
/* ------ */
 
 footer#footer { margin-top:100px; border-radius:50px 50px 0 0; }
 footer#footer div#footer_box { padding:0 20px; } 
</style>

<style>
/* 상품조회 */

 div.goods div.goodsImg { float:left; width:350px; }
 div.goods div.goodsImg img { width:350px; height:auto; }
 
 div.goods div.goodsInfo { float:right; width:330px; font-size:22px; }
 div.goods div.goodsInfo p { margin:0 0 20px 0; }
 div.goods div.goodsInfo p span { display:inline-block; width:100px; margin-right:15px; }
 
 div.goods div.goodsInfo p.cartStock input { font-size:22px; width:50px; padding:5px; margin:0; border:1px solid #eee; }
 div.goods div.goodsInfo p.cartStock button { font-size:26px; border:none; background:none; }
 div.goods div.goodsInfo p.addToCart { text-align:right; }
 div.goods div.gdsDes { font-size:18px; clear:both; padding-top:30px; }
</style>	

<style>
/* 댓글조회 */

 section.replyForm { padding:30px 0; }
 section.replyForm div.input_area { margin:10px 0; }
 section.replyForm textarea { font-size:16px; font-family:'맑은 고딕', verdana; padding:10px; width:500px;; height:150px; }
 section.replyForm button { font-size:20px; padding:5px 10px; margin:10px 0; background:#fff; border:1px solid #ccc; }
 
 section.replyList { padding:30px 0; }
 section.replyList ol { padding:0; margin:0; }
 section.replyList ol li { padding:10px 0; border-bottom:2px solid #eee; }
 section.replyList div.userInfo { }
 section.replyList div.userInfo .userName { font-size:24px; font-weight:bold; }
 section.replyList div.userInfo .date { color:#999; display:inline-block; margin-left:10px; }
 section.replyList div.replyContent { padding:10px; margin:20px 0; }

section.replyList div.replyFooter button { font-size:14px; border: 1px solid #999; background:none; margin-right:10px; }
</style>

<style>
/* 상품소감(댓글) 수정 모달창 : display:none으로 화면에서 숨김*/
 div.replyModal { position:relative; z-index:1; display:none;}
 div.modalBackground { position:fixed; top:0; left:0; width:100%; height:100%; background:rgba(0, 0, 0, 0.8); z-index:-1; }
 div.modalContent { position:fixed; top:20%; left:calc(50% - 250px); width:500px; height:250px; padding:20px 10px; background:#fff; border:2px solid #666;}
 div.modalContent textarea { font-size:16px; font-family:'맑은 고딕', verdana; padding:10px; width:500px; height:200px; }
 div.modalContent button { font-size:20px; padding:5px 10px; margin:10px 0; background:#fff; border:1px solid #ccc; }
 div.modalContent button.modal_cancel { margin-left:20px; }
</style>

<!--  상품 댓글목록(리스트) ajax활성 --> 
  <script> 
  function replyList() {
	
	  var gdsNum = ${view.gdsNum};
	  $.getJSON("/shop/view/replyList" + "?n=" + gdsNum, function(data){
	   var str = "";
	   
	   $(data).each(function(){
	    
	    console.log(data);
	    
	    //날짜 데이터를 보기 쉽게 변환
	    var repDate = new Date(this.repDate);
	    repDate = repDate.toLocaleDateString("ko-US")
	    
	    //HTML 코드 조합
	    str += "<li data-repNum='" + this.repNum + "'>"
	      + "<div class='userInfo'>"
	      + "<span class='userName'>" + this.userName + "</span>"
	      + "<span class='date'>" + repDate + "</span>"
	      + "</div>"
	      + "<div class='replyContent'>" + this.repCon + "</div>"
	      
	      + "<c:if test='${member != null}'>"
	      
	      + "<div class='replyFooter'>"
	      + "<button type='button' class='modify' data-repNum='" + this.repNum + "'>수정</button>"
	      + "<button type='button' class='delete' data-repNum='" + this.repNum + "'>삭제</button>"
	      + "</div>"
	      
	      + "</c:if>"
	      
	      + "</li>";           
	   });
	   
	   $("section.replyList ol").html(str);
	  });
  } 
  
	</script>
 <!-- 
	getJson() 은 비동기식으로 제이슨(Json) 데이터를 가져오는 메서드
	1. http:///shop/view/replyList" + "?n=" + gdsNum 주소로 컨트롤러에 접속하여 데이터를 가져오고, 
	2. 그 데이터를 이용해 HTML코드를 조립하여 <ol> 태그에 추가하는 방식이며,
	3. 테이블에 저장된 날짜 데이터와 컨트롤러에서 뷰로 보낼때의 날짜 데이터 형식이 다르기 때문에, 컨트롤러에서 toLocaleDateString() 를 이용해 1차적으로 데이터를 가공함.
	4. function replyList() 재사용을 위해 함수로 묶고 script를 head로 옮김			   
  -->	

	
</head>
<body>
<div id="root">
	<header id="header">
		<div id="header_box">
			<%@ include file="../include/header.jsp" %>
		</div>
	</header>

	<nav id="nav">
		<div id="nav_box">
			<%@ include file="../include/nav.jsp" %>
		</div>
	</nav>

	<aside id="aside">
		<%@ include file="../include/aside.jsp" %>
	</aside>
	
	<section id="container">
		<div id="container_box">
			<section id ="content">
			
			<form role="form" method="post">
				 <input type="hidden" name="gdsNum" value="${view.gdsNum}" />
			</form>
				
				<div class="goods">
				 <div class="goodsImg">
				  <img src="${view.gdsImg}">
				 </div>
				 
				 <div class="goodsInfo">
				  <p class="gdsName"><span>상품명</span>${view.gdsName}</p>
				  
				  <p class="cateName"><span>카테고리</span>${view.cateName}</p>
				  
				  <p class="gdsPrice">
				   <span>가격 </span><fmt:formatNumber pattern="###,###,###" value="${view.gdsPrice}" /> 원
				  </p>
				  
				  <p class="gdsStock">
				   <span>재고 </span><fmt:formatNumber pattern="###,###,###" value="${view.gdsStock}" /> EA
				  </p>
				  
			    <!-- 수량이 0이 아닐시 수량 증감 버튼과 장바구니담기 버튼 보이기 -->
				  <c:if test="${view.gdsStock != 0}">
				  
				<p class="cartStock">
				<!-- 수량직접입력시 실수를 막기위해 제이쿼리 선택자를 이용, 재고량을 초과할수 없게끔, 최소량은 1 -->
				 <span>구입 수량</span>
				 <button type="button" class="plus">+</button>
				 <input type="number" class="numBox" min="1" max="${view.gdsStock}" value="1" readonly="readonly"/>
				 <button type="button" class="minus">-</button>
				 
				 <!-- 수량 버튼 -->
				 <script>
				  $(".plus").click(function(){
				   var num = $(".numBox").val();
				   var plusNum = Number(num) + 1;
				   
				   if(plusNum >= ${view.gdsStock}) {
				    $(".numBox").val(num);
				   } else {
				    $(".numBox").val(plusNum);          
				   }
				  });
				  
				  $(".minus").click(function(){
				   var num = $(".numBox").val();
				   var minusNum = Number(num) - 1;
				   
				   if(minusNum <= 0) {
				    $(".numBox").val(num);
				   } else {
				    $(".numBox").val(minusNum);          
				   }
				  });
				 </script>
				 
				</p>
				  <!-- 카드(장바구니)담기 -->
					<p class="addToCart">
					 <button type="button" class="addCart_btn">장바구니</button>
					 
				<script>
					 $(".addCart_btn").click(function(){
					  var gdsNum = $("#gdsNum").val();
					  var cartStock = $(".numBox").val();
					           
					  var data = {
					    gdsNum : gdsNum,
					    cartStock : cartStock
					    };
					  
					  $.ajax({
					   url : "/shop/view/addCart",
					   type : "post",
					   data : data,
					   success : function(result){
					    
					    if(result == 1) {
					     alert("상품을 장바구니에 담았습니다.");
					     $(".numBox").val("1");
					    } else {
					     alert("회원가입 후 이용 가능합니다.")
					     $(".numBox").val("1");
					    }
					   },
					   error : function(){
					    alert("세션이 만료되었습니다. 로그인 후 이용가능합니다.");
					   }
					  });
					 });
				</script>
					</p>
				
				</c:if>
					
				<!-- 주문상태 배송완료 시 수량이 감소하며 수량이 0일때 품절 표시 -->
				<c:if test="${view.gdsStock == 0}">
					<p style="color : red">품절된 상품 입니다.</p>
				</c:if>
				 </div>
				 
				 <div class="gdsDes">${view.gdsDes}</div>
				</div>
				
				<div id="reply">
				
				<!-- 조건문으로 로그인에따른 출력 / 로그인 안할시-->
				<c:if test ="${member == null }">
					<p>후기를 남기시려면<a href="/member/signin"> 로그인</a> 해주세요.</p>
				</c:if>
				
				<!-- 로그인시 -->
				<c:if test="${member != null}">
				 <section class="replyForm">
				  <form role="form" method="post" autocomplete="off">
				   
				   <input type ="hidden" name="gdsNum" id="gdsNum" value="${view.gdsNum}">
				   
				   <div class="input_area">
				    <textarea name="repCon" id="repCon"></textarea>
				   </div>
				   
				   <div class="input_area">
				    <button type="button" id="reply_btn">후기 남기기</button>
				   
				   <!-- 댓글작성 -->
				<script>
					 $("#reply_btn").click(function(){
					  
					  var formObj = $(".replyForm form[role='form']");
					  var gdsNum = $("#gdsNum").val();
					  var repCon = $("#repCon").val()
					  
					  var data = {
					    gdsNum : gdsNum,
					    repCon : repCon
					    };
					  
					  $.ajax({
					   url : "/shop/view/registReply",
					   type : "post",
					   data : data,
					   success : function(){
						   replyList();
					    $("#repCon").val("");
					   }
					  });
					 });
				</script>

				   </div>
				   
				  </form>
				 </section>
				 </c:if>
			   <!--  Ajax로 댓글작성
		  1. gdsNum,repCon 변수를 선언, 인풋박스와 텍스트에어리어의 값을 저장하며 제이슨형의 변수 data를 생성  
		  2. 변수 data 즉 제이슨형태는 (키:키값, 키:키값, 키:키값, 키:키값 ...)형태로 되어있다
		  3. 그러므로 ( gdsNum : gdsNum, repCon : repCon ) 는 글씨는 똑같지만 앞에있는것이 키, 뒤에있는것이 값임
		   
		   이후 에이젝스를 이용하는데, 여기서 사용한 에이젝스는 url, type, data, success로 구성되어있다. 
		   url은 데이터가 전송될 주소, 
		   type는 타입(get, post), 
		   data는 전송될 데이터, 
		   success는 데이터 전송이 성공되었을 경우 실행할 함수부이다.

			이외에도 데이터 전송이 실패할 경우 실행되는 error, 
			성공 여부 상관없이 실행되는 complete등이 있다.

			success에서는 위에서 작성한 replyList() 함수를 호출하여, 
			소감(댓글)이 작성 완료되면 소감 목록을 다시 읽어서 출력한다.
		   -->
				 
					<section class="replyList">
					 <ol>
					<!-- ajax 사용을 위해 주석, replyList()로 대체 후 head로 올림
					 <c:forEach items="${reply}" var="reply">
					
					  <li>
					      <div class="userInfo">
					       <span class="userName">${reply.userName}</span>
					       <span class="date"><fmt:formatDate value="${reply.repDate}" pattern="yyyy-MM-dd" /></span>
					      </div>
					      <div class="replyContent">${reply.repCon}</div>
					    </li>
					   </c:forEach>
					 -->			   
					  </ol>    
					  
					<script>
					replyList();
					</script>
					
					<!-- 댓글 수정 모달 창, 제이쿼리내장 페이드(fade) 이용시 기존코드 주석처리 : 300 => 0.3초 -->
				
					<!-- 
					변수 repNum에는 버튼에 부여된 소감 번호(data-repNum)를 저장, 
					변수 repCon에는 버튼의 부모(parent)의 
					부모에서 자식의 클래스가 replyContent인 요소의 값을 저장.
			
					M 버튼의 부모는 <div class="replyFooter">, 
					<div class="replyFooter"> 의 부모는 <li data-repNum="11">, 
					<li data-repNum="11"> 의 자식중 클래스가 replyContent인건 '소감 내용'.
					repNum에 저장된 소감 번호는 모달창의 버튼에 부여하고, 
					repCon에 저장된 소감 내용은 모달창의 텍스트에어리어에 부여.
					 -->

					<script>
					$(document).on("click", ".modify", function(){
					//	 $(".replyModal").attr("style", "display:block;");
						 $(".replyModal").fadeIn(300);
	
				//수정 버튼을 눌러서 나오는 모달창의 텍스트에어리어에는 선택했던 버튼에 해당되는 소감 내용이 들어가며, 
				//수정 버튼에는 선택했던 버튼의 상품 번호가 부여시킴. 

						 var repNum = $(this).attr("data-repNum");
						 var repCon = $(this).parent().parent().children(".replyContent").text();
						 
						 $(".modal_repCon").val(repCon);
						 $(".modal_modify_btn").attr("data-repNum", repNum);
						
					});
					</script>
					
					<!-- 댓글 삭제 -->
					
					<!-- 
					컨트롤러부터 받아온 result의 값이 1이라면 삭제 작업이 진행되었으니 소감 목록을 다시 불러오고, 
					1이 아니라면 삭제 작업이 진행되지 않은것이며, 
					그렇다는건 로그인한 사용자와 소감을 작성한 사용자가 다른것이므로 메시지를 띄움.
					 -->
					<script>
					 $(document).on("click", ".delete", function(){
					  
						 var deletConfirm = confirm("댓글을 삭제하시겠습니까?");
						 
						 if(deletConfirm) {
						 
					  var data = {repNum : $(this).attr("data-repNum")};
					   
					  $.ajax({
					   url : "/shop/view/deleteReply",
					   type : "post",
					   data : data,
					   success : function(result){
						   
						   if(result == 1) {
						    replyList();
						   } else {
						    alert("작성자가 아닙니다.");     
						   }
						  },
						  error : function(){
							  alert("로그인후 이용 바랍니다.")
							 }
					  });
					  
						 }
					 });
					</script>
					
					</section>

				</div>
			</section>
		</div>
	</section>
	

	<footer id="footer">
		<div id="footer_box">
			<%@ include file="../include/footer.jsp" %>
		</div>
	</footer>
	
</div>
	
			<!-- 상품소감(댓글) 수정 모달 창 display:none으로 숨김 -->
			<div class="replyModal">
			
			 <div class="modalContent">
			  
			  <div>
			   <textarea class="modal_repCon" name="modal_repCon"></textarea>
			  </div>
			  
			  <div>
			   <button type="button" class="modal_modify_btn">수정</button>
			   <button type="button" class="modal_cancel">취소</button>
			  </div>
			  
			 </div>
			
			 <div class="modalBackground"></div>
			 
			</div>

<!-- 댓글수정 모달 창 닫기, 페이드 이용시 기존코드 주석 : 300 => 0.3초 -->
<script>
$(".modal_cancel").click(function(){
// $(".replyModal").attr("style", "display:none;");
 $(".replyModal").fadeOut(300);

});
</script>


	<!-- 댓글 수정 (댓글 삭제와 같은맥락의 코드이며
	
	1. 데이터를 Json형태로 저장후 
	2. Ajax를 이용해 컨트롤러로 값을 전달
	3. 컨트롤러에서 되돌아오는 result 1이면 성공, 0이면 실패 
	4. 수정 스크립트는 모달창 HTML코드 밑에 위치해 있어야함 
	)-->
	<script>
	$(".modal_modify_btn").click(function(){
		 var modifyConfirm = confirm("댓글을 수정하시겠습니까?");
		 
		 if(modifyConfirm) {
		  var data = {
		     repNum : $(this).attr("data-repNum"),
		     repCon : $(".modal_repCon").val()
		    };  // ReplyVO 형태로 데이터 생성
		  
		  $.ajax({
		   url : "/shop/view/modifyReply",
		   type : "post",
		   data : data,
		   success : function(result){
		    
		   //result의 값에 따라 동작 
		    if(result == 1) {
		     replyList();	// 리스트 새로고침
		     $(".replyModal").fadeOut(200);
		    } else {
		     alert("작성자가 아닙니다.");       
		    }
		   },
		   error : function(){
			   // 로그인하지 않아서 에러가 발생한 경우 
		    alert("로그인후 이용 바랍니다.")
		   }
		  });
		 }
		 
		});
	</script>

</body>
</html>
