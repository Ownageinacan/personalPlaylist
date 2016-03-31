package edu.ycp.cs320.personalPlaylist.persist;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import edu.ycp.cs320.personalPlaylist.model.Playlist;
import edu.ycp.cs320.personalPlaylist.model.Song;

public class InitialData {

	// reads initial song data from CSV file and returns a List of songs
	public static List<Song> getSongs() throws IOException {
		List<Song> songList = new ArrayList<Song>();
		ReadCSV readSongs = new ReadCSV("songs.csv");	//Create ReadCSV object
		try {
			// auto-generated primary key for authors table
			Integer songId = 1;
			while (true) {
				List<String> tuple = readSongs.next();//Tuple crap that I don't understand still
				if (tuple == null) {
					break;
				}
				Iterator<String> i = tuple.iterator();	
				Song song = new Song();		//Create a song object
//				author.setAuthorId(Integer.parseInt(i.next()));
				
				song.setSongId(songId++);	//Set song ID			
				
				//TEMPORARY HARD CODE; ASK YOURSELF WHY WE WOULD SET ANY OF THESE?
				song.setTitle(i.next());	//Set song title
				song.setArtistLastName(i.next());	//Set song artist's last name
				song.setArtistFirstName(i.next());	//Set song artist's first name
				song.setAlbum(i.next());	//Set song album
				
				songList.add(song);	//Might be redundant
			}
			return songList;	//return the list of songs initialized
		} finally {				//Finally always runs
			readSongs.close();	//close the song reader
		}
	}
	
	
	
	
	// reads initial Book data from CSV file and returns a List of Books
	public static List<Playlist> getPlaylists() throws IOException {
		List<Playlist> playList = new ArrayList<Playlist>();
		ReadCSV readPlaylists = new ReadCSV("playlists.csv");
		try {
			// auto-generated primary key for table books
			Integer playlistId = 1;
			while (true) {
				List<String> tuple = readPlaylists.next();
				if (tuple == null) {
					break;
				}
				Iterator<String> i = tuple.iterator();
				Playlist pl = new Playlist();
//				book.setBookId(Integer.parseInt(i.next()));
				pl.setPlaylistId(playlistId++);				
				pl.setTitle(i.next());

				playList.add(pl);
			}
			return playList;
		} finally {
			readPlaylists.close();
		}
	}
}
