/**
 * Copyright (C) 2014 Embedded Systems and Pervasive Computing Lab - UFCG
 * All rights reserved.
 */

package activity;

import java.util.ArrayList;
import java.util.List;

import adapter.EventAdapter;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import br.com.les.where2go.R;

import persistence.DatabaseStorage;

public class EventsListFragment extends Fragment {

    private ListView listview;
    public static EventAdapter adapter;
    static DatabaseStorage bdHelper;
    public static Context context;
    private ActionBar actionBar;
    private View rootView;
    private Spinner mSearchEventSpinner;
    private ImageButton mBtnAddEvent;
    public static boolean shouldShown = false;
    public static String incomingFragment = "";
    
    public EventsListFragment() {
    }

    /**
     * Instantiate components
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.activity_event_list, container, false);
        mBtnAddEvent = (ImageButton) rootView.findViewById(R.id.bt_add_event);
        mSearchEventSpinner = (Spinner) rootView.findViewById(R.id.searchEventList);
        listview = (ListView) rootView.findViewById(R.id.listViewEvents);
        context = rootView.getContext();
    	bdHelper = new DatabaseStorage(context);
        adapter = new EventAdapter(context, bdHelper.getEvents(), rootView);
        listview.setAdapter(adapter);
        
        listview.setClickable(true);

        actionBar = getActivity().getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        setHasOptionsMenu(true);
        
        searchSpinnerSetUp();
    	
        mBtnAddEvent.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				addEvent();			
			}
		});
        
        return rootView;
    }
    
    /**
     * SetUp the search Spinner elements and listener.
     */
	private void searchSpinnerSetUp() {
		// Essa lista deve ser a lista de tags do BD
        List<String> list = new ArrayList<String>();
        list.add("Todos");
    	list.add("Festa");
    	list.add("Bagacera");
    	list.add("Churrasco");
    	
    	ArrayAdapter<String> spinnerDataAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, list);
    	
    	spinnerDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    	
    	mSearchEventSpinner.setAdapter(spinnerDataAdapter);
    	
    	mSearchEventSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				String filter = parent.getItemAtPosition(position).toString();
				//Atualiza o adapter passando o filtro como parametro
				adapter = new EventAdapter(context, bdHelper.getEvents(), rootView, filter);
				listview.setAdapter(adapter);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
			}
		});		
	}

	/**
     * Inflate the menu items for use in the action bar
     */
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.event_list, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    /**
     * Options for action bar, allowing create a new income.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intentBack = new Intent(context, MainScreen.class);
                intentBack.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intentBack);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    
    /**
     * Actions of the addEvent button.
     */
    public void addEvent() {
    	Intent intent = new Intent(context, CreateEventActivity.class);
        startActivity(intent);
    }
}
