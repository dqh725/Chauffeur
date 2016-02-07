package com.chauffr.home.adapter;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chauffr.R;

/**
 * Created by Wenyue on 2/02/2016.
 */
public class VehicleFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.cartype_view, container, false);

        TextView description = (TextView) v.findViewById(R.id.cartype);
        description.setText(getArguments().getString("description"));
        TextView seat = (TextView)v.findViewById(R.id.seat_number);
        seat.setText(getArguments().getString("seat"));

        return v;
    }

    public static VehicleFragment newInstance(String brief, int seat) {

        VehicleFragment f = new VehicleFragment();
        // Supply brief as an argument.
        Bundle b = new Bundle();
        b.putString("description", brief);
        b.putString("seat", String.valueOf(seat)+" seats");

        f.setArguments(b);

        return f;
    }



}
