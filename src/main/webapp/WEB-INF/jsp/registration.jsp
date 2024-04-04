<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Registration page</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css"
	rel="stylesheet">
<link href="https://getbootstrap.com/docs/5.3/assets/css/docs.css"
	rel="stylesheet">
<link rel="stylesheet" href="css/app.css">
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
<script src="https://code.jquery.com/jquery-3.7.0.min.js"></script>
</head>
<body>
	<div class="container d-flex flex-column align-items-center">
		<h1 class="m-3">Registrarion page</h1>

		<form name='f' action="registration" method='POST'>
			<div class="mb-3">
				<label for="inputFirstName" class="form-label">First name</label> <input
					type="text" class="form-control" id="inputFirstName" name="firstname">				
			</div>
			<div class="mb-3">
				<label for="inputLastName" class="form-label">Last name</label> <input
					type="text" class="form-control" id="inputLastName"	 name="lastname">				
			</div>			
			<div class="mb-3">
				<label for="inputEmail" class="form-label">Email address</label> <input
					type="text" class="form-control" id="inputEmail"
					aria-describedby="emailHelp" name="email">				
			</div>
			<div class="mb-3">
				<label for="inputPassword" class="form-label">Password</label> <input
					type="password" class="form-control" id="inputPassword"
					name="password">
			</div>			
			<button name="submit" type="submit" value="submit"
				class="btn btn-primary">Submit</button>
		</form>
		<a href="login" type="button" class="btn btn-light  m-5">Already registered? Go to login...</a>
	</div>
</body>
</html>