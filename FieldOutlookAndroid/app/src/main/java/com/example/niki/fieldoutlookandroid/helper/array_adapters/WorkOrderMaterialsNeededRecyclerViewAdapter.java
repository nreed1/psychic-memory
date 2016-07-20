package com.example.niki.fieldoutlookandroid.helper.array_adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;


import com.example.niki.fieldoutlookandroid.R;
import com.example.niki.fieldoutlookandroid.businessobjects.Part;
import com.example.niki.fieldoutlookandroid.businessobjects.WorkOrderMaterial;
import com.example.niki.fieldoutlookandroid.fragment.WorkOrderMaterialsNeededFragment;
import com.example.niki.fieldoutlookandroid.fragment.dummy.DummyContent.DummyItem;
import com.example.niki.fieldoutlookandroid.helper.DBHelper;
import com.example.niki.fieldoutlookandroid.helper.ExceptionHelper;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link WorkOrderMaterialsNeededFragment.OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class WorkOrderMaterialsNeededRecyclerViewAdapter extends RecyclerView.Adapter<WorkOrderMaterialsNeededRecyclerViewAdapter.ViewHolder> {

    private final List<WorkOrderMaterial> mValues;
    private final WorkOrderMaterialsNeededFragment.OnListFragmentInteractionListener mListener;
    private final Context context;

    public WorkOrderMaterialsNeededRecyclerViewAdapter(Context context,List<WorkOrderMaterial> items, WorkOrderMaterialsNeededFragment.OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
        this.context=context;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.workorder_material_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mItem = mValues.get(position);
        if(holder.mItem.isFulfilled()){
            holder.mView.setBackground(ContextCompat.getDrawable(context,R.drawable.diagonal_line));
        }

        if(position%2==1){
            holder.mView.setBackgroundColor(0xFFCBC3C2);
        }else{
            holder.mView.setBackgroundColor( 0xFFF0F4F7);
        }

        holder.mMaterialName.setText(mValues.get(position).getNumberAndDescription());


        holder.mMaterialQuantity.setMinValue(0);
        holder.mMaterialQuantity.setMinValue(0);
        holder.mMaterialQuantity.setMaxValue(1000);
        holder.mMaterialQuantity.setFocusableInTouchMode(true);
        holder.mMaterialQuantity.setWrapSelectorWheel(true);
        holder.mMaterialQuantity.setValue(mValues.get(position).getQuantity());
        holder.mMaterialQuantity.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                holder.mItem.setQuantity(newVal);
                DBHelper dbHelper=new DBHelper(context);
                dbHelper.UpdateWorkOrderMaterialsQuantity(mValues.get(position).getWorkOrderId(),holder.mItem.getPartId(),newVal);
            }
        });

//        holder.mView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (null != mListener) {
//                    // Notify the active callbacks interface (the activity, if the
//                    // fragment is attached to one) that an item has been selected.
//                    mListener.onListFragmentInteraction(holder.mItem);
//                }
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener, View.OnLongClickListener, PopupMenu.OnMenuItemClickListener {
        public final View mView;
        public final TextView mMaterialName;
        public final NumberPicker mMaterialQuantity;
        public WorkOrderMaterial mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mMaterialName = (TextView) view.findViewById(R.id.material_name);
            mMaterialQuantity = (NumberPicker) view.findViewById(R.id.material_quantity);
            mView.setOnLongClickListener(this);
        }

        private void removeItem(WorkOrderMaterial material){
            try{
                DBHelper dbHelper=new DBHelper(context);
                dbHelper.RemoveWorkOrderMaterial(mItem.getWorkOrderId(),mItem.getPartId());
                mValues.remove(material);
                Toast.makeText(context,"Removed "+mItem.getNumberAndDescription(),Toast.LENGTH_LONG).show();
                WorkOrderMaterialsNeededRecyclerViewAdapter.this.notifyDataSetChanged();

            }catch (Exception ex){
                ExceptionHelper.LogException(context,ex);
            }
        }

        @Override
        public boolean onLongClick(View v) {
            PopupMenu popupmenu=new PopupMenu(v.getContext(),v);
            popupmenu.inflate(R.menu.part_list_context_menu);
            popupmenu.setOnMenuItemClickListener(this);
            popupmenu.show();
            return true;
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            removeItem(mItem);

            return true;
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mMaterialName.getText() + "'";
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.setHeaderTitle("Select The Action");
            menu.add(0, v.getId(), 0, "Remove");//groupId, itemId, order, title
        }
    }
}
