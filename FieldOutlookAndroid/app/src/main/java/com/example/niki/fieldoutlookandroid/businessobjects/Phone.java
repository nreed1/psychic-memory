package com.example.niki.fieldoutlookandroid.businessobjects;

import java.io.Serializable;

/**
 * Created by Owner on 5/16/2016.
 */
public class Phone implements Serializable {
    private int PhoneId;
    private int PhoneTypeId;
    private int PersonId;
    private String PhoneNumber;
    //private boolean IsPrimary;
    private int CarrierId;

    public int getPhoneId() {
        return PhoneId;
    }

    public void setPhoneId(int phoneId) {
        PhoneId = phoneId;
    }

    public int getPhoneTypeId() {
        return PhoneTypeId;
    }

    public void setPhoneTypeId(int phoneTypeId) {
        PhoneTypeId = phoneTypeId;
    }

    public int getPersonId() {
        return PersonId;
    }

    public void setPersonId(int personId) {
        PersonId = personId;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public int getCarrierId() {
        return CarrierId;
    }

    public void setCarrierId(int carrierId) {
        CarrierId = carrierId;
    }
}
