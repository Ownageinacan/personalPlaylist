<!DOCTYPE html>

<html>
	<head>
		<title>Create A New Account</title>
	</head>

	<body>
		Create A New Account
		<c:if test="${! empty errorMessage}">
				<div class="error">${errorMessage}
		</c:if>
		<form action="${pageContext.servletContext.contextPath}/CreateAccount" method="post">
			<table>
				<tr>
					<td class="label">Desired Username:</td>
					<td><input type="text" name="Username" size="12" value="${Username}" /></td>
				</tr>
				<tr>
					<td class="label">Desired Password:</td>
					<td><input type="password" name="Password" size="12" value="${Password}" /></td>
				</tr>
				<tr>
					<td class="label">re-type Password:</td>
					<td><input type="password" name="rePassword" size="12" value="${rePassword}" /></td>
				</tr>
			</table>
				<input type="Submit" name="CreateAccount" value="Create Account">	
		</form>
	</body>
</html>
