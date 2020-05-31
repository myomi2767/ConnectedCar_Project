<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<div class="container">
		<div class="col-lg-8 col-lg-offset-2">
			<h4>부품 추가</h4>
			<form id="shop_expend_form" role="form-horizontal" action="/connectedcar/expendable/insertExpend.do" method="post">
				<input type="text" name="expend_id" value="" style=" display: none;"/>
				<label for="">제조사 : </label>
				<div class="btn-group btn-group-toggle" data-toggle="buttons" style="">
					<label class="btn btn-secondary" for="model1">
						<input type="radio" name="expend_brand" id="" value="현대">현대
					</label> 
					<label class="btn btn-secondary" for="model1">
						<input type="radio" name="expend_brand" id="" value="기아">기아
					</label>
				</div>
				<div class="form-group">
					<label class="col-2 control-label" for="code">부품코드 :</label>
					<input id="findExpend" name="expend_code" type="text" class="form-control col-10" placeholder="부품 코드를 입력하세요">
				</div>
				<!-- 검색버튼을 눌렀을 때 나와야할 화면 들 표현식을 이용한 화면 구성 -->
				<%  %>
				<div class="form-group">
					<label for="InputName1">부품명 :</label>
					<input name="expend_name" type="text" class="form-control col-10" disabled="disabled" value="불러온값">
				</div>
				<div class="form-group">
					<label for="InputName1">부품종류 :</label>
					<input name="expend_type" type="text" class="form-control col-10" disabled="disabled" value="불러온값">
				</div>
				<div class="form-group">
					<label for="InputName1">부품가격 :</label>
					<input name="expend_price" type="text" class="form-control col-10" disabled="disabled" value="불러온값">
				</div>
				<div class="form-group">
					<label for="InputName1">부품 자동차 모델 :</label>
					<input name="car_model_name" type="text" class="form-control col-10" disabled="disabled" value="불러온값">
				</div>
				<div class="form-group">
				    <label for="InputSubject1">개수 :</label>
				    <input name="shop_expend_count" type="number" class="form-control col-10" id="">
				</div>
				
				<button id="btn_submit" type="submit" class="btn btn-theme" onclick="">추가</button>
				<button type="reset" class="btn btn-theme" onclick="self.close();">취소</button>
			</form>
			<%-- <% int cnt = (int)request.getAttribute("count"); % --%>
		</div>
	</div>
	<script type="text/javascript">
		$("#btn_submit").on("click", function() {
			$("#shop_expend_form").submit();
			opener.parent.location.reload();
		});
		$(document).ready(function() {
			setExpendDetail();
		});
		
		function setExpendDetail() {
			$("#findExpend").keyup(function() {
				var brand = $("input[name=expend_brand]:checked").val();
				var code = $("input[name=expend_code]").val();
				$.ajax({
					url:"/connectedcar/expendable/findExpend.do",
					type:"post",
					data: {
						"expend_code": code,
						"expend_brand": brand
					},
					success: function(data) {
						if(data.length == undefined) {
							//alert(data.expend_type);
							$("input[name=expend_id]").val(data.expend_id);
							$("input[name=expend_name]").val(data.expend_name);
							$("input[name=expend_type]").val(data.expend_type);
							$("input[name=expend_price]").val(data.expend_price);
							$("input[name=car_model_name]").val(data.car_model_name);
						} 
						
					}
				});
			});
		}
	</script>
</body>
</html>