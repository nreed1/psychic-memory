package com.example.niki.fieldoutlookandroid.helper.singleton;

import com.example.niki.fieldoutlookandroid.helper.authentication_service.AuthenticationResponse;

/**
 * Created by Niki on 3/31/2016.
 */
public class Global {
    private AuthenticationResponse User;
    private Boolean IsDayStarted=false;
    private Boolean IsDayEnded=false;

    private static final String DEBUG_SERVICE_URI="http://localhost:32251/WebDataService.svc/xml/";
    private static final String SERVICE_URI="http://fieldoutlookcloudservice.cloudapp.net/WebDataService.svc/xml/";

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

    public Boolean getIsDayEnded(){return IsDayEnded;}
    public void setIsDayEnded(Boolean isDayEnded){IsDayEnded=isDayEnded;}

    public String getDebugServiceUri() {
        return DEBUG_SERVICE_URI;
    }

    public String getServiceUri() {
        return SERVICE_URI;
    }
}
