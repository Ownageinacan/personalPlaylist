package edu.ycp.cs320.lab03;

public class Song 
{
	private float length;
	private String artist;
	private String album;
	private String title;
	private String genre;
	private boolean isPlaying;
	
	Song()
	{
		
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
	
	
	//getters and setters for everything, we can probably get rid of most of the sets since we dont want to mess with some of the properties
	public float getLength() 
	{
		return length;
	}
	public void setLength(float length) 
	{
		this.length = length;
	}
	public String getArtist() 
	{
		return artist;
	}
	public void setArtist(String artist) 
	{
		this.artist = artist;
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
	
	
}
