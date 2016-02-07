package com.chauffeur.appyfuture.chauffeur;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Welcome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_activity);
    }

    public void signup(View view){
        Intent intent = new Intent(this, Signup.class);
        startActivity(intent);
    }
}
