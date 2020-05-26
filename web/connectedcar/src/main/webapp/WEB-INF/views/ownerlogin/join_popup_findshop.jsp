<%@page import="connected.car.owner.ShopinfoVO"%>
<%@page import="java.util.ArrayList"%>
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


	<%
		ArrayList<ShopinfoVO> shoplist = (ArrayList<ShopinfoVO>) request.getAttribute("shoplist");
	%>
	<div class="container">
		<div class="col-lg-8 col-lg-offset-2">
			<h4>내 정비소 찾기</h4>
			<form action="/connectedcar/ownerlogin/searchshop.do" name="findshop"
				method="POST" onsubmit="">

				<div class="btn-group btn-group-toggle" data-toggle="buttons"
					style="">

					<div class="address_find_form">
						<input type="text" class="form-control" id="address_do"
							name="address_do" placeholder="예)경기도">
					</div>

					<div class="address_find_form">
						<input type="text" class="form-control" id="address_si"
							name="address_si" placeholder="예)고양시">
					</div>

					<div class="address_find_form">
						<input type="text" class="form-control" id="address_gu"
							name="address_gu" placeholder="예)일산동구">
					</div>

				</div>

				<!-- 검색 버튼을 누르면, 하단 영역에 주소 목록이 떠야 한다. -->
				<button type="submit" class="btn btn-default" id="search">검색</button>
			</form>

			<br>

			<div style="width: 100%; height: 200px; overflow: auto">


				<table class="table table-striped">
					<thead class="thead-dark">
						<tr>
							<th>정비소 코드</th>
							<th>정비소 이름</th>
							<th>주소</th>
							<th>전화번호</th>
							<th>선택</th>

						</tr>
					</thead>


					<!-- href를 달아서, 만약 주소를 누르면 텍스트가 적히도록 해야 합니다.  -->


					<%
						if (shoplist != null) {

							for (int i = 0; i < shoplist.size(); i++) {
								ShopinfoVO row = shoplist.get(i);
					%>



					<tr>
						<td><%=row.getShop_id()%></td>
						<td><%=row.getShop_name()%></td>
						<td><%=row.getShop_location()%></td>
						<td><%=row.getShop_phone()%></td>
						<td id="selec"><button>선택</button></td>
					</tr>

					<%
						}
						
						}
						%>

				</table>
				
			</div>


			<!-- 위에서 선택한 정보가, 밑에 적힌다.  -->
			<!-- 선택한 정보가 없을 경우, 직접 적어줘야 한다. -->

			<div>
				<br>
				<span>검색 목록에 없다면? ☞ </span> <span id="add_new_shop_btn" class="btn btn-default"
					onclick="addShopPop()">정비소 추가하기</span>



				<form name="sendtxt">
					<br> <br>
					<h4 style="font-weight: bold">선택된 정비소 정보</h4>
					<div class="join_form">
						<label for="pwd">정비소코드 :</label> <input type="text"
							class="form-control" id="shop_id" name="shop_id"
							readonly>
					</div>
					
					
					<div class="join_form">
						<label for="pwd">정비소이름 :</label> <input type="text"
							class="form-control" id="shop_name" name="shop_name"
							readonly>
					</div>

					<div class="join_form">
						<label for="pwd">정비소 위치 :</label> <input type="text"
							class="form-control" id="shop_location" name="shop_location" readonly>
					</div>

					<div class="join_form">
						<label for="pwd">정비소 전화번호 :</label> <input type="text"
							class="form-control" id="shop_phone" name="shop_phone" readonly>
					</div>
					<br>
				</form>
				<!-- 확인 버튼 누르면 팝업 닫히면 팝업 꺼지면서 회원가입 창에 정보가 적힌다.  -->
				
				<button type="confirm" class="btn btn-default" id="submitfrompopup"
					onclick="sendTxt()" style="margin-left: auto; margin-right: auto; text-align: center;">확인</button>

				<br>


			</div>


		</div>
	</div>

</body>

<!-- 정비소  -->
<script type="text/javascript">
	function sendTxt() {
		window.opener.document.userInfo.shop_id.value = document.sendtxt.shop_id.value;
		window.opener.document.userInfo.shop_location.value = document.sendtxt.shop_location.value;
		window.opener.document.userInfo.shop_name.value = document.sendtxt.shop_name.value;
		window.opener.document.userInfo.shop_phone.value = document.sendtxt.shop_phone.value;

		self.close();
	}

	//정비소 추가하기 팝업 열어주는 함수.
	function addShopPop() {
		window.open('addnewshoppopup.do', '_blank',
				'width=1000px,height=1000px');
	}
	
	$(document).ready(function(){
		$("#selec button").on("click", function() {
			var currentRow=$(this).closest("tr");
			var id = currentRow.find('td:eq(0)').text();
			var name = currentRow.find('td:eq(1)').text();
			var location = currentRow.find('td:eq(2)').text();
			var tel = currentRow.find('td:eq(3)').text();
			
			$("#shop_id").val(id);
			$("#shop_name").val(name);
			$("#shop_location").val(location);
			$("#shop_phone").val(tel);
		});
	});
</script>


</html>