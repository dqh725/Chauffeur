package com.chauffr.registration.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;


import com.chauffr.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Wenyue on 13/12/2015.
 */
public class ChauffeurCodeArrayAdapter extends ArrayAdapter<DataPair> {
    private LayoutInflater layoutInflater;
    List<DataPair> mDatapairs;

    private Filter mFilter = new Filter() {
    @Override
    public String convertResultToString(Object resultValue) {
        return ((DataPair)resultValue).getOrigin();
    }

    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        FilterResults results = new FilterResults();

        if (constraint != null) {
            ArrayList<DataPair> suggestions = new ArrayList<DataPair>();
            for (DataPair dataPair : mDatapairs) {
                // Note: change the "contains" to "startsWith" if you only want starting matches
                if (dataPair.getOrigin().toLowerCase().contains(constraint.toString().toLowerCase())||
                        dataPair.getTrimmed().toLowerCase().contains(constraint.toString().toLowerCase())) {
                    suggestions.add(dataPair);
                }
            }
            results.values = suggestions;
            results.count = suggestions.size();
        }

        return results;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {
        clear();
        if (results != null && results.count == 1) {
        // we have filtered results
            addAll((ArrayList<DataPair>) results.values);
        } else {
        // no filter, add entire original list back in
           clear();
        }
        notifyDataSetChanged();
        }
    };

    public ChauffeurCodeArrayAdapter(Context context, int textViewResourceId, List<DataPair> dataPairs) {
        super(context, textViewResourceId, dataPairs);
        // copy all the customers into a master list
        mDatapairs = new ArrayList<DataPair>(dataPairs.size());
        mDatapairs.addAll(dataPairs);
        layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = layoutInflater.inflate(R.layout.custom_drop_down_item, null);
        }

        DataPair dataPair = getItem(position);
        TextView code = (TextView) view.findViewById(R.id.codeLabel);
        code.setText(dataPair.getOrigin());
        return view;
    }

    @Override
    public Filter getFilter() {
        return mFilter;
    }
}