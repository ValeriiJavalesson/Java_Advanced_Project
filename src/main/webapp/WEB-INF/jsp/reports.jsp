<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Reports</title>
<link rel="stylesheet" href="../css/faculties.css">
</head>
<body>
	<jsp:include page="header.jsp"></jsp:include>
	<div
		class="container d-flex flex-column align-items-center page-content">
		<div class="align-items-center container-fluid d-flex justify-content-center flex-wrap">
				<c:forEach var="report" items="${reports}">
					<div class="align-items-center card m-3 shadow-lg bg-lignt border-black border-2 p-3 overflow-hidden" 
						style="width: 20rem; height: 20rem;" id="${report.id}" >
						<div>
							<button type="button" name="${report.faculty.name}"
										class="btn-close end-0  ms-1 me-3 position-absolute close_button" aria-label="Close"
										data-bs-toggle="modal" data-bs-target="#exampleModal"
										onclick="removeReport(this)" id="${report.id}"></button>
							<button type="button" name="${report.faculty.name}" facultyid="${report.faculty.id}"
										class="btn end-0  ms-1 me-1 mt-3 position-absolute edit_button" value='${report.faculty.name}'
										onclick='addReport(this)' id="${report.id}"><img alt="✎" src="../images/edit-pen.svg"></button>
						</div>
						<div class="card-body w-100" id = "${report.id}">
							<p class="align-items-center d-flex h-50 h3 justify-content-center text-center facultyName" onclick='showResult(this)' role="button" id="${report.id}">${report.faculty.name}</p>
							<p class="text-black h4">Number of applications: <span class="text-danger h3">${report.applications.size()}</span></p>
						</div>
					</div>
				</c:forEach>
				<div class="align-items-center card m-3 shadow-lg bg-primary-subtle border-black border-2"
						style="width: 20rem; height: 20rem; cursor: pointer;" onclick='addReport()' allfaculties='${allfaculties}' id="addNewReport">
						<div class="card-body align-content-center">
							<div class="align-items-center d-flex flex-column">
								<p class="card-title h5">Create new</p>
								<p class="card-title h5 mb-0">report</p>
								<p class="card-title text-black h1">+</p>
							</div>
						</div>
				</div>
		</div>
	</div>
	<div class="container d-flex flex-column align-items-center table-content d-none">
		<table class='table bg-light table-group-divider table-bordered'>
			<thead>
				<tr>
					<th scope='col' class="text-center col-2">Add to report</th>
					<th scope='col' class="text-center col-4">Firstname</th>
					<th scope='col' class="text-center col-4">Lastname</th>
					<th scope='col' class="text-center col-2">Total points</th>
				</tr>
			</thead>
			<tbody>
			</tbody>
		</table>
	</div>
	<div class="d-none buttons-group d-flex justify-content-center">
		<div class="input-group me-4 w-25">
			 <span	class="input-group-text">Number of applicants:</span> 
			 <input type="number" class="form-control" id='numberOfStudents' aria-label="Number of applicants:" onChange="viewButtons()" onkeyup='viewButtons()'>
		</div>
		<button type="button" class="btn btn-primary me-2 disabled saveButton"
			onclick="saveReport()">Save</button>
		<a type="button" class="btn btn-warning ms-2" href="reports">Back</a>
	</div>

	<div class="modal fade" id="exampleModal" tabindex="-1"
		aria-labelledby="exampleModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header justify-content-center">
					<h1 class="modal-title fs-5" id="exampleModalLabel">Are you
						sure to delete the report?</h1>
					
				</div>
				<input type='text' id='reportNametoDelete' hidden="true">
				<div class="justify-content-center modal-footer">
					<button type="button" class="btn btn-primary" data-bs-dismiss="modal" onclick="deleteReport()">Yes</button>
					<button type="button" class="btn btn-warning" data-bs-dismiss="modal" aria-label="Close">No</button>
				</div>
			</div>
		</div>
	</div>
	

	<script src="../js/reports.js"></script>
</body>
</html>