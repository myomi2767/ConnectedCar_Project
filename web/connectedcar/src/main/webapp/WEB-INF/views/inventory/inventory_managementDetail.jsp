<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>

<meta charset="UTF-8">

<!-- 테이블 용 css -->
<link rel="stylesheet" type="text/css"	href="/connectedcar/common/css/minjae/table.css">
</head>
<body>
	<div class="container">
		<div class="row">
			<div id="code" class="col-sm-4">부품코드 :</div>
			<div id="model" class="col-sm-4">모델이름 :</div>
			<div class="col-sm-4">
				<div class="row">
					<button id="Recieve_btn" class="btn btn-primary" style="width: 45%; margin-right: 5px"
						onclick="window.open('recieve.do', '_blank', 'width=300px,height=300px')">입고</button>
					<button id="Release_btn" class="btn btn-primary" style="width: 45%"
						onclick="window.open('release.do', '_blank', 'width=300px,height=300px')">출고</button>
				</div>
			</div>
		</div>
		<div class="row" style="padding-top: 10px">
			<div class="col-lg-10 col-lg-offset-1">
				<table id="table_managemenDetailList" class="type01">
					<thead style="text-align: center;">
						<tr>
							<th scope="col">입/출고</th>
							<th scope="col">날짜</th>
							<th scope="col">변화량</th>
						</tr>
					</thead>
					<tbody>
					<c:forEach items="${logList}" var="log">
						<tr>
							<td>${log.in_out_code}</td>
							<td>${log.in_out_date}</td>
							<td>${log.expend_count}</td>
						</tr>
					</c:forEach>
					<div id="expend_id" style="display: none;">${logList.get(0).expend_id}</div>
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
		</div>
		<!-- management area END -->
	</div>
	<script type="text/javascript">
	$(document).ready(function() {
		var id = $("#expend_id").html();
		$("#code").html(opener.parent.$("a[id="+id+"]").closest("tr").children("td").eq(1).html());
		$("#model").html(opener.parent.$("a[id="+id+"]").closest("tr").children("td").eq(4).html());
		
		/* $.ajax({
			url: "/connectedcar/"
		}); */
	});
	</script>
</body>
</html>
