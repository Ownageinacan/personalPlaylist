package edu.ycp.cs320.personalPlaylist.derbyDatabase;

import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.ycp.cs320.personalPlaylist.model.Artist;
import edu.ycp.cs320.personalPlaylist.model.Playlist;
import edu.ycp.cs320.personalPlaylist.model.Song;
import edu.ycp.cs320.personalPlaylist.persist.DatabaseProvider;
import edu.ycp.cs320.personalPlaylist.persist.DerbyDatabase;
import edu.ycp.cs320.personalPlaylist.persist.IDatabase;



public class DerbyDatabaseTest 
{
	private IDatabase db = null;
	
	List<Song> songs = null;
	List<Playlist> playlists = null;
	List<Artist> artists = null;
	
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception 
	{
		//empty
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception 
	{
		//empty
	}

	@Before
	public void setUp() throws Exception 
	{
		// creating DB instance here
		DatabaseProvider.setInstance(new DerbyDatabase());
		db = DatabaseProvider.getInstance();	
	}

	@After
	public void tearDown() throws Exception 
	{
		//empty
	}
	@Test
	public void testFindAllSongs() 
	{
		int countSongs = 0;
		System.out.println("\n Testing FindAllSongs:");

		//get all of the songs
		List<Song> songsList = db.findAllSongs();
		
		//simple test to check if no results were found in the DB
		if (songsList.isEmpty()) 
		{
			System.out.println("No songs were found");
			fail("No songs were found in the database");
		}

		else 
		{			
			songs = new ArrayList<Song>();
			for (Song song : songsList) 
			{
				songs.add(song);
				System.out.println(song);
				countSongs++;
			}
			if(songs.size() != countSongs)
			{
				System.out.println("Only " + countSongs + " songs were found");
				fail("Missing songs");
			}
		}
	}
	@Test
	public void testFindAllPlaylists() 
	{
		int countTotalPlaylists = 0;
		System.out.println("\n Testing FindAllPlaylists:");

		//get all of the songs
		List<Playlist> playlistsList = db.findAllPlaylists();
		
		//simple test to check if no results were found in the DB
		if (playlistsList.isEmpty()) 
		{
			System.out.println("No playlists were found");
			fail("No playlists were found in the database");
		}

		else 
		{			
			playlists = new ArrayList<Playlist>();
			for (Playlist playlist : playlistsList) 
			{
				playlists.add(playlist);
				System.out.println(playlist);
				countTotalPlaylists++;
			}
			if(playlists.size() != countTotalPlaylists)
			{
				System.out.println("Only " + countTotalPlaylists + " were found");
				fail("Not all playlists were found");
			}
		}
	}
	@Test
	public void testFindAllArtists() 
	{
		int countAllArtists = 0;
		System.out.println("\n Testing FindAllArtists:");

		//get all of the songs
		List<Artist> artistList = db.findAllArtists();
		
		//simple test to check if no results were found in the DB
		if (artistList.isEmpty()) 
		{
			System.out.println("No artists were found");
			fail("No artists were found in the database");
		}

		else 
		{			
			artists = new ArrayList<Artist>();
			for (Artist artist : artistList) 
			{
				artists.add(artist);
				System.out.println(artist);
				countAllArtists++;
			}
			if(artists.size() != countAllArtists)
			{
				System.out.println("Only " + countAllArtists + " were found");
				fail("Not all artists were found");
			}
		}
	}

}
