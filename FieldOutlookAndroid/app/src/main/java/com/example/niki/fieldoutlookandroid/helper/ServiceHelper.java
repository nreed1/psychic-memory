package com.example.niki.fieldoutlookandroid.helper;

/**
 * Created by Owner on 5/17/2016.
 */
public class ServiceHelper {
    private static Boolean DEBUG=false;


    private static final String DEBUG_SERVICE_URI="http://localhost:32251/WebDataService.svc/xml/";
    private static final String SERVICE_URI="http://fieldoutlookcloudservice.cloudapp.net/WebDataService.svc/xml/";

    public static String GetServiceURL(){
        if(DEBUG==true) {return DEBUG_SERVICE_URI;}
        return SERVICE_URI;
    }

    public void RefreshData(){

    }
}
