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
			conn = DriverManager.getConnection("jdbc:derby:C:/cs320/dblibrary.db;create=true");

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
	public Integer insertPlaylistIntoPlaylistsTable(String title, int ownerId) throws SQLException{
		//TODO: Implement
		System.out.println("inserting song into playlists table");
		Connection conn = createConnection();
		PreparedStatement insertPlaylist = null;

		try {
			insertPlaylist = conn.prepareStatement("insert into playlists (playlist_title, number_songs, user_ownerid) values (?, ?, ?)");
			insertPlaylist.setString(1, title);
			insertPlaylist.setInt(2, 0);
			insertPlaylist.setInt(3, ownerId);
			return insertPlaylist.executeUpdate();
		} finally {
			DBUtil.closeQuietly(insertPlaylist);
		}
	}
	
	@Override
	public Integer insertGenreIntoGenresTable(String genre) throws SQLException{
		//TODO: Implement
		System.out.println("inserting genre into genres table");
		Connection conn = createConnection();
		PreparedStatement insertGenre = null;

		try {
			insertGenre = conn.prepareStatement("insert into genres (genre_title) values (?)");
			insertGenre.setString(1, genre);
			return insertGenre.executeUpdate();
		} finally {
			DBUtil.closeQuietly(insertGenre);
		}
	}
	
	@Override
	public Integer insertArtistIntoArtistsTable(String artistName) throws SQLException{
		//TODO: Implement
		System.out.println("inserting genre into genres table");
		Connection conn = createConnection();
		PreparedStatement insertArtist = null;

		try {
			insertArtist = conn.prepareStatement("insert into artists (artist_name) values (?)");
			insertArtist.setString(1, artistName);
			return insertArtist.executeUpdate();
		} finally {
			DBUtil.closeQuietly(insertArtist);
		}
	}
	
	@Override
	public Integer insertAlbumIntoAlbumsTable(String albumTitle) throws SQLException{
		//TODO: Implement
		System.out.println("inserting album into albums table");
		Connection conn = createConnection();
		PreparedStatement insertAlbum = null;

		try {
			insertAlbum = conn.prepareStatement("insert into albums (album_title) values (?)");
			insertAlbum.setString(1, albumTitle);
			return insertAlbum.executeUpdate();
		} finally {
			DBUtil.closeQuietly(insertAlbum);
		}
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
	public List<Genre> findAllGenres (String genre)
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



	public List<Album> findAllAlbums(String album)
	{
		return executeTransaction(new Transaction<List<Album>>() {
			@Override
			public List<Album> execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet resultSet = null;

				try {
					stmt = conn.prepareStatement(
							"select * from album " +
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

	//TODO: FINISH LATER; LOW PRIORITY

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
						
						
						//TODO: also check if this is right
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

							//TODO: MAKE THIS MORE COMPLEX
							// WE WANT TO SHOW MORE THAN JUST SONG TITLE
							
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
						System.out.println("loading songs");
						Song song = new Song();
						loadSong(song, resultSet, 1);
						System.out.println("songs loaded");
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
				//TODO: TEST THIS, NO GUARANTEES THAT IT WORKS
				//LIKE I HAVE NO IDEA
				try {
					stmt = conn.prepareStatement(
							"select songs.* " +
									"  from  songs " +
									"  where artists.artist_id = ? "
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
						//TODO: FIND OUT WHAT THESE INDICIES DO

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

	// deletes song (and possibly artist) from library
	//TODO: additionally should delete genre, album if applicable
	// OR OR OR OR OR JUST DELETE IT IF THIS DOESNT WORK
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
									"    and artists.artist_id = songs.artist_id "
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
					//TODO: CHECK AND SEE IF THIS WORKS
					//I HAVE NO CLUE IF IT DOES
					for(int i = 0; i < artists.size(); i++)
					{
						stmt5 = conn.prepareStatement(
								"select songs.artist_id from songs"	+
										"where songs.artist_id = ?"
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

	//TODO: check this

	@Override
	public List<Playlist> deletePlaylistFromPlaylistTable(final String title){
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

					//TODO: THIS MIGHT BE HORRIBLY HORRIBLY WRONG
					// I DONT UNDERSTAND WHY WE NEED RETURN TYPES IN THESE
					// ANYWAY, SO SOMEONE PLEASE CHECK THIS OR REMIND ME TO
					// CHECK WITH THIS

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



	// TODO: Here is where you specify the location of your Derby SQL database
	// TODO: You will need to change this location to the same path as your workspace for this example
	// TODO: Change it here and in SQLDemo under CS320_Lab06->edu.ycp.cs320.sqldemo	
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
