<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
				<a class="navbar-brand text-light m-0 me-3 ms-2"
					data-bs-toggle="offcanvas" href="#offcanvasExample" role="button"
					aria-controls="offcanvasExample"><img
					src='../images/menu-lines.svg' class='card-img-top' alt='lines'
					height='25px' style="filter:invert(1)"></a> 
				<a class="navbar-brand text-light m-1"
					href="home"><img src='../images/navbar.png'
					class='card-img-top' alt='book_image' height='50px'></a> <span
					class="text-light fw-semibold h4 m-1">UNIVERSITY</span>

				<button class="navbar-toggler" type="button"
					data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent"
					aria-controls="navbarSupportedContent" aria-expanded="false"
					aria-label="Toggle navigation">
					<span class="navbar-toggler-icon"></span>
				</button>
				<div class="collapse d-flex justify-content-end navbar-collapse"
					id="navbarSupportedContent">

					<form action="/logout" method="post" class="">
						<input type="submit" class="btn btn-warning" value="Sign Out" />
						<input type="hidden" name="${_csrf.parameterName}"
							value="${_csrf.token}" />
					</form>
				</div>

			</div>
		</nav>
		<div class="offcanvas offcanvas-start" tabindex="-1"
			id="offcanvasExample" aria-labelledby="offcanvasExampleLabel">
			<div class="offcanvas-header">
				<h5 class="offcanvas-title" id="offcanvasExampleLabel">Menu</h5>
				<button type="button" class="btn-close" data-bs-dismiss="offcanvas"
					aria-label="Close"></button>
			</div>
			<div class="offcanvas-body">
				<ul class="navbar-nav me-auto mb-2 mb-lg-0">
					<li class="nav-item "><a
						class="nav-link text-dark fw-semibold" type="button"
						href="cabinet">My cabinet</a></li>
					<li class="nav-item"><a
						class="nav-link text-dark fw-semibold" type="button"
						data-bs-dismiss="offcanvasExample" 
						href="faculties">Faculties</a></li>
					<li class="nav-item "><a
						class="nav-link text-dark fw-semibold" type="button"
						href="subjects">Subjects</a></li>
					<li class="nav-item "><a
						class="nav-link text-dark myapplication fw-semibold" type="button"
						href="reports">All Reports</a></li>
				</ul>
			</div>
		</div>
	</header>

</body>
</html>