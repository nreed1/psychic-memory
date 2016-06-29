package com.example.niki.fieldoutlookandroid.helper;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.niki.fieldoutlookandroid.businessobjects.WorkOrder;
import com.example.niki.fieldoutlookandroid.helper.authentication_service.AuthenticationResponse;
import com.example.niki.fieldoutlookandroid.helper.singleton.Global;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * Created by Niki on 6/7/2016.
 */
public class UnassignAsyncTask extends AsyncTask<Integer, Void, Boolean> {

    private Context context;
    public UnassignAsyncTask(UnassignDelegate delegate, Context ctx){
        this.delegate=delegate;
        context=ctx;
    }


    private UnassignDelegate delegate;

    public interface UnassignDelegate{
        void processFinish(Boolean result);
    }

    @Override
    protected Boolean doInBackground(Integer... params) {
        try{
            NetworkHelper networkHelper=new NetworkHelper();
            Boolean networkAvailable = networkHelper.isConnectionAvailable(context);
            AuthenticationResponse user= Global.GetInstance().getUser();
            HttpURLConnection con = (HttpURLConnection) (new URL(ServiceHelper.GetServiceURL() + "UnassignPersonFromWorkOrderAssignment?PersonID=" + user.GetUserId() +"&WorkOrderID="+params[0]+ "&Token=" + URLEncoder.encode(TokenHelper.getToken()))).openConnection();

            con.setRequestMethod("GET");
            con.connect();
            //InputStream is = con.getInputStream();
            DBHelper dbHelper=new DBHelper(context);
            dbHelper.DeleteWorkOrderById(params[0]);
            return true;
        }catch(Exception ex){
            ExceptionHelper.LogException(context,ex);
            Log.d("Unassign Error: ",ex.getStackTrace().toString());
        }
        return false;
    }

    @Override
    protected void onPostExecute(Boolean result){
        // super.onPostExecute(result);
        delegate.processFinish(result);

    }
}
