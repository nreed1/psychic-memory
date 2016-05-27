package com.example.niki.fieldoutlookandroid.businessobjects;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.drive.realtime.internal.ParcelableChangeInfo;
import com.google.android.gms.vision.barcode.Barcode;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Owner on 5/16/2016.
 */
public class WorkOrder implements Serializable, Parcelable {
    private  int WorkOrderId;
    private int JobId;
    private int WorkOrderTypeId;
    private int CompanyId;
    private int PersonId;
    private String Name;
    private String Description;
    private String ArrivalTime;
    private String EstimatedDurationOfWork;
    private double CostOfJob;
    private String WhereBilled;
    private String Notes;
    private Person Person;
    private Date ArrivalTimeDate;
    private int TotalHoursForJob;
    private int HoursWorkedOnJob;

    public int getJobId() {
        return JobId;
    }

    public void setJobId(int jobId) {
        JobId = jobId;
    }

    public WorkOrder(){

    }

    public WorkOrder(int workOrderId,int workOrderTypeId, int companyId, int personId, String name, String description, String arrivalTime, String estimatedDurationOfWork, double costOfJob, String whereBilled, String notes,
                     Person person, int totalHoursForJob, int hoursWorkedOnJob, int jobId){

        this.setWorkOrderId(workOrderId);
        this.setWorkOrderTypeId(workOrderTypeId);
        this.setCompanyId(companyId);
        this.setPersonId(personId);
        this.setName(name);
        this.setDescription(description);
        this.setArrivalTime(arrivalTime);
        this.setEstimatedDurationOfWork(estimatedDurationOfWork);
        this.setCostOfJob(costOfJob);
        this.setWhereBilled(whereBilled);
        this.setNotes(notes);
        this.setPerson(person);
        this.setTotalHoursForJob(totalHoursForJob);
        this.setHoursWorkedOnJob(hoursWorkedOnJob);
        this.setJobId(jobId);
    }

    public int getTotalHoursForJob() {
        return TotalHoursForJob;
    }

    public void setTotalHoursForJob(int totalHoursForJob) {
        TotalHoursForJob = totalHoursForJob;
    }

    public int getHoursWorkedOnJob() {
        return HoursWorkedOnJob;
    }

    public void setHoursWorkedOnJob(int hoursWorkedOnJob) {
        HoursWorkedOnJob = hoursWorkedOnJob;
    }

    public Date getArrivalTimeDate() {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            SimpleDateFormat output = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date d = sdf.parse(ArrivalTime);
            String formattedTime = output.format(d);

            return d;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void setArrivalTimeDate(Date arrivalTimeDate) {
        ArrivalTimeDate = arrivalTimeDate;
    }

    public com.example.niki.fieldoutlookandroid.businessobjects.Person getPerson() {
        return Person;
    }

    public void setPerson(com.example.niki.fieldoutlookandroid.businessobjects.Person person) {
        Person = person;
    }

    public int getWorkOrderId() {
        return WorkOrderId;
    }

    public void setWorkOrderId(int workOrderId) {
        WorkOrderId = workOrderId;
    }

    public int getWorkOrderTypeId() {
        return WorkOrderTypeId;
    }

    public void setWorkOrderTypeId(int workOrderTypeId) {
        WorkOrderTypeId = workOrderTypeId;
    }

    public int getCompanyId() {
        return CompanyId;
    }

    public void setCompanyId(int companyId) {
        CompanyId = companyId;
    }

    public int getPersonId() {
        return PersonId;
    }

    public void setPersonId(int personId) {
        PersonId = personId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getArrivalTime() {
        return ArrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        ArrivalTime = arrivalTime;
    }

    public String getEstimatedDurationOfWork() {
        return EstimatedDurationOfWork;
    }

    public void setEstimatedDurationOfWork(String estimatedDurationOfWork) {
        EstimatedDurationOfWork = estimatedDurationOfWork;
    }

    public double getCostOfJob() {
        return CostOfJob;
    }

    public void setCostOfJob(double costOfJob) {
        CostOfJob = costOfJob;
    }

    public String getWhereBilled() {
        return WhereBilled;
    }

    public void setWhereBilled(String whereBilled) {
        WhereBilled = whereBilled;
    }

    public String getNotes() {
        return Notes;
    }

    public void setNotes(String notes) {
        Notes = notes;
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
    public static  final Creator<WorkOrder> CREATOR= new Creator<WorkOrder>() {
        @Override
        public WorkOrder createFromParcel(Parcel source) {
            WorkOrder workOrder= new WorkOrder();
            workOrder.Person=source.readParcelable(Person.class.getClassLoader());
            workOrder.WorkOrderTypeId=source.readInt();
            workOrder.CompanyId=source.readInt();
            workOrder.PersonId=source.readInt();
            workOrder.Name=source.readString();
            workOrder.Description=source.readString();
            workOrder.CostOfJob=source.readDouble();
            workOrder.WhereBilled=source.readString();
            workOrder.Notes=source.readString();
            return workOrder;
        }

        @Override
        public WorkOrder[] newArray(int size) {
            return new WorkOrder[0];
        }
    };
}
