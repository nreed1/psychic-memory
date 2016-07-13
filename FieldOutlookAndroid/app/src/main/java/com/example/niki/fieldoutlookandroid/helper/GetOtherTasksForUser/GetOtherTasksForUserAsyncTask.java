package com.example.niki.fieldoutlookandroid.helper.GetOtherTasksForUser;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.niki.fieldoutlookandroid.businessobjects.FlatRateItem;
import com.example.niki.fieldoutlookandroid.businessobjects.FlatRateItemPart;
import com.example.niki.fieldoutlookandroid.businessobjects.OtherTask;
import com.example.niki.fieldoutlookandroid.helper.DBHelper;
import com.example.niki.fieldoutlookandroid.helper.ExceptionHelper;
import com.example.niki.fieldoutlookandroid.helper.ServiceHelper;
import com.example.niki.fieldoutlookandroid.helper.TokenHelper;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * Created by Owner on 7/13/2016.
 */
public class GetOtherTasksForUserAsyncTask extends AsyncTask<Void, Void, Void> {

    private Context context;
    private GetOtherTasksForUserDelegate delegate;

    public interface GetOtherTasksForUserDelegate{
        void processFinish();
    }

    public GetOtherTasksForUserAsyncTask(Context context, GetOtherTasksForUserDelegate delegate){
        this.context=context;
        this.delegate=delegate;
    }

    @Override
    protected Void doInBackground(Void... params) {
        try{
            HttpURLConnection con = (HttpURLConnection) (new URL(ServiceHelper.GetServiceURL() + "GetFlatRateItemList/" + URLEncoder.encode(TokenHelper.getToken()))).openConnection();
            con.setRequestMethod("GET");
            con.connect();
            InputStream is = con.getInputStream();
            // Start parsing XML
            XmlPullParser parser = XmlPullParserFactory.newInstance().newPullParser();
            parser.setInput(is, null);
            int event = parser.getEventType();
            String tagName = null;
            String currentTag = null;
            ArrayList<OtherTask> otherTasks=new ArrayList<>();
            OtherTask otherTask=new OtherTask();
            while (event != XmlPullParser.END_DOCUMENT) {
                tagName = parser.getName();

                if (event == XmlPullParser.START_TAG) {
                    currentTag = tagName;
                    Log.d("currentTag", currentTag);
                    if(currentTag.equals("a:OtherTask")){
                        if(otherTask!=new OtherTask() && otherTask.getSqlid()>0){
                            otherTask.isDirty=false;
                            otherTasks.add(otherTask);
                        }
                        otherTask=new OtherTask();

                    }

                }else if (event == XmlPullParser.TEXT)  {
                    if(currentTag.equals("a:sqlid")){
                       Integer id=Integer.parseInt(parser.getText());
                        otherTask.setSqlid(id);
                    }else if(currentTag.equals("a:name")){
                        otherTask.setName(parser.getText());
                    }else if(currentTag.equals("a:description")){
                       otherTask.setDescription(parser.getText());
                    }else if(currentTag.equals("a:userid")){
                        Integer id=Integer.parseInt(parser.getText());
                        otherTask.setUserid(id);
                    }
                }
                event=parser.next();
            }
            DBHelper dbHelper=new DBHelper(context);
            for(OtherTask other:otherTasks){
                dbHelper.SaveOtherTask(other);
            }
        }catch (Exception ex){
            ExceptionHelper.LogException(context,ex);
        }
        return null;
    }
    @Override
    protected void onPostExecute(Void var){

        delegate.processFinish();

    }
}
