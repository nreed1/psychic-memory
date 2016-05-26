package com.example.niki.fieldoutlookandroid.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.niki.fieldoutlookandroid.businessobjects.OtherTask;
import com.example.niki.fieldoutlookandroid.businessobjects.Person;
import com.example.niki.fieldoutlookandroid.businessobjects.TimeEntryType;
import com.example.niki.fieldoutlookandroid.businessobjects.WorkOrder;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Owner on 4/1/2016.
 */
public class DBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME="fodatabase.db";
    public static final String TABLE_TIME_ENTRY_TYPE="timeentrytype";
    public static final String TIME_ENTRY_TYPE_TYPEID="typeid";
    public static final String TIME_ENTRY_TYPE_NAME="name";
    public static final String TIME_ENTRY_TYPE_DESCRIPTION="description";

    public static final String  TABLE_WORKORDER="workorder";
    public static final String  WORKORDER_ID="workorderid";
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

    public static final String TABLE_OTHERTASKS="othertasks";
    public static final String OTHERTASKS_ID="id";
    public static final String OTHERTASKS_USERID="userid";
    public static final String OTHERTASKS_TASKNAME="taskname";
    public static final String OTHERTASKS_TASKDESCRIPTION="description";

    SQLiteDatabase db;

    public DBHelper(Context context){
        super(context,DATABASE_NAME,null, 1);
        db=this.getWritableDatabase();
       // create();
    }
private void create(){

}
    @Override
    public void onCreate(SQLiteDatabase db) {
     //   File file = new File(DATABASE_NAME);

       // if (file.exists() && !file.isDirectory()) {
        //{

            //db = SQLiteDatabase.openOrCreateDatabase(DATABASE_NAME, null);
            db.execSQL("create table if not exists timeentrytype (id integer primary key, typeid integer,name text, description text)");
            db.execSQL("create table if not exists workorder (id integer primary key, workorderid integer, companyid integer, personid integer, name text, description text, " +
                    "arrivaltime string, estimatedduration real," +
                    "costofjob real, wherebilled text, notes text, workordertypeid integer, totalhoursalotted integer, hoursworked integer)");
            db.execSQL("create table if not exists othertasks(id integer primary key, taskname text, userid integer, description text)");
        //}
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void SaveTimeEntryType(TimeEntryType timeEntryType){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(TIME_ENTRY_TYPE_TYPEID, timeEntryType.getTimeEntryTypeId());
        contentValues.put(TIME_ENTRY_TYPE_NAME, timeEntryType.getName());
        contentValues.put(TIME_ENTRY_TYPE_DESCRIPTION, timeEntryType.getDescription());
        db.insert(TABLE_TIME_ENTRY_TYPE, null,contentValues);
    }
    public ArrayList<TimeEntryType> getTimeEntryTypeList(){
        ArrayList<TimeEntryType> timeEntryTypes=new ArrayList<>();
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor res=db.rawQuery("select * from timeentrytype",null);
        res.moveToFirst();
        while(res.isAfterLast()==false){
            timeEntryTypes.add(new TimeEntryType(res.getInt(res.getColumnIndex(TIME_ENTRY_TYPE_TYPEID)),res.getString(res.getColumnIndex(TIME_ENTRY_TYPE_NAME)),res.getString(res.getColumnIndex(TIME_ENTRY_TYPE_DESCRIPTION))));
            res.moveToNext();
        }
        return timeEntryTypes;
    }

    public void SaveWorkOrder(WorkOrder workOrder) {
        db = this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(WORKORDER_ID,workOrder.getWorkOrderId());
        contentValues.put(WORKORDER_ARRIVALTIME, workOrder.getArrivalTime());
        contentValues.put(WORKORDER_COMPANYID,workOrder.getCompanyId());
        contentValues.put(WORKORDER_COSTOFJOB, workOrder.getCostOfJob());
        contentValues.put(WORKORDER_DESCRIPTION, workOrder.getDescription());
        contentValues.put(WORKORDER_NAME, workOrder.getName());
        contentValues.put(WORKORDER_PERSONID,workOrder.getPersonId());
        contentValues.put(WORKORDER_WHEREBILLED,workOrder.getWhereBilled());
        contentValues.put(WORKORDER_ESTIMATEDDURATION,workOrder.getEstimatedDurationOfWork());
        contentValues.put(WORKORDER_NOTES,workOrder.getNotes());
        contentValues.put(WORKORDER_TYPEID, workOrder.getWorkOrderTypeId());
        db.insert(TABLE_WORKORDER,null, contentValues);
        db.close();

    }
    public void SaveWorkOrderList(ArrayList<WorkOrder> workOrders){
        db = this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        for (WorkOrder workOrder:
                workOrders) {
            contentValues.put(WORKORDER_ID,workOrder.getWorkOrderId());
            contentValues.put(WORKORDER_ARRIVALTIME, workOrder.getArrivalTime());
            contentValues.put(WORKORDER_COMPANYID,workOrder.getCompanyId());
            contentValues.put(WORKORDER_COSTOFJOB, workOrder.getCostOfJob());
            contentValues.put(WORKORDER_DESCRIPTION, workOrder.getDescription());
            contentValues.put(WORKORDER_NAME, workOrder.getName());
            contentValues.put(WORKORDER_PERSONID,workOrder.getPersonId());
            contentValues.put(WORKORDER_WHEREBILLED,workOrder.getWhereBilled());
            contentValues.put(WORKORDER_ESTIMATEDDURATION,workOrder.getEstimatedDurationOfWork());
            contentValues.put(WORKORDER_NOTES,workOrder.getNotes());
            contentValues.put(WORKORDER_TYPEID, workOrder.getWorkOrderTypeId());
            db.insert(TABLE_WORKORDER,null, contentValues);

        }
        //db.close();
    }
    public ArrayList<WorkOrder> GetWorkOrders(){
        try{
            ArrayList<WorkOrder> workOrders=new ArrayList<>();
            SQLiteDatabase db=this.getReadableDatabase();
            Cursor res=db.rawQuery("select * from "+TABLE_WORKORDER,null);
            res.moveToFirst();
            while(res.isAfterLast()==false){
                //public WorkOrder(int workOrderId,int workOrderTypeId, int companyId, int personId, String name, String description, String arrivalTime, String estimatedDurationOfWork, double costOfJob,
                // String whereBilled, String notes,
                  //      Person person, int totalHoursForJob, int hoursWorkedOnJob)
                workOrders.add(new WorkOrder(res.getInt(res.getColumnIndex(WORKORDER_ID)), res.getInt(res.getColumnIndex(WORKORDER_TYPEID)),res.getInt(res.getColumnIndex(WORKORDER_COMPANYID)), res.getInt(res.getColumnIndex(WORKORDER_PERSONID)),
                        res.getString(res.getColumnIndex(WORKORDER_NAME)), res.getString(res.getColumnIndex(WORKORDER_DESCRIPTION)),res.getString(res.getColumnIndex(WORKORDER_ARRIVALTIME)),res.getString(res.getColumnIndex(WORKORDER_ESTIMATEDDURATION)),
                        res.getDouble(res.getColumnIndex(WORKORDER_COSTOFJOB)),res.getString(res.getColumnIndex(WORKORDER_WHEREBILLED)), res.getString(res.getColumnIndex(WORKORDER_NOTES)), null,res.getInt(res.getColumnIndex(WORKORDER_TOTALHOURSFORJOB)),
                        res.getInt(res.getColumnIndex(WORKORDER_HOURSWORKED))));
                res.moveToNext();
            }
            return workOrders;
        }catch (Exception ex){
            System.out.print(ex.getStackTrace());
        }
        return new ArrayList<WorkOrder>();

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
        try{
            ArrayList<OtherTask> otherTasks=new ArrayList<>();
            db=getReadableDatabase();
            Cursor res=db.rawQuery("select * from "+TABLE_OTHERTASKS+" where userid="+userId,null);
            res.moveToFirst();
            while(res.isAfterLast()==false){
                //public OtherTask(int id, int userid, String name, String description)
                otherTasks.add(new OtherTask(res.getInt(res.getColumnIndex(OTHERTASKS_ID)),res.getInt(res.getColumnIndex(OTHERTASKS_USERID)),
                        res.getString(res.getColumnIndex(OTHERTASKS_TASKNAME)), res.getString(res.getColumnIndex(OTHERTASKS_TASKDESCRIPTION))));

                res.moveToNext();
            }
            return otherTasks;
        }catch (Exception ex){

        }
        return new ArrayList<OtherTask>();
    }


}
