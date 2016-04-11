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
import edu.ycp.cs320.personalPlaylist.model.Playlist;
import edu.ycp.cs320.personalPlaylist.model.Song;
import edu.ycp.cs320.personalPlaylist.persist.DerbyDatabase;
import edu.ycp.cs320.personalPlaylist.model.Album;
import edu.ycp.cs320.personalPlaylist.model.Artist;
//import edu.ycp.cs320.booksdb.persist.DBUtil;
//import edu.ycp.cs320.booksdb.persist.PersistenceException;
//import edu.ycp.cs320.booksdb.persist.DerbyDatabase.Transaction;
//import edu.ycp.cs320.personalPlaylist.model.Album;
//import edu.ycp.cs320.personalPlaylist.persist.DBUtil;
//import edu.ycp.cs320.personalPlaylist.persist.InitialData;
//import edu.ycp.cs320.personalPlaylist.persist.DerbyDatabase.Transaction;
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

		//TODO: PLEASE LOOK AT HAKE'S DERBYDATABASE TEMPALTE BEFORE ATTEMPTING TO WRITE ANY OF THESE PLEASE
	
		@Override
		public Integer insertSongIntoSongsTable(String title, Artist artist, Genre genre, Album album) throws SQLException {
			// TODO Auto-generated method stub
			Connection conn = null;
			PreparedStatement insertSong = null;

			try {
				insertSong = conn.prepareStatement("insert into songs (title, artist_id, genre_id, album_id) values (?, ?, ?, ?)");
				insertSong.setString(1, title);
				insertSong.setInt(2, artist.getArtistId());
				insertSong.setInt(3, genre.getGenreId());
				insertSong.setInt(4, album.getAlbumId());
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
		public List<Pair<Song, Playlist>> findAllSongInPlaylist(String playlist) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public List<Playlist> findAllPlaylists() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public List<Song> findAllSongs() {
			// TODO Auto-generated method stub
			return null;
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
					PreparedStatement stmt1 = null;	//playlists table
					PreparedStatement stmt2 = null;	//songs table
					PreparedStatement stmt3 = null; //memberOfPl table
					PreparedStatement stmt4 = null;	//artists table
					PreparedStatement stmt5 = null; //genres table
					PreparedStatement stmt6 = null;	//albums table
					PreparedStatement stmt7 = null; //users table
					
					try{
						// Working
						stmt1 = conn.prepareStatement(
								"create table playlists (" +
										"	playlist_id integer primary key " +
										"		generated always as identity (start with 1, increment by 1), " +									
										"	playlist_title varchar(40)," +	//playlist title
										")"
								);
						stmt1.executeUpdate();
 
						// Working
						
						stmt4 = conn.prepareStatement(
								"create table artists (" +
										"	artist_id integer primary key " +
										"		generated always as identity (start with 1, increment by 1), " +									
										"	artist_name varchar(20)," +	//band name
										")"
								);
						stmt4.executeUpdate();
						
						// Working
						
						stmt5 = conn.prepareStatement(
								"create table genres (" +
										"	genre_id integer primary key " +
										"		generated always as identity (start with 1, increment by 1), " +									
										"	genre_title varchar(20)," +	//genre
										")"
								);
						
						stmt5.executeUpdate();

						// Working
						
						stmt6 = conn.prepareStatement(
								"create table albums (" +
										"	album_id integer primary key " +
										"		generated always as identity (start with 1, increment by 1), " +									
										"	album_title varchar(20)," +	//album title
										")"
								);
						
						stmt6.executeUpdate();
						
						// Working
						
						stmt2 = conn.prepareStatement(
								"create table songs (" +
								"	song_id integer primary key " +
								"		generated always as identity (start with 1, increment by 1), " +
								"	artists integer constraint artist_id references artists, " +	//foreign key artist_id
								"	genres integer constraint genre_id references genres, " +		//foreign key genre_id
								"	albums integer constraint album_id references albums, "+ 		//foreign key album_id
								"	song_title varchar(50)" +	//song title						
								")"
								);
						stmt2.executeUpdate();
						
						// Working
						// TODO: FIGURE OUT HOW TO ACTUALLY INSERT IDs VIA TITLES
						// Note: Thought of this on friday, instead of typing in titles, use our getters.
						// crazy i know
						
						stmt3 = conn.prepareStatement(
								"create table memberOfPl (" +
								"	playlist_id integer constraint playlist_id references playlists, " +	//these 2 lines are getting foreign keys
								"	song_id integer constraint song_id references songs " +
								")"		
								);
						stmt3.executeUpdate();

						// Working
						
						stmt7 = conn.prepareStatement(
								"create table users (" +
								"	user_id integer primary key " +
								"		generated always as identity (start with 1, increment by 1), " +
								"	username varchar(15), " +		//username limited to 15 characters
								"	password varchar(15)" +		//password too. also password is shown as a **STRING** IN THIS TABLE
								")"								//WHICH MEANS WE INSERT PASSWORDS DIRECTLY INTO THE TABLE. WE PROBABLY
								);								//DONT NEED A SEPERATE TABLE FOR PASSWORDS (although that would be useful for
																//a password changing system)
						stmt7.executeUpdate();
						
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
					
					try {
						playList = InitialData.getPlaylists();
						songList = InitialData.getSongs();
					} catch (IOException e) {
						throw new SQLException("Couldn't read initial data", e);
					}

					
					//TODO: finish this and edit this stuff
					
					PreparedStatement insertPlaylist = null;
					PreparedStatement insertSong = null;
					
					try {
						// TODO: ADD MORE FIELDS AS NECESSARY
						// (Right now we only have the title field for playlist)
						// (so we don't need any more fields at the moment)
						// This statement should be working
						insertPlaylist = conn.prepareStatement("insert into playlists (playlist_title, number_songs) values (?, ?)");
						for (Playlist pl : playList) {
//							insertAuthor.setInt(1, author.getAuthorId());	// auto-generated primary key, don't insert this
							insertPlaylist.setString(1, pl.getTitle());
							insertPlaylist.setInt(2,pl.getNumberSongs());
							insertPlaylist.addBatch();
						}
						insertPlaylist.executeBatch();
						
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
