
package activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import static com.facebook.Request.Callback;
import com.facebook.HttpMethod;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import adapter.FacebookFriendsAdapter;
import br.com.les.where2go.R;
import entity.event.Event;
import entity.event.Invitation;
import entity.user.User;
import entity.user.UserFriend;
import persistence.ParseUtil;
import utils.Authenticator;

/**
 * The Class FacebookFriendsActivity.
 */
public class FacebookFriendsActivity extends Activity {

    /** The friend list. */
    private ListView friendList;

    /** The friends. */
    private List<UserFriend> friends;

    /** The host. */
    private User host;

    /** The event to invite. */
    private Event eventToInvite;

    /*
     * (non-Javadoc)
     * 
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */
    @Override
    protected final void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.facebook_friends);
        friendList = (ListView) findViewById(R.id.listViewFacebookFriends);
        friends = new ArrayList<UserFriend>();
        host = Authenticator.getInstance().getLoggedUser();
        final Intent getIntent = getIntent();
        final String eventId = getIntent.getStringExtra("EventId");
        Log.e("Intent", eventId);
        eventToInvite = new Event();
        ParseUtil.findEventById(eventId, new GetCallback<Event>() {

            @Override
            public void done(final Event object, final ParseException e) {
                if (object != null) {
                    eventToInvite = object;
                }
            }
        });
        showFriendsFacebook();
    }

    /**
     * Create a request friends of facebook and get name, picture and id of
     * users.
     */
    public final void showFriendsFacebook() {
        new Request(Session.getActiveSession(), "/me/friends", null,
                HttpMethod.GET, new Callback() {
                    @Override
                    public void onCompleted(final Response response) {
                        Log.e("FACEBOOK", response.getGraphObject()
                                .getInnerJSONObject().toString());
                        try {
                            final JSONObject jsonFriends = new JSONObject(
                                    response.getGraphObject()
                                            .getInnerJSONObject().toString());
                            final JSONArray jArray = jsonFriends
                                    .getJSONArray("data");

                            for (int i = 0; i < jArray.length(); i++) {
                                friends.add(new UserFriend(jArray
                                        .getJSONObject(i).getString("name"),
                                        jArray.getJSONObject(i).getString("id")));
                            }
                            final FacebookFriendsAdapter adapter = new FacebookFriendsAdapter(
                                    friends, getApplicationContext());
                            friendList.setAdapter(adapter);

                        } catch (final JSONException e) {
                            Log.e("FriendsActivity", e.getMessage());
                        }
                    }
                }).executeAsync();
    }

    /*
     * (non-Javadoc)
     * 
     * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
     */
    @Override
    public final boolean onCreateOptionsMenu(final Menu menu) {
        // Inflate the menu items for use in the action bar
        final MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.invite_friends_actions, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /*
     * (non-Javadoc)
     * 
     * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
     */
    @Override
    public final boolean onOptionsItemSelected(final MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_done:
                loadFacebookData();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Carrega os dados do facebook
     */
    private void loadFacebookData() {
        Toast.makeText(getApplicationContext(), "Done",
                Toast.LENGTH_LONG).show();

        for (int i = 0; i < FacebookFriendsAdapter.getmListIdFacebook()
                .size(); i++) {
            final String facebookId = FacebookFriendsAdapter
                    .getmListIdFacebook().get(i);
            ParseUtil.findByFacebookId(facebookId,
                    new FindCallback<User>() {
                        @Override
                        public void done(final List<User> objects,
                                         final ParseException e) {
                            final User guest = objects.get(0);
                            final Invitation invite = new Invitation(
                                    guest, host, eventToInvite);
                            ParseUtil.saveInvitation(invite);
                            onBackPressed();
                        }
                    });
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see android.app.Activity#onBackPressed()
     */
    @Override
    public final void onBackPressed() {
        FacebookFriendsAdapter.setmListIdFacebook(new ArrayList<String>());
        super.onBackPressed();
    }
}
