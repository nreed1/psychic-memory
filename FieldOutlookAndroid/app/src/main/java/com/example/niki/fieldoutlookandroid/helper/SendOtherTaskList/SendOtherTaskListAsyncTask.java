package com.example.niki.fieldoutlookandroid.helper.SendOtherTaskList;

import android.content.Context;
import android.os.AsyncTask;

import com.example.niki.fieldoutlookandroid.businessobjects.OtherTask;
import com.example.niki.fieldoutlookandroid.helper.DBHelper;
import com.example.niki.fieldoutlookandroid.helper.ExceptionHelper;
import com.example.niki.fieldoutlookandroid.helper.ServiceHelper;
import com.example.niki.fieldoutlookandroid.helper.TokenHelper;
import com.example.niki.fieldoutlookandroid.helper.singleton.Global;

import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * Created by Owner on 7/13/2016.
 */
public class SendOtherTaskListAsyncTask extends AsyncTask<Void,Void,Void> {

    private Context context;
    private SendOtherTaskListDelegate delegate;
    public interface  SendOtherTaskListDelegate{
        void processFinish();
    }

    public SendOtherTaskListAsyncTask(Context context, SendOtherTaskListDelegate delegate){
        this.context=context;
        this.delegate=delegate;
    }

    @Override
    protected Void doInBackground(Void... params) {
        try{
            DBHelper dbHelper=new DBHelper(context);
            ArrayList<OtherTask> tasks=dbHelper.GetDirtyOtherTaskListByUserId(Global.GetInstance().getUser().GetUserId());
            for(OtherTask other:tasks) {
                HttpURLConnection con = (HttpURLConnection) (new URL(ServiceHelper.GetServiceURL() + "SaveOtherTask/" + URLEncoder.encode(TokenHelper.getToken()))).openConnection();

                con.setRequestMethod("POST");
                con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                con.connect();
                OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
                wr.write(other.toJson());
                wr.flush();
            }
        }catch (Exception ex){
            ExceptionHelper.LogException(context,ex);
        }
        return null;
    }
    @Override
    protected void onPostExecute(Void result){

        delegate.processFinish();

    }

}
