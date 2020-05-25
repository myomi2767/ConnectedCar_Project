<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="connected.car.inventory.ExpendableVO" %>
<%@ page import="java.util.List"%>	
<!DOCTYPE html>

<html>
<head>
<title>inventory_management</title>
<link rel="stylesheet" type="text/css"
	href="/connectedcar/common/css/minjae/table.css">

</head>

<body>

<!-- 	<div id="blue">
		<div class="container">
			
			/row
		</div>
		/container
	</div>
	/headerwrap -->
	<% List<ExpendableVO> list = (List<ExpendableVO>)request.getAttribute("expendList"); %>
	<div class="container mtb">
		<div class="row">
			<div class="col-lg-8 col-lg-offset-1">
				<h3>부품관리</h3>
			</div>
		</div>
		<div class="row">
			<div class="col-sm-1"></div>
			<div class="col-sm-10">
				<div class="pull-right">
					<button id="add_parts" class="btn btn-primary"
						onclick="window.open('adminexpendableAdd.do', '_blank', 'width=500px,height=600px')">부품추가</button>
				</div>
			</div>
			<div class="col-sm-1"></div>
		</div>
		<!-- management area start -->
		<div class="row">
			<div class="col-sm-1"></div>
			<div id="management" class="col-sm-10"
				style="padding-top: 10px; padding-left: 20px;">
				<table id="table_managementList" class="type01">
					<thead>
						<tr style="font-weight: bold;">
							<th scope="col">부품 코드</th>
							<th scope="col">종류</th>
							<th scope="col">부품명</th>
							<th scope="col">가격</th>
							<th scope="col">제조사</th>
							<th scope="col">부품 자동차 모델</th>
							<th scope="col">부품삭제</th>
						</tr>
					</thead>
					<tbody>
					<!-- 모든 부품리스트 불러오는 곳 -->
					<% 
						for(int i=0;i<list.size();i++){
							ExpendableVO eVo = list.get(i);
					%>
						
						<tr>
							<td><%= eVo.getExpend_code() %></td>
							<td><%= eVo.getExpend_type() %></td>
							<td><%= eVo.getExpend_name() %></td>
							<td><%= eVo.getExpend_price() %></td>
							<td><%= eVo.getExpend_brand() %></td>
							<td><%= eVo.getCar_model_name() %></td>
							<td>
								<form action="/connectedcar/admin/expendableDelete.do" method="POST">
									<input type="hidden" name="expend_id" value="<%= eVo.getExpend_id()%>">
									<button type="submit" id="submit_btn" class="btn btn-theme">삭제</button>
								</form>
							</td>
						</tr>
					<% } %>
						<%-- <%
						for (int i = 0; i < list.size(); i++) {
							BoardVO row = list.get(i);
					%>
					<tr>
					<td><%=row.getMember_id()%></td>
						<td><a href="/maeggiSeggi/board/read.do?askno=<%=row.getAskno()%>"><%=row.getAsk_title()%></a></td>
						<td><%=row.getAsk_regdate()%></td>
					</tr>
					<%
						}
					%> --%>
					</tbody>
				</table>
			</div>
			<div class="col-sm-1"></div>
		</div>
		<!-- management area END -->
	</div>
</body>
</html>