package com.example.niki.fieldoutlookandroid.helper;

import android.content.Context;
import android.location.Location;

/**
 * Created by Niki on 5/25/2016.
 */
public class TimekeepingHelper {
    public void AddTimekeepingEntry(Context context){
        Location location=GetGPSSnapshot(context);


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
