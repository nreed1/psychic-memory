package com.example.niki.fieldoutlookandroid.businessobjects;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Niki on 6/19/2016.
 */
public class Quote implements Serializable, Parcelable {
    private int quoteId;
    private Person customer;
    private String description;
    private String notes;
    private String dateCreated;
    private ArrayList<QuotePart> parts;
    private double amount;

    public Quote(){

    }
    public Quote(int quoteId,Person customer,String description, String notes, String dateCreated, ArrayList<QuotePart> parts, double amount){
        this.quoteId=quoteId;
        this.customer=customer;
        this.dateCreated=dateCreated;
        this.description=description;
        this.parts=parts;
        this.amount=amount;
        this.notes=notes;
    }

    public int getQuoteId() {
        return quoteId;
    }

    public void setQuoteId(int quoteId) {
        this.quoteId = quoteId;
    }

    public Person getCustomer() {
        return customer;
    }

    public void setCustomer(Person customer) {
        this.customer = customer;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public ArrayList<QuotePart> getParts() {
        return parts;
    }

    public void setParts(ArrayList<QuotePart> parts) {
        this.parts = parts;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String toJson(){
        Gson gson=new Gson();
        return  gson.toJson(this);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.quoteId);
        dest.writeParcelable(customer,0);
        dest.writeString(this.description);
        dest.writeString(this.notes);
        dest.writeString(this.dateCreated);
        dest.writeList(parts);
        dest.writeDouble(amount);
    }
    public static  final Creator<Quote> CREATOR= new Creator<Quote>() {
        @Override
        public Quote createFromParcel(Parcel source) {
            Quote quote= new Quote();
            quote.quoteId=source.readInt();
            quote.customer=source.readParcelable(Person.class.getClassLoader());
            quote.description=source.readString();
            quote.notes=source.readString();
            quote.dateCreated=source.readString();
            quote.parts=source.readArrayList(Quote.class.getClassLoader());
            quote.amount=source.readDouble();
            return quote;
        }

        @Override
        public Quote[] newArray(int size) {
            return new Quote[0];
        }
    };
}
