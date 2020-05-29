<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<!DOCTYPE html>
<%@ page import="java.net.URLEncoder"%>
<%@ page import="java.security.SecureRandom"%>
<%@ page import="java.math.BigInteger"%>
<html>

<head>
<meta charset="UTF-8">
<meta name="description" content="">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<!-- The above 4 meta tags *must* come first in the head; any other head content must come *after* these tags -->



<!-- Title -->
<title>로그인 페이지</title>


<!-- font -->
<!--  <link href="/connectedcar/common/css/maeggiFonts.css" rel="stylesheet"> -->
<!-- Core Stylesheet -->
<link href="/connectedcar/common/css/style.css" rel="stylesheet">
<link href="/connectedcar/common/css/k_login.css" rel="stylesheet">


<!-- Responsive CSS -->
<!--  <link href="/connectedcar/common/css/responsive/responsive.css"
	rel="stylesheet">  -->

</head>

<body>

<br>
<br>
<br>


	<div class="container">

		<!-- 여기부터  로그인 컨텐츠-->
		<div id="outter">


			<div id="right_inner">
			 <form action="/connectedcar/ownerlogin/login.do" method="post">
					<div id="login_box">
						<h1>Member Login</h1>
						<ul id="input_button">
							<li id="id_pass">
								<ul>
									<li class="logintext" id="owner_id">
										<div class="logintext_div">
											<input type="text" class="input_logintext" placeholder=' 아이디'
												name="owner_id">
										</div>
									</li>
									<!-- id -->
									<br/>
									<li class="logintext" id="owner_password">
										<div class="logintext_div">
											<input type="password" class="input_logintext" name="owner_password"
												placeholder=' 비밀번호'>
										</div>
									</li>
									<!-- pass -->
								</ul>
							</li>
							<br/>
							<br/>
							<li><input type="submit" id="loginbutton" value="로그인" /></li>
						</ul>

						<!-- <div id="joinlist">
							<a href="/maeggiSeggi/loginandcustomer/join.do">[회원가입]</a>
						</div> -->
						<div id="joinlist" >
							<a href="/connectedcar/ownerlogin/join.do" class = "btn btn-default">회원가입</a>
						</div>

					</div>

		</form>
			</div>

			<script type='text/javascript'>
				var owner_id;
				var shop_id;
				var owner_name;
				var owner_phone;
				

				function logininfo() {
					var results = '';
					results += "<h2> 아이디값:" + owner_id + "</h2>"
					results += "<h2> 이름:" + owner_name + "</h2>"
					results += "<h2> 정비소이름:" + shop_id + "</h2>"
					results += "<h2> 전화번호:" + shop_id + "</h2>"
					$('#logininformation').append(results);
				}

				function logout() {
					alert("로그아웃 되었습니다.");
				}
			</script>




		</div>
	</div>



</body>