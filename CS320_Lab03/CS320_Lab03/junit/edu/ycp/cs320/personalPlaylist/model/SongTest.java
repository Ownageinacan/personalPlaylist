package edu.ycp.cs320.personalPlaylist.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class SongTest {

	private Song Song00;
	private Song Song01;
	private Song Song02;
	
	@Before
	public void setUp()
	{
		Song00 = new Song("I'm not a weaboo", "http://google.com");
		Song01 = new Song("Dance till you ded", "http:/songs/");
		Song02 = new Song("This is not a song", "/songlocation/");
	}
	
	@Test
	public void testGetTitle()
	{
		assertEquals("I'm not a weaboo", Song00.getTitle());
		assertEquals("Dance till you ded", Song01.getTitle());
		assertEquals("This is not a song", Song02.getTitle());
	}

}
