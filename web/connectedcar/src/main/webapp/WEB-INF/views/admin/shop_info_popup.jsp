<%@page import="connected.car.owner.ShopinfoVO"%>
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

</head>
<body>

	<%
		ShopinfoVO shopinfo = (ShopinfoVO) session.getAttribute("shopinfo");
	%>
	<div class="container">
		<div class="col-lg-8 col-lg-offset-2">
			<h4>정비소 자세히 보기</h4>
			

			<br>


			<table class="table table-striped">
				<thead class="thead-dark">
					<tr>
						<th>정비소 코드</th>
						<th>정비소 이름</th>
						<th>정비소 위치</th>
						<th>정비소 전화번호</th>

					</tr>
				</thead>


				<!-- href를 달아서, 만약 주소를 누르면 텍스트가 적히도록 해야 합니다.  -->


				<tr>

					<td><%=shopinfo.getShop_id()%></td>
					<td><%=shopinfo.getShop_name()%></td>
					<td><%=shopinfo.getShop_location()%></td>
					<td><%=shopinfo.getShop_phone()%></td>

				</tr>

			</table>



			<!-- 확인 버튼 누르면 팝업 닫히면 팝업 꺼지면서 회원가입 창에 정보가 적힌다.  -->
			<input type="button" class="btn btn-default" id="submitfrompopup"
				onclick="close()">확인</button>



		</div>
	</div>

</body>

<!-- 정비소  -->
<script type="text/javascript">
	function close() {

		self.close();
	}
</script>


</html>