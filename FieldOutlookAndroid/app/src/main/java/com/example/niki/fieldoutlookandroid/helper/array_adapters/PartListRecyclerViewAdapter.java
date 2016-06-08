package com.example.niki.fieldoutlookandroid.helper.array_adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.niki.fieldoutlookandroid.R;

import com.example.niki.fieldoutlookandroid.businessobjects.PartCategory;
import com.example.niki.fieldoutlookandroid.fragment.PartListFragment;
import com.example.niki.fieldoutlookandroid.fragment.dummy.DummyContent.DummyItem;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link PartListFragment.OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class PartListRecyclerViewAdapter extends RecyclerView.Adapter<PartListRecyclerViewAdapter.ViewHolder> {

    private final List<PartCategory> mValues;
    private final PartListFragment.OnListFragmentInteractionListener mListener;

    public PartListRecyclerViewAdapter(List<PartCategory> items, PartListFragment.OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.part_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);


        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
//        public final TextView mIdView;
//        public final TextView mContentView;
        public PartCategory mItem;
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
