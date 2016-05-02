<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html>
	<head>
		<title>MySongs</title>
	</head>

	<body>	
		<c:out value="${playlistTitle}"/>
		<c:forEach items="${songs}" var="song">
			<div class ="song">
				<input type="checkbox" name="song" value="${song.location}">${song.title}<br>
				<audio controls>
						<source src='${song.location}' type="audio/mpeg">
				</audio>
			</div>	
		</c:forEach>	
		<form action="${pageContext.servletContext.contextPath}/Home" method="get">
		<input type="submit" value="Home">
			</form>
	</body>
</html>
