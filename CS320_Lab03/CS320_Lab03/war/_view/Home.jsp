<!doctype html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>


<html>
	<head>
		<link rel="icon" href="Assets/Icons/PersonalPlayerLogo.png">
		<style type="text/css">
		<!--select {
			position: absolute;
			z-index: 3;
		    border: 1px solid #111;
		   background: transparent;
		   width: 150px;
		   padding: 5px 35px 5px 5px;
		   font-size: 16px;
		   border: 1px solid #ccc;
		   height: 34px;
		   -webkit-appearance: none;
		   -moz-appearance: none;
		   appearance: none;
		   background: 100% no-repeat #eee;
			} -->
		.SongPlayer{
			position:absolute;
			z-index:4;
			left: 200px;	
			top: 900px;
		}
		.topButtons {
			position: absolute;
			z-index: 3;
			left: 200px;	
			top: 50px;
		}
		.topLogo {
			z-index: 2;
			position: absolute;
			left: 0;
			top: 0;
			width: 7.8%;
			height: 12.1%;
			border-top-width: thick;
			border-right-width: thick;
			border-bottom-width: thick;
			border-left-width: thick;
			border-top-style: none;
			border-right-style: solid;
			border-bottom-style: solid;
			border-left-style: none;
			border-top-color: #FFF;
			border-right-color: #FFF;
			border-bottom-color: #FFF;
			border-left-color: #FFF;
		}
		.backgroundPic {
			top: 12.57%;
			left: 8.07%;
			height: 88.43%;
			width: 91.93%;
			position: fixed;
			z-index: 0;
		}
		.sideBorder {
			background-color: #65337E;
			top: 0;
			left: 0;
			width: 7.81%;
			height: 100%;
			position: absolute;
			z-index: 1;
		}
		.topBanner {
			position: absolute;
			background-color: #65337E;
			height: 12.1%;
			width: 100%;
			z-index: 1;
			top: 0;
			left: 0;	
		}
		</style>
		<title>Home</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	</head>

	<body>
		<div class ="topButtons">
			<input type="button" onclick="location.href='http://localhost:8081/lab03/Account';" value="My Account"/>
			<input type="button" onclick="location.href='http://localhost:8081/lab03/Login';" value="Logout" style="left: 200px;" />
		</div>
		<!--
		<form name="Menus">
		<p><select name="Account" onChange="go()" style= "right: 10%; top: 4.5%">
			<option value="http://localhost:8081/lab03/Login">Log Out</option>
			<option value="http://localhost:8081/lab03/Account">My Account</option>
			<option selected></option>
		</select></p>
		
		<script type="text/javascript">
		
		function go(){
		location=
		document.Menus.Account.
		options[document.Menus.Account.selectedIndex].value
		}
		
		</script>
		</form>
		-->
		<div class="sideBorder"></div>
		<div class="backgroundPic"><img src='Assets/Pictures/shutterstock_138386987.jpg' style="height: 100%; width: 100%;"/></div>
		<div class="topLogo">
			<img src='Assets/Icons/PersonalPlayerLogo.png' style="height: 100%; width: 100%;"/>
		</div>
		<div class="topBanner"></div>
		<div class="SongPlayer"><audio controls>
  <source src='Assets/Songs/song.mp3' type="audio/mpeg">
</audio>
</div>
	</body>
</html>