package com.example.niki.fieldoutlookandroid.helper;

/**
 * Created by Owner on 6/1/2016.
 */
public class ExceptionHelper {
    public void LogException(Exception ex){
        try{


        }catch (Exception e){
            LogException(e);
        }
    }
}
