package edu.ycp.cs320.lab03.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.ycp.cs320.personalPlaylist.model.Account;
import edu.ycp.cs320.personalPlaylist.model.AllUsers;

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
			//do nothing
		}else{ //the passwords are different
			errorMessage = "Passwords do not match!";
			System.out.print(Password);
			System.out.print(rePassword);
		}
		if(AllUsers.Users.containsValue(Username)){ //checking if there is already a username with the name
			errorMessage = "Username is Already being used!";
		}else if(Password.isEmpty() ||Username.isEmpty() || rePassword.isEmpty()){
			errorMessage = "Please fill all fields.";
		}
		if(errorMessage == null){ //if everything is hunky dory, add the new account to the map
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
