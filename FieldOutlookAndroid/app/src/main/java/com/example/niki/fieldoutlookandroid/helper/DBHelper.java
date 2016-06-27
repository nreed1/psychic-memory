package com.example.niki.fieldoutlookandroid.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.niki.fieldoutlookandroid.businessobjects.CustomerImage;
import com.example.niki.fieldoutlookandroid.businessobjects.FOException;
import com.example.niki.fieldoutlookandroid.businessobjects.FlatRateItem;
import com.example.niki.fieldoutlookandroid.businessobjects.FlatRateItemPart;
import com.example.niki.fieldoutlookandroid.businessobjects.JobTime;
import com.example.niki.fieldoutlookandroid.businessobjects.OtherTask;
import com.example.niki.fieldoutlookandroid.businessobjects.Part;
import com.example.niki.fieldoutlookandroid.businessobjects.PartCategory;
import com.example.niki.fieldoutlookandroid.businessobjects.Person;
import com.example.niki.fieldoutlookandroid.businessobjects.Quote;
import com.example.niki.fieldoutlookandroid.businessobjects.QuotePart;
import com.example.niki.fieldoutlookandroid.businessobjects.TimeEntry;
import com.example.niki.fieldoutlookandroid.businessobjects.TimeEntryType;
import com.example.niki.fieldoutlookandroid.businessobjects.WorkOrder;
import com.example.niki.fieldoutlookandroid.businessobjects.WorkOrderMaterial;
import com.example.niki.fieldoutlookandroid.businessobjects.WorkOrderPart;
import com.example.niki.fieldoutlookandroid.helper.singleton.Global;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Owner on 4/1/2016.
 */
public class DBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME="fodatabase.db";

    public static final String TABLE_REFRESH="refresh";
    public static final String REFRESH_ID="id";
    public static final String REFRESH_DATETIME="dateoflastdataupdate";

    public static final String TABLE_TIME_ENTRY_TYPE="timeentrytype";
    public static final String TIME_ENTRY_TYPE_TYPEID="typeid";
    public static final String TIME_ENTRY_TYPE_NAME="name";
    public static final String TIME_ENTRY_TYPE_DESCRIPTION="description";

    public static final String TABLE_WORKORDER="workorder";
    public static final String WORKORDER_ID="workorderid";
    public static final String WORKORDER_COMPANYID="companyid";
    public static final String WORKORDER_PERSONID="personid";
    public static final String WORKORDER_NAME="name";
    public static final String WORKORDER_DESCRIPTION="description";
    public static final String WORKORDER_ARRIVALTIME="arrivaltime";
    public static final String WORKORDER_ESTIMATEDDURATION="estimatedduration";
    public static final String WORKORDER_COSTOFJOB="costofjob";
    public static final String WORKORDER_WHEREBILLED="wherebilled";
    public static final String WORKORDER_NOTES="notes";
    public static final String WORKORDER_TYPEID="workordertypeid";
    public static final String WORKORDER_TOTALHOURSFORJOB="totalhoursalotted";
    public static final String WORKORDER_HOURSWORKED="hoursworked";
    public static final String WORKORDER_ISCOMPLETED="iscompleted";
    public static final String WORKORDER_JOBID="jobid";
    public static final String WORKORDER_PROJECTEDHOURS="projectedhours";
    public static final String WORKORDER_ACTUALHOURS="actualhours";
    public static final String WORKORDER_COMPLETEDDATE="completeddate";
    public static final String WORKORDER_SENT="senttocloud";

    public static final String TABLE_OTHERTASKS="othertasks";
    public static final String OTHERTASKS_ID="id";
    public static final String OTHERTASKS_USERID="userid";
    public static final String OTHERTASKS_TASKNAME="taskname";
    public static final String OTHERTASKS_TASKDESCRIPTION="description";
//"create table if not exists person (id integer primary key, personid integer, firstname text, lastname text, fullname text, addressline1 text, addressline2 text, city text, state text, zipcode text," +
    //"phonenumber text)"
    public static final String TABLE_PERSON="person";
    public static final String PERSON_ID="id";
    public static final String PERSON_PERSONID="personid";
    public static final String PERSON_FIRSTNAME="firstname";
    public static final String PERSON_LASTNAME="lastname";
    public static final String PERSON_FULLNAME="fullname";
    public static final String PERSON_ADDRESSLINE1="addressline1";
    public static final String PERSON_ADDRESSLINE2="addressline2";
    public static final String PERSON_CITY="city";
    public static final String PERSON_STATE="state";
    public static final String PERSON_ZIPCODE="zipcode";
    public static final String PERSON_PHONENUMBER="phonenumber";
    public static final String PERSON_COMPANYID="companyid";



    public static final String TABLE_TIMEENTRY="timeentry";
    public static final String TIMEENTRY_ID="id";
    public static final String TIMEENTRY_TIMEENTRYID="timeentryid";
    public static final String TIMEENTRY_EMPLOYEEID="employeeid";
    public static final String TIMEENTRY_DATEENTERED="dateentered";
    public static final String TIMEENTRY_STARTDATE="startdate";
    public static final String TIMEENTRY_ENDDATE="enddate";
    public static final String TIMEENTRY_WORKORDERID="workorderid";
    public static final String TIMEENTRY_STARTLATITUDE="startlatitude";
    public static final String TIMEENTRY_STARTLONGITUDE="startlongitude";
    public static final String TIMEENTRY_ENDLATITUDE="endlatitude";
    public static final String TIMEENTRY_ENDLONGITUDE="endlongitude";
    public static final String TIMEENTRY_NOTES="notes";
    public static final String TIMEENTRY_TIMEENTRYTYPEID="timeentrytypeid";
    public static final String TIMEENTRY_ACCEPTED="accepted";

    public static final String TABLE_EXCEPTION="exceptionlog";
    public static final String EXCEPTION_ID="id";
    public static final String EXCEPTION_MESSAGE="message";
    public static final String EXCEPTION_STACKTRACE="stacktrace";
    public static final String EXCEPTION_USERID="userid";
    public static final String EXCEPTION_DATEINSERTED="dateinserted";

    public static final String TABLE_PARTCATEGORY="partcategory";
    public static final String PARTCATEGORY_ID="id";
    public static final String PARTCATEGORY_PARTCATEGORYID="partcategoryid";
    public static final String PARTCATEGORY_NAME="name";


    public static final String TABLE_PART="part";
    public static final String PART_ID="id";
    public static final String PART_PARTID="partid";
    public static final String PART_PARTTYPEID="parttypeid";
    public static final String PART_PRICEBOOKID="pricebookid";
    public static final String PART_DESCRIPTION="description";
    public static final String PART_PARTNUMBER="partnumber";
    public static final String PART_CATEGORYID="categoryid";
    public static final String PART_MODEL="model";
    public static final String PART_MANUFACTURER="manufacturer";
    public static final String PART_NUMBERANDDESCRIPTION="numberanddescription";
    public static final String PART_QUANTITY="quantity";
    public static  final String PART_PRICE="price";

    public static final String TABLE_PARTCATEGORYHIERARCHY="partcategoryhierarchy";
    public static final String PARTCATEGORYHIERARCHY_ID="id";
    public static final String PARTCATEGORYHIERARCHY_CATEGORYID="partcategoryid";
    public static final String PARTCATEGORYHIERARCHY_SUBCATEGORYID="subcategoryid";

    public static final String TABLE_WORKORDERPART="workorderpart";
    public static final String WORKORDERPART_ID="id";
    public static final String WORKORDERPART_WORKORDERID="workorderid";
    public static final String WORKORDERPART_PARTID="partid";
    public static final String WORKORDERPART_QUANTITY="quantity";
    public static final String WORKORDERPART_FLATRATEITEMID="flatrateitemid";

    public static final String TABLE_CUSTOMERIMAGE="customerimage";
    public static final String CUSTOMERIMAGE_ID="id";
    public static final String CUSTOMERIMAGE_LOCATION="imagelocation";
    public static final String CUSTOMERIMAGE_PERSONID="personid";
    public static final String CUSTOMERIMAGE_IMAGENAME="imagename";
    public static final String CUSTOMERIMAGE_IMAGE="image";

    public static final String TABLE_QUOTE="quote";
    public static final String QUOTE_ID="id";
    public static final String QUOTE_QUOTEID="quoteid";
    public static final String QUOTE_CUSTOMERID="customerid";
    public static final String QUOTE_DESCRIPTION="description";
    public static final String QUOTE_NOTES="notes";
    public static final String QUOTE_DATECREATED="datecreated";
    public static final String QUOTE_AMOUNT="amount";

    public static final String TABLE_QUOTEPART="quotepart";
    public static final String QUOTEPART_ID="id";
    public static final String QUOTEPART_QUOTEID="quoteid";
    public static final String QUOTEPART_PARTID="partid";
    public static final String QUOTEPART_QUANTITY="quantity";
    public static final String QUOTEPART_FLATRATEITEMID="flatrateitemid";

    public static final String TABLE_WORKORDERMATERIALSNEEDED="workordermaterialsneeded";
    public static final String WORKORDERMATERIALSNEEDED_ID="id";
    public static final String WORKORDERMATERIALSNEEDED_WORKORDERID="workorderid";
    public static final String WORKORDERMATERIALSNEEDED_PARTID="partid";
    public static final String WORKORDERMATERIALSNEEDED_QUANTITY="quantity";

    public static final String TABLE_FLATRATEITEM="flatrateitem";
    public static final String FLATRATEITEM_ID="id";
    public static final String FLATRATEITEM_FLATRATEITEMID="flatrateitemid";
    public static final String FLATRATEITEM_NAME="flatratename";
    public static final String FLATRATEITEM_DESCRIPTION="description";
    public static final String FLATRATEITEM_LABORHOURS="laborhours";
    public static final String FLATRATEITEM_LABORRATE="laborrate";
    public static final String FLATRATEITEM_MARKUPPERCENT="markuppercent";
    public static final String FLATRATEITEM_CATEGORY="category";
    public static final String FLATRATEITEM_CATEGORYID="categoryid";
    public static final String FLATRATEITEM_PRICE="price";
    public static final String FLATRATEITEM_MEMBERPRICE="memberprice";
    public static final String FLATRATEITEM_COMPANYID="companyid";


    public static final String TABLE_FLATRATEITEMPARTS="flatrateitemparts";
    public static final String FLATRATEITEMPARTS_ID="id";
    public static final String FLATRATEITEMPARTS_FLATRATEITEMID="flatrateitemid";
    public static final String FLATRATEITEMPARTS_PARTID="partid";
    public static final String FLATRATEITEMPARTS_QUANTITY="quantity";

    SQLiteDatabase db;

    Context ctx;
    public DBHelper(Context context){
        super(context,DATABASE_NAME,null, 1);
        //db=this.getWritableDatabase();
        ctx=context;
       // create();
    }
private void create(){

}
    @Override
    public void onCreate(SQLiteDatabase db) {


            db.execSQL("create table if not exists refresh (id integer primary key,dateoflastdataupdate text )");
        //#start timekeeping
            db.execSQL("create table if not exists timeentrytype (id integer primary key, typeid integer,name text, description text)");
            db.execSQL("create table if not exists timeentry (id integer primary key, timeentryid integer, employeeid integer, dateentered text, startdate text, enddate text, workorderid integer," +
                    "startlatitude real, startlongitude real, endlatitude real, endlongitude real, notes text, timeentrytypeid integer, accepted integer)");
        //#end timekeeping
        //#start workorder
            db.execSQL("create table if not exists workorder (id integer primary key, workorderid integer, companyid integer, personid integer, name text, description text, " +
                    "arrivaltime string, estimatedduration real," +
                    "costofjob real, wherebilled text, notes text, workordertypeid integer, totalhoursalotted integer, hoursworked integer, iscompleted integer, jobid integer, " +
                    "projectedhours integer, actualhours integer,completeddate text, senttocloud integer)");
        //#end workorder

        //#start other tasks
            db.execSQL("create table if not exists othertasks(id integer primary key, taskname text, userid integer, description text)");
        //#end other tasks
        //#start person
            db.execSQL("create table if not exists person (id integer primary key, personid integer, companyid integer,firstname text, lastname text, fullname text, addressline1 text, addressline2 text, city text, state text, zipcode text," +
                    "phonenumber text)");
        //#end person

        //#start exception logger
        db.execSQL("create table if not exists exceptionlog (id integer primary key, message text, stacktrace text, userid integer, dateinserted text)");
        //#end exception logger

        //#start partcategory
        db.execSQL("create table if not exists partcategory (id integer primary key, partcategoryid integer,name text)");
        //#end partcategory

        //#start part
        db.execSQL("create table if not exists part (id integer primary key, partid integer,parttypeid integer,pricebookid integer, description text, partnumber text, categoryid integer, model text,manufacturer text, numberanddescription text, quantity integer, price real)");
        //#end part

        //#start partcategoryhierarchy
            db.execSQL("create table if not exists partcategoryhierarchy (id integer primary key, partcategoryid integer, subcategoryid integer)");
        //#end partcategoryhierarchy

        //#start workorderpart
        db.execSQL("create table if not exists workorderpart (id integer primary key, workorderid integer, partid integer, quantity integer, flatrateitemid integer)");
        //#end workorderpart

        //#start customerimage
        db.execSQL("create table if not exists customerimage (id integer primary key, imagelocation text, personid integer, imagename text, image blob)");
        //#end customerimage

        //#start quotepart
        db.execSQL("create table if not exists quotepart (id integer primary key, quoteid integer, partid integer, quantity integer, flatrateitemid integer)");
        //#end quotepart

        //#start quote
        db.execSQL("create table if not exists quote (id integer primary key, quoteid integer, customerid integer, description text,notes text,datecreated text, amount real)");
        //#end quote


        //#start workordermaterialsneeded
        db.execSQL("create table if not exists workordermaterialsneeded (id integer primary key, workorderid integer, partid integer, quantity integer)");
        //#end workordermaterialsneeded

        //#start flatrateitem
        db.execSQL("create table if not exists flatrateitem (id integer primary key, flatrateitemid integer, flatratename text, description text, laborhours real, laborrate real, markuppercent real, category text, categoryid integer, price real, memberprice real, companyid integer)");
        //#end flatrateitem


        //#start flatrateitempart
        db.execSQL("create table if not exists flatrateitemparts (id integer primary key, flatrateitemid integer, partid integer, quantity integer)");
        //#end flatrateitempart

    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
                //Do nothing
    }

/** Gets the flat rate item by the flatrateid not the id assigned by sqlite
 * @param flatRateItemId not the sqlite id
 * */
    public FlatRateItem GetFlatRateItemById(int flatRateItemId){
        Cursor res=null;
        try{

            db=getReadableDatabase();
            res=db.rawQuery("select * from "+TABLE_FLATRATEITEM,null);
            res.moveToFirst();
            if(!res.isAfterLast()){
                FlatRateItem flatRateItem=new FlatRateItem();
                flatRateItem.setId(res.getInt(res.getColumnIndex(FLATRATEITEM_ID)));
                flatRateItem.setFlatRateItemId(res.getInt(res.getColumnIndex(FLATRATEITEM_FLATRATEITEMID)));
                flatRateItem.setFlatRateName(res.getString(res.getColumnIndex(FLATRATEITEM_NAME)));
                flatRateItem.setDescription(res.getString(res.getColumnIndex(FLATRATEITEM_DESCRIPTION)));
                flatRateItem.setLaborHours(res.getDouble(res.getColumnIndex(FLATRATEITEM_LABORHOURS)));
                flatRateItem.setLaborRate(res.getDouble(res.getColumnIndex(FLATRATEITEM_LABORRATE)));
                flatRateItem.setMarkupPercent(res.getDouble(res.getColumnIndex(FLATRATEITEM_MARKUPPERCENT)));
                flatRateItem.setCategory(res.getString(res.getColumnIndex(FLATRATEITEM_CATEGORY)));
                flatRateItem.setFlatRateItemCategoryId(res.getInt(res.getColumnIndex(FLATRATEITEM_CATEGORYID)));
                flatRateItem.setCompanyId(res.getInt(res.getColumnIndex(FLATRATEITEM_COMPANYID)));
                flatRateItem.setPrice(res.getDouble(res.getColumnIndex(FLATRATEITEM_PRICE)));
                flatRateItem.setMemberPrice(res.getDouble(res.getColumnIndex(FLATRATEITEM_MEMBERPRICE)));
                flatRateItem.setPartList(GetFlatRateItemPartList(flatRateItem.getFlatRateItemId()));

                return flatRateItem;
            }
        }catch (Exception ex){
            ExceptionHelper.LogException(ctx,ex);
        }finally {
            if(res!=null)res.close();
        }
        return null;
    }


    public ArrayList<FlatRateItem> GetFlatRateItems(){
        Cursor res=null;
        try{
            ArrayList<FlatRateItem> flatRateItems=new ArrayList<>();
            db=getReadableDatabase();
            res=db.rawQuery("select * from "+TABLE_FLATRATEITEM,null);
            res.moveToFirst();
            while(!res.isAfterLast()){
                FlatRateItem flatRateItem=new FlatRateItem();
                flatRateItem.setId(res.getInt(res.getColumnIndex(FLATRATEITEM_ID)));
                flatRateItem.setFlatRateItemId(res.getInt(res.getColumnIndex(FLATRATEITEM_FLATRATEITEMID)));
                flatRateItem.setFlatRateName(res.getString(res.getColumnIndex(FLATRATEITEM_NAME)));
                flatRateItem.setDescription(res.getString(res.getColumnIndex(FLATRATEITEM_DESCRIPTION)));
                flatRateItem.setLaborHours(res.getDouble(res.getColumnIndex(FLATRATEITEM_LABORHOURS)));
                flatRateItem.setLaborRate(res.getDouble(res.getColumnIndex(FLATRATEITEM_LABORRATE)));
                flatRateItem.setMarkupPercent(res.getDouble(res.getColumnIndex(FLATRATEITEM_MARKUPPERCENT)));
                flatRateItem.setCategory(res.getString(res.getColumnIndex(FLATRATEITEM_CATEGORY)));
                flatRateItem.setFlatRateItemCategoryId(res.getInt(res.getColumnIndex(FLATRATEITEM_CATEGORYID)));
                flatRateItem.setCompanyId(res.getInt(res.getColumnIndex(FLATRATEITEM_COMPANYID)));
                flatRateItem.setPrice(res.getDouble(res.getColumnIndex(FLATRATEITEM_PRICE)));
                flatRateItem.setMemberPrice(res.getDouble(res.getColumnIndex(FLATRATEITEM_MEMBERPRICE)));
                flatRateItem.setPartList(GetFlatRateItemPartList(flatRateItem.getFlatRateItemId()));

                flatRateItems.add(flatRateItem);
                res.moveToNext();
            }

            return flatRateItems;
        }catch (Exception ex){
            ExceptionHelper.LogException(ctx,ex);
        }finally {
            if(res!=null)res.close();
        }
        return new ArrayList<>();
    }

    public long SaveFlatRateItem(FlatRateItem flatRateItem){
        try{
            db=getWritableDatabase();
            ContentValues contentValues=new ContentValues();
            contentValues.put(FLATRATEITEM_FLATRATEITEMID, flatRateItem.getFlatRateItemId());
            contentValues.put(FLATRATEITEM_NAME, flatRateItem.getFlatRateName());
            contentValues.put(FLATRATEITEM_DESCRIPTION,flatRateItem.getDescription());
            contentValues.put(FLATRATEITEM_LABORHOURS,flatRateItem.getLaborHours());
            contentValues.put(FLATRATEITEM_LABORRATE,flatRateItem.getLaborRate());
            contentValues.put(FLATRATEITEM_MARKUPPERCENT,flatRateItem.getMarkupPercent());
            contentValues.put(FLATRATEITEM_CATEGORY,flatRateItem.getCategory());
            contentValues.put(FLATRATEITEM_CATEGORYID,flatRateItem.getFlatRateItemCategoryId());
            contentValues.put(FLATRATEITEM_COMPANYID,flatRateItem.getCompanyId());
            contentValues.put(FLATRATEITEM_PRICE,flatRateItem.getPrice());
            contentValues.put(FLATRATEITEM_MEMBERPRICE,flatRateItem.getMemberPrice());
            long pkid=db.insert(TABLE_FLATRATEITEM,null, contentValues);
            SaveFlatRateItemParts(flatRateItem.getFlatRateItemId(),flatRateItem.getPartList());
            return pkid;
        }catch (Exception ex){
            ExceptionHelper.LogException(ctx,ex);
        }
        return 0;
    }


    public boolean TruncateFlatRateItem(){
        TruncateFlatRateItemParts();
        db=getReadableDatabase();
        Cursor res= db.rawQuery("delete from "+TABLE_FLATRATEITEM,null);
        if(res==null) return false;
        return true;
    }


    public ArrayList<FlatRateItemPart> GetFlatRateItemPartList(int flatRateItemId){
        Cursor res=null;
        try{
            ArrayList<FlatRateItemPart> flatRateItemParts=new ArrayList<>();
            db=getReadableDatabase();
            res=db.rawQuery("select * from "+TABLE_FLATRATEITEMPARTS+ " where flatrateitemid="+flatRateItemId,null);
            res.moveToFirst();
            while(!res.isAfterLast()){

                FlatRateItemPart flatRateItemPart=new FlatRateItemPart(GetPartById(res.getInt(res.getColumnIndex(FLATRATEITEMPARTS_PARTID))),res.getInt(res.getColumnIndex(FLATRATEITEMPARTS_QUANTITY)));
                flatRateItemParts.add(flatRateItemPart);
                res.moveToNext();
            }
            return flatRateItemParts;
        }catch (Exception ex){
            ExceptionHelper.LogException(ctx,ex);
        }
        finally {
            if(res!=null)res.close();
        }
        return new ArrayList<>();
    }

    public void SaveFlatRateItemParts(int flatRateItemId, ArrayList<FlatRateItemPart> flatRateItemParts){
        try{
            DeleteFlatRateItemPartsByFlatRateItem(flatRateItemId);

            db=getWritableDatabase();
            for(FlatRateItemPart flatRateItemPart:flatRateItemParts){
                ContentValues contentValues=new ContentValues();
                contentValues.put(FLATRATEITEMPARTS_FLATRATEITEMID,flatRateItemId);
                contentValues.put(FLATRATEITEMPARTS_PARTID,flatRateItemPart.getPartId());
                contentValues.put(FLATRATEITEMPARTS_QUANTITY, flatRateItemPart.getQuantity());
                db.insert(TABLE_FLATRATEITEMPARTS, null, contentValues);
            }


        }catch (Exception ex){
            ExceptionHelper.LogException(ctx,ex);
        }
    }


    public boolean DeleteFlatRateItemPartsByFlatRateItem(int flatRateItemId){
        Cursor res=null;
        try{
            db=getWritableDatabase();
            res=db.rawQuery("delete from "+TABLE_FLATRATEITEMPARTS+" where flatrateitemid="+flatRateItemId,null);

            return true;
        }catch (Exception ex){
            ExceptionHelper.LogException(ctx,ex);
        }finally {
            if(res!=null)res.close();
        }
        return false;
    }

    public boolean TruncateFlatRateItemParts(){
        db=getReadableDatabase();
        Cursor res= db.rawQuery("delete from "+TABLE_FLATRATEITEMPARTS,null);
        if(res==null) return false;
        return true;
    }


    public ArrayList<WorkOrderMaterial> GetWorkOrderMaterialsByWorkOrderId(int workOrderId){
        Cursor res=null;
        try{
            ArrayList<WorkOrderMaterial> workOrderMaterials=new ArrayList<>();
            db=getReadableDatabase();
            res=db.rawQuery("select * from "+TABLE_WORKORDERMATERIALSNEEDED+" where workorderid="+workOrderId,null);
            res.moveToFirst();
            while(!res.isAfterLast()){
                WorkOrderMaterial workOrderMaterial=new WorkOrderMaterial(GetPartById(res.getInt(res.getColumnIndex(WORKORDERMATERIALSNEEDED_PARTID))),res.getInt(res.getColumnIndex(WORKORDERMATERIALSNEEDED_QUANTITY)));
                res.moveToNext();
            }
        }catch (Exception ex){
            ExceptionHelper.LogException(ctx,ex);
        }
        finally {
            if(res!=null)res.close();
        }
        return new ArrayList<>();
    }

    public void SaveWorkOrderMaterialsNeeded(int workOrderId, ArrayList<WorkOrderMaterial> workOrderMaterials){
        try{
            DeleteWorkOrderMaterialsByWorkOrder(workOrderId);

            db=getWritableDatabase();
            for(WorkOrderMaterial workOrderMaterial:workOrderMaterials) {
                ContentValues contentValues = new ContentValues();
                contentValues.put(WORKORDERMATERIALSNEEDED_PARTID, workOrderMaterial.getPartId());
                contentValues.put(WORKORDERMATERIALSNEEDED_QUANTITY,workOrderMaterial.getQuantity());
                contentValues.put(WORKORDERMATERIALSNEEDED_WORKORDERID, workOrderId);
                db.insert(TABLE_WORKORDERMATERIALSNEEDED,null, contentValues);
            }
        }catch (Exception ex){
            ExceptionHelper.LogException(ctx,ex);
        }
    }

    public boolean DeleteWorkOrderMaterialsByWorkOrder(int workOrderId){
        Cursor res=null;
        try{
            db=getWritableDatabase();
            res=db.rawQuery("delete from "+TABLE_WORKORDERMATERIALSNEEDED+" where workorderid="+workOrderId,null);
            res.moveToFirst();

            return true;
        }catch (Exception ex){
            ExceptionHelper.LogException(ctx,ex);
        }
        return false;
    }


    public boolean TruncateWorkOrderMaterialsNeeded(){
        db=getReadableDatabase();
        Cursor res= db.rawQuery("delete from "+TABLE_WORKORDERMATERIALSNEEDED,null);
        if(res==null) return false;
        return true;
    }


    public ArrayList<Quote> GetQuoteList(){
        Cursor res=null;
        try{
            ArrayList<Quote> quotes=new ArrayList<>();
            db=getReadableDatabase();
            res=db.rawQuery("select * from quote",null);
            res.moveToFirst();
            while(!res.isAfterLast()){
                Quote newQuote=new Quote(res.getInt(res.getColumnIndex(QUOTE_ID)),GetPersonByPersonId(res.getInt(res.getColumnIndex(QUOTE_CUSTOMERID))),res.getString(res.getColumnIndex(QUOTE_DESCRIPTION)),
                        res.getString(res.getColumnIndex(QUOTE_NOTES)), res.getString(res.getColumnIndex(QUOTE_DATECREATED)),GetQuotePartListByQuote(res.getInt(res.getColumnIndex(QUOTE_ID))),
                        res.getDouble(res.getColumnIndex(QUOTE_AMOUNT)));
                quotes.add(newQuote);
                res.moveToNext();
            }
            return quotes;
        }catch (Exception ex){
            ExceptionHelper.LogException(ctx,ex);
        }finally {
            if(res!=null)res.close();
        }
        return new ArrayList<>();
    }
    public Quote GetQuoteById(int quoteId){
        Cursor res=null;
        try{

            db=getReadableDatabase();
            res=db.rawQuery("select * from quote",null);
            res.moveToFirst();
            if(!res.isAfterLast()){
                Quote newQuote=new Quote(res.getInt(res.getColumnIndex(QUOTE_ID)),GetPersonByPersonId(res.getInt(res.getColumnIndex(QUOTE_CUSTOMERID))),res.getString(res.getColumnIndex(QUOTE_DESCRIPTION)),
                        res.getString(res.getColumnIndex(QUOTE_NOTES)), res.getString(res.getColumnIndex(QUOTE_DATECREATED)),GetQuotePartListByQuote(res.getInt(res.getColumnIndex(QUOTE_ID))),
                        res.getDouble(res.getColumnIndex(QUOTE_AMOUNT)));

                return newQuote;

            }

        }catch (Exception ex){
            ExceptionHelper.LogException(ctx,ex);
        }finally {
            if(res!=null)res.close();
        }
        return null;
    }

    /**
     * This is a HARD delete! (both the quoteparts and the quote)
     * */
    public boolean DeleteQuoteById(int quoteId){
        Cursor res=null;
        try{
            db=getWritableDatabase();
            DeleteAllQuotePartsForQuote(quoteId);
            res=db.rawQuery("delete from "+TABLE_QUOTE+" where quoteid="+quoteId,null);

            return true;
        }catch (Exception ex){
            ExceptionHelper.LogException(ctx,ex);
        }finally {
            if(res!=null)res.close();
        }
        return false;
    }

    public boolean TruncateQuote(){
        db=getReadableDatabase();
        Cursor res= db.rawQuery("delete from "+TABLE_QUOTE,null);
        if(res==null) return false;
        return true;
    }

    public ArrayList<QuotePart> GetQuotePartListByQuote(int quoteId){
        Cursor res=null;
        try{
            ArrayList<QuotePart> quotePartArrayList=new ArrayList<>();
            db=getReadableDatabase();
            res=db.rawQuery("select * from "+TABLE_QUOTEPART+" where quoteid="+quoteId,null);
            res.moveToFirst();
            while(!res.isAfterLast()){
                QuotePart quotePart=new QuotePart(GetPartById(res.getInt(res.getColumnIndex(QUOTEPART_PARTID))),res.getInt(res.getColumnIndex(QUOTEPART_QUANTITY)));
                quotePart.setFlatRateItem(GetFlatRateItemById(res.getInt(res.getColumnIndex(QUOTEPART_FLATRATEITEMID))));
                quotePartArrayList.add(quotePart);
                res.moveToNext();
            }
            return quotePartArrayList;
        }catch (Exception ex){
            ExceptionHelper.LogException(ctx,ex);
        }finally {
            if(res!=null)res.close();
        }
        return new ArrayList<>();
    }

    public boolean SaveQuotePartList(ArrayList<QuotePart> quotePartArrayList,int quoteId){
        try{

            DeleteAllQuotePartsForQuote(quoteId);
            db=getWritableDatabase();
            for (QuotePart quotePart: quotePartArrayList){
                ContentValues contentValues=new ContentValues();
                contentValues.put(QUOTEPART_PARTID,quotePart.getPartId());
                contentValues.put(QUOTEPART_QUOTEID,quoteId);
                contentValues.put(QUOTEPART_QUANTITY,quotePart.getQuantity());
                if(quotePart.getFlatRateItem()!=null) {
                    contentValues.put(QUOTEPART_FLATRATEITEMID, quotePart.getFlatRateItem().getFlatRateItemId());
                }else contentValues.put(QUOTEPART_FLATRATEITEMID,0);

                db.insert(TABLE_QUOTEPART,null,contentValues);

            }
            return true;
        }catch (Exception ex){
            ExceptionHelper.LogException(ctx,ex);
        }
        return false;
    }

    public boolean DeleteAllQuotePartsForQuote(int quoteId){
        Cursor res=null;
        try{
            db=getWritableDatabase();
            res=db.rawQuery("delete from "+TABLE_QUOTEPART+" where quoteid="+quoteId,null);
            res.moveToFirst();
            return true;
        }catch (Exception ex){
            ExceptionHelper.LogException(ctx,ex);
        }
        finally {
            if(res!=null)res.close();
        }
        return false;
    }


    public boolean SaveCustomerImage(String imageLocation, int personId, String imageName){
        try{
            db=getWritableDatabase();
            ContentValues contentValues=new ContentValues();
            contentValues.put(CUSTOMERIMAGE_LOCATION,imageLocation);
            contentValues.put(CUSTOMERIMAGE_IMAGENAME,imageName);
            contentValues.put(CUSTOMERIMAGE_PERSONID,personId);

            db.insert(TABLE_CUSTOMERIMAGE,null, contentValues);
            return true;
        }catch (Exception ex){
            ExceptionHelper.LogException(ctx,ex);
        }
        return false;
    }

    public ArrayList<CustomerImage> GetCustomerImageList(){
        Cursor res=null;
        try{
            ArrayList<CustomerImage> customerImages=new ArrayList<>();
            db=getReadableDatabase();
            res=db.rawQuery("select * from "+TABLE_CUSTOMERIMAGE,null);
            res.moveToFirst();
            ImageHelper imageHelper=new ImageHelper();
            while(!res.isAfterLast()){

                CustomerImage customerImage=new CustomerImage(imageHelper.getImageToString(ctx,res.getString(res.getColumnIndex(CUSTOMERIMAGE_LOCATION))),res.getString(res.getColumnIndex(CUSTOMERIMAGE_IMAGENAME)),res.getInt(res.getColumnIndex(CUSTOMERIMAGE_PERSONID)));
                if(customerImage!=null){
                    customerImages.add(customerImage);
                }
                res.moveToNext();
            }
            return customerImages;
        }catch (Exception ex){
            ExceptionHelper.LogException(ctx,ex);
        }
        finally {
            if(res!=null) res.close();
        }
        return new ArrayList<>();
    }
    public Boolean hasCustomerImages(){
        Cursor res=null;
        try{
            db=getReadableDatabase();
            res= db.rawQuery("select * from "+TABLE_CUSTOMERIMAGE+" limit 1",null);
            res.moveToFirst();
            if(!res.isAfterLast())return true;
        }catch (Exception ex){
            ExceptionHelper.LogException(ctx,ex);
        }
        finally {
            if(res!=null)res.close();
        }
        return false;
    }

    public boolean TruncateCustomerImage(){
        db=getReadableDatabase();
        Cursor res= db.rawQuery("delete from "+TABLE_CUSTOMERIMAGE,null);
        if(res==null) return false;
        return true;
    }

    public String GetLastRefreshDate(){
        db=getReadableDatabase();
        Cursor res=db.rawQuery("select * from "+TABLE_REFRESH,null);
        res.moveToFirst();
        if(res==null || res.isAfterLast()==true)return null;

        return  res.getString(res.getColumnIndex(REFRESH_DATETIME));
    }


    public void SaveLastRefreshDate(Date dateOfRefresh){
        db=getWritableDatabase();
        db.execSQL("delete from "+TABLE_REFRESH);
        ContentValues contentValues=new ContentValues();
        contentValues.put(REFRESH_DATETIME,DateHelper.DateToString(dateOfRefresh));
        db.insert(TABLE_REFRESH, null,contentValues);
    }

    public boolean TruncatePartCategoryHierarchy(){
        db=getReadableDatabase();
        Cursor res= db.rawQuery("delete from "+TABLE_PARTCATEGORYHIERARCHY,null);
        if(res==null) return false;
        return true;
    }
    public void SavePartCategoryHierarchy(int categoryid, int subcategoryid){
        db=getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(PARTCATEGORYHIERARCHY_CATEGORYID,categoryid);
        contentValues.put(PARTCATEGORYHIERARCHY_SUBCATEGORYID, subcategoryid);
        long id=db.insert(TABLE_PARTCATEGORYHIERARCHY,null, contentValues);

    }
    private ArrayList<Integer> GetSubCategoryListIdsByCategoryId(int categoryId){
        Cursor res=null;
        try {
            ArrayList<Integer> subCategoryIds = new ArrayList<>();
            db = getReadableDatabase();
            res = db.rawQuery("select subcategoryid from " + TABLE_PARTCATEGORYHIERARCHY + " where partcategoryid=" + categoryId+" and subcategoryid!=0", null);
            res.moveToFirst();
            while (res.isAfterLast() == false) {
                subCategoryIds.add(res.getInt(0));
                res.moveToNext();
            }
            return subCategoryIds;
        }catch (Exception ex){
            ExceptionHelper.LogException(ctx,ex);
        }
        finally {
            if(res!=null) res.close();
        }
        return new ArrayList<Integer>();
    }
    public ArrayList<PartCategory> GetSubCategoryList(int categoryId){
        ArrayList<Integer> subCategoryIds=GetSubCategoryListIdsByCategoryId(categoryId);
        ArrayList<PartCategory> categoryList=new ArrayList<>();
        for (Integer subcategoryId:subCategoryIds){
            PartCategory partcategory=GetPartCategoryById(subcategoryId);
            if(partcategory!=null)
                categoryList.add(partcategory);
        }
        return categoryList;
    }

    public void SavePartCategory(PartCategory category){
        db=getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(PARTCATEGORY_NAME,category.getName());
        contentValues.put(PARTCATEGORY_PARTCATEGORYID,category.getPartCategoryId());
        db.insert(TABLE_PARTCATEGORY,null, contentValues);
    }

    public ArrayList<PartCategory> GetPartSubCategoryListByCategoryId(int categoryId){
        Cursor res= null;
        try {
            db = getReadableDatabase();
            ArrayList<PartCategory> subcategories = new ArrayList<>();
            res = db.rawQuery("select distinct subcategoryid from partcategoryhierarchy where categoryid=" + categoryId, null);
            res.moveToFirst();
            while (res.isAfterLast() == false) {
                PartCategory category = GetPartCategoryById(res.getInt(0));
                subcategories.add(category);
                res.moveToNext();
            }
            return subcategories;
        }catch (Exception ex){
            ExceptionHelper.LogException(ctx,ex);
        }
        finally {
            if(res!=null) res.close();
        }
        return new ArrayList<>();
    }

    public ArrayList<PartCategory> GetPartCategoryList(int categoryId){
        Cursor res=null;
        try {
            ArrayList<PartCategory> partCategories = new ArrayList<>();
            db = getReadableDatabase();
            if (categoryId == -100) {//Top Headers
                res = db.rawQuery("select distinct pc.partcategoryid from partcategoryhierarchy pc join part p on p.categoryid==pc.partcategoryid where pc.partcategoryid not in (select subcategoryid from partcategoryhierarchy)", null);
                res.moveToFirst();
                ArrayList<Integer> mainHeaders = new ArrayList<>();
                while (res.isAfterLast() == false) {
                    mainHeaders.add(res.getInt(0));
                    PartCategory category = GetPartCategoryById(res.getInt(0));
                    category.setSubCategoryList(GetPartCategoryList(res.getInt(0)));
                    category.setParts(GetPartListByCategoryId(res.getInt(0)));
                    partCategories.add(category);
                    res.moveToNext();
                }
            } else {

                ArrayList<PartCategory> headers = GetSubCategoryList(categoryId);
                if (!headers.isEmpty() && headers.size() > 0) {
                    for (PartCategory subcategory : headers) {
                        if (subcategory != null && subcategory.getPartCategoryId()!=0) {
                            subcategory.setParts(GetPartListByCategoryId(subcategory.getPartCategoryId()));
                            if(!subcategory.getParts().isEmpty()) {
                                subcategory.setSubCategoryList(GetPartCategoryList(subcategory.getPartCategoryId()));

                                partCategories.add(subcategory);
                            }
                        }
                    }
                } else {
                    return new ArrayList<PartCategory>();
                }
            }
            return partCategories;
        }catch(Exception ex){
            ExceptionHelper.LogException(ctx,ex);
        }
        finally {
            if(res!=null) res.close();
        }
        return new ArrayList<>();
    }



    public PartCategory GetPartCategoryById(int categoryId){
        Cursor res=null;

        try {
            db = getReadableDatabase();
            res = db.rawQuery("select * from " + TABLE_PARTCATEGORY + " where " + PARTCATEGORY_PARTCATEGORYID + "=" + categoryId, null);
            res.moveToFirst();
            PartCategory category = null;
            while (res.isAfterLast() == false) {
                category = new PartCategory();
                category.setPartCategoryId(res.getInt(res.getColumnIndex(PARTCATEGORY_PARTCATEGORYID)));
                category.setName(res.getString(res.getColumnIndex(PARTCATEGORY_NAME)));

                res.moveToNext();
            }
            return category;
        }catch (Exception ex){
            ExceptionHelper.LogException(ctx,ex);
        }
        finally {
            if(res!=null) res.close();
        }
        return null;
    }

    public Part GetPartById(int partId){
        Cursor res=null;
        try {
            db = getReadableDatabase();
            res = db.rawQuery("select * from part where partid=" + partId, null);
            res.moveToFirst();
            Part part = null;
            while (res.isAfterLast() == false) {

                part = new Part();
                part.setPartId(res.getInt(res.getColumnIndex(PART_PARTID)));
                part.setCategoryId(res.getInt(res.getColumnIndex(PART_CATEGORYID)));
                part.setPrice(res.getDouble(res.getColumnIndex(PART_PRICE)));
                part.setPricebookId(res.getInt(res.getColumnIndex(PART_PRICEBOOKID)));
                part.setPartTypeId(res.getInt(res.getColumnIndex(PART_PARTTYPEID)));
                part.setDescription(res.getString(res.getColumnIndex(PART_DESCRIPTION)));
                part.setManufacturer(res.getString(res.getColumnIndex(PART_MANUFACTURER)));
                part.setModel(res.getString(res.getColumnIndex(PART_MODEL)));
                part.setNumberAndDescription(res.getString(res.getColumnIndex(PART_NUMBERANDDESCRIPTION)));
                part.setPartNumber(res.getString(res.getColumnIndex(PART_PARTNUMBER)));
               // part.setQuantity(res.getInt(res.getColumnIndex(PART_QUANTITY)));
                return part;

            }

        }catch (Exception ex){
            ExceptionHelper.LogException(ctx,ex);
        }finally {
            if(res!=null) {
                res.close();
            }
        }
        return null;
    }

    public ArrayList<Part> GetPartListByCategoryId(int categoryId){
        Cursor res=null;
        try {
            db = getReadableDatabase();
            res = db.rawQuery("select * from part where categoryid=" + categoryId, null);
            res.moveToFirst();
            ArrayList<Part> partList = new ArrayList<>();
            Part part = null;
            while (!res.isAfterLast()) {

                part = new Part();
                part.setPartId(res.getInt(res.getColumnIndex(PART_PARTID)));
                part.setCategoryId(res.getInt(res.getColumnIndex(PART_CATEGORYID)));
                part.setPrice(res.getDouble(res.getColumnIndex(PART_PRICE)));
                part.setPricebookId(res.getInt(res.getColumnIndex(PART_PRICEBOOKID)));
                part.setPartTypeId(res.getInt(res.getColumnIndex(PART_PARTTYPEID)));
                part.setDescription(res.getString(res.getColumnIndex(PART_DESCRIPTION)));
                part.setManufacturer(res.getString(res.getColumnIndex(PART_MANUFACTURER)));
                part.setModel(res.getString(res.getColumnIndex(PART_MODEL)));
                part.setNumberAndDescription(res.getString(res.getColumnIndex(PART_NUMBERANDDESCRIPTION)));
                part.setPartNumber(res.getString(res.getColumnIndex(PART_PARTNUMBER)));
               // part.setQuantity(res.getInt(res.getColumnIndex(PART_QUANTITY)));
                partList.add(part);
                res.moveToNext();

            }
            return partList;
        }catch (Exception ex){
            ExceptionHelper.LogException(ctx,ex);
        }finally {
            if(res!=null) res.close();
        }
        return null;
    }
    public ArrayList<Part> GetAllParts(){
        Cursor res=null;
        try{
            ArrayList<Part> parts=new ArrayList<>();
            db=getReadableDatabase();
            res=db.rawQuery("select * from part",null);
            res.moveToFirst();
            while(!res.isAfterLast()){
                Part part = new Part();
                part.setPartId(res.getInt(res.getColumnIndex(PART_PARTID)));
                part.setCategoryId(res.getInt(res.getColumnIndex(PART_CATEGORYID)));
                part.setPrice(res.getDouble(res.getColumnIndex(PART_PRICE)));
                part.setPricebookId(res.getInt(res.getColumnIndex(PART_PRICEBOOKID)));
                part.setPartTypeId(res.getInt(res.getColumnIndex(PART_PARTTYPEID)));
                part.setDescription(res.getString(res.getColumnIndex(PART_DESCRIPTION)));
                part.setManufacturer(res.getString(res.getColumnIndex(PART_MANUFACTURER)));
                part.setModel(res.getString(res.getColumnIndex(PART_MODEL)));
                part.setNumberAndDescription(res.getString(res.getColumnIndex(PART_NUMBERANDDESCRIPTION)));
                part.setPartNumber(res.getString(res.getColumnIndex(PART_PARTNUMBER)));
               // part.setQuantity(res.getInt(res.getColumnIndex(PART_QUANTITY)));
                parts.add(part);
                res.moveToNext();

            }
            return parts;
        }catch (Exception ex){
            ExceptionHelper.LogException(ctx,ex);
        }finally {
            if(res!=null)res.close();
        }
        return new ArrayList<>();
    }

    public void SavePart(Part part){
        db=getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(PART_PARTID,part.getPartId());
        contentValues.put(PART_CATEGORYID,part.getCategoryId());
        contentValues.put(PART_DESCRIPTION,part.getDescription());
        contentValues.put(PART_MANUFACTURER,part.getManufacturer());
        contentValues.put(PART_MODEL,part.getModel());
        contentValues.put(PART_PARTNUMBER,part.getPartNumber());
        contentValues.put(PART_NUMBERANDDESCRIPTION,part.getNumberAndDescription());
        contentValues.put(PART_PARTTYPEID,part.getPartTypeId());
        contentValues.put(PART_PRICE,part.getPrice());
        contentValues.put(PART_PRICEBOOKID,part.getPricebookId());
      //  contentValues.put(PART_QUANTITY,part.getQuantity());
        db.insert(TABLE_PART,null, contentValues);
    }

    public  boolean TruncateParts(){
        Cursor res=null;
        try {
            db = getReadableDatabase();
            res = db.rawQuery("delete from " + TABLE_PART, null);
            if (res == null) return false;
            return true;
        }catch (Exception ex){
            ExceptionHelper.LogException(ctx,ex);
        }
        finally {
            if(res!=null) res.close();
        }
        return false;
    }

    public boolean TruncatePartCategory(){
        Cursor res=null;
        try {
            db = getReadableDatabase();
            res = db.rawQuery("delete from " + TABLE_PARTCATEGORY, null);
            return res != null;
        }catch (Exception ex){
            ExceptionHelper.LogException(ctx, ex);
        }finally {
            if(res!=null) res.close();
        }
        return false;
    }

    /* *if TruncateFirst==true it truncates both the Part and PartCategory tables
    * */
    public void SavePartCategoryList(ArrayList<PartCategory> categories,Boolean truncatePartAndPartCategoryFirst){
        if(truncatePartAndPartCategoryFirst){
            TruncatePartCategoryHierarchy();
            TruncateParts();
            TruncatePartCategory();
        }
        //db=getWritableDatabase();
        for (PartCategory category:categories) {
            if(category.getParts()!=null && !category.getParts().isEmpty()){
                ArrayList<Part> partList=category.getParts();
                for (Part part: partList){
                    //SavePart
                    SavePart(part);

                }
            }
            //SaveCategory
            SavePartCategory(category);

            //CategoryHierarchy
            if(category.getSubCategoryList()!=null &&!category.getSubCategoryList().isEmpty()){
                for (PartCategory subCategory: category.getSubCategoryList()){
                    SavePartCategoryHierarchy(category.getPartCategoryId(),subCategory.getPartCategoryId());
                }
            }

            //CategoryHierarchy - Alt
            if(category.getSubCategoryIdList()!=null && !category.getSubCategoryIdList().isEmpty()){
                for (Integer subcategoryId:category.getSubCategoryIdList()){
                    SavePartCategoryHierarchy(category.getPartCategoryId(),subcategoryId);
                }
            }
        }
    }



    public void SaveTimeEntryType(TimeEntryType timeEntryType){
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(TIME_ENTRY_TYPE_TYPEID, timeEntryType.getTimeEntryTypeId());
            contentValues.put(TIME_ENTRY_TYPE_NAME, timeEntryType.getName());
            contentValues.put(TIME_ENTRY_TYPE_DESCRIPTION, timeEntryType.getDescription());
            db.insert(TABLE_TIME_ENTRY_TYPE, null, contentValues);
        }catch (Exception ex){
            ExceptionHelper.LogException(ctx,ex);
        }
    }
    public ArrayList<TimeEntryType> getTimeEntryTypeList(){
        Cursor res=null;
        try {
            ArrayList<TimeEntryType> timeEntryTypes = new ArrayList<>();
            db = this.getReadableDatabase();
            res = db.rawQuery("select * from timeentrytype", null);
            res.moveToFirst();
            while (res.isAfterLast() == false) {
                timeEntryTypes.add(new TimeEntryType(res.getInt(res.getColumnIndex(TIME_ENTRY_TYPE_TYPEID)), res.getString(res.getColumnIndex(TIME_ENTRY_TYPE_NAME)), res.getString(res.getColumnIndex(TIME_ENTRY_TYPE_DESCRIPTION))));
                res.moveToNext();
            }
            return timeEntryTypes;
        }catch (Exception ex){
            ExceptionHelper.LogException(ctx,ex);
        }finally {
            if(res!=null)res.close();
            if(db!=null)db.close();
        }
        return null;
    }
    public TimeEntryType GetTimeEntryTypeById(int id){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor res=db.rawQuery("select * from timeentrytype where typeid="+id,null);
        res.moveToFirst();
        while(res.isAfterLast()==false){
            return new TimeEntryType(res.getInt(res.getColumnIndex(TIME_ENTRY_TYPE_TYPEID)),res.getString(res.getColumnIndex(TIME_ENTRY_TYPE_NAME)),res.getString(res.getColumnIndex(TIME_ENTRY_TYPE_DESCRIPTION)));
            //res.moveToNext();
        }
        return null;
    }
    public boolean TruncateTimeEntryTypes(){
        db=getReadableDatabase();
        Cursor res= db.rawQuery("delete from "+TABLE_EXCEPTION,null);
        if(res==null) return false;
        return true;
    }

    public void DeleteWorkOrderById(int workOrderId){
        db=getWritableDatabase();
        db.rawQuery("delete from workorder where workorderid="+workOrderId,null);
    }

    private boolean WorkOrderExists(int workOrderId){
        Cursor res=null;
        try {
            db = getReadableDatabase();
            res=db.rawQuery("select workorderid from workorder where workorderid="+workOrderId,null);
            res.moveToFirst();
            if(!res.isAfterLast()){
                return true;
            }
            return false;

        }catch (Exception ex){
            ExceptionHelper.LogException(ctx,ex);
        }
        finally {
            if(res!=null) res.close();
        }
        return false;

    }

    public void SaveWorkOrder(WorkOrder workOrder) {
        db = this.getWritableDatabase();
        boolean workorderExists=WorkOrderExists(workOrder.getWorkOrderId());
        ContentValues contentValues = new ContentValues();
        contentValues.put(WORKORDER_ID, workOrder.getWorkOrderId());
        contentValues.put(WORKORDER_ARRIVALTIME, workOrder.getArrivalTime());
        contentValues.put(WORKORDER_COMPANYID, workOrder.getCompanyId());
        contentValues.put(WORKORDER_COSTOFJOB, workOrder.getCostOfJob());
        contentValues.put(WORKORDER_DESCRIPTION, workOrder.getDescription());
        contentValues.put(WORKORDER_NAME, workOrder.getName());
        contentValues.put(WORKORDER_PERSONID, workOrder.getPersonId());
        contentValues.put(WORKORDER_WHEREBILLED, workOrder.getWhereBilled());
        contentValues.put(WORKORDER_ESTIMATEDDURATION, workOrder.getEstimatedDurationOfWork());
        contentValues.put(WORKORDER_NOTES, workOrder.getNotes());
        contentValues.put(WORKORDER_TYPEID, workOrder.getWorkOrderTypeId());
        contentValues.put(WORKORDER_COMPLETEDDATE,workOrder.getReadyForInvoiceDateTime());
        contentValues.put(WORKORDER_ISCOMPLETED, workOrder.getIsReadyForInvoice());
        contentValues.put(WORKORDER_SENT, workOrder.getSentToCloud());
        if(workorderExists){
            int success=db.update(TABLE_WORKORDER,contentValues,WORKORDER_ID+"=?",new String[]{String.valueOf(workOrder.getWorkOrderId())});
        }else {

            db.insert(TABLE_WORKORDER, null, contentValues);
            db.close();
        }

    }
    public void SetWorkOrderToSent(int workOrderId){
        Cursor res=null;
        try{
            db=getWritableDatabase();
            res = db.rawQuery("update "+TABLE_WORKORDER+" set "+WORKORDER_SENT+"=1",null);
            res.moveToFirst();
        }catch (Exception ex){
            ExceptionHelper.LogException(ctx,ex);
        }finally {
            if(res!=null)res.close();
        }
    }
    public void DeleteSentWorkOrders(){
        db=getWritableDatabase();
        db.rawQuery("delete from workorder where senttocloud="+1,null);
    }
    public void SaveWorkOrderList(ArrayList<WorkOrder> workOrders){
        db = this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        for (WorkOrder workOrder:
                workOrders) {
            SavePerson(workOrder.getPerson());
            if(workOrder.getPartList()!=null && !workOrder.getPartList().isEmpty()) {
                SaveWorkOrderPartList(workOrder.getWorkOrderId(), workOrder.getPartList());
            }
            contentValues.put(WORKORDER_ID,workOrder.getWorkOrderId());
            contentValues.put(WORKORDER_ARRIVALTIME, workOrder.getArrivalTime());
            contentValues.put(WORKORDER_COMPANYID,workOrder.getCompanyId());
            contentValues.put(WORKORDER_COSTOFJOB, workOrder.getCostOfJob());
            contentValues.put(WORKORDER_DESCRIPTION, workOrder.getDescription());
            contentValues.put(WORKORDER_NAME, workOrder.getName());
            contentValues.put(WORKORDER_PERSONID,workOrder.getPerson().getPersonId());
            contentValues.put(WORKORDER_WHEREBILLED,workOrder.getWhereBilled());
            contentValues.put(WORKORDER_ESTIMATEDDURATION,workOrder.getEstimatedDurationOfWork());
            contentValues.put(WORKORDER_NOTES,workOrder.getNotes());
            contentValues.put(WORKORDER_TYPEID, workOrder.getWorkOrderTypeId());
            contentValues.put(WORKORDER_COMPLETEDDATE,workOrder.getReadyForInvoiceDateTime());
            if(workOrder.getJobTime()!=null && workOrder.getJobTime().getJobId()>0){
                contentValues.put(WORKORDER_ACTUALHOURS,workOrder.getJobTime().getJobId());
                contentValues.put(WORKORDER_PROJECTEDHOURS,workOrder.getJobTime().getProjectedHours());
                contentValues.put(WORKORDER_ACTUALHOURS,workOrder.getJobTime().getActualHours());
            }
            db.insert(TABLE_WORKORDER,null, contentValues);

        }
        //db.close();
    }

    public ArrayList<WorkOrder> GetCompletedWorkOrders(){
        Cursor res=null;
        try{
            ArrayList<WorkOrder> workOrders=new ArrayList<>();
            SQLiteDatabase db=this.getReadableDatabase();
            res=db.rawQuery("select * from "+TABLE_WORKORDER + " where iscompleted=1",null);
            res.moveToFirst();
            while(res.isAfterLast()==false){
                //public WorkOrder(int workOrderId,int workOrderTypeId, int companyId, int personId, String name, String description, String arrivalTime, String estimatedDurationOfWork, double costOfJob,
                // String whereBilled, String notes,
                //      Person person, int totalHoursForJob, int hoursWorkedOnJob)
                JobTime jobTime=new JobTime(res.getInt(res.getColumnIndex(WORKORDER_JOBID)),res.getInt(res.getColumnIndex(WORKORDER_ACTUALHOURS)),res.getInt(res.getColumnIndex(WORKORDER_PROJECTEDHOURS)));
                WorkOrder newWorkOrder=new WorkOrder(res.getInt(res.getColumnIndex(WORKORDER_ID)), res.getInt(res.getColumnIndex(WORKORDER_TYPEID)),res.getInt(res.getColumnIndex(WORKORDER_COMPANYID)), res.getInt(res.getColumnIndex(WORKORDER_PERSONID)),
                        res.getString(res.getColumnIndex(WORKORDER_NAME)), res.getString(res.getColumnIndex(WORKORDER_DESCRIPTION)),res.getString(res.getColumnIndex(WORKORDER_ARRIVALTIME)),res.getString(res.getColumnIndex(WORKORDER_ESTIMATEDDURATION)),
                        res.getDouble(res.getColumnIndex(WORKORDER_COSTOFJOB)),res.getString(res.getColumnIndex(WORKORDER_WHEREBILLED)), res.getString(res.getColumnIndex(WORKORDER_NOTES)), null,res.getInt(res.getColumnIndex(WORKORDER_TOTALHOURSFORJOB)),
                        res.getInt(res.getColumnIndex(WORKORDER_HOURSWORKED)),0, res.getInt(res.getColumnIndex(WORKORDER_ISCOMPLETED)),GetWorkOrderPartList(res.getInt(res.getColumnIndex(WORKORDER_ID))),jobTime);
                newWorkOrder.setReadyForInvoiceDateTime(res.getString(res.getColumnIndex(WORKORDER_COMPLETEDDATE)));
                newWorkOrder.setPerson(GetPersonByPersonId(newWorkOrder.getPersonId()));
                newWorkOrder.setSentToCloud(res.getInt(res.getColumnIndex(WORKORDER_SENT)));
                workOrders.add(newWorkOrder);
                res.moveToNext();
            }
            return workOrders;
        }catch (Exception ex){
            ExceptionHelper.LogException(ctx,ex);
        }finally {
            if(res!=null)res.close();
        }
        return new ArrayList<WorkOrder>();
    }

    public ArrayList<WorkOrder> GetWorkOrders(){
        Cursor res=null;
        try{
            ArrayList<WorkOrder> workOrders=new ArrayList<>();
            SQLiteDatabase db=this.getReadableDatabase();
            res=db.rawQuery("select * from "+TABLE_WORKORDER,null);
            res.moveToFirst();
            while(res.isAfterLast()==false){
                //public WorkOrder(int workOrderId,int workOrderTypeId, int companyId, int personId, String name, String description, String arrivalTime, String estimatedDurationOfWork, double costOfJob,
                // String whereBilled, String notes,
                  //      Person person, int totalHoursForJob, int hoursWorkedOnJob)
                JobTime jobTime=new JobTime(res.getInt(res.getColumnIndex(WORKORDER_JOBID)),res.getInt(res.getColumnIndex(WORKORDER_ACTUALHOURS)),res.getInt(res.getColumnIndex(WORKORDER_PROJECTEDHOURS)));
                WorkOrder newWorkOrder=new WorkOrder(res.getInt(res.getColumnIndex(WORKORDER_ID)), res.getInt(res.getColumnIndex(WORKORDER_TYPEID)),res.getInt(res.getColumnIndex(WORKORDER_COMPANYID)), res.getInt(res.getColumnIndex(WORKORDER_PERSONID)),
                        res.getString(res.getColumnIndex(WORKORDER_NAME)), res.getString(res.getColumnIndex(WORKORDER_DESCRIPTION)),res.getString(res.getColumnIndex(WORKORDER_ARRIVALTIME)),res.getString(res.getColumnIndex(WORKORDER_ESTIMATEDDURATION)),
                        res.getDouble(res.getColumnIndex(WORKORDER_COSTOFJOB)),res.getString(res.getColumnIndex(WORKORDER_WHEREBILLED)), res.getString(res.getColumnIndex(WORKORDER_NOTES)), null,res.getInt(res.getColumnIndex(WORKORDER_TOTALHOURSFORJOB)),
                        res.getInt(res.getColumnIndex(WORKORDER_HOURSWORKED)),0,res.getInt(res.getColumnIndex(WORKORDER_ISCOMPLETED)),null,jobTime);
                newWorkOrder.setReadyForInvoiceDateTime(res.getString(res.getColumnIndex(WORKORDER_COMPLETEDDATE)));
                newWorkOrder.setPerson(GetPersonByPersonId(newWorkOrder.getPersonId()));
                newWorkOrder.setPartList(GetWorkOrderPartList(newWorkOrder.getWorkOrderId()));
                newWorkOrder.setSentToCloud(res.getInt(res.getColumnIndex(WORKORDER_SENT)));
                newWorkOrder.setWorkOrderMaterials(GetWorkOrderMaterialsByWorkOrderId(newWorkOrder.getWorkOrderId()));
                workOrders.add(newWorkOrder);
                res.moveToNext();
            }
            return workOrders;
        }catch (Exception ex){
            ExceptionHelper.LogException(ctx,ex);
        }
        finally {
            if(res!=null)res.close();
        }
        return new ArrayList<WorkOrder>();

    }
    public boolean TruncateWorkOrders(){
        db=getReadableDatabase();
        Cursor res= db.rawQuery("delete from "+TABLE_WORKORDER,null);
        if(res==null) return false;
        return true;
    }


    public void SaveOtherTask(OtherTask otherTask){
        try {
            db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(OTHERTASKS_USERID, otherTask.getUserid());
            contentValues.put(OTHERTASKS_TASKNAME, otherTask.getName());
            contentValues.put(OTHERTASKS_TASKDESCRIPTION, otherTask.getDescription());
            db.insert(TABLE_OTHERTASKS, null, contentValues);
        }catch (Exception ex){

        }finally {
           // db.close();
        }

    }
    public Boolean TruncateOtherTasks(){
        db=getReadableDatabase();
        Cursor res= db.rawQuery("delete from "+TABLE_OTHERTASKS,null);
        if(res==null) return false;
        return true;
    }

    public boolean UserHasOtherTasks(int userId){
        db=getReadableDatabase();
        Cursor res=db.rawQuery("select * from "+TABLE_OTHERTASKS+" where userid="+userId+" limit 1",null);
        res.moveToFirst();
        if(res.isAfterLast()==false){
            return true;
        }

        return false;
    }

    public ArrayList<OtherTask> GetOtherTaskListByUserId(int userId){
        Cursor res=null;
        try{
            ArrayList<OtherTask> otherTasks=new ArrayList<>();
            db=getReadableDatabase();
            res=db.rawQuery("select * from "+TABLE_OTHERTASKS+" where userid="+userId,null);
            res.moveToFirst();
            while(res.isAfterLast()==false){
                //public OtherTask(int id, int userid, String name, String description)
                otherTasks.add(new OtherTask(res.getInt(res.getColumnIndex(OTHERTASKS_ID)),res.getInt(res.getColumnIndex(OTHERTASKS_USERID)),
                        res.getString(res.getColumnIndex(OTHERTASKS_TASKNAME)), res.getString(res.getColumnIndex(OTHERTASKS_TASKDESCRIPTION))));

                res.moveToNext();
            }
            return otherTasks;
        }catch (Exception ex){
            ExceptionHelper.LogException(ctx,ex);
        }
        finally {
            if(res!=null) res.close();
        }
        return new ArrayList<OtherTask>();
    }

    public void SavePerson(Person person){
        Person p=GetPersonByPersonId(person.getPersonId());//if person does not already exist based on the personid
        if(p==null) {
            db = getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(PERSON_PERSONID, person.getPersonId());
            contentValues.put(PERSON_FIRSTNAME, person.getFirstName());
            contentValues.put(PERSON_LASTNAME, person.getLastName());
            contentValues.put(PERSON_FULLNAME, person.getFullName());
            if (person.getAddress() != null) {
                contentValues.put(PERSON_ADDRESSLINE1, person.getAddress().getStreetAddress1());
                contentValues.put(PERSON_ADDRESSLINE2, person.getAddress().getStreetAddress2());
                contentValues.put(PERSON_CITY, person.getAddress().getCity());
                contentValues.put(PERSON_STATE, person.getAddress().getState());
                contentValues.put(PERSON_ZIPCODE, person.getAddress().getZipCode());
            }
            contentValues.put(PERSON_COMPANYID, person.getCompanyId());

            db.insert(TABLE_PERSON, null, contentValues);
        }

    }
    public ArrayList<Person> GetAllPersons(){
        Cursor res=null;
        try {
            ArrayList<Person> personArrayList=new ArrayList<>();
            db = getReadableDatabase();
            res = db.rawQuery("select * from " + TABLE_PERSON , null);
            res.moveToFirst();
            Person selectedPerson = null;
            while (res.isAfterLast() == false) {
                //public OtherTask(int id, int userid, String name, String description)
                selectedPerson = new Person(res.getInt(res.getColumnIndex(PERSON_PERSONID)), res.getInt(res.getColumnIndex(PERSON_COMPANYID)), 0, res.getString(res.getColumnIndex(PERSON_FIRSTNAME)), res.getString(res.getColumnIndex(PERSON_LASTNAME)),
                        res.getString(res.getColumnIndex(PERSON_FULLNAME)), res.getString(res.getColumnIndex(PERSON_ADDRESSLINE1)), res.getString(res.getColumnIndex(PERSON_ADDRESSLINE2)),
                        res.getString(res.getColumnIndex(PERSON_CITY)), res.getString(res.getColumnIndex(PERSON_STATE)), res.getString(res.getColumnIndex(PERSON_ZIPCODE)));
                    personArrayList.add(selectedPerson);
                res.moveToNext();
            }
            return personArrayList;
           // return selectedPerson;
        }catch (Exception ex){
            ExceptionHelper.LogException(ctx,ex);
        }finally {
            if(res!=null) res.close();
        }
        return new ArrayList<>();
    }
    public Person GetPersonByPersonId(int personId){
        Cursor res=null;
        try {
            db = getReadableDatabase();
            res = db.rawQuery("select * from " + TABLE_PERSON + " where personid=" + personId, null);
            res.moveToFirst();
            Person selectedPerson = null;
            while (res.isAfterLast() == false) {
                //public OtherTask(int id, int userid, String name, String description)
                selectedPerson = new Person(res.getInt(res.getColumnIndex(PERSON_PERSONID)), res.getInt(res.getColumnIndex(PERSON_COMPANYID)), 0, res.getString(res.getColumnIndex(PERSON_FIRSTNAME)), res.getString(res.getColumnIndex(PERSON_LASTNAME)),
                        res.getString(res.getColumnIndex(PERSON_FULLNAME)), res.getString(res.getColumnIndex(PERSON_ADDRESSLINE1)), res.getString(res.getColumnIndex(PERSON_ADDRESSLINE2)),
                        res.getString(res.getColumnIndex(PERSON_CITY)), res.getString(res.getColumnIndex(PERSON_STATE)), res.getString(res.getColumnIndex(PERSON_ZIPCODE)));

                res.moveToNext();
            }
            return selectedPerson;
        }catch (Exception ex){
            ExceptionHelper.LogException(ctx,ex);
        }finally {
            if(res!=null) res.close();
        }
        return null;
    }
    public boolean TruncatePerson(){
        db=getReadableDatabase();
        Cursor res= db.rawQuery("delete from "+TABLE_PERSON,null);
        if(res==null) return false;
        return true;
    }

    public boolean CheckIfDayStarted(){
        Cursor res=null;
        try{
            db=getReadableDatabase();
            res=db.rawQuery("select * from timeentry where "+TIMEENTRY_STARTDATE+" like '%"+DateHelper.GetTodayDateAsString()+"%' and "+TIMEENTRY_TIMEENTRYID+"="+GetTimeEntryTypeByName("start").getTimeEntryTypeId(),null);
            res.moveToFirst();
            if(!res.isAfterLast()) return true;
            return false;

        }catch (Exception ex){
            ExceptionHelper.LogException(ctx,ex);
        }finally {
            if(res!=null)res.close();
        }
        return false;
    }
    public boolean CheckIfDayEnded(){
        Cursor res=null;
        try{
            db=getReadableDatabase();
            res=db.rawQuery("select * from timeentry where "+TIMEENTRY_STARTDATE+" like '%"+DateHelper.GetTodayDateAsString()+"%' and "+TIMEENTRY_TIMEENTRYID+"="+GetTimeEntryTypeByName("end").getTimeEntryTypeId(),null);
            res.moveToFirst();
            if(!res.isAfterLast()) return true;
            return false;

        }catch (Exception ex){
            ExceptionHelper.LogException(ctx,ex);
        }finally {
            if(res!=null)res.close();
        }
        return false;
    }

    /**Inserts a time entry
     * Checks for possible duplicate entries on start and end day and prevents them from being added to the table
     * */
    public void InsertTimeEntry(TimeEntry timeEntry){
        try {
            db = getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            boolean dayStarted = false;
            if (timeEntry.getTimeEntryTypeId() == GetTimeEntryTypeByName("start").getTimeEntryTypeId()) {
                dayStarted = CheckIfDayStarted();
            }
            if (!dayStarted) {
                contentValues.put(TIMEENTRY_TIMEENTRYID, timeEntry.getTimeEntryId());
                contentValues.put(TIMEENTRY_EMPLOYEEID, timeEntry.getEmployeeId());
                contentValues.put(TIMEENTRY_DATEENTERED, DateHelper.DateToString(timeEntry.getDateEntered()));
                contentValues.put(TIMEENTRY_STARTDATE, DateHelper.DateToString(timeEntry.getStartDateTime()));
                // contentValues.put(TIMEENTRY_ENDDATE, DateHelper.DateToString(timeEntry.getEndDateTime()));
                contentValues.put(TIMEENTRY_WORKORDERID, timeEntry.getWorkOrderId());
                contentValues.put(TIMEENTRY_STARTLATITUDE, timeEntry.getStartLatitude());
                contentValues.put(TIMEENTRY_STARTLONGITUDE, timeEntry.getStartLongitude());
                // contentValues.put(TIMEENTRY_ENDLATITUDE,timeEntry.getEndLatitude());
                //contentValues.put(TIMEENTRY_ENDLONGITUDE,timeEntry.getEndLongitude());
                contentValues.put(TIMEENTRY_NOTES, timeEntry.getNotes());
                contentValues.put(TIMEENTRY_TIMEENTRYTYPEID, timeEntry.getTimeEntryTypeId());
                contentValues.put(TIMEENTRY_ACCEPTED, 0);
                db.insert(TABLE_TIMEENTRY, null, contentValues);
            }
        }catch (Exception ex){
            ExceptionHelper.LogException(ctx,ex);
        }
    }

    public void UpdateTimeEntry(TimeEntry timeEntry){
        try{
            db = getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(TIMEENTRY_TIMEENTRYID, timeEntry.getTimeEntryId());
            contentValues.put(TIMEENTRY_EMPLOYEEID, timeEntry.getEmployeeId());
            contentValues.put(TIMEENTRY_DATEENTERED, DateHelper.DateToString(timeEntry.getDateEntered()));
            contentValues.put(TIMEENTRY_STARTDATE, DateHelper.DateToString(timeEntry.getStartDateTime()));
            // contentValues.put(TIMEENTRY_ENDDATE, DateHelper.DateToString(timeEntry.getEndDateTime()));
            contentValues.put(TIMEENTRY_WORKORDERID, timeEntry.getWorkOrderId());
            contentValues.put(TIMEENTRY_STARTLATITUDE, timeEntry.getStartLatitude());
            contentValues.put(TIMEENTRY_STARTLONGITUDE, timeEntry.getStartLongitude());
            // contentValues.put(TIMEENTRY_ENDLATITUDE,timeEntry.getEndLatitude());
            //contentValues.put(TIMEENTRY_ENDLONGITUDE,timeEntry.getEndLongitude());
            contentValues.put(TIMEENTRY_NOTES, timeEntry.getNotes());
            contentValues.put(TIMEENTRY_TIMEENTRYTYPEID, timeEntry.getTimeEntryTypeId());
            contentValues.put(TIMEENTRY_ACCEPTED, timeEntry.isAccepted()?1:0);
        }catch(Exception ex){
            ExceptionHelper.LogException(ctx,ex);
        }
    }

    public boolean AcceptTimeEntryListForToday(){
        Cursor res=null;
        try{
            db=getWritableDatabase();
            ArrayList<TimeEntry> timeEntries=GetTimeEntryListForToday();
            for(TimeEntry timeEntry:timeEntries){
                timeEntry.setAccepted(true);
                res=db.rawQuery("update "+TABLE_TIMEENTRY +" set accepted=1 where id="+timeEntry.getSqlId(),null);
                res.moveToFirst();
                if(res.isAfterLast()){
                    throw new Exception("Update failed on id "+timeEntry.getSqlId());
                }
            }

            return true;
        }catch (Exception ex){
            ExceptionHelper.LogException(ctx,ex);
        }
        finally {
            if(res!=null)res.close();
        }
        return false;

    }


    /**Gets the Time Entry List for current date
     * */
    public ArrayList<TimeEntry> GetTimeEntryListForToday(){
        Cursor res=null;
        try {
            db = getReadableDatabase();
            res = db.rawQuery("select * from " + TABLE_TIMEENTRY + " where dateentered like '%" + DateHelper.GetTodayDateAsString() + "%' order by " + TIMEENTRY_STARTDATE + " asc", null);
            res.moveToFirst();
            ArrayList<TimeEntry> timeEntries = new ArrayList<>();
            TimeEntry previousTimeEntry = new TimeEntry();
            while (res.isAfterLast() == false) {
                // public TimeEntry(int timeEntryId,int employeeId,Date dateEntered, Date startDateTime, Date endDateTime, int workOrderId, double startLatitude, double startLongitude, double endLatitude, double endLongitude, String notes){
                TimeEntry newTimeEntry = new TimeEntry(res.getInt(res.getColumnIndex(TIMEENTRY_TIMEENTRYID)), res.getInt(res.getColumnIndex(TIMEENTRY_EMPLOYEEID)),
                        DateHelper.StringToDate(res.getString(res.getColumnIndex(TIMEENTRY_DATEENTERED))), DateHelper.StringToDate(res.getString(res.getColumnIndex(TIMEENTRY_STARTDATE))),
                        DateHelper.StringToDate(res.getString(res.getColumnIndex(TIMEENTRY_ENDDATE))), res.getInt(res.getColumnIndex(TIMEENTRY_WORKORDERID)),
                        res.getDouble(res.getColumnIndex(TIMEENTRY_STARTLATITUDE)), res.getDouble(res.getColumnIndex(TIMEENTRY_STARTLONGITUDE)),
                        res.getDouble(res.getColumnIndex(TIMEENTRY_ENDLATITUDE)), res.getDouble(res.getColumnIndex(TIMEENTRY_ENDLONGITUDE)),
                        res.getString(res.getColumnIndex(TIMEENTRY_NOTES)), res.getInt(res.getColumnIndex(TIMEENTRY_ID)), res.getInt(res.getColumnIndex(TIMEENTRY_TIMEENTRYTYPEID)),res.getInt(res.getColumnIndex(TIMEENTRY_ACCEPTED))==1?true:false);
                if (previousTimeEntry.getSqlId() != 0) {
                    long difference = newTimeEntry.getStartDateTime().getTime() - previousTimeEntry.getStartDateTime().getTime();//newer.startTime-older.startTime
                    Time time = new Time(difference);
                    while (time.getMinutes() >= 30) {
                        timeEntries.add(new TimeEntry());
                        time.setMinutes(time.getMinutes() - 30);
                    }
                }
                if (newTimeEntry.getTimeEntryTypeId() != 0) {
                    newTimeEntry.setType(GetTimeEntryTypeById(newTimeEntry.getTimeEntryTypeId()));
                }

                timeEntries.add(newTimeEntry);
                previousTimeEntry = newTimeEntry;
                res.moveToNext();
            }
            return timeEntries;
        }catch (Exception ex){
            ExceptionHelper.LogException(ctx,ex);
        }
        finally {
            if(res!=null) res.close();
        }
        return new ArrayList<>();
    }
    /**
     * @param id This is the id assigned by SQLite*/
    public TimeEntry GetTimeEntryById(int id){

        db=getReadableDatabase();
        Cursor res=db.rawQuery("select * from "+TABLE_TIMEENTRY+" where id="+id,null);
        res.moveToFirst();

        TimeEntry timeEntry=new TimeEntry();
        while(res.isAfterLast()==false) {
            // public TimeEntry(int timeEntryId,int employeeId,Date dateEntered, Date startDateTime, Date endDateTime, int workOrderId, double startLatitude, double startLongitude, double endLatitude, double endLongitude, String notes){
            timeEntry=new TimeEntry(res.getInt(res.getColumnIndex(TIMEENTRY_TIMEENTRYID)),res.getInt(res.getColumnIndex(TIMEENTRY_EMPLOYEEID)),
                    DateHelper.StringToDate(res.getString(res.getColumnIndex(TIMEENTRY_DATEENTERED))),DateHelper.StringToDate(res.getString(res.getColumnIndex(TIMEENTRY_STARTDATE))),
                    DateHelper.StringToDate(res.getString(res.getColumnIndex(TIMEENTRY_ENDDATE))), res.getInt(res.getColumnIndex(TIMEENTRY_WORKORDERID)),res.getDouble(res.getColumnIndex(TIMEENTRY_STARTLATITUDE)),
                    res.getDouble(res.getColumnIndex(TIMEENTRY_STARTLONGITUDE)), res.getDouble(res.getColumnIndex(TIMEENTRY_ENDLATITUDE)), res.getDouble(res.getColumnIndex(TIMEENTRY_ENDLONGITUDE)),
                    res.getString(res.getColumnIndex(TIMEENTRY_NOTES)), res.getInt(res.getColumnIndex(TIMEENTRY_ID)), res.getInt(res.getColumnIndex(TIMEENTRY_TIMEENTRYTYPEID)),res.getInt(res.getColumnIndex(TIMEENTRY_ACCEPTED))==1?true:false);
            break;
            //res.moveToNext();
        }
        return timeEntry;
    }
    public boolean TruncateTimeEntry(){
        db=getReadableDatabase();
        Cursor res= db.rawQuery("delete from "+TABLE_TIMEENTRY,null);
        if(res==null) return false;
        return true;
    }

    public TimeEntryType GetTimeEntryTypeByName(String name){
        ArrayList<TimeEntryType> timeEntryTypes=new ArrayList<>();
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor res=db.rawQuery("select * from timeentrytype where "+TIME_ENTRY_TYPE_NAME+" like '%"+name+"%'",null);
        res.moveToFirst();
        TimeEntryType selectedTimeEntryType=null;
        while(res.isAfterLast()==false){
            selectedTimeEntryType= new TimeEntryType(res.getInt(res.getColumnIndex(TIME_ENTRY_TYPE_TYPEID)),res.getString(res.getColumnIndex(TIME_ENTRY_TYPE_NAME)),
                    res.getString(res.getColumnIndex(TIME_ENTRY_TYPE_DESCRIPTION)));

            res.moveToNext();
        }
        return selectedTimeEntryType;
    }

    public void SaveExceptionLog(Exception ex){
        db=getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(EXCEPTION_DATEINSERTED, DateHelper.DateToString(new Date()));
        contentValues.put(EXCEPTION_USERID, Global.GetInstance().getUser().GetUserId());
        contentValues.put(EXCEPTION_MESSAGE,ex.getMessage());
        contentValues.put(EXCEPTION_STACKTRACE, ex.getStackTrace().toString());
        db.insert(TABLE_EXCEPTION,null,contentValues);
    }
    public boolean TruncateException(){
        db=getReadableDatabase();
        Cursor res= db.rawQuery("delete from "+TABLE_EXCEPTION,null);
        if(res==null) return false;
        return true;
    }
    public ArrayList<FOException> GetExceptions(){
        Cursor res=null;
        try {
            db = getReadableDatabase();
            res = db.rawQuery("select * from " + TABLE_EXCEPTION, null);
            res.moveToFirst();
            ArrayList<FOException> exceptions = new ArrayList<>();
            while (res.isAfterLast() == false) {

                res.moveToNext();
            }
            return exceptions;
        }
        catch (Exception ex){
            ExceptionHelper.LogException(ctx,ex);
        }
        finally {
            if(res!=null)res.close();
        }
        return new ArrayList<>();
    }

    public void SaveWorkOrderPartList(int workorderId, ArrayList<WorkOrderPart> parts){
        Cursor res=null;
        try{
            DeleteAllPartsFromWorkOrder(workorderId);
            for (WorkOrderPart part:parts) {
                if(GetPartById(part.getPartId())==null ){
                    SavePart(part);
                }

                SaveWorkOrderPart(workorderId, part.getPartId(),part.getQuantity(),part.getFlatRateItem());

            }
        }catch (Exception ex){
            ExceptionHelper.LogException(ctx,ex);
        }finally {
            if(res!=null)res.close();
        }
    }

    public void UpdateWorkOrderPartQuantity(int workOrderId, int partId, int newQuantity){
        Cursor res=null;
        try{
            db=getWritableDatabase();
            res=db.rawQuery("update workorderpart set quantity="+newQuantity+" where workorderid="+workOrderId+" and partid="+partId,null);
            res.moveToFirst();
        }
        catch (Exception ex){
            ExceptionHelper.LogException(ctx,ex);
        }finally {
            if(res!=null) res.close();
        }
    }

    public void SaveWorkOrderPart(int workorderId, int partId, int quantity, FlatRateItem flatRateItem){
        try{
            db=getReadableDatabase();
            ContentValues contentValues=new ContentValues();
            contentValues.put(WORKORDERPART_WORKORDERID,workorderId);
            contentValues.put(WORKORDERPART_PARTID, partId);
            contentValues.put(WORKORDERPART_QUANTITY,quantity);
            if(flatRateItem!=null) {
                contentValues.put(WORKORDERPART_FLATRATEITEMID, flatRateItem.getFlatRateItemId());
            }else contentValues.put(WORKORDERPART_FLATRATEITEMID,0);
            db.insert(TABLE_WORKORDERPART,null, contentValues);
        }catch (Exception ex){
            ExceptionHelper.LogException(ctx,ex);
        }
    }
    public ArrayList<WorkOrderPart> GetWorkOrderPartList(int workorderid){
        Cursor res=null;
        try{
            ArrayList<WorkOrderPart> partList=new ArrayList<>();
            db=getWritableDatabase();
            res= db.rawQuery("select * from "+TABLE_WORKORDERPART+" where workorderid="+workorderid,null);
            res.moveToFirst();
            while(res.isAfterLast()==false){
                WorkOrderPart newPart=new WorkOrderPart(GetPartById(res.getInt(res.getColumnIndex(WORKORDERPART_PARTID))),res.getInt(res.getColumnIndex(WORKORDERPART_QUANTITY)),GetFlatRateItemById(res.getInt(res.getColumnIndex(WORKORDERPART_FLATRATEITEMID))) );
                if(newPart!=null){
                    partList.add(newPart);
                }
                res.moveToNext();
            }
            return partList;
        }catch (Exception ex){
            ExceptionHelper.LogException(ctx,ex);
        }finally {
            if(res!=null) res.close();
        }
        return new ArrayList<>();
    }
    public boolean TruncateWorkOrderPartList(){
        Cursor res=null;
        try{
            db=getReadableDatabase();
            res= db.rawQuery("delete from "+TABLE_WORKORDERPART,null);
            if(res==null) return false;
            return true;
        }catch (Exception ex){
            ExceptionHelper.LogException(ctx,ex);
        }finally {
            if(res!=null) res.close();
        }
        return false;
    }
    public boolean DeleteAllPartsFromWorkOrder(int workorderid){
        Cursor res=null;
        try{

        }catch (Exception ex){
            ExceptionHelper.LogException(ctx,ex);
        }
        finally {
            if(res!=null) res.close();
        }
        return false;
    }

}
