package com.example.niki.fieldoutlookandroid.helper.GetFlatRateItemAsyncTask;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.niki.fieldoutlookandroid.businessobjects.FlatRateItem;
import com.example.niki.fieldoutlookandroid.businessobjects.FlatRateItemPart;
import com.example.niki.fieldoutlookandroid.businessobjects.Part;
import com.example.niki.fieldoutlookandroid.helper.DBHelper;
import com.example.niki.fieldoutlookandroid.helper.ExceptionHelper;
import com.example.niki.fieldoutlookandroid.helper.GetAvailableVersion.GetAvailableVersionAsyncTask;
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
 * Created by Owner on 6/22/2016.
 */
public class GetFlatRateItemAsyncTask extends AsyncTask<Void, Void, Integer> {

    private Context context;
    private GetFlatRateItemAsyncTaskDelegate delegate;

    public GetFlatRateItemAsyncTask(Context context, GetFlatRateItemAsyncTaskDelegate delegate){
        this.context=context;
        this.delegate=delegate;
    }

    @Override
    protected Integer doInBackground(Void... params) {
       try{
           HttpURLConnection con = (HttpURLConnection) (new URL(ServiceHelper.GetServiceURL() + "GetFlatRateItemList/" + URLEncoder.encode(TokenHelper.getToken()))).openConnection();
           con.setRequestMethod("GET");
           con.connect();
           InputStream is = con.getInputStream();

           // Start parsing XML
           XmlPullParser parser = XmlPullParserFactory.newInstance().newPullParser();
           parser.setInput(is, null);
           int event = parser.getEventType();
           String tagName = null;
           String currentTag = null;
           FlatRateItem flatRateItem=new FlatRateItem();
           ArrayList<FlatRateItem> flatRateItemArrayList=new ArrayList<>();
           ArrayList<FlatRateItemPart> partArrayList=new ArrayList<>();
           FlatRateItemPart newPart=new FlatRateItemPart();
           while (event != XmlPullParser.END_DOCUMENT) {
               tagName = parser.getName();

               if (event == XmlPullParser.START_TAG) {
                   currentTag = tagName;
                   Log.d("currentTag", currentTag);
                   if(currentTag.equals("a:FlatRateItem")){
                       if(flatRateItem!=new FlatRateItem() && flatRateItem.getFlatRateItemId()>0){
                           flatRateItem.setPartList(partArrayList);
                           flatRateItemArrayList.add(flatRateItem);
                       }
                       flatRateItem=new FlatRateItem();
                       partArrayList=new ArrayList<>();
                       newPart=new FlatRateItemPart();
                   }else if(currentTag.equals("a:Part")){
                       if(newPart!=new FlatRateItemPart() && newPart.getPartId()>0){
                           partArrayList.add(newPart);
                       }
                       newPart=new FlatRateItemPart();
                   }

               }else if (event == XmlPullParser.TEXT)  {
                    if(currentTag.equals("a:Category")){
                        flatRateItem.setCategory(parser.getText());
                    }else if(currentTag.equals("a:CompanyId")){
                        Integer id=Integer.parseInt(parser.getText());
                        flatRateItem.setCompanyId(id);
                    }else if(currentTag.equals("a:Description")){
                        flatRateItem.setDescription(parser.getText());
                    }else if(currentTag.equals("a:FlatRateItemCategoryId")){
                        Integer id=Integer.parseInt(parser.getText());
                        flatRateItem.setFlatRateItemCategoryId(id);
                    }else if(currentTag.equals("a:FlatRateItemID")){
                        Integer id=Integer.parseInt(parser.getText());
                        flatRateItem.setFlatRateItemId(id);
                    }else if(currentTag.equals("a:FlatRateName")){
                        flatRateItem.setFlatRateName(parser.getText());
                    }else if(currentTag.equals("a:LaborHours")){
                        Double id=Double.parseDouble(parser.getText());
                        flatRateItem.setLaborHours(id);
                    }else if(currentTag.equals("a:LaborRate")){
                        Double id=Double.parseDouble(parser.getText());
                        flatRateItem.setLaborRate(id);
                    }else if(currentTag.equals("a:MarkupPercent")){
                        Double id=Double.parseDouble(parser.getText());
                        flatRateItem.setMarkupPercent(id);
                    }else if(currentTag.equals("a:MemberPrice")){
                        Double id=Double.parseDouble(parser.getText());
                        flatRateItem.setMemberPrice(id);
                    }else if(currentTag.equals("a:Price")){
                        Double id=Double.parseDouble(parser.getText());
                        flatRateItem.setPrice(id);
                    } else if (currentTag.equals("a:Description")) {
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
                    }else if(currentTag.equals("a:ApplicationCompanyID")){

                    }
               }
               event=parser.next();
           }
           DBHelper dbHelper=new DBHelper(context);
           dbHelper.TruncateFlatRateItem();
           for(FlatRateItem flatRateItem1:flatRateItemArrayList){
               dbHelper.SaveFlatRateItem(flatRateItem1);
           }
        return flatRateItemArrayList.size();
       }catch (Exception ex){
           ExceptionHelper.LogException(context,ex);
       }
        return 0;
    }

    @Override
    protected void onPostExecute(Integer result){

        delegate.processFinish(result);

    }

    public interface  GetFlatRateItemAsyncTaskDelegate{
        void processFinish(Integer result);
    }
}
