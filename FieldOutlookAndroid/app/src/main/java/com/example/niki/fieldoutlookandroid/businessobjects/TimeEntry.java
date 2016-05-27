package com.example.niki.fieldoutlookandroid.businessobjects;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.sql.Time;
import java.util.Date;

/**
 * Created by NReed on 4/1/2016.
 */
public class TimeEntry implements Parcelable,Serializable {
    private TimeEntryType type;
    private int timeEntryId;
    private int employeeId;
    private Date dateEntered;
    private Date startDateTime;
    private Date endDateTime;
    private int workOrderId;
    private double startLatitude;
    private double startLongitude;
    private double endLatitude;
    private double endLongitude;
    private String notes;
    public TimeEntry(){

    }
    public TimeEntry(int timeEntryId,int employeeId,Date dateEntered, Date startDateTime, Date endDateTime, int workOrderId, double startLatitude, double startLongitude, double endLatitude, double endLongitude, String notes){
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
        this.notes=notes;
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
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(getPerson(),0);
        dest.writeInt(getWorkOrderId());
        dest.writeInt(getWorkOrderTypeId());
        dest.writeInt(getCompanyId());
        dest.writeInt(getPersonId());
        dest.writeString(getName());
        dest.writeString(getDescription());
        dest.writeString(getArrivalTime());
        dest.writeString(getEstimatedDurationOfWork());
        dest.writeDouble(getCostOfJob());
        dest.writeString(getWhereBilled());
        dest.writeString(getNotes());
    }
    public static  final Creator<TimeEntry> CREATOR= new Creator<TimeEntry>() {
        @Override
        public TimeEntry createFromParcel(Parcel source) {

        }

        @Override
        public TimeEntry[] newArray(int size) {
            return new TimeEntry[0];
        }
    };
}
