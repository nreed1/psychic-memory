package com.example.niki.fieldoutlookandroid.helper.SendTimeEntryList;

import android.content.Context;
import android.os.AsyncTask;

import com.example.niki.fieldoutlookandroid.businessobjects.TimeEntry;
import com.example.niki.fieldoutlookandroid.helper.ExceptionHelper;
import com.example.niki.fieldoutlookandroid.helper.ServiceHelper;
import com.example.niki.fieldoutlookandroid.helper.TokenHelper;

import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * Created by Niki on 6/23/2016.
 */
public class SendTimeEntryListAsyncTask extends AsyncTask<ArrayList<TimeEntry>,Void, Void> {
    private Context context;

    public SendTimeEntryListAsyncTask(Context context){
        this.context=context;
    }

    @Override
    protected Void doInBackground(ArrayList<TimeEntry>... params) {
        try{
            HttpURLConnection con = (HttpURLConnection) (new URL(ServiceHelper.GetServiceURL() + "SaveTimeEntry/" + URLEncoder.encode(TokenHelper.getToken()) )).openConnection();
            for(TimeEntry timeEntry:params[0]) {
                con.setRequestMethod("POST");
                con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                con.connect();
                OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
                wr.write(timeEntry.toJson().toString());
                wr.flush();
            }
        }catch (Exception ex){
            ExceptionHelper.LogException(context,ex);
        }
        return null;
    }
}
