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
	
	<div><input type="number" size="5px" required="required"></div><!-- 숫자가 있을 때만 등록가능 => required속성 -->
	<div style="padding-top: 10px"><input type="submit" value="확인" onclick="self.close()"></div>
</div>
</body>
</html>