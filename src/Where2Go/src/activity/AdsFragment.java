
package activity;

import java.util.ArrayList;
import java.util.List;

import persistence.ParseUtil;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import entity.event.Event;
import adapter.AdsAdapter;
import adapter.EventAdapter;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;
import br.com.les.where2go.R;

/**
 * The Class AdsFragment.
 */
public class AdsFragment extends Fragment {
	
    /** The listview. */
    private ListView listview;

    /** The adapter. */
    private static AdsAdapter adapter;

    /** The context. */
    private static Context context;
    
    /** The root view. */
    private View rootView;
    
    /** The m search event spinner. */
    private Spinner mSearchEventSpinner;
    
    /**
     * Instantiates a new ads fragment.
     */
    public AdsFragment() {
    }

    /*
     * (non-Javadoc)
     * 
     * @see android.app.Fragment#onCreateView(android.view.LayoutInflater,
     * android.view.ViewGroup, android.os.Bundle)
     */
    @Override
    public final View onCreateView(final LayoutInflater inflater, final ViewGroup container,
            final Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.activity_ads,
                container, false);

        mSearchEventSpinner = (Spinner) rootView
                .findViewById(R.id.searchEventList);
        listview = (ListView) rootView.findViewById(R.id.listViewEvents);
        setContext(rootView.getContext());
        listview.setAdapter(adapter);
        
        searchSpinnerSetUp();

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

                        query.whereEqualTo("isSponsored", true);

                        query.findInBackground(new FindCallback<Event>() {
                            @Override
                            public void done(final List<Event> objects,
                                    final ParseException e) {
                                // Caso não tenha lançado exceção
                                if (e == null) {
                                    adapter = new AdsAdapter(context,
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
		AdsFragment.context = context;
	}
}
