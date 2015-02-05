package entityTest;

import java.text.ParseException;
import java.util.Date;

import junit.framework.Assert;

import org.junit.Test;

import persistence.ParseUtil;
import entity.event.Event;
import entity.event.Invitation;
import entity.user.User;

public class EventTest {

	private User user1, user2, user3, user4;
	private Event event1, event2, event3, event4, event5;
	private Invitation invitation1, invitation2; 
	
	public void setUp() throws ParseException {
		user1 = new User("User1 name");
		user2 = new User("User2 name");
		event1 = new Event("Event 1", "Event 1 Description", "Event 1 Image Path", "Event 1 Info", ParseUtil.ptbr.parse("01/12/2014"), ParseUtil.ptbr.parse("02/12/2014"), 200.00, "", 100, true, new User("Anderson"));
		event2 = new Event("Event 2", "Event 2 Description", "Event 2 Image Path", "Event 2 Info", ParseUtil.ptbr.parse("01/12/2014"), ParseUtil.ptbr.parse("02/12/2014"), 200.00, "", 100, true, new User("Bruno"));
		event3 = new Event("Event 3", "Event 3 Description", "Event 3 Image Path", "Event 3 Info", ParseUtil.ptbr.parse("01/12/2014"), ParseUtil.ptbr.parse("02/12/2014"), 200.00, "", 100, true, new User("Diego"));
		event4 = new Event("Event 4", "Event 4 Description", "Event 4 Image Path", "Event 4 Info", ParseUtil.ptbr.parse("01/12/2014"), ParseUtil.ptbr.parse("02/12/2014"), 200.00, "", 100, true, new User("Marcos"));
		event5 = new Event("Event 5", "Event 5 Description", "Event 5 Image Path", "Event 5 Info", ParseUtil.ptbr.parse("01/12/2014"), ParseUtil.ptbr.parse("02/12/2014"), 200.00, "", 100, true, new User("Julio"));
		invitation1 = new Invitation(user1, user2, event1);
	}
	
	@Test
	public void testGetAndSetEventName(){
		Assert.assertEquals("Event 1", event1.getName());
		Assert.assertEquals("Event 2", event2.getName());
		Assert.assertEquals("Event 3", event3.getName());
		Assert.assertEquals("Event 4", event4.getName());
		Assert.assertEquals("Event 5", event5.getName());
		
		event1.setName("New name event1");
		event2.setName("New name event2");
		event3.setName("New name event3");
		event4.setName("New name event4");
		event5.setName("New name event5");
		
		Assert.assertEquals("New name event1", event1.getName());
		Assert.assertEquals("New name event2", event2.getName());
		Assert.assertEquals("New name event3", event3.getName());
		Assert.assertEquals("New name event4", event4.getName());
		Assert.assertEquals("New name event5", event5.getName());
		
	}
	@Test
	public void testGetAndSetEventDescription(){
		Assert.assertEquals("Event 1 Description", event1.getDescription());
		Assert.assertEquals("Event 2 Description", event2.getDescription());
		Assert.assertEquals("Event 3 Description", event3.getDescription());
		Assert.assertEquals("Event 4 Description", event4.getDescription());
		Assert.assertEquals("Event 5 Description", event5.getDescription());
		
		event1.setDescription("New event 1 Description");
		event2.setDescription("New event 2 Description");
		event3.setDescription("New event 3 Description");
		event4.setDescription("New event 4 Description");
		event5.setDescription("New event 5 Description");
		
		Assert.assertEquals("New event 1 Description", event1.getDescription());
		Assert.assertEquals("New event 2 Description", event2.getDescription());
		Assert.assertEquals("New event 3 Description", event3.getDescription());
		Assert.assertEquals("New event 4 Description", event4.getDescription());
		Assert.assertEquals("New event 5 Description", event5.getDescription());
		
	}
	
	@Test
	public void testGetAndSetEventImagePath(){
		Assert.assertEquals("Event 1 Image Path", event1.getPhoto());
		Assert.assertEquals("Event 2 Image Path", event2.getPhoto());
		
		event1.setPhoto("Event 1 Image Path");
		event2.setPhoto("Event 2 Image Path");
		
		Assert.assertEquals("Event 1 Image Path", event1.getDescription());
		Assert.assertEquals("Event 2 Image Path", event2.getDescription());
	}
	
	@Test
	public void testGetAndSetEventInfo(){
		Assert.assertEquals("Event 1 Info", event1.getInfo());
		Assert.assertEquals("Event 2 Info", event2.getInfo());
		
		event1.setPhoto("New Event 1 Info");
		event2.setPhoto("New Event 2 Info");
		
		Assert.assertEquals("New Event 1 Info", event1.getInfo());
		Assert.assertEquals("New Event 2 Info", event2.getInfo());
	}
	
	@Test
	public void testGetAndSetEventInitialDate(){
		Assert.assertEquals(1, event1.getInitialDate().getDay());
		Assert.assertEquals(12, event1.getInitialDate().getMonth());
		Assert.assertEquals(2014, event1.getInitialDate().getYear());
		
		event1.setInitialDate(new Date(2015, 02, 01) );
		
		Assert.assertEquals(1, event1.getInitialDate().getDay());
		Assert.assertEquals(2, event1.getInitialDate().getMonth());
		Assert.assertEquals(2015, event1.getInitialDate().getYear());
	}
}
