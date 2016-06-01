package com.example.niki.fieldoutlookandroid.helper.time_entry_type_service;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.util.Log;

import com.example.niki.fieldoutlookandroid.businessobjects.Person;
import com.example.niki.fieldoutlookandroid.businessobjects.TimeEntryType;
import com.example.niki.fieldoutlookandroid.businessobjects.WorkOrder;
import com.example.niki.fieldoutlookandroid.helper.ServiceHelper;
import com.example.niki.fieldoutlookandroid.helper.TokenHelper;
import com.example.niki.fieldoutlookandroid.helper.authentication_service.AuthenticationResponse;
import com.example.niki.fieldoutlookandroid.helper.singleton.Global;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * Created by Owner on 6/1/2016.
 */
public class TimeEntryTypeAsyncTask extends AsyncTask<Void, Void,ArrayList<TimeEntryType>> {

    public TimeEntryTypeAsyncTask(TimeEntryTypeResponse delegate){
        this.delegate=delegate;
    }

    public interface  TimeEntryTypeResponse{
        void processFinish(ArrayList<TimeEntryType> workOrders);
    }
    public TimeEntryTypeResponse delegate=null;
    @Override
    protected ArrayList<TimeEntryType> doInBackground(Void... params) {
        try {
            ArrayList<TimeEntryType> timeEntryTypes = new ArrayList<>();

            HttpURLConnection con = (HttpURLConnection) (new URL(Global.GetInstance().getServiceUri() + "GetTimeEntryTypeList?" +"companyID="+Global.GetInstance().getUser().GetCompanyId() + "&Token=" + URLEncoder.encode(TokenHelper.getToken()))).openConnection();
            con.setRequestMethod("GET");
            con.connect();
            InputStream is = con.getInputStream();

            // Start parsing XML
            XmlPullParser parser = XmlPullParserFactory.newInstance().newPullParser();
            parser.setInput(is, null);
            int event = parser.getEventType();
            String tagName = null;
            String currentTag = null;

            TimeEntryType timeEntryType=new TimeEntryType();

            while (event != XmlPullParser.END_DOCUMENT) {
                tagName = parser.getName();

                if (event == XmlPullParser.START_TAG) {
                    currentTag = tagName;
                    if(currentTag.equals("a:TimeEntryType")){
                        if(timeEntryType.getTimeEntryTypeId()!=0){
                            timeEntryTypes.add(timeEntryType);
                        }
                        timeEntryType=new TimeEntryType();
                    }
                }
                else if (event == XmlPullParser.TEXT) {
                    if(currentTag.equals("a:CompanyID")) {

                    }else if(currentTag.equals("a:TimeEntryTypeID")){
                        int id=Integer.parseInt(parser.getText());
                        timeEntryType.setTimeEntryTypeId(id);
                    }else if(currentTag.equals("a:Name")){
                        timeEntryType.setName(parser.getText());
                    }else if(currentTag.equals("a:Description")){
                        timeEntryType.setDescription(parser.getText());
                    }
                }

                event = parser.next();

            }
            timeEntryTypes.add(timeEntryType);
           // Bundle b = new Bundle();
            //b.putParcelableArrayList("timeentrytypes", timeEntryTypes);
            //rec.send(0, b);
            return timeEntryTypes;
        }catch (Exception ex){
            System.out.print(ex.getStackTrace());
        }
        return null;
    }

    @Override
    protected void onPostExecute(ArrayList<TimeEntryType> result){
        // super.onPostExecute(result);
        delegate.processFinish(result);

    }
}
