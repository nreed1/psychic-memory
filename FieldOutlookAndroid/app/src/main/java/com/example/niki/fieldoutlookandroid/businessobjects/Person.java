package com.example.niki.fieldoutlookandroid.businessobjects;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by Owner on 5/16/2016.
 */
public class Person implements Serializable, Parcelable{
    private int PersonId;
    private int CompanyId;
    private int PersonTypeId;
    private String FirstName;
    private String LastName;
    private String FullName;
    private boolean IsBlackFlagged;
    private Address Address;
    private Phone Phone;

    public Person(){

    }
    public Person(int personId, int companyId, int personTypeId,String firstName, String lastName, String fullName, String addressline1, String addressline2, String city, String state, String zip){
        setPersonId(personId);
        setCompanyId(companyId);
        setPersonTypeId(personTypeId);
        setFirstName(firstName);
        setLastName(lastName);
        setFullName(fullName);
        com.example.niki.fieldoutlookandroid.businessobjects.Address address=new Address();
        address.setPersonId(personId);
        address.setStreetAddress1(addressline1);
        address.setStreetAddress2(addressline2);
        address.setCity(city);
        address.setState(state);
        address.setZipCode(zip);
        setAddress(address);
    }

    public int getPersonId() {
        return PersonId;
    }

    public void setPersonId(int personId) {
        PersonId = personId;
    }

    public int getCompanyId() {
        return CompanyId;
    }

    public void setCompanyId(int companyId) {
        CompanyId = companyId;
    }

    public int getPersonTypeId() {
        return PersonTypeId;
    }

    public void setPersonTypeId(int personTypeId) {
        PersonTypeId = personTypeId;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
    }

    public boolean isBlackFlagged() {
        return IsBlackFlagged;
    }

    public void setBlackFlagged(boolean blackFlagged) {
        IsBlackFlagged = blackFlagged;
    }

    public com.example.niki.fieldoutlookandroid.businessobjects.Address getAddress() {
        return Address;
    }

    public void setAddress(com.example.niki.fieldoutlookandroid.businessobjects.Address address) {
        Address = address;
    }

    public com.example.niki.fieldoutlookandroid.businessobjects.Phone getPhone() {
        return Phone;
    }

    public void setPhone(com.example.niki.fieldoutlookandroid.businessobjects.Phone phone) {
        Phone = phone;
    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(getPersonId());
        dest.writeInt(getCompanyId());
        dest.writeInt(getPersonTypeId());
        dest.writeString(getFirstName());
        dest.writeString(getLastName());
        dest.writeString(getFullName());
        dest.writeByte((byte) (isBlackFlagged() ? 1 : 0));

    }
    public static  final Creator<Person> CREATOR= new Creator<Person>() {
        @Override
        public Person createFromParcel(Parcel source) {
            Person person= new Person();
            person.PersonId=source.readInt();
            person.CompanyId=source.readInt();
            person.PersonTypeId=source.readInt();
            person.FirstName=source.readString();
            person.LastName=source.readString();
            person.FullName=source.readString();
            return person;
        }

        @Override
        public Person[] newArray(int size) {
            return new Person[0];
        }
    };
}
