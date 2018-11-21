package se.erik.socialboard;

import java.time.LocalDate;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


import se.erik.socialboard.entity.User;

public class UserTest {
	
	private User testUser;
	
	@Before
	public void init() {
		testUser = new User("testUser@gmail.com","Test","User","mr Test",LocalDate.of(2018, 1, 1));
	}
	
	@Test
	public void testUserActivateFalseSetCorrectDate() {		
		LocalDate expected = LocalDate.now();
		testUser.setActive(false);
		LocalDate actual = testUser.getDeactivationDate();
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void testSetActiveToTrueDeactivationDateIsNull() {
		testUser.setActive(false);
		
		testUser.setActive(true);
		
		Assert.assertNull(testUser.getDeactivationDate());				
	}

}
