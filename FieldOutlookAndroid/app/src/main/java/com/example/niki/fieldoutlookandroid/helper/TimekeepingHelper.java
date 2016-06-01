package com.example.niki.fieldoutlookandroid.helper;

import android.content.Context;
import android.location.Location;

import com.example.niki.fieldoutlookandroid.businessobjects.TimeEntry;
import com.example.niki.fieldoutlookandroid.businessobjects.TimeEntryType;
import com.example.niki.fieldoutlookandroid.helper.singleton.Global;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Niki on 5/25/2016.
 */
public class TimekeepingHelper {
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
        dbHelper.SaveTimeEntry(newTimeEntry);
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
