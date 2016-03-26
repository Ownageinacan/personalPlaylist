package edu.ycp.cs320.lab03.controller;

import edu.ycp.cs320.lab03.AllUsers;

public class LoginController {
	public Boolean Usercheck(String Username, String Password) {
		/*if(Username.equals("Ben") && Password.equals("noodle")){
			return true;
		}else{
			return false;
		}*/
		try {
			if(AllUsers.Users.get(Password).getUsername().equals(Username)){
				return true;
			}else{
				return false;
			}
		} catch (NullPointerException e) {
			return false;
		}
		
	}
}
