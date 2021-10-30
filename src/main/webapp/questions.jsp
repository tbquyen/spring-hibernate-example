<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="f"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<jsp:include page="header.jsp" />
	<form hidden="true" method="POST" enctype="multipart/form-data" action="${ROOT_URL}/questions/upload" name="UploadForm">
		<input type="file" name="file" id="file" onchange="document.forms.UploadForm.submit()"/>
		<input type="hidden" name="categoryId" value="${QuestionsForm.categoryId}">
	</form>
	<f:form action="${ROOT_URL}/questions" modelAttribute="QuestionsForm"
		method="POST">
		<table>
			<tbody>
				<tr>
					<th>Categories</th>
					<td><f:select path="categoryId" onchange="changeCategory()">
							<f:options items="${categories}" itemValue="id" itemLabel="name" />
						</f:select>
						<label for="file">File to upload</label>
					</td>
				</tr>
				<tr>
					<th>Question</th>
					<td><f:textarea path="question" /> <f:errors path="question" /></td>
				</tr>
				<tr>
					<th>OptionA</th>
					<td><f:textarea path="optionA" /> <f:errors path="optionA" /></td>
				</tr>
				<tr>
					<th>OptionB</th>
					<td><f:textarea path="optionB" /> <f:errors path="optionB" /></td>
				</tr>
				<tr>
					<th>OptionC</th>
					<td><f:textarea path="optionC" /> <f:errors path="optionC" /></td>
				</tr>
				<tr>
					<th>OptionD</th>
					<td><f:textarea path="optionD" /> <f:errors path="optionD" /></td>
				</tr>
				<tr>
					<th>Anser</th>
					<td><f:select path="anser">
							<f:option value="A">A</f:option>
							<f:option value="B">B</f:option>
							<f:option value="C">C</f:option>
							<f:option value="D">D</f:option>
						</f:select> <f:errors path="anser" /></td>
				</tr>
				<tr>
					<th>Duration</th>
					<td><f:input path="duration" type="number" min="10" /> <f:errors
							path="duration" /></td>
				</tr>
				<tr>
					<th>Explanation</th>
					<td><f:textarea path="explanation" /> <f:errors
							path="explanation" /></td>
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
		<f:hidden path="id" />
	</f:form>
	<table>
		<thead>
			<tr>
				<th>#</th>
				<th>Question</th>
				<th>OptionA</th>
				<th>OptionB</th>
				<th>OptionC</th>
				<th>OptionD</th>
				<th>Anser</th>
				<th>Duration</th>
				<th>Explanation</th>
				<th>Action</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${questions}" var="question" varStatus="status">
				<tr>
					<td>${status.index + 1}</td>
					<td>${question.question}</td>
					<td>${question.optionA}</td>
					<td>${question.optionB}</td>
					<td>${question.optionC}</td>
					<td>${question.optionD}</td>
					<td>${question.anser}</td>
					<td>${question.duration}</td>
					<td>${question.explanation}</td>
					<td><button onclick="edit('${question.id}')">E</button>
						<button
							onclick="location.href='${ROOT_URL}/questions/${question.category.id}/${question.id}'">D</button></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<jsp:include page="footer.jsp" />
	<script type="text/javascript">
		function edit(id) {
			$
					.get("${ROOT_URL}/questions/" + id)
					.done(
							function(data) {
								document.forms.QuestionsForm.id.value = data.id;
								document.forms.QuestionsForm.categoryId.value = data.category.id;
								document.forms.QuestionsForm.question.value = data.question;
								document.forms.QuestionsForm.anser.value = data.anser;
								document.forms.QuestionsForm.optionA.value = data.optionA;
								document.forms.QuestionsForm.optionB.value = data.optionB;
								document.forms.QuestionsForm.optionC.value = data.optionC;
								document.forms.QuestionsForm.optionD.value = data.optionD;
								document.forms.QuestionsForm.duration.value = data.duration;
								document.forms.QuestionsForm.explanation.value = data.explanation;
								document.forms.QuestionsForm.action.value = "=>";
							}).fail(function(e) {
						console.log("error", e);
					}).always(function() {
					});
		}

		function add() {
			document.forms.QuestionsForm.id.value = 0;
			document.forms.QuestionsForm.question.value = "";
			document.forms.QuestionsForm.anser.value = "";
			document.forms.QuestionsForm.optionA.value = "";
			document.forms.QuestionsForm.optionB.value = "";
			document.forms.QuestionsForm.optionC.value = "";
			document.forms.QuestionsForm.optionD.value = "";
			document.forms.QuestionsForm.duration.value = 10;
			document.forms.QuestionsForm.explanation.value = "";
			document.forms.QuestionsForm.action.value = "+";
		}

		function changeCategory() {
			window.location.href = "${ROOT_URL}/questions/category/"
					+ document.forms.QuestionsForm.categoryId.value;
		}
	</script>
</body>
</html>