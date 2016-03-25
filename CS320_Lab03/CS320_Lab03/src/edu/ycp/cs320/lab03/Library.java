package edu.ycp.cs320.lab03;

public class Library
{
	private int numberSongs;
	
	Library(int numberSongs)
	{
		this.setNumberSongs(numberSongs);
	}
	
	public boolean addSong(String name)// should this be boolean or should we have it access the collections of songs and remove it?
	{
		throw new UnsupportedOperationException("TODO - implement");
	}
	public boolean deleteSong(String name)// should this be boolean or should we have it access the collections of songs and remove it?
	{
		throw new UnsupportedOperationException("TODO - implement");
	}
	public boolean shuffle()// we will probably want to send it the playlist to shuffle in here
	{
		throw new UnsupportedOperationException("TODO - implement");
	}

	//getter and setter
	public int getNumberSongs() {
		return numberSongs;
	}

	public void setNumberSongs(int numberSongs) {
		this.numberSongs = numberSongs;
	}
}
