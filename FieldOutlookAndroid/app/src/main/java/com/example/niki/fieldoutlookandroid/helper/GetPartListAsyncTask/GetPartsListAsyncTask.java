package com.example.niki.fieldoutlookandroid.helper.GetPartListAsyncTask;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.niki.fieldoutlookandroid.businessobjects.Address;
import com.example.niki.fieldoutlookandroid.businessobjects.Part;
import com.example.niki.fieldoutlookandroid.businessobjects.PartCategory;
import com.example.niki.fieldoutlookandroid.businessobjects.Person;
import com.example.niki.fieldoutlookandroid.businessobjects.WorkOrder;
import com.example.niki.fieldoutlookandroid.helper.DBHelper;
import com.example.niki.fieldoutlookandroid.helper.ExceptionHelper;
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
 * Created by Owner on 6/8/2016.
 */
public class GetPartsListAsyncTask extends AsyncTask<Void, Void, ArrayList<PartCategory>> {

    private Context context;
    private GetPartsListDelegate delegate;
    public GetPartsListAsyncTask(GetPartsListDelegate delegate, Context ctx){
        this.delegate=delegate;
        this.context=ctx;
    }

    public GetPartsListAsyncTask(GetPartsListDelegate delegate) {
        this.delegate=delegate;
    }


    public interface  GetPartsListDelegate{
        void processFinish(ArrayList<PartCategory> result);
    }

    @Override
    protected ArrayList<PartCategory> doInBackground(Void... params) {
        try{
            AuthenticationResponse user= Global.GetInstance().getUser();
            ArrayList<Part> parts=new ArrayList<>();
            ArrayList<PartCategory> categories=new ArrayList<>();
            ArrayList<PartCategory> subcategories= new ArrayList<>();

            HttpURLConnection con = (HttpURLConnection) (new URL(ServiceHelper.GetServiceURL() + "GetPartCategoryList?Token=" + URLEncoder.encode(TokenHelper.getToken()))).openConnection();

            con.setRequestMethod("GET");
            con.connect();
            InputStream is = con.getInputStream();

            // Start parsing XML
            XmlPullParser parser = XmlPullParserFactory.newInstance().newPullParser();
            parser.setInput(is, null);
            int event = parser.getEventType();
            String tagName = null;
            String currentTag = null;
            String endTag=null;
            Part newPart=new Part();
            PartCategory category=new PartCategory();
            Boolean isSubCategory=false;
            while (event != XmlPullParser.END_DOCUMENT) {
                tagName = parser.getName();

                if(event==XmlPullParser.END_TAG){
                    endTag=tagName;
                    if(endTag.equals("a:SubCategoryList")){
                        isSubCategory=false;
                    }
                }
                if (event == XmlPullParser.START_TAG) {
                    currentTag = tagName;
                    Log.d("currentTag",currentTag);
                    if(currentTag.equals("a:PartCategory")){
                        if(category!=new PartCategory() && category.getPartCategoryId()!=0){
                            if(newPart!=new Part() && newPart.getPartId()!=0 && !parts.contains(newPart)) parts.add(newPart);
                            if(!parts.isEmpty()){
                                category.setParts(parts);
                            }
                            if(isSubCategory==false) {
                                categories.add(category);
                            }else{
                                subcategories.add(category);
                            }
                        }
                        category=new PartCategory();
                    }
                    else if (currentTag.equals("a:Part")) {
                        if(newPart!=new Part() && newPart.getPartId()!=0){

                            parts.add(newPart);
                        }
                        newPart = new Part();
                    }else if(currentTag.equals("a:SubCategoryList")){
                        isSubCategory=true;
                    }
                } else if (event == XmlPullParser.TEXT) {
                    if (currentTag.equals("a:CategoryName")) {
                       category.setName(parser.getText());
                    } else if (currentTag.equals("a:PartCategoryId")) {
                        Integer id=Integer.parseInt(parser.getText());
                        category.setPartCategoryId(id);
                        newPart.setCategoryId(id);
                    } else if (currentTag.equals("a:Description>")) {
                        newPart.setDescription(parser.getText());
                    } else if (currentTag.equals("a:Manufacturer")) {
                        newPart.setManufacturer(parser.getText());
                    } else if (currentTag.equals("a:Model")) {
                       newPart.setModel(parser.getText());
                    }else if(currentTag.equals("a:NumberAndDescription>")){
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
                    }
                }

                event = parser.next();

            }
            DBHelper dbHelper=new DBHelper(context);
            Boolean truncatePartAndPartCategory=true;
            dbHelper.SavePartCategoryList(categories,truncatePartAndPartCategory);
            return categories;
        }
        catch (Exception ex){
            ExceptionHelper.LogException(context,ex);
        }
        return null;
    }

    @Override
    protected void onPostExecute(ArrayList<PartCategory> result){

        delegate.processFinish(result);

    }
}
