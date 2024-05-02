<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>

<head>
<title>Subject</title>
<link rel="stylesheet" href="../css/subjects.css">
</head>
<body>
	<jsp:include page="header.jsp"></jsp:include>
	<div
		class="container d-flex flex-column align-items-center page-content">
		<div
			class="align-items-center container-fluid d-flex justify-content-center flex-wrap">
			<c:forEach var="subject" items="${subjects}">
				<div class="align-items-center card m-3 shadow-lg bg-lignt border-black border-2 overflow-hidden" style="width: 18rem; height: 7rem;" id="${subject.id}">
					<div>
						<button type="button" name="${subject.name}"
							class="btn-close end-0 mt-3 ms-1 me-3 position-absolute close_button"
							aria-label="Close" data-bs-toggle="modal"
							data-bs-target="#exampleModal" onclick="removeSubject(this)"
							id="${subject.id}"></button>
					</div>
					<div class="card-body d-flex flex-column justify-content-center">
						<div
							class="align-items-center overflow-hidden">
							<p class="card-title h5 text-center">${subject.name}</p>
						</div>						
					</div>
				</div>
			</c:forEach>
				<div class="align-items-center card m-3 shadow-lg bg-primary-subtle border-black border-2 overflow-hidden" style="width: 18rem; height: 7rem; cursor: pointer;" onclick="addNewSubject()">
					<div class="card-body d-flex flex-column justify-content-center">
						<div class="align-items-center d-flex flex-column">
							<p class="card-title h5">Create new subject</p>
							<p class="card-title text-black h3">+</p>
						</div>
					</div>
				</div>
		</div>
	</div>
	
	<div class="modal fade" id="exampleModal" tabindex="-1"
		aria-labelledby="exampleModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header justify-content-center">
					<h1 class="modal-title fs-5" id="exampleModalLabel">Are you
						sure to delete the subject?</h1>
					
				</div>
				<input type='text' id='subjectNametoDelete' hidden="true">
				<div class="justify-content-center modal-footer">
					<button type="button" class="btn btn-primary" data-bs-dismiss="modal" onclick="deleteSubject()">Yes</button>
					<button type="button" class="btn btn-warning" data-bs-dismiss="modal" aria-label="Close">No</button>
				</div>
			</div>
		</div>
	</div>

	<script src="../js/subjects.js"></script>
</body>
</html>