package adapter;

import java.util.ArrayList;
import java.util.List;

import persistence.ParseUtil;
import activity.EditEventActivity;
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
import android.webkit.WebView.FindListener;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import br.com.les.where2go.R;
import entity.event.Event;
import entity.event.EventCanceled;

/**
 * The Class EventAdapter.
 */
public class EventAdapter extends BaseAdapter {

    /** The m list events. */
    private  List<Event> mListEvents;
    
    /** The m inflater. */
    private LayoutInflater mInflater;
    
    /** The posicao. */
    private int posicao;
    
    /** The mcontext. */
    Context mcontext;
    
    /** The parent view. */
    private View parentView;
    
    /** The listview. */
    private ListView listview;

    /**
     * Instantiates a new event adapter.
     *
     * @param context the context
     * @param listEvents the list events
     * @param parentView the parent view
     */
    public EventAdapter(Context context, List<Event> listEvents,View parentView) {
        this.mListEvents = listEvents;
        this.mInflater = LayoutInflater.from(context);
        this.mcontext = context;
        this.parentView = parentView;
    }
    
    /**
     * Instantiates a new event adapter.
     *
     * @param context the context
     * @param listEvents the list events
     * @param parentView the parent view
     * @param filter the filter
     */
    public EventAdapter(Context context, List<Event> listEvents,View parentView, String filter) {
    	if (filter.equals("Todos")) {
    		this.mListEvents = listEvents;
    	} else {
        	List<Event> newListEvents = new ArrayList<Event>();
    		for (int i = 0; i < listEvents.size(); i++) {
        		Event tempEvent = listEvents.get(i);
        		if (tempEvent.getName().equals(filter)) {
        			newListEvents.add(tempEvent);
        		}
    		}
    		this.mListEvents = newListEvents;
    	}
        this.mInflater = LayoutInflater.from(context);
        this.mcontext = context;
        this.parentView = parentView;
    }

    /* (non-Javadoc)
     * @see android.widget.Adapter#getCount()
     */
    @Override
    public int getCount() {
        return mListEvents.size();
    }

    /* (non-Javadoc)
     * @see android.widget.Adapter#getItem(int)
     */
    @Override
    public Event getItem(int position) {
        return mListEvents.get(position);
    }

    /**
     * Get id of item selected.
     *
     * @param index the index
     * @return the item id
     */
    @Override
    public long getItemId(int index) {
        return index;
    }

    /* (non-Javadoc)
     * @see android.widget.Adapter#getView(int, android.view.View, android.view.ViewGroup)
     */
    @Override
    public View getView(final int position, View myView, ViewGroup viewGroup) {
         myView = mInflater.inflate(R.layout.item_event_adapter, null);
         
         final Event event = mListEvents.get(position);

         TextView eventName = (TextView) myView.findViewById(R.id.event_name);
         eventName.setText(event.getName());

         TextView eventDescription = (TextView) myView.findViewById(R.id.event_category);
         
         TextView eventStatus = (TextView) myView.findViewById(R.id.event_status);
         eventStatus.setText(event.getState());
         
         ImageButton thumbnail = (ImageButton) myView.findViewById(R.id.photo);
         Bitmap bitmap = ((BitmapDrawable) thumbnail.getDrawable()).getBitmap();
         int pixel = bitmap.getPixel(bitmap.getWidth()/2,bitmap.getHeight()/2);
         
         LinearLayout card = (LinearLayout) myView.findViewById(R.id.card);
         card.setBackgroundColor(Color.argb(255, Color.red(pixel), Color.green(pixel), Color.blue(pixel)));
         
         eventDescription.setText(event.getDescription());

         TextView eventValue = (TextView) myView.findViewById(R.id.event_price);

         listview = (ListView) parentView.findViewById(R.id.listViewEvents);
         listview.setClickable(true);

         myView.setOnLongClickListener(new OnLongClickListener() {

             @Override
             public boolean onLongClick(View v) {
                 //SE QUISER IMPLEMENTAR O LONG CLICK

                 return false;
             }

         });
         
         ImageButton btOptions = (ImageButton) myView.findViewById(R.id.bt_options);
         btOptions.setOnClickListener(new OnClickListener() {
             @Override
             public void onClick(View v) {
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
    private void showPopupMenu(View v, final Event event) {
        PopupMenu popupMenu = new PopupMenu(mcontext, v);
        popupMenu.getMenuInflater().inflate(R.menu.event_options,
                popupMenu.getMenu());
        popupMenu
                .setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case (R.id.edit):
                                editAlert(event);
                                return true;
                            case (R.id.cancel):
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
    public void editAlert(final Event event) {
        AlertDialog.Builder builder = new AlertDialog.Builder(
        		parentView.getContext());

            builder.setTitle(parentView.getResources().getString(R.string.edit_alert_title));
            builder.setMessage(parentView.getResources().getString(R.string.edit_alert_message));
            builder.setPositiveButton(parentView.getResources().getString(R.string.edit_alert_positive),
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface arg0, int arg1) {
                            Intent intent = new Intent(parentView.getContext(), EditEventActivity.class);
                            intent.putExtra("event_id", event.getObjectId());
                            parentView.getContext().startActivity(intent);
                        }
                    });

            builder.setNegativeButton(parentView.getResources().getString(R.string.edit_alert_negative),
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface arg0, int arg1) {
                            try {
                                finalize();
                            } catch (Throwable e) {
                                e.printStackTrace();
                            }
                        }
                    });

            AlertDialog alert = builder.create();
            alert.show();
    }
    
    /**
     * Alert dialog to cancel event.
     *
     * @param event the event
     */
    public void cancelAlert(final Event event) {
        AlertDialog.Builder builder = new AlertDialog.Builder(
        		parentView.getContext());
            builder.setTitle(parentView.getResources().getString(R.string.cancel_alert_title));
            builder.setMessage(parentView.getResources().getString(R.string.cancel_alert_message));
            builder.setPositiveButton(parentView.getResources().getString(R.string.cancel_alert_positive),
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface arg0, int arg1) {
                        	event.setState(new EventCanceled().getName());
                        	ParseUtil.saveEvent(event);
                        	notifyDataSetChanged();
                        }
                    });
            builder.setNegativeButton(parentView.getResources().getString(R.string.cancel_alert_negative),
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface arg0, int arg1) {
                            try {
                                finalize();
                            } catch (Throwable e) {
                                e.printStackTrace();
                            }
                        }
                    });

            AlertDialog alert = builder.create();
            alert.show();
    }
}
