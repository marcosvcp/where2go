
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
    private TextView tvName;

    /** The tv_email. */
    private TextView tvEmail;

    /** The tv_gender. */
    private TextView tvGender;

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

        tvName = (TextView) rootView.findViewById(R.id.tv_name);
        tvEmail = (TextView) rootView.findViewById(R.id.tv_email);
        tvGender = (TextView) rootView.findViewById(R.id.tv_gender);
        profilePictureView = (ProfilePictureView) rootView
                .findViewById(R.id.profile_picture);
        mUser = Authenticator.getInstance().getLoggedUser();
        tvName.setText(mUser.getName());
        tvEmail.setText(mUser.getEmail());
        tvGender.setText(mUser.getGender());
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
