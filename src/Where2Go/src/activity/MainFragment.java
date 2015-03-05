package activity;

import java.util.Arrays;

import utils.Authenticator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import br.com.les.where2go.R;

import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.widget.LoginButton;

/**
 * Responsible to manager the session of facebook Created by marcos on 29/11/14.
 */
public class MainFragment extends Fragment {

	/** The Constant TAG. */
	private static final String TAG = "MainFragment";

	/** The ui helper. */
	private UiLifecycleHelper uiHelper;

	/** The bt enter. */
	private Button btEnter;

	/** The root view. */
	private View rootView;

	/** The m context. */
	private Context mContext;

	/**
	 * Instantiates a new main fragment.
	 *
	 * @param context
	 *            the context
	 */
	public MainFragment(final Context context) {
		mContext = context;
	}

	public MainFragment() {
	}

	/** The callback. */
	private final Session.StatusCallback callback = new Session.StatusCallback() {
		@Override
		public void call(final Session session, final SessionState state,
				final Exception exception) {
			onSessionStateChange(session, state, exception);
			Authenticator.getInstance().loadLoggedUser(session);
		}
	};

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater,
	 * android.view.ViewGroup, android.os.Bundle)
	 */
	@Override
	public final View onCreateView(final LayoutInflater inflater,
			final ViewGroup container, final Bundle savedInstanceState) {

		rootView = inflater.inflate(R.layout.activity_main, container, false);
		final LoginButton authButton = (LoginButton) rootView
				.findViewById(R.id.authButton);
		authButton.setFragment(this);
		authButton.setReadPermissions(Arrays
				.asList("email", "public_profile", "user_friends",
						"user_birthday", "user_location", "user_events"));

		btEnter = (Button) rootView.findViewById(R.id.bt_enter);
		btEnter.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(final View v) {
				final Intent intent = new Intent(getActivity()
						.getApplicationContext(), MainScreen.class);
				intent.putExtra("eventslist", 0);
				startActivity(intent);
			}
		});
		return rootView;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.support.v4.app.Fragment#onCreate(android.os.Bundle)
	 */
	@Override
	public final void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		uiHelper = new UiLifecycleHelper(getActivity(), callback);
		uiHelper.onCreate(savedInstanceState);
	}

	/**
	 * On session state change.
	 *
	 * @param session
	 *            the session
	 * @param state
	 *            the state
	 * @param exception
	 *            the exception
	 */
	private void onSessionStateChange(final Session session,
			final SessionState state, final Exception exception) {
		if (state.isOpened()) {
			Log.i(TAG, "Logged in...");
		} else if (state.isClosed()) {
			Log.i(TAG, "Logged out...");
			Authenticator.getInstance().logout();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.support.v4.app.Fragment#onResume()
	 */
	@Override
	public final void onResume() {
		super.onResume();
		// For scenarios where the main activity is launched and user
		// session is not null, the session state change notification
		// may not be triggered. Trigger it if it's open/closed.
		final Session session = Session.getActiveSession();
		if (session != null && (session.isOpened() || session.isClosed())) {
			onSessionStateChange(session, session.getState(), null);
		}

		uiHelper.onResume();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.support.v4.app.Fragment#onActivityResult(int, int,
	 * android.content.Intent)
	 */
	@Override
	public final void onActivityResult(final int requestCode,
			final int resultCode, final Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		uiHelper.onActivityResult(requestCode, resultCode, data);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.support.v4.app.Fragment#onPause()
	 */
	@Override
	public final void onPause() {
		super.onPause();
		uiHelper.onPause();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.support.v4.app.Fragment#onDestroy()
	 */
	@Override
	public final void onDestroy() {
		super.onDestroy();
		uiHelper.onDestroy();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.support.v4.app.Fragment#onSaveInstanceState(android.os.Bundle)
	 */
	@Override
	public final void onSaveInstanceState(final Bundle outState) {
		super.onSaveInstanceState(outState);
		uiHelper.onSaveInstanceState(outState);
	}
}
