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
//import edu.ycp.cs320.booksdb.model.Author;
//import edu.ycp.cs320.personsalPlaylist.persist.DerbyDatabase.Transaction;
import edu.ycp.cs320.personalPlaylist.model.Account;
import edu.ycp.cs320.personalPlaylist.model.Album;
import edu.ycp.cs320.personalPlaylist.model.Artist;
import edu.ycp.cs320.personalPlaylist.model.Genre;



//NOTE: RUNNING THIS DATABASE IS A ONE TIME THING; WE WILL NOT RUN THIS EVERY TIME

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
			conn = DriverManager.getConnection("jdbc:derby:/cs320/gitRepository/CS320_Lab03/CS320_Lab03/library.db;create=true");
			
			conn.setAutoCommit(false);
	
			return conn;
		} catch (SQLException | ClassNotFoundException e) {
			System.out.println("Error: " + e.getMessage());
			return null;
		} 
	}

		//TODO: PLEASE LOOK AT HAKE'S DERBYDATABASE TEMPALTE BEFORE ATTEMPTING TO WRITE ANY OF THESE PLEASE
	
		@Override
		public Integer insertSongIntoSongsTable(String title ,String location, int artistId, int genreId, int albumId) throws SQLException {
			// TODO Auto-generated method stub
			System.out.println("inserting song into songs table");
			Connection conn = createConnection();
			PreparedStatement insertSong = null;

			try {
				insertSong = conn.prepareStatement("insert into songs (title, location, artist_id, genre_id, album_id) values (?, ?, ?, ?, ?)");
				insertSong.setString(1, title);
				insertSong.setString(2, location);
				insertSong.setInt(3, artistId);
				insertSong.setInt(4, genreId);
				insertSong.setInt(5, albumId);
				return insertSong.executeUpdate();
			} finally {
				DBUtil.closeQuietly(insertSong);
			}
		}
		@Override
		//TODO: Add more fields eventually to this method, for now let's just get it working
		//Side note: Use integer or not? I don't even know mane
		public Integer insertPlaylistIntoPlaylistsTable(String title){
			//TODO: Implement
			return null;
		}
		@Override
		public List<Artist> findAllArtists(){
			//TODO: Implement
			return null;
		}
		@Override
		public List<Pair<Song, Playlist>> findAllSongInPlaylist(String playlist) {
			// TODO Auto-generated method stub
			return null;
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
						stmt = conn.prepareStatement(
								"select * from playlists " +
								" order by playlist_title asc"
						);
						
						List<Playlist> result = new ArrayList<Playlist>();
						
						resultSet = stmt.executeQuery();
						
						// for testing that a result was returned
						Boolean found = false;
						
						while (resultSet.next()) {
							found = true;
							
							Playlist playlist = new Playlist();
							loadPlaylist(playlist, resultSet, 1);
							
							result.add(playlist);
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

		@Override
		//Note: Same thing as addPlaylist
		public Integer deleteSongFromSongsTable(String title){
			// TODO: Implement
			return null;
		}

		@Override
		public Integer deletePlaylistFromPlaylistTable(String title){
			// TODO: Implement
			return null;
		}

		@Override
		public Integer insertSongIntoPlaylist(String title){
			// Insert song into memberOfPl table and match it w/ a playlist
			return null;
		}
		
		@Override
		public List<Account> findAllAccounts(){
			return executeTransaction(new Transaction<List<Account>>() {
				@Override
				public List<Account> execute(Connection conn) throws SQLException {
					PreparedStatement stmt = null;
					ResultSet resultSet = null;
					
					try {
						System.out.println("looking for all users in db");
						stmt = conn.prepareStatement(
								"select * from users " +
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
						
						// check if any authors were found
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

		/********************************************************
		 ********************************************************
		 ********************************************************
		 ********************************************************
		//TODO: ADD A COUPLE (LOTTA) MORE METHODS
		// BUT DO NOT WORK ON THEM YET, TAKE IT ONE STEP AT A TIME
		// QUALITY > QUANTITY
		******************************************************** 
		********************************************************
		********************************************************
		*/
		
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
												"   number_songs Integer" +
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
												"	artist_name varchar(20)" +	//band name
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
												"	album_title varchar(20)" +	//album title
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
							// TODO: FIGURE OUT HOW TO ACTUALLY INSERT IDs VIA TITLES
							// Note: Thought of this on friday, instead of typing in titles, use our getters.
							// crazy i know
							System.out.println("creating playlistsongs table");
							
							checkPlSongs = conn.prepareStatement(
									"select * from sys.systables where tablename = 'playlistsongs'"	
										);
								checkPlSongsResult = checkPlSongs.executeQuery();
								
							if(!checkPlSongsResult.next()){
								System.out.println("playlistsongs table doesn't exist, creating it");
								stmt3 = conn.prepareStatement(
										"create table playlistsongs (" +
										"	playlist_id integer constraint playlist_id references playlists, " +	//these 2 lines are getting foreign keys
										"	song_id integer constraint song_id references songs " +
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
					PreparedStatement insertplaylistsong = null;
					PreparedStatement insertaccount = null;
					
					try {
						// TODO: ADD MORE FIELDS AS NECESSARY
						// (Right now we only have the title field for playlist)
						// (so we don't need any more fields at the moment)
						// This statement should be working
						//we need to be concious of the order of initialization of tables due to foriegn keys.
						//the last insert to do is the playlist songs, so add on top of insertplaylist
						//DO NOT MAKE INSERT METHODS BELOW INSERTSONG EXCEPT INSERT PLAYLISTSONG
						System.out.println("inserting data into playlists table");
						insertPlaylist = conn.prepareStatement("insert into playlists (playlist_title, number_songs) values (?, ?)");
						for (Playlist pl : playList) {
//							insertAuthor.setInt(1, author.getAuthorId());	// auto-generated primary key, don't insert this
							insertPlaylist.setString(1, pl.getTitle());
							insertPlaylist.setInt(2,pl.getNumberSongs());
							insertPlaylist.addBatch();
						}
						insertPlaylist.executeBatch();
						
						System.out.println("inserting data into songs table");
						insertSong = conn.prepareStatement("insert into songs (song_title,song_location, artist, album, genre) values (?, ?, ?, ?, ?)");
						for (Song song : songList) {
//							insertBook.setInt(1, book.getBookId());		// auto-generated primary key, don't insert this
							
							//TODO: Maybe test these. No guarantees that they're correct, but a girl can hope						
							insertSong.setString(1, song.getTitle());							
							insertSong.setString(2, song.getLocation());						
							insertSong.setInt(3, song.getArtistId());						
							insertSong.setInt(4, song.getAlbumId());
							insertSong.setInt(5, song.getGenreId());							
							
							insertSong.addBatch();
						}
						insertSong.executeBatch();

						
						// TODO: add more things here as necessary
						
						return true;
					} finally {
						DBUtil.closeQuietly(insertSong);
						DBUtil.closeQuietly(insertPlaylist);
					}
				}
			});
		}
			
		
		
		// TODO: Here is where you specify the location of your Derby SQL database
		// TODO: You will need to change this location to the same path as your workspace for this example
		// TODO: Change it here and in SQLDemo under CS320_Lab06->edu.ycp.cs320.sqldemo	
		private Connection connect() throws SQLException {
			
			//********************************
			//THIS CHANGES FOR EACH INDIVIDUAL
			//********************************
			
			Connection conn = DriverManager.getConnection("jdbc:derby:/cs320/gitRepository/CS320_Lab03/CS320_Lab03/library.db;create=true");		
			
			// Set autocommit to false to allow multiple the execution of
			// multiple queries/statements as part of the same transaction.
			conn.setAutoCommit(false);
			
			return conn;
		}
		//retrieves account information from account query resultset
		private void loadAccount(Account account, ResultSet resultSet, int index) throws SQLException {
			account.setAccountId(resultSet.getInt(index++));
			account.setUserName(resultSet.getString(index++));
			account.setPassword(resultSet.getString(index++));
		}
		private void loadPlaylist(Playlist pl, ResultSet resultSet, int index) throws SQLException 
		{
			pl.setTitle(resultSet.getString(index++));
			pl.setNumberSongs(resultSet.getInt(index++));
			pl.setPlaylistId(resultSet.getInt(index++));
			pl.setUserOwnerId(resultSet.getInt(index++));
		}
		private void loadSong(Song song, ResultSet resultSet, int index) throws SQLException 
		{
			song.setTitle(resultSet.getString(index++));
			song.setAlbumId(resultSet.getInt(index++));
			song.setArtistId(resultSet.getInt(index++));
			song.setGenreId(resultSet.getInt(index++));
			song.setLocation(resultSet.getString(index++));
			song.setSongId(resultSet.getInt(index++));
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
