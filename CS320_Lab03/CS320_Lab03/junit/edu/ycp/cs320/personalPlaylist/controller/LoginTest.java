package edu.ycp.cs320.personalPlaylist.controller;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import edu.ycp.cs320.lab03.controller.LoginController;
import edu.ycp.cs320.personalPlaylist.model.Account;
import edu.ycp.cs320.personalPlaylist.model.AllUsers;


public class LoginTest {
	private LoginController controller;
	private Account benAccount;
	@Before
	public void setUp() {
		controller = new LoginController();
		benAccount = new Account("Ben", "Noodle");
		//Need to add ben's account to the AllUsers map/key to check this
		
	}
	
	@Test
	public void testUsercheck() throws Exception {

		assertEquals("Ben", benAccount.getUsername());	//This says that "Ben" is equal to model.getUsername 
		assertEquals("Noodle", benAccount.getPassword());	//Same thing as above
		
		assertTrue(controller.Usercheck("Ben", "Noodle")); //Asserts that usercheck is true
		
	}
}
