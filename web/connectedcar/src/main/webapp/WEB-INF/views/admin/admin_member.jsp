<%@page import="connected.car.owner.OwnerVO"%>
<%@page import="java.util.ArrayList"%>
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
	<%
		ArrayList<OwnerVO> ownerlist = (ArrayList<OwnerVO>) request.getAttribute("admin_ownerlist");
	%>

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
							<th scope="col">아이디</th>
							<th scope="col">이름</th>
							<th scope="col">가입날짜</th>
							<th scope="col">전화번호</th>
							<th scope="col">정비소 코드</th>
							<th scope="col">관리</th>
						</tr>
					</thead>
					<tbody>
					
						<%
							for (int i = 0; i < ownerlist.size(); i++) {
								OwnerVO row = ownerlist.get(i);
						%>
						
						
						
						<tr>
							<td><%=row.getOwner_id()%></td>
							<td><%=row.getOwner_name()%></td>
							<td><%=row.getOwner_regdate()%></td>
							<td><%=row.getOwner_phone()%></td>
							<td><%=row.getShop_id()%></td>
							<td><a href="">삭제</a></td>
						</tr>
					
						<%
							}
						%>
					</tbody>

				</table>

			</div>
			<div class="col-sm-1"></div>
		</div>
	</div>
</body>

</html>