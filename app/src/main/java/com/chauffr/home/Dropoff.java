package com.chauffr.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

import com.chauffr.R;
import com.chauffr.address.AirportAddress;
import com.chauffr.address.HomeAddress;
import com.chauffr.address.OtherAddress;
import com.chauffr.address.WorkAddress;

import org.json.JSONArray;
import org.json.JSONObject;

public class Dropoff extends AppCompatActivity {

    LinearLayout selectedPickup;

    String fromPlace = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dropoff_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        String disableType = getIntent().getExtras().getString("disable");
        switch (disableType){
            case "home":
                selectedPickup = (LinearLayout)findViewById(R.id.home_group);
                break;
            case "work":
                selectedPickup = (LinearLayout)findViewById(R.id.work_group);
                break;
            case "airport":
                selectedPickup = (LinearLayout)findViewById(R.id.airport_group);
                break;
            case "other":
                selectedPickup = (LinearLayout)findViewById(R.id.other_group);
                break;
            default:
        }

        //pickup is selected, set a alpha cove and disable the button for drop off
        selectedPickup.setAlpha((float) 0.4);
        for(int i = 0; i<selectedPickup.getChildCount(); i++){
            View view = selectedPickup.getChildAt(i);
            view.setEnabled(false); // Or whatever you want to do with the view.
        }

    }

    //specify the type is "dropoff", the next button goto the time picker
    private void prepareIntent(Intent intent){
        intent.putExtra("pickOrDrop", "drop");
        startActivity(intent);
    }
    public void openHomeAddress(View view){
        Intent intent = new Intent(this, HomeAddress.class);
        prepareIntent(intent);
    }

    public void openWorkAddress(View view){
        Intent intent = new Intent(this, WorkAddress.class);
        prepareIntent(intent);
    }
    public void openAirportAddress(View view){
        Intent intent = new Intent(this, AirportAddress.class);
        prepareIntent(intent);
    }
    public void openOtherAddress(View view){
        Intent intent = new Intent(this, OtherAddress.class);
        prepareIntent(intent);
    }

}
