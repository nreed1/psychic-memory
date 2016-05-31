package com.example.niki.fieldoutlookandroid.businessobjects;

/**
 * Created by Owner on 5/26/2016.
 */
public class MobileRegistration {
    private int MobileRegistrationId;
    private int UserId;
    private String RegistrationId;

    public int getMobileRegistrationId() {
        return MobileRegistrationId;
    }

    public void setMobileRegistrationId(int mobileRegistrationId) {
        MobileRegistrationId = mobileRegistrationId;
    }

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int userId) {
        UserId = userId;
    }

    public String getRegistrationId() {
        return RegistrationId;
    }

    public void setRegistrationId(String registrationId) {
        RegistrationId = registrationId;
    }
}
