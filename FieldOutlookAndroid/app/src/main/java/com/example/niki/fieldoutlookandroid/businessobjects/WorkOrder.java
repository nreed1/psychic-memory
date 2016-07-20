package com.example.niki.fieldoutlookandroid.businessobjects;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.niki.fieldoutlookandroid.helper.DateHelper;
import com.google.firebase.database.Exclude;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Owner on 5/16/2016.
 */
public class WorkOrder implements Serializable, Parcelable {
    @Expose
    private  int WorkOrderId;
    @Expose
    private int JobId;
    @Expose
    private int WorkOrderTypeId;
    @SerializedName("ApplicationCompanyID")
    @Expose
    private int CompanyId;
    @Expose
    private int PersonId;
    @Expose
    private String Name;
    @Expose
    private String Description;

    private String ArrivalTime;
    @Expose
    private String EstimatedDurationOfWork;
    @Expose
    private double CostOfJob;
    @Expose
    private String WhereBilled;
    @Expose
    private String Notes;
    @Expose
    private Person Person;
    private Date ArrivalTimeDate;
    @Expose
    private int TotalHoursForJob;
    @Expose
    private int HoursWorkedOnJob;
    @Expose
    private int IsReadyForInvoice;
    @Expose
    private ArrayList<WorkOrderPart> PartList;
    @Expose
    private JobTime JobTime;

    private String ReadyForInvoiceDateTime;
    @SerializedName("ArrivalTime")
    @Expose
    private String JsonArrivalTime;
    @SerializedName("ReadyToInvoiceDateTime")
    @Expose
    private String JsonReadyToInvoiceDateTime;

    private int sentToCloud;

    private ArrayList<WorkOrderMaterial> workOrderMaterials;

    public String getJsonReadyToInvoiceDateTime(){return DateHelper.DateToJsonString(DateHelper.StringToDate(this.ReadyForInvoiceDateTime));}

    public String getJsonArrivalTime(){return  DateHelper.DateToJsonString(this.ArrivalTimeDate);};
    public int getJobId() {
        return JobId;
    }

    public void setJobId(int jobId) {
        JobId = jobId;
    }

    public WorkOrder(){
        IsReadyForInvoice =0;
    }

    public WorkOrder(int workOrderId, int workOrderTypeId, int companyId, int personId, String name, String description, String arrivalTime, String estimatedDurationOfWork, double costOfJob, String whereBilled, String notes,
                     Person person, int totalHoursForJob, int hoursWorkedOnJob, int jobId, int isReadyForInvoice, ArrayList<WorkOrderPart> partList, JobTime jobTime){

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
        this.setIsReadyForInvoice(isReadyForInvoice);
        this.setPartList(partList);
        this.setJobTime(jobTime);
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

    public int getIsReadyForInvoice() {
        return IsReadyForInvoice;
    }

    public void setIsReadyForInvoice(int isReadyForInvoice) {
        IsReadyForInvoice = isReadyForInvoice;
    }

    public ArrayList<WorkOrderPart> getPartList() {
        return PartList;
    }

    public void setPartList(ArrayList<WorkOrderPart> partList) {
        PartList = partList;
    }


    public com.example.niki.fieldoutlookandroid.businessobjects.JobTime getJobTime() {
        return JobTime;
    }

    public void setJobTime(com.example.niki.fieldoutlookandroid.businessobjects.JobTime jobTime) {
        JobTime = jobTime;
    }

    public String getReadyForInvoiceDateTime() {
        return ReadyForInvoiceDateTime;
    }

    public void setReadyForInvoiceDateTime(String readyForInvoiceDateTime) {
        ReadyForInvoiceDateTime = readyForInvoiceDateTime;
    }

    public void addWorkOrderPartToList(WorkOrderPart workOrderPart){
        if(getPartList()!=null &&! getPartList().isEmpty()){
            boolean partExistsInList=false;
            for (WorkOrderPart part:this.PartList){
                if(part.getPartId()==workOrderPart.getPartId()){
                    partExistsInList=true;
                    part.setQuantity(part.getQuantity()+1);//Increase quantity if it already exists in the list
                    break;
                }
            }
            if(!partExistsInList){
                this.PartList.add(workOrderPart);
            }
        }else{
            if(getPartList()==null){
                setPartList(new ArrayList<WorkOrderPart>());
            }
            this.PartList.add(workOrderPart);
        }
    }

    public void removeWorkOrderPartFromList(WorkOrderPart workOrderPart){
        if(getPartList()!=null &&! getPartList().isEmpty()){
            boolean partExistsInList=false;
            for (WorkOrderPart part:this.PartList){
                if(part.getPartId()==workOrderPart.getPartId()){
                    partExistsInList=true;
                    break;
                }
            }
            if(!partExistsInList){
                this.PartList.remove(workOrderPart);
            }
        }else{
            if(getPartList()==null){
                setPartList(new ArrayList<WorkOrderPart>());
            }

        }
    }

    public void addWorkOrderMaterialFromList(WorkOrderMaterial workOrderMaterial){
        if(getWorkOrderMaterials()!=null &&! getWorkOrderMaterials().isEmpty()){
            boolean partExistsInList=false;
            for (WorkOrderMaterial part:this.workOrderMaterials){
                if(part.getPartId()==workOrderMaterial.getPartId()){
                    partExistsInList=true;
                    part.setQuantity(part.getQuantity()+1);//Increase quantity if it already exists in the list
                    break;
                }
            }
            if(!partExistsInList){
                workOrderMaterial.setQuantity(1);
                this.workOrderMaterials.add(workOrderMaterial);
            }
        }else{
            if(getWorkOrderMaterials()==null){
                setWorkOrderMaterials(new ArrayList<WorkOrderMaterial>());
            }
            workOrderMaterial.setQuantity(1);
            this.workOrderMaterials.add(workOrderMaterial);
        }
    }
    public void removeWorkOrderMaterialFromList(WorkOrderMaterial workOrderMaterial){
        if(getWorkOrderMaterials()!=null &&! getWorkOrderMaterials().isEmpty()){
            boolean partExistsInList=false;
            for (WorkOrderMaterial part:this.workOrderMaterials){
                if(part.getPartId()==workOrderMaterial.getPartId()){
                    partExistsInList=true;
                    break;
                }
            }
            if(!partExistsInList){
                this.workOrderMaterials.remove(workOrderMaterial);
            }
        }else{
            if(getWorkOrderMaterials()==null){
                setWorkOrderMaterials(new ArrayList<WorkOrderMaterial>());
            }

        }
    }

    public ArrayList<WorkOrderMaterial> getWorkOrderMaterials() {
        return workOrderMaterials;
    }

    public void setWorkOrderMaterials(ArrayList<WorkOrderMaterial> workOrderMaterials) {
        this.workOrderMaterials = workOrderMaterials;
    }
    //    public JSONStringer getJSON(){
//        try {
//            JSONStringer jsonStringer = new JSONStringer().object().key("WorkOrder").object().key("WorkOrderTypeId").value(this.WorkOrderTypeId)
//                    .key("CompanyId").value(this.getCompanyId())
//                    .key("PersonId").value(this.PersonId)
//                    .key("Name").value(this.Name)
//                    .key("Description").value(this.getDescription())
//                    .key("Notes").value(getNotes())
//                    .key("ReadyToInvoice").value(getIsReadyForInvoice())
//
//                    .endObject()
//                    .endObject();
//            return jsonStringer;
//        }catch (Exception ex){
//            Log.d("Workorder JSON", ex.getMessage());
//        }
//        return null;
//    }


    public int getSentToCloud() {
        return sentToCloud;
    }

    public void setSentToCloud(int sentToCloud) {
        this.sentToCloud = sentToCloud;
    }

    public String toJson(){
        final Gson gson=new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

        return gson.toJson(this);
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
        dest.writeInt(getIsReadyForInvoice());
        dest.writeList(getPartList());
        dest.writeParcelable(getJobTime(),0);
        dest.writeString(getReadyForInvoiceDateTime());
        dest.writeInt(getSentToCloud());
        dest.writeList(getWorkOrderMaterials());
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
            workOrder.IsReadyForInvoice =source.readInt();
            workOrder.PartList=source.readArrayList(WorkOrderPart.class.getClassLoader());
            workOrder.JobTime=source.readParcelable(com.example.niki.fieldoutlookandroid.businessobjects.JobTime.class.getClassLoader());
            workOrder.ReadyForInvoiceDateTime =source.readString();
            workOrder.sentToCloud=source.readInt();
            workOrder.workOrderMaterials=source.readArrayList(WorkOrderMaterial.class.getClassLoader());
            return workOrder;
        }

        @Override
        public WorkOrder[] newArray(int size) {
            return new WorkOrder[0];
        }
    };
}
