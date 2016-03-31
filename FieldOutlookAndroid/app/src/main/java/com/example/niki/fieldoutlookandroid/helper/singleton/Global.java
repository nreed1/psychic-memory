package com.example.niki.fieldoutlookandroid.helper.singleton;

import com.example.niki.fieldoutlookandroid.helper.AuthenticationResponse;

/**
 * Created by Niki on 3/31/2016.
 */
public class Global {
    private AuthenticationResponse User;
    private Boolean IsDayStarted=false;

    private static Global instance=null;
    public static Global GetInstance(){
        if(instance==null){
            instance=new Global();
        }
        return instance;
    }
    private Global(){

    }

    public AuthenticationResponse getUser() {
        return User;
    }

    public void setUser(AuthenticationResponse user) {
        User = user;
    }

    public Boolean getIsDayStarted() {
        return IsDayStarted;
    }

    public void setIsDayStarted(Boolean isDayStarted) {
        IsDayStarted = isDayStarted;
    }
}
