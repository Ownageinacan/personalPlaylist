/*package edu.ycp.cs320.lab03.controller;

import java.util.List;

import edu.ycp.cs320.personalPlaylist.model.Playlist;
import edu.ycp.cs320.personalPlaylist.persist.DatabaseProvider;
import edu.ycp.cs320.personalPlaylist.persist.IDatabase;
import edu.ycp.cs320.personalPlaylistdb.InitDatabase;

public class HomeController {
	IDatabase db = DatabaseProvider.getInstance();
	
	public List<Playlist> getAllPlayListsbyAccount(String user, String pass){
		InitDatabase.init();
		
		List<Playlist> playlists = db.findPlaylistsByAccount(user, pass);
		return playlists;
	}

	public List<Playlist> getAllPlayLists() {
		InitDatabase.init();
		List<Playlist> playlists = db.findAllPlaylists();
		return playlists;
		
	}
}
*/