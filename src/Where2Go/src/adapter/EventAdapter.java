package adapter;

import java.util.List;

import br.com.les.where2go.R;
import entity.event.Event;

import persistence.DatabaseStorage;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;

public class EventAdapter extends BaseAdapter {

    private static List<Event> mListEvents;
    @SuppressWarnings("unused")
    private LayoutInflater mInflater;
    private int posicao;
    Context mcontext;
    private DatabaseStorage bdHelper;
    private View parentView;
    private ListView listview;

    /**
     * Class constructor
     */
    public EventAdapter() {
    }

    @SuppressWarnings("static-access")
    public EventAdapter(Context context, List<Event> listEvents,
            DatabaseStorage bdHelper, View parentView) {
        this.mListEvents = listEvents;
        this.mInflater = LayoutInflater.from(context);
        this.mcontext = context;
        this.parentView = parentView;
        this.bdHelper = bdHelper;
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
    public View getView(final int position, View myView, ViewGroup parent) {
    	 LayoutInflater inflater = (LayoutInflater) mcontext
                 .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
         myView = inflater.inflate(R.layout.item_event_adapter, null);
         final Event event = mListEvents.get(position);

         TextView eventName = (TextView) myView.findViewById(R.id.nome);
         eventName.setText(event.getName());

         TextView eventDescription = (TextView) myView
                 .findViewById(R.id.categoria);
         eventDescription.setText(event.getDescription());

         TextView eventValue = (TextView) myView.findViewById(R.id.valor);

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
             Event event = EventAdapter.this.getItem(posicao);

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
                            case (R.id.delete):
                                deleteAlert(event);
                                break;
                            default:
                                break;
                        }
                        return true;
                    }
                });
        popupMenu.show();
    }

    public void editAlert(final Event event) {
        AlertDialog.Builder builder = new AlertDialog.Builder(
                parentView.getContext());

            builder.setTitle("Cancel Event");
            builder.setMessage("Mensagem");
            builder.setPositiveButton("OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface arg0, int arg1) {
                            
                        }
                    });

            builder.setNegativeButton("CANCEL",
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
     * Alert dialog to delete confirmation
     * 
     * @param event
     */
    public void deleteAlert(final Event event) {
        AlertDialog.Builder builder = new AlertDialog.Builder(
                parentView.getContext());
            builder.setTitle("DELETE");
            builder.setMessage("MESSAGE");
            builder.setPositiveButton("OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface arg0, int arg1) {
                        	
                        }
                    });

            builder.setNegativeButton("CANCEL",
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
