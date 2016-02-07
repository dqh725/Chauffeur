package com.chauffr;


import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;


/**
 * Created by Wenyue on 19/12/2015.
 */
public class FriendlyDialog {

    public static void errorDialog(final Context context, String message){
        new AlertDialog.Builder(context)
                .setTitle("Error!")
                .setMessage(message)
                .setNegativeButton(R.string.back, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                        dialog.cancel();
                        ((Activity)context).onBackPressed();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    };

    public static void infoDialog(Context context,String title, String message){
        new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
               .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                       // click to do something
                       dialog.cancel();
                   }
               })
                .setIcon(android.R.drawable.ic_dialog_info)
                .show();
    };

}
