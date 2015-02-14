
package adapter;

import activity.MyInvitesFragment;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;

import br.com.les.where2go.R;
import entity.event.Invitation;
import persistence.ParseUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * The Class InviteAdapter.
 */
public class InviteAdapter extends BaseAdapter {

    /** The m list invites. */
    private final List<Invitation> mListInvites;

    /** The m inflater. */
    private final LayoutInflater mInflater;

    /** The mcontext. */
    private final Context mcontext;

    /** The parent view. */
    private final View parentView;

    /** The listview. */
    private ListView listview;

    /**
     * Instantiates a new event adapter.
     * 
     * @param context the context
     * @param listInvites the list invites
     * @param view the parent view
     */
    public InviteAdapter(final Context context,
            final List<Invitation> listInvites, final View view) {
        mListInvites = listInvites;
        mInflater = LayoutInflater.from(context);
        mcontext = context;
        parentView = view;
    }

    /**
     * Instantiates a new invite adapter.
     * 
     * @param context the context
     * @param listInvites the list invites
     * @param view the parent view
     * @param filter the filter
     */
    public InviteAdapter(final Context context,
            final List<Invitation> listInvites, final View view,
            final String filter) {

        final List<Invitation> newListInvites = new ArrayList<Invitation>();
        for (int i = 0; i < listInvites.size(); i++) {
            final Invitation tempInvite = listInvites.get(i);
            if (tempInvite.getState().equals(filter)) {
                newListInvites.add(tempInvite);
            }
        }
        mListInvites = newListInvites;
        mInflater = LayoutInflater.from(context);
        mcontext = context;
        parentView = view;
    }

    /*
     * (non-Javadoc)
     * 
     * @see android.widget.Adapter#getCount()
     */
    @Override
    public final int getCount() {
        return mListInvites.size();
    }

    /*
     * (non-Javadoc)
     * 
     * @see android.widget.Adapter#getItem(int)
     */
    @Override
    public final Invitation getItem(final int position) {
        return mListInvites.get(position);
    }

    /**
     * Get id of item selected.
     * 
     * @param index the index
     * @return the item id
     */
    @Override
    public final long getItemId(final int index) {
        return index;
    }

    /*
     * (non-Javadoc)
     * 
     * @see android.widget.Adapter#getView(int, android.view.View,
     * android.view.ViewGroup)
     */
    @Override
    public final View getView(final int position, View myView,
            final ViewGroup viewGroup) {
        myView = mInflater.inflate(R.layout.item_invitation_adapter, null);

        final Invitation invite = mListInvites.get(position);

        final TextView inviteName = (TextView) myView
                .findViewById(R.id.invitation_event_name);
        inviteName.setText(invite.getEvent().getName());

        final TextView inviteStatus = (TextView) myView
                .findViewById(R.id.invitation_status);
        inviteStatus.setText(invite.getState());

        listview = (ListView) myView.findViewById(R.id.listInvites);

        final ImageButton btOptions = (ImageButton) myView
                .findViewById(R.id.invitation_bt_options);
        btOptions.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(final View v) {
                showPopupMenu(v, invite);
            }
        });

        return myView;
    }

    /**
     * Show popup menu.
     * 
     * @param v the v
     * @param invite the invite
     */
    private void showPopupMenu(final View v, final Invitation invite) {
        final PopupMenu popupMenu = new PopupMenu(mcontext, v);
        final Intent intent = new Intent(mcontext, MyInvitesFragment.class);
        popupMenu.getMenuInflater().inflate(R.menu.invitation_options,
                popupMenu.getMenu());
        popupMenu
                .setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(final MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.invitation_accept:
                                if (!invite.getState().equals("Accepted")) {
                                    invite.setState("Accepted");
                                    ParseUtil.saveInvitation(invite);
                                    mListInvites.remove(invite);
                                    notifyDataSetChanged();
                                }
                                return true;
                            case R.id.invitation_refuse:
                                if (!invite.getState().equals("Denied")) {
                                    invite.setState("Denied");
                                    ParseUtil.saveInvitation(invite);
                                    mListInvites.remove(invite);
                                    notifyDataSetChanged();
                                }
                                return true;
                            default:
                                break;
                        }
                        return true;
                    }
                });
        popupMenu.show();
    }
}
