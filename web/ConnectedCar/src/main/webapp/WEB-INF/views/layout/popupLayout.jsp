<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>maeggiSeggi</title>
</head>
<body>
	<div>
		<tiles:insertAttribute name="content"></tiles:insertAttribute>
	</div>
	<div>
		<tiles:insertAttribute name="bottom"></tiles:insertAttribute>
	</div>
</body>
</html>