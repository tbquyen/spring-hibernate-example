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
	<f:form action="${ROOT_URL}/categories" modelAttribute="CategoriesForm"
		method="POST">
		<h3>Categories</h3>
		<label>Cattegory Name&nbsp;<f:input path="name" /></label>
		<input type="submit" name="action" value="+">
		<input type="button" onclick="add()" value="X">
		<br>
		<f:errors path="name" />
		<f:hidden path="id" />
	</f:form>
	<br>
	<table>
		<thead>
			<tr>
				<th>#</th>
				<th>Name</th>
				<th>Action</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${categories}" var="category" varStatus="status">
				<tr>
					<td>${status.index + 1}</td>
					<td>${category.name}</td>
					<td><button onclick="edit('${category.id}')">E</button>
						<button
							onclick="location.href='${ROOT_URL}/categories/d/${category.id}'">D</button></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<jsp:include page="footer.jsp" />
	<script type="text/javascript">
		function edit(id) {
			$.get("${ROOT_URL}/categories/" + id).done(function(data) {
				document.forms.CategoriesForm.name.value = data.name;
				document.forms.CategoriesForm.id.value = data.id;
				document.forms.CategoriesForm.action.value = "=>";
			}).fail(function(e) {
				console.log("error", e);
			}).always(function() {
			});
		}

		function add() {
			document.forms.CategoriesForm.name.value = "";
			document.forms.CategoriesForm.id.value = 0;
			document.forms.CategoriesForm.action.value = "+";
		}
	</script>
</body>
</html>