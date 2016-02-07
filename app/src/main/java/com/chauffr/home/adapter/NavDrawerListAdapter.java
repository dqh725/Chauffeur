package com.chauffr.home.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.chauffr.R;
import com.joanzapata.iconify.widget.IconButton;
import com.joanzapata.iconify.widget.IconTextView;

import java.util.ArrayList;

/**
 * Created by Wenyue on 21/12/2015.
 */
public class NavDrawerListAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<NavDrawerItem> navDrawerItems;

    public NavDrawerListAdapter(Context context, ArrayList<NavDrawerItem> navDrawerItems){
        this.context = context;
        this.navDrawerItems = navDrawerItems;
    }

    @Override
    public int getCount() {
        if( navDrawerItems!=null)
            return navDrawerItems.size();
        else
            return -1;
    }

    @Override
    public Object getItem(int position) {
        return navDrawerItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater)
                    context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.drawer_list_item, null);
        }

        IconTextView drawerIcon = (IconTextView) convertView.findViewById(R.id.drawer_item_icon);
        TextView drawerText = (TextView) convertView.findViewById(R.id.drawer_item_text);
        drawerIcon.setText(navDrawerItems.get(position).getIcon());
        drawerText.setText(navDrawerItems.get(position).getText());

        return convertView;
    }

}