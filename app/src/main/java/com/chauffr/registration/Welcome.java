package com.chauffr.registration;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.chauffr.API;
import com.chauffr.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

public class Welcome extends AppCompatActivity {
    private class GetChauffeurByCodeTask extends AsyncTask<String, Void, JSONObject > {
        @Override
        protected JSONObject doInBackground(String... codes) {
            JSONObject chauffeur = API.getChauffeurByCodeRequest(codes[0]);
            return chauffeur;
        }
    }


    TextView chauffeur_name;
    private JSONObject chauffeur;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_activity);
        String code = getIntent().getStringExtra("code");
        try {
            if(code!=null) {
                chauffeur = new GetChauffeurByCodeTask().execute(code).get();
                chauffeur_name = (TextView)findViewById(R.id.chauffeur_name);
                chauffeur_name.setText(chauffeur.get("CompanyName").toString());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void signup(View view){
        Intent intent = new Intent(this, Signup.class);
        try {
            intent.putExtra("ChauffeurId",chauffeur.get("Id").toString());
            startActivity(intent);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
