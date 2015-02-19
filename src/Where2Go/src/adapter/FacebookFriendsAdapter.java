
package adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

import com.facebook.widget.ProfilePictureView;

import java.util.ArrayList;
import java.util.List;

import br.com.les.where2go.R;
import entity.user.UserFriend;

/**
 * The Class FacebookFriendsAdapter.
 */
public class FacebookFriendsAdapter extends BaseAdapter {

    /** The profile picture view. */
    private ProfilePictureView profilePictureView;

    /** The m friends. */
    private final List<UserFriend> mFriends;

    /** The m list id facebook. */
    private static List<String> mListIdFacebook;

    /** The m context. */
    private final Context mContext;

    /** The cbSelectFriend. */
    private CheckBox cbSelectFriend;

    /**
     * Instantiates a new facebook friends adapter.
     * 
     * @param friends the friends
     * @param context the context
     */
    public FacebookFriendsAdapter(final List<UserFriend> friends, final Context context) {
        mFriends = friends;
        mContext = context;
        setmListIdFacebook(new ArrayList<String>());
    }

    /*
     * (non-Javadoc)
     * 
     * @see android.widget.Adapter#getCount()
     */
    @Override
    public final int getCount() {
        return mFriends.size();
    }

    /*
     * (non-Javadoc)
     * 
     * @see android.widget.Adapter#getItem(int)
     */
    @Override
    public final Object getItem(int position) {
        return mFriends.get(position);
        /** The profile picture view. */

    }

    /*
     * (non-Javadoc)
     * 
     * @see android.widget.Adapter#getItemId(int)
     */
    @Override
    public final long getItemId(final int position) {
        return 0;
    }

    /*
     * (non-Javadoc)
     * 
     * @see android.widget.Adapter#getView(int, android.view.View,
     * android.view.ViewGroup)
     */
    @Override
    public final View getView(final int position, final View convertView, final ViewGroup parent) {
        View myView = LayoutInflater.from(mContext).inflate(
                R.layout.facebook_friends_adapter, null);
        TextView tvFacebookName = (TextView) myView
                .findViewById(R.id.tv_facebook_friend);
        UserFriend user = (UserFriend) getItem(position);
        tvFacebookName.setText(user.getFriendName());
        profilePictureView = (ProfilePictureView) myView
                .findViewById(R.id.image_facebook_friend);
        final String friendID = mFriends.get(position).getFriendid();

        profilePictureView.setProfileId(friendID);

        cbSelectFriend = (CheckBox) myView.findViewById(R.id.cbSelectFriend);
        cbSelectFriend.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(final CompoundButton buttonView, final boolean isChecked) {
                if (isChecked) {
                    mListIdFacebook.add(friendID);
                    Log.v("CHECKBOX", "MARCADO" + friendID);
                } else {
                    mListIdFacebook.remove(friendID);
                    Log.v("CHECKBOX", "DESMARCADO" + friendID);
                }
            }
        });
        return myView;
    }

    /**
     * Gets the m list id facebook.
     * 
     * @return the m list id facebook
     */
    public static List<String> getmListIdFacebook() {
        return mListIdFacebook;
    }

    /**
     * Sets the m list id facebook.
     * 
     * @param listIdFacebook the new m list id facebook
     */
    public static void setmListIdFacebook(final List<String> listIdFacebook) {
        FacebookFriendsAdapter.mListIdFacebook = listIdFacebook;
    }
}
