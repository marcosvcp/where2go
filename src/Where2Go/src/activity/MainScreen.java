package activity;

import java.util.ArrayList;
import java.util.List;

import slidermenu.NavDrawerItem;
import slidermenu.NavDrawerListAdapter;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import br.com.les.where2go.R;

import com.facebook.Session;

import entity.event.Event;

/**
 * Application core.
 */  
public class MainScreen extends Activity {

    /** The main fragment. */
    private MainFragment mainFragment;
    
    /** The m views. */
    private List<View> mViews;
    
    /** The events. */
    public static List<Event> events = new ArrayList<Event>();
    
    /** The choosed fragment. */
    public static int choosedFragment = 0;
    
    /** The backit. */
    public static boolean backit = false;
    
    /** The m context. */
    private static Context mContext;
	
	/** The m drawer layout. */
	private DrawerLayout mDrawerLayout;
	
	/** The m drawer list. */
	private ListView mDrawerList;
	
	/** The m drawer toggle. */
	private ActionBarDrawerToggle mDrawerToggle;
	
	/** The m drawer title. */
	private CharSequence mDrawerTitle;
	
	/** The m content. */
	private static Fragment mContent;
	
	// nav drawer title
	/** The m title. */
	private CharSequence mTitle;

	// slide menu items
	/** The nav menu titles. */
	private String[] navMenuTitles;
	
	/** The nav menu icons. */
	private TypedArray navMenuIcons;
	
	/** The nav drawer items. */
	private ArrayList<NavDrawerItem> navDrawerItems;
	
	/** The adapter. */
	private NavDrawerListAdapter adapter;

	
	
    /**
     * Called when the activity is first created.
     *
     * @param savedInstanceState the saved instance state
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_screen);
        setStatusBarColor(findViewById(R.id.statusBarBackground),getResources().getColor(R.color.status_bar));

        mTitle = mDrawerTitle = getTitle();
        navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);
        
        navMenuIcons = getResources().obtainTypedArray(R.array.nav_drawer_icons);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.list_slidermenu);
        
        navDrawerItems = new ArrayList<NavDrawerItem>();
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[0], navMenuIcons.getResourceId(0, -1)));
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[1], navMenuIcons.getResourceId(1, -1)));
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[2], navMenuIcons.getResourceId(2, -1)));
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[3], navMenuIcons.getResourceId(3, -1), true, "22"));
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[4], navMenuIcons.getResourceId(4, -1), true, "50+"));
        // Recycle the typed array
        navMenuIcons.recycle();

        mDrawerList.setOnItemClickListener(new SlideMenuClickListener());

        // setting the nav drawer list adapter
        adapter = new NavDrawerListAdapter(getApplicationContext(),navDrawerItems);
        mDrawerList.setAdapter(adapter);

//        // enabling action bar app icon and behaving it as toggle button
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.drawable.ic_drawer, R.string.app_name, R.string.app_name) {

            public void onDrawerClosed(View view) {
                getActionBar().setTitle(mTitle);
                // calling onPrepareOptionsMenu() to show action bar icons
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                getActionBar().setTitle("Where2Go");

                // calling onPrepareOptionsMenu() to hide action bar icons
                invalidateOptionsMenu();
            }
        };
        
        if (savedInstanceState != null) {
            // Restore the fragment's instance
            mContent = getFragmentManager().getFragment(
                    savedInstanceState, "mContent");
        }
        
		mDrawerLayout.setDrawerListener(mDrawerToggle);
        Intent i = getIntent();
        Bundle extras = i.getExtras();
        if (i.getIntExtra("eventslist", -1) != -1) {

            displayView(i.getIntExtra("eventslist", -1));
            choosedFragment = i.getIntExtra("fragmentIndex", -1);
            backit = false;
            return;
        }
        
		if (savedInstanceState == null) {
			// on first time display view for first nav item
			displayView(0);
		}

    }
    
    /**
     * Display view for selected nav drawer item.
     *
     * @see SlideMenuClickEvent
     */
    private class SlideMenuClickListener implements
            ListView.OnItemClickListener {
        
        /* (non-Javadoc)
         * @see android.widget.AdapterView.OnItemClickListener#onItemClick(android.widget.AdapterView, android.view.View, int, long)
         */
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
            displayView(position);
        }
    }
    
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main_screen, menu);
		return true;
	}
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// toggle nav drawer on selecting action bar app icon/title
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		// Handle action bar actions click
		switch (item.getItemId()) {
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	/* *
	 * Called when invalidateOptionsMenu() is triggered
	 */
	/* (non-Javadoc)
	 * @see android.app.Activity#onPrepareOptionsMenu(android.view.Menu)
	 */
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// if nav drawer is opened, hide the action items
		boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
		return super.onPrepareOptionsMenu(menu);
	}
	
    /**
     * Diplaying fragment view for selected nav drawer list item.
     *
     * @param position the position
     */
    private void displayView(int position) {
        // update the main content by replacing fragments
        Fragment fragment = null;
        backit = false;
        switch (position) {
            case 0:
                fragment = new AdsFragment();
                backit = true;
                choosedFragment = 0;
                break;
            case 1:
                fragment = new MapFragment();
                break;
            case 2:
                fragment = new EventsListFragment();
                break;
            case 3:
            	fragment = new ProfileFragment();
                break;
            case 4:
            	logoutFacebook();
                final Intent intent = new Intent(getApplicationContext(),MainActivity.class);
            	startActivity(intent);
                break;
            default:
                break;
        }
        
		if (fragment != null) {
			FragmentManager fragmentManager = getFragmentManager();
			fragmentManager.beginTransaction().replace(R.id.frame_container, fragment).commit();

			// update selected item and title, then close the drawer
			mDrawerList.setItemChecked(position, true);
			mDrawerList.setSelection(position);
			setTitle(navMenuTitles[position]);
			mDrawerLayout.closeDrawer(mDrawerList);
			
		} else {
			// error in creating fragment
			Log.e("MainActivity", "Error in creating fragment");
		}
    }
    
    /* (non-Javadoc)
     * @see android.app.Activity#onSaveInstanceState(android.os.Bundle)
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // Save the fragment's instance
        try {
            getFragmentManager().putFragment(outState, "mContent", mContent);
        } catch (Exception e) {
            // TODO: handle exception
        }

    }
    
	/* (non-Javadoc)
	 * @see android.app.Activity#setTitle(java.lang.CharSequence)
	 */
	@Override
	public void setTitle(CharSequence title) {
		mTitle = title;
		getActionBar().setTitle(mTitle);
	}
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onPostCreate(android.os.Bundle)
	 */
	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onConfigurationChanged(android.content.res.Configuration)
	 */
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggls
		mDrawerToggle.onConfigurationChanged(newConfig);
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
	
	/**
	 * Logout facebook.
	 */
	public void logoutFacebook(){
		if (Session.getActiveSession() != null) {
		    Session.getActiveSession().closeAndClearTokenInformation();
		}
		Session.setActiveSession(null);
	}
}
