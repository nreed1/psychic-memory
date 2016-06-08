package com.example.niki.fieldoutlookandroid.helper.available_workorders;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.example.niki.fieldoutlookandroid.businessobjects.Address;
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

/**
 * Created by Niki on 6/7/2016.
 */
public class GetAvailableWorkOrdersAsyncTask extends AsyncTask<Void,Void, ArrayList<WorkOrder>> {
    public GetAvailableWorkOrdersDelegate delegate;
    public GetAvailableWorkOrdersAsyncTask(GetAvailableWorkOrdersDelegate delegate){
        this.delegate=delegate;
    }


    public interface GetAvailableWorkOrdersDelegate{
        void processFinish(ArrayList<WorkOrder> workOrders);
    }

    @Override
    protected ArrayList<WorkOrder> doInBackground(Void... params) {
        try{
            AuthenticationResponse user= Global.GetInstance().getUser();
            ArrayList<WorkOrder> assignedWorkOrders=new ArrayList<>();

            HttpURLConnection con = (HttpURLConnection) (new URL(ServiceHelper.GetServiceURL() + "GetUnassignedWorkOrders?companyID=" + user.GetCompanyId() + "&Token=" + URLEncoder.encode(TokenHelper.getToken()))).openConnection();

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
            Address address=new Address();
            customer.setAddress(address);
            while (event != XmlPullParser.END_DOCUMENT) {
                tagName = parser.getName();

                if (event == XmlPullParser.START_TAG) {
                    currentTag = tagName;
                    Log.d("currentTag",currentTag);
                    if (currentTag.equals("a:WorkOrder")) {
                        if(newWorkOrder!=new WorkOrder() && newWorkOrder.getWorkOrderId()!=0){
                            if(customer!=new Person() && customer.getPersonId()!=0){
                                newWorkOrder.setPerson(customer);
                            }
                            assignedWorkOrders.add(newWorkOrder);
                        }
                        newWorkOrder = new WorkOrder();
                    }else if (currentTag.equals("a:Customer")) {
//                        if(customer!=new Person() && customer.getPersonId()!=0){
//                            newWorkOrder.setPerson(customer);
//                        }
                        customer=new Person();
                        address=new Address();
                        customer.setAddress(address);
                    }
                } else if (event == XmlPullParser.TEXT) {
                    if (currentTag.equals("a:Name")) {
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
                    }else if(currentTag.equals("a:City")){
                        customer.getAddress().setCity(parser.getText());
                    }else if(currentTag.equals("a:State")) {
                        customer.getAddress().setState(parser.getText());
                    }else if(currentTag.equals("a:StreetAddress1")){
                        customer.getAddress().setStreetAddress1(parser.getText());
                    }else if(currentTag.equals("a:StreetAddress2")){
                        customer.getAddress().setStreetAddress2(parser.getText());
                    }else if(currentTag.equals("a:UnitNumber")){
                        customer.getAddress().setUnitNumber(parser.getText());
                    }else if(currentTag.equals("a:ZipCode")){
                        customer.getAddress().setZipCode(parser.getText());
                    }
                }

                event = parser.next();

            }
           // Bundle b = new Bundle();
            // b.putParcelableArrayList("assignedWorkOrders", assignedWorkOrders);
            //rec.send(0, b);
            return assignedWorkOrders;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
    @Override
    protected void onPostExecute(ArrayList<WorkOrder> result){
        // super.onPostExecute(result);
        delegate.processFinish(result);

    }
}
