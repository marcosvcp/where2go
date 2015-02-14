
package activity;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import br.com.les.where2go.R;

import com.facebook.widget.ProfilePictureView;

import entity.user.User;
import utils.Authenticator;

// TODO: Auto-generated Javadoc
/**
 * Fragment responsible to manager of logged user.
 * 
 */
public class ProfileFragment extends Fragment {

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

    /*
     * (non-Javadoc)
     * 
     * @see android.app.Fragment#onCreateView(android.view.LayoutInflater,
     * android.view.ViewGroup, android.os.Bundle)
     */
    @Override
    public final View onCreateView(final LayoutInflater inflater, final ViewGroup container,
            final Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.activity_profile,
                container, false);

        tv_name = (TextView) rootView.findViewById(R.id.tv_name);
        tv_email = (TextView) rootView.findViewById(R.id.tv_email);
        tv_gender = (TextView) rootView.findViewById(R.id.tv_gender);
        profilePictureView = (ProfilePictureView) rootView
                .findViewById(R.id.profile_picture);
        mUser = Authenticator.getInstance().getLoggedUser();
        tv_name.setText(mUser.getName());
        tv_email.setText(mUser.getEmail());
        tv_gender.setText(mUser.getGender());
        profilePictureView.setProfileId(mUser.getFacebookId());
        return rootView;
    }

    /**
     * Gets the m user.
     * 
     * @return the m user
     */
    public static User getmUser() {
        return mUser;
    }

    /**
     * Sets the m user.
     * 
     * @param newUser the new m user
     */
    public static void setmUser(final User newUser) {
        ProfileFragment.mUser = newUser;
    }
}
