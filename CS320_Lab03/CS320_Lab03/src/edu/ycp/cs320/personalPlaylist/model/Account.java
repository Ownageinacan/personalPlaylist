//Another maybe useless class? This one is probably more useful than the others
//potentially keep it

package edu.ycp.cs320.personalPlaylist.model;


public class Account 
{
	private String Username;
	private String Password;
	private int AccountId;
	
	
	public Account()	
	{										 
		
	}
//	public boolean login(String Username, String Password)// do we need this since we have the login controller?
//	{
//		throw new UnsupportedOperationException("TODO - implement");
//	}
	
	public boolean createPlaylist(String name)	//Probably want to use an ArrayList to hardcode this, maybe link this to database after that's implemented? -alex
	{
		throw new UnsupportedOperationException("TODO - implement");
	}
	public boolean deletePlaylist(String name)	//Using the ArrayList suggestion, simply remove the desired playlist from the list -alex
	{
		throw new UnsupportedOperationException("TODO - implement");
	}
	
	//getters and setters
	public void setAccountId(int id){
		this.AccountId = id;
	}
	public int getAccountId(){
		return AccountId;
	}
	public String getUserName() 
	{
		return Username;
	}
	public void setUserName(String username) 
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
