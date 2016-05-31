package com.example.niki.fieldoutlookandroid.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.niki.fieldoutlookandroid.businessobjects.OtherTask;
import com.example.niki.fieldoutlookandroid.businessobjects.Person;
import com.example.niki.fieldoutlookandroid.businessobjects.TimeEntry;
import com.example.niki.fieldoutlookandroid.businessobjects.TimeEntryType;
import com.example.niki.fieldoutlookandroid.businessobjects.WorkOrder;

import java.io.File;
import java.sql.Time;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * Created by Owner on 4/1/2016.
 */
public class DBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME="fodatabase.db";
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

        //#start timekeeping
            db.execSQL("create table if not exists timeentrytype (id integer primary key, typeid integer,name text, description text)");
            db.execSQL("create table if not exists timeentry (id integer primary key, timeentryid integer, employeeid integer, dateentered text, startdate text, enddate text, workorderid integer," +
                    "startlatitude real, startlongitude real, endlatitude real, endlongitude real, notes text)");
        //#end timekeeping
        //#start workorder
            db.execSQL("create table if not exists workorder (id integer primary key, workorderid integer, companyid integer, personid integer, name text, description text, " +
                    "arrivaltime string, estimatedduration real," +
                    "costofjob real, wherebilled text, notes text, workordertypeid integer, totalhoursalotted integer, hoursworked integer)");
        //#end workorder

        //#start other tasks
            db.execSQL("create table if not exists othertasks(id integer primary key, taskname text, userid integer, description text)");
        //#end other tasks
        //#start person
            db.execSQL("create table if not exists person (id integer primary key, personid integer, companyid integer,firstname text, lastname text, fullname text, addressline1 text, addressline2 text, city text, state text, zipcode text," +
                    "phonenumber text)");
        //#end person

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
            SavePerson(workOrder.getPerson());
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
                WorkOrder newWorkOrder=new WorkOrder(res.getInt(res.getColumnIndex(WORKORDER_ID)), res.getInt(res.getColumnIndex(WORKORDER_TYPEID)),res.getInt(res.getColumnIndex(WORKORDER_COMPANYID)), res.getInt(res.getColumnIndex(WORKORDER_PERSONID)),
                        res.getString(res.getColumnIndex(WORKORDER_NAME)), res.getString(res.getColumnIndex(WORKORDER_DESCRIPTION)),res.getString(res.getColumnIndex(WORKORDER_ARRIVALTIME)),res.getString(res.getColumnIndex(WORKORDER_ESTIMATEDDURATION)),
                        res.getDouble(res.getColumnIndex(WORKORDER_COSTOFJOB)),res.getString(res.getColumnIndex(WORKORDER_WHEREBILLED)), res.getString(res.getColumnIndex(WORKORDER_NOTES)), null,res.getInt(res.getColumnIndex(WORKORDER_TOTALHOURSFORJOB)),
                        res.getInt(res.getColumnIndex(WORKORDER_HOURSWORKED)),0);
                newWorkOrder.setPerson(GetPersonByPersonId(newWorkOrder.getPersonId()));
                workOrders.add(newWorkOrder);
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

    public void SavePerson(Person person){
        db=getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(PERSON_PERSONID, person.getPersonId());
        contentValues.put(PERSON_FIRSTNAME,person.getFirstName());
        contentValues.put(PERSON_LASTNAME,person.getLastName());
        contentValues.put(PERSON_FULLNAME, person.getFullName());
        if(person.getAddress()!=null) {
            contentValues.put(PERSON_ADDRESSLINE1, person.getAddress().getStreetAddress1());

        }
        contentValues.put(PERSON_COMPANYID,person.getCompanyId());

        db.insert(TABLE_PERSON,null, contentValues);

    }
    public Person GetPersonByPersonId(int personId){
        db=getReadableDatabase();
        Cursor res=db.rawQuery("select * from "+TABLE_PERSON+" where personid="+personId,null);
        res.moveToFirst();
        Person selectedPerson=null;
        while(res.isAfterLast()==false){
            //public OtherTask(int id, int userid, String name, String description)
          selectedPerson=new Person( res.getInt(res.getColumnIndex(PERSON_PERSONID)),res.getInt(res.getColumnIndex(PERSON_COMPANYID)),0,res.getString(res.getColumnIndex(PERSON_FIRSTNAME)),res.getString(res.getColumnIndex(PERSON_LASTNAME)),
                  res.getString(res.getColumnIndex(PERSON_FULLNAME)), res.getString(res.getColumnIndex(PERSON_ADDRESSLINE1)), res.getString(res.getColumnIndex(PERSON_ADDRESSLINE2)),
                  res.getString(res.getColumnIndex(PERSON_CITY)),res.getString(res.getColumnIndex(PERSON_STATE)), res.getString(res.getColumnIndex(PERSON_ZIPCODE)));

            res.moveToNext();
        }
        return selectedPerson;
    }

    public void SaveTimeEntry(TimeEntry timeEntry){
        db=getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(TIMEENTRY_TIMEENTRYID,timeEntry.getTimeEntryId());
        contentValues.put(TIMEENTRY_EMPLOYEEID,timeEntry.getEmployeeId());
        contentValues.put(TIMEENTRY_DATEENTERED, DateHelper.DateToString(timeEntry.getDateEntered()));
        contentValues.put(TIMEENTRY_STARTDATE, DateHelper.DateToString(timeEntry.getStartDateTime()));
        contentValues.put(TIMEENTRY_ENDDATE, DateHelper.DateToString(timeEntry.getEndDateTime()));
        contentValues.put(TIMEENTRY_WORKORDERID,timeEntry.getWorkOrderId());
        contentValues.put(TIMEENTRY_STARTLATITUDE,timeEntry.getStartLatitude());
        contentValues.put(TIMEENTRY_STARTLONGITUDE,timeEntry.getStartLongitude());
        contentValues.put(TIMEENTRY_ENDLATITUDE,timeEntry.getEndLatitude());
        contentValues.put(TIMEENTRY_ENDLONGITUDE,timeEntry.getEndLongitude());
        contentValues.put(TIMEENTRY_NOTES,timeEntry.getNotes());
        db.insert(TABLE_TIMEENTRY,null, contentValues);
    }

    public ArrayList<TimeEntry> GetTimeEntryListForToday(){
        db=getReadableDatabase();
        Cursor res=db.rawQuery("select * from "+TABLE_TIMEENTRY+" where dateentered like '%"+DateHelper.GetTodayDateAsString()+"%' order by "+TIMEENTRY_STARTDATE +" asc",null);
        res.moveToFirst();
        ArrayList<TimeEntry> timeEntries=new ArrayList<>();
        TimeEntry previousTimeEntry=new TimeEntry();
        while(res.isAfterLast()==false) {
            // public TimeEntry(int timeEntryId,int employeeId,Date dateEntered, Date startDateTime, Date endDateTime, int workOrderId, double startLatitude, double startLongitude, double endLatitude, double endLongitude, String notes){
            TimeEntry newTimeEntry=new TimeEntry(res.getInt(res.getColumnIndex(TIMEENTRY_TIMEENTRYID)),res.getInt(res.getColumnIndex(TIMEENTRY_EMPLOYEEID)),
                    DateHelper.StringToDate(res.getString(res.getColumnIndex(TIMEENTRY_DATEENTERED))),DateHelper.StringToDate(res.getString(res.getColumnIndex(TIMEENTRY_STARTDATE))),
                    DateHelper.StringToDate(res.getString(res.getColumnIndex(TIMEENTRY_ENDDATE))), res.getInt(res.getColumnIndex(TIMEENTRY_WORKORDERID)),
                    res.getDouble(res.getColumnIndex(TIMEENTRY_STARTLATITUDE)),res.getDouble(res.getColumnIndex(TIMEENTRY_STARTLONGITUDE)),
                    res.getDouble(res.getColumnIndex(TIMEENTRY_ENDLATITUDE)), res.getDouble(res.getColumnIndex(TIMEENTRY_ENDLONGITUDE)),
                    res.getString(res.getColumnIndex(TIMEENTRY_NOTES)));
            if(previousTimeEntry.getTimeEntryId()!=0){
                long difference = newTimeEntry.getStartDateTime().getTime()-previousTimeEntry.getStartDateTime().getTime();//newer.startTime-older.startTime
                Time time= new Time(difference);
                while(time.getMinutes()>=30) {
                    timeEntries.add(new TimeEntry());
                    time.setMinutes(time.getMinutes() - 30);
                }
            }

            timeEntries.add(newTimeEntry);
            res.moveToNext();
        }
        return timeEntries;
    }
    /*This gets the TimeEntry by the id assigned by SQLite*/
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
                    res.getString(res.getColumnIndex(TIMEENTRY_NOTES)));
            break;
            //res.moveToNext();
        }
        return timeEntry;
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

}
