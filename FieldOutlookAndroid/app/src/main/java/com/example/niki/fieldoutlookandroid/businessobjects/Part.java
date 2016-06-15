package com.example.niki.fieldoutlookandroid.businessobjects;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by Niki on 6/7/2016.
 */
public class Part implements Serializable,Parcelable{
    private int partId;
    private int partTypeId;
    private int pricebookId;
    private String description;
    private String partNumber;
    private int categoryId;
    private String Model;
    private String Manufacturer;
    private String NumberAndDescription;
   // private int quantity;
    private double price;

    public int getPartId() {
        return partId;
    }

    public void setPartId(int partId) {
        this.partId = partId;
    }

    public int getPartTypeId() {
        return partTypeId;
    }

    public void setPartTypeId(int partTypeId) {
        this.partTypeId = partTypeId;
    }

    public int getPricebookId() {
        return pricebookId;
    }

    public void setPricebookId(int pricebookId) {
        this.pricebookId = pricebookId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPartNumber() {
        return partNumber;
    }

    public void setPartNumber(String partNumber) {
        this.partNumber = partNumber;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getModel() {
        return Model;
    }

    public void setModel(String model) {
        Model = model;
    }

    public String getManufacturer() {
        return Manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        Manufacturer = manufacturer;
    }

    public String getNumberAndDescription() {
        return NumberAndDescription;
    }

    public void setNumberAndDescription(String numberAndDescription) {
        NumberAndDescription = numberAndDescription;
    }

//    public int getQuantity() {
//        return quantity;
//    }
//
//    public void setQuantity(int quantity) {
//        this.quantity = quantity;
//    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public int describeContents() {
        return 0;
    }


//    private int partId;
//    private int partTypeId;
//    private int pricebookId;
//    private String description;
//    private String partNumber;
//    private int categoryId;
//    private String Model;
//    private String Manufacturer;
//    private String NumberAndDescription;
//    private int quantity;
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
       // dest.writeInt(getQuantity());
        dest.writeDouble(getPrice());

    }
    public static  final Creator<Part> CREATOR= new Creator<Part>() {
        @Override
        public Part createFromParcel(Parcel source) {
            Part part= new Part();
            part.partId=source.readInt();
            part.partTypeId=source.readInt();
            part.pricebookId=source.readInt();
            part.description=source.readString();
            part.partNumber=source.readString();
            part.categoryId=source.readInt();
            part.Model=source.readString();
            part.Manufacturer=source.readString();
            part.NumberAndDescription=source.readString();
           // part.quantity=source.readInt();
            part.price=source.readDouble();
            return part;
        }

        @Override
        public Part[] newArray(int size) {
            return new Part[0];
        }
    };
}
