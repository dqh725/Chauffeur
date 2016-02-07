package com.chauffr;

import android.content.Context;
import android.content.Intent;

import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.chauffr.home.Login;
import com.chauffr.home.Pickup;
import com.chauffr.registration.Connecting;

import org.json.JSONObject;

import java.util.concurrent.ExecutionException;


public class Entry extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.entry_layout);
    }

    public void openConnectingActivity(View view) {
        Intent intent = new Intent(this, Connecting.class);
        startActivity(intent);
    }

    public void openLoginActivity(View view) {
        Intent intent = new Intent(this, Pickup.class);
        startActivity(intent);

    }

    private class GetAddressDetail extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... params) {
            GeoCoder gc = new GeoCoder(params[0]);
            String test = gc.getCountry(GeoCoder.LONG_NAME);
            Log.i("TAG", gc.getCountry(0));
            Log.i("TAG", gc.getLocality(0));
            Log.i("TAG", gc.getPostalCode(0));
            Log.i("TAG", gc.getRoute(0));
            Log.i("TAG", gc.getStreetNumber(0));
            Log.i("TAG", gc.getState(0));
            return test;
        }
    }




}
