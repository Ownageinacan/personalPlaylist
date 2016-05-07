<!doctype html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>


<html>
	<head>
		<title>Home</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
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
		.Playlist
		{
			position:absolute;
			z-index:3;
			left: 500px;
			top: 500px;
			font-size: 32px;
		}
		.song
		{
			position:absolute;
			z-index:3;
			left: 500px;
			top: 500px;
			font-size: 20px;
		}
		tr.playlistRow{
			text-align: left;
			color: blue;
			font-weight: bold;
		}
		td.playlistCol{
			text-align: left;
			color: blue;
			font-weight: bold;
			max-width: 400px;
			padding-left: 20px;				
		}
		.SongPlayer{
			position: absolute;
			z-index:4;
			left: 500px;	
			top: 500px;
		}	
		.HiddenSongPlayer{
			position: absolute;
			z-index:4;
			left: 500px;	
			top: 500px;
			opacity: 100;
		}
		.sideButtons{
			position: absolute;
			z-index: 3;
			left: 5px;	
			top: 15%;
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
		.playlistsHead{
			font-size: 40px;
			font-weight: bold;
			font-family: "Verdana", sans-serif;
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
		.playlists{
			position: absolute;
			z-index: 3;
			left: 9.2%;	
			top: 14%;
			color: white;
			background-color: #65337E;
			border-top-width: thick;
			border-right-width: thick;
			border-bottom-width: thick;
			border-left-width: thick;
			border-top-style: none;
			border-right-style: solid;
			border-bottom-style: solid;
			border-left-style: solid;
			border-top-color: #FFF;
			border-right-color: #FFF;
			border-bottom-color: #FFF;
			border-left-color: #FFF;
			border-radius: 7px;
		}
		.topButtons{
			position: absolute;
			left: 130px;
			top: 25px;
			z-index: 2;
			color: white;
			font-size: 14px;
			font-weight: bold;
			font-family: "Verdana", sans-serif;
		}
		.searchAlbum{
			left: 400px;
			top: 25px;
			position: absolute;
			color: white;
			font-size: 14px;
			font-weight: bold;
			font-family: "Verdana", sans-serif;
		}
		.searchArtist{
			left: 700px;
			top: 25px;
			position: absolute;
			color: white;
			font-size: 14px;
			font-weight: bold;
			font-family: "Verdana", sans-serif;
		}
		</style>
	</head>

	<body>
		<div class ="sideButtons">
			<form action="${pageContext.servletContext.contextPath}/Account" method="post">
			<input type="Submit" name="submitaccount" value="My Account">
			</form>
			
			<form action="${pageContext.servletContext.contextPath}/Login" method="get">
			<input type="Submit" name="submitLogout" value="Logout">
			</form>
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
		<div class="playlists">
			<div class="playlistsHead">
			Playlists
			</div>
			<form action="${pageContext.servletContext.contextPath}/Songs" method="get">
				<c:forEach items="${playlists}" var="playlist">
					<input type="radio" name="playlist" value="${playlist.title}">${playlist.title}<br>
				</c:forEach>
			<input type="submit" value="Show Me the Playlist">
			</form>
			<form action="${pageContext.servletContext.contextPath}/Home" method="post">
				<input type="button" name="deletePlaylist" value="deletePlaylist">
			</form>
		</div>
		<div class="sideBorder"></div>
		<div class="backgroundPic"><img src='Assets/Pictures/shutterstock_138386987.jpg' style="height: 100%; width: 100%;"/></div>
		<div class="topLogo">
			<img src='Assets/Icons/PersonalPlayerLogo.png' style="height: 100%; width: 100%;"/>
		</div>
		<div class="topBanner">
			<div class="topButtons">
				<div class="createPlaylistButton">
					<form action="${pageContext.servletContext.contextPath}/Home" method="post">
						<table>
							<tr>
								<td class="label">Playlist Name:</td>
								<td><input type="text" name="createPlaylistName" size="12" value="${createPlaylistName}" /></td>
							</tr>
						</table>
						<input type="Submit" name="createPlaylist" value="createPlaylist">
					</form>
				</div>
			</div>
			<div class="searchAlbum">
					<form action="${pageContext.servletContext.contextPath}/Songs" method="get">
						<table>
							<tr>
								<td class="label">Album Name:</td>
								<td><input type="text" name="searchAlbum" size="12" value="${albumName}" /></td>
							</tr>
						</table>
						<input type="Submit" name="searchAlbum" value="search songs in an Album">
					</form>
			</div>
			<div class="searchArtist">
					<form action="${pageContext.servletContext.contextPath}/Songs" method="get">
						<table>
							<tr>
								<td class="label">Artist Name:</td>
								<td><input type="text" name="searchArtist" size="12" value="${artistName}" /></td>
							</tr>
						</table>
						<input type="Submit" name="searchArtist" value="search songs by artist">
					</form>
			</div>
		</div>
		<!--
		<div class="Playlist">
			<table>
			    <tr>
					<td class="Playlist">Playlist</td>
			    </tr>
			    <tr>
			    	<td class="song">Song</td>
			    </tr>    
			    <c:forEach items="${song}" var="pair">
			        <tr class="playlistRow">
			            <td class="playlistCol">${song}</td>
			            <td class="playlistCol">${pair.left.songId}</td>		            
			        </tr>
			    </c:forEach>
			</table>
		</div>
 
		<div class="SongPlayer">
			<audio controls>
  			<source src='Assets/Songs/song.mp3' type="audio/mpeg">
			</audio>
			</div>
		-->	
	</body>
</html>