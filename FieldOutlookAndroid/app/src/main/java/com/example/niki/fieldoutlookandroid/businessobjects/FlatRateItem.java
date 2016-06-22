package com.example.niki.fieldoutlookandroid.businessobjects;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Owner on 6/22/2016.
 */
public class FlatRateItem implements Serializable, Parcelable {

    private int id;
    @Expose
    private int FlatRateItemId;
    @Expose
    private String FlatRateName;
    @Expose
    private String Description;
    @Expose
    private double LaborHours;
    @Expose
    private double LaborRate;
    @Expose
    private double MarkupPercent;
    @Expose
    private String Category;
    @Expose
    private int FlatRateItemCategoryId;
    @Expose
    private int CompanyId;
    @Expose
    private double Price;
    @Expose
    private double MemberPrice;
    @Expose
    private ArrayList<FlatRateItemPart> PartList;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFlatRateItemId() {
        return FlatRateItemId;
    }

    public void setFlatRateItemId(int flatRateItemId) {
        FlatRateItemId = flatRateItemId;
    }

    public String getFlatRateName() {
        return FlatRateName;
    }

    public void setFlatRateName(String flatRateName) {
        FlatRateName = flatRateName;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public double getLaborHours() {
        return LaborHours;
    }

    public void setLaborHours(double laborHours) {
        LaborHours = laborHours;
    }

    public double getLaborRate() {
        return LaborRate;
    }

    public void setLaborRate(double laborRate) {
        LaborRate = laborRate;
    }

    public double getMarkupPercent() {
        return MarkupPercent;
    }

    public void setMarkupPercent(double markupPercent) {
        MarkupPercent = markupPercent;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public int getFlatRateItemCategoryId() {
        return FlatRateItemCategoryId;
    }

    public void setFlatRateItemCategoryId(int flatRateItemCategoryId) {
        FlatRateItemCategoryId = flatRateItemCategoryId;
    }

    public int getCompanyId() {
        return CompanyId;
    }

    public void setCompanyId(int companyId) {
        CompanyId = companyId;
    }

    public double getPrice() {
        return Price;
    }

    public void setPrice(double price) {
        Price = price;
    }

    public double getMemberPrice() {
        return MemberPrice;
    }

    public void setMemberPrice(double memberPrice) {
        MemberPrice = memberPrice;
    }

    public ArrayList<FlatRateItemPart> getPartList() {
        return PartList;
    }

    public void setPartList(ArrayList<FlatRateItemPart> partList) {
        PartList = partList;
    }

    @Override
    public int describeContents() {
        return 0;
    }


    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(getId());
        dest.writeInt(getFlatRateItemId());
        dest.writeString(getFlatRateName());
        dest.writeString(getDescription());
        dest.writeDouble(getLaborHours());
        dest.writeDouble(getLaborRate());
        dest.writeDouble(getMarkupPercent());
        dest.writeString(getCategory());
        dest.writeInt(getFlatRateItemCategoryId());
        dest.writeDouble(getPrice());
        dest.writeDouble(getMemberPrice());
        dest.writeList(getPartList());
    }
    public static  final Creator<FlatRateItem> CREATOR= new Creator<FlatRateItem>() {
        @Override
        public FlatRateItem createFromParcel(Parcel source) {
            FlatRateItem flatRateItem= new FlatRateItem();
            flatRateItem.setId(source.readInt());
            flatRateItem.setFlatRateItemId(source.readInt());
            flatRateItem.setFlatRateName(source.readString());
            flatRateItem.setDescription(source.readString());
            flatRateItem.setLaborHours(source.readDouble());
            flatRateItem.setLaborRate(source.readDouble());
            flatRateItem.setMarkupPercent(source.readDouble());
            flatRateItem.setCategory(source.readString());
            flatRateItem.setFlatRateItemCategoryId(source.readInt());
            flatRateItem.setCompanyId(source.readInt());
            flatRateItem.setPrice(source.readDouble());
            flatRateItem.setMemberPrice(source.readDouble());
            flatRateItem.setPartList(source.readArrayList(FlatRateItemPart.class.getClassLoader()));
            return flatRateItem;
        }

        @Override
        public FlatRateItem[] newArray(int size) {
            return new FlatRateItem[0];
        }
    };
}
