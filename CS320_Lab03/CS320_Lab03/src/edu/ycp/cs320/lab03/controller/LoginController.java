package edu.ycp.cs320.lab03.controller;

public class LoginController {
	public Boolean Usercheck(String Username, String Password) {
		if(Username.equals("Ben") && Password.equals("noodle")){
			return true;
		}else{
			return false;
		}	
	}
	public String convertToAsterix(String word){
		return "****";
	}
}
