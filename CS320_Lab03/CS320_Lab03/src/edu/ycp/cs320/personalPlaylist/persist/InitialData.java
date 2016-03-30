package edu.ycp.cs320.personalPlaylist.persist;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import edu.ycp.cs320.booksdb.model.Author;
import edu.ycp.cs320.booksdb.model.Book;

public class InitialData {

	// reads initial Author data from CSV file and returns a List of Authors
	public static List<Artist> getAuthors() throws IOException {
		List<Artist> authorList = new ArrayList<Artist>();
		ReadCSV readAuthors = new ReadCSV("authors.csv");
		try {
			// auto-generated primary key for authors table
			Integer authorId = 1;
			while (true) {
				List<String> tuple = readAuthors.next();
				if (tuple == null) {
					break;
				}
				Iterator<String> i = tuple.iterator();
				Artist author = new Artist();
//				author.setAuthorId(Integer.parseInt(i.next()));
				author.setAuthorId(authorId++);				
				author.setLastname(i.next());
				author.setFirstname(i.next());
				authorList.add(author);
			}
			return authorList;
		} finally {
			readAuthors.close();
		}
	}
	
	// reads initial Book data from CSV file and returns a List of Books
	public static List<Book> getBooks() throws IOException {
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
