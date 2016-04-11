package edu.ycp.cs320.personalPlaylistdb;

import java.util.Scanner;

import edu.ycp.cs320.personalPlaylist.persist.DatabaseProvider;
import edu.ycp.cs320.personalPlaylist.persist.DerbyDatabase;

public class InitDatabase {
	public static void init(Scanner keyboard) {
		System.out.print("Which database (0=fake, 1=derby): ");
		int which = Integer.parseInt(keyboard.nextLine());
		if (which == 0) {
			System.out.print("FakeDatabase has not and will not be implemented. Goodbye.");
			DatabaseProvider.setInstance(null/*new FakeDatabase()*/); //lets not use the fake database since it doesn't work anyway
		} else if (which == 1) {
			DatabaseProvider.setInstance(new DerbyDatabase()); //Persistent database to be implemented.
		} else {
			throw new IllegalArgumentException("Invalid choice: " + which);
		}
	}
}
