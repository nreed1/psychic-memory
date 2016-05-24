package com.example.niki.fieldoutlookandroid.helper.assigned_job_service;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.content.Context;
import android.os.Bundle;

import com.example.niki.fieldoutlookandroid.businessobjects.Person;
import com.example.niki.fieldoutlookandroid.businessobjects.WorkOrder;
import com.example.niki.fieldoutlookandroid.helper.ServiceHelper;
import com.example.niki.fieldoutlookandroid.helper.TokenHelper;
import com.example.niki.fieldoutlookandroid.helper.authentication_service.AuthenticationResponse;
import com.example.niki.fieldoutlookandroid.helper.singleton.Global;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p/>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class AssignedJobServiceHelper extends IntentService {
    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String ACTION_FOO = "com.example.niki.fieldoutlookandroid.helper.assigned_job_service.action.FOO";
    private static final String ACTION_BAZ = "com.example.niki.fieldoutlookandroid.helper.assigned_job_service.action.BAZ";

    // TODO: Rename parameters
    private static final String EXTRA_PARAM1 = "com.example.niki.fieldoutlookandroid.helper.assigned_job_service.extra.PARAM1";
    private static final String EXTRA_PARAM2 = "com.example.niki.fieldoutlookandroid.helper.assigned_job_service.extra.PARAM2";

    public AssignedJobServiceHelper() {
        super("AssignedJobServiceHelper");
    }



    @Override
    protected void onHandleIntent(Intent intent) {
       try{
           AuthenticationResponse user= Global.GetInstance().getUser();
           ArrayList<WorkOrder> assignedWorkOrders=new ArrayList<>();
           final android.os.ResultReceiver rec = (android.os.ResultReceiver) intent.getParcelableExtra("rec");

           HttpURLConnection con = (HttpURLConnection) (new URL(ServiceHelper.GetServiceURL() + "GetWorkOrderListByAssignedPersonID?personID=" + user.GetUserId() + "&Token=" + URLEncoder.encode(TokenHelper.getToken()))).openConnection();

           con.setRequestMethod("GET");
           con.connect();
           InputStream is = con.getInputStream();

           // Start parsing XML
           XmlPullParser parser = XmlPullParserFactory.newInstance().newPullParser();
           parser.setInput(is, null);
           int event = parser.getEventType();
           String tagName = null;
           String currentTag = null;
           WorkOrder newWorkOrder = new WorkOrder();
           Person customer=new Person();
           while (event != XmlPullParser.END_DOCUMENT) {
               tagName = parser.getName();

               if (event == XmlPullParser.START_TAG) {
                   currentTag = tagName;
                   if (currentTag.equals("a:WorkOrder")) {
                       if(newWorkOrder!=new WorkOrder()){
                           assignedWorkOrders.add(newWorkOrder);
                       }
                       newWorkOrder = new WorkOrder();
                   }else if (currentTag.equals("a:Customer")) {
                       if(customer!=new Person()){
                           newWorkOrder.setPerson(customer);
                       }
                       customer=new Person();
                   }
               } else if (event == XmlPullParser.TEXT) {
                   if (currentTag.equals("a:WorkOrder")) {
                       if(newWorkOrder!=new WorkOrder()){
                           assignedWorkOrders.add(newWorkOrder);
                       }
                       newWorkOrder = new WorkOrder();
                   } else if (currentTag.equals("a:Name")) {
                       newWorkOrder.setName(parser.getText());
                   } else if (currentTag.equals("a:Description")) {
                       newWorkOrder.setDescription(parser.getText());
                   } else if (currentTag.equals("a:Notes")) {
                       newWorkOrder.setNotes(parser.getText());
                   } else if (currentTag.equals("a:WhereBilled")) {
                       newWorkOrder.setWhereBilled(parser.getText());
                   } else if (currentTag.equals("a:WorkOrderID")) {
                       String stringToInt=parser.getText();
                       if(stringToInt!=null && !stringToInt.isEmpty()) newWorkOrder.setWorkOrderId(Integer.parseInt(stringToInt));
                   } else if (currentTag.equals("a:Customer")) {
                       if(customer!=new Person()){
                           newWorkOrder.setPerson(customer);
                       }
                       customer=new Person();
                   }else if(currentTag.equals("a:FirstName")){
                       customer.setFirstName(parser.getText());
                   }else if(currentTag.equals("a:LastName")){
                       customer.setLastName(parser.getText());
                   }else if(currentTag.equals("a:PersonID")){
                       String stringToInt=parser.getText();
                       if(stringToInt!=null && !stringToInt.isEmpty() ) {
                           customer.setPersonId(Integer.parseInt(stringToInt));
                           newWorkOrder.setPersonId(Integer.parseInt(stringToInt));
                       }
                   }else if(currentTag.equals("a:FullName")){
                       customer.setFullName(parser.getText());
                   }else if(currentTag.equals("a:ArrivalTime")){
                       newWorkOrder.setArrivalTime(parser.getText());
                   }
               }

               event = parser.next();

           }
           Bundle b = new Bundle();
           b.putParcelableArrayList("assignedWorkOrders", assignedWorkOrders);
           rec.send(0, b);

//            DefaultHttpClient httpClient = new DefaultHttpClient();
//            HttpGet request = new HttpGet(SERVICE_URI + "GetPersonListByCompany/1/asdf");
//
//            request.setHeader("Accept", "application/xml");
//            request.setHeader("Content-type", "application/xml");
//
//            HttpResponse response = httpClient.execute(request);
//
//            HttpEntity responseEntity = response.getEntity();
//            String output = null;

//            output = EntityUtils.toString(responseEntity);

           //return output;
       } catch (Exception ex) {
           ex.printStackTrace();
       }
    }
}






