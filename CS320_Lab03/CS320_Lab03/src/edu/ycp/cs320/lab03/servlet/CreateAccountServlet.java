package edu.ycp.cs320.lab03.servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Authentication.User;

import edu.ycp.cs320.lab03.Account;
import edu.ycp.cs320.lab03.AllUsers;
import edu.ycp.cs320.lab03.controller.LoginController;

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
		String errorMessage = null;
		String Password = req.getParameter("Password");
		String Username = req.getParameter("Username");
		String rePassword = req.getParameter("rePassword");
		Account newUser = new Account(Username, Password);
		if(Password.equals(rePassword)){
	
		}else{
			errorMessage = "Passwords do not match!";
			System.out.print(Password);
			System.out.print(rePassword);
		}
		if(AllUsers.Users.containsValue(Username)){
			errorMessage = "Username is Already being used!";
		}
		if(errorMessage == null){
			newUser = new Account(Username, Password);
			AllUsers.Users.put(Password, newUser);
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
