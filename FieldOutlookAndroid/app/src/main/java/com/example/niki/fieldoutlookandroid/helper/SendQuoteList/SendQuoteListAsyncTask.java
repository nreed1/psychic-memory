package com.example.niki.fieldoutlookandroid.helper.SendQuoteList;

import android.content.Context;
import android.os.AsyncTask;

import com.example.niki.fieldoutlookandroid.businessobjects.Quote;
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
public class SendQuoteListAsyncTask extends AsyncTask<ArrayList<Quote>, Void,Void> {

    private Context context;

    public SendQuoteListAsyncTask(Context context){
        this.context=context;
    }
    @Override
    protected Void doInBackground(ArrayList<Quote>... params) {
        try{
            HttpURLConnection con = (HttpURLConnection) (new URL(ServiceHelper.GetServiceURL() + "SaveQuote/" + URLEncoder.encode(TokenHelper.getToken()) )).openConnection();
            for(Quote quote:params[0]) {
                con.setRequestMethod("POST");
                con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                con.connect();
                OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
                wr.write(quote.toJson().toString());
                wr.flush();
            }
        }catch (Exception ex){
            ExceptionHelper.LogException(context,ex);
        }
        return null;
    }
}
