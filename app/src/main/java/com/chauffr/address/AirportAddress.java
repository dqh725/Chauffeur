package com.chauffr.address;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.chauffr.R;
import com.chauffr.home.BookingInfoHolder;
import com.chauffr.home.DateTimePicker;
import com.chauffr.home.Dropoff;
import com.joanzapata.iconify.widget.IconButton;

import org.json.JSONObject;


public class AirportAddress extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    IconButton back, next;
    String pickOrDrop = null;
    EditText flight;
    Spinner spinner;
    Switch flightType;


    boolean isInternationalFlight = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.address_airport_activity);

        pickOrDrop = getIntent().getExtras().getString("pickOrDrop");
        prepareView();

    }

    private void prepareView(){
        back = (IconButton)findViewById(R.id.back);
        next = (IconButton)findViewById(R.id.next);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        if(pickOrDrop.equals("pick")){
            ((TextView)findViewById(R.id.title)).setText("Pick up from airport");
        }
        else{
            ((TextView)findViewById(R.id.title)).setText("Drop off airport");
            findViewById(R.id.flight).setVisibility(View.GONE);
            findViewById(R.id.switch1).setVisibility(View.GONE);

        }

        spinner = (Spinner) findViewById(R.id.airport_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.airport_list, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        flight = (EditText)findViewById(R.id.flight_input);
        flightType=(Switch)findViewById(R.id.switch1);
        flightType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flightType.isChecked()){
                    isInternationalFlight = true;
                }
                else{
                    isInternationalFlight = false;
                }
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pickOrDrop.equals("pick")) {
                    BookingInfoHolder.getInstance().setFromAirport(spinner.getSelectedItem().toString());
                    BookingInfoHolder.getInstance().setFlightNumber(flight.getText().toString());
                    BookingInfoHolder.getInstance().setIsInternationalFlight(isInternationalFlight);
                    Intent intent = new Intent(getApplicationContext(), Dropoff.class);
                    intent.putExtra("disable", "airport");
                    startActivity(intent);
                } else {
                    BookingInfoHolder.getInstance().setToAirport(spinner.getSelectedItem().toString());
                    Intent intent = new Intent(getApplicationContext(), DateTimePicker.class);
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
