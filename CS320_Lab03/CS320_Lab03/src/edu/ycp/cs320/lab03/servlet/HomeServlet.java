package edu.ycp.cs320.lab03.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HomeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		try{
			if(!req.getSession().getAttribute("Username").equals(null)){
				req.getRequestDispatcher("/_view/Home.jsp").forward(req, resp);
			}
		}catch(NullPointerException e){
			req.getRequestDispatcher("/_view/Login.jsp").forward(req, resp);
		}
			
	}
}
