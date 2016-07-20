package com.example.niki.fieldoutlookandroid.helper.array_adapters;

import com.example.niki.fieldoutlookandroid.businessobjects.Part;

import java.util.ArrayList;

/**
 * Created by Owner on 7/20/2016.
 */
public class SelectedPartsSingleton extends ArrayList<Part> {
    private static SelectedPartsSingleton instance=null;
    private SelectedPartsSingleton(){
        new ArrayList<>();
    }
    public static SelectedPartsSingleton getInstance(){
        if(instance==null){
            instance=new SelectedPartsSingleton();
            
        }
        return instance;
    }
}
