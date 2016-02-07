package com.chauffr.home;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.preference.PreferenceManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.chauffr.R;
import com.chauffr.address.AirportAddress;
import com.chauffr.address.HomeAddress;
import com.chauffr.address.OtherAddress;
import com.chauffr.address.WorkAddress;
import com.chauffr.home.adapter.NavDrawerItem;
import com.chauffr.home.adapter.NavDrawerListAdapter;
import com.chauffr.home.adapter.SideBarList;
import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.fonts.FontAwesomeIcons;

import java.util.ArrayList;


public class Pickup extends AppCompatActivity {

    ListView drawerList;
    DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pickup_activity);
        context = this;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(getIcon(FontAwesomeIcons.fa_bars));
        getSupportActionBar().setHomeButtonEnabled(true);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerList = (ListView) findViewById(R.id.left_drawer);
        // Set the adapter for the list view
        ArrayList<NavDrawerItem> navDrawerItems =new SideBarList().getList();
        NavDrawerListAdapter adapter = new NavDrawerListAdapter(getApplicationContext(),
                navDrawerItems);
        drawerList.setAdapter(adapter);
        drawerList.setOnItemClickListener(new DrawerItemClickListener());
        // Set the list's click listener
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.string.drawer_open, R.string.drawer_close) {

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle("Options");
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle("Chauffr");
                //getSupportActionBar().setTitle(mActivityTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }


    private Drawable getIcon( FontAwesomeIcons icon){
        return new IconDrawable(context, icon)
                .colorRes(R.color.white)
                .actionBarSize();
    }
    // return back layout
    public boolean onOptionsItemSelected(MenuItem item){
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return false;
    }


    //specify the type is "pick", the next button goto dropoff class
    private void prepareIntent(Intent intent){
        intent.putExtra("pickOrDrop", "pick");
        startActivity(intent);
    }
    public void openHomeAddress(View view){
        Intent intent = new Intent(this, HomeAddress.class);
        prepareIntent(intent);
    }

    public void openWorkAddress(View view){
        Intent intent = new Intent(this, WorkAddress.class);
        prepareIntent(intent);
    }
    public void openAirportAddress(View view){
        Intent intent = new Intent(this, AirportAddress.class);
        prepareIntent(intent);
    }
    public void openOtherAddress(View view){
        Intent intent = new Intent(this, OtherAddress.class);
        prepareIntent(intent);
    }


    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    private void selectItem(int position) {
        switch (position){
            case 0:
                Toast.makeText(this,"0",Toast.LENGTH_LONG).show();
                break;
            case 1:
                break;
            case 6:
                final SharedPreferences mSharedPreference= PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                mSharedPreference.edit().clear().commit();
                Intent intent = new Intent(this,Login.class );
                startActivity(intent);
                this.finish();
                break;
            default:
                Toast.makeText(this,"asdf",Toast.LENGTH_LONG).show();
        }
    }
}
