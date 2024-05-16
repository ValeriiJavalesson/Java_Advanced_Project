<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html>
<html>
<head>
<title>Cabinet</title>
</head>
<body>
	<jsp:include page="header.jsp"></jsp:include>
	<div>
		<c:set var="user" value="${current_user}" />
	</div>
	<div
		class="container d-flex flex-column align-items-center">

		<div class='mb-3' id='user_photo'>
			<c:if test="${user.encodedImage eq ''}">
	  			<img id='photo' src='../images/user_profile_background.svg' class='bg-gradient' alt='User`s Photo'>
			</c:if>
			<c:if test="${user.encodedImage ne ''}">
	  			<img id='photo' src="data:image/jpeg;base64, ${user.encodedImage}" class='bg-gradient' alt='User`s Photo'> 
			</c:if>		
			<input type='file' name='file' onchange='savePhoto()' hidden='true'>	
			<div class='edit_photo' id='edit_photo' onclick='editPhoto()'>Edit photo✎</div>
		</div>

		<div class="userInfo mb-3">${user.firstname}	${user.lastname}</div>
		<div class="applicationform align-items-center d-flex flex-column"></div>
	</div>
	<security:authorize access="hasRole('ROLE_ADMIN')">		
		<div class="modal fade" id="exampleModal" tabindex="-1"
			aria-labelledby="exampleModalLabel" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header justify-content-center">
						<h1 class="modal-title fs-5" id="exampleModalLabel">Are you
							sure to delete your application?</h1>
						
					</div>
					<input type='text' id='facultyNametoDelete' hidden="true">
					<div class="justify-content-center modal-footer">
						<button type="button" class="btn btn-primary" data-bs-dismiss="modal" onclick="deleteApplication()">Yes</button>
						<button type="button" class="btn btn-warning" data-bs-dismiss="modal" aria-label="Close">No</button>
					</div>
				</div>
			</div>
		</div>
	</security:authorize>
	<script src="../js/cabinet.js"></script>
</body>
</html>