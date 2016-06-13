package com.example.niki.fieldoutlookandroid.helper.array_adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.niki.fieldoutlookandroid.R;

import com.example.niki.fieldoutlookandroid.businessobjects.Part;
import com.example.niki.fieldoutlookandroid.businessobjects.PartCategory;
import com.example.niki.fieldoutlookandroid.fragment.PartListFragment;
import com.example.niki.fieldoutlookandroid.fragment.dummy.DummyContent.DummyItem;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link PartListFragment.OnPartListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class PartListRecyclerViewAdapter extends RecyclerView.Adapter<PartListRecyclerViewAdapter.ViewHolder> {

    private final List<PartCategory> mValues;
    private final List<Part> mParts;
    private final PartCategory category;
    private final PartListFragment.OnPartListFragmentInteractionListener mListener;
    private final PartListFragment.OnPartListPartFragmentInteractionListener mPartListener;

    public PartListRecyclerViewAdapter(List<PartCategory> items, List<Part> parts,PartCategory partCategory, PartListFragment.OnPartListFragmentInteractionListener listener, PartListFragment.OnPartListPartFragmentInteractionListener partListener) {
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
