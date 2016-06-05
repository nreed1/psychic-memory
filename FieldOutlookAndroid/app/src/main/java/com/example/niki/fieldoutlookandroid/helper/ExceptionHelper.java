package com.example.niki.fieldoutlookandroid.helper;

import android.content.Context;

/**
 * Created by Owner on 6/1/2016.
 */
public class ExceptionHelper {
    public static void LogException(Context context,java.lang.Exception ex){
        try{
            DBHelper dbHelper=new DBHelper(context);
            dbHelper.SaveExceptionLog(ex);

        }catch (Exception e){
            LogException(context,e);
        }
    }
}
