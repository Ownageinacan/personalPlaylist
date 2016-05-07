package edu.ycp.cs320.lab03.controller;

import java.sql.SQLException;
import java.util.List;

import edu.ycp.cs320.personalPlaylist.model.Account;
import edu.ycp.cs320.personalPlaylist.model.Album;
import edu.ycp.cs320.personalPlaylist.model.Artist;
import edu.ycp.cs320.personalPlaylist.model.Genre;
import edu.ycp.cs320.personalPlaylist.model.Playlist;
import edu.ycp.cs320.personalPlaylist.model.Song;
import edu.ycp.cs320.personalPlaylist.persist.DatabaseProvider;
import edu.ycp.cs320.personalPlaylist.persist.IDatabase;
import edu.ycp.cs320.personalPlaylistdb.InitDatabase;

public class MasterController {
	private MasterController controller = null;
	
	//gets an account's playlist
	public List<Playlist> getAllPlayListsbyAccount(String user, String pass){
		InitDatabase.init();
		IDatabase db = DatabaseProvider.getInstance();
		
		List<Playlist> playlists = db.findPlaylistsByAccount(user, pass);
		return playlists;
	}
	
	//gets everyplaylsit
	public List<Playlist> getAllPlayLists() {
		InitDatabase.init();
		IDatabase db = DatabaseProvider.getInstance();
		
		List<Playlist> playlists = db.findAllPlaylists();
		return playlists;
		
	}
	
	//checks if username and pass exist
	public Boolean Usercheck(String Username, String Password) {
		InitDatabase.init();
		IDatabase db = DatabaseProvider.getInstance();
		
		List<Account> accountList = db.findAllAccounts();
		
		for(Account ac : accountList){
			if(ac.getUsername().equals(Username)){
				if(ac.getPassword().equals(Password)){
					return true;
				}
			}
		}
		return false;
	}
	
	//creates a new user
	public void CreateAccount(String username, String password) {
		InitDatabase.init();
		IDatabase db = DatabaseProvider.getInstance();
		
		db.insertAccountIntoAccountsTable(username, password);
	}
	
	//creates a new palylsit
	public void CreatePlaylist(String PlaylistName, String User) throws SQLException {
		InitDatabase.init();
		IDatabase db = DatabaseProvider.getInstance();
		
		db.insertPlaylistIntoPlaylistsTable(PlaylistName, getAccount_id(User));
}
	
	//takes in a account and returns its id
	public int getAccount_id(String User) throws SQLException {
		InitDatabase.init();
		IDatabase db = DatabaseProvider.getInstance();
		
		int User_id = -1;
		List<Account> accountList = db.findAllAccounts();
		
		for(Account ac : accountList){
			if(ac.getUsername().equals(User)){
				User_id = ac.getAccountId();
			}
		}
		return User_id;
}
	
	//takes in a album and returns its id
	public int getAlbum_id(String album){
		InitDatabase.init();
		IDatabase db = DatabaseProvider.getInstance();
		
		int Album_id = -1;
		List<Album> albumList = db.findAllAlbums();
		
		for(Album al : albumList){
			if(al.getTitle().toLowerCase().equals(album.toLowerCase())){
				Album_id = al.getAlbumId();
			}
		}
		return Album_id;
	}
	
	//takes in a artist and returns its id
	public int getArtist_id(String artist){
		InitDatabase.init();
		IDatabase db = DatabaseProvider.getInstance();
		
		int Artist_id = -1;
		List<Artist> artistList = db.findAllArtists();
		
		for(Artist ar : artistList){
			if(ar.getArtistName().toLowerCase().equals(artist.toLowerCase())){
				Artist_id = ar.getArtistId();
			}
		}
		return Artist_id;
	}
	
	//takes in a Genre and returns its id
	public int getGenre_id(String genre){
		InitDatabase.init();
		IDatabase db = DatabaseProvider.getInstance();
		
		int Genre_id = -1;
		List<Genre> genreList = db.findAllGenres();
		
		for(Genre ge : genreList){
			if(ge.getGenre().toLowerCase().equals(genre.toLowerCase())){
				Genre_id = ge.getGenreId();
			}
		}
		return Genre_id;
	}
	
	//takes in a playlist and returns its id
	public int getPlaylist_id(String Playlist){
		InitDatabase.init();
		IDatabase db = DatabaseProvider.getInstance();
		
		int Playlist_id = -1;
		List<Playlist> PlaylistList = db.findAllPlaylists();
		
		for(Playlist pl : PlaylistList){
			if(pl.getTitle().toLowerCase().equals(Playlist.toLowerCase())){
				Playlist_id = pl.getPlaylistId();
			}
		}
		return Playlist_id;
	}
	
	//creates a new song
	public void createNewSong(String songName, String Playlist,  String Genre, String Artist,
			String Album, String Location) throws SQLException {
		InitDatabase.init();
		IDatabase db = DatabaseProvider.getInstance(); 
		controller = new MasterController();
		
		int Genre_id = controller.getGenre_id(Genre);
		int Artist_id = controller.getArtist_id(Artist);
		int Album_id = controller.getAlbum_id(Album);
		
		if(Genre_id < 0){
			db.insertGenreIntoGenresTable(Genre);
		}
		if(Artist_id < 0){
			db.insertArtistIntoArtistsTable(Artist);
		}
		if(Album_id < 0){
			db.insertAlbumIntoAlbumsTable(Album);
		}
		
		//TODO: call db insertSongIntoPlaylist
		db.insertSongIntoPlaylist(Playlist, songName, Album_id, Artist_id, Genre_id, Location);
	}
	
	//deletes playlist
	public void deletePlaylist(String PLaylist) {
		InitDatabase.init();
		IDatabase db = DatabaseProvider.getInstance();
		
		db.removePlaylistFromPlaylistTable(PLaylist);
	}

	//gets songs from an album
	public List<Song> getSongsInAlbum(String albumName) {
		InitDatabase.init();
		IDatabase db = DatabaseProvider.getInstance();

		List<Song> songList = db.findAllSongs();
		
		return songList;
	}

	public List<Song> getSongsByArtist(String artistName) {
		InitDatabase.init();
		IDatabase db = DatabaseProvider.getInstance();

		List<Song> songList = db.findSongByArtistName(artistName);
		return songList;
	}
}
