package com.example.niki.fieldoutlookandroid.helper.array_adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.niki.fieldoutlookandroid.R;
import com.example.niki.fieldoutlookandroid.businessobjects.Part;
import com.example.niki.fieldoutlookandroid.businessobjects.PartCategory;
import com.example.niki.fieldoutlookandroid.businessobjects.Person;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Niki on 6/21/2016.
 */
public class CustomerAutoCompleteArrayAdapter extends ArrayAdapter<Person> implements Filterable{
    public class ViewHolder
    {
        TextView customerName;

    }
    private Activity context;
    private ArrayList<Person> personList;
    private ArrayList<Person> originalList;
    public CustomerAutoCompleteArrayAdapter(Activity context, ArrayList<Person> persons){
        super(context, R.layout.autocomplete_person_item_view,persons);
        this.context=context;
        personList=persons;
        originalList=persons;

    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        ViewHolder holder = null;

        if(convertView == null)
        {
            LayoutInflater inflater = context.getLayoutInflater();
            convertView = inflater.inflate(R.layout.autocomplete_person_item_view,parent,false);

            holder = new ViewHolder();
            holder.customerName = (TextView) convertView.findViewById(R.id.customerNameAutoCompleteView);



            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }

        if(position%2==1){
            convertView.setBackgroundColor(getContext().getResources().getColor( R.color.lightGray));
        }else{
            convertView.setBackgroundColor(getContext().getResources().getColor( R.color.listItemBackground));
        }

        holder.customerName.setText(""+personList.get(position).getFullName());


        return convertView;
    }
    @Override
    public Filter getFilter() {
        return new Filter() {
            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                // list = (List<Part>) results.values;

                    personList=(ArrayList<Person>)results.values;

                CustomerAutoCompleteArrayAdapter.this.notifyDataSetChanged();
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                FilterResults results = new FilterResults();
                if (constraint.length() == 0) {
                  //  filteredList=null;
                    results.values = originalList;
                } else {
                    results.values = getFilteredResults(constraint.toString().toLowerCase());
                    //filteredList=(ArrayList<Part>)results.values;
                }


                //results.values = filteredResults;

                return results;
            }
        };
    }
    protected List<Person> getFilteredResults(String constraint) {
        List<Person> results = new ArrayList<>();

        for (Person item : originalList) {
            if (item.getFullName().toLowerCase().contains(constraint)) {
                results.add(item);
            }
        }
        return results;
    }

}
