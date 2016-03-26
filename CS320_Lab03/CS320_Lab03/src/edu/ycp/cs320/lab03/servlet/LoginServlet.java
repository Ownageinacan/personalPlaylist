package edu.ycp.cs320.lab03.servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.ycp.cs320.lab03.Account;
import edu.ycp.cs320.lab03.controller.LoginController;

public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.getRequestDispatcher("/_view/Login.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		// Decode form parameters and dispatch to controller
		String errorMessage = null;
		Boolean result = null;
		LoginController controller = new LoginController();
		try {
			//String Username = getStringFromParameter(req.getParameter("Username"));
			//String Password = getStringFromParameter(req.getParameter("Password"));
			String Username = req.getParameter("Username");
			String Password = req.getParameter("Password");
			result = controller.Usercheck(Username, Password);
			
			if (result == false) {
				errorMessage = "Incorrect Username/Password";
			}
		} catch (NumberFormatException e) {
			errorMessage = "Incorrect Username/Password";
		}
		
		// Add parameters as request attributes
		req.setAttribute("Username", req.getParameter("Username"));
		req.setAttribute("Password", req.getParameter("Password"));
		
		// Add result objects as request attributes
		req.setAttribute("errorMessage", errorMessage);
		req.setAttribute("result", result);
		
		if(result == true){
			// Forward to view to render the result HTML document
			//req.getRequestDispatcher("/_view/Home.jsp").forward(req, resp);
			resp.sendRedirect("/lab03/Home");
		}else{
			req.getRequestDispatcher("/_view/Login.jsp").forward(req, resp);
		}
	}
	/*private Double getStringFromParameter(String s) {
		if (s == null || s.equals("")) {
			return null;
		} else {
			return Double.parseDouble(s);
		}
	}*/
}
