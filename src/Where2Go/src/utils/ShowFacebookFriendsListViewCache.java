package utils;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import br.com.les.where2go.R;

public class ShowFacebookFriendsListViewCache {
    
	/** The base view. */
    private final View mBaseView;

    /** The user name. */
    private TextView userName;

    /** The image user. */
    private ImageView imageUser;

    /**
     * Instantiates a new comment list view cache.
     * 
     * @param baseView the base view
     */
    public ShowFacebookFriendsListViewCache(
            final View baseView) {
        this.mBaseView =
                baseView;
    }

    /**
     * Gets the view base.
     * 
     * @return the view base
     */
    public final View getViewBase() {
        return mBaseView;
    }

    /**
     * Gets the text name user.
     * 
     * @param resource the resource
     * @return the text name user
     */
    public final TextView getTextNameUser(
            final int resource) {
        if (userName == null) {
            userName =
                    (TextView) mBaseView
                            .findViewById(R.id.tv_facebook_friend);
        }
        return userName;
    }

    /**
     * Gets the image user.
     * 
     * @param resource the resource
     * @return the image user
     */
    public final ImageView getImageUser(
            final int resource) {
        if (imageUser == null) {
            imageUser =
                    (ImageView) mBaseView
                            .findViewById(R.id.image_facebook_friend);
        }
        return imageUser;
    }
}
