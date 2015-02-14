
package activity;

import adapter.FacebookFriendsAdapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import br.com.les.where2go.R;

import com.facebook.HttpMethod;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;

import entity.event.Event;
import entity.event.Invitation;
import entity.user.User;
import entity.user.UserFriend;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import persistence.ParseUtil;
import utils.Authenticator;

import java.util.ArrayList;
import java.util.List;

/**
 * The Class FacebookFriendsActivity.
 */
public class FacebookFriendsActivity extends Activity {

    /** The friend list. */
    private ListView friendList;

    /** The friends. */
    private List<UserFriend> friends;

    /** The root view. */
    private View rootView;

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
        rootView = getLayoutInflater().inflate(R.layout.activity_main, null);
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
                HttpMethod.GET, new Request.Callback() {
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

                            // for (int i = 0; i < jArray.length(); i++) {
                            // Log.e("Json",
                            // jArray.getJSONObject(i).getString(name));
                            // }
                            // for (int i = 0; i < jArray.length(); i++) {
                            // friends.add(new UserFriend(jArray
                            // .getJSONObject(i).getString("name"),
                            // jArray.getJSONObject(i)
                            // .getJSONObject("picture")
                            // .getJSONObject("data")
                            // .getString("url"), jArray
                            // .getJSONObject(i).getString(
                            // "id")));
                            // }
                            for (int i = 0; i < jArray.length(); i++) {
                                friends.add(new UserFriend(jArray
                                        .getJSONObject(i).getString("name"),
                                        jArray.getJSONObject(i).getString("id")));
                            }
                            final FacebookFriendsAdapter adapter = new FacebookFriendsAdapter(
                                    friends, getApplicationContext());
                            friendList.setAdapter(adapter);

                        } catch (final JSONException e) {
                            e.printStackTrace();
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
                Log.v("CHECKBOX", "Completa"
                        + FacebookFriendsAdapter.getmListIdFacebook().size());
                Log.e("teste", eventToInvite.getName());
                Toast.makeText(getApplicationContext(), "Done",
                        Toast.LENGTH_LONG).show();

                for (int i = 0; i < FacebookFriendsAdapter.getmListIdFacebook()
                        .size(); i++) {
                    Log.e("ENTROU NO FOR", "XX");
                    final String facebookId = FacebookFriendsAdapter
                            .getmListIdFacebook().get(i);
                    Log.e("ESTOU BUSCANDO FB ID", facebookId);
                    ParseUtil.findByFacebookId(facebookId,
                            new FindCallback<User>() {
                                @Override
                                public void done(final List<User> objects,
                                        final ParseException e) {
                                    Log.e("ENTROU NO PARSE", "XX");
                                    final User guest = objects.get(0);
                                    final Invitation invite = new Invitation(
                                            guest, host, eventToInvite);
                                    Log.e("ANTES DE GRAVAR NO PARSE", "XX");
                                    ParseUtil.saveInvitation(invite);
                                    Log.e("DEPOIS DE GRAVAR NO PARSE", "XX");
                                    onBackPressed();
                                }
                            });
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
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
