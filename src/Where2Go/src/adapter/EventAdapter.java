package adapter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import persistence.ParseUtil;
import utils.Authenticator;
import utils.ImageLoader;
import activity.EditEventActivity;
import activity.EventDetailActivity;
import activity.FacebookFriendsActivity;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.PopupMenu.OnMenuItemClickListener;
import android.widget.TextView;
import br.com.les.where2go.R;

import com.parse.GetDataCallback;
import com.parse.ParseException;

import entity.event.Event;
import entity.event.EventCanceled;

/**
 * The Class EventAdapter.
 */
public class EventAdapter extends BaseAdapter implements Serializable {

    /**
     * The Constant serialVersionUID.
     */
    private static final long serialVersionUID = 1L;
    public static final String FINALIZE = "Finalize";
    public static final String EVENT_ID = "event_id";
    public static final String TODOS = "Todos";

    /**
     * The m list events.
     */
    private transient List<Event> mListEvents;

    /**
     * The m inflater.
     */
    private transient final LayoutInflater mInflater;

    /**
     * The mcontext.
     */
    private transient final Context mcontext;

    /**
     * The parent view.
     */
    private transient final View parentView;

    /**
     * The listview.
     */
    private transient ListView listview;

    /**
     * The parent activity.
     */
    private transient Activity parentActivity;
    
    /**
     * The image loader.
     */
    private ImageLoader imgLoader;

    /**
     * Instantiates a new event adapter.
     *
     * @param context
     *            the context
     * @param listEvents
     *            the list events
     * @param view
     *            the parent view
     */
    public EventAdapter(final Context context, final List<Event> listEvents,
            final View view) {
        mListEvents = listEvents;
        mInflater = LayoutInflater.from(context);
        mcontext = context;
        parentView = view;
    }

    /**
     * Instantiates a new event adapter.
     *
     * @param context
     *            the context
     * @param listEvents
     *            the list events
     * @param view
     *            the parent view
     * @param filter
     *            the filter
     * @param activity
     *            the parent activity
     */
    public EventAdapter(final Context context, final List<Event> listEvents,
            final View view, final String filter, final Activity activity) {
        if (filter.equals(TODOS)) {
            mListEvents = listEvents;
        } else {
            final List<Event> newListEvents = new ArrayList<Event>();
            for (int i = 0; i < listEvents.size(); i++) {
                final Event tempEvent = listEvents.get(i);
                final List<String> tempEventTags = tempEvent.getTags();
                if (tempEventTags.contains(filter)
                        || tempEvent.isOwner(Authenticator.getInstance()
                                .getLoggedUser())) {
                    newListEvents.add(tempEvent);
                }
            }
            mListEvents = newListEvents;
        }
        mInflater = LayoutInflater.from(context);
        mcontext = context;
        parentView = view;
        parentActivity = activity;
    }

    /*
     * (non-Javadoc)
     *
     * @see android.widget.Adapter#getCount()
     */
    @Override
    public final int getCount() {
        return mListEvents.size();
    }

    /*
     * (non-Javadoc)
     *
     * @see android.widget.Adapter#getItem(int)
     */
    @Override
    public final Event getItem(final int position) {
        return mListEvents.get(position);
    }

    /**
     * Get id of item selected.
     *
     * @param index
     *            the index
     * @return the item id
     */
    @Override
    public final long getItemId(final int index) {
        return index;
    }

    /*
     * (non-Javadoc)
     *
     * @see android.widget.Adapter#getView(int, android.view.View,
     * android.view.ViewGroup)
     */
    @Override
    public final View getView(final int position, final View myView,
            final ViewGroup viewGroup) {
        final View view = mInflater.inflate(R.layout.item_event_adapter, null);

        final Event event = mListEvents.get(position);
        final LinearLayout card = (LinearLayout) view.findViewById(R.id.card);

        final TextView eventName = (TextView) view
                .findViewById(R.id.event_name);
        eventName.setText(event.getName());

        final TextView eventInitialDate = (TextView) view
                .findViewById(R.id.event_initial_date);
        eventInitialDate
        .setText(ParseUtil.PT_BR.format(event.getInitialDate()));

        ImageButton thumbnail = (ImageButton) view.findViewById(R.id.photo);
        imgLoader = new ImageLoader(mcontext);
        String url;
        if(event.getPhoto() != null){
        	url = event.getPhoto().getUrl().toString();
        }else{
        	url = "http://www.hdpaperz.com/wallpaper/original/free-hd-wallpaper.jpg";
        }
        imgLoader.DisplayImage(url, thumbnail);
        Bitmap bmp = imgLoader.getBitmap(url);
        int pixel = bmp.getPixel(bmp.getWidth() / 2, bmp.getHeight() / 2);
        Log.d("pixel", "" + pixel);
        card.setBackgroundColor(Color.argb(255, Color.red(pixel),
                Color.green(pixel), Color.blue(pixel)));
        
       /* final ImageButton thumbnail = (ImageButton) view
                .findViewById(R.id.photo);

        if (event.getImageEvent() == null && event.getPhoto() != null) {
            event.getPhoto().getDataInBackground(new GetDataCallback() {
                @Override
                public void done(byte[] bytes, ParseException e) {
                    final Bitmap bitmap = ((BitmapDrawable) thumbnail.getDrawable())
                            .getBitmap();
                    int pixel = bitmap.getPixel(bitmap.getWidth() / 2,
                            bitmap.getHeight() / 2);
                    final Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0,
                            bytes.length);
                    thumbnail.setImageBitmap(bmp);
                    event.setImageEvent(bmp);
                    pixel = bmp.getPixel(bmp.getWidth() / 2, bmp.getHeight() / 2);
                    card.setBackgroundColor(Color.argb(255, Color.red(pixel),
                            Color.green(pixel), Color.blue(pixel)));
                }
            });
        }else if(event.getImageEvent() != null) {
            final Bitmap bitmap = event.getImageEvent();
            int pixel = bitmap.getPixel(bitmap.getWidth() / 2,
                    bitmap.getHeight() / 2);
            thumbnail.setImageBitmap(bitmap);
            pixel = bitmap.getPixel(bitmap.getWidth() / 2, bitmap.getHeight() / 2);
            card.setBackgroundColor(Color.argb(255, Color.red(pixel),
                    Color.green(pixel), Color.blue(pixel)));
        }*/

        listview = (ListView) parentView.findViewById(R.id.listViewEvents);
        listview.setClickable(true);

        view.setOnLongClickListener(new OnLongClickListener() {

            @Override
            public boolean onLongClick(final View v) {
                if (Authenticator.getInstance().getLoggedUser() != null) {
                    showPopupMenu(v, event);
                }
                return false;
            }
        });

        view.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(final View v) {
                showDetail(event);
            }
        });

        final ImageButton btOptions = (ImageButton) view
                .findViewById(R.id.bt_options);
        btOptions.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(final View v) {
                if (Authenticator.getInstance().getLoggedUser() != null) {
                    showPopupMenu(v, event);
                }
            }
        });
        return view;
    }

    /**
     * Shows popup menu with delete and edit options for a given event in the
     * list.
     *
     * @param v
     *            - view refers the screen listing
     * @param event
     *            selected
     */
    private void showPopupMenu(final View v, final Event event) {
        final PopupMenu popupMenu = new PopupMenu(mcontext, v);
        if (event.isOwner(Authenticator.getInstance().getLoggedUser())) {
            popupMenu.getMenuInflater().inflate(R.menu.event_options,
                    popupMenu.getMenu());
        } else {
            popupMenu.getMenuInflater().inflate(R.menu.not_owner_event_options,
                    popupMenu.getMenu());
        }
        popupMenu.setOnMenuItemClickListener(new OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(final MenuItem item) {
                switch (item.getItemId()) {
                case R.id.details:
                    showDetail(event);
                    return true;
                case R.id.invite:
                    final Intent intent = new Intent(parentActivity,
                            FacebookFriendsActivity.class);
                    intent.putExtra("EventId", event.getObjectId());
                    parentActivity.startActivity(intent);
                    return true;
                case R.id.edit:
                    editAlert(event);
                    return true;
                case R.id.cancel:
                    cancelAlert(event);
                    break;
                default:
                    break;
                }
                return true;
            }
        });
        popupMenu.show();
    }

    /**
     * Redirects to EventDetail activity
     *
     * @param event
     *            the event
     */
    public final void showDetail(final Event event) {
        final Intent intentDetail = new Intent(parentView.getContext(),
                EventDetailActivity.class);
        intentDetail.putExtra(EVENT_ID, event.getObjectId());
        parentView.getContext().startActivity(intentDetail);
    }

    /**
     * Alert dialog to edit event.
     *
     * @param event
     *            the event
     */
    public final void editAlert(final Event event) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(
                parentView.getContext());

        builder.setTitle(parentView.getResources().getString(
                R.string.edit_alert_title));
        builder.setMessage(parentView.getResources().getString(
                R.string.edit_alert_message));
        builder.setPositiveButton(
                parentView.getResources().getString(
                        R.string.edit_alert_positive),
                        new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface arg0,
                            final int arg1) {
                        final Intent intent = new Intent(parentView
                                .getContext(), EditEventActivity.class);
                        intent.putExtra(EVENT_ID, event.getObjectId());
                        parentView.getContext().startActivity(intent);
                    }
                });

        builder.setNegativeButton(
                parentView.getResources().getString(
                        R.string.edit_alert_negative),
                        new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface arg0,
                            final int arg1) {
                        try {
                        } catch (final Exception e) {
                            Log.e(FINALIZE, e.getMessage());
                        }
                    }
                });

        final AlertDialog alert = builder.create();
        alert.show();
    }

    /**
     * Alert dialog to cancel event.
     *
     * @param event
     *            the event
     */
    public final void cancelAlert(final Event event) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(
                parentView.getContext());
        builder.setTitle(parentView.getResources().getString(
                R.string.cancel_alert_title));
        builder.setMessage(parentView.getResources().getString(
                R.string.cancel_alert_message));
        builder.setPositiveButton(
                parentView.getResources().getString(
                        R.string.cancel_alert_positive),
                        new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface arg0,
                            final int arg1) {
                        event.setState(new EventCanceled().getName());
                        ParseUtil.saveEvent(event);
                        notifyDataSetChanged();
                    }
                });
        builder.setNegativeButton(
                parentView.getResources().getString(
                        R.string.cancel_alert_negative),
                        new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface arg0,
                            final int arg1) {
                        try {
                        } catch (final Exception e) {
                            Log.e(FINALIZE, e.getMessage());
                        }
                    }
                });

        final AlertDialog alert = builder.create();
        alert.show();
    }
}
