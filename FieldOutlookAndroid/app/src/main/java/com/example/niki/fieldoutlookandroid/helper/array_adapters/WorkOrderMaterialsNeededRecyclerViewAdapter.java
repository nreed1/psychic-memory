package com.example.niki.fieldoutlookandroid.helper.array_adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;
import android.widget.TextView;


import com.example.niki.fieldoutlookandroid.R;
import com.example.niki.fieldoutlookandroid.businessobjects.Part;
import com.example.niki.fieldoutlookandroid.businessobjects.WorkOrderMaterial;
import com.example.niki.fieldoutlookandroid.fragment.WorkOrderMaterialsNeededFragment;
import com.example.niki.fieldoutlookandroid.fragment.dummy.DummyContent.DummyItem;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link WorkOrderMaterialsNeededFragment.OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class WorkOrderMaterialsNeededRecyclerViewAdapter extends RecyclerView.Adapter<WorkOrderMaterialsNeededRecyclerViewAdapter.ViewHolder> {

    private final List<WorkOrderMaterial> mValues;
    private final WorkOrderMaterialsNeededFragment.OnListFragmentInteractionListener mListener;

    public WorkOrderMaterialsNeededRecyclerViewAdapter(List<WorkOrderMaterial> items, WorkOrderMaterialsNeededFragment.OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.workorder_material_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mMaterialName.setText(mValues.get(position).getNumberAndDescription());
        holder.mMaterialQuantity.setMinValue(0);
        holder.mMaterialQuantity.setValue(mValues.get(position).getQuantity());

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
        public final TextView mMaterialName;
        public final NumberPicker mMaterialQuantity;
        public WorkOrderMaterial mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mMaterialName = (TextView) view.findViewById(R.id.material_name);
            mMaterialQuantity = (NumberPicker) view.findViewById(R.id.material_quantity);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mMaterialName.getText() + "'";
        }
    }
}
