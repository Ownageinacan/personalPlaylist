<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html>
	<head>
		<title>Index view</title>
	</head>

	<body>
		My Account
		<form action="${pageContext.servletContext.contextPath}/Account" method="post">
		<table>
		
			 <tr>
       				<td class="nameColHeading">Last Name</td>
       				<td class="nameColHeading">First Name</td>       				
			 </tr>
			 
			<c:forEach items="${accounts}" var="account">
			        <tr class="accountRow">
			            <td class="nameCol">${account.UserName}</td>
			            <td class="nameCol">${account.Password}</td>			            
			        </tr>
			</c:forEach>
		
		</table>
		</form>
	</body>
</html>
