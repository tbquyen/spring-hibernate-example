<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="f"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no" />
<title>LOGIN</title>
</head>
<body>
	<f:form action="login" modelAttribute="LoginForm">
		<h3>LOGIN</h3>
		<label>Username&nbsp;<f:input path="username"
				autofocus="autofocus" /></label>
		<f:errors path="username" />
		<br>
		<br>
		<label>Password&nbsp;&nbsp;<f:password path="password" /></label>
		<f:errors path="password" />
		<f:errors />
		<br>
		<label><f:checkbox path="rememberme" value="on"/>Remember me</label>
		<br>
		<br>
		<input type="submit" value="LOGIN">
	</f:form>
</body>
</html>