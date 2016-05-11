<!DOCTYPE html>

<html>
	<head>
		<title>Create A New Account</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<link rel="icon" href="Assets/Icons/PersonalPlayerLogo.png">
		<style type="text/css">
		.backgroundPic{
			top: 0%;
			left: 0%;
			height: 100%;
			width: 100%;
			position: fixed;
			z-index: 0;
		}
		.information{
			font-size: 12px;
			font-weight: bold;
			font-family: "Verdana", sans-serif;
			position: absolute;
			z-index: 1;
			left: 37%;	
			top: 40%;
			color: white;
			background-color: #65337E;
			border-top-width: thick;
			border-right-width: thick;
			border-bottom-width: thick;
			border-left-width: thick;
			border-top-style: solid;
			border-right-style: solid;
			border-bottom-style: solid;
			border-left-style: solid;
			border-top-color: #FFF;
			border-right-color: #FFF;
			border-bottom-color: #FFF;
			border-left-color: #FFF;
			border-radius: 7px;
		}
		</style>
	</head>

	<body>
		<div class="backgroundPic"><img src='Assets/Pictures/music.jpg'></div>
		<div class="information">
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
			<form action="${pageContext.servletContext.contextPath}/Login" method="get">
			<input type="Submit" name="Login" value="Back to Login">
			</form>
		</div>
	</body>
</html>
