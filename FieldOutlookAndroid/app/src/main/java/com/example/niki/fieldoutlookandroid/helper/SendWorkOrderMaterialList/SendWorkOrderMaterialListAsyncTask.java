package com.example.niki.fieldoutlookandroid.helper.SendWorkOrderMaterialList;

import android.content.Context;
import android.os.AsyncTask;

import com.example.niki.fieldoutlookandroid.businessobjects.Quote;
import com.example.niki.fieldoutlookandroid.businessobjects.WorkOrderMaterial;
import com.example.niki.fieldoutlookandroid.helper.DateHelper;
import com.example.niki.fieldoutlookandroid.helper.ExceptionHelper;
import com.example.niki.fieldoutlookandroid.helper.ServiceHelper;
import com.example.niki.fieldoutlookandroid.helper.TokenHelper;
import com.example.niki.fieldoutlookandroid.helper.singleton.Global;
import com.google.gson.Gson;

import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * Created by Owner on 6/24/2016.
 */
public class SendWorkOrderMaterialListAsyncTask extends AsyncTask<ArrayList<WorkOrderMaterial>, Void, Void> {

    private Context context;

    public SendWorkOrderMaterialListAsyncTask(Context context){
        this.context=context;
    }

    @Override
    protected Void doInBackground(ArrayList<WorkOrderMaterial>... params) {
        try{
            ArrayList<WorkOrderMaterial> workOrderMaterials=params[0];
            WorkOrderMaterial workOrderMaterial=workOrderMaterials.get(0);
            if(!workOrderMaterials.isEmpty()){
                HttpURLConnection con = (HttpURLConnection) (new URL(ServiceHelper.GetServiceURL() + "SaveWorkOrderMaterialList?workOrderID="+workOrderMaterial.getWorkOrderId()+"&requestedById="+ Global.GetInstance().getUser().GetUserId()+"&neededBy="+ DateHelper.DateToJsonString(workOrderMaterial.getNeededBy())+"&companyID="+Global.GetInstance().getUser().GetCompanyId()+"&Token=" + URLEncoder.encode(TokenHelper.getToken()) )).openConnection();
                //for(WorkOrderMaterial quote:params[0]) {
                con.setRequestMethod("POST");
                con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                con.connect();
                OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
                Gson gson=new Gson();
                wr.write(gson.toJson(workOrderMaterials).toString());
                wr.flush();
                //}
            }
        }catch (Exception ex){
            ExceptionHelper.LogException(context,ex);
        }
        return null;
    }
}
