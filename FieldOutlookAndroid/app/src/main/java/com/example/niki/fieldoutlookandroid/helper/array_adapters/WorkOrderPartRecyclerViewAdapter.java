package com.example.niki.fieldoutlookandroid.helper.array_adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.example.niki.fieldoutlookandroid.R;

import com.example.niki.fieldoutlookandroid.businessobjects.Part;
import com.example.niki.fieldoutlookandroid.businessobjects.WorkOrder;
import com.example.niki.fieldoutlookandroid.businessobjects.WorkOrderPart;
import com.example.niki.fieldoutlookandroid.fragment.WorkOrderPartFragment;
import com.example.niki.fieldoutlookandroid.fragment.dummy.DummyContent.DummyItem;
import com.example.niki.fieldoutlookandroid.helper.DBHelper;

import java.util.ArrayList;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link WorkOrderPartFragment.OnWorkOrderPartListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class WorkOrderPartRecyclerViewAdapter extends RecyclerView.Adapter<WorkOrderPartRecyclerViewAdapter.ViewHolder> {

    private final WorkOrder mValues;
    private final WorkOrderPartFragment.OnWorkOrderPartListFragmentInteractionListener mListener;
    private final Context context;

    public WorkOrderPartRecyclerViewAdapter(WorkOrder items, WorkOrderPartFragment.OnWorkOrderPartListFragmentInteractionListener listener, Context context) {
        mValues = items;
        mListener = listener;
        this.context=context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.workorder_part_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.getPartList().get(position);
        if(mValues.getPartList().get(position).getNumberAndDescription()!=null && !mValues.getPartList().get(position).getNumberAndDescription().isEmpty()) {
            holder.mNumberandDescription.setText(mValues.getPartList().get(position).getNumberAndDescription());
        }else{
            String partNumber=mValues.getPartList().get(position).getPartNumber();
            if(partNumber!=null &&!partNumber.isEmpty()) {
                holder.mNumberandDescription.setText(mValues.getPartList().get(position).getPartNumber() + "-" + mValues.getPartList().get(position).getDescription());
            }else{
                holder.mNumberandDescription.setText( mValues.getPartList().get(position).getDescription());
            }
        }
        //holder.mQuantity.setDisplayedValues(null);
        holder.mQuantity.setMinValue(0);
        holder.mQuantity.setMaxValue(1000);
        holder.mQuantity.setFocusableInTouchMode(true);
        holder.mQuantity.setWrapSelectorWheel(true);
        //holder.mQuantity.sele
        holder.mQuantity.setValue(mValues.getPartList().get(position).getQuantity());
        holder.mQuantity.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                holder.mItem.setQuantity(newVal);
                DBHelper dbHelper=new DBHelper(context);
                dbHelper.UpdateWorkOrderPartQuantity(mValues.getWorkOrderId(),holder.mItem.getPartId(),newVal);
            }
        });

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onWorkOrderPartListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if(mValues.getPartList()==null) {
            mValues.setPartList(new ArrayList<WorkOrderPart>());
        }
        return mValues.getPartList().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mNumberandDescription;
        public final NumberPicker mQuantity;
        public WorkOrderPart mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;

            mNumberandDescription=(TextView)view.findViewById(R.id.workOrderPartListNameAndDescription);
            mQuantity=(NumberPicker)view.findViewById(R.id.workOrderPartListQuantity);

        }

        @Override
        public String toString() {
            return super.toString() + " '" + mNumberandDescription.getText() + "'";
        }
    }
}
