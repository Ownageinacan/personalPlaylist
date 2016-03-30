package edu.ycp.cs320.personalPlaylist.persist;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import edu.ycp.cs320.booksdb.model.Author;
import edu.ycp.cs320.booksdb.model.Book;
import edu.ycp.cs320.personalPlaylist.model.Playlist;
import edu.ycp.cs320.personalPlaylist.model.Song;

public class InitialData {

	// reads initial song data from CSV file and returns a List of songs
	public static List<Song> getSongs() throws IOException {
		List<Song> songList = new ArrayList<Song>();
		ReadCSV readSongs = new ReadCSV("songs.csv");
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
				
				//TEMPORARY HARD CODE; ASK YOURSELF WHY WOULD WE SET ANY OF THESE?
				song.setTitle(i.next());	//Set song title
				song.setArtist(i.next());	//Set song artist
				song.setAlbum(i.next());	//Set song album
				
				songList.add(song);	//Might be redundant
			}
			return songList;	//return the list of songs initialized
		} finally {
			readSongs.close();	//close the song reader
		}
	}
	
	
	
	
	// reads initial Book data from CSV file and returns a List of Books
	public static List<Playlist> getPlaylists() throws IOException {
		List<Book> bookList = new ArrayList<Book>();
		ReadCSV readBooks = new ReadCSV("books.csv");
		try {
			// auto-generated primary key for table books
			Integer bookId = 1;
			while (true) {
				List<String> tuple = readBooks.next();
				if (tuple == null) {
					break;
				}
				Iterator<String> i = tuple.iterator();
				Book book = new Book();
//				book.setBookId(Integer.parseInt(i.next()));
				book.setBookId(bookId++);				
				book.setAuthorId(Integer.parseInt(i.next()));
				book.setTitle(i.next());
				book.setIsbn(i.next());
				bookList.add(book);
			}
			return bookList;
		} finally {
			readBooks.close();
		}
	}
}
