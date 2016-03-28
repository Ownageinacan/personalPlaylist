package edu.ycp.cs320.personalPlaylist;

public class Album 
{
	private String title;
	
	Album(String title)
	{
		this.setTitle(title);
	}

	//getter and setter
	public String getTitle() 
	{
		return title;
	}

	public void setTitle(String title) 
	{
		this.title = title;
	}
}
