<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<!DOCTYPE html>
<html>
<head>

<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
	<!-- Title -->
    <title>Carnect</title>
</head>
<body>
	<div class="col-12">
		<!-- 이곳에 top화면을 연결하세요" -->
		<tiles:insertAttribute name="top"></tiles:insertAttribute>
	</div>
	<div class="col-12">
		<tiles:insertAttribute name="content"></tiles:insertAttribute>
	</div>
	<div class="col-12">
		<tiles:insertAttribute name="bottom"></tiles:insertAttribute>
	</div>
	
	
	
</body>


</html>