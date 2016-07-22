package com.example.niki.fieldoutlookandroid.businessobjects;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.niki.fieldoutlookandroid.helper.DateHelper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by NReed on 4/1/2016.
 */
public class TimeEntry implements Parcelable,Serializable {
    @Expose
    private TimeEntryType type;
    @Expose
    private int timeEntryTypeId;
    @Expose
    private int timeEntryId;
    @Expose
    private int employeeId;
    @Expose
    private Date dateEntered;
    @Expose
    private Date startDateTime;
    @Expose
    private Date endDateTime;
    @Expose
    private int workOrderId;
    @Expose
    private double startLatitude;
    @Expose
    private double startLongitude;
    @Expose
    private double endLatitude;
    private double endLongitude;
    @Expose
    private String notes;
    private int sqlId;
    private boolean accepted;
    @Expose
    private int otherTaskId;


    public TimeEntry(){
        sqlId=0;

    }
    public TimeEntry(int timeEntryId,int employeeId,Date dateEntered, Date startDateTime, Date endDateTime, int workOrderId, double startLatitude, double startLongitude, double endLatitude, double endLongitude, String notes, int sqlId, int timeEntryTypeId, boolean isAccepted, int otherTaskId){
        this.timeEntryId=timeEntryId;
        this.employeeId=employeeId;
        this.dateEntered=dateEntered;
        this.startDateTime=startDateTime;
        this.endDateTime=endDateTime;
        this.workOrderId=workOrderId;
        this.startLatitude=startLatitude;
        this.startLongitude=startLongitude;
        this.endLatitude=endLatitude;
        this.endLongitude=endLongitude;
        this.timeEntryTypeId=timeEntryTypeId;
        this.notes=notes;
        this.sqlId=sqlId;
        this.accepted=isAccepted;
        this.otherTaskId=otherTaskId;
    }


    public TimeEntryType getType() {
        return type;
    }

    public void setType(TimeEntryType type) {
        this.type = type;
    }

    public int getTimeEntryId() {
        return timeEntryId;
    }

    public void setTimeEntryId(int timeEntryId) {
        this.timeEntryId = timeEntryId;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public Date getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(Date startDateTime) {
        this.startDateTime = startDateTime;
    }

    public Date getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(Date endDateTime) {
        this.endDateTime = endDateTime;
    }

    public int getWorkOrderId() {
        return workOrderId;
    }

    public void setWorkOrderId(int workOrderId) {
        this.workOrderId = workOrderId;
    }

    public double getStartLatitude() {
        return startLatitude;
    }

    public void setStartLatitude(double startLatitude) {
        this.startLatitude = startLatitude;
    }

    public double getStartLongitude() {
        return startLongitude;
    }

    public void setStartLongitude(double startLongitude) {
        this.startLongitude = startLongitude;
    }

    public double getEndLatitude() {
        return endLatitude;
    }

    public void setEndLatitude(double endLatitude) {
        this.endLatitude = endLatitude;
    }

    public double getEndLongitude() {
        return endLongitude;
    }

    public void setEndLongitude(double endLongitude) {
        this.endLongitude = endLongitude;
    }

    public Date getDateEntered() {
        return dateEntered;
    }

    public void setDateEntered(Date dateEntered) {
        this.dateEntered = dateEntered;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public int getSqlId() {
        return sqlId;
    }

    public void setSqlId(int sqlId) {
        this.sqlId = sqlId;
    }

    public int getTimeEntryTypeId() {
        return timeEntryTypeId;
    }

    public void setTimeEntryTypeId(int timeEntryTypeId) {
        this.timeEntryTypeId = timeEntryTypeId;
    }

    public boolean isAccepted() {
        return accepted;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }

    public int getOtherTaskId() {
        return otherTaskId;
    }

    public void setOtherTaskId(int otherTaskId) {
        this.otherTaskId = otherTaskId;
    }

    public String toJson(){
        Gson gson=new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        return gson.toJson(this);
    }

    @Override
    public int describeContents() {
        return 0;
    }
//    private TimeEntryType type;
//    private int timeEntryId;
//    private int employeeId;
//    private Date dateEntered;
//    private Date startDateTime;
//    private Date endDateTime;
//    private int workOrderId;
//    private double startLatitude;
//    private double startLongitude;
//    private double endLatitude;
//    private double endLongitude;
//    private String notes;
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(getType(),0);
        dest.writeInt(getTimeEntryId());
        dest.writeInt(getEmployeeId());
        dest.writeString(DateHelper.DateToString(getDateEntered()));
        dest.writeString(DateHelper.DateToString(getStartDateTime()));
        dest.writeString(DateHelper.DateToString(getEndDateTime()));
        dest.writeInt(getWorkOrderId());
        dest.writeDouble(getStartLatitude());
        dest.writeDouble(getStartLongitude());
        dest.writeDouble(getEndLatitude());
        dest.writeDouble(getEndLongitude());
        dest.writeString(getNotes());
        dest.writeInt(getSqlId());
        dest.writeInt(getTimeEntryTypeId());
        dest.writeInt(isAccepted()?1:0);
        dest.writeInt(getOtherTaskId());
    }
    public static  final Creator<TimeEntry> CREATOR= new Creator<TimeEntry>() {
        @Override
        public TimeEntry createFromParcel(Parcel source) {
            return new TimeEntry(source.readInt(),source.readInt(),DateHelper.StringToDate(source.readString()),DateHelper.StringToDate(source.readString()),
                    DateHelper.StringToDate(source.readString()),source.readInt(),source.readDouble(),source.readDouble(),source.readDouble(),source.readDouble(),source.readString(), source.readInt(), source.readInt(), source.readInt()==1?true:false,source.readInt());
        }

        @Override
        public TimeEntry[] newArray(int size) {
            return new TimeEntry[0];
        }
    };
}
