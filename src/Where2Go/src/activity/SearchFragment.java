package activity;

import java.util.ArrayList;
import java.util.List;

import persistence.ParseUtil;
import utils.Authenticator;
import adapter.EventAdapter;
import adapter.EventSearchedAdapter;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import br.com.les.where2go.R;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import entity.event.Event;

/**
 * The Class EventsListFragment.
 */
public class SearchFragment extends Fragment {

    /** The listview. */
    private ListView listview;

    /** The adapter. */
    private static EventSearchedAdapter adapter;

    /** The context. */
    private static Context context;

    /** The action bar. */
    private ActionBar actionBar;

    /** The root view. */
    private View rootView;

    /** The m search event spinner. */
    private Spinner mSearchEventSpinner;
    
    private EditText stringToSearch;
    private Button btnSearch;
    private String filter;


    /**
     * Instantiates a new events list fragment.
     */
    public SearchFragment() {
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
    public final View onCreateView(final LayoutInflater inflater,
            final ViewGroup container, final Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.activity_search_fragment, container,
                false);
        mSearchEventSpinner = (Spinner) rootView
                .findViewById(R.id.searchTagsList);
        listview = (ListView) rootView.findViewById(R.id.listViewEventsSearched);
        stringToSearch = (EditText) rootView.findViewById(R.id.editSearchInput);
        btnSearch = (Button) rootView.findViewById(R.id.btnSearch);
        setContext(rootView.getContext());
        listview.setAdapter(adapter);

        listview.setClickable(true);

        actionBar = getActivity().getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        setHasOptionsMenu(true);

        searchSpinnerSetUp();
        btnSearch.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				updateListView();
				
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
        tags.add("Search");
        
        
        // Get all tags from parser
        ParseUtil.findAllSearchTags(new FindCallback<ParseObject>() {
            @Override
            public void done(final List<ParseObject> objects,
                    final ParseException e) {
                if (e == null) {
                    for (final ParseObject po : objects) {
                        tags.add(po.getString("name"));
                    }
                }
            }
        });

        final ArrayAdapter<String> spinnerDataAdapter = new ArrayAdapter<String>(
                context, android.R.layout.simple_spinner_item, tags);

        spinnerDataAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSearchEventSpinner.setAdapter(spinnerDataAdapter);
        mSearchEventSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(final AdapterView<?> parent,final View view, final int position, final long id) {
                    	setFilterSelected(parent, view, position, id);
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
     * @param menu
     *            the menu
     * @param inflater
     *            the inflater
     */
    @Override
    public final void onCreateOptionsMenu(final Menu menu,
            final MenuInflater inflater) {
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
    public final boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                final Intent intentBack = new Intent(context, MainScreen.class);
                intentBack.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intentBack);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Get the context.
     *
     * @return context
     */
    public static Context getContext() {
        return context;
    }

    /**
     * Set the context.
     *
     * @param context
     *            the new context
     */
    public static void setContext(final Context context) {
        SearchFragment.context = context;
    }
    
    private void updateListView(){
        // Atualiza o adapter passando o filtro como parametro
        // BUSCA NO SERVIDOR TODOS OS EVENTOS E SETA NO ADAPTER
        ParseQuery<Event> query = ParseUtil
                .getQueryEvent();
        query.whereContains("name", stringToSearch.getText().toString());
        query.findInBackground(new FindCallback<Event>() {
            @Override
            public void done(final List<Event> objects,
                    final ParseException e) {
                // Caso não tenha lançado exceção
                if (e == null) {
                    adapter = new EventSearchedAdapter(context,
                            objects, rootView, filter,
                            getActivity());
                    listview.setAdapter(adapter);
                }
            }
        });	
    }
    
    private void setFilterSelected(AdapterView<?> parent, View view, int position, long id){
    	filter = parent.getItemAtPosition(position).toString();
    }
    
    public class LongOperation extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            ParseQuery<Event> query = ParseUtil
                    .getQueryEvent();
            Log.d("Search", "1");
            query.whereContains("name", "Scrum");
            Log.d("Search", "2");
            query.findInBackground(new FindCallback<Event>() {
                @Override
                public void done(final List<Event> objects,
                        final ParseException e) {
                    // Caso não tenha lançado exceção
                    if (e == null) {
                        adapter = new EventSearchedAdapter(context,
                                objects, rootView, filter,
                                getActivity());
                        listview.setAdapter(adapter);
                    }
                }
            });	
            Log.d("Search", "3");
            return "Executed";
        }

        @Override
        protected void onPostExecute(String result) {}

        @Override
        protected void onPreExecute() {}

        @Override
        protected void onProgressUpdate(Void... values) {}
    }

}
