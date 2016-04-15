package edu.ycp.cs320.personalPlaylistdb;

import java.util.List;
import java.util.Scanner;

import edu.ycp.cs320.personalPlaylist.model.Playlist;
import edu.ycp.cs320.personalPlaylist.persist.DatabaseProvider;
import edu.ycp.cs320.personalPlaylist.persist.IDatabase;

public class AllPlaylistsQuery 
{
	public static void main(String[] args) 
	{
		Scanner keyboard = new Scanner(System.in);

		// Create the default IDatabase instance
		InitDatabase.init(keyboard);
		
		// get the DB instance and execute transaction
		IDatabase db = DatabaseProvider.getInstance();
		List<Playlist> playlistList = db.findAllPlaylists();
		
		// check if anything was returned and output the list
		if (playlistList.isEmpty()) 
		{
			System.out.println("There are no playlists in the database");
		}
		else {
			for (Playlist playlist : playlistList) 
			{
				System.out.println(playlist.getTitle());
			}
		}
	}
}
