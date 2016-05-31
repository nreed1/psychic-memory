package com.example.niki.fieldoutlookandroid.helper.array_adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.niki.fieldoutlookandroid.R;
import com.example.niki.fieldoutlookandroid.businessobjects.OtherTask;
import com.example.niki.fieldoutlookandroid.businessobjects.TimeEntry;
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
           // holder.timeTextView = (TextView) convertView.findViewById(R.id.timeStampTimeline);
            holder.descriptionTimeline = (TextView) convertView.findViewById(R.id.timeLineTextView);
           // holder.iconLayout=(RelativeLayout)convertView.findViewById(R.id.timelineIconLayout);
            holder.iconView=(View)convertView.findViewById(R.id.view1);


            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }

        if(rows.get(position).getTimeEntryId()==0){
        // holder.iconLayout.setVisibility(View.INVISIBLE);
            holder.iconView.setVisibility(View.INVISIBLE);
        }else {
           // if(holder.timeTextView!=null)
                //holder.timeTextView.setText("" + DateHelper.DateToString(rows.get(position).getStartDateTime()));
            holder.descriptionTimeline.setText(rows.get(position).getWorkOrderId());
        }
        return convertView;
    }
}
