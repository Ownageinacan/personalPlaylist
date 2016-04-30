package edu.ycp.cs320.lab03.servlet;

import java.util.List;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import edu.ycp.cs320.lab03.controller.HomeController;
import edu.ycp.cs320.personalPlaylist.model.Playlist;

public class HomeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private HomeController controller = null;	
	
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
		controller = new HomeController();
		System.out.println("getting all playlists");
		List<Playlist> playlists = controller.getAllPlayLists();	
		req.setAttribute("playlists", playlists);
		System.out.print(playlists.get(1).getTitle());
		
		req.getRequestDispatcher("/_view/Home.jsp").forward(req, resp);
		/*try{
				req.getSession().getAttribute("Username");
				req.getRequestDispatcher("/_view/Home.jsp").forward(req, resp);
		}catch(NullPointerException e){
			req.getRequestDispatcher("/_view/Login.jsp").forward(req, resp);
		}*/
			
	}
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		controller = new HomeController();
		System.out.println("getting all playlists");
		List<Playlist> playlists = controller.getAllPlayLists();	
		req.setAttribute("playlists", playlists);
		System.out.print(playlists.get(0).getTitle());
		req.getRequestDispatcher("/_view/Home.jsp").forward(req, resp);
	}	
}
