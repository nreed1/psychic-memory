package com.example.niki.fieldoutlookandroid.businessobjects;

import java.io.Serializable;

/**
 * Created by Owner on 5/24/2016.
 */
public class OtherTask implements Serializable {
    private int id;
    private int userid;
    private String name;
    private String description;

    public OtherTask(){

    }
    public OtherTask(int id, int userid, String name, String description){
        this.id=id;
        this.userid=userid;
        this.name=name;
        this.description=description;
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
}
