package com.chauffr.registration;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import com.chauffr.API;
import com.chauffr.Entry;
import com.chauffr.FriendlyDialog;
import com.chauffr.R;
import com.chauffr.registration.adapter.ChauffeurCodeArrayAdapter;
import com.chauffr.registration.adapter.DataPair;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class Connecting extends AppCompatActivity {


    private class GetCodesListTask extends AsyncTask<Void, Void, List<DataPair>> {

        @Override
        protected List<DataPair> doInBackground(Void... params) {
            JSONArray codePairs = API.getChauffeurCodeList();
            List<DataPair> codes = new ArrayList<DataPair>();
            if(codePairs!=null) {
                for (int i = 0; i < codePairs.length(); i++) {
                    try {
                        JSONObject temp = (JSONObject) codePairs.get(i);
                        codes.add(new DataPair(temp.get("ChauffrCode").toString(), temp.get("ChauffrCodeTrimmed").toString()));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                return codes;
            }
            else{
                codes.add(new DataPair("",""));
                return codes;
            }

        }

        @Override
        protected void onPostExecute(List<DataPair> codes) {
            if(codes.get(0).getOrigin()==""){
                FriendlyDialog.errorDialog(Connecting.this, "Connection failed");
            }
        }
    }


    List<DataPair> AUTO_OPTION=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.connecting_layout);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);


        try {
//            AUTO_OPTION = new GetCodesOptionsTask().execute().get();
//            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
//                    android.R.layout.simple_dropdown_item_1line, AUTO_OPTION);
            AUTO_OPTION =  new GetCodesListTask().execute().get();
            ChauffeurCodeArrayAdapter chauffeurCodeOptions = new ChauffeurCodeArrayAdapter(this, R.layout.custom_drop_down_item, AUTO_OPTION);
            final AutoCompleteTextView codeEdit = (AutoCompleteTextView)
                    findViewById(R.id.code);
            codeEdit.setThreshold(5);
            codeEdit.setAdapter(chauffeurCodeOptions);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }


    // return back layout
    public boolean onOptionsItemSelected(MenuItem item){
        Intent myIntent = new Intent(getApplicationContext(), Entry.class);
        startActivityForResult(myIntent, 0);
        this.finish();
        return true;
    }

    public void connectMe(View view){
        String code =  ((EditText)findViewById(R.id.code)).getText().toString();
        ArrayList<String> codes = new ArrayList<String>();
        for(int i=0; i<AUTO_OPTION.size(); i++){
            codes.add(AUTO_OPTION.get(i).getOrigin());
        }
        if(AUTO_OPTION != null &&codes.contains(code)){
            Intent intent = new Intent(this, Welcome.class);
            intent.putExtra("code", code);
            startActivity(intent);
        }
        else{

            new AlertDialog.Builder(Connecting.this)
                .setTitle("Error!")
                .setMessage("No Chauffeur with this code found, check with your driver with correct code?")
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                        dialog.cancel();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();

        }

    }

}
