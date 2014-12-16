package activity;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
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

/**
 * Application core.
 */
public class MainActivity extends FragmentActivity {

    /** The main fragment. */
    private MainFragment mainFragment;
    
    /** The m views. */
    private List<View> mViews;
    
    /** The events. */
    public static List<Event> events = new ArrayList<Event>();
    
    /**
     * Called when the activity is first created.
     *
     * @param savedInstanceState the saved instance state
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Parse.initialize(this, "nUL0Lh3eOXpMYmaUgMJuveMYC0cIkEupF0eaqmh6", "vd7smIM4ePAFUtWhQfT7TpNKmJ2d9PvfDeqke16D");
        ParseObject.registerSubclass(Event.class);
        setContentView(R.layout.activity_main);
        setStatusBarColor(findViewById(R.id.statusBarBackground),getResources().getColor(R.color.status_bar));

        mViews = new LinkedList<View>();
        
        if (savedInstanceState == null) {
            // Add the fragment on initial activity setup
            mainFragment = new MainFragment();
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(android.R.id.content, mainFragment)
                    .commit();
        } else {
            // Or set the fragment from restored state info
            mainFragment = (MainFragment) getSupportFragmentManager()
                    .findFragmentById(android.R.id.content);
        }
        
    }

    /* (non-Javadoc)
     * @see android.support.v4.app.FragmentActivity#onResume()
     */
    @Override
    protected void onResume() {
        super.onResume();
        // Logs 'install' and 'app activate' App Events.
        AppEventsLogger.activateApp(this);
    }

    /* (non-Javadoc)
     * @see android.support.v4.app.FragmentActivity#onPause()
     */
    @Override
    protected void onPause() {
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
    public void enterEventList() {
		Intent intent = new Intent(getApplicationContext(), MainScreen.class);
		intent.putExtra("eventslist", 2);
        startActivity(intent);
    	unlockAll();
    }
    
	/**
	 * Sets the status bar color.
	 *
	 * @param statusBar the status bar
	 * @param color the color
	 */
	public void setStatusBarColor(View statusBar,int color){
	    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
	           Window w = getWindow();
	           w.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
	           //status bar height
	           int actionBarHeight = getActionBarHeight();
	           int statusBarHeight = getStatusBarHeight();
	           //action bar height
	           statusBar.getLayoutParams().height = actionBarHeight + statusBarHeight;
	           statusBar.setBackgroundColor(color);
	     }
	}
	
	/**
	 * Gets the action bar height.
	 *
	 * @return the action bar height
	 */
	public int getActionBarHeight() {
	    int actionBarHeight = 0;
	    TypedValue tv = new TypedValue();
	    if (getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true))
	    {
	       actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data,getResources().getDisplayMetrics());
	    }
	    return actionBarHeight;
	}

	/**
	 * Gets the status bar height.
	 *
	 * @return the status bar height
	 */
	public int getStatusBarHeight() {
	    int result = 0;
	    int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
	    if (resourceId > 0) {
	        result = getResources().getDimensionPixelSize(resourceId);
	    }
	    return result;
	}
	
	/* (non-Javadoc)
	 * @see android.support.v4.app.FragmentActivity#onActivityResult(int, int, android.content.Intent)
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		 super.onActivityResult(requestCode, resultCode, data);
		  ParseFacebookUtils.finishAuthentication(requestCode, resultCode, data);
	}
}
