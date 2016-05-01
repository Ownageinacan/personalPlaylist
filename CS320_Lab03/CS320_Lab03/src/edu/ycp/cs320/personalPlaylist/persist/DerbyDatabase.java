package edu.ycp.cs320.personalPlaylist.persist;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import edu.ycp.cs320.personalPlaylist.model.Pair;
import edu.ycp.cs320.personalPlaylist.model.PlayListSongs;
import edu.ycp.cs320.personalPlaylist.model.Playlist;
import edu.ycp.cs320.personalPlaylist.model.Song;
import edu.ycp.cs320.personalPlaylist.persist.DerbyDatabase;
import edu.ycp.cs320.personalPlaylist.persist.DBUtil;
//import edu.ycp.cs320.booksdb.persist.DerbyDatabase.Transaction;
//import edu.ycp.cs320.booksdb.model.Author;
//import edu.ycp.cs320.personsalPlaylist.persist.DerbyDatabase.Transaction;
import edu.ycp.cs320.personalPlaylist.model.Account;
import edu.ycp.cs320.personalPlaylist.model.Album;
import edu.ycp.cs320.personalPlaylist.model.Artist;
import edu.ycp.cs320.personalPlaylist.model.Genre;


public class DerbyDatabase implements IDatabase {
	static {
		try {
			Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
		} catch (Exception e) {
			throw new IllegalStateException("Could not load Derby driver");
		}
	}

	//Don't touch transaction and MAX_ATTEMPTS. I mean it
	private interface Transaction<ResultType> {
		public ResultType execute(Connection conn) throws SQLException;
	}

	private static final int MAX_ATTEMPTS = 10;

	//creates a connection to the database, call this everytime you do a database query
	private Connection createConnection(){
		Connection conn = null;
		try {
			Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
			//conn = DriverManager.getConnection("jdbc:derby:C:/CS320/library.db;create=true");
			conn = DriverManager.getConnection("jdbc:derby:C:/cs320/dblibrary.db;create=true");

			conn.setAutoCommit(false);

			return conn;
		} catch (SQLException | ClassNotFoundException e) {
			System.out.println("Error: " + e.getMessage());
			return null;
		} 
	}
	@Override
	public Integer insertSongIntoSongsTable(final String title, final String location, final int albumId, final Artist artist, final int genreId) {
		return executeTransaction(new Transaction<Integer>() {
			@Override
			public Integer execute(Connection conn) throws SQLException {
				PreparedStatement stmt1 = null;
				PreparedStatement stmt2 = null;
				PreparedStatement stmt3 = null;
				PreparedStatement stmt4 = null;
				PreparedStatement stmt5 = null;
				PreparedStatement stmt6 = null;				

				ResultSet resultSet1 = null;
				//	(unused)	ResultSet resultSet2 = null;
				ResultSet resultSet3 = null;
				//	(unused)	ResultSet resultSet4 = null;
				ResultSet resultSet5 = null;				
				//	(unused)	ResultSet resultSet6 = null;

				// for saving artist ID and song ID
				Integer artist_id = -1;
				Integer song_id   = -1;

				// try to retrieve artist_id (if it exists) from DB, for Artist's name, passed into query
				try {
					stmt1 = conn.prepareStatement(
							"select artist_id from artists " +
									"  where artist_name = ?"
							);
					stmt1.setString(1, artist.getArtistName());

					// execute the query, get the result
					resultSet1 = stmt1.executeQuery();


					// if Artist was found then save artist_id					
					if (resultSet1.next())
					{
						artist_id = resultSet1.getInt(1);
						System.out.println("Artist <" + artist.getArtistName() + "> found with ID: " + artist_id);						
					}
					else
					{
						System.out.println("Artist <" + artist.getArtistName() + "> not found");

						// if the Artist is new, insert new Artist into Artists table
						if (artist_id <= 0) {
							// prepare SQL insert statement to add Artist to Artists table
							stmt2 = conn.prepareStatement(
									"insert into artists (artist_name) " +
											"  values(?) "
									);
							stmt2.setString(1, artist.getArtistName());

							// execute the update
							stmt2.executeUpdate();

							System.out.println("New artist <" + artist.getArtistName() + "> inserted in Artists table");						

							// try to retrieve author_id for new Author - DB auto-generates author_id
							stmt3 = conn.prepareStatement(
									"select artist_id from artists " +
											"  where artist_name = ?"
									);
							stmt3.setString(1, artist.getArtistName());

							// execute the query							
							resultSet3 = stmt3.executeQuery();

							// get the result - there had better be one							
							if (resultSet3.next())
							{
								artist_id = resultSet3.getInt(1);
								System.out.println("New artist <" + artist.getArtistName() + "> ID: " + artist_id);						
							}
							else	// really should throw an exception here - the new artist should have been inserted, but we didn't find them
							{
								System.out.println("New artist <" + artist.getArtistName() + "> not found in Artists table (ID: " + artist_id);
							}
						}
					}

					// now insert new song into songs table
					// prepare SQL insert statement to add new song to songs table
					stmt4 = conn.prepareStatement(
							"insert into songs (song_title, song_location, album, artist, genre) " +
									"  values(?, ?, ?, ?, ?) "
							);
					stmt4.setString(1, title);
					stmt4.setString(2, location);
					stmt4.setInt(3, albumId);
					stmt4.setInt(4, artist.getArtistId());
					stmt4.setInt(5, genreId);

					// execute the update
					stmt4.executeUpdate();

					System.out.println("New song <" + title + "> inserted into Songs table");					

					// now retrieve song_id for new song, so that we can set up SongArtist entry
					// and return the song_id, which the DB auto-generates
					// prepare SQL statement to retrieve book_id for new Book
					stmt5 = conn.prepareStatement(
							"select song_id from books " +
									"  where title = ? and location = ? "
							);
					stmt5.setString(1, title);
					stmt5.setString(2, location);

					// execute the query
					resultSet5 = stmt5.executeQuery();

					// get the result - there had better be one
					if (resultSet5.next())
					{
						song_id = resultSet5.getInt(1);
						System.out.println("New song <" + title + "> ID: " + song_id);						
					}
					else	// really should throw an exception here - the new song should have been inserted, but we didn't find it
					{
						System.out.println("New song <" + title + "> not found in Songs table (ID: " + song_id);
					}

					// now that we have all the information, insert entry into BookAuthors table
					// which is the junction table for Books and Authors
					// prepare SQL insert statement to add new Book to Books table
					/*stmt6 = conn.prepareStatement(
							"insert into bookAuthors (book_id, author_id) " +
							"  values(?, ?) "
					);
					stmt6.setInt(1, book_id);
					stmt6.setInt(2, author_id);

					// execute the update
					stmt6.executeUpdate();

					System.out.println("New enry for book ID <" + book_id + "> and author ID <" + author_id + "> inserted into BookAuthors junction table");						

					System.out.println("New book <" + title + "> inserted into Books table");					
					 */
					return song_id;
				} finally {
					DBUtil.closeQuietly(resultSet1);
					DBUtil.closeQuietly(stmt1);
					//	(unused)		DBUtil.closeQuietly(resultSet2);
					DBUtil.closeQuietly(stmt2);					
					DBUtil.closeQuietly(resultSet3);
					DBUtil.closeQuietly(stmt3);					
					// (unused)			DBUtil.closeQuietly(resultSet4);
					DBUtil.closeQuietly(stmt4);
					DBUtil.closeQuietly(resultSet5);
					DBUtil.closeQuietly(stmt5);
					// (unused)			DBUtil.closeQuietly(resultSet6);
					DBUtil.closeQuietly(stmt6);
				}
			}
		});
	}

	@Override
	public Integer insertPlaylistIntoPlaylistsTable(final String title, final int ownerId) throws SQLException{
		return executeTransaction(new Transaction<Integer>(){
			@Override
			public Integer execute(Connection conn) throws SQLException
			{
				PreparedStatement stmt = null;
				PreparedStatement stmt2 = null;
				//ResultSet resultSet = null; //never used
				ResultSet resultSet2 = null;

				//for saving playlist id
				Integer playlist_id = -1;

				try
				{
					stmt = conn.prepareStatement(
							"insert into playlists (playlist_title, number_songs, user_ownerid) values (?, ?, ?)");
					stmt.setString(1, title);
					stmt.setInt(2, 0);
					stmt.setInt(3, ownerId);

					stmt.executeUpdate();

					System.out.println("New playlist <"+title+"> inserted into Playlist table");

					stmt2 = conn.prepareStatement(
							"select playlist_id from playlists " +
									" where title = ? and ownerId = ? "

							);
					stmt2.setString(1, title);
					stmt2.setInt(2, ownerId);

					resultSet2 = stmt2.executeQuery();

					if(resultSet2.next())
					{
						playlist_id = resultSet2.getInt(1);
						System.out.println("New playlist <"+title+"> ID: " + playlist_id);
					}
					else
					{
						System.out.println("New playlist <"+title+"> not found in playlist table (ID: "+playlist_id);
					}

					//Does not do anything with junction table at this time

					return playlist_id;
				}
				finally
				{
					//DBUtil.closeQuietly(resultSet);
					DBUtil.closeQuietly(stmt);
				}

			}
		});
	}

	@Override
	public Integer insertGenreIntoGenresTable(final String genre) throws SQLException{
		return executeTransaction(new Transaction<Integer>(){
			@Override
			public Integer execute(Connection conn) throws SQLException{

				PreparedStatement stmt = null;
				PreparedStatement stmt2 = null;

				//ResultSet resultSet = null; //unused
				ResultSet resultSet2 = null;

				Integer genre_id = -1;

				try{
					stmt = conn.prepareStatement("insert into genres (genre_title) values (?)");
					stmt.setString(1, genre);

					stmt.executeUpdate();

					System.out.println("New genre <"+genre+"> inserted into genres table");

					stmt2 = conn.prepareStatement(
							"select genre_id from genres "+
									" where genre_title = ?"
							);

					stmt2.setString(1, genre);
					resultSet2 = stmt2.executeQuery();

					if(resultSet2.next()){
						genre_id = resultSet2.getInt(1);
						System.out.println("New genre <"+genre+"> ID: "+genre_id);
					}else{
						System.out.println("New genre <"+genre+"> not found in genres table (ID: "+ genre_id);
					}

					return genre_id;

				}finally{

					//DBUtil.closeQuietly(resultSet);
					DBUtil.closeQuietly(resultSet2);
					DBUtil.closeQuietly(stmt);
					DBUtil.closeQuietly(stmt2);
				}

			}
		});

	}

	@Override
	public Integer insertArtistIntoArtistsTable(final String name) throws SQLException{
		return executeTransaction(new Transaction<Integer>(){
			@Override
			public Integer execute(Connection conn) throws SQLException{

				PreparedStatement stmt = null;
				PreparedStatement stmt2 = null;

				//ResultSet resultSet = null; //unused
				ResultSet resultSet2 = null;

				Integer artist_id = -1;

				try{
					stmt = conn.prepareStatement("insert into artists (artist_name) values (?)");
					stmt.setString(1, name);

					stmt.executeUpdate();

					System.out.println("New artist <"+name+"> inserted into artists table");

					stmt2 = conn.prepareStatement(
							"select artist_id from artists "+
									" where artist_name = ?"
							);

					stmt2.setString(1, name);
					resultSet2 = stmt2.executeQuery();

					if(resultSet2.next()){
						artist_id = resultSet2.getInt(1);
						System.out.println("New artist <"+name+"> ID: "+artist_id);
					}else{
						System.out.println("New artist <"+name+"> not found in artists table (ID: "+ artist_id);
					}

					return artist_id;

				}finally{

					//DBUtil.closeQuietly(resultSet);
					DBUtil.closeQuietly(resultSet2);
					DBUtil.closeQuietly(stmt);
					DBUtil.closeQuietly(stmt2);
				}

			}
		});

	}

	@Override
	public Integer insertAlbumIntoAlbumsTable(final String title) throws SQLException{
		return executeTransaction(new Transaction<Integer>(){
			@Override
			public Integer execute(Connection conn) throws SQLException{

				PreparedStatement stmt = null;
				PreparedStatement stmt2 = null;

				//ResultSet resultSet = null; //unused
				ResultSet resultSet2 = null;

				Integer album_id = -1;

				try{
					stmt = conn.prepareStatement("insert into albums (album_title) values (?)");
					stmt.setString(1, title);

					stmt.executeUpdate();

					System.out.println("New album <"+title+"> inserted into albums table");

					stmt2 = conn.prepareStatement(
							"select album_id from albums "+
									" where album_title = ?"
							);

					stmt2.setString(1, title);
					resultSet2 = stmt2.executeQuery();

					if(resultSet2.next()){
						album_id = resultSet2.getInt(1);
						System.out.println("New album <"+title+"> ID: "+album_id);
					}else{
						System.out.println("New album <"+title+"> not found in artists table (ID: "+ album_id);
					}

					return album_id;

				}finally{

					//DBUtil.closeQuietly(resultSet);
					DBUtil.closeQuietly(resultSet2);
					DBUtil.closeQuietly(stmt);
					DBUtil.closeQuietly(stmt2);
				}

			}
		});

	}

	@Override
	public Integer insertAccountIntoAccountsTable(final String username, final String password)
	{
		return executeTransaction(new Transaction<Integer>(){
			@Override
			public Integer execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				PreparedStatement stmt2 = null;

				//ResultSet resultSet = null; //unused
				ResultSet resultSet2 = null;

				Integer user_id = -1;

				try{
					stmt = conn.prepareStatement(
							"insert into accounts (username, password) "+
									" values (?, ?) "
							);
					stmt.setString(1, username);
					stmt.setString(2, password);

					stmt.executeUpdate();
					System.out.println("New account <"+username+"> inserted into accounts table");

					//now get user_id
					stmt2 = conn.prepareStatement(
							"select user_id from accounts "+
									" where username = ? and password = ? "
							);
					stmt2.setString(1, username);
					stmt2.setString(2, password);

					resultSet2 = stmt2.executeQuery();

					if(resultSet2.next()){
						user_id = resultSet2.getInt(1);
						System.out.println("New user <"+username+"> ID: "+user_id);
					}
					else
					{
						System.out.println("New user <"+username+"> not found in the accounts table (ID: "+user_id);
					}

					return user_id;	

				}finally{
					DBUtil.closeQuietly(resultSet2);
					DBUtil.closeQuietly(stmt2);
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}


	//TODO: Hardcore test this method. My first SQL Statement might be wonky.

	// Also, this assumes THAT A SONG EXISTS THAT WE ARE TRYING TO INSERT. IF A SONG DOES NOT EXIST DO NOT CALL THIS METHOD.
	// Works if playlist does not exist. 

	@Override
	public Integer insertSongIntoPlaylist(final String plTitle, final int ownerId, final String songTitle, final int albumId, final int artistId, final int genreId) throws SQLException
	{
		return executeTransaction(new Transaction<Integer>(){
			@Override
			public Integer execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				PreparedStatement stmt2 = null;
				PreparedStatement stmt3 = null;
				PreparedStatement stmt4 = null;
				PreparedStatement stmt5 = null;

				ResultSet resultSet = null;
				//ResultSet resultSet2 = null; //unused
				ResultSet resultSet3 = null;
				ResultSet resultSet4 = null;
				//ResultSet resultSet5 = null; //unused

				Integer playlist_id = -1;
				Integer song_id = -1;

				try{
					//try to retrieve playlist_id
					stmt = conn.prepareStatement(
							"select playlist_id from playlists "+
									" where playlist_title = ? and user_ownerid = ? "
							);

					stmt.setString(1, plTitle);
					stmt.setInt(2, ownerId);

					//execute query, get result
					resultSet = stmt.executeQuery();

					if(resultSet.next())
					{
						playlist_id = resultSet.getInt(1);
						System.out.println("Playlist <"+plTitle+"> found with ID: "+playlist_id);
					}else{
						System.out.println("Playlist <"+plTitle+"> not found");
						System.out.println("Automatically creating playlist for you...");

						if(playlist_id <= 0){
							stmt2 = conn.prepareStatement(
									"insert into playlists (playlist_title, number_songs, user_ownerid) "+
											" values(?, ?, ?) "
									);
							stmt2.setString(1, plTitle);
							stmt2.setInt(2, 0);
							stmt2.setInt(3, ownerId);

							stmt2.executeUpdate();

							System.out.println("New playlist <"+plTitle+"> inserted into playlists table");

							// try to retrieve playlist_id for new playlist

							stmt3 = conn.prepareStatement(
									"select playlist_id from playlists "+
											" where playlist_title = ? and user_ownerid = ? "										
									);
							stmt3.setString(1, plTitle);
							stmt3.setInt(2, ownerId);

							resultSet3 = stmt3.executeQuery();

							if(resultSet3.next()){
								playlist_id = resultSet3.getInt(1);
								System.out.println("New playlist <"+plTitle+"> ID: "+playlist_id);
							}
							else //Should throw an exception instead of the println, but also should never occur
							{
								System.out.println("New playlist <" +plTitle+ "> not found in playlist table (ID: "+playlist_id);
								System.out.println("This message should literally never be displayed, if it is then there's something horribly wrong with stmt3");
							}

						}

					}

					//TODO: Note, does not include song 'location'
					// Get song_id
					stmt4 = conn.prepareStatement(
							"select song_id from songs "+
									" where song_title = ? and album = ? "+
									" and artist = ? and genre = ? "
							);

					stmt4.setString(1, songTitle);
					stmt4.setInt(2, albumId);
					stmt4.setInt(3, artistId);
					stmt4.setInt(4, genreId);

					resultSet4 = stmt4.executeQuery();

					if(resultSet4.next()){
						song_id = resultSet4.getInt(1);
						System.out.println("Song <"+songTitle+"> found, ID: "+song_id);
					}
					else //Should throw an exception instead of the println, but also should never occur
					{
						System.out.println("Song <"+songTitle+"> not found in songs table (ID: "+song_id);
						System.out.println("This message should literally never be displayed, if it is then there's something horribly wrong with stmt4");
					}

					//Now we have song_id and playlist_id. The rest is cake.

					stmt5 = conn.prepareStatement(
							"insert into playlistsongs (playlist_id, song_id) "+
									" values(?, ?) "
							);
					stmt5.setInt(1, playlist_id);
					stmt5.setInt(2, song_id);

					stmt5.executeUpdate();

					System.out.println("New entry for Song <"+songTitle+"> inserted into playlist <"+plTitle+"> via playlistsongs table");


					return song_id;	

				}finally{

					DBUtil.closeQuietly(resultSet);
					//DBUtil.closeQuietly(resultSet2); //unused
					DBUtil.closeQuietly(resultSet3);
					DBUtil.closeQuietly(resultSet4);
					//DBUtil.closeQuietly(resultSet5); //unused
					DBUtil.closeQuietly(stmt5);
					DBUtil.closeQuietly(stmt4);
					DBUtil.closeQuietly(stmt3);
					DBUtil.closeQuietly(stmt2);
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}

	@Override
	public List<Artist> findAllArtists(){
		return executeTransaction(new Transaction<List<Artist>>() {
			@Override
			public List<Artist> execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet resultSet = null;

				try {
					stmt = conn.prepareStatement(
							"select * from artists " +
									" order by artist_name asc"
							);

					List<Artist> result = new ArrayList<Artist>();

					resultSet = stmt.executeQuery();

					// for testing that a result was returned
					Boolean found = false;

					while (resultSet.next()) {
						found = true;

						Artist artist = new Artist();
						loadArtist(artist, resultSet, 1);

						result.add(artist);
					}

					// check if any playlists were found
					if (!found) {
						System.out.println("No artists were found in the database");
					}

					return result;
				} finally {
					DBUtil.closeQuietly(resultSet);
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}

	@Override
	public List<Genre> findAllGenres()
	{
		return executeTransaction(new Transaction<List<Genre>>() {
			@Override
			public List<Genre> execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet resultSet = null;

				try {
					stmt = conn.prepareStatement(
							"select * from genres " +
									" order by genre_title asc"
							);

					List<Genre> result = new ArrayList<Genre>();

					resultSet = stmt.executeQuery();

					// for testing that a result was returned
					Boolean found = false;

					while (resultSet.next()) {
						found = true;

						Genre genre = new Genre();
						loadGenre(genre, resultSet, 1);

						result.add(genre);
					}

					// check if any playlists were found
					if (!found) {
						System.out.println("No genres were found in the database");
					}

					return result;
				} finally {
					DBUtil.closeQuietly(resultSet);
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}



	public List<Album> findAllAlbums()
	{
		return executeTransaction(new Transaction<List<Album>>() {
			@Override
			public List<Album> execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet resultSet = null;

				try {
					stmt = conn.prepareStatement(
							"select * from albums " +
									" order by album_title asc"
							);

					List<Album> result = new ArrayList<Album>();

					resultSet = stmt.executeQuery();

					// for testing that a result was returned
					Boolean found = false;

					while (resultSet.next()) {
						found = true;

						Album album = new Album();
						loadAlbum(album, resultSet, 1);

						result.add(album);
					}

					// check if any playlists were found
					if (!found) {
						System.out.println("No albums were found in the database");
					}

					return result;
				} finally {
					DBUtil.closeQuietly(resultSet);
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}

	@Override
	public List<Playlist> findPlaylistByTitle(String title)
	{
		return executeTransaction(new Transaction<List<Playlist>>()
		{
			@Override
			public List<Playlist> execute(Connection conn) throws SQLException
			{
				PreparedStatement stmt = null;
				ResultSet resultSet = null;

				try{

					stmt = conn.prepareStatement(
							"select * from playlistsongs "+
									"where playlists.playlist_title = ?"
							);
					List<Playlist> result = new ArrayList<Playlist>();
					resultSet = stmt.executeQuery();

					//execute query, get results, put them into a list, return list

					while(resultSet.next())
					{
						//TODO: CHECK WHY INDEX IS 1
						Playlist pl = new Playlist();
						loadPlaylist(pl, resultSet, 1);

						//also check if this is right
						//(should be right)

						result.add(pl);
					}

					return result;

				}finally{
					DBUtil.closeQuietly(resultSet);
					DBUtil.closeQuietly(stmt);
				}

			}

		});
	}

	@Override
	public List<Playlist> findAllPlaylists() 
	{
		return executeTransaction(new Transaction<List<Playlist>>() {
			@Override
			public List<Playlist> execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet resultSet = null;

				try {
					System.out.println("looking for playlists");
					stmt = conn.prepareStatement(
							"select * from playlists "
							//" order by playlist_title asc"
							);
					System.out.println("found playlists");
					List<Playlist> result = new ArrayList<Playlist>();
					System.out.println("created list");
					resultSet = stmt.executeQuery();
					System.out.println("executed query");
					// for testing that a result was returned
					Boolean found = false;
					System.out.println("starting the loop");
					while (resultSet.next()) {
						found = true;
						System.out.println("found was set to true");
						Playlist playlist = new Playlist();
						System.out.println("created a playlist");
						loadPlaylist(playlist, resultSet, 1);
						System.out.println("loaded a playlist!!!!!!!!!1111one");
						result.add(playlist);
						System.out.println("added a playlist");
					}

					// check if any playlists were found
					if (!found) {
						System.out.println("No playlists were found in the database");
					}

					return result;
				} finally {
					DBUtil.closeQuietly(resultSet);
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}

	@Override
	public List<Song> findAllSongs() 
	{
		return executeTransaction(new Transaction<List<Song>>() {
			@Override
			public List<Song> execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet resultSet = null;

				try {
					stmt = conn.prepareStatement(
							"select * from songs " +
									" order by song_title asc"
							);

					List<Song> result = new ArrayList<Song>();

					resultSet = stmt.executeQuery();

					// for testing that a result was returned
					Boolean found = false;

					while (resultSet.next()) {
						found = true;

						Song song = new Song();
						loadSong(song, resultSet, 1);

						result.add(song);
					}

					// check if any songs were found
					if (!found) {
						System.out.println("No songs were found in the database");
					}

					return result;
				} finally {
					DBUtil.closeQuietly(resultSet);
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}


	//This is kind of a really dangerous method to have
	//But since this will never reach the public... Who cares about security!

	@Override
	public List<Account> findAllAccounts(){
		return executeTransaction(new Transaction<List<Account>>() {
			@Override
			public List<Account> execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet resultSet = null;

				try {
					System.out.println("looking for all users in the db");
					stmt = conn.prepareStatement(
							"select * from accounts " +
									" order by username asc, password asc"
							);

					List<Account> result = new ArrayList<Account>();

					resultSet = stmt.executeQuery();

					// for testing that a result was returned
					Boolean found = false;

					while (resultSet.next()) {
						found = true;

						Account account = new Account();
						loadAccount(account, resultSet, 1);

						result.add(account);
					}

					// check if any accounts were found
					if (!found) {
						System.out.println("No accounts were found in the database");
					}

					return result;
				} finally {
					DBUtil.closeQuietly(resultSet);
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}

	@Override
	public List<Song> findSongByTitle(final String title)
	{
		return executeTransaction(new Transaction<List<Song>>()
		{
			@Override
			public List<Song> execute(Connection conn) throws SQLException
			{
				PreparedStatement stmt = null;
				ResultSet resultSet = null;

				try{

					stmt = conn.prepareStatement(
							"select * from songs "+
									"where songs.song_title = ?"
							);
					stmt.setString(1, title);
					List<Song> result = new ArrayList<Song>();
					resultSet = stmt.executeQuery();

					//execute query, get results, put them into a list, return list

					while(resultSet.next())
					{
						//TODO: CHECK WHY INDEX IS 1
						Song sl = new Song();
						loadSong(sl, resultSet, 1);


						//TODO: also check if this is right
						result.add(sl);
					}

					return result;

				}finally{
					DBUtil.closeQuietly(resultSet);
					DBUtil.closeQuietly(stmt);
				}

			}

		});
	}

	@Override
	public List<Song> findSongsByPlaylistTitle(final String title)
	{
		return executeTransaction(new Transaction<List<Song>>()
		{
			@Override
			public List<Song> execute(Connection conn) throws SQLException
			{
				PreparedStatement stmt = null;
				ResultSet resultSet = null;

				try
				{

					stmt = conn.prepareStatement(
							//Shows every column in songs 
							// (song_id, title, location, album_id, artist_id, genre_id
							"select songs.* "+
							" from songs, playlists, playListSongs "+
							" where playlists.playlist_title = ? "+
							" and songs.song_id = playListSongs.song_id "+
							" and playlists.playlist_id = playListSongs.playlist_id "

							);

					stmt.setString(1, title);
					List<Song> result = new ArrayList<Song>();
					System.out.println("executing findSongsByPlaylistTitle");
					resultSet = stmt.executeQuery();
					System.out.println("executed findSongsByPlaylistTitle");
					// for testing that a result was returned

					Boolean found = false;

					while(resultSet.next())
					{
						//TODO: CHECK WHY INDICIES ARE 1 AND 4?

						found = true;
						//System.out.println("loading songs"); //annoying spam statement
						Song song = new Song();
						loadSong(song, resultSet, 1);
						//System.out.println("songs loaded");  //annoying spam statement
						result.add(song);

					}

					// check if title was found
					if(!found)
					{
						System.out.println("<"+title+"> was not found in the playlists table");
					}

					return result;

				}finally{
					DBUtil.closeQuietly(resultSet);
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}

	@Override
	public List<Pair<Song, Artist>> findSongByArtistName(final int artistId)
	{
		return executeTransaction(new Transaction<List<Pair<Song,Artist>>>() {
			@Override
			public List<Pair<Song, Artist>> execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet resultSet = null;

				// try to retrieve songs based on artist name

				try {
					stmt = conn.prepareStatement(
							"select songs.* " +
									"  from songs " +
									"  where songs.artist = ? "
							);

					stmt.setInt(1, artistId);

					// establish the list of (Author, Book) Pairs to receive the result
					List<Pair<Song, Artist>> result = new ArrayList<Pair<Song,Artist>>();

					// execute the query, get the results, and assemble them in an ArrayLsit
					resultSet = stmt.executeQuery();
					while (resultSet.next()) {
						Artist artist = new Artist();
						loadArtist(artist, resultSet, 1);	//this is at an index of 1
						Song song = new Song();
						loadSong(song, resultSet, 4);	//this is at an index of 4

						result.add(new Pair<Song, Artist>(song, artist));
					}

					return result;
				} finally {
					DBUtil.closeQuietly(resultSet);
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}


	@Override
	public List<Artist> findArtistBySongTitle(final String title, final String name)
	{
		return executeTransaction(new Transaction<List<Artist>>()
		{
			@Override
			public List<Artist> execute(Connection conn) throws SQLException{

				PreparedStatement stmt = null;
				ResultSet resultSet = null;
				PreparedStatement stmt2 = null;
				ResultSet resultSet2 = null;
				PreparedStatement stmt3 = null;
				//ResultSet resultSet3 = null; //unused
				PreparedStatement stmt4 = null;
				ResultSet resultSet4 = null;

				Integer artist_id = -1;

				try{

					stmt2 = conn.prepareStatement(
							"select songs.artist "+
									" from songs "+
									" where songs.song_title = ?"
							);

					stmt2.setString(1, title);

					//We now have ints
					resultSet2 = stmt2.executeQuery();

					if(resultSet2.next()){
						artist_id = resultSet2.getInt(1);
						System.out.println("Artist <"+name+"> found with ID: "+artist_id);
					}else{
						System.out.println("Artist <"+name+"> not found");

						if(artist_id <= 0){
							stmt3 = conn.prepareStatement(
									"insert into artists (artist_name)"+
											" values(?) "
									);
							stmt3.setString(1, name);
							stmt3.executeUpdate();

							System.out.println("New artist <"+name+"> inserted into artist table");

							stmt4 = conn.prepareStatement(
									"select artist_id from artists " +
											" where artist_name = ? "
									);
							stmt4.setString(1, name);

							resultSet4 = stmt4.executeQuery();

							if(resultSet4.next()){
								artist_id = resultSet4.getInt(1);
								System.out.println("New artist <"+name+"> ID: "+artist_id);
							}else{
								System.out.println("Just created the artist required. Should never see this message.");
							}

						}

					}

					stmt = conn.prepareStatement(
							"select artists.* "+
									" from artists "+
									" where artist_id = ? "
							);

					stmt.setInt(1, artist_id);		

					List<Artist> result = new ArrayList<Artist>();

					resultSet = stmt.executeQuery();

					while(resultSet.next()){
						Artist artist = new Artist();
						loadArtist(artist, resultSet, 1);

						result.add(artist);
					}	

					return result;

				}finally{
					DBUtil.closeQuietly(resultSet);
					DBUtil.closeQuietly(resultSet2);
					DBUtil.closeQuietly(resultSet4);
					DBUtil.closeQuietly(stmt);
					DBUtil.closeQuietly(stmt2);
					DBUtil.closeQuietly(stmt3);
					DBUtil.closeQuietly(stmt4);
				}

			}
		});
	}

	@Override
	public List<Album> findAlbumBySongTitle(final String title, final String albumTitle)
	{
		return executeTransaction(new Transaction<List<Album>>()
		{
			@Override
			public List<Album> execute(Connection conn) throws SQLException{

				PreparedStatement stmt = null;
				ResultSet resultSet = null;
				PreparedStatement stmt2 = null;
				ResultSet resultSet2 = null;
				PreparedStatement stmt3 = null;
				//ResultSet resultSet3 = null; //unused
				PreparedStatement stmt4 = null;
				ResultSet resultSet4 = null;

				Integer album_id = -1;

				try{

					stmt2 = conn.prepareStatement(
							"select songs.album "+
									" from songs "+
									" where songs.song_title = ?"
							);

					stmt2.setString(1, title);

					//We now have ints
					resultSet2 = stmt2.executeQuery();

					if(resultSet2.next()){
						album_id = resultSet2.getInt(1);
						System.out.println("Album <"+albumTitle+"> found with ID: "+album_id);
					}else{
						System.out.println("Album <"+albumTitle+"> not found");

						if(album_id <= 0){
							stmt3 = conn.prepareStatement(
									"insert into albums (album_title)"+
											" values(?) "
									);
							stmt3.setString(1, albumTitle);
							stmt3.executeUpdate();

							System.out.println("New album <"+albumTitle+"> inserted into album table");

							stmt4 = conn.prepareStatement(
									"select album_id from albums " +
											" where album_title = ? "
									);
							stmt4.setString(1, albumTitle);

							resultSet4 = stmt4.executeQuery();

							if(resultSet4.next()){
								album_id = resultSet4.getInt(1);
								System.out.println("New album <"+albumTitle+"> ID: "+album_id);
							}else{
								System.out.println("Just created the album required. Should never see this message.");
							}

						}

					}

					stmt = conn.prepareStatement(
							"select albums.* "+
									" from albums "+
									" where album_id = ? "
							);

					stmt.setInt(1, album_id);		

					List<Album> result = new ArrayList<Album>();

					resultSet = stmt.executeQuery();

					while(resultSet.next()){
						Album album = new Album();
						loadAlbum(album, resultSet, 1);

						result.add(album);
					}	

					return result;

				}finally{
					DBUtil.closeQuietly(resultSet);
					DBUtil.closeQuietly(resultSet2);
					DBUtil.closeQuietly(resultSet4);
					DBUtil.closeQuietly(stmt);
					DBUtil.closeQuietly(stmt2);
					DBUtil.closeQuietly(stmt3);
					DBUtil.closeQuietly(stmt4);
				}

			}
		});
	}

	@Override															//genre name
	public List<Genre> findGenreBySongTitle(final String title, final String name)
	{
		return executeTransaction(new Transaction<List<Genre>>()
		{
			@Override
			public List<Genre> execute(Connection conn) throws SQLException{

				PreparedStatement stmt = null;
				ResultSet resultSet = null;
				PreparedStatement stmt2 = null;
				ResultSet resultSet2 = null;
				PreparedStatement stmt3 = null;
				//ResultSet resultSet3 = null; //unused
				PreparedStatement stmt4 = null;
				ResultSet resultSet4 = null;

				Integer genre_id = -1;

				try{

					stmt2 = conn.prepareStatement(
							"select songs.genre "+
									" from songs "+
									" where songs.song_title = ?"
							);

					stmt2.setString(1, title);

					//We now have ints
					resultSet2 = stmt2.executeQuery();

					if(resultSet2.next()){
						genre_id = resultSet2.getInt(1);
						System.out.println("Genre <"+name+"> found with ID: "+genre_id);
					}else{
						System.out.println("Genre <"+name+"> not found");

						if(genre_id <= 0){
							stmt3 = conn.prepareStatement(
									"insert into genres (genre_title)"+
											" values(?) "
									);
							stmt3.setString(1, name);
							stmt3.executeUpdate();

							System.out.println("New genre <"+name+"> inserted into genres table");

							stmt4 = conn.prepareStatement(
									"select genre_id from genres " +
											" where genre_title = ? "
									);
							stmt4.setString(1, name);

							resultSet4 = stmt4.executeQuery();

							if(resultSet4.next()){
								genre_id = resultSet4.getInt(1);
								System.out.println("New genre <"+name+"> ID: "+genre_id);
							}else{
								System.out.println("Just created the genre required. Should never see this message.");
							}

						}

					}

					stmt = conn.prepareStatement(
							"select genres.* "+
									" from genres "+
									" where genre_id = ? "
							);

					stmt.setInt(1, genre_id);		

					List<Genre> result = new ArrayList<Genre>();

					resultSet = stmt.executeQuery();

					while(resultSet.next()){
						Genre genre = new Genre();
						loadGenre(genre, resultSet, 1);

						result.add(genre);
					}	

					return result;

				}finally{
					DBUtil.closeQuietly(resultSet);
					DBUtil.closeQuietly(resultSet2);
					DBUtil.closeQuietly(resultSet4);
					DBUtil.closeQuietly(stmt);
					DBUtil.closeQuietly(stmt2);
					DBUtil.closeQuietly(stmt3);
					DBUtil.closeQuietly(stmt4);
				}

			}
		});
	}

	@Override
																		//password not necessary but nice safety feature
	public List<Playlist> findPlaylistsByAccount(final String username, final String password)
	{
		return executeTransaction(new Transaction<List<Playlist>>()
		{
			@Override
			public List<Playlist> execute(Connection conn) throws SQLException
			{
				PreparedStatement stmt = null;
				PreparedStatement stmt2 = null;

				ResultSet resultSet = null;
				ResultSet resultSet2 = null;


				Integer user_id = -1;
				
				try{
					
					//first get the user_id
					stmt2 = conn.prepareStatement(
							"select user_id "+
									" from accounts "+
									" where username = ? and password = ? "
							);

					stmt2.setString(1, username);
					stmt2.setString(2, password);

					//We now have ints
					resultSet2 = stmt2.executeQuery();

					//Here we would test if the user_id doesn't exist
					//but there's no way we would be calling this method if it didn't
					//R-right?
					
					//if(resultSet2.next()){
					user_id = resultSet2.getInt(1);
					//}else{
					//(would have created it or thrown exception)
					//}
					
					//Since we don't need to check anything, we can just jump right
					//into the next statement
					
					stmt = conn.prepareStatement(
							"select playlists.playlist_title"+
									" from playlists "+
									" where user_ownerid = ?"
							);
					
					stmt.setInt(1, user_id);
					

					List<Playlist> result = new ArrayList<Playlist>();

					resultSet = stmt.executeQuery();

					while(resultSet.next()){
						Playlist pl = new Playlist();
						loadPlaylist(pl, resultSet, 1);

						result.add(pl);
					}	

					return result;

				}finally{
					DBUtil.closeQuietly(resultSet2);
					DBUtil.closeQuietly(resultSet);
					DBUtil.closeQuietly(stmt);
					DBUtil.closeQuietly(stmt2);
				}

			}

		});
	}
	
	
	// deletes song (and possibly artist) from library

	@Override
	public List<Artist> removeSongByTitle(final String title)
	{
		return executeTransaction(new Transaction<List<Artist>>(){
			@Override
			public List<Artist> execute(Connection conn) throws SQLException {
				PreparedStatement stmt1 = null;
				PreparedStatement stmt2 = null;
				PreparedStatement stmt3 = null;
				PreparedStatement stmt4 = null;
				PreparedStatement stmt5 = null;
				PreparedStatement stmt6 = null;

				ResultSet resultSet1 = null;
				ResultSet resultSet2 = null;
				//ResultSet resultSet3 = null;
				//ResultSet resultSet4 = null;
				ResultSet resultSet5 = null;
				//Resultset resultSet6 = null;

				try{
					//First get Artist of Song to be deleted
					//in the event that it's the only song by
					//this artist, in which case we remove the artist as well
					stmt1 = conn.prepareStatement(
							"select artists.* " +
									"  from  artists, songs " +
									"  where songs.song_title = ? " +
									"    and artists.artist_id = songs.artist "
							);

					// get the song's artist
					stmt1.setString(1,  title);
					resultSet1 = stmt1.executeQuery();

					//assemble list of artists
					List<Artist> artists = new ArrayList<Artist>();

					while(resultSet1.next())
					{
						Artist artist = new Artist();
						loadArtist(artist, resultSet1, 1);
						artists.add(artist);
					}

					//check if artists are found
					if(artists.size() == 0)
					{
						System.out.println("No artist was found for song <" + title + "> in the database");
					}

					//now get song(s) to be deleted

					stmt2 = conn.prepareStatement(
							"select songs.* "+
									" from songs "+
									" where songs.song_title = ? "
							);

					//get song(s)
					stmt2.setString(1,  title);
					resultSet2 = stmt2.executeQuery();

					//create list of songs from query
					List<Song> songs = new ArrayList<Song>();

					while(resultSet2.next())
					{
						Song song = new Song();
						loadSong(song, resultSet2, 1);
						songs.add(song);
					}

					//delete the specified song from the junction table
					//(have to remove foreign key to be able to delete in
					// other tables)

					stmt3 = conn.prepareStatement(
							"delete from playlistsongs"+
									"where song_id = ?"
							);

					stmt3.setInt(1,  songs.get(0).getSongId());
					stmt3.executeUpdate();

					System.out.println("Deleted junction table entries for song(s) <"+ title +"> from DB");

					//Now we can finally delete the entries in the songs table
					stmt4 = conn.prepareStatement(
							"delete from songs " +
									" where song_title = ? "
							);

					//delete the song entries from the DB for this title
					stmt4.setString(1,  title);
					stmt4.executeUpdate();

					System.out.println("Deleted song(s) with title <" + title + "> from DB");

					//Now check to see if the artists have created other songs

					for(int i = 0; i < artists.size(); i++)
					{
						stmt5 = conn.prepareStatement(
								"select songs.* from songs"	+
										"where songs.artist = ?"
								);

						stmt5.setInt(1, songs.get(i).getArtistId());
						resultSet5 = stmt5.executeQuery();

						//if nothing is returned, delete artist
						if(!resultSet5.next())
						{
							//TODO: this might be wrong
							stmt6 = conn.prepareStatement(
									"delete from artists "+
											" where artists.artist_id = ?"
									);

							stmt6.setInt(1,  artists.get(i).getArtistId());
							stmt6.executeUpdate();

							System.out.println("Deleted artist <"+ artists.get(i).getArtistName()+ "> from DB");
							DBUtil.closeQuietly(stmt6);
						}

						//finally done with this, so close it
						DBUtil.closeQuietly(resultSet5);
						DBUtil.closeQuietly(stmt5);



					}
					return artists;

				} finally {
					DBUtil.closeQuietly(resultSet1);
					DBUtil.closeQuietly(resultSet2);
					DBUtil.closeQuietly(stmt1);
					DBUtil.closeQuietly(stmt2);
					DBUtil.closeQuietly(stmt3);
					DBUtil.closeQuietly(stmt4);

				}


			}
		});
	}

	@Override
	public List<Playlist> removePlaylistFromPlaylistTable(final String title){
		return executeTransaction(new Transaction<List<Playlist>>(){
			@Override
			public List<Playlist> execute (Connection conn) throws SQLException{
				PreparedStatement stmt1 = null;
				PreparedStatement stmt2 = null;
				PreparedStatement stmt3 = null;

				ResultSet resultSet1 = null;
				//ResultSet resultSet2 = null; //unused
				//ResultSet resultSet3 = null;

				try{

					//get playlists to be deleted
					//also need to get playlist id to remove it from playlistsongs

					stmt1 = conn.prepareStatement(
							"select playlists.* " +
									"  from  playlists " +
									"  where playlists.playlist_title = ? "
							);

					// get the playlists

					stmt1.setString(1, title);
					resultSet1 = stmt1.executeQuery();

					// assemble list of playlists
					List<Playlist> playlists = new ArrayList<Playlist>();

					while(resultSet1.next()){
						Playlist pl = new Playlist();
						loadPlaylist(pl, resultSet1, 1);
						playlists.add(pl);
					}

					// delete entries in playlistsongs

					stmt2 = conn.prepareStatement(
							"delete from playlistsongs " +
									" where playlist_id = ?"
							);

					// get playlist ID
					stmt2.setInt(1, playlists.get(0).getPlaylistId());
					stmt2.executeUpdate();

					System.out.println("Deleted junction table entries for playlist(s) <"+ title+" from DB");

					//Now remove entires from playlist table

					stmt3 = conn.prepareStatement(
							"delete from playlists " +
									" where playlist_title = ? "
							);

					stmt3.setString(1, title);
					stmt3.executeUpdate();

					System.out.println("Deleted playlist(s) with title <"+title+"> from DB");

					return playlists;

				}finally{

					DBUtil.closeQuietly(resultSet1);
					//DBUtil.closeQuietly(resultSet2);
					//DBUtil.closeQuietly(resultSet3);
					DBUtil.closeQuietly(stmt1);
					DBUtil.closeQuietly(stmt2);
					DBUtil.closeQuietly(stmt3);

				}		
			}
		});
	}

	// wrapper SQL transaction function that calls actual transaction function (which has retries)
	public<ResultType> ResultType executeTransaction(Transaction<ResultType> txn) {
		try {
			return doExecuteTransaction(txn);
		} catch (SQLException e) {
			throw new PersistenceException("Transaction failed", e);
		}
	}
	// SQL transaction function which retries the transaction MAX_ATTEMPTS times before failing
	public<ResultType> ResultType doExecuteTransaction(Transaction<ResultType> txn) throws SQLException {
		Connection conn = connect();

		try {
			int numAttempts = 0;
			boolean success = false;
			ResultType result = null;

			while (!success && numAttempts < MAX_ATTEMPTS) {
				try {
					result = txn.execute(conn);
					conn.commit();
					success = true;
				} catch (SQLException e) {
					if (e.getSQLState() != null && e.getSQLState().equals("41000")) {
						// Deadlock: retry (unless max retry count has been reached)
						numAttempts++;
					} else {
						// Some other kind of SQLException
						throw e;
					}
				}
			}

			if (!success) {
				throw new SQLException("Transaction failed (too many retries)");
			}

			// Success!
			return result;
		} finally {
			DBUtil.closeQuietly(conn);
		}
	}


	// Creates (what should be) all of the required tables
	// memberOfPl table is what connects songs & playlists via IDs

	public void createTables(){
		executeTransaction(new Transaction<Boolean>(){
			@Override
			public Boolean execute(Connection conn) throws SQLException {
				System.out.println("creating statements");
				PreparedStatement stmt1 = null;	//playlists table
				PreparedStatement stmt2 = null;	//songs table
				PreparedStatement stmt3 = null; //playlistsongs table
				PreparedStatement stmt4 = null;	//artists table
				PreparedStatement stmt5 = null; //genres table
				PreparedStatement stmt6 = null;	//albums table
				PreparedStatement stmt7 = null; //users table
				PreparedStatement checkPl = null; //checking if playlist table is made
				PreparedStatement checkSongs = null; //checking if songs table is made
				PreparedStatement checkPlSongs = null; //checking if playlistsongs table is made
				PreparedStatement checkArtists = null; //checking if artists table is made
				PreparedStatement checkGenres = null; //checking if genres table is made
				PreparedStatement checkAlbums = null; //checking if albums table is made
				PreparedStatement checkUsers = null; //checking if users table is made
				ResultSet checkPlResult = null; //result of checkPl
				ResultSet checkSongsResult = null; //result of checkSongs
				ResultSet checkPlSongsResult = null; //result of checkPlSongs
				ResultSet checkArtistsResult = null; //result of checkArtists
				ResultSet checkGenresResult = null; //result of checkGenres
				ResultSet checkAlbumsResult = null; //result of checkAlbums
				ResultSet checkUsersResult = null; //result of checkUsers

				try{
					// Working
					System.out.println("creating playlists table");

					checkPl = conn.prepareStatement(
							"select * from sys.systables where tablename = 'playlists'"	
							);
					checkPlResult = checkPl.executeQuery();

					if(!checkPlResult.next()){
						System.out.println("playlists table doesn't exist, creating it");
						stmt1 = conn.prepareStatement(
								"create table playlists (" +
										"	playlist_id integer primary key " +
										"		generated always as identity (start with 1, increment by 1), " +									
										"	playlist_title varchar(40)," +	//playlist title
										"   number_songs Integer," +
										"   user_ownerid Integer" +
										")"
								);
						stmt1.executeUpdate();
						System.out.println("platlists table created");
					}else{
						System.out.println("playlists table does exist, ignoring it");
					}
					// Working
					System.out.println("creating artists table");

					checkArtists = conn.prepareStatement(
							"select * from sys.systables where tablename = 'artists'"	
							);
					checkArtistsResult = checkArtists.executeQuery();

					if(!checkArtistsResult.next()){
						System.out.println("artists table doesn't exist, creating it");
						stmt4 = conn.prepareStatement(
								"create table artists (" +
										"	artist_id integer primary key " +
										"		generated always as identity (start with 1, increment by 1), " +									
										"	artist_name varchar(50)" +	//band name
										")"
								);
						stmt4.executeUpdate();
						System.out.println("artists table created");
					}else{
						System.out.println("artists table does exist, ignoring it");
					}	
					// Working
					System.out.println("creating genres table");
					checkGenres = conn.prepareStatement(
							"select * from sys.systables where tablename = 'genres'"	
							);
					checkGenresResult = checkGenres.executeQuery();

					if(!checkGenresResult.next()){
						System.out.println("genres table doesn't exist, creating it");
						stmt5 = conn.prepareStatement(
								"create table genres (" +
										"	genre_id integer primary key " +
										"		generated always as identity (start with 1, increment by 1), " +									
										"	genre_title varchar(20)" +	//genre
										")"
								);
						stmt5.executeUpdate();
						System.out.println("genres table created");
					}else{
						System.out.println("genres table does exist, ignoring it");
					}

					// Working
					System.out.println("creating albums table");

					checkAlbums = conn.prepareStatement(
							"select * from sys.systables where tablename = 'albums'"	
							);
					checkAlbumsResult = checkAlbums.executeQuery();

					if(!checkAlbumsResult.next()){
						System.out.println("albums table doesn't exist, creating it");
						stmt6 = conn.prepareStatement(
								"create table albums (" +
										"	album_id integer primary key " +
										"		generated always as identity (start with 1, increment by 1), " +									
										"	album_title varchar(50)" +	//album title
										")"
								);

						stmt6.executeUpdate();
						System.out.println("albums table created");
					}else{
						System.out.println("albums table does exist, ignoring it");
					}
					// Working
					System.out.println("creating songs table");

					checkSongs = conn.prepareStatement(
							"select * from sys.systables where tablename = 'songs'"	
							);
					checkSongsResult = checkSongs.executeQuery();

					if(!checkSongsResult.next()){
						System.out.println("songs table doesn't exist, creating it");
						stmt2 = conn.prepareStatement(
								"create table songs (" +
										"	song_id integer primary key " +
										"		generated always as identity (start with 1, increment by 1), " +
										"	song_title varchar(50)," +	//song title
										"   song_location varchar(50)," +
										"	album integer constraint album_id references albums, "+ 		//foreign key album_id
										"	artist integer constraint artist_id references artists, " +	//foreign key artist_id
										"	genre integer constraint genre_id references genres " +		//foreign key genre_id
										")"
								);
						stmt2.executeUpdate();
						System.out.println("songs table created");
					}else{
						System.out.println("songs table does exist, ignoring it");
					}
					// Working
					System.out.println("creating playlistsongs table");

					checkPlSongs = conn.prepareStatement(
							"select * from sys.systables where tablename = 'playlistsongs'"	
							);
					checkPlSongsResult = checkPlSongs.executeQuery();

					if(!checkPlSongsResult.next()){
						System.out.println("playlistsongs table doesn't exist, creating it");
						stmt3 = conn.prepareStatement(
								"create table playlistsongs (" +
										//"	playlist_id integer constraint playlist_id references playlists, " +	//these 2 lines are getting foreign keys
										//"	song_id integer constraint song_id references songs " +
										"playlist_id integer, " +
										"song_id integer " +
										")"		
								);
						stmt3.executeUpdate();
						System.out.println("playlistsongs table created");
					}else{
						System.out.println("playlistsongs table does exist, ignoring it");
					}
					// Working
					System.out.println("creating accounts table");

					checkUsers = conn.prepareStatement(
							"select * from sys.systables where tablename = 'accounts'"	
							);
					checkUsersResult = checkUsers.executeQuery();

					if(!checkUsersResult.next()){
						System.out.println("accounts table doesn't exist, creating it");
						stmt7 = conn.prepareStatement(
								"create table accounts (" +
										"	user_id integer primary key " +
										"		generated always as identity (start with 1, increment by 1), " +
										"	username varchar(15), " +		//username limited to 15 characters
										"	password varchar(15)" +		//password too. also password is shown as a **STRING** IN THIS TABLE
										")"								//WHICH MEANS WE INSERT PASSWORDS DIRECTLY INTO THE TABLE. WE PROBABLY
								);								//DONT NEED A SEPERATE TABLE FOR PASSWORDS (although that would be useful for
						//a password changing system)
						stmt7.executeUpdate();
					}else{
						System.out.println("accounts table does exist, ignoring it");
					}
					System.out.println("created accounts table");
					conn.commit();
					return true;					
				}finally{
					DBUtil.closeQuietly(stmt1);
					DBUtil.closeQuietly(stmt2);
					DBUtil.closeQuietly(stmt3);
					DBUtil.closeQuietly(stmt4);
					DBUtil.closeQuietly(stmt5);
					DBUtil.closeQuietly(stmt6);
					DBUtil.closeQuietly(stmt7);
				}

			}
		});
	}


	// load initial data retrieved from CSVs into DB tables
	// (think FakeDatabase for now)
	public void loadInitialData(){
		executeTransaction(new Transaction<Boolean>() {
			@Override
			public Boolean execute(Connection conn) throws SQLException {
				List<Playlist> playList;
				List<Song> songList;
				List<Album> albumList;
				List<Artist> artistList;
				List<Genre> genreList;
				List<PlayListSongs> playlistsongs;
				List<Account> accountlist;

				try {
					System.out.println("trying to access initialData class getters");
					playList = InitialData.getPlaylists();
					songList = InitialData.getSongs();
					albumList = InitialData.getAblum();
					artistList = InitialData.getArtists();
					genreList = InitialData.getGenres();
					playlistsongs = InitialData.getplaylistSongs();
					accountlist = InitialData.getAccounts();

				} catch (IOException e) {
					throw new SQLException("Couldn't read initial data", e);
				}


				//TODO: finish this and edit this stuff
				System.out.println("preparing to insert data into tables");
				PreparedStatement insertPlaylist = null;
				PreparedStatement insertSong = null;
				PreparedStatement insertAlbum = null;
				PreparedStatement insertArtist = null;
				PreparedStatement insertGenre = null;
				PreparedStatement insertPlaylistSong = null;
				PreparedStatement insertAccount = null;

				try {
					// TODO: ADD MORE FIELDS AS NECESSARY
					// (Right now we only have the title field for playlist)
					// (so we don't need any more fields at the moment)
					// This statement should be working
					//we need to be concious of the order of initialization of tables due to foriegn keys.
					//the last insert to do is the playlist songs, so add on top of insertplaylist
					//DO NOT MAKE INSERT METHODS BELOW INSERTSONG EXCEPT INSERT PLAYLISTSONg

					System.out.println("inserting data into genres table");
					insertGenre = conn.prepareStatement("insert into genres (genre_title) values (?)");
					for (Genre ge : genreList) {
						insertGenre.setString(1, ge.getGenre());
						insertGenre.addBatch();
					}
					insertGenre.executeBatch();

					System.out.println("inserting data into artists table");
					insertArtist = conn.prepareStatement("insert into artists (artist_name) values (?)");
					for (Artist ar : artistList) {
						insertArtist.setString(1, ar.getArtistName());
						insertArtist.addBatch();
					}
					insertArtist.executeBatch();

					System.out.println("inserting data into albums table");
					insertAlbum = conn.prepareStatement("insert into albums (album_title) values (?)");
					for (Album al : albumList) {
						insertAlbum.setString(1, al.getTitle());
						insertAlbum.addBatch();
					}
					insertAlbum.executeBatch();

					System.out.println("inserting data into Accounts table");
					insertAccount = conn.prepareStatement("insert into accounts (username, password) values (?, ?)");
					for (Account ac : accountlist) {
						insertAccount.setString(1, ac.getUsername());
						insertAccount.setString(2,ac.getPassword());
						insertAccount.addBatch();
					}
					insertAccount.executeBatch();

					System.out.println("inserting data into playlists table");
					insertPlaylist = conn.prepareStatement("insert into playlists (playlist_title, number_songs, user_ownerid) values (?, ?, ?)");
					for (Playlist pl : playList) {
						insertPlaylist.setString(1, pl.getTitle());
						insertPlaylist.setInt(2,pl.getNumberSongs());
						insertPlaylist.setInt(3,pl.getUserOwnerId());
						insertPlaylist.addBatch();
					}
					insertPlaylist.executeBatch();

					System.out.println("inserting data into songs table");
					insertSong = conn.prepareStatement("insert into songs (song_title,song_location, artist, album, genre) values (?, ?, ?, ?, ?)");
					for (Song song : songList) {
						//insertBook.setInt(1, book.getBookId());		// auto-generated primary key, don't insert this

						insertSong.setString(1, song.getTitle());							
						insertSong.setString(2, song.getLocation());						
						insertSong.setInt(3, song.getArtistId());						
						insertSong.setInt(4, song.getAlbumId());
						insertSong.setInt(5, song.getGenreId());							

						insertSong.addBatch();
					}
					insertSong.executeBatch();

					System.out.println("inserting data into playlistSongs table");
					insertPlaylistSong = conn.prepareStatement("insert into playlistsongs (playlist_id, song_id) values (?, ?)");
					for (PlayListSongs pll : playlistsongs) {
						insertPlaylistSong.setInt(1, pll.getPlayListId());
						insertPlaylistSong.setInt(2, pll.getSongId());
						insertPlaylistSong.addBatch();
					}
					insertPlaylistSong.executeBatch();

					// TODO: add more things here as necessary

					return true;
				} finally {
					DBUtil.closeQuietly(insertSong);
					DBUtil.closeQuietly(insertPlaylist);
					DBUtil.closeQuietly(insertAlbum);
					DBUtil.closeQuietly(insertGenre);
					DBUtil.closeQuietly(insertPlaylistSong);
					DBUtil.closeQuietly(insertArtist);
					DBUtil.closeQuietly(insertAccount);
				}
			}
		});
	}

	private Connection connect() throws SQLException {

		//********************************
		//THIS CHANGES FOR EACH INDIVIDUAL
		//********************************

		Connection conn = DriverManager.getConnection("jdbc:derby:C:/cs320/dblibrary.db;create=true");		

		// Set autocommit to false to allow multiple the execution of
		// multiple queries/statements as part of the same transaction.
		conn.setAutoCommit(false);

		return conn;
	}
	//retrieves account information from account query resultset
	private void loadAccount(Account account, ResultSet resultSet, int index) throws SQLException {
		account.setAccountId(resultSet.getInt(index++));
		account.setUsername(resultSet.getString(index++));
		account.setPassword(resultSet.getString(index++));
	}
	private void loadPlaylist(Playlist pl, ResultSet resultSet, int index) throws SQLException 
	{
		pl.setPlaylistId(resultSet.getInt(index++));
		pl.setTitle(resultSet.getString(index++));
		pl.setNumberSongs(resultSet.getInt(index++));
		pl.setUserOwnerId(resultSet.getInt(index++));
	}
	private void loadSong(Song song, ResultSet resultSet, int index) throws SQLException 
	{
		song.setSongId(resultSet.getInt(index++));
		song.setTitle(resultSet.getString(index++));
		song.setLocation(resultSet.getString(index++));
		song.setAlbumId(resultSet.getInt(index++));
		song.setArtistId(resultSet.getInt(index++));
		song.setGenreId(resultSet.getInt(index++));


	}
	//retrieves artist information from query result set
	private void loadArtist(Artist artist, ResultSet resultSet, int index) throws SQLException
	{
		artist.setArtistId(resultSet.getInt(index++));
		artist.setArtistName(resultSet.getString(index++));

	}
	private void loadGenre(Genre genre, ResultSet resultSet, int index) throws SQLException
	{
		genre.setGenreId(resultSet.getInt(index++));
		genre.setGenre(resultSet.getString(index++));
	}
	private void loadAlbum(Album album, ResultSet resultSet, int index) throws SQLException
	{
		album.setAlbumId(resultSet.getInt(index++));
		album.setTitle(resultSet.getString(index++));
	}
	// The main method creates the database tables and loads the initial data.
	public static void main(String[] args) throws IOException {

		System.out.println("Creating tables...");
		DerbyDatabase db = new DerbyDatabase();
		db.createTables();

		System.out.println("Loading initial data...");
		db.loadInitialData();

		System.out.println("Success!");
	}
}
