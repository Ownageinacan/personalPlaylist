package edu.ycp.cs320.personalPlaylist.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class PlaylistTest {

	private Playlist playlist;
	private Playlist playlist2;
	private Playlist playlist3;
	
	@Before
	public void setUp() {
		playlist = new Playlist(15, "playlist1", 4);
		playlist2 = new Playlist(22, "pl2", 15);
		playlist3 = new Playlist(420, "bernie sanders is an abstract artist", 3);
	}
	
	@Test
	public void testSetPlaylistId()
	{
		playlist.setPlaylistId(1);
		playlist2.setPlaylistId(2);
		playlist3.setPlaylistId(-50);
		
		assertEquals(1, playlist.getPlaylistId());
		assertEquals(2, playlist2.getPlaylistId());
		assertEquals(0, playlist3.getPlaylistId());
	}
	
	@Test
	public void testGetPlaylistId()
	{

		assertEquals(4, playlist.getPlaylistId());
		assertEquals(15, playlist2.getPlaylistId());
		assertEquals(3, playlist3.getPlaylistId());
	}
	
	@Test
	public void testSetTitle()
	{
		playlist.setTitle("To meme or not to meme?");
		playlist2.setTitle("Shawn says 'why is this awful'");
		playlist3.setTitle("I won't tell him that junit tests aren't that bad");
		
		assertEquals("To meme or not to meme?", playlist.getTitle());
		assertEquals("Shawn says 'why is this awful'", playlist2.getTitle());
		assertEquals("I won't tell him that junit tests aren't that bad", playlist3.getTitle());
	}
	
	@Test
	public void testGetTitle()
	{
		assertEquals("playlist1",playlist.getTitle());
		assertEquals("pl2",playlist2.getTitle());
		assertEquals("bernie sanders is an abstract artist",playlist3.getTitle());
	}
	
	@Test
	public void testGetNumberSongs()
	{
		assertEquals(15, playlist.getNumberSongs());
		assertEquals(22, playlist2.getNumberSongs());
		assertEquals(420, playlist3.getNumberSongs());
	}
	
	@Test
	public void testSetNumberSongs()
	{
		playlist.setNumberSongs(2);
		playlist2.setNumberSongs(600);
		playlist3.setNumberSongs(-2);
		
		assertEquals(2, playlist.getNumberSongs());
		assertEquals(600, playlist2.getNumberSongs());
		assertEquals(0, playlist3.getNumberSongs());
	}
	@Test
	public void testAddSong()
	{
		playlist.addSong("Memes ahoy");
		
	}
	@Test
	public void testRemoveSong()
	{
		throw new UnsupportedOperationException("TODO - implement");
	}
	@Test
	public void testSkipSong()
	{
		throw new UnsupportedOperationException("TODO - implement");
	}
	@Test
	public void testReverseSong()
	{
		throw new UnsupportedOperationException("TODO - implement");
	}
	@Test
	public void testShuffle()
	{
		throw new UnsupportedOperationException("TODO - implement");
	}
	
}
