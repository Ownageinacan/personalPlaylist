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
import edu.ycp.cs320.personalPlaylist.model.Artist;
//import edu.ycp.cs320.booksdb.persist.DBUtil;
//import edu.ycp.cs320.booksdb.persist.PersistenceException;
//import edu.ycp.cs320.booksdb.persist.DerbyDatabase.Transaction;
//import edu.ycp.cs320.personalPlaylist.model.Album;
//import edu.ycp.cs320.personalPlaylist.persist.DBUtil;
//import edu.ycp.cs320.personalPlaylist.persist.InitialData;
//import edu.ycp.cs320.personalPlaylist.persist.DerbyDatabase.Transaction;



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
		public Integer insertSongIntoSongsTable(String title, String artist, String album) {
			// TODO Auto-generated method stub
			return null;
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
		
		
		// Creates Playlists, Songs, and memberOfPl table
		// memberOfPl table is what connects songs & playlists via IDs
		
		public void createTables(){
			executeTransaction(new Transaction<Boolean>(){
				@Override
				public Boolean execute(Connection conn) throws SQLException {
					PreparedStatement stmt1 = null;
					PreparedStatement stmt2 = null;
					PreparedStatement stmt3 = null;
					//add more prepared statements for more tables
					
					try{
						//This should be correct
						stmt1 = conn.prepareStatement(
								"create table playlists (" +
										"	playlist_id integer primary key " +
										"		generated always as identity (start with 1, increment by 1), " +									
										"	playlist_title varchar(40)," +
										")"
								);
						stmt1.executeUpdate();
						
						//TODO: Test this statement using SQLdemo in lab06
						stmt2 = conn.prepareStatement(
								"create table songs (" +
								"	song_id integer primary key " +
								"		generated always as identity (start with 1, increment by 1), " +
								"	artist_id integer constraint artist_id references artists, " +
								"	genre_id integer constraint genre_id references genres, " +
								"	album_id integer constraint album_id reference albums, "+
								"	song_title varchar(50)" +
								")"
								);
						stmt2.executeUpdate();
						
						//TODO: test this statement using SQLdemo in lab06
						
						stmt3 = conn.prepareStatement(
								"create table memberOfPl (" +
								"	playlist_id integer constraint playlist_id references playlists, " +	//these 2 lines are getting foreign key
								"	song_id integer constraint song_id references songs, " +
								")"		
								);
						stmt3.executeUpdate();
						
						return true;					
					}finally{
						DBUtil.closeQuietly(stmt1);
						DBUtil.closeQuietly(stmt2);
						DBUtil.closeQuietly(stmt3);
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

					PreparedStatement insertPlaylist = null;
					PreparedStatement insertSong = null;

					//TODO: finish this and edit this stuff
					
					try {
						insertPlaylist = conn.prepareStatement("insert into authors (author_lastname, author_firstname) values (?, ?)");
						for (Playlist pl : playList) {
//							insertAuthor.setInt(1, author.getAuthorId());	// auto-generated primary key, don't insert this
//							insertAuthor.setString(1, pl.getLastname());	// TODO: edit these to match playlist fields
//							insertAuthor.setString(2, author.getFirstname()); // ^
							insertPlaylist.addBatch();
						}
						insertPlaylist.executeBatch();
						
						insertSong = conn.prepareStatement("insert into books (author_id, title, isbn) values (?, ?, ?)");
						for (Song song : songList) {
//							insertBook.setInt(1, book.getBookId());		// auto-generated primary key, don't insert this
//							insertBook.setInt(1, book.getAuthorId());	// TODO: edit these to match song fields
//							insertBook.setString(2, book.getTitle());
//							insertBook.setString(3, book.getIsbn());
//							insertBook.addBatch();
						}
						insertSong.executeBatch();
						
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
			Connection conn = DriverManager.getConnection("jdbc:derby:C:/CS320/CS320_Library_Example/CS320_Lab06/library.db;create=true");		
			
			// Set autocommit to false to allow multiple the execution of
			// multiple queries/statements as part of the same transaction.
			conn.setAutoCommit(false);
			
			return conn;
		}
		
	}
