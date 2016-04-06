package edu.ycp.cs320.personalPlaylist.model;

public class Artist {

	private int artistId;
	private String artistName;

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

	public void setArtistName(String name) 
	{
		this.artistName = name;
	}

	public String getArtistName() 
	{
		return this.artistName;
	}
}
