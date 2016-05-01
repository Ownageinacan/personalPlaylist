package edu.ycp.cs320.personalPlaylist.persist;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import edu.ycp.cs320.personalPlaylist.model.Song;	//using song instead of pair. when using song, requires all 3 parameters
import edu.ycp.cs320.personalPlaylist.model.Playlist;
import edu.ycp.cs320.personalPlaylist.model.Account;
import edu.ycp.cs320.personalPlaylist.model.Album;
import edu.ycp.cs320.personalPlaylist.model.Artist;
import edu.ycp.cs320.personalPlaylist.model.Genre;
import edu.ycp.cs320.personalPlaylist.model.Pair;

public interface IDatabase {

	public Integer insertSongIntoSongsTable(String title, String location, int artistId, int genreId, int albumId) throws SQLException;
	
	public List<Playlist> findAllPlaylists();	//list of playlists
	public List<Song> findAllSongs();	//list of songs
	public List<Album> findAllAlbums();	//list of albums

	public Integer insertPlaylistIntoPlaylistsTable(String title, int ownerId) throws SQLException;
	public Integer insertGenreIntoGenresTable(String genre) throws SQLException;
	public Integer insertArtistIntoArtistsTable(String artistName) throws SQLException;
	public Integer insertAlbumIntoAlbumsTable(String albumTitle) throws SQLException;
	public List<Playlist> deletePlaylistFromPlaylistTable(String title);
	public Integer insertSongIntoPlaylist(Song song, Playlist playlist) throws SQLException;
	public List<Account> findAllAccounts(); //list of accounts
	public List<Artist> findAllArtists(); //list of artists
	public List<Pair<Song, Artist>> findSongByArtistName(int artistId);
	public List<Artist> removeSongByTitle(String title);//Maybe change type of song to something else?
	public List<Genre> findAllGenres();
	public List<Playlist> findPlaylistByTitle(String title);
	public List<Song> findSongByTitle(String title);
	public List<Song> findSongsByPlaylistTitle(String title);

	
	//public List<Artist, Album, Playlist> findSongByArtistAndAlbumAndPlaylist(); //trio class?
}
