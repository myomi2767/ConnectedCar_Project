<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>admin expendable Add</title>
</head>
<body>
	<div class="container">
		<div class="col-lg-8 col-lg-offset-2">
			<form id="add" role="form-horizontal" action="/connectedcar/admin/adminexpendableAdd.do" method="POST">
				<div class="form-group">
					<label class="col-2 control-label" for="code">부품코드 :</label>
					<input type="text" class="form-control col-10" name="expend_code">
				</div>
				<div class="form-group">
					<label class="col-2 control-label" for="expend_type">부품종류 :</label>
					<input type="text" class="form-control col-10" name="expend_type">
				</div>
				<div class="form-group">
					<label class="col-2 control-label" for="expend_name">부품명 :</label>
					<input type="text" class="form-control col-10" name="expend_name">
				</div>
				<div class="form-group">
					<label class="col-2 control-label" for="expend_price">부품가격 :</label>
					<input type="text" class="form-control col-10" name="expend_price">
				</div>
				<div class="form-group">
					<label class="col-2 control-label" for="expend_brand">부품브랜드 :</label>
					<input type="text" class="form-control col-10" name="expend_brand" value="현대모비스">
				</div>
				<div class="form-group">
					<label class="col-2 control-label" for="car_model_name">부품 자동차 모델 :</label>
					<input type="text" class="form-control col-10" name="car_model_name">
				</div>
				<button type="submit" id="submit_btn" class="btn btn-theme">추가</button>
				<button type="reset" class="btn btn-theme">취소</button>
			</form>
		</div>
	</div>
</body>
<script>
    $("#submit_btn").click(function(){
    	//팝업 닫으면서 부모창 reload하는 법
	  	opener.parent.location.reload();
    	//팝업 닫기
    	self.close();
    });
</script>
</html>