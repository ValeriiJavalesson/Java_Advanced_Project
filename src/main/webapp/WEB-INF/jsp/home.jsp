<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
<head>
<title><spring:message code='home.title'/></title>

</head>
<body>
	<jsp:include page="header.jsp"></jsp:include>
	<div
		class="container d-flex flex-column align-items-center applicationform">
		<h1 class="m-3"><spring:message code='home.greeting'/></h1>
	</div>
</body>
</html>