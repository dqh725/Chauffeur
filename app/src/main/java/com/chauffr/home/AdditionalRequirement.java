package com.chauffr.home;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.chauffr.API;
import com.chauffr.R;
import com.chauffr.home.adapter.Vehicle;
import com.chauffr.home.adapter.VehicleFragment;
import com.chauffr.home.adapter.FragmentAdapter;
import com.joanzapata.iconify.widget.IconButton;
import com.viewpagerindicator.CirclePageIndicator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class AdditionalRequirement extends AppCompatActivity {


    List<Vehicle> cars = null;
    List<Fragment> fragments=null;
    ViewPager pager = null;

    IconButton back, next, add_baby, minus_baby;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.additional_requirement_activity);

        setupBtn();

        try {
            cars = new GetCarTypeListTask().execute().get();
            pager = (ViewPager)findViewById(R.id.pager);
            fragments = new ArrayList<Fragment>();
            for(Vehicle car: cars){
                fragments.add(VehicleFragment.newInstance(car.getDescription(), car.getSeats() ));
            }

            pager.setAdapter(new FragmentAdapter(getSupportFragmentManager(), fragments));

            CirclePageIndicator circlePageIndicator = (CirclePageIndicator)findViewById(R.id.car_count);
            circlePageIndicator.setViewPager(pager);
            circlePageIndicator.setFillColor(Color.GREEN);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


    }

    private void setupBtn(){
        back = (IconButton)findViewById(R.id.back);
        next = (IconButton)findViewById(R.id.next);
        add_baby = (IconButton)findViewById(R.id.add_seat);
        minus_baby = (IconButton) findViewById(R.id.minus_seat);


        add_baby.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int max = 4;
                TextView baby_seat = (TextView)findViewById(R.id.baby_seat);
                int num = Integer.parseInt(baby_seat.getText().toString());
                if( num < 4 )
                    baby_seat.setText(String.valueOf(num + 1));
            }
        });
        minus_baby.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView baby_seat = (TextView)findViewById(R.id.baby_seat);
                int num = Integer.parseInt(baby_seat.getText().toString());
                if(num>0)
                    baby_seat.setText(String.valueOf(num-1));
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        next.setVisibility(View.GONE);
    }



// asynctask of get all car type of this chauffeur
    private class GetCarTypeListTask extends AsyncTask<Void, Void, List<Vehicle>> {

        private int getChauffeurId(){
            final SharedPreferences mSharedPreference= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            int chauffeurId = 5;
            try {
                JSONObject client = new JSONObject(mSharedPreference.getString("Client", ""));
                chauffeurId = Integer.parseInt(client.getString("ChauffeurId"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return chauffeurId;
        }
        @Override
        protected List<Vehicle> doInBackground(Void... params) {
            int chauffeurId = getChauffeurId();
            JSONArray vehicleListJSON = API.getCarTypeList(chauffeurId);
            List<Vehicle> cars = new ArrayList<Vehicle>();
            if(vehicleListJSON!=null){
                for(int i=0; i<vehicleListJSON.length(); i++){
                    try {
                        JSONObject temp = (JSONObject)(vehicleListJSON.get(i));
                        Log.i("JSON", temp.toString());
                        cars.add(new Vehicle(
                                temp.getString("Description").toString(),
                                Integer.parseInt(temp.getString("Seats")),
                                Float.parseFloat(temp.getString("FareMin")),
                                Float.parseFloat(temp.getString("FareKmsFirst")),
                                Float.parseFloat(temp.getString("FareKmsAfter"))
                        ));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                return cars;
            }
            else
                return null;

        }
    }
}
