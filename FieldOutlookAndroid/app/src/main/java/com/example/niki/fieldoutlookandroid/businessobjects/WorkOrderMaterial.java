package com.example.niki.fieldoutlookandroid.businessobjects;

import android.os.Parcel;

import com.example.niki.fieldoutlookandroid.helper.DateHelper;
import com.google.gson.annotations.Expose;

import java.util.Date;

/**
 * Created by Owner on 6/22/2016.
 */
public class WorkOrderMaterial extends Part {
    @Expose
    private int quantity;
    private int workOrderId;
    private Date neededBy;
    private boolean fulfilled;

    public WorkOrderMaterial(){

    }
    public WorkOrderMaterial(Part part, int quantity){
        this.setPartId(part.getPartId());
        this.setCategoryId(part.getCategoryId());
        this.setManufacturer(part.getManufacturer());
        this.setModel(part.getModel());
        this.setNumberAndDescription(part.getNumberAndDescription());
        this.setPartNumber(part.getPartNumber());
        this.setDescription(part.getDescription());
        this.setPartTypeId(part.getPartTypeId());
        this.setPricebookId(part.getPricebookId());
        this.setPrice(part.getPrice());
        this.setQuantity(quantity);
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getWorkOrderId() {
        return workOrderId;
    }

    public void setWorkOrderId(int workOrderId) {
        this.workOrderId = workOrderId;
    }

    public Date getNeededBy() {
        return neededBy;
    }

    public void setNeededBy(Date neededBy) {
        this.neededBy = neededBy;
    }

    public boolean isFulfilled() {
        return fulfilled;
    }

    public void setFulfilled(boolean fulfilled) {
        this.fulfilled = fulfilled;
    }

    @Override
    public int describeContents() {
        return 0;
    }
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(getPartId());
        dest.writeInt(getPartTypeId());
        dest.writeInt(getPricebookId());
        dest.writeString(getDescription());
        dest.writeString(getPartNumber());
        dest.writeInt(getCategoryId());
        dest.writeString(getModel());
        dest.writeString(getManufacturer());
        dest.writeString(getNumberAndDescription());
        dest.writeInt(getQuantity());
        dest.writeDouble(getPrice());
        dest.writeInt(getWorkOrderId());
        dest.writeString(DateHelper.DateToString(getNeededBy()));
        dest.writeInt(fulfilled?1:0);

    }
    public static  final Creator<WorkOrderMaterial> CREATOR= new Creator<WorkOrderMaterial>() {
        @Override
        public WorkOrderMaterial createFromParcel(Parcel source) {
            WorkOrderMaterial part= new WorkOrderMaterial();
            part.setPartId(source.readInt());
            part.setPartTypeId(source.readInt());
            part.setPricebookId(source.readInt());
            part.setDescription(source.readString());
            part.setPartNumber(source.readString());
            part.setCategoryId(source.readInt());
            part.setModel(source.readString());
            part.setManufacturer(source.readString());
            part.setNumberAndDescription(source.readString());
            part.setQuantity(source.readInt());
            part.setPrice(source.readDouble());
            part.setWorkOrderId(source.readInt());
            part.setNeededBy(DateHelper.StringToDate(source.readString()));
            part.setFulfilled(source.readInt()==1?true:false);
            return part;
        }

        @Override
        public WorkOrderMaterial[] newArray(int size) {
            return new WorkOrderMaterial[0];
        }
    };
}
