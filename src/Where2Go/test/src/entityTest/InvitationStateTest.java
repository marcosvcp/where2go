package entityTest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;

import entity.event.Event;
import entity.event.Invitation;
import entity.user.User;
import persistence.ParseUtil;

public class InvitationStateTest {
	private User user1, user2, user3, user4;
	private Event event1, event2, event3, event4, event5;
	private final String INVITATION_PENDING = "Pending";
	private final String INVITATION_DECLINED = "Declined";
	private final String INVITATION_CONFIRMED = "Confirmed";
	
	@Before
	public void setUp() throws ParseException {
		user1 = new User("User1 name");
		user2 = new User("User2 name");
		event1 = new Event("Event 1", "Event 1 Description", "Event 1 Image Path", "Event 1 Info", ParseUtil.ptbr.parse("01/12/2014"), ParseUtil.ptbr.parse("02/12/2014"), 200.00, "", 100, true, new User("Anderson"));
		event2 = new Event("Event 2", "Event 2 Description", "Event 2 Image Path", "Event 2 Info", ParseUtil.ptbr.parse("01/12/2014"), ParseUtil.ptbr.parse("02/12/2014"), 200.00, "", 100, true, new User("Bruno"));
		event3 = new Event("Event 3", "Event 3 Description", "Event 3 Image Path", "Event 3 Info", ParseUtil.ptbr.parse("01/12/2014"), ParseUtil.ptbr.parse("02/12/2014"), 200.00, "", 100, true, new User("Diego"));
		event4 = new Event("Event 4", "Event 4 Description", "Event 4 Image Path", "Event 4 Info", ParseUtil.ptbr.parse("01/12/2014"), ParseUtil.ptbr.parse("02/12/2014"), 200.00, "", 100, true, new User("Marcos"));
		event5 = new Event("Event 5", "Event 5 Description", "Event 5 Image Path", "Event 5 Info", ParseUtil.ptbr.parse("01/12/2014"), ParseUtil.ptbr.parse("02/12/2014"), 200.00, "", 100, true, new User("Julio"));
		
	}
	
	@Test
	public void testStates(){
		Invitation i1 = new Invitation(user1, user2, event1);
		Invitation i2 = new Invitation(user1, user3, event1);
		Assert.assertEquals(INVITATION_PENDING, i1.getState());
		i1.setState(INVITATION_CONFIRMED);
		Assert.assertEquals(INVITATION_CONFIRMED, i1.getState());
		
		Assert.assertEquals(INVITATION_PENDING, i2.getState());
		i2.setState(INVITATION_DECLINED);
		Assert.assertEquals(INVITATION_DECLINED, i2.getState());
		
	}
	
}
