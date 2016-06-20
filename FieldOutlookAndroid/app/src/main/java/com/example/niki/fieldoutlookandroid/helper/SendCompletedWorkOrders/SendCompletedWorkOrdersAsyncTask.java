package com.example.niki.fieldoutlookandroid.helper.SendCompletedWorkOrders;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.niki.fieldoutlookandroid.businessobjects.PartCategory;
import com.example.niki.fieldoutlookandroid.businessobjects.WorkOrder;
import com.example.niki.fieldoutlookandroid.helper.DBHelper;
import com.example.niki.fieldoutlookandroid.helper.ExceptionHelper;
import com.example.niki.fieldoutlookandroid.helper.ServiceHelper;
import com.example.niki.fieldoutlookandroid.helper.TokenHelper;
import com.example.niki.fieldoutlookandroid.helper.singleton.Global;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.InputStream;
import java.io.OutputStreamWriter;
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
        this.context=context;
    }

    public interface SendCompletedWorkOrdersDelegate{
        void processFinish(Boolean success);
    }
    /**TODO: Post to webservice
     * */
    @Override
    protected Boolean doInBackground(Void... params) {
        try{
            DBHelper db=new DBHelper(context);
            ArrayList<WorkOrder> completed=db.GetCompletedWorkOrders();
            if(completed!=null && !completed.isEmpty()) {
                for (WorkOrder workOrder:completed) {


                    HttpURLConnection con = (HttpURLConnection) (new URL(ServiceHelper.GetServiceURL() + "SaveWorkOrder/" + URLEncoder.encode(TokenHelper.getToken()) )).openConnection();

                    con.setRequestMethod("POST");
                    con.setRequestProperty("Content-Type","application/json; charset=UTF-8");
                    con.connect();
                    OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
                    wr.write(workOrder.toJson().toString());
                    wr.flush();


                }
                return true;
            }
        }catch (Exception ex){
            ExceptionHelper.LogException(context,ex);
        }
        return false;
    }
    @Override
    protected void onPostExecute(Boolean result){

        delegate.processFinish(result);

    }
}
