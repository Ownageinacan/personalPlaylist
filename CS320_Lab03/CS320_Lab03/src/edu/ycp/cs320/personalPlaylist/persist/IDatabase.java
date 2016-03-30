package edu.ycp.cs320.personalPlaylist.persist;

import java.util.List;

import edu.ycp.cs320.personalPlaylist.model.Song;	//using song instead of pair. when using song, requires all 3 parameters
import edu.ycp.cs320.personalPlaylist.model.Playlist;
//import edu.ycp.cs320.personalPlaylist.model.Album;
//import edu.ycp.cs320.personalPlaylist.model.Artist;
import edu.ycp.cs320.personalPlaylist.model.Pair;

public interface IDatabase {

	public Integer insertSongIntoSongsTable(String title, String artist, String album);
	public List<Playlist> findAllPlaylists();
	public List<Song> findAllSongs();
	public List<Pair<Song, Playlist>> findAllSongInPlaylist(String playlist);
	
	//public List<Album> findAllAlbums();
	//public List<Artist> findAllArtists();
	//public List<Artist, Album, Playlist> findSongByArtistAndAlbumAndPlaylist(); //trio class?
}
