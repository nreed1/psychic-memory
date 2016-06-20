package com.example.niki.fieldoutlookandroid;

import android.app.Dialog;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.niki.fieldoutlookandroid.businessobjects.OtherTask;
import com.example.niki.fieldoutlookandroid.businessobjects.Part;
import com.example.niki.fieldoutlookandroid.businessobjects.PartCategory;
import com.example.niki.fieldoutlookandroid.businessobjects.TimeEntryType;
import com.example.niki.fieldoutlookandroid.businessobjects.WorkOrder;
import com.example.niki.fieldoutlookandroid.fragment.AssignedJobFragment;
import com.example.niki.fieldoutlookandroid.fragment.AvailableJobFragment;
import com.example.niki.fieldoutlookandroid.fragment.NewOtherTaskFragment;
import com.example.niki.fieldoutlookandroid.fragment.OtherTaskListFragment;
import com.example.niki.fieldoutlookandroid.fragment.PartListFragment;
import com.example.niki.fieldoutlookandroid.fragment.PricebookFragment;
import com.example.niki.fieldoutlookandroid.fragment.QuoteFragment;
import com.example.niki.fieldoutlookandroid.fragment.SelectedWorkorderFragment;
import com.example.niki.fieldoutlookandroid.fragment.StartDayFragment;
import com.example.niki.fieldoutlookandroid.fragment.StartFragment;
import com.example.niki.fieldoutlookandroid.fragment.TimekeepingFragment;
import com.example.niki.fieldoutlookandroid.fragment.TimesheetReviewFragment;
import com.example.niki.fieldoutlookandroid.fragment.TravelToFragment;
import com.example.niki.fieldoutlookandroid.fragment.WorkOrderPartFragment;
import com.example.niki.fieldoutlookandroid.helper.DBHelper;
import com.example.niki.fieldoutlookandroid.helper.DateHelper;
import com.example.niki.fieldoutlookandroid.helper.GetPartListAsyncTask.GetPartsListAsyncTask;
import com.example.niki.fieldoutlookandroid.helper.SendCompletedWorkOrders.SendCompletedWorkOrdersAsyncTask;
import com.example.niki.fieldoutlookandroid.helper.ServiceHelper;
import com.example.niki.fieldoutlookandroid.helper.assigned_job_service.AssignedJobReciever;
import com.example.niki.fieldoutlookandroid.helper.assigned_job_service.AssignedJobServiceHelper;
import com.example.niki.fieldoutlookandroid.helper.assigned_job_service.AssignedJobsAsyncTask;
import com.example.niki.fieldoutlookandroid.helper.singleton.Global;
import com.example.niki.fieldoutlookandroid.helper.time_entry_type_service.TimeEntryTypeAsyncTask;
import com.example.niki.fieldoutlookandroid.helper.time_entry_type_service.TimeEntryTypeReciever;
import com.example.niki.fieldoutlookandroid.helper.time_entry_type_service.TimeEntryTypeServiceHelper;
import com.google.firebase.messaging.FirebaseMessaging;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainNavigationActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, AssignedJobFragment.OnFragmentInteractionListener, AvailableJobFragment.OnFragmentInteractionListener,
        PricebookFragment.OnFragmentInteractionListener, TimekeepingFragment.OnFragmentInteractionListener,
        QuoteFragment.OnFragmentInteractionListener,StartFragment.OnFragmentInteractionListener,  StartDayFragment.OnStartDayFragmentInteractionListener,
                    TravelToFragment.OnTravelToFragmentInteractionListener, OtherTaskListFragment.OnOtherTaskListFragmentInteractionListener,
                    NewOtherTaskFragment.OnNewOtherTaskFragmentInteractionListener, TimesheetReviewFragment.OnTimesheetReviewFragmentInteractionListener,
        SelectedWorkorderFragment.OnSelectedWorkOrderFragmentInteractionListener, PartListFragment.OnPartListFragmentInteractionListener ,
        PartListFragment.OnPartListPartFragmentInteractionListener, WorkOrderPartFragment.OnWorkOrderPartListFragmentInteractionListener,
        WorkOrderPartFragment.OnWorkOrderPartMenuItemInteractionListener, SelectedWorkorderFragment.OnSelectedWorkOrderMenuItemInteractionListener{
    Toolbar toolbar;
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
    private Uri fileUri;
    private DBHelper dbHelper;
    ProgressDialog progressDialog;
    private WorkOrder workOrder;
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
        FirebaseMessaging.getInstance().subscribeToTopic("news");
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
//        if (id == R.id.action_settings) {
//            return true;
//        }

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
//            toolbar.setTitle("Pricebook");
//            android.app.FragmentManager fragmentManager= getFragmentManager();
//            android.app.FragmentTransaction fragmentTransaction= fragmentManager.beginTransaction();
//            PricebookFragment pricebookFragment= new PricebookFragment();
//            fragmentTransaction.replace(R.id.fragment_container, pricebookFragment, getString(R.string.Pricebook)).addToBackStack("Pricebook");
//            fragmentTransaction.commit();
            android.app.FragmentManager fragmentManager= getFragmentManager();
            android.app.FragmentTransaction fragmentTransaction= fragmentManager.beginTransaction();
            PartListFragment partListFragment=new PartListFragment();
            Bundle b=new Bundle();
            b.putParcelableArrayList("categories-given",null);
            partListFragment.setArguments(b);
            fragmentTransaction.replace(R.id.fragment_container, partListFragment, "PartList").addToBackStack("PartList");
            fragmentTransaction.commit();
        }else if(id==R.id.nav_edit_timekeeping){
            toolbar.setTitle("Timekeeping");
            android.app.FragmentManager fragmentManager= getFragmentManager();
            android.app.FragmentTransaction fragmentTransaction= fragmentManager.beginTransaction();
            //TimekeepingFragment timekeepingFragment=new TimekeepingFragment();
            TimesheetReviewFragment timesheetReviewFragment=new TimesheetReviewFragment();
            fragmentTransaction.replace(R.id.fragment_container, timesheetReviewFragment, getString(R.string.Timekeeping)).addToBackStack("Timekeeping");
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
            //Refresh the data //refdata
            progressDialog=new ProgressDialog(this);
            progressDialog.setTitle("Refreshing Data");
            progressDialog.isIndeterminate();
            progressDialog.setMessage("Getting Work Orders");
            progressDialog.show();

            ArrayList<WorkOrder> completedWorkOrders=dbHelper.GetCompletedWorkOrders();
            final AssignedJobsAsyncTask  assignedJobsAsyncTask = new AssignedJobsAsyncTask(new AssignedJobsAsyncTask.AssignedJobsResponse() {

                @Override
                public void processFinish(ArrayList<WorkOrder> workOrders) {
                    if (dbHelper == null)
                        dbHelper = new DBHelper(getApplicationContext());
                    dbHelper.SaveWorkOrderList(workOrders);
                    progressDialog.setMessage("Downloaded " + workOrders.size() + " Work Orders");
                }
            });
            if(completedWorkOrders!=null && !completedWorkOrders.isEmpty()){
                progressDialog.setMessage("Sending "+completedWorkOrders.size()+" Work Orders");
                SendCompletedWorkOrdersAsyncTask sendCompletedWorkOrdersAsyncTask=new SendCompletedWorkOrdersAsyncTask(getApplicationContext(), new SendCompletedWorkOrdersAsyncTask.SendCompletedWorkOrdersDelegate() {
                    @Override
                    public void processFinish(Boolean success) {
                        if(success) {
                            progressDialog.setMessage("Sending Work Orders successful!");



                            assignedJobsAsyncTask.execute((String) null);
                        }
                        else{
                            AlertDialog.Builder alert=new AlertDialog.Builder(getApplicationContext());
                            alert.setMessage("Unable to upload or download new data. Please try again and if this error persists contact the administrator.");
                            alert.show();
                            return;
                        }
                    }
                });
                sendCompletedWorkOrdersAsyncTask.execute((Void)null);
            }
            else{



                assignedJobsAsyncTask.execute((String) null);
            }


            String lastRefreshDateString=dbHelper.GetLastRefreshDate();
            Date lastRefreshDate= new Date();
            if(lastRefreshDateString!=null) {
                lastRefreshDate=DateHelper.StringToDate(lastRefreshDateString);
            }

           if(true){// if(lastRefreshDate==null || lastRefreshDate.before(new Date())) {
                TimeEntryTypeAsyncTask timeEntryTypeAsyncTask = new TimeEntryTypeAsyncTask(new TimeEntryTypeAsyncTask.TimeEntryTypeResponse() {
                    @Override
                    public void processFinish(ArrayList<TimeEntryType> timeEntryTypes) {
                        progressDialog.setMessage("Saving "+timeEntryTypes.size()+ " Time Entry Types");
                        if (dbHelper == null) dbHelper = new DBHelper(getApplicationContext());
                        for (TimeEntryType t : timeEntryTypes) {
                            dbHelper.SaveTimeEntryType(t);
                        }

                    }
                });
                timeEntryTypeAsyncTask.execute();

                GetPartsListAsyncTask getPartsListAsyncTask=new GetPartsListAsyncTask(new GetPartsListAsyncTask.GetPartsListDelegate() {
                    @Override
                    public void processFinish(ArrayList<PartCategory> result) {
                        progressDialog.setMessage("Saving "+result.size()+" Parts List");
                        progressDialog.dismiss();
                    }
                },getApplicationContext());
                getPartsListAsyncTask.execute((Void)null);


            }
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(String id) {
        toolbar.setTitle("Selected Work Order");

        int position=Integer.parseInt(id);
        WorkOrder selectedWorkOrder = dbHelper.GetWorkOrders().get(position);

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
                final AlertDialog.Builder customerSearch=new AlertDialog.Builder(this);
                customerSearch.setCancelable(true);
                LayoutInflater inflater = (LayoutInflater)this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View search_View = inflater.inflate(R.layout.customer_search_picture_attach, null);
                // customerSearch.setContentView(search_View);
                customerSearch.setView(search_View);

                customerSearch.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //customerSearch.
                        dialog.cancel();
                    }
                }).setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AutoCompleteTextView autoCompleteTextView=(AutoCompleteTextView) search_View.findViewById(R.id.autoCompleteCustomerTextView);
                        //autoCompleteTextView.get
                    }
                });
                //customerSearch.set


                customerSearch.setTitle("Select Customer: ");
                //customerSearch.create();
                customerSearch.show();
               Toast.makeText(this, "Image saved to:\n" +fileUri, Toast.LENGTH_LONG).show();
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
//    @Override
//    public void onReceiveResult(int resultCode, Bundle resultData) {
//        DBHelper dbHelper=new DBHelper(this.getApplicationContext());
//        ArrayList<WorkOrder> resultWorkOrders=resultData.getParcelableArrayList("assignedWorkOrders");
//        ArrayList<TimeEntryType> resultTimeEntryTypes=resultData.getParcelableArrayList("timeentrytypes");
//        if(resultWorkOrders!=null &&!resultWorkOrders.isEmpty()){
//            workOrders=resultWorkOrders;
//
//            dbHelper.SaveWorkOrderList(workOrders);
//
//        }else if(resultTimeEntryTypes!=null && !resultTimeEntryTypes.isEmpty() ){
//            for(TimeEntryType t:resultTimeEntryTypes){
//                dbHelper.SaveTimeEntryType(t);
//            }
//        }
//
//        progressDialog.dismiss();
//
//    }

    @Override
    public void onStartDayFragmentInteraction(String nextFragment) {
        if(nextFragment.equals("Travel")){
            StartTravelToFragment();
        }else if(nextFragment.equals("Shop")){
            //Snapshot of time
        }else if(nextFragment.equals("Other")){
            if(dbHelper.UserHasOtherTasks(Global.GetInstance().getUser().GetUserId())){
                StartOtherTaskListFragment();

            }else{
                //New Other Task Screen
               StartNewOtherTaskFragment();
            }
        }else if(nextFragment.equals("End")){
            //Review and Send
            StartTimekeepingReviewFragment();
        }
    }

    private void StartTimekeepingReviewFragment(){
        toolbar.setTitle("Review Day");
        android.app.FragmentManager fragmentManager= getFragmentManager();
        android.app.FragmentTransaction fragmentTransaction= fragmentManager.beginTransaction();
        TimesheetReviewFragment timesheetReviewFragment=new TimesheetReviewFragment();
        fragmentTransaction.replace(R.id.fragment_container, timesheetReviewFragment, "TimesheetReview").addToBackStack("TimesheetReview");
        fragmentTransaction.commit();
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

    @Override
    public void onTimesheetReviewFragmentInteraction(Uri uri) {

    }

    @Override
    public void onSelectedWorkOrderFragmentInteraction(String uri) {
        switch (uri){
            case "ViewParts":
                android.app.FragmentManager fragmentManager= getFragmentManager();
                android.app.FragmentTransaction fragmentTransaction= fragmentManager.beginTransaction();
                PartListFragment partListFragment=new PartListFragment();
                Bundle b=new Bundle();
                b.putParcelableArrayList("categories-given",null);
                partListFragment.setArguments(b);
                fragmentTransaction.replace(R.id.fragment_container, partListFragment, "WorkOrderPartList").addToBackStack("WorkOrderPartList");
                fragmentTransaction.commit();
                return;
            default:
                return;
        }
    }

    @Override
    public void onPartListFragmentInteraction(PartCategory item) {
        android.app.FragmentManager fragmentManager= getFragmentManager();
        android.app.FragmentTransaction fragmentTransaction= fragmentManager.beginTransaction();
        PartListFragment partListFragment=new PartListFragment();
        Bundle b=new Bundle();
        if(item.getParts()!=null && !item.getParts().isEmpty() && item.getSubCategoryList()!=null &&!item.getSubCategoryList().isEmpty()){
            b.putParcelable("part-category",item);
        }
        else if(item.getSubCategoryList()!=null && !item.getSubCategoryList().isEmpty()) {
            b.putParcelableArrayList("categories-given", item.getSubCategoryList());
        }else if(item.getParts()!=null && !item.getParts().isEmpty()){
            b.putParcelableArrayList("parts",item.getParts());
        }else{
            b.putParcelableArrayList("categories-given",null);
        }
        b.putParcelable("selectedworkorder",workOrder);
        partListFragment.setArguments(b);
        fragmentTransaction.replace(R.id.fragment_container, partListFragment, "SubPartList").addToBackStack("SubPartList");
        fragmentTransaction.commit();
    }

    @Override
    public void onPartListPartInteraction(Part item) {
        //TODO
    }

    @Override
    public void onWorkOrderPartListFragmentInteraction(Part item) {

    }

    @Override
    public void onWorkOrderPartMenuItemInteraction(WorkOrder selectedWorkOrder,MenuItem item) {
        this.workOrder=selectedWorkOrder;
        if(item.getItemId()==R.id.addPartToWorkOrderMenuItem){
            android.app.FragmentManager fragmentManager= getFragmentManager();
            android.app.FragmentTransaction fragmentTransaction= fragmentManager.beginTransaction();
            PartListFragment partListFragment=new PartListFragment();
            Bundle b=new Bundle();
            b.putParcelableArrayList("categories-given",null);
            b.putParcelable("selectedworkorder", selectedWorkOrder);
            partListFragment.setArguments(b);
            fragmentTransaction.replace(R.id.fragment_container, partListFragment, "PartList").addToBackStack("PartList");
            fragmentTransaction.commit();
        }
    }

    @Override
    public void onSelectedWorkOrderMenuItemInteraction(WorkOrder selectedWorkorder) {
        this.workOrder=selectedWorkorder;
        android.app.FragmentManager fragmentManager= getFragmentManager();
        android.app.FragmentTransaction fragmentTransaction= fragmentManager.beginTransaction();
        WorkOrderPartFragment workOrderPartFragment=new WorkOrderPartFragment();
        Bundle b=new Bundle();
        b.putParcelable("selectedWorkOrder",selectedWorkorder);
        workOrderPartFragment.setArguments(b);
        fragmentTransaction.replace(R.id.fragment_container, workOrderPartFragment, "WorkOrderPartList").addToBackStack("WorkOrderPartList");
        fragmentTransaction.commit();
    }
}
