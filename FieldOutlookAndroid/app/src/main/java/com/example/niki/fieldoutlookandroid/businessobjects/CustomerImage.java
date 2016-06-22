package com.example.niki.fieldoutlookandroid.businessobjects;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.Serializable;

/**
 * Created by Niki on 6/16/2016.
 */
public class CustomerImage implements Serializable {
    private  byte[] image;
    private String imageName;
    private int personId;
    private String imageString;

    public CustomerImage(){

    }
    public CustomerImage(String imageString, String imageName, int personId){
        //this.image=image;
        this.imageString=imageString;
        this.imageName=imageName;
        this.personId=personId;
    }


    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    public String getImageString() {
        return imageString;
    }

    public void setImageString(String imageString) {
        this.imageString = imageString;
    }

    public String toJson(){
        Gson gson=new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        return gson.toJson(this);
    }
}
