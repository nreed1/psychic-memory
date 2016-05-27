package com.example.niki.fieldoutlookandroid.helper;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Owner on 5/27/2016.
 */
public class DateHelper {
    public static Date StringToDate(String time){
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            SimpleDateFormat output = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date d = sdf.parse(time);
            String formattedTime = output.format(d);
            return d;
        }catch (Exception ex){

        }
        return null;
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

    public static String GetTodayDateAsString(){
        try{
            SimpleDateFormat output=new SimpleDateFormat("yyyy-MM-dd");
            Date date= new Date();
            return output.format(date);
        }catch (Exception ex){

        }
        return "";
    }
}
