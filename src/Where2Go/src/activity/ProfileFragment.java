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


public class ProfileFragment extends Fragment{
	
	private ProfilePictureView profilePictureView;
    private TextView tv_name;
    private TextView tv_email;
    private TextView tv_gender;
    private User mUser;
    
    
    public ProfileFragment() {
    }
    
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
 
}
