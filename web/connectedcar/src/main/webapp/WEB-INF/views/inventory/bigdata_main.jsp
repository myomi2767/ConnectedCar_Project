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
<title>빅데이터 분석 결과</title>
<!-- 차트 -->
<script src="https://cdn.jsdelivr.net/npm/chart.js@2.9.3/dist/Chart.min.js"></script>

</head>
<body>	
	<div id="div_expendable">	
		<div class="container mtb">
			<h2>출고량 분석 예상 구매량 조회</h2>
			<br/>
			<div class="col-sm-6" id="table_expendable">
				<canvas id="annual_chart" width="1000" height="1000"></canvas>
			</div>
		</div>
	</div>
	<div>
		
		
	</div>
	<script type="text/javascript">
		window.onload = function() {
			setAnnualChart();
			setTypeChart();
			setDoughnutChart();
		}
		
		function setAnnualChart() {
			var ctx = document.getElementById("annual_chart").getContext("2d");
			var annualChart = new Chart(ctx, {
				type: 'bar',
				data: {				
					labels: [
						for (var i = 0; i < '${bList.size()}'; i++) {
							'${bList.get(' + i + ').getExpend_type()}',
						}
					],
					datasets: [{
						label: '월 별 매출액',
						data: ['${annualList[0]}','${annualList[1]}','${annualList[2]}','${annualList[3]}',
							'${annualList[4]}','${annualList[5]}','${annualList[6]}','${annualList[7]}',
							'${annualList[8]}','${annualList[9]}','${annualList[10]}','${annualList[11]}'],
						backgroundColor: [
							'#ff6c5f', '#ff6c5f', '#ff6c5f', '#ff6c5f',
							'#ffc168', '#ffc168', '#ffc168', '#ffc168',
							'#2dde98', '#2dde98', '#2dde98', '#2dde98',
							'#1cc7d0', '#1cc7d0', '#1cc7d0', '#1cc7d0'
						],
						borderColor: [
							'#ff6c5f', '#ff6c5f', '#ff6c5f', '#ff6c5f',
							'#ffc168', '#ffc168', '#ffc168', '#ffc168',
							'#2dde98', '#2dde98', '#2dde98', '#2dde98',
							'#1cc7d0', '#1cc7d0', '#1cc7d0', '#1cc7d0'
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
					},
					legend: {
						labels: {
							display: "false"
						}
					}
				}
			});
		}
		
		function setTypeChart() {
			var ctx = document.getElementById("type_chart").getContext("2d");
			var typeChart = new Chart(ctx, {
				type: 'line',
				data: {
					labels: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
					datasets: [{
						label: '${map1.getKey()}',
						fill: false,
						data: ['${map1.getValue()[0]}','${map1.getValue()[1]}','${map1.getValue()[2]}','${map1.getValue()[3]}',
							'${map1.getValue()[4]}','${map1.getValue()[5]}','${map1.getValue()[6]}','${map1.getValue()[7]}',
							'${map1.getValue()[8]}','${map1.getValue()[9]}','${map1.getValue()[10]}','${map1.getValue()[11]}'],
						backgroundColor: '#36a2eb',
						borderColor: '#36a2eb',
						borderWidth: 3
					}, 
					{
						label: '${map2.getKey()}',
						fill: false,
						data: ['${map2.getValue()[0]}','${map2.getValue()[1]}','${map2.getValue()[2]}','${map2.getValue()[3]}',
							'${map2.getValue()[4]}','${map2.getValue()[5]}','${map2.getValue()[6]}','${map2.getValue()[7]}',
							'${map2.getValue()[8]}','${map2.getValue()[9]}','${map2.getValue()[10]}','${map2.getValue()[11]}'],
						backgroundColor: '#4bc0c0',
						borderColor: '#4bc0c0',
						borderWidth: 3
					},
					{
						label: '${map3.getKey()}',
						fill: false,
						data: ['${map3.getValue()[0]}','${map3.getValue()[1]}','${map3.getValue()[2]}','${map3.getValue()[3]}',
							'${map3.getValue()[4]}','${map3.getValue()[5]}','${map3.getValue()[6]}','${map3.getValue()[7]}',
							'${map3.getValue()[8]}','${map3.getValue()[9]}','${map3.getValue()[10]}','${map3.getValue()[11]}'],
						backgroundColor: '#ffcd56',
						borderColor: '#ffcd56',
						borderWidth: 3
					}, 
					{
						label: '${map4.getKey()}',
						fill: false,
						data: ['${map4.getValue()[0]}','${map4.getValue()[1]}','${map4.getValue()[2]}','${map4.getValue()[3]}',
							'${map4.getValue()[4]}','${map4.getValue()[5]}','${map4.getValue()[6]}','${map4.getValue()[7]}',
							'${map4.getValue()[8]}','${map4.getValue()[9]}','${map4.getValue()[10]}','${map4.getValue()[11]}'],
						backgroundColor: '#ff9f40',
						borderColor: '#ff9f40',
						borderWidth: 3
					}, 
					{
						label: '${map5.getKey()}',
						fill: false,
						data: ['${map5.getValue()[0]}','${map5.getValue()[1]}','${map5.getValue()[2]}','${map5.getValue()[3]}',
							'${map5.getValue()[4]}','${map5.getValue()[5]}','${map5.getValue()[6]}','${map5.getValue()[7]}',
							'${map5.getValue()[8]}','${map5.getValue()[9]}','${map5.getValue()[10]}','${map5.getValue()[11]}'],
						backgroundColor: '#ff6384',
						borderColor: '#ff6384',
						borderWidth: 3
					}]
				},
				options: {
					scales: {
						yAxes: [{
							ticks:{
								beginAtZero: true
							}
						}]
					},
					legend: {
						labels: {
							display: "false"
						}
					}
				}
			});
		}
		
		function setDoughnutChart()
		{
			var ctx = document.getElementById("doughnut_chart").getContext("2d");
			var typeChart = new Chart(ctx, {
				type: 'doughnut',
				data: {
					datasets: [{
						data: [
							'${map1.getValue()[thisMonth]}', '${map2.getValue()[thisMonth]}', '${map3.getValue()[thisMonth]}',
							'${map4.getValue()[thisMonth]}', '${map5.getValue()[thisMonth]}'],
						backgroundColor: [
							'#36a2eb',
							'#4bc0c0',
							'#ffcd56',
							'#ff9f40',
							'#ff6384',
						],
						label: '부품 별 이달의 매출량'
					}],
					labels: [
						'${map1.getKey()}',
						'${map2.getKey()}',
						'${map3.getKey()}',
						'${map4.getKey()}',
						'${map5.getKey()}'
					]
				},
				options: {
					responsive: true,
					legend: {
						position: 'top',
					},
					title: {
						display: true,
						text: '각 부품 별 이달의 매출량'
					},
					animation: {
						animateScale: true,
						animateRotate: true
					}
				}
			});
		}
		
	
	</script>
</body>
</html>