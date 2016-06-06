package com.example.niki.fieldoutlookandroid.helper.array_adapters;

import android.app.Activity;
import android.content.res.Resources;
import android.inputmethodservice.Keyboard;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.niki.fieldoutlookandroid.R;
import com.example.niki.fieldoutlookandroid.businessobjects.WorkOrder;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by Niki on 5/19/2016.
 */
public class WorkOrderArrayAdapter extends ArrayAdapter<WorkOrder> {
    private final Activity _context;
    private final ArrayList<WorkOrder> rows;

    public class ViewHolder
    {
        TextView workOrderName;
        TextView workOrderCustomerName;
        TextView workOrderDate;
        TextView workOrderTime;
        LinearLayout backgroundLayout;
    }

    public WorkOrderArrayAdapter(Activity context, int workorder_list_item, ArrayList<WorkOrder> rows)
    {
        super(context, R.layout.workorder_list_item ,rows);
        this._context = context;
        this.rows = rows;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        ViewHolder holder = null;

        if(convertView == null)
        {
            LayoutInflater inflater = _context.getLayoutInflater();
            convertView = inflater.inflate(R.layout.workorder_list_item,parent,false);

            holder = new ViewHolder();
            holder.workOrderCustomerName = (TextView) convertView.findViewById(R.id.listItemWorkOrderCustomer);
            holder.workOrderName = (TextView) convertView.findViewById(R.id.listItemWorkOrderName);
            holder.workOrderDate=(TextView)convertView.findViewById(R.id.listItemDateOfWorkOrder);
            holder.workOrderTime=(TextView)convertView.findViewById(R.id.listItemTimeOfWorkOrder);
            holder.backgroundLayout=(LinearLayout)convertView.findViewById(R.id.workOrderListItemLinearLayout);
            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }
        if((position % 2) == 1){

            holder.backgroundLayout.setBackgroundColor( getContext().getResources().getColor( R.color.lightGray));

        }else{

            holder.backgroundLayout.setBackgroundColor(getContext().getResources().getColor(R.color.listItemBackground));
        }
        DateFormat dateFormat= new SimpleDateFormat("MM/dd");
        DateFormat timeFormat= new SimpleDateFormat("HH:mm");
        holder.workOrderName.setText(""+rows.get(position).getName()+"- "+rows.get(position).getDescription());
        if(rows.get(position).getPerson()!=null) {
            holder.workOrderCustomerName.setText(rows.get(position).getPerson().getFullName());
        }
        if(rows.get(position).getArrivalTime()!=null) {
            holder.workOrderDate.setText(dateFormat.format(rows.get(position).getArrivalTimeDate()));
            holder.workOrderTime.setText(timeFormat.format(rows.get(position).getArrivalTimeDate()));
        }
        return convertView;
    }
}
