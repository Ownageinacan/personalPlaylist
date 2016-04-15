package edu.ycp.cs320.personalPlaylistdb;



import edu.ycp.cs320.personalPlaylist.persist.DatabaseProvider;
import edu.ycp.cs320.personalPlaylist.persist.DerbyDatabase;

public class InitDatabase {
	public static void init() {
			DatabaseProvider.setInstance(new DerbyDatabase()); //Persistent database to be implemented.
	}
}
