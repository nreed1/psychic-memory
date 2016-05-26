package com.example.niki.fieldoutlookandroid;

import android.app.FragmentManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.niki.fieldoutlookandroid.businessobjects.OtherTask;
import com.example.niki.fieldoutlookandroid.businessobjects.TimeEntryType;
import com.example.niki.fieldoutlookandroid.businessobjects.WorkOrder;
import com.example.niki.fieldoutlookandroid.fragment.AssignedJobFragment;
import com.example.niki.fieldoutlookandroid.fragment.AvailableJobFragment;
import com.example.niki.fieldoutlookandroid.fragment.NewOtherTaskFragment;
import com.example.niki.fieldoutlookandroid.fragment.OtherTaskListFragment;
import com.example.niki.fieldoutlookandroid.fragment.PricebookFragment;
import com.example.niki.fieldoutlookandroid.fragment.QuoteFragment;
import com.example.niki.fieldoutlookandroid.fragment.SelectedWorkorderFragment;
import com.example.niki.fieldoutlookandroid.fragment.StartDayFragment;
import com.example.niki.fieldoutlookandroid.fragment.StartFragment;
import com.example.niki.fieldoutlookandroid.fragment.TimekeepingFragment;
import com.example.niki.fieldoutlookandroid.fragment.TravelToFragment;
import com.example.niki.fieldoutlookandroid.helper.DBHelper;
import com.example.niki.fieldoutlookandroid.helper.ServiceHelper;
import com.example.niki.fieldoutlookandroid.helper.assigned_job_service.AssignedJobReciever;
import com.example.niki.fieldoutlookandroid.helper.assigned_job_service.AssignedJobServiceHelper;
import com.example.niki.fieldoutlookandroid.helper.singleton.Global;
import com.example.niki.fieldoutlookandroid.helper.time_entry_type_service.TimeEntryTypeReciever;
import com.example.niki.fieldoutlookandroid.helper.time_entry_type_service.TimeEntryTypeServiceHelper;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainNavigationActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, AssignedJobFragment.OnFragmentInteractionListener, AvailableJobFragment.OnFragmentInteractionListener,
        PricebookFragment.OnFragmentInteractionListener, TimekeepingFragment.OnFragmentInteractionListener,
        QuoteFragment.OnFragmentInteractionListener,StartFragment.OnFragmentInteractionListener, AssignedJobReciever.Listener, StartDayFragment.OnStartDayFragmentInteractionListener,
                    TravelToFragment.OnTravelToFragmentInteractionListener, OtherTaskListFragment.OnOtherTaskListFragmentInteractionListener,
                    NewOtherTaskFragment.OnNewOtherTaskFragmentInteractionListener, TimeEntryTypeReciever.Listener {
    Toolbar toolbar;
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
    private Uri fileUri;
    private DBHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_navigation);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        dbHelper=new DBHelper(this.getApplicationContext());


        //FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        TextView name=(TextView)navigationView.getHeaderView(0).findViewById(R.id.userNameText);
        if(name!=null){
            name.setText(Global.GetInstance().getUser().GetFullName());
        }

        StartMainFragment();
    }

    private void StartMainFragment() {
        FragmentManager fragmentManager= getFragmentManager();
        android.app.FragmentTransaction fragmentTransaction= fragmentManager.beginTransaction();
        StartFragment startFragment= new StartFragment();
        fragmentTransaction.replace(R.id.fragment_container, startFragment, getString(R.string.StartDay));
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            FragmentManager fragmentManager=getFragmentManager();
            if(fragmentManager.getBackStackEntryCount()>0) {
                if((fragmentManager.getBackStackEntryCount()-2)>=0) {
                    FragmentManager.BackStackEntry entry = fragmentManager.getBackStackEntryAt(fragmentManager.getBackStackEntryCount() - 2);
                    toolbar.setTitle(entry.getName());
                }else{
                    toolbar.setTitle("Field Outlook");
                }

                fragmentManager.popBackStack();


            }else {
                //super.onBackPressed();
            }
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
            toolbar.setTitle("Assigned Jobs");
            android.app.FragmentManager fragmentManager= getFragmentManager();
            android.app.FragmentTransaction fragmentTransaction= fragmentManager.beginTransaction();
            AssignedJobFragment assignedJobFragment= new AssignedJobFragment();
            Bundle b =new Bundle();
            b.putParcelableArrayList("workOrders", dbHelper.GetWorkOrders());
            assignedJobFragment.setArguments(b);
            fragmentTransaction.replace(R.id.fragment_container, assignedJobFragment, getString(R.string.AssignedJobs)).addToBackStack("Assigned Jobs");
            fragmentTransaction.commit();
        }
        else if(id==R.id.nav_available_jobs){
            toolbar.setTitle("Available Jobs");
            android.app.FragmentManager fragmentManager= getFragmentManager();
            android.app.FragmentTransaction fragmentTransaction= fragmentManager.beginTransaction();
            AvailableJobFragment availableJobFragment= new AvailableJobFragment();
            fragmentTransaction.replace(R.id.fragment_container, availableJobFragment, getString(R.string.AvailableJobs)).addToBackStack("Available Jobs");
            fragmentTransaction.commit();
        }else if(id==R.id.nav_pricebook_view){
            toolbar.setTitle("Pricebook");
            android.app.FragmentManager fragmentManager= getFragmentManager();
            android.app.FragmentTransaction fragmentTransaction= fragmentManager.beginTransaction();
            PricebookFragment pricebookFragment= new PricebookFragment();
            fragmentTransaction.replace(R.id.fragment_container, pricebookFragment, getString(R.string.Pricebook)).addToBackStack("Pricebook");
            fragmentTransaction.commit();
        }else if(id==R.id.nav_edit_timekeeping){
            toolbar.setTitle("Timekeeping");
            android.app.FragmentManager fragmentManager= getFragmentManager();
            android.app.FragmentTransaction fragmentTransaction= fragmentManager.beginTransaction();
            TimekeepingFragment timekeepingFragment=new TimekeepingFragment();
            fragmentTransaction.replace(R.id.fragment_container, timekeepingFragment, getString(R.string.Timekeeping)).addToBackStack("Timekeeping");
            fragmentTransaction.commit();
        }else if(id==R.id.nav_logout){
            Intent intent= new Intent(this, LoginActivity.class);
            startActivity(intent);
        }else if(id==R.id.nav_quote){
            toolbar.setTitle("Quote");
            android.app.FragmentManager fragmentManager= getFragmentManager();
            android.app.FragmentTransaction fragmentTransaction= fragmentManager.beginTransaction();
            QuoteFragment quoteFragment=new QuoteFragment();
            fragmentTransaction.replace(R.id.fragment_container, quoteFragment, "Quote").addToBackStack("Quote");
            fragmentTransaction.commit();
        }else if(id== R.id.nav_camera){
            // create Intent to take a picture and return control to the calling application
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE); // create a file to save the image
            intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri); // set the image file name

            // start the image capture Intent
            startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
        }else if(id==R.id.nav_update_data){
            //ServiceHelper serviceHelper=new ServiceHelper();
            //serviceHelper.RefreshData();
            Intent i=new Intent(this, AssignedJobServiceHelper.class);
            AssignedJobReciever reciever=new AssignedJobReciever(new Handler());
            reciever.setListener(this);
            i.putExtra("rec", reciever);
            startService(i);
            Intent timeentryintent=new Intent(this, TimeEntryTypeServiceHelper.class);
            TimeEntryTypeReciever timeEntryTypeReciever=new TimeEntryTypeReciever(new Handler());
            timeEntryTypeReciever.setListener(this);
            timeentryintent.putExtra("rec", timeEntryTypeReciever);
            startService(timeentryintent);

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(String id) {
        toolbar.setTitle("Selected Work Order");

        int position=Integer.parseInt(id);
        WorkOrder selectedWorkOrder = workOrders.get(position);

        SelectedWorkorderFragment selectedWorkorderFragment=new SelectedWorkorderFragment();
        Bundle b= new Bundle();
        b.putParcelable("workOrder", selectedWorkOrder);
        selectedWorkorderFragment.setArguments(b);
        android.app.FragmentManager fragmentManager= getFragmentManager();
        android.app.FragmentTransaction fragmentTransaction= fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.fragment_container, selectedWorkorderFragment, "Selected Work Order").addToBackStack("Selected Work Order");
        fragmentTransaction.commit();
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                // Image captured and saved to fileUri specified in the Intent


                Toast.makeText(this, "Image saved to:\n" +
                        data.getData(), Toast.LENGTH_LONG).show();
            } else if (resultCode == RESULT_CANCELED) {
                // User cancelled the image capture
            } else {
                // Image capture failed, advise user
            }
        }


    }

    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;

    /** Create a file Uri for saving an image or video */
    private static Uri getOutputMediaFileUri(int type){
        return Uri.fromFile(getOutputMediaFile(type));
    }

    /** Create a File for saving an image or video */
    private static File getOutputMediaFile(int type){
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.

        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "MyCameraApp");
        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (! mediaStorageDir.exists()){
            if (! mediaStorageDir.mkdirs()){
                Log.d("MyCameraApp", "failed to create directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE){
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "IMG_"+ timeStamp + ".jpg");
        } else if(type == MEDIA_TYPE_VIDEO) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "VID_"+ timeStamp + ".mp4");
        } else {
            return null;
        }

        return mediaFile;
    }
    private ArrayList<WorkOrder> workOrders= new ArrayList<>();
    @Override
    public void onReceiveResult(int resultCode, Bundle resultData) {
        DBHelper dbHelper=new DBHelper(this.getApplicationContext());
        ArrayList<WorkOrder> resultWorkOrders=resultData.getParcelableArrayList("assignedWorkOrders");
        ArrayList<TimeEntryType> resultTimeEntryTypes=resultData.getParcelableArrayList("timeentrytypes");
        if(resultWorkOrders!=null &&!resultWorkOrders.isEmpty()){
            workOrders=resultWorkOrders;

            dbHelper.SaveWorkOrderList(workOrders);

        }else if(resultTimeEntryTypes!=null && !resultTimeEntryTypes.isEmpty() ){

        }


    }

    @Override
    public void onStartDayFragmentInteraction(String nextFragment) {
        if(nextFragment.equals("TravelTo")){
            StartTravelToFragment();
        }else if(nextFragment.equals("AtShop")){
            //Snapshot of time
        }else if(nextFragment.equals("Other")){
            if(dbHelper.UserHasOtherTasks(Global.GetInstance().getUser().GetUserId())){
                StartOtherTaskListFragment();

            }else{
                //New Other Task Screen
               StartNewOtherTaskFragment();
            }
        }else if(nextFragment.equals("EndDay")){
            //Review and Send
        }
    }

    private void StartNewOtherTaskFragment(){
        toolbar.setTitle("Other Task");
        android.app.FragmentManager fragmentManager= getFragmentManager();
        android.app.FragmentTransaction fragmentTransaction= fragmentManager.beginTransaction();
        NewOtherTaskFragment newOtherTaskFragment=new NewOtherTaskFragment();
        fragmentTransaction.replace(R.id.fragment_container, newOtherTaskFragment, "NewOtherTask").addToBackStack("NewOtherTask");
        fragmentTransaction.commit();
    }

    private void StartOtherTaskListFragment(){

        toolbar.setTitle("Other Task");
        android.app.FragmentManager fragmentManager= getFragmentManager();
        android.app.FragmentTransaction fragmentTransaction= fragmentManager.beginTransaction();
        OtherTaskListFragment otherTaskListFragment=new OtherTaskListFragment();
        fragmentTransaction.replace(R.id.fragment_container, otherTaskListFragment, "OtherTaskListFragment").addToBackStack("OtherTaskListFragment");
        fragmentTransaction.commit();
    }

    private void StartTravelToFragment(){
        toolbar.setTitle("Travel To");
        android.app.FragmentManager fragmentManager= getFragmentManager();
        android.app.FragmentTransaction fragmentTransaction= fragmentManager.beginTransaction();
        QuoteFragment quoteFragment=new QuoteFragment();
        fragmentTransaction.replace(R.id.fragment_container, quoteFragment, "TravelTo").addToBackStack("TravelTo");
        fragmentTransaction.commit();
    }

    @Override
    public void onTravelToFragmentInteraction(WorkOrder workOrder) {
        toolbar.setTitle("Selected Work Order");
        android.app.FragmentManager fragmentManager= getFragmentManager();
        android.app.FragmentTransaction fragmentTransaction= fragmentManager.beginTransaction();
        SelectedWorkorderFragment selectedWorkorderFragment= new SelectedWorkorderFragment();
        Bundle b=new Bundle();
        b.putParcelable("workOrder",workOrder);
        fragmentTransaction.replace(R.id.fragment_container, selectedWorkorderFragment, "SelectedWorkOrder").addToBackStack("SelectedWorkOrder");
        fragmentTransaction.commit();
    }

    @Override
    public void onOtherTaskListFragmentInteraction(OtherTask item) {
        if(item==null){
            StartNewOtherTaskFragment();
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onNewOtherTaskFragmentInteraction(String whatToDo) {
        if(whatToDo.toLowerCase().equals("back")){
            StartMainFragment();
        }
    }
}
