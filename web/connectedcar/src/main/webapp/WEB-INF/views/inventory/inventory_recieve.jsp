<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
<head>
	<title>inventory_recieve</title>
</head>
<body>
<div class="container">
	<h4>입고</h4>
	<div class="row">
		<div class="col-sm-4"></div>
		<div class="col-sm-4"><input type="number" required="required"></div><!-- 숫자가 있을 때만 등록가능 => required속성 -->
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
			
			//opener.parent.$("#code").html()
			self.close();
		});
	})
</script>
</body>
</html>