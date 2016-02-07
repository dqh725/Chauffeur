package com.chauffr.registration.adapter;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;

/**
 * Created by Wenyue on 11/12/2015.
 */
public class ExpiryDatePicker implements View.OnClickListener, DatePickerDialog.OnDateSetListener {
    EditText _editText;
    private int _month;
    private int _birthYear;
    private Context _context;
    private CharSequence title;

    public ExpiryDatePicker(Context context, int editTextViewID)
    {
        Activity act = (Activity)context;
        this._editText = (EditText)act.findViewById(editTextViewID);
        this._editText.setOnClickListener(this);
        this._context = context;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int day) {
        _birthYear = year;
        _month = monthOfYear;
        updateDisplay();
    }
    @Override
    public void onClick(View v) {
        DatePickerDialog dialog =  new DatePickerDialog(_context, this, 2015, 12, 1){
            @Override
            public void setTitle(CharSequence title){
                super.setTitle("Expiry date of your card");
            }
        };
        dialog.getDatePicker().setCalendarViewShown(false);

        LinearLayout pickerParentLayout = (LinearLayout) dialog.getDatePicker().getChildAt(0);

        LinearLayout pickerSpinnersHolder = (LinearLayout) pickerParentLayout.getChildAt(0);

        pickerSpinnersHolder.getChildAt(0).setVisibility(View.GONE);
        dialog.show();
    }



    // updates the date in the birth date EditText
    private void updateDisplay() {

        _editText.setText(new StringBuilder()
                // Month is 0 based so add 1
                .append(_month + 1).append("/").append(_birthYear));
    }
}