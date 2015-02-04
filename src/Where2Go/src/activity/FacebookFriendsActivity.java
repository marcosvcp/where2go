package activity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.facebook.HttpMethod;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;

import entity.user.UserFriend;
import br.com.les.where2go.R;
import adapter.FacebookFriendsAdapter;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

public class FacebookFriendsActivity extends Activity {

	private ListView friendList;
	private List<UserFriend> friends;
	private View rootView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.facebook_friends);
		friendList = (ListView) findViewById(R.id.listViewFacebookFriends);
		friends = new ArrayList<UserFriend>();
		rootView = getLayoutInflater().inflate(R.layout.activity_main, null);
		showFriendsFacebook();
	}
	
	/**
	 * Create a request friends of facebook and get name, picture and id of users
	 */
	public void showFriendsFacebook() {
		new Request(Session.getActiveSession(), "/me/taggable_friends", null,
				HttpMethod.GET, new Request.Callback() {
					public void onCompleted(Response response) {
						Log.e("FACEBOOK", response.getGraphObject()
								.getInnerJSONObject().toString());
						try {
							JSONObject jsonFriends = new JSONObject(response
									.getGraphObject().getInnerJSONObject()
									.toString());
							JSONArray jArray = jsonFriends.getJSONArray("data");
							for (int i = 0; i < jArray.length(); i++) {
								friends.add(new UserFriend(jArray
										.getJSONObject(i).getString("name"),
										jArray.getJSONObject(i)
												.getJSONObject("picture")
												.getJSONObject("data")
												.getString("url"), jArray
												.getJSONObject(i).getString(
														"id")));
							}
							FacebookFriendsAdapter adapter = new FacebookFriendsAdapter(friends, getApplicationContext());
							friendList.setAdapter(adapter);
							
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
				}).executeAsync();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    // Inflate the menu items for use in the action bar
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.invite_friends_actions, menu);
	    return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle presses on the action bar items
	    switch (item.getItemId()) {
	        case R.id.action_done:
	            Toast.makeText(getApplicationContext(), "Done", Toast.LENGTH_LONG).show();
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
}
