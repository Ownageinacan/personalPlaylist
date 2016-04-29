package edu.ycp.cs320.lab03.controller;


import java.util.List;

import edu.ycp.cs320.personalPlaylist.model.Account;
import edu.ycp.cs320.personalPlaylist.persist.DatabaseProvider;
import edu.ycp.cs320.personalPlaylist.persist.IDatabase;
import edu.ycp.cs320.personalPlaylistdb.InitDatabase;

public class LoginController {
	
	
	public Boolean Usercheck(String Username, String Password) {
		InitDatabase.init();
		IDatabase db = DatabaseProvider.getInstance();
		List<Account> accountList = db.findAllAccounts();
		
		for(Account ac : accountList){
			if(ac.getUsername().equals(Username)){
				if(ac.getPassword().equals(Password)){
					return true;
				}
			}
		}
		return false;
	}
}
