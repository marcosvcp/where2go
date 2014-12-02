package activity;


import java.util.ArrayList;
import java.util.List;

import persistence.DatabaseStorage;

import entity.event.Event;

import br.com.les.where2go.R;
import br.com.les.where2go.R.id;
import br.com.les.where2go.R.layout;
import br.com.les.where2go.R.menu;
import adapter.EventAdapter;
import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

public class EventListActivity extends Activity {
	public static EventAdapter adapter;
    private static ListView listview;
    private static DatabaseStorage bdHelper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_event_list);
        listview = (ListView) findViewById(R.id.listViewEvents);
        
        bdHelper = new DatabaseStorage(getApplicationContext());
        List<Event> events = new ArrayList<Event>();
        adapter = new EventAdapter(getApplicationContext(), events, bdHelper, getWindow().getDecorView().findViewById(android.R.id.content));
        listview.setAdapter(adapter);

        ActionBar actionBar = getActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
        actionBar.setDisplayHomeAsUpEnabled(true);

        //adapter = new EventAdapter(getApplicationContext(), listEvents, bdHelper, parentView);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.event_list, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
