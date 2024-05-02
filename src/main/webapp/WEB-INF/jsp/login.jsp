<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Login page</title>
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
		<h1 class="m-3">Login page</h1>

		<form name='f' action="login" method='POST'>
			<div class="mb-3">
				<label for="inputEmail" class="form-label">Email address</label> <input
					type="text" class="form-control" id="inputEmail"
					aria-describedby="emailHelp" name="username">
			</div>
			<div class="mb-3">
				<label for="inputPassword" class="form-label">Password</label> <input
					type="password" class="form-control" id="inputPassword"
					name="password">
			</div>
			<button name="submit" type="submit" value="submit"
				class="btn btn-primary">Submit</button>
		</form>
		<div id="alert_message">
			<c:if test="${param.error ne null}">
				<div class="alert alert-warning m-2">Invalid username and password.</div>
			</c:if>
			<c:if test="${param.logout ne null}">
				<div class="alert alert-info m-2">You have
					been logged out.</div>
			</c:if>
			<c:if test="${param.registered ne null}">
				<div class="alert alert-success m-2">You have
					been successfully registered.</div>
			</c:if>


		</div>
		<a href="registration" type="button" class="btn btn-light m-5">Don't have account? Go to registration</a>

	</div>
	<script type="text/javascript" src="js/login.js"></script>

</body>
</html>