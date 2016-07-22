package com.example.niki.fieldoutlookandroid.helper;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Owner on 5/27/2016.
 */
public class DateHelper {

    public static Date WebserviceDateStringToJavaDate(String time){
        try{
            if(time==null)return null;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            SimpleDateFormat output = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            //Date d = sdf.parse(time);
            Date d=null;
            if(d==null) d=sdf.parse(time);
            return d;
        }catch (Exception ex){
            System.out.print(ex.getStackTrace());
        }
        return null;
    }
    public static Date StringToDate(String time){
        try {
            if(time==null)return null;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            SimpleDateFormat output = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            //Date d = sdf.parse(time);
            Date d=null;
            if(d==null) d=output.parse(time);
            return d;
        }catch (Exception ex){
            System.out.print(ex.getStackTrace());
        }
        return null;
    }

    public static String DateToJsonString(Date date){
        long javaMillis=date.getTime();
        long ticks = 621355968000000000L+javaMillis*10000;
        return "\\/Date("+ticks+")\\/";
    }


    public static String DateToString(Date date){
        try{
            SimpleDateFormat output = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String formattedTime = output.format(date);
            return formattedTime;

        }catch (Exception ex){

        }
        return "";
    }
    public static String DatetoMMDDYYYString(Date date){
        try{
            SimpleDateFormat output=new SimpleDateFormat("yyyy-MM-dd");
            return output.format(date);
        }catch (Exception ex){

        }
        return "";
    }

    public static String GetTodayDateAsString(){
        try{
            SimpleDateFormat output=new SimpleDateFormat("yyyy-MM-dd");
            Date date= new Date();
            return output.format(date);
        }catch (Exception ex){

        }
        return "";
    }



    public static String GetStringTimeFromDate(Date date){
        try{
            SimpleDateFormat output=new SimpleDateFormat("hh:mm a");
            return output.format(date)+" ";
        }catch (Exception ex){

        }
        return "";
    }
}
