<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html>
	<head>
		<title>MySongs</title>
	</head>

	<body>
		hurp
			<form action="${pageContext.servletContext.contextPath}/Songs" method="post">
				<c:forEach items="${songs}" var="song">
					<input type="checkbox" name="song" value="${song.location}">${song.title}<br>
				</c:forEach>
			<input type="submit" value="Submit">
			</form>
	</body>
</html>
