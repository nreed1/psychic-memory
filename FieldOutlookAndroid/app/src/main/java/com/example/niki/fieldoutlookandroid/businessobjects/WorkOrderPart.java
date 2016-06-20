package com.example.niki.fieldoutlookandroid.businessobjects;

import android.os.Parcel;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by Owner on 6/15/2016.
 */
public class WorkOrderPart extends Part  {
    private int quantity;

    public WorkOrderPart(){

    }
    public WorkOrderPart(Part part, int quantity){
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

    }

    public int getQuantity() {
        return quantity;
    }


    public void setQuantity(int quantity) {
        this.quantity = quantity;
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

    }
    public static  final Creator<WorkOrderPart> CREATOR= new Creator<WorkOrderPart>() {
        @Override
        public WorkOrderPart createFromParcel(Parcel source) {
            WorkOrderPart part= new WorkOrderPart();
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
            return part;
        }

        @Override
        public WorkOrderPart[] newArray(int size) {
            return new WorkOrderPart[0];
        }
    };
}
