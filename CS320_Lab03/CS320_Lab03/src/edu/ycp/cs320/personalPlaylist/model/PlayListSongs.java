package edu.ycp.cs320.personalPlaylist.model;

public class PlayListSongs {
	int songId;
	int playListId;

	public PlayListSongs(){

	}
	
	public int getSongId(){
		return this.songId;
	}
	public int getPlayListId(){
		return this.playListId;
	}
	public void setSongId(int Id){
		this.songId = Id;
	}
	public void setPlayListId(int Id){
		this.playListId = Id;
	}
}

