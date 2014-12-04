package test;


import persistence.DatabaseStorage;
import activity.EventListActivity;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;
import android.webkit.WebView.FindListener;
import android.widget.EditText;
import android.widget.ListView;
import br.com.les.where2go.R;

import com.robotium.solo.Solo;

import entity.event.Event;

public class EventListTest extends
        ActivityInstrumentationTestCase2<activity.EventListActivity> {

    private Solo solo;
    private static DatabaseStorage dBHelper;
    private Event testEvent1;
    
    @SuppressWarnings("deprecation")
    public EventListTest() {
        super("activity", EventListActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        testEvent1 = new Event(1, "Event 1", "Opened", "Event 1 Description", "Event Image Path", "Event 1 Info", "01/12/2014", "02/12/2014", "200,00", "", 100, "01/12/2014");
        dBHelper = new DatabaseStorage(getActivity().getApplicationContext());
        solo = new Solo(getInstrumentation(), getActivity());
        dBHelper.recreate();
        dBHelper.create();
        dBHelper.add(testEvent1);
        
    }

    @Override
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
    }

    public void test1Layout() throws Exception {
    	setUp();
        final int listViewEvents;
        listViewEvents = br.com.les.where2go.R.id.listViewEvents;
        assertNotNull(getActivity().findViewById(listViewEvents));
        tearDown();
    }
    
    public void test2ListView() throws Exception {
    	setUp();
    	final int listViewEvents;
        listViewEvents = br.com.les.where2go.R.id.listViewEvents;
    	//Get the List
    	ListView eventListView = (ListView) getActivity().findViewById(listViewEvents);
    	// Verify if the size its 1 and not 2
    	assertEquals("Incorrect number of events!", 1, eventListView.getCount());
    	assertFalse("Incorrect number of events!", eventListView.getCount() == 2);
    	// Get Event
    	Event event = (Event) eventListView.getItemAtPosition(0);
    	assertTrue("Event on listView not working",event.getId() == 1);
    	assertEquals("Event on listView not working","Event 1" , event.getName());
    	assertEquals("Event on listView not working", "Opened" ,event.getStatus());
    	assertEquals("Event on listView not working", "Event 1 Description", event.getDescription());
    	assertEquals("Event on listView not working", "Event Image Path", event.getPhoto());
    	assertEquals("Event on listView not working", "Event 1 Info", event.getInfo());
    	assertEquals("Event on listView not working", "01/12/2014", event.getInitialDate());
    	assertEquals("Event on listView not working", "02/12/2014", event.getFinalDate());
    	assertEquals("Event on listView not working", "200,00", event.getPrice());
    	assertEquals("Event on listView not working", "", event.getOutfit());
    	assertTrue("Event on listView not working",event.getCapacity() == 100);
    	assertEquals("Event on listView not working","01/12/2014" ,event.getTimestamp() );
    	tearDown();
    }
    
    public void test4CancelEvent() throws Exception {
    	setUp();
    	final int listViewEvents;
    	listViewEvents = br.com.les.where2go.R.id.listViewEvents;
    	ListView eventListView = (ListView) getActivity().findViewById(listViewEvents);
    	Event event = (Event) eventListView.getItemAtPosition(0);
    	assertEquals("Event on listView not working", "Opened" ,event.getStatus());
        solo.clickOnView(getActivity().findViewById(R.id.bt_options));
        solo.sleep(1000);
        solo.clickOnText("cancel");
        solo.sleep(1000);
        solo.clickOnText("YES");
        solo.sleep(1000);
        event = (Event) eventListView.getItemAtPosition(0);
    	assertEquals("Event on listView not working", "Event Canceled" ,event.getStatus());
    	tearDown();
    }
    
    public void test3EditEvent() throws Exception {
    	setUp();
    	final int listViewEvents;
    	listViewEvents = br.com.les.where2go.R.id.listViewEvents;
    	ListView eventListView = (ListView) getActivity().findViewById(listViewEvents);
    	Event event = (Event) eventListView.getItemAtPosition(0);
    	assertEquals("Event on listView not working","Event 1" , event.getName());
    	assertEquals("Event on listView not working", "Event 1 Description", event.getDescription());
    	assertEquals("Event on listView not working", "Event 1 Info", event.getInfo());

    	solo.clickOnView(getActivity().findViewById(R.id.bt_options));
        solo.sleep(1000);
        solo.clickOnText("edit");
        solo.sleep(1000);
        solo.clickOnText("YES");
        solo.sleep(1000);
        
        solo.clearEditText((EditText)solo.getView(R.id.et_event_name));
        solo.enterText((EditText) solo.getView(R.id.et_event_name),"Edited Name");
        solo.sleep(1000);
        solo.clearEditText((EditText)solo.getView(R.id.et_event_description));
        solo.enterText((EditText) solo.getView(R.id.et_event_description),"Edited Description");
        solo.sleep(1000);
        solo.clearEditText((EditText)solo.getView(R.id.et_event_info));
        solo.enterText((EditText) solo.getView(R.id.et_event_info),"Edited info");
        solo.sleep(1000);
        solo.clickOnView(solo.getView(R.id.bt_edit_event));
        solo.sleep(1000);
        event = (Event) eventListView.getItemAtPosition(0);
        assertEquals("Event on listView not working","Edited Name" , event.getName());
    	assertEquals("Event on listView not working", "Edited Description", event.getDescription());
    	assertEquals("Event on listView not working", "Edited info", event.getInfo());
    	tearDown();
    }
}
