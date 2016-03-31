package edu.ycp.cs320.personalPlaylist.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class LibraryTest {

	private Library library;
	private Library library2;
	private Library library3;
	
	@Before
	public void setUp() {
		library = new Library(12);	//New library with 12 songs
		library2 = new Library(200);	//New library with 200 songs
		library3 = new Library(-50);	//Can't have negative songs
	}

	@Test
	public void testGetNumberSongs() throws Exception
	{
		assertEquals(12, library.getNumberSongs());
		assertEquals(200, library2.getNumberSongs());
		assertEquals(0, library3.getNumberSongs());	//Returns 0 because we can't have a negative number of songs
	}

	@Test
	public void testSetNumberSongs() throws Exception
	{
		library.setNumberSongs(15);
		library2.setNumberSongs(-50);
		library3.setNumberSongs(80);
		
		assertEquals(15, library.getNumberSongs());
		assertEquals(0, library2.getNumberSongs());
		assertEquals(80, library3.getNumberSongs());
	}	

	@Test
	public void testAddSong() throws Exception
	{
		throw new UnsupportedOperationException("TODO - implement");
	}

	@Test
	public void testDeleteSong() throws Exception
	{
		throw new UnsupportedOperationException("TODO - implement");
	}

	@Test
	public void testShuffle() throws Exception
	{
		throw new UnsupportedOperationException("TODO - implement");
	}

}
