package edu.ycp.cs320.personalPlaylist.controller;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import edu.ycp.cs320.lab03.controller.HomeController;
import edu.ycp.cs320.personalPlaylist.model.*;
import edu.ycp.cs320.personalPlaylist.persist.DatabaseProvider;
import edu.ycp.cs320.personalPlaylist.persist.DerbyDatabase;
import edu.ycp.cs320.personalPlaylist.persist.IDatabase;
import edu.ycp.cs320.personalPlaylistdb.InitDatabase;

public class HomeControllerTest 
{
	HomeController controller;
	List<Playlist> playlists;
	private IDatabase db = null;
	
	@Before
	public void setUp() throws Exception 
	{
		controller = new HomeController();
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
		if(listOfPlaylists != playlists)
		{
			fail("Not all of the playlists were found");
		}
		assertTrue(controller.getAllPlayLists() == db.findAllPlaylists());
		
	}

}