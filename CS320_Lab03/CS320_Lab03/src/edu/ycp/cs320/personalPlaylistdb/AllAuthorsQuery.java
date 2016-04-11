package edu.ycp.cs320.personalPlaylistdb;

import java.util.List;
import java.util.Scanner;

import edu.ycp.cs320.personalPlaylist.model.Artist;
import edu.ycp.cs320.personalPlaylist.persist.DatabaseProvider;
import edu.ycp.cs320.personalPlaylist.persist.IDatabase;

public class AllAuthorsQuery {
	public static void main(String[] args) throws Exception {
		Scanner keyboard = new Scanner(System.in);

		// Create the default IDatabase instance
		InitDatabase.init(keyboard);
		
		// get the DB instance and execute transaction
		IDatabase db = DatabaseProvider.getInstance();
		List<Author> authorList = db.findAllAuthors();
		
		// check if anything was returned and output the list
		if (authorList.isEmpty()) {
			System.out.println("There are no authors in the database");
		}
		else {
			for (Author author : authorList) {
				System.out.println(author.getLastname() + ", " + author.getFirstname());
			}
		}
	}
}