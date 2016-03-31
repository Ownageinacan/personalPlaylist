package edu.ycp.cs320.personalPlaylist.model;

public class Song
{
	

	private float length;
	private String artistFirstName;
	private String artistLastName;
	private String album;
	private String title;
	private String genre;
	private boolean isPlaying;
	private String address;
	private int songId;
	
	public Song()
	{
		length = 0;
		artistFirstName = "";
		artistLastName = "";
		album = "";
		title = "";
		genre = "";
		address = "";
		songId = 0;
	}
	public boolean play()
	{
		throw new UnsupportedOperationException("TODO - implement");
	}
	public boolean pause()
	{
		throw new UnsupportedOperationException("TODO - implement");
	}
	public boolean repeat()
	{
		throw new UnsupportedOperationException("TODO - implement");
	}
	public boolean rewind()
	{
		throw new UnsupportedOperationException("TODO - implement");
	}
	public boolean fastForward()
	{
		throw new UnsupportedOperationException("TODO - implement");
	}
	

	
	//getters and setters for everything else, we can probably get rid of most of the sets since we dont want to mess with some of the properties
	
	public void setSongId(int songId)
	{
		this.songId = songId;
	}
	
	public int getSongId()
	{
		return songId;
	}
	
	public float getLength() 
	{
		return length;
	}
	public void setLength(float length) 
	{
		this.length = length;
	}
	
	public String getArtistLastName()
	{
		return artistLastName;
	}
	public void setArtistLastName(String lastname)
	{
		this.artistLastName = lastname;
	}
	public String getArtistFirstName() 
	{
		return artistFirstName;
	}
	public void setArtistFirstName(String firstname) 
	{
		this.artistFirstName = firstname;
	}
	public String getAlbum() 
	{
		return album;
	}
	public void setAlbum(String album) 
	{
		this.album = album;
	}
	public String getTitle() 
	{
		return title;
	}
	public void setTitle(String title) 
	{
		this.title = title;
	}
	public String getGenre() 
	{
		return genre;
	}
	public void setGenre(String genre) 
	{
		this.genre = genre;
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
	
}
