package com.example.niki.fieldoutlookandroid.businessobjects;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by NReed on 4/1/2016.
 */
public class TimeEntry implements Serializable {
    private TimeEntryType type;
    private int timeEntryId;
    private int employeeId;
    private Date startDateTime;
    private Date endDateTime;
    private int jobId;
    private double startLatitude;
    private double startLongitude;
    private double endLatitude;
    private double endLongitude;

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

    public int getJobId() {
        return jobId;
    }

    public void setJobId(int jobId) {
        this.jobId = jobId;
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
}
