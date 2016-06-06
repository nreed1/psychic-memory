package com.example.niki.fieldoutlookandroid.businessobjects;

import java.io.Serializable;

/**
 * Created by Owner on 5/16/2016.
 */
public class Address implements Serializable {
    private int AddressId;
    private int AddressTypeId;
    private int PersonId;
    private String StreetAddress1;
    private String StreetAddress2;
    private String UnitNumber;
    private String City;
    private String State;
    private String ZipCode;

    public String getPrintableAddress(){
        String printableAddress="";
        if(StreetAddress1!=null &&!StreetAddress1.isEmpty()){
            printableAddress+=StreetAddress1+" \n";
        }
        if(StreetAddress2!=null && !StreetAddress2.isEmpty()){
            printableAddress+=StreetAddress2+" \n";
        }
        if(UnitNumber!=null && !UnitNumber.isEmpty()){
            printableAddress+=UnitNumber+" \n";
        }
        if(City!=null && !City.isEmpty()){
            printableAddress+=City;
        }
        if(State!=null && !State.isEmpty()){
            printableAddress+=", "+State;
        }
        if(ZipCode!=null && !ZipCode.isEmpty()){
            printableAddress+=" "+ZipCode;
        }
        return printableAddress;

    }

    public int getAddressId() {
        return AddressId;
    }

    public void setAddressId(int addressId) {
        AddressId = addressId;
    }

    public int getAddressTypeId() {
        return AddressTypeId;
    }

    public void setAddressTypeId(int addressTypeId) {
        AddressTypeId = addressTypeId;
    }

    public int getPersonId() {
        return PersonId;
    }

    public void setPersonId(int personId) {
        PersonId = personId;
    }

    public String getStreetAddress1() {
        return StreetAddress1;
    }

    public void setStreetAddress1(String streetAddress1) {
        StreetAddress1 = streetAddress1;
    }

    public String getStreetAddress2() {
        return StreetAddress2;
    }

    public void setStreetAddress2(String streetAddress2) {
        StreetAddress2 = streetAddress2;
    }

    public String getUnitNumber() {
        return UnitNumber;
    }

    public void setUnitNumber(String unitNumber) {
        UnitNumber = unitNumber;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }

    public String getZipCode() {
        return ZipCode;
    }

    public void setZipCode(String zipCode) {
        ZipCode = zipCode;
    }
}
