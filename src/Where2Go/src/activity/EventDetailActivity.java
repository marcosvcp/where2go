
package activity;

import java.util.List;

import persistence.ParseUtil;
import android.R.color;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
    private TextView tvEventName;
    
    /** The event desscription. */
    private TextView tvEventDescription;

    /** The event initialDate. */
    private TextView tvInitialDate;
    
    /** The event final date. */
    private TextView tvFinalDate;
    
    /** The event initial hour. */
    private TextView tvInitialHour;
    
    /** The event final hour. */
    private TextView tvFinalHour;
    
    /** The event capacity. */
    private TextView tvCapacity;
    
    /** The event status. */
    private TextView tvStatus;
    
    /** The event price. */
    private TextView tvPrice;
    
    /** The event outfit. */
    private TextView tvOutfit;
    
    /** The event owner. */
    private TextView tvOwner;
    
    /** The event creation date. */
    private TextView tvCreatedAt;
    
    /** The event notes. */
    private TextView tvNotes;
    
    /**
     * Called when the activity is first created.
     * 
     * @param savedInstanceState the saved instance state
     */
    @Override
    public final void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);
        setStatusBarColor(findViewById(R.id.statusBarBackground),
                getResources().getColor(R.color.status_bar));        
        final Bundle data = getIntent().getExtras();
        final String key = data.getString("event_id");
        
        tvEventName = (TextView) findViewById(R.id.tv_event_name_detail);
        tvEventDescription = (TextView) findViewById(R.id.tv_event_description_detail);
        tvInitialDate = (TextView) findViewById(R.id.tv_data_inicio);
        tvFinalDate = (TextView) findViewById(R.id.tv_detail_final_date);
        tvInitialHour = (TextView) findViewById(R.id.tv_hora_inicio);
        tvFinalHour = (TextView) findViewById(R.id.tv_detail_final_hour);
        
        tvCapacity = (TextView) findViewById(R.id.tv_detail_capacity);
        tvStatus = (TextView) findViewById(R.id.tv_detail_status);
        tvPrice = (TextView) findViewById(R.id.tv_detail_price);
        tvOutfit = (TextView) findViewById(R.id.tv_detail_outfit);
        tvOwner = (TextView) findViewById(R.id.tv_detail_owner);
        tvCreatedAt = (TextView) findViewById(R.id.tv_detail_created_at);
        tvNotes = (TextView) findViewById(R.id.tv_detail_notes);
        
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
        tvEventName.setText(event.getName());
        tvEventDescription.setText(event.getDescription());
        tvInitialDate.setText(ParseUtil.ptbr.format(event.getInitialDate()));
        tvFinalDate.setText(ParseUtil.ptbr.format(event.getFinalDate()));
        tvCapacity.setText(String.valueOf(event.getCapacity()));
        tvStatus.setText(event.getState());
        tvPrice.setText(String.valueOf(event.getPrice()));
        tvOutfit.setText(event.getOutfit());
        tvNotes.setText(event.getNote());
        tvOwner.setText(event.getOwnerName());
        tvCreatedAt.setText(ParseUtil.ptbr.format(event.getCreatedAt()));
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
     */
    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        final MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.event_detail_actions, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /*
     * (non-Javadoc)
     * 
     * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
     */
    @Override
    public final boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_invite:
                final Intent intent = new Intent(
                        getApplicationContext(),
                        FacebookFriendsActivity.class);
                intent.putExtra("EventId", event.getObjectId());
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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
