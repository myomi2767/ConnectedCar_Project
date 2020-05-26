<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
					<button id="add_parts" class="btn btn-primary"
						onclick="window.open('expendableAdd.do', '_blank', 'width=500px,height=500px')">부품추가</button>
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
							<th scope="col">최근 수정일</th>
							<th scope="col">부품 코드</th>
							<th scope="col">종류</th>
							<th scope="col">제조사</th>
							<th scope="col">모델 이름</th>
							<th scope="col">개수</th>
							<th scope="col">가격</th>
							<th scope="col">관리</th>
						</tr>
					</thead>
					<tbody>
						
						<c:forEach items="${expendList}" var="expend">
							<tr>
								<td>${expend.shop_expend_date}</td>
								<td>${expend.expend_code}</td>
								<td>${expend.expend_type}</td>
								<td>${expend.expend_brand}</td>
								<td>${expend.car_model_name}</td>
								<td>${expend.shop_expend_count}</td>
								<td>${expend.expend_price}</td>
								<td>
									<a id='${expend.expend_id}' onclick='window.open("manageDetail.do?expend_id=" + $(this).attr("id"), "_blank", "width=800px,height=500px");'>관리</a>
								</td>
							</tr>
						</c:forEach>
						
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
	<script type="text/javascript">
	</script>
</body>
</html>