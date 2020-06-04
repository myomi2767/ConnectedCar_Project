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
			<canvas id="annual_chart" width="75%" height="50%"></canvas>
		</div>
	</div>
	<div>
		
		
	</div>
	<script type="text/javascript">
		window.onload = function() {
			if('${bList}' != null && '${loginuser}' != null) {
				setAnnualChart();
			}
			
		}
		
		function setAnnualChart() {
			var ctx = document.getElementById("annual_chart").getContext("2d");
			var annualChart = new Chart(ctx, {
				type: 'bar',
				data: {				
					labels: ['${bList.get(0).expend_type}' + "\n" + '${bList.get(0).car_model_name}',
						'${bList.get(1).expend_type}' + "\n" + '${bList.get(1).car_model_name}',
						'${bList.get(2).expend_type}' + "\n" + '${bList.get(2).car_model_name}',
						'${bList.get(3).expend_type}' + "\n" + '${bList.get(3).car_model_name}',
						'${bList.get(4).expend_type}' + "\n" + '${bList.get(4).car_model_name}',
						'${bList.get(5).expend_type}' + "\n" + '${bList.get(5).car_model_name}',
						'${bList.get(6).expend_type}' + "\n" + '${bList.get(6).car_model_name}',
						'${bList.get(7).expend_type}' + "\n" + '${bList.get(7).car_model_name}',
						'${bList.get(8).expend_type}' + "\n" + '${bList.get(8).car_model_name}',
						'${bList.get(9).expend_type}' + "\n" + '${bList.get(9).car_model_name}'
						],
					datasets: [{
						label: '올해 출고량',
						data: ['${bList.get(0).out_count}','${bList.get(1).out_count}','${bList.get(2).out_count}','${bList.get(3).out_count}',
							'${bList.get(4).out_count}','${bList.get(5).out_count}','${bList.get(6).out_count}','${bList.get(7).out_count}',
							'${bList.get(8).out_count}','${bList.get(9).out_count}'],
						backgroundColor: [
							'#ff5b74', '#bebebe', '#bebebe', '#bebebe',
							'#bebebe', '#bebebe', '#bebebe', '#bebebe',
							'#bebebe', '#0080ff'
						],
						borderColor: [
							'#ff5b74', '#bebebe', '#bebebe', '#bebebe',
							'#bebebe', '#bebebe', '#bebebe', '#bebebe',
							'#bebebe', '#0080ff'
						],
						borderWidth: 1
					}]
				},
				options: {
					scales: {
						yAxes: [{
							display: true,
							scaleLabel: {
								fontSize: 20,
								fontStyle: 'bold',
								display: true,
								labelString: '출고량'
								},
							ticks:{
								beginAtZero: true
							}
						}],
						xAxes: [{
							display: true,
							scaleLabel: {
								fontSize: 20,
								fontStyle: 'bold',
								display: true,
								labelString: '부품 종류'
							}
						}]
					},
					legend: {
						labels: {
							fontSize: 20,
                            fontColor: '#000000',
                            fontStyle: 'bold'
						}
					}
				}
			});
		}
	</script>
</body>
</html>