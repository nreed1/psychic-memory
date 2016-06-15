package com.example.niki.fieldoutlookandroid.businessobjects;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by Owner on 6/15/2016.
 */
public class JobTime implements Serializable,Parcelable {
    private int jobId;
    private int projectedHours;
    private int actualHours;

    public JobTime(){

    }
    public JobTime(int jobId, int actualHours, int projectedHours){
        this.jobId=jobId;
        this.actualHours=actualHours;
        this.projectedHours=projectedHours;
    }

    public int getJobId() {
        return jobId;
    }

    public void setJobId(int jobId) {
        this.jobId = jobId;
    }

    public int getProjectedHours() {
        return projectedHours;
    }

    public void setProjectedHours(int projectedHours) {
        this.projectedHours = projectedHours;
    }

    public int getActualHours() {
        return actualHours;
    }

    public void setActualHours(int actualHours) {
        this.actualHours = actualHours;
    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(getJobId());
        dest.writeInt(getActualHours());
        dest.writeInt(getProjectedHours());
    }
    public static  final Creator<JobTime> CREATOR= new Creator<JobTime>() {
        @Override
        public JobTime createFromParcel(Parcel source) {
            JobTime jobTime=new JobTime();
            jobTime.jobId=source.readInt();
            jobTime.actualHours=source.readInt();
            jobTime.projectedHours=source.readInt();
            return jobTime;
        }

        @Override
        public JobTime[] newArray(int size) {
            return new JobTime[0];
        }
    };
}
