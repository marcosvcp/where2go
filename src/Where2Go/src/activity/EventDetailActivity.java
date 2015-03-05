package activity;

import java.util.ArrayList;
import java.util.List;

import persistence.ParseUtil;
import utils.Authenticator;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import br.com.les.where2go.R;

import com.facebook.widget.ProfilePictureView;
import com.parse.GetCallback;
import com.parse.ParseException;

import entity.event.Event;
import entity.user.User;

//import entity.notifications.Notification;

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

    private ImageButton btAccept;
    private ImageView imageEvent;
    private ImageButton btDecline;

    /**
     * Called when the activity is first created.
     *
     * @param savedInstanceState
     *            the saved instance state
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
        btAccept = (ImageButton) findViewById(R.id.bt_acept);
        btDecline = (ImageButton) findViewById(R.id.bt_decline);
        imageEvent = (ImageView) findViewById(R.id.imageView1);
        
        if(Authenticator.getInstance().getLoggedUser() == null){
        	btAccept.setVisibility(View.GONE);
            btDecline.setVisibility(View.GONE);
        }
        
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

        btAccept.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(final View v) {
                final Notification not = event.addParticipant(Authenticator
                        .getInstance().getLoggedUser());
                Toast.makeText(getApplicationContext(),
                        ((entity.notifications.Notification) not).getMessage(),
                        Toast.LENGTH_SHORT).show();

            }
        });

        btDecline.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(final View v) {
                final Notification not = event.removeParticipant(Authenticator
                        .getInstance().getLoggedUser());
                Toast.makeText(getApplicationContext(),
                        ((entity.notifications.Notification) not).getMessage(),
                        Toast.LENGTH_SHORT).show();

            }
        });
        
    }

    /**
     * Set all fields of view with name of events fields.
     */
    private void setDataFields() {
        tvEventName.setText(event.getName());
        tvEventDescription.setText(event.getDescription());
        tvInitialDate.setText(ParseUtil.PT_BR.format(event.getInitialDate()));
        tvFinalDate.setText(ParseUtil.PT_BR.format(event.getFinalDate()));
        tvCapacity.setText(String.valueOf(event.getCapacity()));
        tvStatus.setText(event.getState());
        tvPrice.setText(String.valueOf(event.getPrice()));
        tvOutfit.setText(event.getOutfit());
        tvNotes.setText(event.getNote());
        tvOwner.setText(event.getOwnerName());
        tvCreatedAt.setText(ParseUtil.PT_BR.format(event.getCreatedAt()));
        if (event.getPhoto() != null) {
            try {
                Bitmap bmp = BitmapFactory
                        .decodeByteArray(event.getPhoto().getData(), 0, event.getPhoto().getData().length);
                imageEvent.setImageBitmap(bmp);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        loadParticipantsPictures();

    }
    
    private void loadParticipantsPictures(){
    	List<ProfilePictureView> pictures = new ArrayList<ProfilePictureView>();
    	pictures.add((ProfilePictureView) findViewById(R.id.image_facebook_1));
    	pictures.add((ProfilePictureView) findViewById(R.id.image_facebook_2));
    	pictures.add((ProfilePictureView) findViewById(R.id.image_facebook_3));
    	pictures.add((ProfilePictureView) findViewById(R.id.image_facebook_4));
    	pictures.add((ProfilePictureView) findViewById(R.id.image_facebook_5));
    	
    	List<User> participants = event.getParticipants();
    	for (int i = 0; i < 4 ; i ++){
    		if(participants.size() > i){
    			pictures.get(i).setVisibility(View.VISIBLE);
    			pictures.get(i).setProfileId(participants.get(i).getFacebookId());
    		}
    	}
    	
    	if(participants.size() > 5){
    		TextView mp = (TextView)findViewById(R.id.tv_more_participants);
    		mp.setVisibility(View.VISIBLE);
    		mp.setText("+ " + (participants.size() - 5));
    	}
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
	             final Intent intent = new Intent(getApplicationContext(),
	             FacebookFriendsActivity.class);
	             intent.putExtra("EventId", event.getObjectId());
	             startActivity(intent);
	        	return true;
            case R.id.action_notification:
                createNotification(getWindow().getDecorView().findViewById(
                        android.R.id.content));
                return true;
            
            default:
                return super.onOptionsItemSelected(item);
        }
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

    public void createNotification(final View view) {
        // Prepare intent which is triggered if the
        // notification is selected
        final Intent intent = new Intent(this,
                NotificationReceiverActivity.class);
        final PendingIntent pIntent = PendingIntent.getActivity(this, 0,
                intent, 0);

        // Build notification
        // Actions are just fake
        final Notification noti = new Notification.Builder(this)
                .setContentTitle("New invite for " + event.getName())
                .setContentText("Where2go")
                .setSmallIcon(R.drawable.ic_launcher).setContentIntent(pIntent)
                .addAction(R.drawable.ic_action_accept, "Accept", pIntent)
                .addAction(R.drawable.ic_action_cancel, "Ignore", pIntent)
                .addAction(R.drawable.ic_action_discard, "Cancel", pIntent)
                .build();
        final NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        // hide the notification after its selected
        noti.flags |= Notification.FLAG_AUTO_CANCEL;

        notificationManager.notify(0, noti);

    }
}
