package com.example.niki.fieldoutlookandroid.helper.array_adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.niki.fieldoutlookandroid.R;

import com.example.niki.fieldoutlookandroid.businessobjects.Part;
import com.example.niki.fieldoutlookandroid.businessobjects.PartCategory;
import com.example.niki.fieldoutlookandroid.fragment.PartListFragment;
import com.example.niki.fieldoutlookandroid.fragment.dummy.DummyContent.DummyItem;
import com.example.niki.fieldoutlookandroid.helper.DBHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link PartListFragment.OnPartListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class PartListRecyclerViewAdapter extends RecyclerView.Adapter<PartListRecyclerViewAdapter.ViewHolder> implements Filterable {

    private List<PartCategory> mValues;
    private List<Part> mParts;
    private final PartCategory category;
    private final PartListFragment.OnPartListFragmentInteractionListener mListener;
    private final PartListFragment.OnPartListPartFragmentInteractionListener mPartListener;
    protected List<Part> list;
    protected List<Part> originalList;
    protected Context context;
    protected DBHelper dbHelper;

    public PartListRecyclerViewAdapter(Context context,List<PartCategory> items, List<Part> parts,PartCategory partCategory, PartListFragment.OnPartListFragmentInteractionListener listener, PartListFragment.OnPartListPartFragmentInteractionListener partListener) {
        category=partCategory;
        if(category!=null){
            mValues=category.getSubCategoryList();
            mParts=category.getParts();

        }else {
            mValues = items;
            mParts = parts;

        }
        mListener = listener;
        mPartListener = partListener;
        this.context=context;
        dbHelper=new DBHelper(context);
        originalList=dbHelper.GetAllParts();

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.part_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        if(category!=null){
            if(mValues!=null && !mValues.isEmpty() && position<mValues.size()){
                holder.mItem = mValues.get(position);
                holder.categoryName.setText(holder.mItem.getName());
                holder.iconImageView.setImageResource(R.mipmap.ic_add);
            }else{
                holder.mPart = mParts.get(position-mValues.size());
                holder.categoryName.setText(holder.mPart.getNumberAndDescription());
                holder.iconImageView.setImageResource(R.mipmap.ic_wrench);
            }

        }else {
            if (mParts != null && !mParts.isEmpty()) {
                holder.mPart = mParts.get(position);
                holder.categoryName.setText(holder.mPart.getNumberAndDescription());
                holder.iconImageView.setImageResource(R.mipmap.ic_wrench);
            } else {
                holder.iconImageView.setImageResource(R.mipmap.ic_add);
                holder.mItem = mValues.get(position);
                holder.categoryName.setText(holder.mItem.getName());
            }
        }

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener ) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    if(holder.mItem!=null) {
                        mListener.onPartListFragmentInteraction(holder.mItem);
                    }
                }else if(null != mPartListener){
                    if(holder.mPart!=null){
                        mPartListener.onPartListPartInteraction(holder.mPart);
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if(category!=null) return mValues.size()+mParts.size();
        if(mParts!=null && !mParts.isEmpty()) return mParts.size();

        return mValues.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
               // list = (List<Part>) results.values;
                if(((ArrayList<?>)results.values).get(0) instanceof Part){
                    mParts=(ArrayList<Part>)results.values;
                }else{
                    mParts=null;
                    mValues=(ArrayList<PartCategory>)results.values;
                }
                PartListRecyclerViewAdapter.this.notifyDataSetChanged();
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                List<Part> filteredResults = null;
                FilterResults results = new FilterResults();
                if (constraint.length() == 0) {
                    results.values = dbHelper.GetPartCategoryList(-100);
                } else {
                    results.values = getFilteredResults(constraint.toString().toLowerCase());
                }


                //results.values = filteredResults;

                return results;
            }
        };
    }
    protected List<Part> getFilteredResults(String constraint) {
        List<Part> results = new ArrayList<>();

        for (Part item : originalList) {
            if (item.getNumberAndDescription().toLowerCase().contains(constraint)) {
                results.add(item);
            }
        }
        return results;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
//        public final TextView mIdView;
//        public final TextView mContentView;
        public PartCategory mItem;
        public Part mPart;
        public final ImageView iconImageView;
        public final TextView categoryName;


        public ViewHolder(View view) {
            super(view);
            mView = view;
//            mIdView = (TextView) view.findViewById(R.id.id);
//            mContentView = (TextView) view.findViewById(R.id.content);
            iconImageView=(ImageView)view.findViewById(R.id.partCategoryImage);
            categoryName=(TextView)view.findViewById(R.id.categoryTextView);
        }


        @Override
        public String toString() {
            return super.toString() + " '" + categoryName.getText() + "'";
        }
    }
}
