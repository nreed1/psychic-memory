package com.example.niki.fieldoutlookandroid.businessobjects;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by NReed on 4/1/2016.
 */
public class TimeEntryType implements Parcelable,Serializable {
    private int timeEntryTypeId;
    private String name;
    private String description;

    public int getTimeEntryTypeId() {
        return timeEntryTypeId;
    }

    public void setTimeEntryTypeId(int timeEntryTypeId) {
        this.timeEntryTypeId = timeEntryTypeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TimeEntryType(){

    }

    public TimeEntryType(int id, String name, String description ){
        this.timeEntryTypeId=id;
        this.name=name;
        this.description=description;
    }

    public TimeEntryType(Parcel in){
        this.timeEntryTypeId=in.readInt();
        this.name=in.readString();
        this.description=in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(timeEntryTypeId);
        dest.writeString(name);
        dest.writeString(description);
    }
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public TimeEntryType createFromParcel(Parcel in) {
            return new TimeEntryType(in);
        }

        public TimeEntryType[] newArray(int size) {
            return new TimeEntryType[size];
        }
    };
}
