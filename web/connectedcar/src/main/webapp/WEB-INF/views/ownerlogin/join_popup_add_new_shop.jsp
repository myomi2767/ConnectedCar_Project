<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<style type="text/css">
.address_find_form {
	display: inline-block;
}

.btn btn-default {
	display: inline-block;
}

.join_form {
	margin-bottom: 20px;
	maring-top: 20px;
}

#submitfrompopup {
	margin-bottom: 20px;
	maring-top: 20px;
	margin-left: auto;
	margin-right: auto;
}
</style>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">

</head>
<body>
	<div class="container">
		<div class="col-lg-8 col-lg-offset-2">

			<form id="addshopform" name="shopinfo"
			method="POST">

				<div class="join_form">
					<label for="pwd">정비소 주소 :</label> <input type="text"
						class="form-control" id="shop_address" name="shop_location"
						placeholder="예)서울특별시 강남구 테헤란로26">
				</div>

				<div class="join_form">
					<label for="pwd">정비소 이름 :</label> <input type="text"
						class="form-control" id="shop_name" name="shop_name"
						placeholder="예)멀캠정비소">
				</div>

				<div class="join_form">
					<label for="pwd">정비소 전화번호 :</label> <input type="text"
						class="form-control" id="shop_phone" name="shop_phone"
						placeholder="예)020001111">
				</div>
				<br>

				<input type="button" class="btn btn-default" id="submit"
				onclick="" value="정비소 추가 완료하기"/>
			</form>
			<br>


		</div>


	</div>
	

</body>

<!-- 정비소  -->
<script type="text/javascript">
 $(document).ready(function() {
	$("#submit").on("click", function() {
		addshop();
		alert('정비소가 추가되었습니다. \n이제 추가한 정비소를 검색할 수 있습니다.');
			self.close();
	});
})
function addshop(){
	
	var params = $("#addshopform").serialize();
	$.ajax(
	{
		type:"POST",
		url:"/connectedcar/ownerlogin/addnewshoppopup.do",
		data: params,
		dataType : "text",
		success : function(){
			
		}

	});
	
	
	
	
} 
 


</script>


</html>