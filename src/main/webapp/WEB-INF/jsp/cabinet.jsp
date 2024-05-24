<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
<head>
<title><spring:message code='cabinet.title'/></title>
</head>
<body>
	<jsp:include page="header.jsp"></jsp:include>
	<div>
		<c:set var="user" value="${current_user}" />
	</div>
	<div>
		<input id="cabinet_faculty" type="hidden" value="<spring:message code="cabinet.faculty"/>"/>
		<input id="cabinet_subject" type="hidden" value="<spring:message code="cabinet.subject"/>"/>
		<input id="cabinet_certificate" type="hidden" value="<spring:message code="cabinet.certificate"/>"/>
		<input id="cabinet_points" type="hidden" value="<spring:message code="cabinet.points"/>"/>
		<input id="cabinet_create" type="hidden" value="<spring:message code="cabinet.create"/>"/>
		<input id="cabinet_edit" type="hidden" value="<spring:message code="cabinet.edit"/>"/>
		<input id="cabinet_save" type="hidden" value="<spring:message code="cabinet.save"/>"/>
		<input id="cabinet_cancel" type="hidden" value="<spring:message code="cabinet.cancel"/>"/>
		<input id="cabinet_noapplication" type="hidden" value="<spring:message code="cabinet.noapplication"/>"/>
		<input id="cabinet_delete" type="hidden" value="<spring:message code="cabinet.delete"/>"/>
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
			<div class='edit_photo' id='edit_photo' onclick='editPhoto()'><spring:message code="cabinet.photo"/>✎</div>
		</div>

		<div class="userInfo mb-3">${user.firstname}	${user.lastname}</div>
		<div class="applicationform align-items-center d-flex flex-column"></div>
	</div>

		<div class="modal fade" id="exampleModal" tabindex="-1"
			aria-labelledby="exampleModalLabel" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header justify-content-center">
						<h1 class="modal-title fs-5" id="exampleModalLabel"><spring:message code='cabinet.delete.confirm'/></h1>
						
					</div>
					<input type='text' id='facultyNametoDelete' hidden="true">
					<div class="justify-content-center modal-footer">
						<button type="button" class="btn btn-primary" data-bs-dismiss="modal" onclick="deleteApplication()"><spring:message code='cabinet.delete.confirm.yes'/></button>
						<button type="button" class="btn btn-warning" data-bs-dismiss="modal" aria-label="Close"><spring:message code='cabinet.delete.confirm.no'/></button>
					</div>
				</div>
			</div>
		</div>

	<script src="../js/cabinet.js"></script>
</body>
</html>