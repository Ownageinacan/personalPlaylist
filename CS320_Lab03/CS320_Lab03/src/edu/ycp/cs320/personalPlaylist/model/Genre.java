package edu.ycp.cs320.personalPlaylist.model;

public class Genre {
	private String genre;
	private int genreId;
	
	public Genre(String genre)
	{
		this.genre = genre;
	}

	public void setGenreId(int id)
	{
		this.genreId = id;
	}
	public int getGenreId()
	{
		return genreId;
	}
	
	//getter and setter
	public String getGenre() 
	{
		return genre;
	}

	public void setGenre(String genre) 
	{
		this.genre = genre;
	}
}
