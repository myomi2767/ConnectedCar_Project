<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

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

<!-- 차트 -->
<script src="https://cdn.jsdelivr.net/npm/chart.js@2.9.3/dist/Chart.min.js"></script>

</head>
<body>	
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
					<c:forEach items="${expendList}" var="expend">
						<tr>
							<td>${expend.expend_code}</td>
							<td>${expend.expend_name}</td>
							<td>${expend.shop_expend_count}</td>
						</tr>
					</c:forEach>
				</table>
			</div>
			<div class="col-sm-6"  id = "graph_expendable">
				<h3>연 매출 그래프</h3>
				<!-- <table class="table table-striped">
		  			<thead class="thead-dark">
						<tr>
							<th>부품코드</th>
							<th>부품명</th>
							<th>수량</th>				
						</tr>
					</thead>
						<tr>
							<td></td>
							<td></td>
							<td></td>
						</tr>
				</table> -->
				<canvas id="annual_chart" width="400" height="400"></canvas>
				<h3>재고 관리</h3>
				<table class="table table-striped">
	  				<thead class="thead-dark">
						<tr>
							<th>부품코드</th>
							<th>부품명</th>
							<th>수량</th>			
						</tr>
					</thead>
						<tr>
							<td></td>
							<td></td>
							<td></td>
						</tr>
				</table>
			</div>
		</div>
	</div>
	<div>
		
		
	</div>
	<script type="text/javascript">
		window.onload = function() {
			setAnnualChart();
		}
		
		function setAnnualChart() {
			var ctx = document.getElementById("annual_chart").getContext("2d");
			var annualChart = new Chart(ctx, {
				type: 'bar',
				data: {
					labels: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
					datasets: [{
						label: "분기 별 매출액",
						data: ['${annualList[0]}','${annualList[1]}','${annualList[2]}','${annualList[3]}',
							'${annualList[4]}','${annualList[5]}','${annualList[6]}','${annualList[7]}',
							'${annualList[8]}','${annualList[9]}','${annualList[10]}','${annualList[11]}'],
						backgroundColor: [
							'#F8D3D2', '#F8D3D2', '#F8D3D2', '#F8D3D2',
							'#C77766', '#C77766', '#C77766', '#C77766',
							'#EFC1A9', '#EFC1A9', '#EFC1A9', '#EFC1A9',
							'#F8F8DB', '#F8F8DB', '#F8F8DB', '#F8F8DB'
						],
						borderColor: [
							'#F8D3D2', '#F8D3D2', '#F8D3D2', '#F8D3D2',
							'#C77766', '#C77766', '#C77766', '#C77766',
							'#EFC1A9', '#EFC1A9', '#EFC1A9', '#EFC1A9',
							'#F8F8DB', '#F8F8DB', '#F8F8DB', '#F8F8DB'
						],
						borderWidth: 1
					}]
				},
				options: {
					scales: {
						yAxes: [{
							ticks:{
								beginAtZero: true
							}
						}]
					}
				}
			});
		}
	</script>
</body>
</html>