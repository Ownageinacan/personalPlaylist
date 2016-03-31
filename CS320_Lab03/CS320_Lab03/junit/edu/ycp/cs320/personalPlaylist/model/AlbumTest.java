package edu.ycp.cs320.personalPlaylist.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;


public class AlbumTest {

	private Album testAlbum;
	
	@Before
	public void setUp() {
		testAlbum = new Album("Alex's memer ahoy");
	}

	@Test
	public void testGetTitle()
	{
		assertEquals("Alex's memer ahoy", testAlbum.getTitle());	//test to see if the correct title is returned
	}
	
	@Test
	public void testSetTitle()
	{
		testAlbum.setTitle("Can't live without the memes");
		assertEquals("Can't live without the memes", testAlbum.getTitle());
	}
}
