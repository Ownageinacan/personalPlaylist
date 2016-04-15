package edu.ycp.cs320.personalPlaylistdb;

import java.util.List;
import java.util.Scanner;

import edu.ycp.cs320.personalPlaylist.model.Song;
import edu.ycp.cs320.personalPlaylist.persist.DatabaseProvider;
import edu.ycp.cs320.personalPlaylist.persist.IDatabase;

public class AllSongsQuery 
{
	public static void main(String[] args) 
	{
		Scanner keyboard = new Scanner(System.in);

		// Create the default IDatabase instance
		InitDatabase.init(keyboard);
		
		// get the DB instance and execute transaction
		IDatabase db = DatabaseProvider.getInstance();
		List<Song> songList = db.findAllSongs();
		
		// check if anything was returned and output the list
		if (songList.isEmpty()) 
		{
			System.out.println("There are no songs in the database");
		}
		else {
			for (Song song : songList) 
			{
				System.out.println(song.getTitle());
			}
		}
	}
}
