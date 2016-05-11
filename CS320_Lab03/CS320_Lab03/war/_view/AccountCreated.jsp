<!DOCTYPE html>

<html>
	<head>
		<title>Account Created!</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<link rel="icon" href="Assets/Icons/PersonalPlayerLogo.png">
		<style type="text/css">
		.stuff{
			font-size: 40px;
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
		<div class = "stuff">
			Account created!
			<form action="${pageContext.servletContext.contextPath}/Login" method="get">
			<input type="Submit" name="Login" value="Back to Login">
			</form>
		</div>
	</body>
</html>
