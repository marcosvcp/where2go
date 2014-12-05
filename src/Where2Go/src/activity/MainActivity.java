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
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import br.com.les.where2go.R;

import com.facebook.AppEventsLogger;

import entity.event.Event;

/**
 * Application core
 */
public class MainActivity extends FragmentActivity {

    private MainFragment mainFragment;
    private List<View> mViews;
    private ImageButton btEnter;
    public static List<Event> events = new ArrayList<Event>();
    
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setStatusBarColor(findViewById(R.id.statusBarBackground),getResources().getColor(R.color.status_bar));

        mViews = new LinkedList<View>();

        btEnter = (ImageButton) findViewById(R.id.bt_enter);
        btEnter.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(), MainScreen.class);
				intent.putExtra("eventslist", 0);
	            startActivity(intent);
			}
		});
        
        /*if (savedInstanceState == null) {
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
        }*/
        
        
        
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Logs 'install' and 'app activate' App Events.
        AppEventsLogger.activateApp(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Logs 'app deactivate' App Event.
        AppEventsLogger.deactivateApp(this);
    }
    
    /**
     * Bloqueia todas as Views, para evitar que duas das mesmas sejam selecionadas ao mesmo tempo.
     */
    private void lockAll() {
        for (final View v : mViews) {
            v.setEnabled(false);
        }
    }
    
    /**
     * Desbloqueia as Views.
     */
    private void unlockAll() {
        for (final View v : mViews) {
            v.setEnabled(true);
        }
    }
    
    /**
     * Evento referente a ação do botão EnterEventList.
     */
    public void enterEventList() {
		Intent intent = new Intent(getApplicationContext(), MainScreen.class);
		intent.putExtra("eventslist", 2);
        startActivity(intent);
    	unlockAll();
    }
    
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
	
	public int getActionBarHeight() {
	    int actionBarHeight = 0;
	    TypedValue tv = new TypedValue();
	    if (getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true))
	    {
	       actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data,getResources().getDisplayMetrics());
	    }
	    return actionBarHeight;
	}

	public int getStatusBarHeight() {
	    int result = 0;
	    int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
	    if (resourceId > 0) {
	        result = getResources().getDimensionPixelSize(resourceId);
	    }
	    return result;
	}
}
