package com.example.niki.fieldoutlookandroid.businessobjects;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;

import java.io.Serializable;

/**
 * Created by Owner on 5/24/2016.
 */
public class OtherTask implements Serializable {

    private int id;
    @Expose
    private int userid;
    @Expose
    private String name;
    @Expose
    private String description;
    @Expose
    private int sqlid;
    public boolean isDirty;
    public OtherTask(){

    }
    public OtherTask(int id, int userid, String name, String description){
        this.id=id;
        this.userid=userid;
        this.name=name;
        this.description=description;
        isDirty=true;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getSqlid() {
        return sqlid;
    }

    public void setSqlid(int sqlid) {
        this.sqlid = sqlid;
    }

    public String toJson(){
        Gson gson= new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        return gson.toJson(this);
    }
}
