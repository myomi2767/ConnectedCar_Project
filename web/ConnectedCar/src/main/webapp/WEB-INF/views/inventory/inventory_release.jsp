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
	<h3>출고</h3>
	<div class="row">
		<div class="col-sm-3"></div>
		<div class="col-sm-3"><input type="number" size="5px" required="required"></div><!-- 숫자가 있을 때만 등록가능 => required속성 -->
		<div class="col-sm-3" style="padding-top: 10px"><input type="submit" value="확인" onclick="self.close()"></div>
		<div class="col-sm-3"></div>
	</div>
	<!-- <div class="row">
		<div class="col-sm-4"></div>
		
		<div class="col-sm-4" style="padding-top: 10px"><input type="submit" value="확인" onclick="self.close()"></div>
		<div class="col-sm-4"></div>
	</div> -->
</div>
</body>
</html>
