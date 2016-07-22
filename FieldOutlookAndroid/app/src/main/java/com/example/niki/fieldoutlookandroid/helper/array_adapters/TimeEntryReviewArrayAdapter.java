package com.example.niki.fieldoutlookandroid.helper.array_adapters;

import android.app.Activity;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.niki.fieldoutlookandroid.R;
import com.example.niki.fieldoutlookandroid.businessobjects.TimeEntry;
import com.example.niki.fieldoutlookandroid.businessobjects.TimeEntryType;
import com.example.niki.fieldoutlookandroid.helper.DBHelper;
import com.example.niki.fieldoutlookandroid.helper.DateHelper;

import java.util.ArrayList;

/**
 * Created by Owner on 5/31/2016.
 */
public class TimeEntryReviewArrayAdapter extends ArrayAdapter<TimeEntry> {
    private final Activity _context;
    private final ArrayList<TimeEntry> rows;



    public class ViewHolder
    {
        TextView timeTextView;
        TextView descriptionTimeline;
        RelativeLayout iconLayout;
        View iconView;
    }

    public TimeEntryReviewArrayAdapter(Activity context, int workorder_list_item, ArrayList<TimeEntry> rows)
    {
        super(context, R.layout.timelinelayout ,rows);
        this._context = context;
        this.rows = rows;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        ViewHolder holder = null;

        if(convertView == null)
        {
            LayoutInflater inflater = _context.getLayoutInflater();
            convertView = inflater.inflate(R.layout.timelinelayout,parent,false);

            holder = new ViewHolder();
            holder.timeTextView = (TextView) convertView.findViewById(R.id.timeStampTimeline);
            holder.descriptionTimeline = (TextView) convertView.findViewById(R.id.timeLineTextView);
           // holder.iconLayout=(RelativeLayout)convertView.findViewById(R.id.timelineIconLayout);
            holder.iconView=convertView.findViewById(R.id.view1);


            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }

        if(rows.get(position).getSqlId()==0){
        // holder.iconLayout.setVisibility(View.INVISIBLE);
            holder.iconView.setVisibility(View.INVISIBLE);
        }else {
           // if(holder.timeTextView!=null)
                holder.timeTextView.setText("" + DateHelper.GetStringTimeFromDate(rows.get(position).getStartDateTime()));
            if(rows.get(position).getType()!=null) {
                TimeEntryType timeEntryType=rows.get(position).getType();
                DBHelper dbHelper=new DBHelper(_context);
                holder.descriptionTimeline.setText(String.valueOf(timeEntryType.getName()));
                if(timeEntryType.getName().contains("Job") && rows.get(position).getWorkOrderId()!=0){
                    holder.descriptionTimeline.setText(holder.descriptionTimeline.getText()+" - "+dbHelper.GetWorkOrderById(rows.get(position).getWorkOrderId()).getPerson().getFullName());
                }else if(timeEntryType.getName().contains("Other")){
                    holder.descriptionTimeline.setText(holder.descriptionTimeline.getText()+" - "+dbHelper.GetOtherTaskById(rows.get(position).getOtherTaskId()).getName());
                }

            }
        }
        return convertView;
    }
}
