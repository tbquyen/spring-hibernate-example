<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="f"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Categories</title>
</head>
<body>
	<jsp:include page="header.jsp" />
	<f:form action="${ROOT_URL}/users" modelAttribute="UsersForm"
		method="POST">
		<table>
			<tbody>
				<tr>
					<th>Username</th>
					<td><f:input path="username" /> <f:errors path="username" /></td>
				</tr>
				<tr>
					<th>Password</th>
					<td><f:password path="password" /> <f:errors path="password" /></td>
				</tr>
				<tr>
					<th>Role</th>
					<td><f:select path="role">
							<f:option value="ROLE_MEMBER">MEMBER</f:option>
							<f:option value="ROLE_MENTOR">MEMTOR</f:option>
						</f:select> <f:errors path="role" /></td>
				</tr>
				<tr>
					<th>Status</th>
					<td><f:select path="status" disabled="true">
							<f:option value="0">CREDENTIALSNONEXPIRED</f:option>
							<f:option value="1">NORMAL</f:option>
							<f:option value="2">ACCOUNTNONLOCKED</f:option>
							<f:option value="3">ACCOUNTNONEXPIRED</f:option>
							<f:option value="4">DISABLED</f:option>
						</f:select> <f:errors path="status" /></td>
				</tr>
			</tbody>
			<tfoot>
				<tr>
					<th></th>
					<td><input type="submit" name="action" value="+"><input
						type="button" onclick="add()" value="X"></td>
				</tr>
			</tfoot>
		</table>
	</f:form>
	<br>
	<table>
		<thead>
			<tr>
				<th>#</th>
				<th>User name</th>
				<th>Password</th>
				<th>Role</th>
				<th>Status</th>
				<th>Action</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${users}" var="user" varStatus="status">
				<tr>
					<td>${status.index + 1}</td>
					<td>${user.username}</td>
					<td>${user.password}</td>
					<td>${user.role}</td>
					<td>
						<c:if test="${user.status == 0}">CREDENTIALSNONEXPIRED</c:if>
						<c:if test="${user.status == 1}">NORMAL</c:if>
						<c:if test="${user.status == 2}">ACCOUNTNONLOCKED</c:if>
						<c:if test="${user.status == 3}">ACCOUNTNONEXPIRED</c:if>
						<c:if test="${user.status == 4}">DISABLED</c:if>
					</td>
					<td><button onclick="edit('${user.username}')">E</button>
						<button
							onclick="location.href='${ROOT_URL}/users/d/${user.username}'">D</button></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<jsp:include page="footer.jsp" />
	<script type="text/javascript">
		function edit(id) {
			$.get("${ROOT_URL}/users/" + id).done(function(data) {
				document.forms.UsersForm.username.value = data.username;
				document.forms.UsersForm.password.value = "";
				document.forms.UsersForm.role.value = data.role;
				document.forms.UsersForm.status.disabled = false;
				document.forms.UsersForm.status.value = data.status;
				document.forms.UsersForm.action.value = "=>";
			}).fail(function(e) {
				console.log("error", e);
			}).always(function() {
			});
		}

		function add() {
			document.forms.UsersForm.username.value = "";
			document.forms.UsersForm.password.value = "";
			document.forms.UsersForm.role.value = "ROLE_MEMBER";
			document.forms.UsersForm.status.disabled = true;
			document.forms.UsersForm.status.value = "0";
			document.forms.UsersForm.action.value = "+";
		}
	</script>
</body>
</html>