package com.chauffeur.appyfuture.chauffeur;


import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class Connecting extends AppCompatActivity {
    Button connectMe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.connecting_layout);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    public boolean onOptionsItemSelected(MenuItem item){
        Intent myIntent = new Intent(getApplicationContext(), Entry.class);
        startActivityForResult(myIntent, 0);
        return true;
    }

    public void connectMe(View view){
        Intent intent = new Intent(this, Welcome.class);
        startActivity(intent);
    }
}
