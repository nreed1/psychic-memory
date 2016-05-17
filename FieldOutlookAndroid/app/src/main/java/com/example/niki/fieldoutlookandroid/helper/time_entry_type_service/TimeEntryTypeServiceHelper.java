package com.example.niki.fieldoutlookandroid.helper.time_entry_type_service;

import android.app.IntentService;
import android.content.Intent;
import android.os.ResultReceiver;

import com.example.niki.fieldoutlookandroid.businessobjects.TimeEntryType;
import com.example.niki.fieldoutlookandroid.helper.TokenHelper;
import com.example.niki.fieldoutlookandroid.helper.singleton.Global;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Owner on 4/1/2016.
 */
public class TimeEntryTypeServiceHelper extends IntentService {
    public TimeEntryTypeServiceHelper(){super("TimeEntryTypeServiceHelper");}
    @Override
    protected void onHandleIntent(Intent intent) {
        try {
            ArrayList<TimeEntryType> timeEntryTypes = new ArrayList<>();
            final ResultReceiver rec = (ResultReceiver) intent.getParcelableExtra("rec");
            HttpURLConnection con = (HttpURLConnection) (new URL(Global.GetInstance().getServiceUri() + "GetTimeEntryTypeList?" + 2 + "&Token=" + TokenHelper.getToken())).openConnection();
        }catch (Exception ex){

        }
    }
}
