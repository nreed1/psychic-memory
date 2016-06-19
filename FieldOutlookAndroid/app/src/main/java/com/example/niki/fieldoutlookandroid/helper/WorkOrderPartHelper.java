package com.example.niki.fieldoutlookandroid.helper;

import com.example.niki.fieldoutlookandroid.businessobjects.Part;
import com.example.niki.fieldoutlookandroid.businessobjects.WorkOrder;
import com.example.niki.fieldoutlookandroid.businessobjects.WorkOrderPart;

import java.util.ArrayList;

/**
 * Created by Niki on 6/17/2016.
 */
public class WorkOrderPartHelper {
    private ArrayList<Part> parts=new ArrayList<>();
    private WorkOrder workOrder;

    private static WorkOrderPartHelper instance=null;
    private WorkOrderPartHelper(){

    }
    public static WorkOrderPartHelper getInstance(){
        if(instance==null){
            instance=new WorkOrderPartHelper();
        }
        return instance;
    }

    public void setWorkOrder(WorkOrder workOrder){this.workOrder=workOrder;}
    public ArrayList<Part> getPartList(){ return parts;}
    public void setPartList(ArrayList<Part> partList){
        for(Part part:partList){
            WorkOrderPart workOrderPart=new WorkOrderPart(part,1);


        }
    }
    public void addPartList(ArrayList<Part> partArrayList){
        parts.addAll(partArrayList);
    }
    public void removePartList(ArrayList<Part> partArrayList){
        parts.removeAll(partArrayList);
    }
    public void addPart(Part part){
        parts.add(part);
    }
    public void removePart(Part part){
        parts.remove(part);
    }

}
