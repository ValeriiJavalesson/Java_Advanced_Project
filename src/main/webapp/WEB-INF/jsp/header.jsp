<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title></title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css"
	rel="stylesheet">
<link href="https://getbootstrap.com/docs/5.3/assets/css/docs.css"
	rel="stylesheet">
<link rel="stylesheet" href="../css/app.css">
<link rel="stylesheet" href="../css/header.css">
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
<script src="https://code.jquery.com/jquery-3.7.0.min.js"></script>
<script src="../js/home.js"></script>
</head>
<body>
	<header>
		<nav
			class="navbar navbar-expand-lg bg-secondary-subtle mb-3 border-bottom sticky-top ">
			<div class="container-fluid">
				<a class="navbar-brand text-light m-0" href="home"><img
					src='../images/navbar.png' class='card-img-top' alt='book_image'
					height='60px'></a>
				<button class="navbar-toggler" type="button"
					data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent"
					aria-controls="navbarSupportedContent" aria-expanded="false"
					aria-label="Toggle navigation">
					<span class="navbar-toggler-icon"></span>
				</button>
				<div class="collapse navbar-collapse" id="navbarSupportedContent">
					<ul class="navbar-nav me-auto mb-2 mb-lg-0">
						<li class="nav-item "><a class="nav-link active text-light fw-semibold"
							aria-current="page" href="home">Home</a></li>
						<li class="nav-item "><a
							class="nav-link text-light fw-semibold" type="button"
							id="myapplication" onclick="getUserApplication()">My
								application</a></li>
						<li class="nav-item "><a
							class="nav-link text-light fw-semibold" type="button"
							id="myapplication" onclick="addFaculty()">Add faculty</a></li>
						<li class="nav-item "><a
							class="nav-link text-light fw-semibold" type="button"
							id="myapplication" onclick="addNewSubject()">Add subject</a></li>
						<li class="nav-item "><a
							class="nav-link text-light myapplication fw-semibold" type="button"
							id="myapplication" onclick="getListFacultites()">All students</a></li>
					</ul>
					<form action="/logout" method="post">
						<input type="submit" class="btn btn-warning" value="Sign Out" />
						<input type="hidden" name="${_csrf.parameterName}"
							value="${_csrf.token}" />
					</form>
				</div>
			</div>
		</nav>
	</header>

</body>
</html>