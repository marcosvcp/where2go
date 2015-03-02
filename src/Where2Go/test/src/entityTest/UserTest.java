package entityTest;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

import com.parse.ParseObject;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import entity.establishment.Establishment;
import entity.event.Event;
import entity.event.Invitation;
import entity.user.User;
import persistence.ParseUtil;

/**
 * Testes da entidade usuario
 * Created by brunomb on 02/12/14.
 */
public class UserTest extends TestCase{
	private User user1, user2;
	
	@Before
	public void setUp() throws ParseException {
		user1 = new User("User1 name");
		user2 = new User("User2 name");
	}
	
	@Test
	public void testUserConstructor() {
        ParseObject.registerSubclass(Event.class);
        ParseObject.registerSubclass(User.class);
        ParseObject.registerSubclass(Invitation.class);
        ParseObject.registerSubclass(Establishment.class);
		user1 = null;
		user2 = null;
		user1 = new User("New Test User1");
		user2 = new User("New Test User2");
		user1.setName("User1 name");
		user2.setName("User2 name");
		user1.setAge(25);
		user2.setAge(25);
		Assert.assertNotNull("User constructor not working", user1);
		Assert.assertNotNull("User constructor not working", user2);
	}
	
	@Test
	public void testGetName() {
		Assert.assertNotNull("User getName not working", user1.getName());
		Assert.assertNotNull("User getName not working", user2.getName());
		Assert.assertEquals("User getName not working", "User1 name", user1.getName());
		Assert.assertNotSame("User getName not working", "User2 name", user2.getName());
	}
	
	@Test
	public void testSetName() {
		user1.setName("New User1 name");
		Assert.assertEquals("User SetName not working", "New User1 name", user1.getName());
		Assert.assertNotSame("User setName not working", "User1 name", user1.getName());
		user2.setName("New User2 name");
		Assert.assertEquals("User SetName not working", "New User2 name", user2.getName());
		Assert.assertNotSame("User setName not working", "User2 name", user2.getName());
	}
	
	@Test
	public void testGetAge() {
		Assert.assertNotNull("User getAge not working", user1.getAge());
		Assert.assertNotNull("User getAge not working", user2.getAge());
		Assert.assertEquals("User getAge not working", 25, user1.getAge());
		Assert.assertNotSame("User getAge not working", 25, user2.getAge());
	}
	
	@Test
	public void testSetAge() {
		user1.setAge(50);
		Assert.assertEquals("User SetAge not working", 50, user1.getAge());
		Assert.assertNotSame("User setAge not working", 25, user1.getAge());
		user2.setAge(45);
		Assert.assertEquals("User SetAge not working", 45, user2.getAge());
		Assert.assertNotSame("User SetAge not working", 30, user2.getAge());
	}
	
	@Test
	public void testHashCode() {
		Assert.assertNotNull(user1.hashCode());
		Assert.assertNotNull(user2.hashCode());
	}
	
	@Test
	public void testEquals() {
		Assert.assertFalse("User Equals not working", user1.equals(user2));
		Assert.assertTrue("User Equals not working", user1.equals(user1));
	}
	
}
