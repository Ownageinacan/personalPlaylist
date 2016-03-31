package edu.ycp.cs320.personalPlaylist.model;

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
		
		if(this.numberSongs > 0){	//Check to make sure number of songs is more than 0 before returning it
			return numberSongs;
		}else{
			return 0;	//Return 0 if numSongs is less than 0 (which is impossible)
		}
		
	}

	public void setNumberSongs(int numberSongs) {
		this.numberSongs = numberSongs;
	}
}
