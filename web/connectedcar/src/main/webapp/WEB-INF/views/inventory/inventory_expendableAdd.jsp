<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
			<form role="form-horizontal">
				<label for="">제조사 : </label>
				<div class="btn-group btn-group-toggle" data-toggle="buttons" style="">
					<label class="btn btn-secondary" for="model1">
						<input type="radio" name="model" id="" value="현대">현대
					</label> 
					<label class="btn btn-secondary" for="model1">
						<input type="radio" name="model" id="" value="기아">기아
					</label>
				</div>
				<div class="form-group">
					<label class="col-2 control-label" for="code">부품코드 :</label>
					<div class="col-9"> 
						<select class="form-control" id="code">
							<option id="code" value="">1</option>
							<option value="">2</option>
							<option value="">3</option>
							<option value="">4</option>
						</select>
					</div>
				</div>
				<!-- 검색버튼을 눌렀을 때 나와야할 화면 들 표현식을 이용한 화면 구성 -->
				<%  %>
				<div class="form-group">
					<label for="InputName1">부품명 :</label>
					<input type="text" class="form-control col-10" disabled="disabled" value="불러온값">
				</div>
				<div class="form-group">
					<label for="InputName1">부품종류 :</label>
					<input type="text" class="form-control col-10" disabled="disabled" value="불러온값">
				</div>
				<div class="form-group">
					<label for="InputName1">부품가격 :</label>
					<input type="text" class="form-control col-10" disabled="disabled" value="불러온값">
				</div>
				<div class="form-group">
					<label for="InputName1">부품 자동차 모델 :</label>
					<input type="text" class="form-control col-10" disabled="disabled" value="불러온값">
				</div>
				<div class="form-group">
				    <label for="InputSubject1">개수 :</label>
				    <input type="number" class="form-control col-10" id="">
				</div>
				
				<button type="submit" class="btn btn-theme">추가</button>
				<button type="reset" class="btn btn-theme">취소</button>
			</form>
		</div>
	</div>
	<script type="text/javascript">
	</script>
</body>
</html>