<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
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
<link rel="stylesheet" href="css/registration.css">
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
<script src="https://code.jquery.com/jquery-3.7.0.min.js"></script>
<script src="js/registration.js"></script>
</head>
<body>
	<div class="container d-flex flex-column align-items-center">
		<h1 class="m-3">Registrarion page</h1>

		<form:form method='POST' action="registration"
			modelAttribute="userForm" enctype="multipart/form-data" id="userform" class="container">
			<div class="justify-content-center row">
				<div class="m-3 col-4 p-0" id = "user_photo">
						<img id='photo' src='../images/user_profile_background.svg' class='bg-gradient' alt='User`s Photo'>
						<input type='file' name='file' onchange='savePreviewPhoto(this)' hidden='true'>
						<div class='edit_photo' id='edit_photo' onclick='editUserPhoto()'>Add photo ✎</div>
				</div>
				<div class="m-3 col-5">
					<div class="mb-3">						
						<div class="d-flex justify-content-between">
							<label for="inputFirstName" class="form-label">First name</label>
							<div class="errorMessage text-danger" hidden='true'>First name must contain at least 3 characters</div>
						</div>	
						<input type="text" class="form-control" id="inputFirstName" name="firstname">			
					</div>
					<div class="mb-3">
						<div class="d-flex justify-content-between">
							<label for="inputLastName" class="form-label">Last name</label> 							
							<div class="errorMessage text-danger" hidden='true'>Last name must contain at least 3 characters</div>
						</div>	
						<input type="text" class="form-control" id="inputLastName"	name="lastname">
					</div>
					<div class="mb-3">
					<div class="d-flex justify-content-between">
							<label for="inputEmail" class="form-label">Email address</label> 							
							<div class="errorMessage text-danger" hidden='true'>Invalid email</div>
							<div class="isPresent text-danger" hidden='true'>Email is already registered</div>
						</div>
						<input type="text" class="form-control" id="inputEmail"	aria-describedby="emailHelp" name="email">
					</div>
					<div class="mb-3">
						<div class="d-flex justify-content-between">
							<label for="inputPassword" class="form-label">Password</label> 							
							<div class="errorMessage text-danger" hidden='true'>Password must contain at least 6 characters</div>
						</div>
						<input type="password" class="form-control" id="inputPassword" name="password">
					</div>
					<button name="submit" type="button" value="submit"
						class="btn btn-primary" onclick='checkForm()'>Submit</button>
				</div>
			</div>

		</form:form>
		<a href="login" type="button" class="btn btn-light  m-5">Already
			registered? Go to login...</a>
	</div>
</body>
</html>