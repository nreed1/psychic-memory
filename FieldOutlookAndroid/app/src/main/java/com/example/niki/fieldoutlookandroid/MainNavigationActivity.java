package com.example.niki.fieldoutlookandroid;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.niki.fieldoutlookandroid.fragment.AssignedJobFragment;
import com.example.niki.fieldoutlookandroid.fragment.AvailableJobFragment;
import com.example.niki.fieldoutlookandroid.fragment.PricebookFragment;
import com.example.niki.fieldoutlookandroid.fragment.QuoteFragment;
import com.example.niki.fieldoutlookandroid.fragment.TimekeepingFragment;
import com.example.niki.fieldoutlookandroid.helper.singleton.Global;

public class MainNavigationActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, AssignedJobFragment.OnFragmentInteractionListener, AvailableJobFragment.OnFragmentInteractionListener,
        PricebookFragment.OnFragmentInteractionListener, TimekeepingFragment.OnFragmentInteractionListener, QuoteFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_navigation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        TextView name=(TextView)navigationView.getHeaderView(0).findViewById(R.id.userNameText);
        if(name!=null){
            name.setText(Global.GetInstance().getUser().GetFirstName());
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_navigation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if(id==R.id.nav_assigned_jobs){
            android.app.FragmentManager fragmentManager= getFragmentManager();
            android.app.FragmentTransaction fragmentTransaction= fragmentManager.beginTransaction();
            AssignedJobFragment assignedJobFragment= new AssignedJobFragment();
            fragmentTransaction.replace(R.id.fragment_container, assignedJobFragment, getString(R.string.AssignedJobs));
            fragmentTransaction.commit();
        }
        else if(id==R.id.nav_available_jobs){
            android.app.FragmentManager fragmentManager= getFragmentManager();
            android.app.FragmentTransaction fragmentTransaction= fragmentManager.beginTransaction();
            AvailableJobFragment availableJobFragment= new AvailableJobFragment();
            fragmentTransaction.replace(R.id.fragment_container, availableJobFragment, getString(R.string.AvailableJobs));
            fragmentTransaction.commit();
        }else if(id==R.id.nav_pricebook_view){
            android.app.FragmentManager fragmentManager= getFragmentManager();
            android.app.FragmentTransaction fragmentTransaction= fragmentManager.beginTransaction();
            PricebookFragment pricebookFragment= new PricebookFragment();
            fragmentTransaction.replace(R.id.fragment_container, pricebookFragment, getString(R.string.Pricebook));
            fragmentTransaction.commit();
        }else if(id==R.id.nav_edit_timekeeping){
            android.app.FragmentManager fragmentManager= getFragmentManager();
            android.app.FragmentTransaction fragmentTransaction= fragmentManager.beginTransaction();
            TimekeepingFragment timekeepingFragment=new TimekeepingFragment();
            fragmentTransaction.replace(R.id.fragment_container, timekeepingFragment, getString(R.string.Timekeeping));
            fragmentTransaction.commit();
        }else if(id==R.id.nav_logout){
          //TODO logout
            Intent intent= new Intent(this, LoginActivity.class);
            startActivity(intent);
        }else if(id==R.id.nav_quote){
            android.app.FragmentManager fragmentManager= getFragmentManager();
            android.app.FragmentTransaction fragmentTransaction= fragmentManager.beginTransaction();
            AssignedJobFragment assignedJobFragment= new AssignedJobFragment();
            fragmentTransaction.replace(R.id.fragment_container, assignedJobFragment, "Assigned");
            fragmentTransaction.commit();
        }
//        if (id == R.id.nav_camera) {
//            // Handle the camera action
//        } else if (id == R.id.nav_gallery) {
//
//        } else if (id == R.id.nav_slideshow) {
//
//        } else if (id == R.id.nav_manage) {
//
//        } else if (id == R.id.nav_share) {
//
//        } else if (id == R.id.nav_send) {
//
//        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(String id) {

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
