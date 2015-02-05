package adapter;

import java.util.ArrayList;
import java.util.List;

import com.facebook.widget.ProfilePictureView;
import com.parse.ParseFacebookUtils.Permissions.Friends;

import activity.ImageLoadTask;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.TextView;
import br.com.les.where2go.R;
import entity.user.UserFriend;

public class FacebookFriendsAdapter extends BaseAdapter {
    
	/** The profile picture view. */
    private ProfilePictureView profilePictureView;

	private List<UserFriend> mFriends;
	private static List<String> mListIdFacebook;
	private Context mContext;
	private CheckBox cb_select_friend;

	public FacebookFriendsAdapter(List<UserFriend> friends, Context context) {
		mFriends = friends;
		mContext = context;
		mListIdFacebook = new ArrayList<String>();
	}

	@Override
	public int getCount() {
		return mFriends.size();
	}

	@Override
	public Object getItem(int position) {
		return mFriends.get(position);    /** The profile picture view. */

	}

	@Override
	public long getItemId(int position) {
 		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View myView = LayoutInflater.from(mContext).inflate(
				R.layout.facebook_friends_adapter, null);
		TextView tv_facebook_name = (TextView) myView
				.findViewById(R.id.tv_facebook_friend);
		UserFriend user = (UserFriend) getItem(position);
		tv_facebook_name.setText(user.getFriendName());
        profilePictureView = (ProfilePictureView) myView
                .findViewById(R.id.image_facebook_friend);
		final String friendID = mFriends.get(position).getFriendid();
		
        profilePictureView.setProfileId(friendID);
		
		cb_select_friend = (CheckBox) myView.findViewById(R.id.cb_select_friend);
		cb_select_friend.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(isChecked){
					mListIdFacebook.add(friendID);
					Log.v("CHECKBOX", "MARCADO" + friendID);
				}else{
					mListIdFacebook.remove(friendID);
					Log.v("CHECKBOX", "DESMARCADO" + friendID);
				}
			}
		});
		return myView;
	}

	public static List<String> getmListIdFacebook() {
		return mListIdFacebook;
	}

	public static void setmListIdFacebook(List<String> mListIdFacebook) {
		FacebookFriendsAdapter.mListIdFacebook = mListIdFacebook;
	}
}
