<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>

<head>
<title><spring:message code='subjects.title'/></title>
<link rel="stylesheet" href="../css/subjects.css">
</head>
<body>
	<jsp:include page="header.jsp"></jsp:include>
	<div>
		<input id="subjects_name" type="hidden" value="<spring:message code="subjects.name"/>"/>
		<input id="subjects_save" type="hidden" value="<spring:message code="subjects.save"/>"/>
		<input id="subjects_cancel" type="hidden" value="<spring:message code="subjects.cancel"/>"/>
		<input id="subjects_exists" type="hidden" value="<spring:message code="subjects.exists"/>"/>
		<input id="subjects_error" type="hidden" value="<spring:message code="subjects.error"/>"/>
		<input id="subjects_notfound" type="hidden" value="<spring:message code="subjects.notfound"/>"/>
		<input id="subjects_check" type="hidden" value="<spring:message code="subjects.check"/>"/>
	</div>	
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
							<p class="card-title h5"><spring:message code='subjects.create'/></p>
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
					<h1 class="modal-title fs-5" id="exampleModalLabel"><spring:message code='subjects.delete.confirm'/></h1>
					
				</div>
				<input type='text' id='subjectNametoDelete' hidden="true">
				<div class="justify-content-center modal-footer">
					<button type="button" class="btn btn-primary" data-bs-dismiss="modal" onclick="deleteSubject()"><spring:message code='subjects.delete.confirm.yes'/></button>
					<button type="button" class="btn btn-warning" data-bs-dismiss="modal" aria-label="Close"><spring:message code='subjects.delete.confirm.no'/></button>
				</div>
			</div>
		</div>
	</div>

	<script src="../js/subjects.js"></script>
</body>
</html>