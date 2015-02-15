
package activity;

import java.util.List;

import persistence.ParseUtil;
import android.R.color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import br.com.les.where2go.R;

import com.parse.GetCallback;
import com.parse.ParseException;

import entity.event.Event;

/**
 * Application core.
 */
public class EventDetailActivity extends FragmentActivity {

    /** The main fragment. */
    private MainFragment mainFragment;

    /** The m views. */
    private List<View> mViews;
    
    /** The event. */
    private Event event;

    /** The event name. */
    private TextView etEventName;
    
    /** The event desscription. */
    private TextView etEventDescription;

    
    /**
     * Called when the activity is first created.
     * 
     * @param savedInstanceState the saved instance state
     */
    @Override
    public final void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);
        setStatusBarColor(findViewById(R.id.statusBarBackground),color.transparent);
        
        final Bundle data = getIntent().getExtras();
        final String key = data.getString("event_id");
        
        etEventName = (TextView) findViewById(R.id.et_event_name_detail);
        etEventDescription = (TextView) findViewById(R.id.et_event_description_detail);
        
        // Busca no servidor o Objeto que tem o ID
        ParseUtil.findEventById(key, new GetCallback<Event>() {
            @Override
            public void done(final Event newEvent, final ParseException e) {
                if (e == null) {
                    event = newEvent;
                    setDataFields();
                }
            }
        });
    }

    /**
     * Set all fields of view with name of events fields.
     */
    private void setDataFields() {
        etEventName.setText(event.getName());
        etEventDescription.setText(event.getDescription());
//        etEventInitialDate.setText(ParseUtil.ptbr.format(event
//                .getInitialDate()));
//        etEventFinalDate
//                .setText(ParseUtil.ptbr.format(event.getFinalDate()));
//        etEventInitialTime.setText(Calendar.getInstance().get(Calendar.HOUR_OF_DAY) + ":"
//                + Calendar.getInstance().get(Calendar.MINUTE));
//        etEventFinalTime.setText("23:59");
    }

    /**
     * Sets the status bar color.
     * 
     * @param statusBar the status bar
     * @param color the color
     */
    public final void setStatusBarColor(final View statusBar, final int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // status bar height
            int actionBarHeight = getActionBarHeight();
            int statusBarHeight = getStatusBarHeight();
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
        TypedValue tv = new TypedValue();
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
        int resourceId = getResources().getIdentifier("status_bar_height",
                "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }
}
