<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>

<html lang="en">
<head>

<link rel="stylesheet" href="../css/locales.css">
</head>
<div class="align-items-end container d-flex justify-content-end dropdown">
	<a class="btn p-0" href="${location.href}?lang=en"><img id=locales_flags class="rounded-1" alt="<spring:message code="login.english"/>" src="../images/en.png"></a> 
	<a class="btn p-0" href="${location.href}?lang=uk"><img id=locales_flags class="rounded-1"  alt="<spring:message code="login.ukrainian"/>" src="../images/uk.png"></a>
</div>


</html>

