package com.example.niki.fieldoutlookandroid.businessobjects;

import android.media.session.MediaSession;

import com.example.niki.fieldoutlookandroid.helper.TokenHelper;
import com.google.gson.Gson;

/**
 * Created by Niki on 7/12/2016.
 */
public class UploadImageRequest {
    public byte[] bytes;
    public String Token= TokenHelper.getToken();
    public String filePath;
    public int objectId;

    public String toJson(){
        Gson gson=new Gson();
        return gson.toJson(this);
    }
}
