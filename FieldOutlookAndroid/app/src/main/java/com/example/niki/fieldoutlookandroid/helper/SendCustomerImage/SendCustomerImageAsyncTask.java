package com.example.niki.fieldoutlookandroid.helper.SendCustomerImage;

import android.content.Context;
import android.os.AsyncTask;

import com.example.niki.fieldoutlookandroid.businessobjects.CustomerImage;
import com.example.niki.fieldoutlookandroid.businessobjects.UploadImageRequest;
import com.example.niki.fieldoutlookandroid.helper.DBHelper;
import com.example.niki.fieldoutlookandroid.helper.ExceptionHelper;
import com.example.niki.fieldoutlookandroid.helper.ImageHelper;
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
            UploadImageRequest uploadImageRequest =new UploadImageRequest();
            if(params!=null){
                customerImage=new CustomerImage();
                customerImage.setImageName(params[0]);
                customerImage.setPersonId(Integer.parseInt(params[1]));
                uploadImageRequest.filePath=params[0];
                uploadImageRequest.objectId=Integer.parseInt(params[1]);
                uploadImageRequest.bytes=imageHelper.getImageToBytes(params[2]);
                //customerImage.setImageString(imageHelper.getImageToString(context,params[2]));
               // customerImage.setImage(imageHelper.getImageToByteArray());
            }else{
                customerImageArrayList=dbHelper.GetCustomerImageList();
            }
            if(customerImage!=null){
                con=(HttpURLConnection) (new URL(ServiceHelper.GetServiceURL() + "SaveCustomerImage")).openConnection();
                con.setRequestMethod("POST");
//                con.setRequestMethod("POST");
//                DefaultHttpClient client=new DefaultHttpClient();
//                HttpPost post=new HttpPost(ServiceHelper.GetServiceURL()+"SaveCustomerImage");
//                post.setEntity(new ByteArrayEntity(imageHelper.getImageToBytes(params[2])));
//                HttpResponse response=client.execute(post);
//                Log.d("response",String.valueOf(response.getStatusLine().getStatusCode()));
                //con.setRequestProperty("Content-Type","application/json; charset=UTF-8");
                con.connect();
                //?storageType=CustomerFileStorage&personId="+customerImage.getPersonId()
               // +"&imageName="+customerImage.getImageName()+"&Token="+ URLEncoder.encode(TokenHelper.getToken()) )
               OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
                wr.write(uploadImageRequest.toJson());
                wr.flush();
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
