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
	<f:form action="${ROOT_URL}/quiz" modelAttribute="QuizForm"
		method="POST">
		<table>
			<thead>
				<tr>
					<td><h1>Learning Quiz Engine</h1></td>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td>Select number of questions</td>
					<td><f:select path="numberofquestions">
							<f:option value="5">5</f:option>
							<f:option value="10">10</f:option>
						</f:select></td>
				</tr>
				<tr>
					<td>Select categories</td>
					<td><f:select path="categoryId">
							<f:option value="0">Random</f:option>
							<f:options items="${categories}" itemValue="id" itemLabel="name" />
						</f:select></td>
				</tr>
			</tbody>
			<tfoot>
				<tr>
					<th></th>
					<td><input type="submit" value="START"></td>
				</tr>
			</tfoot>
		</table>
	</f:form>
	<jsp:include page="footer.jsp" />
</body>
</html>