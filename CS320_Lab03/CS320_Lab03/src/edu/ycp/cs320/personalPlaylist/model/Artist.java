package edu.ycp.cs320.personalPlaylist.model;

public class Artist {

	private int artistId;
	private String artistLastName;
	private String artistFirstName;

	public Artist() {

	}
	public void setArtistId(int id)
	{
		this.artistId = id;
	}

	public int getArtistId()
	{
		return this.artistId;
	}

	public void setArtistLastName(String name) 
	{
		this.artistLastName = name;
	}
	public void setArtistFirstName(String name) 
	{
		this.artistFirstName = name;
	}

	public String getArtistLastName() 
	{
		return this.artistLastName;
	}
	public String getArtistFirstName() 
	{
		return this.artistFirstName;
	}
}
