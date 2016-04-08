package edu.ycp.cs320.personalPlaylist.model;

public class Song
{
	
	private int artistId;
	private int albumId;
	private String title;
	private int genreId;
	private int songId;
	private String location;
	
	//private boolean isPlaying;
	//private String address;
	//private float length;
	
	public Song(String title, String location) //Constructor (empty)
	{
		this.location = location;
		this.title = title;
	}
	
	//I promise all of these are correct
	public void setlocation(String location){
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
	
	
	/*
	
	//PROBABLY DON'T NEED THESE
		public void setLength(float length) 
	{
		this.length = length;
	}
		public float getLength() 
	{
		return length;
	}	
	public boolean isPlaying() 
	{
		return isPlaying;
	}
	public void setPlaying(boolean isPlaying) 
	{
		this.isPlaying = isPlaying;
	}
	
	public void setAddress(String address)
	{
		this.address = address;
	}
	
	public String getAddress()
	{
		return this.address;
	}
	*/
}
