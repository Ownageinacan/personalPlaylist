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
			left: 400px;
			top: 100px;
		}
		</style>
	</head>

	<body>	
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
		<div class ="CreateNewSong">
			<form action="${pageContext.servletContext.contextPath}/Home" method="post">
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
				<input type="Submit" name="createPlaylist" value="createPlaylist">
			</form>
		</div>	
	</body>
</html>
