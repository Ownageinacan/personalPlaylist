package edu.ycp.cs320.personalPlaylist.model;


public class Account 
{
	private String Username;
	private String Password;
	private int AccountId;
	
	
	public Account()	
	{										 
		
	}
	//getters and setters
	public void setAccountId(int id){
		this.AccountId = id;
	}
	public int getAccountId(){
		return AccountId;
	}
	public String getUsername() 
	{
		return Username;
	}
	public void setUsername(String username) 
	{
		this.Username = username;
	}
	public String getPassword() 
	{
		return Password;
	}
	public void setPassword(String password) 
	{
		this.Password = password;
	}
	
}
