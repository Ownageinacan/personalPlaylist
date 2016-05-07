package edu.ycp.cs320.personalPlaylist.model;

public class Playlist 
{
	private String namePlaylist;
	private int numberSongs;
	private int playlistId;
	private int userOwnerId;

	public Playlist()	
	{
	
	}
	


	public void setPlaylistId(int id)
	{
		this.playlistId = id;
	}
	
	public int getPlaylistId()	// Get playlist id for determining which playlist to use
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
	
	
	//PROBABLY DON'T NEED THIS EITHER BUT HEY WHO CARES		
	public int getNumberSongs() 
	{
		return numberSongs;
	}
	public void setNumberSongs(int numberSongs) 
	{
		if(numberSongs >= 0)
		{
			this.numberSongs = numberSongs;
		}else{
			this.numberSongs = 0;
		}
	}


	public void addSong(String song) {
		// TODO Auto-generated method stub
		
	}
	public void setUserOwnerId(int Id) {
		this.userOwnerId = Id;
	}
	public int getUserOwnerId(){
		return userOwnerId;
	}
}
