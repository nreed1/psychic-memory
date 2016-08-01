package com.example.niki.fieldoutlookandroid.helper;

/**
 * Created by Owner on 5/17/2016.
 */
public class ServiceHelper {
    private static Boolean DEBUG=false;


    private static final String DEBUG_SERVICE_URI="http://localhost:32251/WebDataService.svc/xml/";
    private static final String SERVICE_URI="https://fieldoutlookcloudservice.cloudapp.net:8080/WebDataService.svc/xml/";

    public static String GetServiceURL(){
        if(DEBUG==true) {return DEBUG_SERVICE_URI;}
        return SERVICE_URI;
    }
}
