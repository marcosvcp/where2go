
package activity;

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

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

import adapter.EventAdapter;
import br.com.les.where2go.R;
import entity.event.Event;
import persistence.ParseUtil;

/**
 * The Class EventsListFragment.
 */
public class EventsListFragment extends Fragment {

    /** The listview. */
    private ListView listview;

    /** The adapter. */
    private static EventAdapter adapter;

    /** The context. */
    private static Context context;

    /** The action bar. */
    private ActionBar actionBar;

    /** The root view. */
    private View rootView;

    /** The m search event spinner. */
    private Spinner mSearchEventSpinner;

    /** The m btn add event. */
    private ImageButton mBtnAddEvent;

    /** The should shown. */
    private static boolean shouldShown = false;

    /** The incoming fragment. */
    private static String incomingFragment = "";

    /**
     * Instantiates a new events list fragment.
     */
    public EventsListFragment() {
    }

    /**
     * Instantiate components.
     * 
     * @param inflater the inflater
     * @param container the container
     * @param savedInstanceState the saved instance state
     * @return the view
     */
    @Override
    public final View onCreateView(final LayoutInflater inflater, final ViewGroup container,
            final Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.activity_event_list, container,
                false);
        mBtnAddEvent = (ImageButton) rootView.findViewById(R.id.bt_add_event);
        mSearchEventSpinner = (Spinner) rootView
                .findViewById(R.id.searchEventList);
        listview = (ListView) rootView.findViewById(R.id.listViewEvents);
        setContext(rootView.getContext());
        listview.setAdapter(adapter);

        listview.setClickable(true);

        actionBar = getActivity().getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        setHasOptionsMenu(true);

        searchSpinnerSetUp();

        mBtnAddEvent.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(final View v) {
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
            public void done(final List<ParseObject> objects, final ParseException e) {
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
                    public void onItemSelected(final AdapterView<?> parent,
                            final View view, final int position, final long id) {
                        final String filter = parent
                                .getItemAtPosition(position).toString();
                        // Atualiza o adapter passando o filtro como parametro
                        // BUSCA NO SERVIDOR TODOS OS EVENTOS E SETA NO ADAPTER
                        ParseQuery<Event> query = ParseUtil.getQueryEvent();

                        query.whereEqualTo("isPublic", true);

                        query.findInBackground(new FindCallback<Event>() {
                            @Override
                            public void done(final List<Event> objects,
                                    final ParseException e) {
                                // Caso não tenha lançado exceção
                                if (e == null) {
                                    adapter = new EventAdapter(context,
                                            objects, rootView, filter, getActivity());
                                    listview.setAdapter(adapter);
                                }
                            }
                        });
                    }

                    @Override
                    public void onNothingSelected(final AdapterView<?> parent) {
                        // TODO Auto-generated method stub
                    }
                });
    }

    /**
     * Inflate the menu items for use in the action bar.
     * 
     * @param menu the menu
     * @param inflater the inflater
     */
    @Override
    public final void onCreateOptionsMenu(final Menu menu, final MenuInflater inflater) {
        inflater.inflate(R.menu.event_list, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    /**
     * Options for action bar, allowing create a new income.
     * 
     * @param item the item
     * @return true, if successful
     */
    @Override
    public final boolean onOptionsItemSelected(final MenuItem item) {
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
    public final void addEvent() {
        Intent intent = new Intent(context, CreateEventActivity.class);
        startActivity(intent);
    }

    /**
     * Gets the adapter.
     * 
     * @return the adapter
     */
    public static EventAdapter getAdapter() {
        return adapter;
    }

    /**
     * Sets the adapter.
     * 
     * @param newAdapter the new adapter
     */
    public static void setAdapter(final EventAdapter newAdapter) {
        EventsListFragment.adapter = newAdapter;
    }

    /**
     * Get the context.
     * @return context
     */
	public static Context getContext() {
		return context;
	}

	/**
	 * Sets the context.
	 * @param context the new context
	 */
	public static void setContext(Context context) {
		EventsListFragment.context = context;
	}
}
