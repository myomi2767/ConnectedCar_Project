
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
<title>재고 관리</title>

<!-- 
<!-- Core Stylesheet -->
<link href="/connectedcar/common/css/k_inventorymain.css" rel="stylesheet">


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
	
	
	<div id="div_expendable">

		
		<div class="container mtb">
			<div class="col-sm-6" id="table_expendable">
			<h3>재고 관리</h3>
						<table class="table table-striped">
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
							for (int i = 0; i < 30; i++) {
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
					<div class="col-sm-6"  id = "graph_expendable">
					<h3>재고 현황 그래프</h3>
					
						<table class="table table-striped">
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
						
							<h3>재고 관리</h3>
						<table class="table table-striped">
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

		</div>
	</div>
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