package entityTest;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import android.test.InstrumentationTestCase;

import com.parse.ParseFile;
import com.parse.ParseObject;

import entity.establishment.Establishment;
import entity.event.Event;
import entity.event.Invitation;
import entity.user.User;

public class EventTest extends InstrumentationTestCase {

    private User user1, user2, user3, user4;
    private final DateFormat formatter = new SimpleDateFormat("MM/dd/yy");
    private Event event1, event2, event3, event4, event5;
    private Invitation invitation1, invitation2;
    private ParseFile user1photo;

    @Override
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

        event1 = new Event("Event Name 1", "Event 1 Description",
                formatter.parse("03/15/15"), formatter.parse("03/15/15"), "",
                "", true, user1,true);
        event1.setNote("Event Note 1");
        final byte[] data = new byte[10];
        user1photo = new ParseFile(data);
        event1.setPhoto(user1photo);
        event1.setPrice(10.00);
        event1.setOutfit("Gala");
        ;
        event1.setCapacity(100);
        event1.addTags("@testtag1@testtag2");

        event2 = new Event("Event Name 2", "Event 2 Description",
                formatter.parse("02/29/02"), formatter.parse("02/29/02"), "",
                "", true, user2,false);

        invitation1 = new Invitation(user1, user2, event1);
    }

    @Test
    public void testEventConstructor() throws ParseException {
        event1 = null;
        Assert.assertNull(event1);
        event1 = new Event();
        Assert.assertNotNull("Event constructor not working", event1);
        event1 = null;
        Assert.assertNull(event1);
        event1 = new Event("New Event Name", "New Event 1 Description",
                formatter.parse("01/29/02"), formatter.parse("01/29/02"),
                "20:00", "23:00", true, user1,false);
        Assert.assertNotNull("User constructor not working", user1);
    }

    // Não sei se vai funcionar por causa da conexao com o servidor
    // @Test
    // public void testGetOwner() throws com.parse.ParseException {
    // assertNotNull("getOwner not working", event1.getOwner());
    // }

    // Não sei se vai funcionar por causa da conexao com o servidor
    // @Test
    // public void testSetOwner() throws com.parse.ParseException {
    // event1.setOwner(user2);
    // assertNotNull("getOwner not working", event1.getOwner());
    // }

    @Test
    public void testGetOwnerName() {
        Assert.assertEquals("Event getOwnerName not working. ", "User1 name",
                event1.getOwnerName());
    }

    @Test
    public void testSetOwnerName() {
        event1.setOwnerName("New User1 name");
        Assert.assertEquals("Event setOwnerName not working. ",
                "New User1 name", event1.getOwnerName());
        Assert.assertNotSame("Event setOwnerName not working. ", "User1 name",
                event1.getOwnerName());
    }

    @Test
    public void testGetName() {
        Assert.assertEquals("Event getName not working. ", "Event Name 1",
                event1.getName());
    }

    @Test
    public void testSetName() {
        event1.setName("New Event1 name");
        Assert.assertEquals("Event SetName not working. ", "New Event1 name",
                event1.getName());
        Assert.assertNotSame("Event setName not working. ", "Event Name 1",
                event1.getName());
    }

    @Test
    public void testGetNote() {
        Assert.assertEquals("Event getNote not working. ", "Event Note 1",
                event1.getNote());
    }

    @Test
    public void testSetNote() {
        event1.setNote("New Event1 Note");
        Assert.assertEquals("Event SetNote not working. ", "New Event1 Note",
                event1.getNote());
        Assert.assertNotSame("Event setNote not working. ", "Event Note 1",
                event1.getNote());
    }

    @Test
    public void testGetDescription() {
        Assert.assertEquals("Event getDescription not working. ",
                "Event 1 Description", event1.getDescription());
    }

    @Test
    public void testSetDescription() {
        event1.setDescription("New Event1 Description");
        Assert.assertEquals("Event setDescription not working. ",
                "New Event1 Description", event1.getDescription());
        Assert.assertNotSame("Event setDescription not working. ",
                "Event 1 Description", event1.getDescription());
    }

    @Test
    public void testGetPhoto() {
        Assert.assertEquals("Event getPhoto not working. ", user1photo,
                event1.getPhoto());
    }

    @Test
    public void testSetPhoto() {
        final byte[] newData = new byte[42];
        final ParseFile newUser1photo = new ParseFile(newData);
        event1.setPhoto(newUser1photo);
        Assert.assertEquals("Event setPhoto not working. ", newUser1photo,
                event1.getPhoto());
        Assert.assertNotSame("Event setPhoto not working. ", user1photo,
                event1.getPhoto());
    }

    @Test
    public void testGetInitialDate() throws ParseException {
        Assert.assertEquals("Event getInitialDate not working. ",
                formatter.parse("03/15/15"), event1.getInitialDate());
    }

    @Test
    public void testSetInitialDate() throws ParseException {
        event1.setInitialDate(formatter.parse("03/16/15"));
        Assert.assertEquals("Event setInitialDate not working. ",
                formatter.parse("03/16/15"), event1.getInitialDate());
        Assert.assertNotSame("Event setInitialDate not working. ",
                formatter.parse("03/15/15"), event1.getInitialDate());
    }

    @Test
    public void testGetFinalDate() throws ParseException {
        Assert.assertEquals("Event getfinalDate not working. ",
                formatter.parse("03/15/15"), event1.getFinalDate());
    }

    @Test
    public void testSetFinalDate() throws ParseException {
        event1.setFinalDate(formatter.parse("03/16/15"));
        Assert.assertEquals("Event setFinalDate not working. ",
                formatter.parse("03/16/15"), event1.getFinalDate());
        Assert.assertNotSame("Event setFinalDate not working. ",
                formatter.parse("03/15/15"), event1.getFinalDate());
    }

    @Test
    public void testGetPrice() throws ParseException {
        Assert.assertEquals("Event getPrice not working. ", 10.00,
                event1.getPrice());
    }

    @Test
    public void testSetPrice() throws ParseException {
        event1.setPrice(50.00);
        Assert.assertEquals("Event setPrice not working. ", 50.00,
                event1.getPrice());
        Assert.assertNotSame("Event setPrice not working. ", 10.00,
                event1.getPrice());
    }

    @Test
    public void testGetOutfit() throws ParseException {
        Assert.assertEquals("Event getOutfit not working. ", "Gala",
                event1.getOutfit());
    }

    @Test
    public void testSetOutfit() throws ParseException {
        event1.setOutfit("Esporte Fino");
        Assert.assertEquals("Event setOutfit not working. ", "Esporte Fino",
                event1.getOutfit());
        Assert.assertNotSame("Event setOutfit not working. ", "Gala",
                event1.getOutfit());
    }

    @Test
    public void testGetCapacity() throws ParseException {
        Assert.assertEquals("Event getCapacity not working. ", 100,
                (int) event1.getCapacity());
    }

    @Test
    public void testSetCapacity() throws ParseException {
        event1.setCapacity(50);
        Assert.assertEquals("Event getCapacity not working. ", 50,
                (int) event1.getCapacity());
        Assert.assertNotSame("Event getCapacity not working. ", 100,
                event1.getCapacity());
    }

    // Não sei se vai funcionar por causa da conexao com o servidor
    // @Test
    // public void testSetParticipants() throws ParseException {
    // Assert.assertEquals("Event setParticipants not working. ", "",
    // event1.getParticipants());
    // }

    @Test
    public void testGetState() throws ParseException {
        Assert.assertEquals("Event getState not working. ", "Opened",
                event1.getState());
    }

    @Test
    public void testSetState() throws ParseException {
        event1.setState("Finished");
        Assert.assertEquals("Event setState not working. ", "Finished",
                event1.getState());
        Assert.assertNotSame("Event setState not working. ", "Opened",
                event1.getState());
    }

    @Test
    public void testGetFacebookID() throws ParseException {
        Assert.assertEquals("Event getFacebookID not working. ",
                user1.getFacebookId(), event1.getFacebookId());
    }

    @Test
    public void testSetFacebookID() throws ParseException {
        event1.setState("424242");
        Assert.assertEquals("Event setFacebookID not working. ", "424242",
                event1.getState());
        Assert.assertNotSame("Event setFacebookID not working. ", "0",
                event1.getFacebookId());
    }

    @Test
    public void testIsPublic() throws ParseException {
        Assert.assertEquals("Event isPublic not working. ", true,
                event1.isPublic());
    }

    @Test
    public void testSetPublic() throws ParseException {
        event1.setPublic(false);
        Assert.assertEquals("Event setPublic not working. ", false,
                event1.isPublic());
        Assert.assertNotSame("Event setPublic not working. ", true,
                event1.isPublic());
    }

    // @Test
    // public void testGetTags() throws ParseException {
    // Assert.assertEquals("Event getTag not working. ", "testtag1", event1
    // .getTags().get(0));
    // }

    // @Test
    // public void testAddTags() throws ParseException {
    // event1.addTags("@testtagx@testtagy@testtagz");
    // Assert.assertEquals("Event setPublic not working. ", "testtagx",
    // event1.getTags().get(0));
    // Assert.assertNotSame("Event setPublic not working. ", "testtag1",
    // event1.getTags().get(0));
    // }

    // @Test
    // public void testGetAndSetEventName(){
    // Assert.assertEquals("Event 1", event1.getName());
    // Assert.assertEquals("Event 2", event2.getName());
    // Assert.assertEquals("Event 3", event3.getName());
    // Assert.assertEquals("Event 4", event4.getName());
    // Assert.assertEquals("Event 5", event5.getName());
    //
    // event1.setName("New name event1");
    // event2.setName("New name event2");
    // event3.setName("New name event3");
    // event4.setName("New name event4");
    // event5.setName("New name event5");
    //
    // Assert.assertEquals("New name event1", event1.getName());
    // Assert.assertEquals("New name event2", event2.getName());
    // Assert.assertEquals("New name event3", event3.getName());
    // Assert.assertEquals("New name event4", event4.getName());
    // Assert.assertEquals("New name event5", event5.getName());
    //
    // }
    // @Test
    // public void testGetAndSetEventDescription(){
    // Assert.assertEquals("Event 1 Description", event1.getDescription());
    // Assert.assertEquals("Event 2 Description", event2.getDescription());
    // Assert.assertEquals("Event 3 Description", event3.getDescription());
    // Assert.assertEquals("Event 4 Description", event4.getDescription());
    // Assert.assertEquals("Event 5 Description", event5.getDescription());
    //
    // event1.setDescription("New event 1 Description");
    // event2.setDescription("New event 2 Description");
    // event3.setDescription("New event 3 Description");
    // event4.setDescription("New event 4 Description");
    // event5.setDescription("New event 5 Description");
    //
    // Assert.assertEquals("New event 1 Description", event1.getDescription());
    // Assert.assertEquals("New event 2 Description", event2.getDescription());
    // Assert.assertEquals("New event 3 Description", event3.getDescription());
    // Assert.assertEquals("New event 4 Description", event4.getDescription());
    // Assert.assertEquals("New event 5 Description", event5.getDescription());
    //
    // }
    //
    // @Test
    // public void testGetAndSetEventImagePath(){
    // Assert.assertEquals("Event 1 Image Path", event1.getPhoto());
    // Assert.assertEquals("Event 2 Image Path", event2.getPhoto());
    //
    // event1.setPhoto("Event 1 Image Path");
    // event2.setPhoto("Event 2 Image Path");
    //
    // Assert.assertEquals("Event 1 Image Path", event1.getDescription());
    // Assert.assertEquals("Event 2 Image Path", event2.getDescription());
    // }
    //
    // @Test
    // public void testGetAndSetEventInitialDate(){
    // Assert.assertEquals(1, event1.getInitialDate().getDay());
    // Assert.assertEquals(12, event1.getInitialDate().getMonth());
    // Assert.assertEquals(2014, event1.getInitialDate().getYear());
    //
    // event1.setInitialDate(new Date(2015, 02, 01) );
    //
    // Assert.assertEquals(1, event1.getInitialDate().getDay());
    // Assert.assertEquals(2, event1.getInitialDate().getMonth());
    // Assert.assertEquals(2015, event1.getInitialDate().getYear());
    // }
    //
    // @Test
    // public void testGetAndSetEventFinalDate(){
    // Assert.assertEquals(2, event1.getFinalDate().getDay());
    // Assert.assertEquals(12, event1.getFinalDate().getMonth());
    // Assert.assertEquals(2014, event1.getFinalDate().getYear());
    //
    // event1.setInitialDate(new Date(2015, 02, 02) );
    //
    // Assert.assertEquals(2, event1.getInitialDate().getDay());
    // Assert.assertEquals(2, event1.getInitialDate().getMonth());
    // Assert.assertEquals(2015, event1.getInitialDate().getYear());
    // }
    //
    // @Test
    // public void testGetAndSetEventPrice(){
    // Assert.assertEquals(200.00, event1.getPrice());
    //
    // event1.setPrice(500.00);
    //
    // Assert.assertEquals(500.00, event1.getPrice());
    // }
    //
    // @Test
    // public void testGetAndSetEventOutfit(){
    // Assert.assertEquals("", event1.getOutfit());
    //
    // event1.setOutfit("Casual");
    //
    // Assert.assertEquals("Casual", event1.getOutfit());
    // }
    //
    // @Test
    // public void testGetAndSetEventCapacity(){
    // Assert.assertEquals(Integer.valueOf(100), event1.getCapacity());
    //
    // event1.setCapacity(Integer.valueOf(999));
    //
    // Assert.assertEquals(Integer.valueOf(999), event1.getCapacity());
    // }
    //
    // @Test
    // public void testIsPublicMethod(){
    // Assert.assertTrue(event1.isPublic());
    // event1.setPublic(false);
    // Assert.assertFalse(event1.isPublic());
    // event1.setPublic(false);
    // Assert.assertFalse(event1.isPublic());
    // }
    //
    // @Test
    // public void testGetEventOwner(){
    // Assert.assertEquals("Anderson", event1.getFacebookId());
    // }
}
