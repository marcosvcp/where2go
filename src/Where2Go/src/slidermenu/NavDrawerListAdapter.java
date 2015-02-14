/**
 * Copyright (C) 2014 Embedded Systems and Pervasive Computing Lab - UFCG
 * All rights reserved.
 */

package slidermenu;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import br.com.les.where2go.R;

import java.util.ArrayList;

/**
 * The Class NavDrawerListAdapter.
 */
public class NavDrawerListAdapter extends BaseAdapter {

    /** The context. */
    private final Context mContext;

    /** The nav drawer items. */
    private final ArrayList<NavDrawerItem> mNavDrawerItems;

    /**
     * Instantiates a new nav drawer list adapter.
     * 
     * @param context the context
     * @param navDrawerItems the nav drawer items
     */
    public NavDrawerListAdapter(final Context context,
            final ArrayList<NavDrawerItem> navDrawerItems) {
        mContext = context;
        mNavDrawerItems = navDrawerItems;
    }

    /*
     * (non-Javadoc)
     * 
     * @see android.widget.Adapter#getCount()
     */
    @Override
    public final int getCount() {
        return mNavDrawerItems.size();
    }

    /*
     * (non-Javadoc)
     * 
     * @see android.widget.Adapter#getItem(int)
     */
    @Override
    public final Object getItem(final int position) {
        return mNavDrawerItems.get(position);
    }

    /*
     * (non-Javadoc)
     * 
     * @see android.widget.Adapter#getItemId(int)
     */
    @Override
    public final long getItemId(final int position) {
        return position;
    }

    /*
     * (non-Javadoc)
     * 
     * @see android.widget.Adapter#getView(int, android.view.View,
     * android.view.ViewGroup)
     */
    @Override
    public final View getView(final int position, View convertView, final ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) mContext
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.drawer_list_item, null);
        }
        TextView txtTitle = (TextView) convertView.findViewById(R.id.title);
        txtTitle.setText(mNavDrawerItems.get(position).getTitle());
        return convertView;
    }

}
