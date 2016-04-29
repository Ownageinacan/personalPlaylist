<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html>
	<head>
		<title>Index view</title>
	</head>

	<body>
		My Account
		<form action="${pageContext.servletContext.contextPath}/Home" method="post">
		<table>
		
			 
			<c:forEach items="${accounts}" var="account">
			        <tr class="UserRow">
			            <td class="nameCol">Username: ${account.username}</td>			            			            
			        </tr>
			         <tr class="PassRow">
			            <td class="nameCol">Password: ${account.password}</td>			            
			        </tr>
			</c:forEach>
		
		</table>
		<input type="Submit" name="submithome" value="Home">
		</form>
	</body>
</html>
