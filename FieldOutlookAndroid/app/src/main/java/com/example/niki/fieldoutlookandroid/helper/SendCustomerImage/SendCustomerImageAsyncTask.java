package com.example.niki.fieldoutlookandroid.helper.SendCustomerImage;

import android.content.Context;
import android.os.AsyncTask;

import com.example.niki.fieldoutlookandroid.businessobjects.CustomerImage;
import com.example.niki.fieldoutlookandroid.helper.DBHelper;
import com.example.niki.fieldoutlookandroid.helper.ExceptionHelper;
import com.example.niki.fieldoutlookandroid.helper.ImageHelper;
import com.example.niki.fieldoutlookandroid.helper.SendCompletedWorkOrders.SendCompletedWorkOrdersAsyncTask;
import com.example.niki.fieldoutlookandroid.helper.ServiceHelper;
import com.example.niki.fieldoutlookandroid.helper.TokenHelper;

import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * Created by Niki on 6/21/2016.
 */
public class SendCustomerImageAsyncTask extends AsyncTask<String,Void,Boolean> {

    private Context context;
    private SendCustomersAsyncTaskDelegate delegate;
    private DBHelper dbHelper;

    public interface SendCustomersAsyncTaskDelegate{
        void processFinish(Boolean result);
    }

    /**When starting the task the params expected are
     * param[0]==imagename
     * param[1]==personid
     * param[2]==imagelocation
     * */
    public SendCustomerImageAsyncTask(Context context, SendCustomersAsyncTaskDelegate delegate){
        this.context=context;
        this.delegate=delegate;
        dbHelper=new DBHelper(context);
    }

    @Override
    protected Boolean doInBackground(String... params) {
        try{
            CustomerImage customerImage=null;
            ArrayList<CustomerImage> customerImageArrayList=null;
            HttpURLConnection con=null;
            ImageHelper imageHelper=new ImageHelper();
            if(params!=null){
                customerImage.setImageName(params[0]);
                customerImage.setPersonId(Integer.parseInt(params[1]));
                customerImage.setImageString(imageHelper.getImageToString(context,params[2]));
            }else{
                customerImageArrayList=dbHelper.GetCustomerImageList();
            }
            if(customerImage!=null){
                con=(HttpURLConnection) (new URL(ServiceHelper.GetServiceURL() + "SaveCustomerImage?storageType=CustomerFileStorage&personId="+customerImage.getPersonId()
                        +"&imageName="+customerImage.getImageName()+"&imageString="+customerImage.getImageString()+"&Token="+ URLEncoder.encode(TokenHelper.getToken()) )).openConnection();
                con.setRequestMethod("GET");
//                con.setRequestMethod("POST");
//                con.setRequestProperty("Content-Type","application/json; charset=UTF-8");
//                con.connect();
//                OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
//                wr.write(customerImage.toJson().toString());
//                wr.flush();
            }else {
                for(CustomerImage customerImage1:customerImageArrayList) {
                    con=(HttpURLConnection) (new URL(ServiceHelper.GetServiceURL() + "SaveCustomerImage?storageType=CustomerFileStorage&personId="+customerImage.getPersonId()
                            +"&imageName="+customerImage.getImageName()+"&imageString="+customerImage.getImageString()+"&Token="+ URLEncoder.encode(TokenHelper.getToken()) )).openConnection();
                    con.setRequestMethod("GET");
//                    con.setRequestMethod("POST");
//                    con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
//                    con.connect();
//                    OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
//                    wr.write(customerImage1.toJson().toString());
//                    wr.flush();
                }
                dbHelper.TruncateCustomerImage();
            }

        }catch (Exception ex){
            ExceptionHelper.LogException(context,ex);
        }
        return false;
    }
}
