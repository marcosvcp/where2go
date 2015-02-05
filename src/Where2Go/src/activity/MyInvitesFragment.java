package activity;

import java.util.ArrayList;
import java.util.List;

import persistence.ParseUtil;
import utils.Authenticator;
import adapter.InviteAdapter;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import br.com.les.where2go.R;

import com.parse.FindCallback;
import com.parse.ParseException;

import entity.event.Event;
import entity.event.Invitation;
import entity.user.User;

/**
 * The Class AdsFragment.
 */
public class MyInvitesFragment extends Fragment {

    /** The listview. */
    private static ListView listview;

    /** The adapter. */
    public static InviteAdapter adapter;

    /** The context. */
    public static Context context;

    /** The action bar. */
    private ActionBar actionBar;

    /** The root view. */
    private View rootView;

    private User guest, host;
    private Event event;

    /** The m search event spinner. */
    private Spinner mSearchEventSpinner;

    /**
     * Instantiates a new ads fragment.
     */
    public MyInvitesFragment() {
    }

    /*
     * (non-Javadoc)
     * 
     * @see android.app.Fragment#onCreateView(android.view.LayoutInflater,
     * android.view.ViewGroup, android.os.Bundle)
     */
    @Override
    public View onCreateView(final LayoutInflater inflater,
            final ViewGroup container, final Bundle savedInstanceState) {
        final View rootView = inflater.inflate(
                R.layout.activity_my_invite_fragment, container, false);
        mSearchEventSpinner = (Spinner) rootView
                .findViewById(R.id.searchInvitationList);
        listview = (ListView) rootView.findViewById(R.id.listInvites);
        context = rootView.getContext();
        loadInvites("Pending");
        listview.setClickable(true);

        searchSpinnerSetUp();

        // ParseQuery<Event> query = ParseUtil.getQueryEvent();
        //
        // ParseUtil.findByFacebookId(Authenticator.getInstance().getLoggedUser().getFacebookId(),
        // new FindCallback<User>() {
        //
        // @Override
        // public void done(List<User> objects, ParseException e) {
        // guest = objects.get(0);
        // }
        // });
        //
        // ParseUtil.findByFacebookId("826299547415878", new
        // FindCallback<User>() {
        //
        // @Override
        // public void done(List<User> objects, ParseException e) {
        // host = objects.get(0);
        // }
        // });
        //
        // ParseUtil.findEventById("dqUnb8PbiM", new GetCallback<Event>() {
        //
        // @Override
        // public void done(Event object, ParseException e) {
        // event = object;
        // Log.v("guest", guest.toString());
        // Log.v("host", host.toString());
        // Log.v("event", event.toString());
        // Invitation invite = new Invitation(guest, host, event);
        // ParseUtil.saveInvitation(invite);
        // }
        // });

        return rootView;
    }

    private void searchSpinnerSetUp() {
        // mListInvites = new ArrayList<Invitation>();

        // ParseUtil.findInvitationByUserGuest(Authenticator.getInstance()
        // .getLoggedUser(), new FindCallback<Invitation>() {
        //
        // @Override
        // public void done(final List<Invitation> objects,
        // final ParseException e) {
        // mListInvites = objects;
        // }
        // });

        final List<String> status = new ArrayList<String>();
        status.add("Pending");
        status.add("Accepted");
        status.add("Denied");
        final ArrayAdapter<String> spinnerDataAdapter = new ArrayAdapter<String>(
                context, android.R.layout.simple_spinner_item, status);
        spinnerDataAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSearchEventSpinner.setAdapter(spinnerDataAdapter);
        mSearchEventSpinner
                .setOnItemSelectedListener(new OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(final AdapterView<?> parent,
                            final View view, final int position, final long id) {
                        loadInvites(parent.getItemAtPosition(position)
                                .toString());
                    }

                    @Override
                    public void onNothingSelected(final AdapterView<?> parent) {
                        // TODO Auto-generated method stub

                    }
                });
    }

    public void loadInvites(final String filter) {
        new ArrayList<Invitation>();

        ParseUtil.findInvitationByUserGuest(Authenticator.getInstance()
                .getLoggedUser(), new FindCallback<Invitation>() {

            @Override
            public void done(final List<Invitation> objects,
                    final ParseException e) {
                adapter = new InviteAdapter(context, objects, rootView, filter);
                listview.setAdapter(adapter);
            }
        });
        // adapter = new InviteAdapter(context, mListInvites, rootView);
    }

    public void loadInvites() {
        new ArrayList<Invitation>();

        ParseUtil.findInvitationByUserGuest(Authenticator.getInstance()
                .getLoggedUser(), new FindCallback<Invitation>() {

            @Override
            public void done(final List<Invitation> objects,
                    final ParseException e) {
                adapter = new InviteAdapter(context, objects, rootView);
                listview.setAdapter(adapter);
            }
        });
        // adapter = new InviteAdapter(context, mListInvites, rootView);
    }

}
