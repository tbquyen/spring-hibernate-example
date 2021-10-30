<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="s"%>
<c:set scope="request" value="<%=request.getContextPath()%>"
	var="ROOT_URL"></c:set>

<s:authorize access="isAuthenticated()">
	<label><s:authentication property="principal.username" /></label>
	|
	<s:authorize url="/users">
		<a href="${ROOT_URL}/users">List Users</a>
		|
	</s:authorize>
	<s:authorize url="/categories">
		<a href="${ROOT_URL}/categories">List Categories</a>
		|
	</s:authorize>
	<s:authorize url="/questions">
		<a href="${ROOT_URL}/questions">List Questions</a>
		|
	</s:authorize>
	<s:authorize url="/quiz">
		<a href="${ROOT_URL}/quiz">Quiz Engine</a>
		|
	</s:authorize>
	<s:authorize url="/quizreport">
		<a href="${ROOT_URL}/quizreport">Quiz Report</a>
		|
	</s:authorize>
	<s:authorize url="/changePassword">
		<a href="${ROOT_URL}/changePassword">ChangePassword</a>
		|
	</s:authorize>
	<s:authorize url="/logout">
		<a href="${ROOT_URL}/logout">Logout</a>
	</s:authorize>
</s:authorize>
<s:authorize access="!isAuthenticated()">
	<a href="login">Login</a>
	|
	<a href="changePassword">ChangePassword</a>
</s:authorize>
<c:if test="${successMessage != null && successMessage != ''}">
	<br>
	<span style="color: green;">${successMessage}</span>
</c:if>
<c:if test="${errorMessage != null && errorMessage != ''}">
	<br>
	<span style="color: red;">${errorMessage}</span>
</c:if>
<c:if test="${warningMessage != null && warningMessage != ''}">
	<br>
	<span style="color: yellow;">${warningMessage}</span>
</c:if>
