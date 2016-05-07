package edu.ycp.cs320.lab03.servlet;

import java.util.List;
import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.ycp.cs320.lab03.controller.MasterController;
import edu.ycp.cs320.personalPlaylist.model.Playlist;

public class HomeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private MasterController controller = null;	
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String user = (String) req.getSession().getAttribute("Username");
		String pass = (String) req.getSession().getAttribute("Password");
		if (user == null) {
			System.out.println("   User: <" + user + "> not logged in or session timed out");

			// user is not logged in, or the session expired
			resp.sendRedirect(req.getContextPath() + "/Login");
			return;
		}
		controller = new MasterController();
		System.out.println("getting all playlists");
		List<Playlist> playlists = controller.getAllPlayListsbyAccount(user, pass);	
		req.setAttribute("playlists", playlists);
		System.out.print(playlists.get(1).getTitle());
		
		req.getRequestDispatcher("/_view/Home.jsp").forward(req, resp);
	}
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String createPlaylistName = req.getParameter("createPlaylistName");
		String user = (String) req.getSession().getAttribute("Username");
		String pass = (String) req.getSession().getAttribute("Password");
		String deleteButton = req.getParameter("deletePlaylist");
		
		controller = new MasterController();

		if(!(createPlaylistName == null)){
			try {
				controller.CreatePlaylist(createPlaylistName, user);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		//doesn't work
		if(!(deleteButton == null)){
			controller.deletePlaylist(deleteButton);
		}
		List<Playlist> playlists = controller.getAllPlayListsbyAccount(user, pass);	
		req.setAttribute("playlists", playlists);

		req.getRequestDispatcher("/_view/Home.jsp").forward(req, resp);
	}	
}
