<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
<head>
	<title>inventory_release</title>
</head>
<body>
<div class="container">
	<div id="shop_id" style="display: none;">${shop_id}</div>
	<div id="expend_id" style="display: none;">${expend_id}</div>
	<h4 id="code">출고</h4>
	<div class="row">
		<div class="col-sm-4"></div>
		<div class="col-sm-4"><input id="count" type="number" required="required"></div><!-- 숫자가 있을 때만 등록가능 => required속성 -->
		<div class="col-sm-4"></div>
	</div>
	<div class="row">
		<div class="col-sm-3"></div>
		<div class="col-sm-3" style="padding-top: 10px"><input id="btn_in" type="submit" value="확인" onclick=""></div>
		<div class="col-sm-3"></div>
	</div>
</div>
<script type="text/javascript">
	$(document).ready(function() {
		$("#btn_in").on("click", function() {
			insert();	
			setTimeout(function() {
				opener.parent.location.reload();
				self.close();
			}, 1000);
		});
	})
	
	function insert() {
		var sId = $("#shop_id").html();
		var eId = $("#expend_id").html();
		var code = $("#code").html();
		var count = $("#count").val();
		
		$.ajax({
			url: "/connectedcar/expendable/insertLog.do",
			type: "post",
			dataType: "text",
			data: {
				"shop_id" : sId,
				"expend_id" : eId,
				"in_out_code" : code,
				"expend_count" : count
			},
			success: function(data) {
				
			}
		});
	}
</script>
</body>
</html>
