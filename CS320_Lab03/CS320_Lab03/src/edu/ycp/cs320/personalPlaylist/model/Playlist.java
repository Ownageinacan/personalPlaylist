package edu.ycp.cs320.personalPlaylist.model;

public class Playlist 
{
	private int numberSongs;
	private String namePlaylist;
	private int playlistId;
	
	public Playlist()
	{
		numberSongs = 0;
		namePlaylist = "";
		playlistId = 0;
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
	
	public void setPlaylistId(int playlistId)	
	{
		this.playlistId = playlistId;
	}
	public int getPlaylistId()	// Get/Set playlist id are for determining which playlist to use
	{
		return this.playlistId;
	}
	
	public void setTitle(String title)	//Might never use this method, who knows how this'll work
	{
		this.namePlaylist = title;
	}
	
	public String getTitle()	//Definitely going to use this one; see FakeDatabase and look at findAllSongInPlaylist
	{
		return namePlaylist;
	}	
	
	public int getNumberSongs() 
	{
		return numberSongs;
	}
	public void setNumberSongs(int numberSongs) 
	{
		this.numberSongs = numberSongs;
	}

	
}
