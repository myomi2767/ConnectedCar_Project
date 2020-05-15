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
<!--  <link href="/mycar/common/css/maeggiFonts.css" rel="stylesheet"> -->
<!-- Core Stylesheet -->
<link href="/mycar/common/css/style.css" rel="stylesheet">
<link href="/mycar/common/css/k_login.css" rel="stylesheet">


<!-- Responsive CSS -->
<!--  <link href="/mycar/common/css/responsive/responsive.css"
	rel="stylesheet">  -->

</head>

<body>


	<div class="container">

		<!-- 여기부터  로그인 컨텐츠-->
		<div id="outter">


			<div id="right_inner">
			 <form action="/maeggiSeggi/loginandcustomer/login.do" method="post">
					<div id="login_box">
						<h1>Member Login</h1>
						<ul id="input_button">
							<li id="id_pass">
								<ul>
									<li class="logintext" id="member_id">
									<div class="logintext_div">
											<input type="text" class="input_logintext" placeholder=' 아이디'
												name="member_id">
										</div></li>
									<!-- id -->
									<li class="logintext" id="pass"><div class="logintext_div">
											<input type="password" class="input_logintext" name="pass"
												placeholder=' 비밀번호'>
										</div></li>
									<!-- pass -->
								</ul>
							</li>
							<li><input type="submit" id="loginbutton" value="로그인" /></li>
						</ul>

						<!-- <div id="joinlist">
							<a href="/maeggiSeggi/loginandcustomer/join.do">[회원가입]</a>
						</div> -->
						<div id="joinlist">
							<a href="">[회원가입]</a>
						</div>

					</div>

		</form>
			</div>

			<script type='text/javascript'>
				var user_id;
				var user_email;
				var user_name;
				var user_gender;
				var user_age;
				var user_birthday;

				function logininfo() {
					var results = '';
					results += "<h2> 아이디값:" + user_id + "</h2>"
					results += "<h2> 이름:" + user_name + "</h2>"
					results += "<h2> 이메일:" + user_email + "</h2>"
					$('#logininformation').append(results);
				}

				function logout() {
					alert("로그아웃 되었습니다.");
				}
			</script>




		</div>
	</div>



</body>