package edu.ycp.cs320.personalPlaylist.controller;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import edu.ycp.cs320.lab03.controller.LoginController;
import edu.ycp.cs320.personalPlaylist.model.Account;


public class LoginTest {
	private LoginController controller;
	
	@Before
	public void setUp() {
		controller = new LoginController();
		
		
	}
	
	@Test
	public void testUsercheck() throws Exception {
		Account aModel = new Account();
		
		assertEquals("Ben", aModel.getUsername());	//This says that "Ben" is equal to model.getUsername (WHICH IS CURRENTLY FALSE, no setup)
		assertEquals("Noodle", aModel.getPassword());	//Same thing as above
		
		assertTrue(controller.Usercheck("Ben", "Noodle")); //Asserts that usercheck is true
		
	}
}
