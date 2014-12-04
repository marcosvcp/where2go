/**
 * Copyright (C) 2014 Embedded Systems and Pervasive Computing Lab - UFCG
 * All rights reserved.
 */

package activity;

import adapter.EventAdapter;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import br.com.les.where2go.R;

import java.util.Calendar;

import persistence.DatabaseStorage;

public class EventsListFragment extends Fragment {

    private ListView listview;
    public static EventAdapter adapter;
    static DatabaseStorage bdHelper;
    public static Context context;
    private ActionBar actionBar;
    private View rootView;
    public static boolean shouldShown = false;
    public static String incomingFragment = "";
    private int num = -30;

    public EventsListFragment() {
    }

    /**
     * Instantiate components
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.activity_event_list, container, false);
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
        return rootView;
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
            case R.id.action_create_event:
                Intent intent = new Intent(context, CreateEventActivity.class);
                startActivity(intent);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }
}
