package edu.ycp.cs320.lab03;

public class Account 
{
	private String Username;
	private String Password;
	
	Account(String Username, String Password)
	{										 
		this.setUsername(Username);
		this.setPassword(Password);
	}
	public boolean login(String Username, String Password)// do we need this since we have the login controller?
	{
		throw new UnsupportedOperationException("TODO - implement");
	}
	public boolean createPlaylist(String name)
	{
		throw new UnsupportedOperationException("TODO - implement");
	}
	public boolean deletePlaylist(String name)
	{
		throw new UnsupportedOperationException("TODO - implement");
	}
	
	//getters and setters
	public String getUsername() 
	{
		return Username;
	}
	public void setUsername(String username) 
	{
		Username = username;
	}
	public String getPassword() 
	{
		return Password;
	}
	public void setPassword(String password) 
	{
		Password = password;
	}
	
}
