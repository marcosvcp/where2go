
package adapter;

import activity.EditEventActivity;
import activity.FacebookFriendsActivity;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
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
import android.widget.TextView;
import android.widget.Toast;
import br.com.les.where2go.R;
import entity.event.Event;
import entity.event.EventCanceled;
import persistence.ParseUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * The Class EventAdapter.
 */
public class EventAdapter extends BaseAdapter implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The m list events. */
    private List<Event> mListEvents;

    /** The m inflater. */
    private final LayoutInflater mInflater;

    /** The mcontext. */
    private final Context mcontext;

    /** The parent view. */
    private final View parentView;

    /** The listview. */
    private ListView listview;

    /** The parent activity. */
    private Activity parentActivity;

    /**
     * Instantiates a new event adapter.
     * 
     * @param context the context
     * @param listEvents the list events
     * @param view the parent view
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
     * @param context the context
     * @param listEvents the list events
     * @param view the parent view
     * @param filter the filter
     * @param activity the parent activity
     */
    public EventAdapter(final Context context, final List<Event> listEvents,
            final View view, final String filter,
            final Activity activity) {
        if (filter.equals("Todos")) {
            mListEvents = listEvents;
        } else {
            final List<Event> newListEvents = new ArrayList<Event>();
            for (int i = 0; i < listEvents.size(); i++) {
                final Event tempEvent = listEvents.get(i);
                final ArrayList<String> tempEventTags = tempEvent.getTags();
                if (tempEventTags.contains(filter)) {
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
     * @param index the index
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
    public final View getView(final int position, View myView,
            final ViewGroup viewGroup) {
        myView = mInflater.inflate(R.layout.item_event_adapter, null);

        final Event event = mListEvents.get(position);
        final LinearLayout card = (LinearLayout) myView.findViewById(R.id.card);

        final TextView eventName = (TextView) myView
                .findViewById(R.id.event_name);
        eventName.setText(event.getName());

        final TextView eventDescription = (TextView) myView
                .findViewById(R.id.event_category);

        final TextView eventStatus = (TextView) myView
                .findViewById(R.id.event_status);
        eventStatus.setText(event.getState());

        final ImageButton thumbnail = (ImageButton) myView
                .findViewById(R.id.photo);
        final Bitmap bitmap = ((BitmapDrawable) thumbnail.getDrawable())
                .getBitmap();
        final int pixel = bitmap.getPixel(bitmap.getWidth() / 2,
                bitmap.getHeight() / 2);

        card.setBackgroundColor(Color.argb(255, Color.red(pixel),
                Color.green(pixel), Color.blue(pixel)));

        eventDescription.setText(event.getDescription());

        final TextView eventValue = (TextView) myView
                .findViewById(R.id.event_price);

        listview = (ListView) parentView.findViewById(R.id.listViewEvents);
        listview.setClickable(true);

        myView.setOnLongClickListener(new OnLongClickListener() {

            @Override
            public boolean onLongClick(final View v) {
                // SE QUISER IMPLEMENTAR O LONG CLICK

                return false;
            }

        });
        
        myView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(final View v) {
				Toast.makeText(mcontext, "CLICOU", Toast.LENGTH_LONG).show();
            }
        });
        
        final ImageButton btOptions = (ImageButton) myView
                .findViewById(R.id.bt_options);
        btOptions.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(final View v) {
                showPopupMenu(v, event);
            }
        });

        return myView;
    }

    /**
     * Shows popup menu with delete and edit options for a given event in the
     * list.
     * 
     * @param v - view refers the screen listing
     * @param event selected
     */
    private void showPopupMenu(final View v, final Event event) {
        final PopupMenu popupMenu = new PopupMenu(mcontext, v);
        popupMenu.getMenuInflater().inflate(R.menu.event_options,
                popupMenu.getMenu());
        popupMenu
                .setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(final MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.invite:
                                final Intent intent = new Intent(
                                        parentActivity,
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
     * Alert dialog to edit event.
     * 
     * @param event the event
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
                        intent.putExtra("event_id", event.getObjectId());
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
                            finalize();
                        } catch (final Throwable e) {
                            e.printStackTrace();
                        }
                    }
                });

        final AlertDialog alert = builder.create();
        alert.show();
    }

    /**
     * Alert dialog to cancel event.
     * 
     * @param event the event
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
                            finalize();
                        } catch (final Throwable e) {
                            e.printStackTrace();
                        }
                    }
                });

        final AlertDialog alert = builder.create();
        alert.show();
    }
}
