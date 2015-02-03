package adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import br.com.les.where2go.R;
import entity.event.Event;
import entity.event.Invitation;

public class InviteAdapter extends BaseAdapter {

    /** The m list invites. */
    private final List<Invitation> mListInvites;

    /** The m inflater. */
    private final LayoutInflater mInflater;

    /** The posicao. */
    private int posicao;

    /** The mcontext. */
    Context mcontext;

    /** The parent view. */
    private final View parentView;

    /** The listview. */
    private ListView listview;

    /**
     * Instantiates a new event adapter.
     *
     * @param context
     *            the context
     * @param listEvents
     *            the list events
     * @param parentView
     *            the parent view
     */
    public InviteAdapter(final Context context,
            final List<Invitation> listInvites, final View parentView) {
        this.mListInvites = listInvites;
        this.mInflater = LayoutInflater.from(context);
        this.mcontext = context;
        this.parentView = parentView;
    }

    /**
     * Instantiates a new invite adapter.
     *
     * @param context
     *            the context
     * @param listInvites
     *            the list invites
     * @param parentView
     *            the parent view
     * @param filter
     *            the filter
     */
    public InviteAdapter(final Context context,
            final List<Invitation> listInvites, final View parentView,
            final String filter) {
        if (filter.equals("Todos")) {
            this.mListInvites = listInvites;
        } else {
            final List<Event> newListInvites = new ArrayList<Event>();
            for (int i = 0; i < listInvites.size(); i++) {
                final Invitation tempInvite = listInvites.get(i);
                // final ArrayList<String> tempEventTags = tempEvent.getTags();
                // if (tempEventTags.contains(filter)) {
                // newListInvites.add(tempEvent);
                // }
            }
            this.mListInvites = listInvites;
        }
        this.mInflater = LayoutInflater.from(context);
        this.mcontext = context;
        this.parentView = parentView;
    }

    /*
     * (non-Javadoc)
     *
     * @see android.widget.Adapter#getCount()
     */
    @Override
    public int getCount() {
        return mListInvites.size();
    }

    /*
     * (non-Javadoc)
     *
     * @see android.widget.Adapter#getItem(int)
     */
    @Override
    public Invitation getItem(final int position) {
        return mListInvites.get(position);
    }

    /**
     * Get id of item selected.
     *
     * @param index
     *            the index
     * @return the item id
     */
    @Override
    public long getItemId(final int index) {
        return index;
    }

    /*
     * (non-Javadoc)
     * 
     * @see android.widget.Adapter#getView(int, android.view.View,
     * android.view.ViewGroup)
     */
    @Override
    public View getView(final int position, View myView,
            final ViewGroup viewGroup) {
        myView = mInflater.inflate(R.layout.item_invitation_adapter, null);

        final Invitation invite = mListInvites.get(position);

        final TextView inviteName = (TextView) myView
                .findViewById(R.id.invitation_event_name);
        inviteName.setText(invite.getEvent().getName());

        final TextView inviteStatus = (TextView) myView
                .findViewById(R.id.invitation_status);
        inviteStatus.setText("A Definir");

        listview = (ListView) myView.findViewById(R.id.listInvites);

        return myView;
    }

}
