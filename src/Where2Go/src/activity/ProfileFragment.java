package activity;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import br.com.les.where2go.R;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.model.GraphUser;
import com.facebook.widget.ProfilePictureView;

import entity.user.User;

/**
 * Fragment responsible to manager of logged user.
 *
 * @author julioandherson
 */
public class ProfileFragment extends Fragment{

	/** The profile picture view. */
	private ProfilePictureView profilePictureView;
    
    /** The tv_name. */
    private TextView tv_name;
    
    /** The tv_email. */
    private TextView tv_email;
    
    /** The tv_gender. */
    private TextView tv_gender;
    
    /** The m user. */
    private static User mUser;
    
    /**
     * Instantiates a new profile fragment.
     */
    public ProfileFragment() {
    }
    
    /* (non-Javadoc)
     * @see android.app.Fragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.activity_profile, container, false);
		
        tv_name = (TextView) rootView.findViewById(R.id.tv_name);
		tv_email = (TextView) rootView.findViewById(R.id.tv_email);
		tv_gender = (TextView) rootView.findViewById(R.id.tv_gender);
		profilePictureView = (ProfilePictureView) rootView.findViewById(R.id.profile_picture);
        Session session = Session.getActiveSession();
        if (session != null && session.isOpened()) {
                makeMeRequest(session);
        }
		return rootView;
    }
    
	/**
	 * Make me request to get user's data
	 *
	 * @param session the session
	 */
	private void makeMeRequest(final Session session) {
		Request request = Request.newMeRequest(session,
				new Request.GraphUserCallback() {
					@Override
					public void onCompleted(GraphUser user, Response response) {
						if (session == Session.getActiveSession()) {
							if (user != null) {
								Log.v("USERS", user.asMap().toString());
								mUser = new User(user.getName());
								mUser.setBirthday(user.asMap().get("birthday").toString());
								mUser.setEmail(user.asMap().get("email").toString());
								mUser.setGender(user.asMap().get("gender").toString());
								
								tv_name.setText(mUser.getName());
								tv_email.setText(mUser.getEmail());
								tv_gender.setText(mUser.getGender());
								profilePictureView.setProfileId(user.getId());
							}
						}
					}
				});
		request.executeAsync();
	}

	public static User getmUser() {
		return mUser;
	}

	public static void setmUser(User mUser) {
		ProfileFragment.mUser = mUser;
	}
}