package adapter;

import java.util.List;

import entity.event.Event;

import persistence.DatabaseStorage;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

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

        return myView;
    }


}
