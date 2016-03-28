package edu.ycp.cs320.lab03.controller;

import edu.ycp.cs320.personalPlaylist.AllUsers;

public class LoginController {
	public Boolean Usercheck(String Username, String Password) {
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
