package com.example.niki.fieldoutlookandroid.helper.assigned_job_service;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.example.niki.fieldoutlookandroid.businessobjects.Address;
import com.example.niki.fieldoutlookandroid.businessobjects.JobTime;
import com.example.niki.fieldoutlookandroid.businessobjects.Part;
import com.example.niki.fieldoutlookandroid.businessobjects.Person;
import com.example.niki.fieldoutlookandroid.businessobjects.WorkOrder;
import com.example.niki.fieldoutlookandroid.businessobjects.WorkOrderPart;
import com.example.niki.fieldoutlookandroid.helper.DBHelper;
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
 * Created by Owner on 6/1/2016.
 */
public class AssignedJobsAsyncTask extends AsyncTask<String, String, ArrayList<WorkOrder>> {


    public AssignedJobsAsyncTask(AssignedJobsResponse assignedJobsResponse) {
        this.delegate=assignedJobsResponse;
    }

    public interface  AssignedJobsResponse{
        void processFinish(ArrayList<WorkOrder> workOrders);
    }
    public AssignedJobsResponse delegate=null;
    @Override
    protected ArrayList<WorkOrder> doInBackground(String... params) {
        try{
            AuthenticationResponse user= Global.GetInstance().getUser();
            ArrayList<WorkOrder> assignedWorkOrders=new ArrayList<>();
            //final android.os.ResultReceiver rec = (android.os.ResultReceiver)  intent.getParcelableExtra("rec");

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
            Address address=new Address();
            customer.setAddress(address);
            ArrayList<WorkOrderPart> partList= new ArrayList<>();
            WorkOrderPart newPart=new WorkOrderPart();
            JobTime jobTime=new JobTime();
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
                            newWorkOrder.setPartList(partList);
                            newWorkOrder.setJobTime(jobTime);
                            assignedWorkOrders.add(newWorkOrder);
                        }
                        newWorkOrder = new WorkOrder();
                        newPart=new WorkOrderPart();
                        partList=new ArrayList<>();
                        jobTime=new JobTime();
                    }else if (currentTag.equals("a:Customer")) {
                        customer=new Person();
                        address=new Address();
                        customer.setAddress(address);
                    }else if(currentTag.equals("a:Part")){
                        if(newPart!=new Part() && newPart.getPartId()>0){
                            partList.add(newPart);
                        }
                        newPart=new WorkOrderPart();
                    }
                } else if (event == XmlPullParser.TEXT) {
                    if (currentTag.equals("a:Name")) {
                        if(newWorkOrder.getName()==null ||newWorkOrder.getName().isEmpty()){
                            newWorkOrder.setName(parser.getText());
                        }

                    } else if (currentTag.equals("a:Description")) {
                        if(newWorkOrder.getDescription()==null|| newWorkOrder.getDescription().isEmpty()) {
                            newWorkOrder.setDescription(parser.getText());
                        }else if(newPart.getDescription()==null ||newPart.getDescription().isEmpty()){
                            newPart.setDescription(parser.getText());
                        }
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
                    }else if(currentTag.equals("a:ApplicationCompanyID")){
                        Integer id=Integer.parseInt(parser.getText());
                        newWorkOrder.setCompanyId(id);
                        customer.setCompanyId(id);


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
                    }else if (currentTag.equals("a:Description")) {
                        newPart.setDescription(parser.getText());
                    } else if (currentTag.equals("a:Manufacturer")) {
                        newPart.setManufacturer(parser.getText());
                    } else if (currentTag.equals("a:Model")) {
                        newPart.setModel(parser.getText());
                    }else if(currentTag.equals("a:NumberAndDescription")){
                        newPart.setNumberAndDescription(parser.getText());
                    }else if(currentTag.equals("a:PartID")){
                        Integer id=Integer.parseInt(parser.getText());
                        newPart.setPartId(id);
                    }else if(currentTag.equals("a:PartNumber")){
                        newPart.setPartNumber(parser.getText());
                    }else if(currentTag.equals("a:PartTypeID")){
                        Integer id=Integer.parseInt(parser.getText());
                        newPart.setPartTypeId(id);
                    }else if(currentTag.equals("a:PriceBookID")){
                        Integer id=Integer.parseInt(parser.getText());
                        newPart.setPricebookId(id);
                    }else if(currentTag.equals("a:PriceA")){
                        Double price=Double.parseDouble(parser.getText());
                        newPart.setPrice(price);
                    }else if(currentTag.equals("a:CategoryID")){
                        Integer id=Integer.parseInt(parser.getText());
                        newPart.setCategoryId(id);
                    }else if(currentTag.equals("a:Quantity")){
                        if(parser.getText()!=null && !parser.getText().isEmpty()) {
                            Integer amount = Integer.parseInt(parser.getText());
                            newPart.setQuantity(amount);
                        }
                    }else if(currentTag.equals("a:ActualHours")){
                        Integer actualHours=Integer.parseInt(parser.getText());
                        jobTime.setActualHours(actualHours);
                    }else if(currentTag.equals("a:JobID")){
                        Integer jobId=Integer.parseInt(parser.getText());
                        jobTime.setJobId(jobId);
                    }else if(currentTag.equals("a:ProjectedHours")){
                        Integer projectedHours=Integer.parseInt(parser.getText());
                        jobTime.setProjectedHours(projectedHours);
                    }
                }
                event = parser.next();
            }

            return assignedWorkOrders;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(ArrayList<WorkOrder> result){

        delegate.processFinish(result);

    }
}
