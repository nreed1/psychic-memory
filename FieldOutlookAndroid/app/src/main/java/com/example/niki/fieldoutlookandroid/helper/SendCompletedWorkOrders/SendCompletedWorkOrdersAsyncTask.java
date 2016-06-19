package com.example.niki.fieldoutlookandroid.helper.SendCompletedWorkOrders;

import android.content.Context;
import android.os.AsyncTask;

import com.example.niki.fieldoutlookandroid.businessobjects.PartCategory;
import com.example.niki.fieldoutlookandroid.helper.ExceptionHelper;
import com.example.niki.fieldoutlookandroid.helper.ServiceHelper;
import com.example.niki.fieldoutlookandroid.helper.TokenHelper;
import com.example.niki.fieldoutlookandroid.helper.singleton.Global;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * Created by Niki on 6/16/2016.
 */
public class SendCompletedWorkOrdersAsyncTask extends AsyncTask<Void,Integer,Boolean> {

    private SendCompletedWorkOrdersDelegate delegate;
    private Context context;
    public SendCompletedWorkOrdersAsyncTask(Context context,SendCompletedWorkOrdersDelegate delegate){
        this.delegate=delegate;
    }

    public interface SendCompletedWorkOrdersDelegate{
        void processFinish(Boolean success);
    }
    /**TODO: Post to webservice
     * */
    @Override
    protected Boolean doInBackground(Void... params) {
        try{
            HttpURLConnection con = (HttpURLConnection) (new URL(ServiceHelper.GetServiceURL() + "SaveWorkOrderList/Token/" + URLEncoder.encode(TokenHelper.getToken())+"/")).openConnection();

            con.setRequestMethod("POST");
            con.connect();
        }catch (Exception ex){
            ExceptionHelper.LogException(context,ex);
        }
        return null;
    }
    @Override
    protected void onPostExecute(Boolean result){

        delegate.processFinish(result);

    }
}
