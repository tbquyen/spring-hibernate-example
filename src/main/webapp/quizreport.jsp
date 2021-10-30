<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="f"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Quiz Report</title>
</head>
<body>
	<jsp:include page="header.jsp" />
	<br>
	<h1>QUIZ REPOR</h1>
	<table>
		<thead>
			<tr>
				<th>#</th>
				<th>User name</th>
				<th>Category</th>
				<th>Start</th>
				<th>End</th>
				<th>passed</th>
				<th>questions</th>
				<th>Duration</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${quizs}" var="quiz" varStatus="status">
				<tr>
					<td>${status.index + 1}</td>
					<td>${quiz.user.username}</td>
					<c:if test="${quiz.category == null}">
						<td>Random</td>
					</c:if>
					<c:if test="${quiz.category != null}">
						<td>${quiz.category.name}</td>
					</c:if>
					<td><fmt:formatDate value="${quiz.timeStart}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
					<td><fmt:formatDate value="${quiz.timeEnd}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
					<td>${quiz.passed}</td>
					<td>${quiz.questions}</td>
					<td>${quiz.duration}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<jsp:include page="footer.jsp" />
</body>
</html>