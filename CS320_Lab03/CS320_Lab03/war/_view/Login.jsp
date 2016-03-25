<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html>
	<head>
		<link rel="icon" href="Assets/Icons/PersonalPlayerLogo.png">
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>Personal Player, Person Music</title>
<style type="text/css">
.error{
	position: absolute;
	left: 0px;
	top: -35px;
	color: red;
	height: 40px;
	width: 200px;
}
.UserPass {
	position: absolute;
	left: 10px;
	top: 50px;
	z-index: 3;
}
header {
	position: absolute;
	z-index: 2;
	left: 30px;
	top: 30px;
}
.main {
	position: fixed;
	height: 100%;
	width: 100%;
	top: 0;
	left: 0;
	z-index: 0;
}
.main_box_imprint {
	height: 186px;
	width: 200px;
	background-color: #FFF;
	position: absolute;
	left: 407px;
	top: 0px;
	z-index: 2;
	border-radius: 7px;
}
.main_box {
	height: 186px;
	width: 600px;
	background-color: #FFF;
	position: absolute;
	left: 32.6%;
	top: 31.5%;
	z-index: 1;
	border-radius: 7px;
	border-top-width: 7px;
	border-right-width: 14px;
	border-bottom-width: 7px;
	border-left-width: 7px;
	border-top-style: solid;
	border-right-style: solid;
	border-bottom-style: solid;
	border-left-style: solid;
	border-top-color: #65337E;
	border-right-color: #65337E;
	border-bottom-color: #65337E;
	border-left-color: #65337E;
}
.logo {
	left: -197px;
	z-index: 2;
	height: 200px;
	width: 200px;
	top: -7px;
	position: absolute;
}
</style>
	</head>

	<body>
		<header>
			<img src='Assets/Text/header.png' style="height: 150px; width: 800px;" />
		</header>
			<div class="main_box">
				<div class="main_box_imprint"></div> <!-- cutout for fillet -->
			 	<div class="logo">
					<img src='Assets/Icons/PersonalPlayerLogo.png' style="height: 200px; width: 200px;" />
				</div> <!-- logo -->
				<div class="UserPass">	
					<c:if test="${! empty errorMessage}">
						<div class="error">${errorMessage}</div>
					</c:if>
					<form action="${pageContext.servletContext.contextPath}/Login" method="post">
						<table>
							<tr>
								<td class="label">Username:</td>
								<td><input type="text" name="Username" size="12" value="${Username}" /></td>
							</tr>
							<tr>
								<td class="label">Password:</td>
								<td><input type="password" name="Password" size="12" value="${Password}" /></td>
							</tr>
						</table>
						<input type="Submit" name="Login" value="Login">
						<input type="button" onclick="location.href='http://localhost:8081/lab03/CreateAccount';" value="Create Account" style="left: 100px;" />
					</form>
					</div>
			</div> <!-- user and pass box -->
			<div class="main">
			 	<img src='Assets/Pictures/maxresdefault.jpg' style="height: 100%; width: 100%;" />
			</div> 
			<!-- background image -->
	</body>
</html>