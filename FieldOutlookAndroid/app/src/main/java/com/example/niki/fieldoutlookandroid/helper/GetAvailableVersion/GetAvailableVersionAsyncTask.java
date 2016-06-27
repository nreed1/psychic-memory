package com.example.niki.fieldoutlookandroid.helper.GetAvailableVersion;

import android.os.AsyncTask;
import android.util.Log;

import com.example.niki.fieldoutlookandroid.businessobjects.Part;
import com.example.niki.fieldoutlookandroid.businessobjects.PartCategory;
import com.example.niki.fieldoutlookandroid.helper.ExceptionHelper;
import com.example.niki.fieldoutlookandroid.helper.ServiceHelper;
import com.example.niki.fieldoutlookandroid.helper.TokenHelper;
import com.example.niki.fieldoutlookandroid.helper.singleton.Global;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * Created by Owner on 6/27/2016.
 */
public class GetAvailableVersionAsyncTask extends AsyncTask<Void, Void, Integer> {

    public interface GetAvailableVersionDelegate {
        void processFinish(int result);
    }
    private GetAvailableVersionDelegate delegate;

    public GetAvailableVersionAsyncTask(GetAvailableVersionDelegate delegate){
        this.delegate=delegate;
    }

    @Override
    protected Integer doInBackground(Void... params) {
        try{
            HttpURLConnection con = (HttpURLConnection) (new URL(ServiceHelper.GetServiceURL() + "GetAvailableAndroidMobileVersion?"+"Token=" + URLEncoder.encode(TokenHelper.getToken()))).openConnection();

            con.setRequestMethod("GET");
            con.connect();
            InputStream is = con.getInputStream();

            // Start parsing XML
            XmlPullParser parser = XmlPullParserFactory.newInstance().newPullParser();
            parser.setInput(is, null);
            int event = parser.getEventType();
            String tagName = null;
            String currentTag = null;

            while (event != XmlPullParser.END_DOCUMENT) {
                tagName = parser.getName();

                if (event == XmlPullParser.START_TAG) {
                    currentTag = tagName;
                    Log.d("currentTag", currentTag);

                } else if (event == XmlPullParser.TEXT) {
                    Integer version=Integer.parseInt(parser.getText());
                    return version;
                }

                break;
            }
        }catch (Exception ex){
            //Do nothing
        }
        return 0;
    }
    @Override
    protected void onPostExecute(Integer result){

        delegate.processFinish(result);

    }
}
