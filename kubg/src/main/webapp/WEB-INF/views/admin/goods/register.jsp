<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
	<title>Admin</title>
	
	
<!-- 제이쿼리-->
<script src='https://code.jquery.com/jquery-3.3.1.min.js'></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>

<!-- 부트스트랩 -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>

<!-- 이지윅 에디터 -->
<script src="/resources/ckeditor/ckeditor.js"></script>
	
<style>
 body { font-family:'맑은 고딕', verdana; padding:0; margin:0; }
 ul { padding:0; margin:0; list-style:none;  }

 div#root { width:90%; margin:0 auto; }
 
 header#header { font-size:60px; padding:20px 0; }
 header#header h1 a { color:#000; font-weight:bold; }
 
 nav#nav { padding:10px; text-align:right; }
 nav#nav ul li { display:inline-block; margin-left:10px; }

   section#container { padding:20px 0; border-top:2px solid #eee; border-bottom:2px solid #eee; }
 section#container::after { content:""; display:block; clear:both; }
 aside { float:left; width:200px; }
 div#container_box { float:right; width:calc(100% - 200px - 20px); }
 
 aside ul li { text-align:center; margin-bottom:10px; }
 aside ul li a { display:block; width:100%; padding:10px 0;}
 aside ul li a:hover { background:#eee; }
 
 footer#footer { background:#f9f9f9; padding:20px; }
 footer#footer ul li { display:inline-block; margin-right:10px; }
</style>

<style>
.inputArea { margin:10px 0; }
select { width:100px; }
label { display:inline-block; width:70px; padding:5px; }
label[for='gdsDes'] { display:block; }
input { width:150px; }
textarea#gdsDes { width:400px; height:180px; }

.select_img img {margin: 20px 0;}

</style>

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

	<section id="container">
		<aside>
			<%@ include file="../include/aside.jsp" %>
		</aside>
		<div id="container_box">
			<h2>상품 등록</h2>
			
	<form role="form" method="post" autocomplete="off" enctype="multipart/form-data">

	<div class="inputArea"> 
	 <label>1차 분류</label>
	 <select class="category1">
	  <option value="">전체</option>
	 </select>
	
	 <label>2차 분류</label>
	 <select class="category2" name="cateCode">
	  <option value="">전체</option>
	 </select>
	</div>
	
	<div class="inputArea">
	 <label for="gdsName">상품명</label>
	 <input type="text" id="gdsName" name="gdsName" />
	</div>
	
	<div class="inputArea">
	 <label for="gdsPrice">상품가격</label>
	 <input type="text" id="gdsPrice" name="gdsPrice" />
	</div>
	
	<div class="inputArea">
	 <label for="gdsStock">상품수량</label>
	 <input type="text" id="gdsStock" name="gdsStock" />
	</div>
	
	<div class="inputArea">
	 <label for="gdsDes">상품소개</label>
	 <textarea rows="5" cols="50" id="gdsDes" name="gdsDes"></textarea>

<!-- 이지웍 에디터  -->
	<script>
	 var ckeditor_config = {
	   resize_enaleb : false,
	   enterMode : CKEDITOR.ENTER_BR,
	   shiftEnterMode : CKEDITOR.ENTER_P,
	   filebrowserUploadUrl : "/admin/goods/ckUpload"
	 };
	 
	 CKEDITOR.replace("gdsDes", ckeditor_config);
	</script>

	</div>
	
	<div class="inputArea">
		 <label for="gdsImg">이미지</label>
		 <input type="file" id="gdsImg" name="file" />
		 <div class="select_img"><img  src="" /></div>
		 
		 <!-- 파일 선택시 어떤이미지인지 미리 보여주는 스크립트 -->
		 <script>
		  $("#gdsImg").change(function(){
		   if(this.files && this.files[0]) {
		    var reader = new FileReader;
		    reader.onload = function(data) {
		     $(".select_img img").attr("src", data.target.result).width(500);        
		    }
		    reader.readAsDataURL(this.files[0]);
		   }
		  });
		 </script>
		 <!-- 현재 프로젝트의 실제경로를 표시, 경로를기준으로 파일을 저장하고 불러옴-->
		<%=request.getRealPath("/") %>
	</div>
	
	
	<div class="inputArea">
	 <button type="submit" id="register_Btn" class="btn btn-primary">등록</button>
	</div>
	
	</form>

		</div>
	</section>

	<footer id="footer">
		<div id="footer_box">
			<%@ include file="../include/footer.jsp" %>
		</div>
	</footer>
	
</div>

<script>
// 컨트롤러에서 데이터 받기
var jsonData = JSON.parse('${category}');
console.log(jsonData);

var cate1Arr = new Array();
var cate1Obj = new Object();

// 1차 분류 셀렉트 박스에 삽입할 데이터 준비
for(var i = 0; i < jsonData.length; i++) {
 
 if(jsonData[i].level == "1") {
  cate1Obj = new Object();  //초기화
  cate1Obj.cateCode = jsonData[i].cateCode;
  cate1Obj.cateName = jsonData[i].cateName;
  cate1Arr.push(cate1Obj);
 }
}

// 1차 분류 셀렉트 박스에 데이터 삽입
var cate1Select = $("select.category1")

for(var i = 0; i < cate1Arr.length; i++) {
 cate1Select.append("<option value='" + cate1Arr[i].cateCode + "'>"
      + cate1Arr[i].cateName + "</option>"); 
}

// -- 1차 분류
// 컨트롤러에서 모델에 보낸 값인 category를 jsonData에 저장. 
// jsondata에서 level값이 1인 경우에만 cate1Obj에 추가하고, 
// 이 추가한 데이터를 cate1Arr에 추가. 이렇게 추가한 값을 cate1Select에 추가함. 

$(document).on("change", "select.category1", function(){

	 var cate2Arr = new Array();
	 var cate2Obj = new Object();
	 
	 // 2차 분류 셀렉트 박스에 삽입할 데이터 준비
	 for(var i = 0; i < jsonData.length; i++) {
	  
	  if(jsonData[i].level == "2") {
	   cate2Obj = new Object();  //초기화
	   cate2Obj.cateCode = jsonData[i].cateCode;
	   cate2Obj.cateName = jsonData[i].cateName;
	   cate2Obj.cateCodeRef = jsonData[i].cateCodeRef;
	   
	   cate2Arr.push(cate2Obj);
	  }
	 }
	 
	 var cate2Select = $("select.category2");

	 /*
	 for(var i = 0; i < cate2Arr.length; i++) {
	   cate2Select.append("<option value='" + cate2Arr[i].cateCode + "'>"
	        + cate2Arr[i].cateName + "</option>");
	 }
	 */
	 
	 //2차 분류가 중복되어 밑의 코드로 수정 - 1차를선택했을때 2차분류의 내용을 지움
	 cate2Select.children().remove();

	 $("option:selected", this).each(function(){
	  
	  var selectVal = $(this).val();  
	  
	  cate2Select.append("<option value='" + selectVal + "'>전체</option>");
	  //selectVal은 현재선택한 1차 분류의 코드 이므로 2차 분류의 기본선택지인 '전체'에도 같은 코드가 적용
	  
	  for(var i = 0; i < cate2Arr.length; i++) {
	   if(selectVal == cate2Arr[i].cateCodeRef) {
	    cate2Select.append("<option value='" + cate2Arr[i].cateCode + "'>"
	         + cate2Arr[i].cateName + "</option>");
	   }
	  }
	  
	 });
	 
	});

// -- 2차 분류
// 2차분류는 1차 분류의 select.category1 이 변경되었다면 실행되는 함수이다
// 2차 분류이기 때문에 level이 2인 조건을 넣어둠, 2차 분류에만 있는 참조코드명인 cateCodeRef도 있음 나머지는1과 같음

// 변수 selectVal에 현재 선택된(:selected) 1차 분류값인 $(this).val(); 를 저장하고, 셀렉트 박스에 '전체'라고 표시될 데이터를 추가함
// 1차 분류값인 selectVal과 cate2Arr[i].cateCodeRef를 비교하여 동일할 경우, 즉 상위 카테고리에 맞는 하위 카테고리일 경우에만 데이터를 추가함


</script>

</body>
</html>
