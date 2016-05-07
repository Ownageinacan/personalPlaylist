package edu.ycp.cs320.personalPlaylist.model;

public class Song
{
	
	private String title;
	private String location;
	private int artistId;
	private int albumId;
	private int genreId;
	private int songId;
	
	
	public Song()
	{
		
	}
	
	//getters and setters
	public void setLocation(String location){
		this.location = location;
	}
	public String getLocation(){
		return this.location;
	}
	public void setSongId(int songId)
	{
		this.songId = songId;
	}
	public int getSongId()
	{
		return songId;
	}
	public int getArtistId()
	{
		return artistId;
	}
	public void setArtistId(int id)
	{
		this.artistId = id;
	}
	public int getAlbumId() 
	{
		return albumId;
	}
	public void setAlbumId(int id) 
	{
		this.albumId = id;
	}
	public String getTitle() 
	{
		return title;
	}
	public void setTitle(String title) 
	{
		this.title = title;
	}
	public int getGenreId() 
	{
		return genreId;
	}
	public void setGenreId(int id) 
	{
		this.genreId = id;
	}
}
