<%@page pageEncoding="UTF-8" isErrorPage="true" isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8" />
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no" />
<!-- Title -->
<title>${errorCode}</title>
<style type="text/css">
p {
	padding: 0px;
	margin: 0px;
}
</style>
</head>
<body>
	<jsp:include page="header.jsp" />
	<div>
		<h1 class="text-danger">${errorCode}</h1>
	</div>
	<div>
		<h4>OPPSSS!!!! Sorry...!</h4>
		<p>${msg}</p>
		<hr>
		<p class="mb-0">Whenever you need to, be sure to use margin
			utilities to keep things nice and tidy.</p>
		<a class="btn btn-danger" href="${ROOT_URL}">Go Back</a>
	</div>
	<c:set var="exception" value="${exception}" />

	<c:if test="${exception != null}">
		<p style="color: red;">
			<c:out value="${exception.message}" />
		</p>
		<div
			style="height: 35vh; width: 100%; overflow: auto; border: 1px solid black;">
			<c:forEach items="${exception.cause.stackTrace}" var="element">
				<p>
					<c:out value="${element}" />
				</p>
			</c:forEach>
		</div>
	</c:if>
	<jsp:include page="footer.jsp" />
</body>
</html>