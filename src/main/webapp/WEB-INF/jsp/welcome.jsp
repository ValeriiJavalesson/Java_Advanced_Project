<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title><spring:message code='welcome.title'/></title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css"
	rel="stylesheet">
<link href="https://getbootstrap.com/docs/5.3/assets/css/docs.css"
	rel="stylesheet">
<link rel="stylesheet" href="css/registration.css">
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
<script src="https://code.jquery.com/jquery-3.7.0.min.js"></script>
</head>
<body>	
	<div class="container d-flex flex-column align-items-center">
	<jsp:include page="locales.jsp"></jsp:include>
		<h1 class="m-3"><spring:message code='welcome.title'/></h1>
		<a href="login" type="button" class="btn btn-primary m-2"><spring:message code='welcome.login'/></a>
		<a href="registration" type="button" class="btn btn-primary m-2"><spring:message code='welcome.registration'/></a>
	</div>
	
</body>
</html>