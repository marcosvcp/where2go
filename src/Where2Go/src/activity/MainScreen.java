package activity;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
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

import com.facebook.Session;

import java.util.ArrayList;
import java.util.List;

import br.com.les.where2go.R;
import slidermenu.NavDrawerItem;
import slidermenu.NavDrawerListAdapter;
import utils.Authenticator;

/**
 * Application core.
 */
public class MainScreen extends Activity {

	/** The choosed fragment. */
	private static int choosedFragment = 0;

	/** The backit. */
	private boolean backit = false;

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
	private List<NavDrawerItem> navDrawerItems;

	/** The adapter. */
	private NavDrawerListAdapter adapter;

	/**
	 * Called when the activity is first created.
	 * 
	 * @param savedInstanceState
	 *            the saved instance state
	 */
	@Override
	public final void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_screen);
		setStatusBarColor(findViewById(R.id.statusBarBackground),
				getResources().getColor(R.color.status_bar));

		mTitle = mDrawerTitle = getTitle();
		navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);

		navMenuIcons = getResources()
				.obtainTypedArray(R.array.nav_drawer_icons);
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.list_slidermenu);

		navDrawerItems = new ArrayList<NavDrawerItem>();
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[0], navMenuIcons
				.getResourceId(0, -1)));
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[1], navMenuIcons
				.getResourceId(1, -1)));
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[2], navMenuIcons
				.getResourceId(2, -1)));
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[3], navMenuIcons
				.getResourceId(3, -1), true, "22"));
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[4], navMenuIcons
				.getResourceId(4, -1), true, "50+"));
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[5], navMenuIcons
				.getResourceId(5, -1)));
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[6], navMenuIcons
				.getResourceId(6, -1)));
		// Recycle the typed array
		navMenuIcons.recycle();

		mDrawerList.setOnItemClickListener(new SlideMenuClickListener());

		// setting the nav drawer list adapter
		adapter = new NavDrawerListAdapter(getApplicationContext(),
				navDrawerItems);
		mDrawerList.setAdapter(adapter);

		// // enabling action bar app icon and behaving it as toggle button
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);

		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.ic_drawer, R.string.app_name, R.string.app_name) {

			@Override
			public void onDrawerClosed(final View view) {
				getActionBar().setTitle(mTitle);
				// calling onPrepareOptionsMenu() to show action bar icons
				invalidateOptionsMenu();
			}

			@Override
			public void onDrawerOpened(final View drawerView) {
				getActionBar().setTitle("Where2Go");

				// calling onPrepareOptionsMenu() to hide action bar icons
				invalidateOptionsMenu();
			}
		};

		if (savedInstanceState != null) {
			// Restore the fragment's instance
			setmContent(getFragmentManager().getFragment(savedInstanceState,
					"mContent"));
		}

		mDrawerLayout.setDrawerListener(mDrawerToggle);
		final Intent intent = getIntent();
		if (intent.getIntExtra("eventslist", -1) != -1) {

			displayView(intent.getIntExtra("eventslist", -1));
			setChoosedFragment(intent.getIntExtra("fragmentIndex", -1));
			setBackit(false);
			return;
		}

		if (savedInstanceState == null) {
			// on first time display view for first nav item
			displayView(0);
		}

	}

	/**
	 * Display view for selected nav drawer item.
	 */
	private class SlideMenuClickListener implements
			ListView.OnItemClickListener {

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * android.widget.AdapterView.OnItemClickListener#onItemClick(android
		 * .widget.AdapterView, android.view.View, int, long)
		 */
		@Override
		public void onItemClick(final AdapterView<?> parent, final View view,
				final int position, final long id) {
			displayView(position);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
	 */
	@Override
	public final boolean onCreateOptionsMenu(final Menu menu) {
		getMenuInflater().inflate(R.menu.main_screen, menu);
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
	 */
	@Override
	public final boolean onOptionsItemSelected(final MenuItem item) {
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
	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onPrepareOptionsMenu(android.view.Menu)
	 */
	@Override
	public final boolean onPrepareOptionsMenu(final Menu menu) {
		// if nav drawer is opened, hide the action items
		return super.onPrepareOptionsMenu(menu);
	}

	/**
	 * Diplaying fragment view for selected nav drawer list item.
	 * 
	 * @param position
	 *            the position
	 */
	private void displayView(final int position) {
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
				fragment = new EventsListFragment();
				break;
			case 2:
				fragment = new MyEventsListFragment();
				break;
			case 3:
				fragment = new MyInvitesFragment();
				break;
			case 4:
				fragment = new MapFragment();
				break;
			case 5:
				fragment = new ProfileFragment();
				break;
			case 6:
				logoutFacebook();
				final Intent intent = new Intent(getApplicationContext(),
						MainActivity.class);
				startActivity(intent);
				break;
			default:
				break;
		}

		if (fragment != null) {
			final FragmentManager fragmentManager = getFragmentManager();
			fragmentManager.beginTransaction()
					.replace(R.id.frame_container, fragment).commit();

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

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onSaveInstanceState(android.os.Bundle)
	 */
	@Override
	protected final void onSaveInstanceState(final Bundle outState) {
		super.onSaveInstanceState(outState);

		// Save the fragment's instance
		try {
			getFragmentManager().putFragment(outState, "mContent", mContent);
		} catch (final Exception e) {
			Log.e("Mainscreen", "MainScreen not load");
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#setTitle(java.lang.CharSequence)
	 */
	@Override
	public final void setTitle(final CharSequence title) {
		mTitle = title;
		getActionBar().setTitle(mTitle);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onPostCreate(android.os.Bundle)
	 */
	@Override
	protected final void onPostCreate(final Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.app.Activity#onConfigurationChanged(android.content.res.Configuration
	 * )
	 */
	@Override
	public final void onConfigurationChanged(final Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggls
		mDrawerToggle.onConfigurationChanged(newConfig);
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
	public int getActionBarHeight() {
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

	/**
	 * Logout facebook.
	 */
	public final void logoutFacebook() {
		if (Session.getActiveSession() != null) {
			Session.getActiveSession().closeAndClearTokenInformation();
			Authenticator.getInstance().logout();
		}
		Session.setActiveSession(null);
	}

	/**
	 * Get the mContent.
	 * 
	 * @return mContent
	 */
	public static Fragment getmContent() {
		return mContent;
	}

	/**
	 * Set the mContent.
	 * 
	 * @param mContent
	 *            the new mContent
	 */
	public static void setmContent(Fragment mContent) {
		MainScreen.mContent = mContent;
	}

	/**
	 * Get the ChoosedFragment.
	 * 
	 * @return the choosedFragmet
	 */
	public static int getChoosedFragment() {
		return choosedFragment;
	}

	/**
	 * Set the ChoosedFragment.
	 * 
	 * @param choosedFragment
	 *            the new choosedFragment
	 */
	public static void setChoosedFragment(int choosedFragment) {
		MainScreen.choosedFragment = choosedFragment;
	}

	/**
	 * Check if isBackit.
	 *
	 * @return true if isBackit, else return false
	 */
	public  boolean isBackit() {
		return backit;
	}

	/**
	 * Set the state of Backit.
	 *
	 * @param backit
	 *            the new state of backit
	 */
	public void setBackit(boolean backit) {
		backit = backit;
	}
}
