<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="f"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Quiz Engine</title>
</head>
<body>
	<jsp:include page="header.jsp" />
	<h1>
		Question ${index} of ${total}: <span id="time-count"></span>
	</h1>
	<f:form action="${ROOT_URL}/quiz/${id}/${index}"
		modelAttribute="QuizForm" method="POST">
		<table>
			<thead>
				<tr>
					<td colspan="2">${question.question}</td>
				</tr>
			</thead>
			<tbody>
				<c:if test="${question.optionA != null}">
					<tr>
						<td style="width: 3em;">A<f:radiobutton path="anser"
								value="A" />
						</td>
						<td>${question.optionA}</td>
					</tr>
				</c:if>
				<c:if test="${question.optionB != null}">
					<tr>
						<td>B<f:radiobutton path="anser" value="B" /></td>
						<td>${question.optionB}</td>
					</tr>
				</c:if>
				<c:if test="${question.optionC != null}">
					<tr>
						<td>C<f:radiobutton path="anser" value="C" /></td>
						<td>${question.optionC}</td>
					</tr>
				</c:if>
				<c:if test="${question.optionD != null}">
					<tr>
						<td>D<f:radiobutton path="anser" value="D" /></td>
						<td>${question.optionD}</td>
					</tr>
				</c:if>
			</tbody>
			<tfoot>
				<tr>
					<th colspan="2"><c:if test="${previousIndex != null}">
							<input type="submit" name="previous" value="PREVIOUS">
						</c:if> <c:if test="${nextIndex != null}">
							<input type="submit" name="next" value="NEXT">
						</c:if><input type="submit" name="end" value="END"></th>
				</tr>
			</tfoot>
		</table>
		<f:hidden path="total" />
	</f:form>
	<input type="hidden" id="time-start"
		value="<fmt:formatDate value="${timeStart}" pattern="yyyy-MM-dd HH:mm:ss" timeZone="UTC" />">
	<input type="hidden" id="duration" value="${duration}">
	<c:if test="${previousIndex != null}">
		<a href="${ROOT_URL}/quiz/${id}/${previousIndex}">PREVIOUS</a>
	</c:if>
	&nbsp;|&nbsp;
	<c:if test="${nextIndex != null}">
		<a href="${ROOT_URL}/quiz/${id}/${nextIndex}">NEXT</a>
	</c:if>
	<jsp:include page="footer.jsp" />
	<script type="text/javascript">
		window.onload = function() {
			const UTC_MILLISECONDS = function(timeUTC) {
				return Date.UTC(timeUTC.getFullYear(), timeUTC.getMonth(),
						timeUTC.getDate(), timeUTC.getHours(), timeUTC
								.getMinutes(), timeUTC.getSeconds());
			}

			const FORMAT_TIME = function(seconds) {
				let minutes = (seconds - seconds % 60) / 60;
				let hours = (minutes - minutes % 60) / 60;

				seconds = '0' + (seconds % 60).toString();
				minutes = '0' + (minutes % 60).toString();
				hours = '0' + hours.toString();

				seconds = seconds.slice(-2);
				minutes = minutes.slice(-2);
				hours = hours.slice(-2);

				return hours + ":" + minutes + ":" + seconds;
			}

			const timeStart = document.getElementById('time-start');
			const duration = document.getElementById('duration').value;
			const timeZone = UTC_MILLISECONDS(new Date(timeStart.value));

			const caculator = setInterval(function() {
				const milliseconds = new Date().getTime() - timeZone;
				const seconds = duration - Math.floor(milliseconds / 1000);

				if (seconds <= 0) {
					clearInterval(caculator);
					document.forms.QuizForm.end.click();
				}

				const formatTime = FORMAT_TIME(seconds);
				document.getElementById('time-count').innerText = formatTime;
			}, 500);
		}
	</script>
</body>
</html>