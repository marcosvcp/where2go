package entityTest;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

import android.test.InstrumentationTestCase;
import android.test.InstrumentationTestRunner;

import com.parse.ParseObject;

import java.text.ParseException;

import entity.establishment.Establishment;
import entity.event.Event;
import entity.event.Invitation;
import entity.user.User;

/**
 * Testes da entidade usuario
 * Created by brunomb on 02/12/14.
 */
public class UserTest extends InstrumentationTestCase{
	private User user1, user2;
	
	@Before
	public void setUp() throws ParseException {
		ParseObject.registerSubclass(Event.class);
        ParseObject.registerSubclass(User.class);
        ParseObject.registerSubclass(Invitation.class);
        ParseObject.registerSubclass(Establishment.class);
		user1 = new User("42");
		user1.setName("User1 name");
		user1.setBirthday("01/01/2001");
		user1.setEmail("user1@email.com");
		user1.setAge(25);
		user1.setGender("male");
		user2 = new User("13");
		user2.setName("User2 name");
		user2.setBirthday("02/02/2002");
		user2.setEmail("user2@email.com");
		user2.setAge(30);
		user2.setGender("female");
	}

	@Test
	public void testUserConstructor() {
		user1 = null;
		user2 = null;
		Assert.assertNull(user1);
		Assert.assertNull(user2);
		user1 = new User("42");
		user2 = new User("13");
		Assert.assertNotNull("User constructor not working", user1);
		Assert.assertNotNull("User constructor not working", user2);
		user1 = null;
		user2 = null;
		Assert.assertNull(user1);
		Assert.assertNull(user2);
		user1 = new User();
		user2 = new User();
		Assert.assertNotNull("User constructor not working", user1);
		Assert.assertNotNull("User constructor not working", user2);
	}
	
	@Test
	public void testGetFacebookId(){
		Assert.assertEquals("User getFacebookId not working", "42", user1.getFacebookId());
		Assert.assertEquals("User getFacebookId not working", "13", user2.getFacebookId());
	}
	
	@Test
	public void testSetFacebookId(){
		user1.setFacebookId("10");
		user2.setFacebookId("20");
		Assert.assertEquals("User getFacebookId not working", "10", user1.getFacebookId());
		Assert.assertEquals("User getFacebookId not working", "20", user2.getFacebookId());
	}
	
	@Test
	public void testGetName() {
		Assert.assertEquals("User getName not working. ", "User1 name", user1.getName());
		Assert.assertEquals("User getName not working. ", "User2 name", user2.getName());
	}
	
	@Test
	public void testSetName() {
		user1.setName("New User1 name");
		Assert.assertEquals("User SetName not working. ", "New User1 name", user1.getName());
		Assert.assertNotSame("User setName not working. ", "User1 name", user1.getName());
		user2.setName("New User2 name");
		Assert.assertEquals("User SetName not working. ", "New User2 name", user2.getName());
		Assert.assertNotSame("User setName not working. ", "User2 name", user2.getName());
	}
	
	@Test
	public void testGetBirthday() {
		Assert.assertEquals("User getBirthday not working. ", "01/01/2001", user1.getBirthday());
		Assert.assertEquals("User getBirthday not working. ", "02/02/2002", user2.getBirthday());
	}
	
	@Test
	public void testSetBirthday() {
		user1.setBirthday("01/01/2010");
		Assert.assertEquals("User SetBirthday not working. ", "01/01/2010", user1.getBirthday());
		Assert.assertNotSame("User SetBirthday not working. ", "01/01/2001", user1.getBirthday());
		user2.setBirthday("02/02/2010");
		Assert.assertEquals("User SetBirthday not working. ", "02/02/2010", user2.getBirthday());
		Assert.assertNotSame("User SetBirthday not working. ", "02/02/2002", user2.getBirthday());
	}
	
	@Test
	public void testGetEmail() {
		Assert.assertEquals("User getEmail not working. ", "user1@email.com", user1.getEmail());
		Assert.assertEquals("User getEmail not working. ", "user2@email.com", user2.getEmail());
	}
	
	@Test
	public void testSetEmail() {
		user1.setEmail("newuser1@email.com");
		Assert.assertEquals("User SetEmail not working. ", "newuser1@email.com", user1.getEmail());
		Assert.assertNotSame("User SetEmail not working. ", "01/01/2001", user1.getEmail());
		user2.setEmail("newuser2@email.com");
		Assert.assertEquals("User SetEmail not working. ", "newuser2@email.com", user2.getEmail());
		Assert.assertNotSame("User SetEmail not working. ", "02/02/2002", user2.getEmail());
	}
	
	@Test
	public void testGetAge() {
		Assert.assertEquals("User getEmail not working. ", 25, user1.getAge());
		Assert.assertEquals("User getEmail not working. ", 30, user2.getAge());
	}
	
	@Test
	public void testSetAge() {
		user1.setAge(42);
		Assert.assertEquals("User setAge not working. ", 42, user1.getAge());
		Assert.assertNotSame("User setAge not working. ", 25, user1.getAge());
		user2.setAge(13);
		Assert.assertEquals("User setAge not working. ", 13, user2.getAge());
		Assert.assertNotSame("User setAge not working. ", 30, user2.getAge());
	}
	
	@Test
	public void testGetGender() {
		Assert.assertEquals("User getGender not working. ", "male", user1.getGender());
		Assert.assertEquals("User getGender not working. ", "female", user2.getGender());
	}
	
	@Test
	public void testSetGender() {
		user1.setGender("female");
		Assert.assertEquals("User setGender not working. ", "female", user1.getGender());
		Assert.assertNotSame("User setGender not working. ", "male", user1.getGender());
		user2.setGender("male");
		Assert.assertEquals("User setGender not working. ", "male", user2.getGender());
		Assert.assertNotSame("User setGender not working. ", "female", user2.getGender());
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
