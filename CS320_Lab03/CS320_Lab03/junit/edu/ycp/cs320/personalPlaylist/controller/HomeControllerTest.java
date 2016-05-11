package edu.ycp.cs320.personalPlaylist.controller;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import edu.ycp.cs320.lab03.controller.MasterController;
import edu.ycp.cs320.personalPlaylist.model.*;
import edu.ycp.cs320.personalPlaylist.persist.DatabaseProvider;
import edu.ycp.cs320.personalPlaylist.persist.DerbyDatabase;
import edu.ycp.cs320.personalPlaylist.persist.IDatabase;
import edu.ycp.cs320.personalPlaylistdb.InitDatabase;

public class HomeControllerTest 
{
	MasterController controller;
	List<Playlist> playlists;
	List<Account> accounts;
	private IDatabase db = null;
	
	@Before
	public void setUp() throws Exception 
	{
		controller = new MasterController();
		DatabaseProvider.setInstance(new DerbyDatabase());
		db = DatabaseProvider.getInstance();
		InitDatabase.init();
	}

	@Test
	public void getAllPlaylistsTest() 
	{
		playlists = new ArrayList<Playlist>();
		List<Playlist> listOfPlaylists = controller.getAllPlayLists();
		playlists = db.findAllPlaylists();
		//If the sizes of the each are the same and non-zero then the controller method 
		//is returning the correct number of playlists
		if(listOfPlaylists.size() != playlists.size() && playlists.size() != 0)
		{
			fail("Not all of the playlists were found");
		}
		for(int i=0; i<listOfPlaylists.size(); i++){
			assertTrue(playlists.get(i).getTitle().equals(listOfPlaylists.get(i).getTitle()));
		}		
	}
	
	@Test
	public void usercheckTest() 
	{
		//If this passes then the username ben and password noodle are correct
		//as they should be
		assertTrue(controller.Usercheck("ben", "noodle"));
	}
	
	@Test
	public void createAccountTest() 
	{
		controller.CreateAccount("tim", "toodle");
		accounts = new ArrayList<Account>();
		accounts = db.findAllAccounts();
		
		Account newAccount = accounts.get(accounts.size() - 1);
		if(newAccount.getUsername() == "tim" && newAccount.getPassword() == "toodle")
		{
			db.removeAccountByAccountName("tim");
			assertTrue(true);
		}
		else
		{
			fail("The account was not created");
		}
	}
	
	@Test
	public void createPlaylistTest() throws SQLException 
	{
		controller.CreatePlaylist("Durp", "ben");
		playlists = new ArrayList<Playlist>();
		playlists = db.findAllPlaylists();
		int userId = controller.getAccount_id("ben");
		
		Playlist newPlaylist = playlists.get(playlists.size() - 1);
		if(newPlaylist.getTitle() == "Durp" && newPlaylist.getUserOwnerId() == userId)
		{
			controller.deletePlaylist("Durp");
			assertTrue(true);
		}
		else
		{
			fail("The playlist was not created");
		}
	}
	
	@Test
	public void testgetSongsInAlbum()
	{
		//TODO: Implement
		fail("TODO: Implement");
	}
	
	@Test
	public void testgetSongsbyArtist()
	{
		//TODO: Implement
		fail("TODO: Implement");
	}
	
	@Test
	public void testgetSongsByGenre()
	{
		//TODO: Implement
		fail("TODO: Implement");
	}
}
