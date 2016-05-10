<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html>
	<head>
		<title>MySongs</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<link rel="icon" href="Assets/Icons/PersonalPlayerLogo.png">
		
		<style type="text/css">
		.CreateNewSong{
			position: absolute;
			z-index: 2;
			left: 309px;
			top: 0px;
			font-size: 12px;
			font-weight: bold;
			font-family: "Verdana", sans-serif;
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
		.backgroundImage{
			position: fixed;
			z-index: 0;
			height: 100%;
			width: 100%;
			top: 0%;
			left: 0%;
			}
			.Songs{
			position: absolute;
			z-index: 2;
			left: 0px;
			top: 0px;
			font-size: 12px;
			font-weight: bold;
			font-family: "Verdana", sans-serif;
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
		<div class = "backgroundImage">
		<img src='Assets/Pictures/Speakers.jpg'>
		</div>
		<div class = "Songs">
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
		</div>			
		
		<div class ="CreateNewSong">
			<form action="${pageContext.servletContext.contextPath}/Songs" method="post">
				<table>
					<tr>
						<td class="label">Song Name:</td>
						<td><input type="text" name="createSongName" size="12" value="${createSongName}" /></td>
					</tr>
					<tr>
						<td class="label">Genre:</td>
						<td><input type="text" name="createSongGenre" size="12" value="${createSongGenre}" /></td>
					</tr>
					<tr>
						<td class="label">Artist:</td>
						<td><input type="text" name="createSongArtist" size="12" value="${createSongArtist}" /></td>
					</tr>
					<tr>
						<td class="label">Song Album:</td>
						<td><input type="text" name="createSongAlbum" size="12" value="${createSongAlbum}" /></td>
					</tr>
					<tr>
						<td class="label">Location</td>
						<td><input type="text" name="createSongLocation" size="12" value="${createSongLocation}" /></td>
					</tr>
				</table>
				<input type="Submit" name="createSong" value="createSong">
			</form>
		</div>	
	</body>
</html>
