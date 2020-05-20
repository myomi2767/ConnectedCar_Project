<%@page import="java.nio.channels.SeekableByteChannel"%>
<%@page import="connected.car.owner.OwnerVO"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.Calendar"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>

<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">
<link rel="shortcut icon" href="/connectedcar/images/favicon.png">

<title>SOLID - Bootstrap 3 Theme</title>

<!-- Bootstrap core CSS -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">

<!-- Custom styles for this template -->
<link href="/connectedcar/common/css/style.css" rel="stylesheet">
<link href="/connectedcar/common/css/font-awesome.min.css" rel="stylesheet">

<!-- Just for debugging purposes. Don't actually copy this line! -->
<!--[if lt IE 9]><script src="../../assets/js/ie8-responsive-file-warning.js"></script><![endif]-->

<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
      <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->

<script src="/connectedcar/common/js/modernizr.js"></script>
<script src="/connectedcar/common/js/jquery-3.2.1.min.js"></script>
<style type="text/css">
ul {list-style-type:none;margin:0;padding:0;}
li {display:inline-block;}

</style>
</head>

<body>

<!-- 로그인 세션 걸어 줄 로그인유저 정보 정의 -->
<%
	OwnerVO loginuser = (OwnerVO)session.getAttribute("loginuser");
%>

<%
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-mm-dd");
		Calendar today = Calendar.getInstance();
		int year = today.get(Calendar.YEAR);
		int month = today.get(Calendar.MONTH) + 1;
		int date = today.get(Calendar.DATE);
		String Dday = Integer.toString(year)+'-'+Integer.toString(month)+'-'+Integer.toString(date);
	
	%>
                
                
                
                <!--  Login Register Area -->
         

	<div >
	<div class ="Login">
	          <ul class ="loginlist" >
	          <% if(loginuser == null){ %>
                            <li >
                                <a href="/connectedcar/ownerlogin/login.do" >sign in</a>
                            </li>
                            <% } else {
                            	session.setAttribute("loginuser", loginuser);
                            	session.setAttribute("today", Dday);
                            	session.setAttribute("id", loginuser.getOwner_id());
                            	session.setAttribute("name", loginuser.getOwner_name());
                            %>
                            <li ><span><%= session.getAttribute("name") %>님 환영합니다.&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp</span>
                                <a href="/connectedcar/ownerlogin/logout.do" >sign out</a>
                            </li>
                             <% } %>
                            <li >
                                <a href="/connectedcar/ownerlogin/join.do">sign up</a>
                            </li>
             </ul>
	</div>
	</div>
	<div class="navbar navbar-default " role="navigation">
	
		<div class="container">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle" data-toggle="collapse"
					data-target=".navbar-collapse">
					<span class="sr-only">Toggle navigation</span> <span
						class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="index.html">Carnect</a>
			</div>
			<div class="navbar-collapse collapse navbar-right" style="margin-top: 12px;">
				<ul class="nav navbar-nav">
					<li class="active"><a href="/connectedcar/inventory/inventorymain.do">MAIN</a></li>
					<li><a href="/connectedcar/inventory/manageList.do">재고관리</a></li>	
					 <li class="dropdown"><a href="/connectedcar/admin/expendable.do" class="dropdown-toggle"
						data-toggle="dropdown">MANAGER <b class="caret"></b></a>
						<ul class="dropdown-menu">
							<li><a href="/connectedcar/admin/expendable.do">부품추가 및 삭제</a></li>
							<li><a href="/connectedcar/admin/member.do">회원 관리</a></li>
						</ul></li> 
				</ul>
			</div>
			<!--/.nav-collapse -->
		</div>
	</div>

	<!-- Bootstrap core JavaScript
    ================================================== -->
	<!-- Placed at the end of the document so the pages load faster -->
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
	<script src="/connectedcar/common/js/bootstrap.min.js"></script>
	<script src="/connectedcar/common/js/retina-1.1.0.js"></script>
	<script src="/connectedcar/common/js/jquery.hoverdir.js"></script>
	<script src="/connectedcar/common/js/jquery.hoverex.min.js"></script>
	<script src="/connectedcar/common/js/jquery.prettyPhoto.js"></script>
	<script src="/connectedcar/common/js/jquery.isotope.min.js"></script>
	<script src="/connectedcar/common/js/custom.js"></script>
</body>
</html>