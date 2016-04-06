package edu.ycp.cs320.personalPlaylist.persist;

import java.util.List;

import edu.ycp.cs320.personalPlaylist.model.Song;	//using song instead of pair. when using song, requires all 3 parameters
import edu.ycp.cs320.personalPlaylist.model.Playlist;
import edu.ycp.cs320.personalPlaylist.model.Album;
import edu.ycp.cs320.personalPlaylist.model.Artist;
import edu.ycp.cs320.personalPlaylist.model.Pair;

public interface IDatabase {

	public Integer insertSongIntoSongsTable(String title, String artist, String album);
	public List<Pair<Song, Playlist>> findAllSongInPlaylist(String playlist);
	
	public List<Playlist> findAllPlaylists();	//list of playlists
	public List<Song> findAllSongs();	//list of songs
//	public List<Album> findAllAlbums();	//list of albums
//	public List<Artist> findAllArtists();	//list of artists
	public Integer insertPlaylistIntoPlaylistsTable(String title);
	public Integer deleteSongFromSongsTable(String title);
	public Integer deletePlaylistFromPlaylistTable(String title);
	public Integer insertSongIntoPlaylist(String title);
	
	//public List<Artist, Album, Playlist> findSongByArtistAndAlbumAndPlaylist(); //trio class?
}
