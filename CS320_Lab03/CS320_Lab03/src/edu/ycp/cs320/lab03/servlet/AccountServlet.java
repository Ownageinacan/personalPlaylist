package edu.ycp.cs320.lab03.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.ycp.cs320.personalPlaylist.model.Account;


public class AccountServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		req.getRequestDispatcher("/_view/Account.jsp").forward(req, resp);
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String User = (String) req.getSession().getAttribute("Username");
		String Pass = (String) req.getSession().getAttribute("Passwword");
		
		Account account = new Account();
		account.setUserName(User);
		account.setPassword(Pass);
		ArrayList<Account> accounts = new ArrayList<Account>();
		accounts.add(account);
		req.setAttribute("accounts", accounts);
		System.out.println(User);
		req.getRequestDispatcher("/_view/Account.jsp").forward(req, resp);
	}	
}
