package edu.ycp.cs320.personalPlaylist.model;

public class Album 
{
	private String title;
	private int albumId;
	
	public Album()
	{

	}

	public void setAlbumId(int id)
	{
		this.albumId = id;
	}
	public int getAlbumId()
	{
		return albumId;
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
