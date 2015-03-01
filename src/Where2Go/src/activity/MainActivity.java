package activity;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedList;
import java.util.List;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Base64;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import br.com.les.where2go.R;

import com.facebook.AppEventsLogger;
import com.parse.Parse;
import com.parse.ParseFacebookUtils;
import com.parse.ParseObject;

import entity.event.Event;
import entity.event.Invitation;
import entity.user.User;

/**
 * Application core.
 */
public class MainActivity extends FragmentActivity {

    /** The main fragment. */
    private MainFragment mainFragment;

    /** The m views. */
    private List<View> mViews;

    /**
     * Called when the activity is first created.
     *
     * @param savedInstanceState
     *            the saved instance state
     */
    @Override
    public final void onCreate(final Bundle savedInstanceState) {
        try {
            final PackageInfo info = getPackageManager().getPackageInfo(
                    "br.com.les.where2go", PackageManager.GET_SIGNATURES);
            for (final android.content.pm.Signature signature : info.signatures) {
                final MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:",
                        Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (final PackageManager.NameNotFoundException e) {
            Log.d("Error1", "NameNotFoundException");

        } catch (final NoSuchAlgorithmException e) {
            Log.d("Error2", "Algorthim");
        }
        super.onCreate(savedInstanceState);
        Parse.initialize(this, "nUL0Lh3eOXpMYmaUgMJuveMYC0cIkEupF0eaqmh6",
                "vd7smIM4ePAFUtWhQfT7TpNKmJ2d9PvfDeqke16D");
        ParseObject.registerSubclass(Event.class);
        ParseObject.registerSubclass(User.class);
        ParseObject.registerSubclass(Invitation.class);
        setContentView(R.layout.activity_main);
        setStatusBarColor(findViewById(R.id.statusBarBackground),
                getResources().getColor(R.color.status_bar));

        mViews = new LinkedList<View>();

        if (savedInstanceState == null) {
            // Add the fragment on initial activity setup
            mainFragment = new MainFragment(getApplicationContext());
            getSupportFragmentManager().beginTransaction()
                    .add(android.R.id.content, mainFragment).commit();
        } else {
            // Or set the fragment from restored state info
            mainFragment = (MainFragment) getSupportFragmentManager()
                    .findFragmentById(android.R.id.content);
        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see android.support.v4.app.FragmentActivity#onResume()
     */
    @Override
    protected final void onResume() {
        super.onResume();
        // Logs 'install' and 'app activate' App Events.
        AppEventsLogger.activateApp(this);
    }

    /*
     * (non-Javadoc)
     * 
     * @see android.support.v4.app.FragmentActivity#onPause()
     */
    @Override
    protected final void onPause() {
        super.onPause();
        // Logs 'app deactivate' App Event.
        AppEventsLogger.deactivateApp(this);
    }

    /**
     * Block all views to avoid multiple clicks.
     */
    private void lockAll() {
        for (final View v : mViews) {
            v.setEnabled(false);
        }
    }

    /**
     * Unlock all views.
     */
    private void unlockAll() {
        for (final View v : mViews) {
            v.setEnabled(true);
        }
    }

    /**
     * Open activity MainScreen.
     */
    public final void enterEventList() {
        final Intent intent = new Intent(getApplicationContext(),
                MainScreen.class);
        intent.putExtra("eventslist", 2);
        startActivity(intent);
        unlockAll();
    }

    /**
     * Sets the status bar color.
     *
     * @param statusBar
     *            the status bar
     * @param color
     *            the color
     */
    public final void setStatusBarColor(final View statusBar, final int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            final Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // status bar height
            final int actionBarHeight = getActionBarHeight();
            final int statusBarHeight = getStatusBarHeight();
            // action bar height
            statusBar.getLayoutParams().height = actionBarHeight
                    + statusBarHeight;
            statusBar.setBackgroundColor(color);
        }
    }

    /**
     * Gets the action bar height.
     *
     * @return the action bar height
     */
    public final int getActionBarHeight() {
        int actionBarHeight = 0;
        final TypedValue tv = new TypedValue();
        if (getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
            actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data,
                    getResources().getDisplayMetrics());
        }
        return actionBarHeight;
    }

    /**
     * Gets the status bar height.
     *
     * @return the status bar height
     */
    public final int getStatusBarHeight() {
        int result = 0;
        final int resourceId = getResources().getIdentifier(
                "status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see android.support.v4.app.FragmentActivity#onActivityResult(int, int,
     * android.content.Intent)
     */
    @Override
    protected final void onActivityResult(final int requestCode,
            final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ParseFacebookUtils.finishAuthentication(requestCode, resultCode, data);
    }

}
