package edu.ycp.cs320.personalPlaylist.model;

public class Playlist 
{
	private int numberSongs;
	private String namePlaylist;
	
	Playlist()
	{
		
	}
	public boolean addSong(String name)//should these be file names?
	{
		throw new UnsupportedOperationException("TODO - implement");
	}
	public boolean removeSong(String name)
	{
		throw new UnsupportedOperationException("TODO - implement");
	}
	public boolean skipSong()
	{
		throw new UnsupportedOperationException("TODO - implement");
	}
	public boolean reverseSong()
	{
		throw new UnsupportedOperationException("TODO - implement");
	}
	public boolean shuffle()
	{
		throw new UnsupportedOperationException("TODO - implement");
	}
	
	//getters and setters
	public int getNumberSongs() 
	{
		return numberSongs;
	}
	public void setNumberSongs(int numberSongs) 
	{
		this.numberSongs = numberSongs;
	}
	public String getNamePlaylist() 
	{
		return namePlaylist;
	}
	public void setNamePlaylist(String namePlaylist) 
	{
		this.namePlaylist = namePlaylist;
	}
	
}
