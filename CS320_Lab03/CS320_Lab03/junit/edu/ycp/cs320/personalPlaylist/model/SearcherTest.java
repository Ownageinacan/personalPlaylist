package edu.ycp.cs320.personalPlaylist.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class SearcherTest {

	private Searcher searcher;
	
	@Before
	public void setUp()
	{
		searcher = new Searcher(null, null, null, null); //may need this later
	}
	
	@Test
	public void testGetTitleContainsWord()
	{
		
	}
	
	@Test
	public void testSetTitleContainsWord()
	{
		
	}
	
	@Test
	public void testGetArtist()
	{
		
	}
	
	@Test
	public void testSetArtist()
	{
		
	}
	
	@Test
	public void testGetAlbum()
	{
		
	}
	
	@Test
	public void testSetAlbum()
	{
		
	}
	
	@Test
	public void testGetContainsSong()
	{
		
	}
	
	@Test
	public void testSetContainsSong()
	{
		
	}
	

	@Test
	public void testPlayListSearch()	//Probably don't need these
	{
		throw new UnsupportedOperationException("TODO - implement");
	}
	
	@Test
	public void testSongSearch()
	{
		throw new UnsupportedOperationException("TODO - implement");
	}
	
	@Test
	public void albumSearch()
	{
		throw new UnsupportedOperationException("TODO - implement");
	}
}
