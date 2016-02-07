package com.chauffr.registration;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.chauffr.R;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;

public class Signup2 extends AppCompatActivity {
    private static final int SELECT_PICTURE = 1;
    private String selectedImagePath;

    EditText firstname, lastname;
    Button next;
    ImageButton avatar;
    JSONObject newClient = null;
    private String deviceKey = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup2_activity);
        deviceKey = Settings.Secure.getString(this.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        try {
            newClient = new JSONObject(getIntent().getStringExtra("newClient"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        avatar = (ImageButton) findViewById(R.id.upload);
        avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,
                        "Select an avatar ..."), SELECT_PICTURE);
            }
        });
        firstname = (EditText) findViewById(R.id.firstName);
        lastname = (EditText) findViewById(R.id.lastName);
        next = (Button) findViewById(R.id.next_btn);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if(newClient!=null) {
                        Intent intent = new Intent(Signup2.this, Signup3.class);
                        newClient.put("FirstName", firstname.getText().toString());
                        newClient.put("LastName", lastname.getText().toString());
                        Bitmap bmp = ((BitmapDrawable)avatar.getDrawable()).getBitmap();
                        Log.i("mybit", bmp.toString());
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
                        byte[] byteArray = stream.toByteArray();
                        newClient.put("Thumbnail", byteArray);
                        newClient.put("DeviceKey", deviceKey);
                        Log.i("myvalue",deviceKey);
                        intent.putExtra("newClient", newClient.toString());
                        Log.i("myvalue", newClient.toString());
                        startActivity(intent);
                    }
                    else{
                        onBackPressed();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                Uri selectedImageUri = data.getData();
                Picasso.with(this)
                        .load(selectedImageUri)
                        .resize(100, 100)
                        .centerCrop()
                        .into(avatar);
            }
        }
    }

//    public String getPath(Uri uri) {
//        String[] projection = { MediaStore.Images.Media.DATA };
//        Cursor cursor = managedQuery(uri, projection, null, null, null);
//        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
//        cursor.moveToFirst();
//        return cursor.getString(column_index);
//    }

}
