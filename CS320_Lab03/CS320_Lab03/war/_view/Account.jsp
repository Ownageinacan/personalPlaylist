<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html>
	<head>
		<title>Home</title>
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
		.accountStuff{
			font-size: 12px;
			font-weight: bold;
			font-family: "Verdana", sans-serif;
			position: absolute;
			z-index: 1;
			left: 43%;	
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
		<div class="accountStuff">
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
		</div>
	</body>
</html>
