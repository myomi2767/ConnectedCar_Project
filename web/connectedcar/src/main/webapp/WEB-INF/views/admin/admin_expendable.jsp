<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html lang="en">
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
	<div class="container mtb">
		<div class="row">
			<div class="col-lg-8 col-lg-offset-1">
				<h3>재고관리</h3>
			</div>
		</div>
		<div class="row">
			<div class="col-sm-1"></div>
			<div class="col-sm-10">
				<div class="pull-right">
					<button id="add parts" class="btn btn-primary"
						onclick="window.open('adminexpendableAdd.do', '_blank', 'width=500px,height=500px')">부품추가</button>
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
							<th scope="col">부품코드</th>
							<th scope="col">종류</th>
							<th scope="col">제조사</th>
							<th scope="col">모델</th>
							<th scope="col">이름</th>
							<th scope="col">비고</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td>AA001</td>
							<td>브레이크 패드</td>
							<td>현대</td>
							<td>그랜져</td>
							<td>이름???</td>
							<td><a href="#">비고</a></td>
						</tr>
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