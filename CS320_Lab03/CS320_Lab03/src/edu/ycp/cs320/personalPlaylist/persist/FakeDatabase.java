package edu.ycp.cs320.personalPlaylist.persist;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import edu.ycp.cs320.personalPlaylist.model.Playlist;
import edu.ycp.cs320.personalPlaylist.model.Song;
import edu.ycp.cs320.personalPlaylist.model.Pair;

public class FakeDatabase implements IDatabase {

	private List<Song> songList;
	private List<Playlist> playList;

	// Fake database constructor - initializes the DB
	// the DB only consists for a List of Authors and a List of Books
	public FakeDatabase() {
		songList = new ArrayList<Song>();
		playList = new ArrayList<Playlist>();

		// Add initial data
		readInitialData();

		//		System.out.println(authorList.size() + " authors");
		//		System.out.println(bookList.size() + " books");
	}

	// loads the initial data retrieved from the CSV files into the DB
	public void readInitialData() {
		try {
			songList.addAll(InitialData.getSongs());
			playList.addAll(InitialData.getPlaylists());
		} catch (IOException e) {
			throw new IllegalStateException("Couldn't read initial data", e);
		}
	}

	public List<Song> findAllSongs()	//return entire library of songs
	{
		return songList;
	}

	public List<Playlist> findAllPlaylists()	//return all playlists
	{
		return playList;
	}
	public List<Pair<Song,Playlist>> findAllSongInPlaylist(String playlist)	//Return songs within a given playlist (TEMP)
	{
		List<Pair<Song, Playlist>> result = new ArrayList<Pair<Song, Playlist>>();

		for(Playlist pl : playList){
			if(pl.getTitle().equals(playlist)){	//See: getTitle method in playlist class

				for(Song song : songList){
					Song song = findSongBySongId(song.getSongId());
					result.add(new Pair<Song, Playlist>(song, pl));
				}
			}
		}
		return result;
	}

	private Song findSongBySongId(int songId)
	{
		for (Song song : songList) {
			if (song.getSongId() == songId) {
				return song;
			}
		}
		return null;
	}




	// query that retrieves Book and its Author by Title
	@Override
	public List<Pair<Artist, Book>> findAuthorAndBookByTitle(String title) {
		List<Pair<Artist, Book>> result = new ArrayList<Pair<Artist,Book>>();
		for (Book book : bookList) {
			//			System.out.println("Book: <" + book.getTitle() + ">" + "  Title: <" + title + ">");

			if (book.getTitle().equals(title)) {
				Artist author = findAuthorByAuthorId(book.getAuthorId());
				result.add(new Pair<Artist, Book>(author, book));
			}
		}
		return result;
	}

	// query that retrieves all Books, for the Author's last name
	@Override
	public List<Pair<Artist, Book>> findAuthorAndBookByAuthorLastName(String lastName)
	{
		// create list of <Author, Book> for returning result of query
		List<Pair<Artist, Book>> result = new ArrayList<Pair<Artist, Book>>();

		// search through table of Books
		for (Book book : bookList) {
			for (Artist author : authorList) {
				if (book.getAuthorId() == author.getAuthorId()) {
					if (author.getLastname().equals(lastName)) {
						// if this book is by the specified author, add it to the result list
						result.add(new Pair<Artist, Book>(author, book));						
					}
				}
			}
		}
		return result;
	}


	// query that retrieves all Books, with their Authors, from DB
	@Override
	public List<Pair<Artist, Book>> findAllBooksWithAuthors() {
		List<Pair<Artist, Book>> result = new ArrayList<Pair<Artist,Book>>();
		for (Book book : bookList) {
			Artist author = findAuthorByAuthorId(book.getAuthorId());
			result.add(new Pair<Artist, Book>(author, book));
		}
		return result;
	}


	// query that retrieves all Authors from DB
	@Override
	public List<Artist> findAllAuthors() {
		List<Artist> result = new ArrayList<Artist>();
		for (Artist author : authorList) {
			result.add(author);
		}
		return result;
	}


	// query that inserts a new Book, and possibly new Author, into Books and Authors lists
	// insertion requires that we maintain Book and Author id's
	// this can be a real PITA, if we intend to use the IDs to directly access the ArrayLists, since
	// deleting a Book/Author in the list would mean updating the ID's, since other list entries are likely to move to fill the space.
	// or we could mark Book/Author entries as deleted, and leave them open for reuse, but we could not delete an Author
	//    unless they have no Books in the Books table
	@Override
	public Integer insertBookIntoBooksTable(String title, String isbn, String lastName, String firstName)
	{
		int authorId = -1;
		int bookId   = -1;

		// search Authors list for the Author, by first and last name, get author_id
		for (Artist author : authorList) {
			if (author.getLastname().equals(lastName) && author.getFirstname().equals(firstName)) {
				authorId = author.getAuthorId();
			}
		}

		// if the Author wasn't found in Authors list, we have to add new Author to Authors list
		if (authorId < 0) {
			// set author_id to size of Authors list + 1 (before adding Author)
			authorId = authorList.size() + 1;

			// add new Author to Authors list
			Artist newAuthor = new Artist();			
			newAuthor.setAuthorId(authorId);
			newAuthor.setLastname(lastName);
			newAuthor.setFirstname(firstName);
			authorList.add(newAuthor);

			System.out.println("New author (ID: " + authorId + ") " + "added to Authors table: <" + lastName + ", " + firstName + ">");
		}

		// set book_id to size of Books list + 1 (before adding Book)
		bookId = bookList.size() + 1;

		// add new Book to Books list
		Book newBook = new Book();
		newBook.setBookId(bookId);
		newBook.setAuthorId(authorId);
		newBook.setTitle(title);
		newBook.setIsbn(isbn);
		bookList.add(newBook);

		// return new Book Id
		return bookId;
	}


	// query that retrieves an Author based on author_id
	private Artist findAuthorByAuthorId(int authorId) {
		for (Artist author : authorList) {
			if (author.getAuthorId() == authorId) {
				return author;
			}
		}
		return null;
	}
}
