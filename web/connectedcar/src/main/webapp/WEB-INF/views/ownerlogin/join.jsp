<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<!DOCTYPE html>
<html>
​
<head>
​ ​
<meta charset="UTF-8">
<meta name="description" content="">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<!-- The above 4 meta tags *must* come first in the head; any other head content must come *after* these tags -->
​
<!-- Title -->
<title>회원 가입</title> ​

<!-- Core Stylesheet -->
<link href="/connectedcar/common/css/k_join.css" rel="stylesheet">
​ ​
<!-- Responsive CSS -->
<link href="/maeggiSeggi/common/css/responsive/responsive.css"
	rel="stylesheet">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/jquery-modal/0.9.1/jquery.modal.min.css" />



<!-- JS -->
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/jquery-modal/0.9.1/jquery.modal.min.js"></script>

​
<script type="text/javascript">
	$(function() {
		$("#alert-success").hide();
		$("#alert-danger").hide();
		$("input").keyup(function() {
			var owner_password = $("#owner_password").val();
			var owner_password_confirm = $("#owner_password_confirm").val();
			if (pass != "" || owner_password != "") {
				if (owner_password == owner_password_confirm) {
					$("#alert-success").show();
					$("#alert-danger").hide();
					$("#submit").removeAttr("disabled");
				} else {
					$("#alert-success").hide();
					$("#alert-danger").show();
					$("#submit").attr("disabled", "disabled");
				}
			}
		});
	});
	function join_check() {
		var form = document.userInfo;
		if (!form.owner_id.value) {
			alert("아이디를 입력하세요.");
			form.owner_id.focus();
			return false;
		}

		if (!form.owner_pass.value) {
			alert("비밀번호를 입력하세요.");
			form.owner_pass.focus();
			return false;
		}
		if (form.owner_password.value.length<3 || form.owner_password.value.length>12) {
			alert("비밀번호를 3~12자로 입력해주세요.")
			form.owner_password.focus();
			return false;
		}
		// 비밀번호와 비밀번호 확인 입력 값 동일여부 확인
		if (form.owner_password.value != form.owner_password_confirm.value) {
			alert("비밀번호를 동일하게 입력하세요.");
			form.owner_password_confirm.focus();
			return false;
		}
		if (!form.owner_name.value) {
			alert("이름을 입력하세요.");
			form.owner_name.focus();
			return false;
		}

		if (!(form.owner_phone.value.length == 11)) {
			alert("폰번호는 11자리로 입력해야 합니다.")
			form.owner_phone.focus();
			return false;
		}
		if (!form.shop_phone.value) {
			alert("전화번호를 입력하세요.");
			form.phonenum.focus();
			return false;
		}
		if (isNaN(form.owner_phone.value)) {
			alert("전화번호는 - 제외한 숫자만 입력해주세요.");
			form.phonenum.focus();
			return false;
		}
	}

	$(document).ready(function() {
		//id 중복체크
		$("#owner_id").on("keyup", function() {
			$.get("/connectedcar/ownerlogin/idCheck.do", {"owner_id":$("#owner_id").val()},
					function(data) {
				$("#checkVal").text(data);
			}, "text")

		});

		//핸드폰 번호 수
		$("#owner_phone").on("keyup", function() {
			myphone = $("#owner_phone").val();
			resultStr = "";//결과 문자열을 저장할 변수
			colour = "";
			if (myphone.length != 11) {
				resultStr = "핸드폰 번호는 11자리로 입력해야 합니다.";
				colour = "red";
			} else {
				resultStr = "핸드폰 번호 입력완료.";
				colour = "green";
			}
			//웹페이지에 div태그 내부에 문자열을 추가 -> html()을 이용해도 좋다.
			$("#result_phone").text(resultStr);
			$("#result_phone").css("color", colour);
		});

	});
</script>
​
</head>
​
<body>
	​
	<div id="outter">
		​ ​
		<div class="container mtb">
			<form action="/connectedcar/ownerlogin/join.do" name="userInfo"
				method="POST" onsubmit="return join_check()">
				<div class="join_form">
					<label for="id">아이디 :</label> <input type="text"
						class="form-control" id="owner_id" name="owner_id"
						placeholder="Enter id"> <span id="checkVal"
						style="color: red;"></span>
				</div>

				<div class="join_form">
					<label for="pwd">비밀번호 :</label> <input type="password"
						class="form-control" id="owner_password" name="owner_password"
						placeholder="최소3자 이상 최대 12자 이하"> ​
				</div>
				<div class="join_form">
					<label for="pwd">비밀번호 확인 :</label> <input type="password"
						class="form-control" id="owner_password_confirm"
						name="owner_password_confirm" placeholder="Enter password">
					<div class="alert alert-success" id="alert-success">비밀번호가
						일치합니다.</div>
					<div class="alert alert-danger" id="alert-danger">비밀번호가 일치하지
						않습니다.</div>
				</div>

				<div class="join_form">
					<label for="pwd">이름 :</label> <input type="text"
						class="form-control" id=owner_name name="owner_name"
						placeholder="name">
				</div>

				<div class="join_form">
					<label for="pwd"> 핸드폰 번호 : ( - 없이 11자리 숫자만 입력 )</label> <input
						type="text" class="form-control" id="owner_phone"
						name="owner_phone" placeholder="예시 : 01012345678">
					<div id="result_phone" style="color: red;"></div>
				</div>

				<!-- =================================정비소 정보 입력 시작 =============================================-->

				<span id="inputshoptxt" style="font-weight: bold;">정비소 정보 입력
					: </span> <span id="add_shop_info_btn" class="btn btn-default"
					onclick="newPop()">내 정비소 찾기</span> <br> <br>
				<br>

				<div class="join_form">
					<label for="pwd">정비소코드 :</label> <input type="text"
						class="form-control" id="shop_id" name="shop_id"
						placeholder="정비소 찾기 창을 이용해주세요." readonly>
				</div>
				<div class="join_form">
					<label for="pwd">정비소이름 :</label> <input type="text"
						class="form-control" id="shop_name"
						placeholder="정비소 찾기 창을 이용해주세요." readonly>
				</div>

				<div class="join_form">
					<label for="pwd">정비소 위치 :</label> <input type="text"
						class="form-control" id="shop_location"
						placeholder="정비소 찾기 창을 이용해주세요." readonly>
				</div>

				<div class="join_form">
					<label for="pwd">정비소 전화번호 :</label> <input type="text"
						class="form-control" id="shop_phone"
						placeholder="정비소 찾기 창을 이용해주세요." readonly>
				</div>






				<div class="join_form">
					<button type="submit" class="btn btn-default" id="submit">Submit</button>
				</div>
			</form>
		</div>
		​ ​
	</div>

	​
</body>
<script type="text/javascript">
	//정비소 찾기 팝업 열어주는 함수.
	function newPop() {
		window.open('joinshoppopup.do', '_blank', 'width=1000px,height=700px');
	}
</script>


</html>