package com.example.niki.fieldoutlookandroid.businessobjects;


import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Niki on 6/7/2016.
 */
public class PartCategory implements Serializable, Parcelable {
    private int partCategoryId;
    private String name;
    private ArrayList<PartCategory> subCategoryList;
    private ArrayList<Part> parts;
    private ArrayList<Integer> subCategoryIdList;
    public PartCategory(){

    }
    public PartCategory(int partCategoryId, String name, ArrayList<PartCategory> subCategoryList,ArrayList<Part> partList){
        this.partCategoryId=partCategoryId;
        this.name=name;
        this.subCategoryList=subCategoryList;
        this.parts=partList;
    }


    public int getPartCategoryId() {
        return partCategoryId;
    }

    public void setPartCategoryId(int partCategoryId) {
        this.partCategoryId = partCategoryId;
    }

    public ArrayList<PartCategory> getSubCategoryList() {
        return subCategoryList;
    }

    public void setSubCategoryList(ArrayList<PartCategory> subCategoryList) {
        this.subCategoryList = subCategoryList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Part> getParts() {
        return parts;
    }

    public void setParts(ArrayList<Part> parts) {
        this.parts = parts;
    }

    public ArrayList<Integer> getSubCategoryIdList(){return subCategoryIdList;}
    public void setSubCategoryIdList(ArrayList<Integer> subCategoryIdList){this.subCategoryIdList=subCategoryIdList;}
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(getPartCategoryId());
        dest.writeString(getName());
        dest.writeList(getSubCategoryList());
        dest.writeList(getParts());
        dest.writeList(getSubCategoryIdList());
    }
    public static  final Creator<PartCategory> CREATOR= new Creator<PartCategory>() {
        @Override
        public PartCategory createFromParcel(Parcel source) {
            PartCategory partCategory= new PartCategory();
            partCategory.partCategoryId=source.readInt();
            partCategory.name=source.readString();
            partCategory.subCategoryList=source.readArrayList(PartCategory.class.getClassLoader());
            partCategory.parts=source.readArrayList(Part.class.getClassLoader());
            partCategory.subCategoryIdList=source.readArrayList(Integer.class.getClassLoader());
            return partCategory;
        }

        @Override
        public PartCategory[] newArray(int size) {
            return new PartCategory[0];
        }
    };
}
