package edu.ycp.cs320.personalPlaylist.persist;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import edu.ycp.cs320.personalPlaylist.model.Account;
import edu.ycp.cs320.personalPlaylist.model.Album;
import edu.ycp.cs320.personalPlaylist.model.Artist;
import edu.ycp.cs320.personalPlaylist.model.Genre;
import edu.ycp.cs320.personalPlaylist.model.PlayListSongs;
import edu.ycp.cs320.personalPlaylist.model.Playlist;
import edu.ycp.cs320.personalPlaylist.model.Song;

public class InitialData {

	// reads initial song data from CSV file and returns a List of songs
	public static List<Song> getSongs() throws IOException {
		List<Song> songList = new ArrayList<Song>();
		ReadCSV readSongs = new ReadCSV("songs.csv");	//Create ReadCSV object
		try {
			System.out.println("getting songs from csv");
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
				//TEMPORARY HARD CODE; ASK YOURSELF WHY WE WOULD SET ANY OF THESE?
				song.setTitle(i.next());	//Set song title
				song.setLocation(i.next());
				song.setArtistId(Integer.parseInt(i.next()));
				song.setAlbumId(Integer.parseInt(i.next()));
				song.setGenreId(Integer.parseInt(i.next()));
				song.setSongId(songId++); //Set song ID
				
				songList.add(song);	//Might be redundant
			}
			System.out.println("returning the songs");
			return songList;	//return the list of songs initialized
		} finally {				//Finally always runs
			readSongs.close();	//close the song reader
		}
	}
	
	
	
	
	// reads initial PlayList data from CSV file and returns a List of Books
	public static List<Playlist> getPlaylists() throws IOException {
		List<Playlist> playList = new ArrayList<Playlist>();
		ReadCSV readPlaylists = new ReadCSV("playlists.csv");
		try {
			System.out.println("getting playlists from csv");
			// auto-generated primary key for table books
			Integer playlistId = 1;
			while (true) {
				List<String> tuple = readPlaylists.next();
				if (tuple == null) {
					break;
				}
				Iterator<String> i = tuple.iterator();
				Playlist pl = new Playlist(); //PLAYLIST CONSTRUCTOR WAS CHANGED; THIS MAY CAUSE FUTURE PAIN. Each playlist contains 5 songs for now
				pl.setTitle(i.next());
				pl.setNumberSongs(Integer.parseInt(i.next()));
				pl.setUserOwnerId(Integer.parseInt(i.next()));
				pl.setPlaylistId(playlistId++);
//				book.setBookId(Integer.parseInt(i.next()));
//				pl.setPlaylistId(playlistId++);					//Restore these if playlist constructor is changed again	
//				pl.setTitle(i.next());							//

				playList.add(pl);
			}
			System.out.println("returning the playlists");
			return playList;
		} finally {
			readPlaylists.close();
		}
	}
	
	//reads initial artist data from a CSV
	public static List<Artist> getArtists() throws IOException {
		List<Artist> ArtistList = new ArrayList<Artist>();
		ReadCSV readArtists = new ReadCSV("artists.csv");	//Create ReadCSV object
		try {
			System.out.println("getting artists from csv");
			// auto-generated primary key for authors table
			Integer artistId = 1;
			while (true) {
				List<String> tuple = readArtists.next();//Tuple crap that I don't understand still
				if (tuple == null) {
					break;
				}
				Iterator<String> i = tuple.iterator();	
				Artist artist = new Artist();		//Create a artist object
//				author.setAuthorId(Integer.parseInt(i.next()));
				
				artist.setArtistId(artistId++);
				artist.setArtistName(i.next());	//Set song title
				
				ArtistList.add(artist);	//Might be redundant
			}
			System.out.println("returning the artists");
			return ArtistList;	//return the list of artists initialized
		} finally {				//Finally always runs
			readArtists.close();	//close the song reader
		}
	}
	
	//reads initial genre data from a CSV
		public static List<Genre> getGenres() throws IOException {
			List<Genre> GenreList = new ArrayList<Genre>();
			ReadCSV readGenres = new ReadCSV("genres.csv");	//Create ReadCSV object
			try {
				System.out.println("getting genres from csv");
				// auto-generated primary key for authors table
				Integer genreId = 1;
				while (true) {
					List<String> tuple = readGenres.next();//Tuple crap that I don't understand still
					if (tuple == null) {
						break;
					}
					Iterator<String> i = tuple.iterator();	
					Genre genre = new Genre();		//Create a artist object
//					author.setAuthorId(Integer.parseInt(i.next()));
					
					genre.setGenreId(genreId++);
					genre.setGenre(i.next());	//Set genre title
					
					GenreList.add(genre);	//Might be redundant
				}
				System.out.println("returning the genres");
				return GenreList;	//return the list of artists initialized
			} finally {				//Finally always runs
				readGenres.close();	//close the song reader
			}
		}
		
		//reads initial album data from a CSV
				public static List<Album> getAblum() throws IOException {
					List<Album> AlbumList = new ArrayList<Album>();
					ReadCSV readAlbums = new ReadCSV("albums.csv");	//Create ReadCSV object
					try {
						System.out.println("getting albums from csv");
						// auto-generated primary key for authors table
						Integer albumId = 1;
						while (true) {
							List<String> tuple = readAlbums.next();//Tuple crap that I don't understand still
							if (tuple == null) {
								break;
							}
							Iterator<String> i = tuple.iterator();	
							Album album = new Album();		//Create a album object
//							author.setAuthorId(Integer.parseInt(i.next()));
							
							album.setAlbumId(albumId++);
							album.setTitle(i.next());	//Set genre title
							
							AlbumList.add(album);	//Might be redundant
						}
						System.out.println("returning the albums");
						return AlbumList;	//return the list of albums initialized
					} finally {				//Finally always runs
						readAlbums.close();	//close the song reader
					}
				}
				
				//reads initial user data from a CSV
				public static List<Account> getAccounts() throws IOException {
					List<Account> AccountList = new ArrayList<Account>();
					ReadCSV readAccounts = new ReadCSV("users.csv");	//Create ReadCSV object
					try {
						System.out.println("getting accounts from csv");
						// auto-generated primary key for authors table
						Integer accountId = 1;
						while (true) {
							List<String> tuple = readAccounts.next();//Tuple crap that I don't understand still
							if (tuple == null) {
								break;
							}
							Iterator<String> i = tuple.iterator();	
							Account account = new Account();		//Create a user object
//							author.setAuthorId(Integer.parseInt(i.next()));
							
							account.setUsername(i.next());
							account.setPassword(i.next());	//Set genre title
							account.setAccountId(accountId++);
							
							AccountList.add(account);	//Might be redundant
						}
						System.out.println("returning the accounts");
						return AccountList;	//return the list of accounts initialized
					} finally {				//Finally always runs
						readAccounts.close();	//close the song reader
					}
				}
				//reads initial PlaylistSongs data from a CSV
				public static List<PlayListSongs> getplaylistSongs() throws IOException {
					List<PlayListSongs> PlayListSongsList = new ArrayList<PlayListSongs>();
					ReadCSV readPlayListSongs = new ReadCSV("PlayListSongs.csv");	//Create ReadCSV object
					try {
						System.out.println("getting playlistSongs from csv");
						// auto-generated primary key for authors table
						while (true) {
							List<String> tuple = readPlayListSongs.next();//Tuple crap that I don't understand still
							if (tuple == null) {
								break;
							}
							Iterator<String> i = tuple.iterator();	
							PlayListSongs playlistsong = new PlayListSongs();		//Create a user object
//							author.setAuthorId(Integer.parseInt(i.next()));
							
							playlistsong.setPlayListId(Integer.parseInt(i.next()));
							playlistsong.setSongId(Integer.parseInt(i.next()));
							
							PlayListSongsList.add(playlistsong);	//Might be redundant
						}
						System.out.println("returning the playlistSongs");
						return PlayListSongsList;	//return the list of artists initialized
					} finally {				//Finally always runs
						readPlayListSongs.close();	//close the song reader
					}
				}
}
