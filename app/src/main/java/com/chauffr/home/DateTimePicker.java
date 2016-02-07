package com.chauffr.home;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.chauffr.R;
import com.joanzapata.iconify.widget.IconButton;

public class DateTimePicker extends AppCompatActivity {

    IconButton back, next;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.date_time_picker_activity);

        setupToolbarBtn();
    }

    private void setupToolbarBtn(){
        back = (IconButton)findViewById(R.id.back);
        next = (IconButton)findViewById(R.id.next);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), BookingSummary.class);
                startActivity(intent);
            }
        });
    }
}
