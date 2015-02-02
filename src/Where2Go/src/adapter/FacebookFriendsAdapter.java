package adapter;

import java.util.List;

import activity.ImageLoadTask;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import br.com.les.where2go.R;
import entity.user.User;
import entity.user.UserFriend;

public class FacebookFriendsAdapter extends BaseAdapter {

	private List<UserFriend> mFriends;
	private Context mContext;

	public FacebookFriendsAdapter(List<UserFriend> friends, Context context) {
		mFriends = friends;
		mContext = context;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mFriends.size();
	}

	@Override
	public Object getItem(int position) {
		return mFriends.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
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
		ImageView friendImage = (ImageView) myView
				.findViewById(R.id.image_facebook_friend);
		ImageLoadTask image = new ImageLoadTask(user.getFriendPicture(),
				friendImage);
		image.execute();
		return myView;
	}
}
