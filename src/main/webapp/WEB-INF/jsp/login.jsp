<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html lang="uk">
<head>
<meta charset="UTF-8">
<title><spring:message code='login.title'/></title>
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
		<h1 class="m-3"><spring:message code='login.title'/></h1>

		<form name='f' action="login" method='POST'>
			<div class="mb-3">
				<label for="inputEmail" class="form-label"><spring:message code='registration.email'/></label> <input
					type="text" class="form-control" id="inputEmail"
					aria-describedby="emailHelp" name="username">
			</div>
			<div class="mb-3">
				<label for="inputPassword" class="form-label"><spring:message code='registration.password'/></label> <input
					type="password" class="form-control" id="inputPassword"
					name="password">
			</div>
			<button name="submit" type="submit" value="submit"
				class="btn btn-primary"><spring:message code='login.submit'/></button>
		</form>
		<div id="alert_message">
			<c:if test="${param.error ne null}">
				<div class="alert alert-warning m-2"><spring:message code='registration.invalid'/></div>
			</c:if>
			<c:if test="${param.logout ne null}">
				<div class="alert alert-info m-2"><spring:message code='login.logout'/></div>
			</c:if>
			<c:if test="${param.registered ne null}">
				<div class="alert alert-success m-2"><spring:message code='login.registered'/></div>
			</c:if>


		</div>
		<a href="registration" type="button" class="btn btn-light m-5"><spring:message code='login.registration'/></a>

	</div>
	<script type="text/javascript" src="js/login.js"></script>

</body>
</html>