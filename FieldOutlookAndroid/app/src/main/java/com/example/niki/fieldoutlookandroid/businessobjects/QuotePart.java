package com.example.niki.fieldoutlookandroid.businessobjects;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

/**
 * Created by Niki on 6/19/2016.
 */
public class QuotePart  extends Part {
    @Expose
    private int quantity;
    private FlatRateItem flatRateItem;

    public QuotePart(){

    }
    public QuotePart(Part part, int quantity, FlatRateItem flatRateItem){
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
        this.setFlatRateItem(flatRateItem);

    }

    public int getQuantity() {
        return quantity;
    }


    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public FlatRateItem getFlatRateItem() {
        return flatRateItem;
    }

    public void setFlatRateItem(FlatRateItem flatRateItem) {
        this.flatRateItem = flatRateItem;
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
    public static  final Parcelable.Creator<QuotePart> CREATOR= new Parcelable.Creator<QuotePart>() {
        @Override
        public QuotePart createFromParcel(Parcel source) {
            QuotePart part= new QuotePart();
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
        public QuotePart[] newArray(int size) {
            return new QuotePart[0];
        }
    };
}
