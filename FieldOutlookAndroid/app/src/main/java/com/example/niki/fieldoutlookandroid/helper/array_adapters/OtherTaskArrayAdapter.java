package com.example.niki.fieldoutlookandroid.helper.array_adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.niki.fieldoutlookandroid.R;
import com.example.niki.fieldoutlookandroid.businessobjects.OtherTask;
import com.example.niki.fieldoutlookandroid.businessobjects.WorkOrder;
import com.example.niki.fieldoutlookandroid.fragment.OtherTaskListFragment;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by Owner on 5/24/2016.
 */
public class OtherTaskArrayAdapter extends ArrayAdapter<OtherTask> {
    private final Activity _context;
    private final ArrayList<OtherTask> rows;



    public class ViewHolder
    {
        TextView otherTaskName;
        TextView otherTaskDescription;

    }

    public OtherTaskArrayAdapter(Activity context, int workorder_list_item, ArrayList<OtherTask> rows)
    {
        super(context, R.layout.othertask_list_item ,rows);
        this._context = context;
        this.rows = rows;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        ViewHolder holder = null;

        if(convertView == null)
        {
            LayoutInflater inflater = _context.getLayoutInflater();
            convertView = inflater.inflate(R.layout.othertask_list_item,parent,false);

            holder = new ViewHolder();
            holder.otherTaskName = (TextView) convertView.findViewById(R.id.othertasklistitem_name);
            holder.otherTaskDescription = (TextView) convertView.findViewById(R.id.othertasklistitem_description);


            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.otherTaskName.setText(""+rows.get(position).getName());
        holder.otherTaskDescription.setText(rows.get(position).getDescription());

        return convertView;
    }
}
