<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Faculties</title>
<link rel="stylesheet" href="../css/faculties.css">
</head>
<body>
	<jsp:include page="header.jsp"></jsp:include>
	<div
		class="container d-flex flex-column align-items-center page-content">
		<div class="align-items-center container-fluid d-flex justify-content-center flex-wrap">
				<c:forEach var="faculty" items="${faculties}">
					<div class="align-items-center card m-3 shadow-lg bg-lignt border-black border-2 p-3 overflow-hidden"
						style="width: 20rem; height: 20rem;" id="${faculty.id}">
						<security:authorize access="hasRole('ROLE_ADMIN')">							
							<div>
								<button type="button" name="${faculty.name}"
											class="btn-close end-0  ms-1 me-3 position-absolute close_button" aria-label="Close"
											data-bs-toggle="modal" data-bs-target="#exampleModal"
											onclick="removeFaculty(this)" id="${faculty.id}"></button>
								<button type="button" name="${faculty.name}"
											class="btn end-0  ms-1 me-1 mt-3 position-absolute edit_button"
											onclick="addFaculty(this)" id="${faculty.id}"><img alt="✎" src="../images/edit-pen.svg"></button>
							</div>
						</security:authorize>
						<div class="card-body w-100">
							<div class="mb-2 w-100 align-items-center overflow-hidden pe-2 ps-2" style="height: 8rem;">
								<p class="align-items-center card-title d-flex h-100 h3 justify-content-center text-center" >${faculty.name}</p>
							</div>
							<div class="text-black" style="height: 7rem;">
							<p class="h6">Subjects:</p>
								<ul>
									<c:forEach var="subject" items="${faculty.subjects}">
										<li><p class="card-text" id="${subject.id}">${subject.name}</p></li>
									</c:forEach>
								</ul>
							</div>	
							<security:authorize access="hasRole('ROLE_ADMIN')">		
								<div class="align-items-end d-flex flex-row justify-content-between text-black" style="height: 2rem;">
									<div class="h6">Number of applications:</div>							
									<span class="card-text h2 text-danger" id="numberOfApplication" name="${faculty.name}">0</span>
								</div>	
							</security:authorize>
						</div>
					</div>
				</c:forEach>
				<security:authorize access="hasRole('ROLE_ADMIN')">					
					<div class="align-items-center card m-3 shadow-lg bg-primary-subtle border-black border-2"
							style="width: 20rem; height: 20rem; cursor: pointer;" onclick="addFaculty()">
							<div class="card-body align-content-center">
								<div class="align-items-center d-flex flex-column">
									<p class="card-title h5">Create new</p>
									<p class="card-title h5 mb-0">faculty</p>
									<p class="card-title text-black h1">+</p>
								</div>
							</div>
					</div>
				</security:authorize>
		</div>
	</div>
	
	<div class="modal fade" id="exampleModal" tabindex="-1"
		aria-labelledby="exampleModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header justify-content-center">
					<h1 class="modal-title fs-5" id="exampleModalLabel">Are you
						sure to delete the faculty?</h1>
					
				</div>
				<input type='text' id='facultyNametoDelete' hidden="true">
				<div class="justify-content-center modal-footer">
					<button type="button" class="btn btn-primary" data-bs-dismiss="modal" onclick="deleteFaculty()">Yes</button>
					<button type="button" class="btn btn-warning" data-bs-dismiss="modal" aria-label="Close">No</button>
				</div>
			</div>
		</div>
	</div>

	<script src="../js/faculties.js"></script>
</body>
</html>