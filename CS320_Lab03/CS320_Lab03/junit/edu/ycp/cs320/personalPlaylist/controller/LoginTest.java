package edu.ycp.cs320.personalPlaylist.controller;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import edu.ycp.cs320.lab03.controller.MasterController;
import edu.ycp.cs320.personalPlaylist.model.Account;

//TODO: FINISH THIS JAZZ

public class LoginTest {
	private MasterController controller;
	private Account benAccount;
	@Before
	public void setUp() {
		controller = new MasterController();
		benAccount = new Account();
		//Need to add ben's account to the AllUsers map/key to check this
		
	}
	
	@Test
	public void testUsercheck() throws Exception {
		
		assertTrue(controller.Usercheck("ben", "noodle")); //Asserts that usercheck is true
		
	}
}
