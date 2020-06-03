<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="connected.car.admin.ExpendableVO"%>
<%@ page import="connected.car.admin.Pagination"%>
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
	<%
		List<ExpendableVO> list = (List<ExpendableVO>) request.getAttribute("expendList");
	%>
	<%
		Pagination paging = (Pagination) request.getAttribute("paging");
	%>
	<div class="container mtb">
		<div class="row">
			<div class="col-lg-8 col-lg-offset-1">
				<h3>부품관리</h3>

			</div>
		</div>
		<div class="row">
			<div class="col-lg-10 col-lg-offset-1">
				<hr>
			</div>
		</div>

		<div class="row">
			<div class="col-lg-10 col-lg-offset-1"></div>
		</div>

		<div class="row">
			<div class="col-sm-1"></div>
			<div class="col-sm-1">
				<div>
					<select id="cntPerPage" name="sel" onchange="selChange()">
						<option value="5"
							<c:if test="${paging.cntPerPage == 5}">selected</c:if>>5줄
							보기</option>
						<option value="10"
							<c:if test="${paging.cntPerPage == 10}">selected</c:if>>10줄
							보기</option>
						<option value="15"
							<c:if test="${paging.cntPerPage == 15}">selected</c:if>>15줄
							보기</option>
						<option value="20"
							<c:if test="${paging.cntPerPage == 20}">selected</c:if>>20줄
							보기</option>
					</select>
				</div>
				<!-- 옵션선택 끝 -->
			</div>
			<div class="col-sm-8">
				<!-- <form action="/connectedcar/admin/search.do" method="GET"> --><!-- <label>부품 검색</label> -->
					<input type="text" id="keyword" name="keyword" style="width: 600px;" />
					<Button type="button" id="searchBtn" class="btn btn-default">검색</Button>
				<!-- </form> -->
			</div>
			<div class="col-sm-1">
				<div class="pull-right">
					<button id="add_parts" class="btn btn-primary" style=""
						onclick="window.open('adminexpendableAdd.do', '_blank', 'width=500px,height=600px')">부품추가</button>
				</div>
			</div>

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
							for (int i = 0; i < list.size(); i++) {
								ExpendableVO eVo = list.get(i);
						%>

						<tr>
							<td><%=eVo.getExpend_code()%></td>
							<td><%=eVo.getExpend_type()%></td>
							<td><%=eVo.getExpend_name()%></td>
							<td><%=eVo.getExpend_price()%></td>
							<td><%=eVo.getExpend_brand()%></td>
							<td><%=eVo.getCar_model_name()%></td>
							<td>
								<form action="/connectedcar/admin/expendableDelete.do"
									method="POST">
									<input type="hidden" name="expend_id"
										value="<%=eVo.getExpend_id()%>">
									<button type="submit" id="submit_btn" class="btn btn-theme"
										onclick="alert('삭제되었습니다.');">삭제</button>
								</form>
							</td>
						</tr>
						<%
							}
						%>
					</tbody>
				</table>
				<% if(paging.getKeyword()==null){ %>
				<div style="display: block; text-align: center;">
					<c:if test="${paging.startPage != 1 }">
						<a href="/connectedcar/admin/expendable.do?curPage=${paging.startPage - 1 }&cntPerPage=${paging.cntPerPage}">&lt;</a>
					</c:if>
					<c:forEach begin="${paging.startPage }" end="${paging.endPage }" var="p">
						<c:choose>
							<c:when test="${p == paging.curPage }">
								<b>${p }</b>
							</c:when>
							<c:when test="${p != paging.curPage }">
								<a href="/connectedcar/admin/expendable.do?curPage=${p }&cntPerPage=${paging.cntPerPage}">${p }</a>
							</c:when>
						</c:choose>
					</c:forEach>
					<c:if test="${paging.endPage != paging.lastPage}">
						<a href="/connectedcar/admin/expendable.do?curPage=${paging.endPage+1 }&cntPerPage=${paging.cntPerPage}">&gt;</a>
					</c:if>
				</div>
				<% }else { %>
				<div style="display: block; text-align: center;">
					<c:if test="${paging.startPage != 1 }">
						<a href="/connectedcar/admin/search.do?keyword=${paging.keyword}&curPage=${paging.startPage - 1 }&cntPerPage=${paging.cntPerPage}">&lt;</a>
					</c:if>
					<c:forEach begin="${paging.startPage }" end="${paging.endPage }" var="p">
						<c:choose>
							<c:when test="${p == paging.curPage }">
								<b>${p }</b>
							</c:when>
							<c:when test="${p != paging.curPage }">
								<a href="/connectedcar/admin/search.do?keyword=${paging.keyword}&curPage=${p }&cntPerPage=${paging.cntPerPage}">${p }</a>
							</c:when>
						</c:choose>
					</c:forEach>
					<c:if test="${paging.endPage != paging.lastPage}">
						<a href="/connectedcar/admin/search.do?keyword=${paging.keyword}&curPage=${paging.endPage+1 }&cntPerPage=${paging.cntPerPage}">&gt;</a>
					</c:if>
				</div>
				<% } %>
			</div>
			<div class="col-sm-1"></div>
		</div>
		<!-- management area END -->

	</div>
	<script type="text/javascript">
		function selChange() {
			var sel = document.getElementById('cntPerPage').value;
			var path = $(location).attr('pathname');
			if(path=="/connectedcar/admin/expendable.do"){
				location.href = "/connectedcar/admin/expendable.do?curPage=${paging.curPage}&cntPerPage="
					+ sel;	
			}else{
				location.href = "/connectedcar/admin/search.do?keyword=${paging.keyword}&curPage=${paging.curPage}&cntPerPage="
					+ sel;
			}
			
			
		}
		$(document).ready(function(){
			$("#searchBtn").on("click", function(){
				keyword = $("#keyword").val();
				location.href="/connectedcar/admin/search.do?&keyword="+encodeURI(keyword);
				/* $.get("/LPG/match/list.do", {"mchName":$("#mchName").val(),"mchDate":$("#mchDate").val(),
					"mchPlay":$("#mchPlay").val(),"mchType":$("#mchType"),"grdArea":$("#grdArea").val()}); */
			});
		});
	</script>
</body>
</html>