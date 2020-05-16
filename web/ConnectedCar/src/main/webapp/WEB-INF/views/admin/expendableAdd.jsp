<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h3>부품 추가</h3>
	<div class="hline"></div>
	<form role="form">
		<div class="form-group">
			<label for="InputName1">제조사</label> 
			<input type="radio" value="현대">현대
			<input type="radio" value="기아">기아
		</div>
		<div class="form-group">
			<label for="InputName1">부품코드</label> 
			<select name="">
				<option value="">1</option>
				<option value="">2</option>
				<option value="">3</option>
				<option value="">4</option>
			</select>
		</div>
		
		
		<div class="form-group">
			<label for="InputName1">부품명 </label> 
			<input type="text" disabled="disabled" value="불러온값">
		</div>
		<div class="form-group">
			<label for="InputName1">차량모델</label> 
			<input type="text" disabled="disabled" value="불러온값">
		</div>
		<div class="form-group">
		    <label for="InputSubject1">개수</label>
		    <input type="number" class="form-control" id="">
		</div>
		<button type="submit" class="btn btn-theme">추가</button>
		<button type="reset" class="btn btn-theme">취소</button>
	</form>
</body>
</html>