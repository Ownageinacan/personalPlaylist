package edu.ycp.cs320.lab03.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.ycp.cs320.lab03.controller.MasterController;
////////////////////////////////////////////////////////////////////
//This class was written without using the completed CS320 Lab03
/////////////////////////////////////////////////////////////////////
public class CreateAccountServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		req.getRequestDispatcher("/_view/CreateAccount.jsp").forward(req, resp);
	}
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		//ArrayList<Account> Users = new ArrayList<Account>();
		MasterController controller = new MasterController();
		String errorMessage = null;
		String Password = req.getParameter("Password");
		String Username = req.getParameter("Username");
		String rePassword = req.getParameter("rePassword");
		//Account newUser = new Account();
		if(!Password.equals(rePassword)){
			errorMessage = "Passwords do not match!";
			System.out.print(Password);
			System.out.print(rePassword);
		}else if(controller.Usercheck(Username, Password) == true){ //user already exists
			errorMessage = "User already exists!";
		}else{ //create account was successful
			controller.CreateAccount(Username, Password);
			req.getRequestDispatcher("/_view/AccountCreated.jsp").forward(req, resp);
		}
		
		// Add parameters as request attributes
		req.setAttribute("Username", req.getParameter("Username"));
		
		// Add result objects as request attributes
		req.setAttribute("errorMessage", errorMessage);
		
		//render the page
		req.getRequestDispatcher("/_view/CreateAccount.jsp").forward(req, resp);
	}
}
