package edu.ycp.cs320.personalPlaylist.derbyDatabase;

import static org.junit.Assert.fail;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.ycp.cs320.personalPlaylist.model.Album;
import edu.ycp.cs320.personalPlaylist.model.Artist;
import edu.ycp.cs320.personalPlaylist.model.Genre;
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
	List<Genre> genres = null;
	List<Album> albums = null;
	
	
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
	public void testInsertSongIntoSongsTable() throws SQLException {
		System.out.println("\n*** Testing insertSongIntoSongsTable ***");

		String songName     = "DankMemes";
		String location  = "H:/someplacethatIdunno";
		int albumId = 2;
		int artistId = 3;
		int genreId = 1;
				
		// insert new book (and possibly new author) into DB
		Integer song_id = db.insertSongIntoSongsTable(songName, location, albumId, artistId, genreId);

		// check the return value - should be a song_id > 0
		if (song_id > 0)
		{
			// try to retrieve the song from the DB
			// get the list of (Songs) from DB
			songs = db.findSongByTitle(songName);
			
			if (songs.isEmpty()) {
				System.out.println("No songs found for name <" + songName + ">");
				fail("Failed to insert new book <" + songName + "> into Library DB");
			}
			// otherwise, the test was successful.  Now remove the song just inserted to return the DB
			// to it's original state, except for using a song_id
			else {
				System.out.println("New song (ID: " + song_id + ") successfully added to Songs table: <" + songName + ">");
				
				// now delete Book (and its Author) from DB
				// leaving the DB in its previous state - except that an author_id, and a song_id have been used
				List<Artist> artist = db.removeSongByTitle(songName);				
			}
		}
		else
		{
			System.out.println("Failed to insert new song (ID: " + song_id + ") into Songs table: <" + songName + ">");
			fail("Failed to insert new song <" + songName + "> into DB");
		}
	}
	@Test
	public void insertPlaylistIntoPlaylistsTable()
	{
		//TODO: Implement
		fail("TODO: Implement");
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
	@Test
	public void testFindAllGenres()
	{
		int countAllGenres = 0;
		System.out.println("\n Testing FindAllGenres:");

		//get all of the songs
		List<Genre> genreList = db.findAllGenres();
		
		//simple test to check if no results were found in the DB
		if (genreList.isEmpty()) 
		{
			System.out.println("No genres were found");
			fail("No genres were found in the database");
		}

		else 
		{			
			genres = new ArrayList<Genre>();
			for (Genre genre : genreList) 
			{
				genres.add(genre);
				System.out.println(genre);
				countAllGenres++;
			}
			if(genres.size() != countAllGenres)
			{
				System.out.println("Only " + countAllGenres + " were found");
				fail("Not all genres were found");
			}
		}
	}
	@Test
	public void testFindAllAlbums()
	{
		int countAllAlbums = 0;
		System.out.println("\n Testing FindAllAlbums:");

		//get all of the songs
		List<Album> albumList = db.findAllAlbums();
		
		//simple test to check if no results were found in the DB
		if (albumList.isEmpty()) 
		{
			System.out.println("No albums were found");
			fail("No albums were found in the database");
		}

		else 
		{			
			albums = new ArrayList<Album>();
			for (Album album : albumList) 
			{
				albums.add(album);
				System.out.println(album);
				countAllAlbums++;
			}
			if(albums.size() != countAllAlbums)
			{
				System.out.println("Only " + countAllAlbums + " were found");
				fail("Not all albums were found");
			}
		}
	}
	@Test
	public void testFindAllSongsInPlaylist()
	{
		//TODO: Implement
		fail("TODO: Implement");
	}
	@Test
	public void testFindPlaylistByTitle()
	{
		//TODO: Implement
		fail("TODO: Implement");
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
	public void testFindSongsByPlaylistTitle()
	{
		//TODO: Implement
		fail("TODO: Implement");
	}	
	@Test
	public void testFindSongByArtistName()
	{
		//TODO: Implement
		fail("TODO: Implement");
	}	
	@Test
	public void testRemoveSongByTitle()
	{
		//TODO: Implement
		fail("TODO: Implement");
	}
	@Test
	public void testDeletePlaylistFromPlaylistTable()
	{
		//TODO: Implement
		fail("TODO: Implement");
	}
	@Test
	public void testinsertSongIntoPlaylist()
	{
		//TODO: Implement
		fail("TODO: Implement");
	}
	@Test
	public void testFindAllAccounts()
	{
		//TODO: Implement
		fail("TODO: Implement");
	}
	@Test
	public void testExecuteTransaction()
	{
		//TODO: Implement
		fail("TODO: Implement");
	}
	@Test
	public void testDoExecuteTransaction()
	{
		//TODO: Implement
		fail("TODO: Implement");
	}
	@Test
	public void testCreateTables()
	{
		//TODO: Implement
		fail("TODO: Implement");
	}
	@Test
	public void testLoadInitialData()
	{
		//TODO: Implement
		fail("TODO: Implement");
	}
	
}
