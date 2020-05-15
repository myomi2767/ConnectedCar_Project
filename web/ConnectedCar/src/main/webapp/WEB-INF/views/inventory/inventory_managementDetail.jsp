<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
<head>
<title>inventory_managementDetail</title>
<!-- 테이블 용 css -->
<link rel="stylesheet" type="text/css"
	href="/mycar/common/css/minjae/table.css">

<!-- 그리드 시스템을 사용하기 위한 부트스트랩 -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css"
	integrity="sha384-1q8mTJOASx8j1Au+a5WDVnPi2lkFfwwEAa8hDDdjZlpLegxhjVME1fgjWPGmkzs7"
	crossorigin="anonymous">

</head>
<body>
	<div class="container centered">
		<div class="row">
			<div class="col-sm-4">부품코드 :</div>
			<div class="col-sm-4">모델이름 :</div>
			<div class="col-sm-4">
				<div class="row righted">
					<div class="col-sm-3"></div>
					<div class="col-sm-3">
						<button id="Recieve_btn"
							onclick="window.open('recieve.do', '_blank', 'width=300px,height=300px')">입고</button>
					</div>
					<div class="col-sm-3">
						<button id="Release_btn"
							onclick="window.open('release.do', '_blank', 'width=300px,height=300px')">출고</button>
					</div>
					<div class="col-sm-3"></div>
				</div>
			</div>
		</div>
		<div class="row" style="padding-top: 10px">
			<table id="table_managemenDetailList" class="type01">
				<thead style="text-align: center;">
					<tr>
						<th scope="col">입/출고</th>
						<th scope="col">날짜</th>
						<th scope="col">변화량</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td>입고</td>
						<td>오늘</td>
						<td>+5</td>
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
		<!-- management area END -->
	</div>
</body>
</html>
