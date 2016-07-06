package com.example.niki.fieldoutlookandroid.helper.GetPersonListAsyncTask;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.niki.fieldoutlookandroid.businessobjects.Address;
import com.example.niki.fieldoutlookandroid.businessobjects.JobTime;
import com.example.niki.fieldoutlookandroid.businessobjects.Part;
import com.example.niki.fieldoutlookandroid.businessobjects.Person;
import com.example.niki.fieldoutlookandroid.businessobjects.WorkOrder;
import com.example.niki.fieldoutlookandroid.businessobjects.WorkOrderPart;
import com.example.niki.fieldoutlookandroid.helper.DBHelper;
import com.example.niki.fieldoutlookandroid.helper.ExceptionHelper;
import com.example.niki.fieldoutlookandroid.helper.ServiceHelper;
import com.example.niki.fieldoutlookandroid.helper.TokenHelper;
import com.example.niki.fieldoutlookandroid.helper.singleton.Global;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * Created by Niki on 7/5/2016.
 */
public class GetPersonListAsyncTask extends AsyncTask<Void, Void,Boolean> {

    private Context context;
    private GetPersonListDelegate delegate;
    public interface GetPersonListDelegate{
        void processFinish(Boolean result);
    }
    public GetPersonListAsyncTask(Context context, GetPersonListDelegate delegate){
        this.context=context;
        this.delegate=delegate;
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        try{
            HttpURLConnection con = (HttpURLConnection) (new URL(ServiceHelper.GetServiceURL() + "GetPersonListByCompany?CompanyID=" + Global.GetInstance().getUser().GetCompanyId() + "&Token=" + URLEncoder.encode(TokenHelper.getToken()))).openConnection();

            con.setRequestMethod("GET");
            con.connect();
            InputStream is = con.getInputStream();

            // Start parsing XML
            XmlPullParser parser = XmlPullParserFactory.newInstance().newPullParser();
            parser.setInput(is, null);
            int event = parser.getEventType();
            String tagName = null;
            String currentTag = null;
          //  WorkOrder newWorkOrder = new WorkOrder();
            Person customer=new Person();
            Address address=new Address();
            customer.setAddress(address);
            ArrayList<Person> personList=new ArrayList<>();
           // ArrayList<WorkOrderPart> partList= new ArrayList<>();
            //WorkOrderPart newPart=new WorkOrderPart();
           // JobTime jobTime=new JobTime();
            while (event != XmlPullParser.END_DOCUMENT) {
                tagName = parser.getName();

                if (event == XmlPullParser.START_TAG) {
                    currentTag = tagName;
                    Log.d("currentTag",currentTag);
                    if (currentTag.equals("a:Person")) {

                        if(customer!=new Person() && customer.getPersonId()!=0){
                            personList.add(customer);
                        }
                        customer=new Person();
                        address=new Address();
                        customer.setAddress(address);

                    }
                } else if (event == XmlPullParser.TEXT) {
                    if(currentTag.equals("a:FirstName")){
                        customer.setFirstName(parser.getText());
                    }else if(currentTag.equals("a:LastName")){
                        customer.setLastName(parser.getText());
                    }else if(currentTag.equals("a:PersonID")){
                        String stringToInt=parser.getText();
                        if(stringToInt!=null && !stringToInt.isEmpty() ) {
                            customer.setPersonId(Integer.parseInt(stringToInt));
                           // newWorkOrder.setPersonId(Integer.parseInt(stringToInt));
                        }
                    }else if(currentTag.equals("a:ApplicationCompanyID")){
                        Integer id=Integer.parseInt(parser.getText());
                       // newWorkOrder.setCompanyId(id);
                        customer.setCompanyId(id);


                    }else if(currentTag.equals("a:FullName")){
                        customer.setFullName(parser.getText());
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
            savePersonList(personList);
            return true;
        }catch (Exception ex){
            ExceptionHelper.LogException(context,ex);
        }
        return false;
    }
    private boolean savePersonList(ArrayList<Person> persons){
        try {
            DBHelper dbHelper = new DBHelper(context);
            for (Person p : persons) {
                dbHelper.SavePerson(p);
            }
            return true;
        }catch (Exception ex){
            throw ex;
        }
        //eturn false;
    }
    @Override
    protected void onPostExecute(Boolean result){

        delegate.processFinish(result);

    }
}
