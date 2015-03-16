package activity;

import java.util.ArrayList;
import java.util.List;

import persistence.ParseUtil;
import utils.Authenticator;
import adapter.InviteAdapter;
import android.app.Fragment;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
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

import entity.event.Invitation;

/**
 * The Class AdsFragment.
 */
public class MyInvitesFragment extends Fragment {

    /** The listview. */
    private static ListView listview;

    /** The adapter. */
    private static InviteAdapter adapter;

    /** The context. */
    private static Context context;

    /** The root view. */
    private View rootView;

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
    public final View onCreateView(final LayoutInflater inflater,
            final ViewGroup container, final Bundle savedInstanceState) {
        final View rootViewLayout = inflater.inflate(
                R.layout.activity_my_invite_fragment, container, false);
        mSearchEventSpinner = (Spinner) rootViewLayout
                .findViewById(R.id.searchInvitationList);
        setListview((ListView) rootViewLayout.findViewById(R.id.listInvites));
        setContext(rootViewLayout.getContext());
        loadInvites("Pending");
        listview.setClickable(true);

        searchSpinnerSetUp();
        return rootViewLayout;
    }

    /**
     * Search spinner set up.
     */
    private void searchSpinnerSetUp() {
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

    /**
     * Load invites.
     *
     * @param filter
     *            the filter
     */
    public final void loadInvites(final String filter) {
        ParseUtil.findInvitationByUserGuest(Authenticator.getInstance()
                .getLoggedUser(), new FindCallback<Invitation>() {

            @Override
            public void done(final List<Invitation> objects,
                    final ParseException e) {
                if (objects != null) {
                    adapter = new InviteAdapter(context, objects, rootView,
                            filter);
                    listview.setAdapter(adapter);
                }
            }
        });
    }

    /**
     * Load invites.
     *
     * @param rootView
     *            the root view
     */
    public static void loadInvites(final View rootView) {
        ParseUtil.findInvitationByUserGuest(Authenticator.getInstance()
                .getLoggedUser(), new FindCallback<Invitation>() {

            @Override
            public void done(final List<Invitation> objects,
                    final ParseException e) {
                adapter = new InviteAdapter(context, objects, rootView,
                        "Pending");
                listview.setAdapter(adapter);
                createNotification();
            }
        });
    }

    /**
     * Load invites.
     */
    public final void loadInvites() {
        ParseUtil.findInvitationByUserGuest(Authenticator.getInstance()
                .getLoggedUser(), new FindCallback<Invitation>() {

            @Override
            public void done(final List<Invitation> objects,
                    final ParseException e) {
                adapter = new InviteAdapter(context, objects, rootView);
                listview.setAdapter(adapter);
            }
        });
    }

    /**
     * Get the listView.
     *
     * @return the ListView
     */
    public static ListView getListview() {
        return listview;
    }

    /**
     * Set the listView.
     *
     * @param listview
     *            the new listview
     */
    public static void setListview(final ListView listview) {
        MyInvitesFragment.listview = listview;
    }

    /**
     * Get the context.
     *
     * @return the context
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
        MyInvitesFragment.context = context;
    }

    public static void createNotification() {
        // Prepare intent which is triggered if the
        // notification is selected
        final Intent intent = new Intent(context,
                NotificationReceiverActivity.class);
        final PendingIntent pIntent = PendingIntent.getActivity(context, 0,
                intent, 0);

        // Build notification
        // Actions are just fake
        final Notification noti = new Notification.Builder(context)
                .setContentTitle("New invite for " + "teste")
                .setContentText("Where2go")
                .setSmallIcon(R.drawable.ic_launcher).setContentIntent(pIntent)
                .addAction(R.drawable.ic_action_accept, "Accept", pIntent)
                .addAction(R.drawable.ic_action_cancel, "Ignore", pIntent)
                .addAction(R.drawable.ic_action_discard, "Cancel", pIntent)
                .build();
        final String ns = Context.NOTIFICATION_SERVICE;
        final NotificationManager mNotificationManager = (NotificationManager) context
                .getSystemService(ns);
        // hide the notification after its selected
        noti.flags |= Notification.FLAG_AUTO_CANCEL;

        mNotificationManager.notify(0, noti);

    }
}