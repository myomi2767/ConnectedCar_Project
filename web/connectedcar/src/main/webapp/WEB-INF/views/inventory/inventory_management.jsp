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
				<br/>
			</div>
		</div>
		<div class="row">
			<div class="col-sm-1"></div>
			<div class="col-sm-1">
				<div >
					<select id="cntPerPage" name="sel" onchange="selChange()">
						<option value="5"
							<c:if test="${paging.cntPerPage == 5}">selected</c:if>>5줄 보기</option>
						<option value="10"
							<c:if test="${paging.cntPerPage == 10}">selected</c:if>>10줄 보기</option>
						<option value="15"
							<c:if test="${paging.cntPerPage == 15}">selected</c:if>>15줄 보기</option>
						<option value="20"
							<c:if test="${paging.cntPerPage == 20}">selected</c:if>>20줄 보기</option>
					</select>
				</div> <!-- 옵션선택 끝 -->
			</div>
			<div class="col-sm-9">
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
							<th scope="col">등록일</th>
							<th scope="col">부품 코드</th>
							<th scope="col">종류</th>
							<th scope="col">제조사</th>
							<th scope="col">모델 이름</th>
							<th scope="col">개수</th>
							<th scope="col">가격</th>
							<th scope="col">관리</th>
							<th scope="col">삭제</th>
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
									<a id='${expend.expend_id}'class="btn btn-theme" onclick='window.open("manageDetail.do?expend_id=" + $(this).attr("id"), "_blank", "width=800px,height=500px");'>관리</a>
								</td>
								<td><form action="/connectedcar/expendable/deleteExpend.do"
									method="POST">
									<input type="hidden" name="expend_id"
										value="${expend.expend_id}">
									<button type="submit" id="expend_delete_btn" class="btn btn-default"
										onclick="alert('삭제되었습니다.');">삭제</button>
								</form></td>
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
				<div style="display: block; text-align: center;">		
					<c:if test="${paging.startPage != 1 }">
						<a href="/connectedcar/inventory/manageList.do?curPage=${paging.startPage - 1 }&cntPerPage=${paging.cntPerPage}">&lt;</a>
					</c:if>
					<c:forEach begin="${paging.startPage }" end="${paging.endPage }" var="p">
						<c:choose>
							<c:when test="${p == paging.curPage }">
								<b>${p }</b>
							</c:when>
							<c:when test="${p != paging.curPage }">
								<a href="/connectedcar/inventory/manageList.do?curPage=${p }&cntPerPage=${paging.cntPerPage}">${p }</a>
							</c:when>
						</c:choose>
					</c:forEach>
					<c:if test="${paging.endPage != paging.lastPage}">
						<a href="/connectedcar/inventory/manageList.do?curPage=${paging.endPage+1 }&cntPerPage=${paging.cntPerPage}">&gt;</a>
					</c:if>
				</div>
			</div>
			<div class="col-sm-1"></div>
		</div>
		<!-- management area END -->
	</div>
	<script type="text/javascript">
	function selChange() {
		var sel = document.getElementById('cntPerPage').value;
		location.href="/connectedcar/inventory/manageList.do?curPage=${paging.curPage}&cntPerPage="+sel;
	}
	</script>
</body>
</html>