package entityTest;

import java.text.ParseException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import persistence.ParseUtil;
import entity.event.Event;
import entity.event.Invitation;
import entity.user.User;

public class EventStateTest {
	private Event event1, event2, event3, event4, event5;
	private final String EVENT_CANCELED = "Canceled";
	private final String EVENT_FINISHED = "Finished";
	private final String EVENT_OPENED = "Opened";
	
	@Before
	public void setUp() throws ParseException {
		event1 = new Event("Event 1", "Event 1 Description", "Event 1 Image Path", "Event 1 Info", ParseUtil.ptbr.parse("01/12/2014"), ParseUtil.ptbr.parse("02/12/2014"), 200.00, "", 100, true, new User("Anderson"));
		event2 = new Event("Event 2", "Event 2 Description", "Event 2 Image Path", "Event 2 Info", ParseUtil.ptbr.parse("01/12/2014"), ParseUtil.ptbr.parse("02/12/2014"), 200.00, "", 100, true, new User("Bruno"));
		event3 = new Event("Event 3", "Event 3 Description", "Event 3 Image Path", "Event 3 Info", ParseUtil.ptbr.parse("01/12/2014"), ParseUtil.ptbr.parse("02/12/2014"), 200.00, "", 100, true, new User("Diego"));
		event4 = new Event("Event 4", "Event 4 Description", "Event 4 Image Path", "Event 4 Info", ParseUtil.ptbr.parse("01/12/2014"), ParseUtil.ptbr.parse("02/12/2014"), 200.00, "", 100, true, new User("Marcos"));
		event5 = new Event("Event 5", "Event 5 Description", "Event 5 Image Path", "Event 5 Info", ParseUtil.ptbr.parse("01/12/2014"), ParseUtil.ptbr.parse("02/12/2014"), 200.00, "", 100, true, new User("Julio"));
		
	}
	
	@Test
	public void testStates(){
		Assert.assertEquals(EVENT_OPENED, event1.getState());
		event1.setState(EVENT_FINISHED);
		Assert.assertEquals(EVENT_FINISHED, event1.getState());
		
		Assert.assertEquals(EVENT_OPENED, event2.getState());
		event2.setState(EVENT_CANCELED);
		Assert.assertEquals(EVENT_CANCELED, event2.getState());
		
	}
}
