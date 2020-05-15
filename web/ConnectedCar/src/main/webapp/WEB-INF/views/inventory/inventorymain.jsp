
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<!DOCTYPE html>

<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="description" content="">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<!-- The above 4 meta tags must come first in the head; any other head content must come after these tags -->
<!-- Title -->
<title>1:1문의사항</title>

<!-- Jquery-3.2.1 js -->
<script src="/mycar/common/js/jquery-3.2.1.min.js"></script>

<!-- 
<!-- Core Stylesheet -->
<link href="/mycar/common/css/k_inventorymain.css" rel="stylesheet">


<!-- All Plugins JS (지우지말것)-->
<script src="/mycar/common/js/others/plugins.js"></script>

<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">



</head>
<body>
	<%-- <%
		ArrayList<BoardVO> list = (ArrayList<BoardVO>)request.getAttribute("list");
		memberVO admin_loginuser = (memberVO) session.getAttribute("admin_loginuser");
	
	%> --%>
	
	
	<%--  <% if(session.getAttribute("id")!= null){ //로그인유저만 접근 가능 %> --%>
	<!--  <script type="text/javascript">
	 var sessionid = session.getAttribute("id");
	 document.write(sessionid);
	 </script>
		  -->
	
	
	
	
	<!-- 1:1 ask area start -->
	<div id="div_expendable">

		<h3>재고 관리</h3>
						<table class="table table-striped" id="table_expendable">
  							<thead class="thead-dark">
							<tr>
							<th>부품코드</th>
							<th>부품명</th>
							<th>수량</th>
												
							</tr>
							</thead>
						<%-- 
							<%
								for (int i = 0; i < mealplan.size(); i++) {
									mealPlannerVO meal = mealplan.get(i);
							%> --%>
							<% 
							for (int i = 0; i < 10; i++) {
								%>
							
								
							<tr>

								<td><%--  <%=meal.getPlanner_date()%> --%> </td>
								<td><%-- <% if(meal.getPlanner_code()==1){
									out.println("아침");
								}else if(meal.getPlanner_code()==2){
									out.println("점심");
								}else{
									out.println("저녁");
								}%> --%></td>
								<td><%-- <%=meal.getMeal_name()%> --%></td>
							

								<%
									}
								%>
							</tr>

						</table>

		
	</div>
	<div></div>
	<div>
		
		
	</div>
<%--  <% } else{ %>
	
		<script type="text/javascript">
		alert("로그인이 필요한 기능입니다!");
		document.location.href="/maeggiSeggi/loginandcustomer/login.do";
		</script>
		<% }%>
	  --%>
</body>
</html>