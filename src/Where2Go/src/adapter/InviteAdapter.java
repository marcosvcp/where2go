package adapter;

import java.util.ArrayList;
import java.util.List;

import persistence.ParseUtil;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.PopupMenu.OnMenuItemClickListener;
import android.widget.TextView;
import br.com.les.where2go.R;
import entity.event.Invitation;

/**
 * The Class InviteAdapter.
 */
public class InviteAdapter extends BaseAdapter {

    public static final String DENIED = "Denied";
    public static final String ACCEPTED = "Accepted";
    /** The m list invites. */
    private final List<Invitation> mListInvites;

    /** The m inflater. */
    private final LayoutInflater mInflater;

    /** The mcontext. */
    private final Context mcontext;

    /**
     * Instantiates a new event adapter.
     *
     * @param context
     *            the context
     * @param listInvites
     *            the list invites
     * @param view
     *            the parent view
     */
    public InviteAdapter(final Context context,
            final List<Invitation> listInvites, final View view) {
        mListInvites = listInvites;
        mInflater = LayoutInflater.from(context);
        mcontext = context;
    }

    /**
     * Instantiates a new invite adapter.
     *
     * @param context
     *            the context
     * @param listInvites
     *            the list invites
     * @param view
     *            the parent view
     * @param filter
     *            the filter
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
     * @param index
     *            the index
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
    public final View getView(final int position, final View myView,
            final ViewGroup viewGroup) {
        final View view = mInflater.inflate(R.layout.item_invitation_adapter,
                null);

        final Invitation invite = mListInvites.get(position);

        final TextView inviteName = (TextView) view
                .findViewById(R.id.invitation_event_name);
        inviteName.setText(invite.getEvent().getName());

        final TextView inviteStatus = (TextView) view
                .findViewById(R.id.invitation_status);
        inviteStatus.setText(invite.getState());

        final ImageButton btOptions = (ImageButton) view
                .findViewById(R.id.invitation_bt_options);
        btOptions.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(final View v) {
                showPopupMenu(v, invite);
            }
        });

        return view;
    }

    /**
     * Show popup menu.
     *
     * @param v
     *            the v
     * @param invite
     *            the invite
     */
    private void showPopupMenu(final View v, final Invitation invite) {
        final PopupMenu popupMenu = new PopupMenu(mcontext, v);
        popupMenu.getMenuInflater().inflate(R.menu.invitation_options,
                popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(final MenuItem item) {
                switch (item.getItemId()) {
                case R.id.invitation_accept:
                    confirmInvitationState();
                    return true;
                case R.id.invitation_refuse:
                    deniedInvitationState();
                    return true;
                default:
                    break;
                }
                return true;
            }

            /**
             * Muda o estado do convite para negado, caso seja negado o mesmo.
             */
            private void deniedInvitationState() {
                if (!invite.getState().equals(DENIED)) {
                    invite.setState(DENIED);
                    ParseUtil.saveInvitation(invite);
                    mListInvites.remove(invite);
                    notifyDataSetChanged();
                }
            }

            /**
             * Muda o estado do convite para aceito, caso seja aceito o mesmo.
             */
            private void confirmInvitationState() {
                if (!invite.getState().equals(ACCEPTED)) {
                    invite.setState(ACCEPTED);
                    ParseUtil.saveInvitation(invite);
                    mListInvites.remove(invite);
                    notifyDataSetChanged();
                }
            }
        });
        popupMenu.show();
    }
}
