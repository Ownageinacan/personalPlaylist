//THIS CLASS IS USELESS BUT WE MAY USE IT LATER
package edu.ycp.cs320.personalPlaylist.model;

import java.util.ArrayList;

public class Searcher 
{
	private String titleContainsWord;
	private String artist;
	private String album;
	private String containsSong;
	
	Searcher(String titleContainsWord, String artist, String album, String containsSong)
	{
		this.setTitleContainsWord(titleContainsWord);
		this.setArtist(artist);
		this.setAlbum(album);
		this.setContainsSong(containsSong);
	}
	public ArrayList<Playlist> playListSearch(String titleContainsWord, String containsSong)
	{
		throw new UnsupportedOperationException("TODO - implement");
	}
	public ArrayList<Song> songSearch(String titleContainsWord, String artist, String album)
	{
		throw new UnsupportedOperationException("TODO - implement");
	}
	public ArrayList<Song> albumSearch(String titleContainsWord)
	{
		throw new UnsupportedOperationException("TODO - implement");
	}
	
	//getters and setters
	public String getTitleContainsWord() 
	{
		return titleContainsWord;
	}
	public void setTitleContainsWord(String titleContainsWord) 
	{
		this.titleContainsWord = titleContainsWord;
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
	public String getContainsSong() 
	{
		return containsSong;
	}
	public void setContainsSong(String containsSong) 
	{
		this.containsSong = containsSong;
	}
	
	
}
