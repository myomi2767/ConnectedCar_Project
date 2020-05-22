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
	<div class="container mtb">
		<div class="row">
			<div class="col-lg-8 col-lg-offset-1">
				<h3>회원정보 관리</h3>
			</div>
		</div>
		<div class="row">
			<div class="col-sm-1"></div>
			<div id="management" class="col-sm-10"
				style="padding-top: 10px; padding-left: 20px;">
				<table id="table_managementList" class="type01">
					<thead>
						<tr style="font-weight: bold;">
							<th scope="col">이름</th>
							<th scope="col">가입날짜</th>
							<th scope="col">정비소</th>
							<th scope="col">이름</th>
							<th scope="col">비고</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td>김매니저</td>
							<td>오늘</td>
							<td>김정비소</td>
							<td>성민재</td>
							<td><a href="#">삭제</a></td>
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
	</div>
</body>
</html>