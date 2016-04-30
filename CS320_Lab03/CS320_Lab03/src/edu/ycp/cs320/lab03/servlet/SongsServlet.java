package edu.ycp.cs320.lab03.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.ycp.cs320.personalPlaylist.model.Song;
import edu.ycp.cs320.personalPlaylist.persist.DatabaseProvider;
import edu.ycp.cs320.personalPlaylist.persist.IDatabase;
import edu.ycp.cs320.personalPlaylistdb.InitDatabase;

public class SongsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String user = (String) req.getSession().getAttribute("Username");
		if (user == null) {
			System.out.println("   User: <" + user + "> not logged in or session timed out");

			// user is not logged in, or the session expired
			resp.sendRedirect(req.getContextPath() + "/Login");
			return;
		}
		String playlistTitle = req.getParameter("playlist");
		List<Song> songs = null; 
		InitDatabase.init();
		IDatabase db = DatabaseProvider.getInstance();
		songs = db.findSongsByPlaylistTitle(playlistTitle);
		
		req.setAttribute("songs", songs);
		req.setAttribute("playlistTitle", playlistTitle);
		
		req.getRequestDispatcher("/_view/Songs.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		req.getRequestDispatcher("/_view/Songs.jsp").forward(req, resp);
	}
}
