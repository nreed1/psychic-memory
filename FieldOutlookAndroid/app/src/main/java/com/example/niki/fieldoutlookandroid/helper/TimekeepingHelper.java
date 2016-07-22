package com.example.niki.fieldoutlookandroid.helper;

import android.content.Context;
import android.location.Location;
import android.widget.Toast;

import com.example.niki.fieldoutlookandroid.businessobjects.TimeEntry;
import com.example.niki.fieldoutlookandroid.businessobjects.TimeEntryType;
import com.example.niki.fieldoutlookandroid.helper.singleton.Global;

import java.util.Calendar;

/**
 * Created by Niki on 5/25/2016.
 */
public class TimekeepingHelper {
    public interface TimekeepingInteractionListener{
        void onTimekeepingInteraction(Object o);
    }
    public void AddTimekeepingEntry(Context context, TimeEntryType timeEntryType){
        if(timeEntryType==null) return;
        Location location=GetGPSSnapshot(context);

        TimeEntry newTimeEntry=new TimeEntry();

        newTimeEntry.setDateEntered(Calendar.getInstance().getTime());
        newTimeEntry.setStartDateTime(Calendar.getInstance().getTime());
        newTimeEntry.setEmployeeId(Global.GetInstance().getUser().GetUserId());
        newTimeEntry.setTimeEntryTypeId(timeEntryType.getTimeEntryTypeId());
        if(location!=null){
            newTimeEntry.setStartLatitude(location.getLatitude());
            newTimeEntry.setStartLongitude(location.getLongitude());
        }

        DBHelper dbHelper=new DBHelper(context);
        dbHelper.InsertTimeEntry(newTimeEntry);

    }
    public void AddTimekeepingEntry(Context context, TimeEntryType timeEntryType, int workOrderId){
        if(timeEntryType==null || workOrderId==0){
            Toast.makeText(context,"Unable to add time entry", Toast.LENGTH_LONG).show();
            return;}
        Location location=GetGPSSnapshot(context);

        TimeEntry newTimeEntry=new TimeEntry();

        newTimeEntry.setDateEntered(Calendar.getInstance().getTime());
        newTimeEntry.setStartDateTime(Calendar.getInstance().getTime());
        newTimeEntry.setEmployeeId(Global.GetInstance().getUser().GetUserId());
        newTimeEntry.setTimeEntryTypeId(timeEntryType.getTimeEntryTypeId());
        if(location!=null){
            newTimeEntry.setStartLatitude(location.getLatitude());
            newTimeEntry.setStartLongitude(location.getLongitude());
        }
        newTimeEntry.setWorkOrderId(workOrderId);

        DBHelper dbHelper=new DBHelper(context);
        dbHelper.InsertTimeEntry(newTimeEntry);

    }
    public void AddOtherTimekeepingEntry(Context context, TimeEntryType timeEntryType, int otherTaskId){
        if(timeEntryType==null){
            Toast.makeText(context,"Unable to add time entry", Toast.LENGTH_LONG).show();

            return;}
        Location location=GetGPSSnapshot(context);

        TimeEntry newTimeEntry=new TimeEntry();

        newTimeEntry.setDateEntered(Calendar.getInstance().getTime());
        newTimeEntry.setStartDateTime(Calendar.getInstance().getTime());
        newTimeEntry.setEmployeeId(Global.GetInstance().getUser().GetUserId());
        newTimeEntry.setTimeEntryTypeId(timeEntryType.getTimeEntryTypeId());
        if(location!=null){
            newTimeEntry.setStartLatitude(location.getLatitude());
            newTimeEntry.setStartLongitude(location.getLongitude());
        }
        newTimeEntry.setWorkOrderId(0);
        newTimeEntry.setOtherTaskId(otherTaskId);

        DBHelper dbHelper=new DBHelper(context);
        dbHelper.InsertTimeEntry(newTimeEntry);

    }

    public void AddTimekeepingEntry(Context context, String entryType){
        DBHelper dbHelper=new DBHelper(context);
        TimeEntryType timeEntryType=dbHelper.GetTimeEntryTypeByName(entryType);
        int workOrderId=0;
        if(entryType.equals("job")){
          //  dbHelper.get
        }
        AddTimekeepingEntry(context,timeEntryType);
    }
    public void AddTimekeepingEntry(Context context, String entryType, int workOrderId){
        DBHelper dbHelper=new DBHelper(context);
        TimeEntryType timeEntryType=dbHelper.GetTimeEntryTypeByName(entryType);
        AddTimekeepingEntry(context,timeEntryType, workOrderId);
    }
    public void AddOtherTaskTimekeepingEntry(Context context, String entryType, int otherTaskId){
        DBHelper dbHelper=new DBHelper(context);
        TimeEntryType timeEntryType=dbHelper.GetTimeEntryTypeByName(entryType);
        AddOtherTimekeepingEntry(context,timeEntryType, otherTaskId);
    }


    private Location GetGPSSnapshot(Context context){
        try{
            LocationHelper locationHelper=new LocationHelper();
            Location location=locationHelper.getLastKnownLocation(context);
            return location;

        }catch (Exception ex){

        }
        return null;
    }

}
