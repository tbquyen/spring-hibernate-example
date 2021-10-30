<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="f"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Quiz Engine</title>
</head>
<body>
	<jsp:include page="header.jsp" />
	<br>
	<br>
	<h1>QUIZ FINISH: ${passed}/${total}</h1>
	<jsp:include page="footer.jsp" />
</body>
</html>