<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>403 page</title>
</head>
<body>
	<jsp:include page="../../header.jsp"></jsp:include>
	<div class="align-items-center container d-flex flex-column mt-4">
		<span class="text-center h2">Sorry, you do not have permission to view this page</span>
		<a type="button" class="border-dark-subtle btn btn-light m-3 shadow" href="home">Go home</a>
	</div>
</body>
</html>