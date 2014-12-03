package adapter;

import java.util.List;

import activity.EditEventActivity;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import br.com.les.where2go.R;
import entity.event.Event;

public class EventAdapter extends BaseAdapter {

    private static List<Event> mListEvents;
    private LayoutInflater mInflater;
    private int posicao;
    Context mcontext;
    private Activity parentActivity;
    private ListView listview;

    /**
     * Class constructor
     */
    public EventAdapter(Context context, List<Event> listEvent) {
    	mListEvents = listEvent;
    	mInflater = LayoutInflater.from(context);
    	mcontext = context;
    }
    
    public EventAdapter(Context context, List<Event> listEvents,Activity parentActivity) {
        this.mListEvents = listEvents;
        this.mInflater = LayoutInflater.from(context);
        this.mcontext = context;
        this.parentActivity = parentActivity;
    }

    @Override
    public int getCount() {
        return mListEvents.size();
    }

    @Override
    public Event getItem(int position) {
        return mListEvents.get(position);
    }

    /**
     * Get id of item selected
     */
    @Override
    public long getItemId(int index) {
        return index;
    }

    @Override
    public View getView(final int position, View myView, ViewGroup viewGroup) {
         myView = mInflater.inflate(R.layout.item_event_adapter, null);
         
         final Event event = mListEvents.get(position);

         TextView eventName = (TextView) myView.findViewById(R.id.event_name);
         eventName.setText(event.getName());

         TextView eventDescription = (TextView) myView
                 .findViewById(R.id.event_category);
         
         TextView eventStatus = (TextView) myView
                 .findViewById(R.id.event_status);
         eventStatus.setText(event.getStatus());
         
         eventDescription.setText(event.getDescription());

         TextView eventValue = (TextView) myView.findViewById(R.id.event_price);

         listview = (ListView) parentActivity.findViewById(R.id.listViewEvents);
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
     * list
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
     * Alert dialog to edit event
     * 
     * @param event
     */
    public void editAlert(final Event event) {
        AlertDialog.Builder builder = new AlertDialog.Builder(
        		parentActivity);

            builder.setTitle(parentActivity.getResources().getString(R.string.edit_alert_title));
            builder.setMessage(parentActivity.getResources().getString(R.string.edit_alert_message));
            builder.setPositiveButton(parentActivity.getResources().getString(R.string.edit_alert_positive),
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface arg0, int arg1) {
                            Intent intent = new Intent(parentActivity, EditEventActivity.class);
                            intent.putExtra("event_id", event.getId());
                            parentActivity.startActivity(intent);
                        }
                    });

            builder.setNegativeButton(parentActivity.getResources().getString(R.string.edit_alert_negative),
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
     * Alert dialog to cancel event
     * 
     * @param event
     */
    public void cancelAlert(final Event event) {
        AlertDialog.Builder builder = new AlertDialog.Builder(
        		parentActivity);
            builder.setTitle(parentActivity.getResources().getString(R.string.cancel_alert_title));
            builder.setMessage(parentActivity.getResources().getString(R.string.cancel_alert_message));
            builder.setPositiveButton(parentActivity.getResources().getString(R.string.cancel_alert_positive),
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface arg0, int arg1) {
                        event.setStatus("Canceled");
                        notifyDataSetChanged();
                        }
                    });
            builder.setNegativeButton(parentActivity.getResources().getString(R.string.cancel_alert_negative),
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
