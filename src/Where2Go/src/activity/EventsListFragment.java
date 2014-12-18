package activity;

import java.util.ArrayList;
import java.util.List;

import persistence.ParseUtil;
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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import br.com.les.where2go.R;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;

import entity.event.Event;

/**
 * The Class EventsListFragment.
 */
public class EventsListFragment extends Fragment {

    /** The listview. */
    private ListView listview;

    /** The adapter. */
    public static EventAdapter adapter;

    /** The context. */
    public static Context context;

    /** The action bar. */
    private ActionBar actionBar;

    /** The root view. */
    private View rootView;

    /** The m search event spinner. */
    private Spinner mSearchEventSpinner;

    /** The m btn add event. */
    private ImageButton mBtnAddEvent;

    /** The should shown. */
    public static boolean shouldShown = false;

    /** The incoming fragment. */
    public static String incomingFragment = "";

    /**
     * Instantiates a new events list fragment.
     */
    public EventsListFragment() {
    }

    /**
     * Instantiate components.
     *
     * @param inflater
     *            the inflater
     * @param container
     *            the container
     * @param savedInstanceState
     *            the saved instance state
     * @return the view
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.activity_event_list, container,
                false);
        mBtnAddEvent = (ImageButton) rootView.findViewById(R.id.bt_add_event);
        mSearchEventSpinner = (Spinner) rootView
                .findViewById(R.id.searchEventList);
        listview = (ListView) rootView.findViewById(R.id.listViewEvents);
        context = rootView.getContext();
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
        final List<String> tags = new ArrayList<String>();
        tags.add("Todos");

        // Get all tags from parser
        ParseUtil.findAllTags(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    for (ParseObject po : objects) {
                        tags.add(po.getString("nome"));
                    }
                }
            }
        });

        ArrayAdapter<String> spinnerDataAdapter = new ArrayAdapter<String>(
                context, android.R.layout.simple_spinner_item, tags);

        spinnerDataAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSearchEventSpinner.setAdapter(spinnerDataAdapter);
        mSearchEventSpinner
                .setOnItemSelectedListener(new OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent,
                            View view, int position, long id) {
                        final String filter = parent
                                .getItemAtPosition(position).toString();
                        // Atualiza o adapter passando o filtro como parametro
                        // BUSCA NO SERVIDOR TODOS OS EVENTOS E SETA NO ADAPTER
                        ParseUtil.findAllEvents(new FindCallback<Event>() {
                            @Override
                            public void done(List<Event> objects,
                                    ParseException e) {
                                // Caso não tenha lançado exceção
                                if (e == null) {
                                    adapter = new EventAdapter(context,
                                            objects, rootView, filter);
                                    listview.setAdapter(adapter);
                                }
                            }
                        });
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        // TODO Auto-generated method stub
                    }
                });
    }

    /**
     * Inflate the menu items for use in the action bar.
     *
     * @param menu
     *            the menu
     * @param inflater
     *            the inflater
     */
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.event_list, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    /**
     * Options for action bar, allowing create a new income.
     *
     * @param item
     *            the item
     * @return true, if successful
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
