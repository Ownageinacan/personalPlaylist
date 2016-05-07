package edu.ycp.cs320.lab03.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import edu.ycp.cs320.lab03.controller.MasterController;
import edu.ycp.cs320.personalPlaylist.model.Song;
import edu.ycp.cs320.personalPlaylist.persist.DatabaseProvider;
import edu.ycp.cs320.personalPlaylist.persist.IDatabase;
import edu.ycp.cs320.personalPlaylistdb.InitDatabase;
////////////////////////////////////////////////////////////////////
//This class was written without using the completed CS320 Lab03
/////////////////////////////////////////////////////////////////////
public class SongsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private MasterController controller = null;	

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		InitDatabase.init();
		IDatabase db = DatabaseProvider.getInstance();
		HttpSession session = req.getSession(true);
		String user = (String) req.getSession().getAttribute("Username");
		if (user == null) {
			System.out.println("   User: <" + user + "> not logged in or session timed out");

			// user is not logged in, or the session expired
			resp.sendRedirect(req.getContextPath() + "/Login");
			return;
		}
		String playlistTitle = req.getParameter("playlist");
		session.setAttribute("playlistTitle", playlistTitle);
		
		List<Song> songs = null; 
		songs = db.findSongsByPlaylistTitle(playlistTitle);
		
		req.setAttribute("songs", songs);
		req.setAttribute("playlistTitle", playlistTitle);
		
		req.getRequestDispatcher("/_view/Songs.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		HttpSession session = req.getSession(true);
		InitDatabase.init();
		IDatabase db = DatabaseProvider.getInstance();
		controller = new MasterController();

		String createSongName = req.getParameter("createSongName");
		String createSongGenre = req.getParameter("createSongGenre");
		String createSongArtist = req.getParameter("createSongArtist");
		String createSongAlbum = req.getParameter("createSongAlbum");
		String createSongLocation = req.getParameter("createSongLocation");
		String playlistTitle = (String) session.getAttribute("playlistTitle");
		List<Song> songs = db.findSongsByPlaylistTitle(playlistTitle);
		
		if(!(createSongName == null)){
			try {
				controller.createNewSong(createSongName, playlistTitle, createSongGenre,
						createSongArtist, createSongAlbum, createSongLocation);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		req.setAttribute("songs", songs);
		req.setAttribute("playlistTitle", playlistTitle);
		req.getRequestDispatcher("/_view/Songs.jsp").forward(req, resp);
	}
}
