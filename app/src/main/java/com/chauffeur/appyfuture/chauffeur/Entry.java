package com.chauffeur.appyfuture.chauffeur;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Entry extends AppCompatActivity {

    Button register, login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.entry_layout);
    }

    public void openConnectingActivity(View view) {
        Intent intent = new Intent(this, Connecting.class);
        startActivity(intent);
    }
}
