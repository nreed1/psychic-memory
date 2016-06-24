package com.example.niki.fieldoutlookandroid.helper.array_adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.niki.fieldoutlookandroid.R;

import com.example.niki.fieldoutlookandroid.businessobjects.Part;
import com.example.niki.fieldoutlookandroid.businessobjects.PartCategory;
import com.example.niki.fieldoutlookandroid.businessobjects.WorkOrder;
import com.example.niki.fieldoutlookandroid.businessobjects.WorkOrderPart;
import com.example.niki.fieldoutlookandroid.fragment.PartListFragment;
import com.example.niki.fieldoutlookandroid.fragment.dummy.DummyContent.DummyItem;
import com.example.niki.fieldoutlookandroid.helper.DBHelper;
import com.example.niki.fieldoutlookandroid.helper.WorkOrderPartHelper;

import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Locale;

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
    protected List<Part> filteredList;
    private WorkOrder workOrder;
    private ArrayList<Integer> partIdsInWorkOrder=new ArrayList<>();


    public PartListRecyclerViewAdapter(Context context,List<PartCategory> items, List<Part> parts,PartCategory partCategory, WorkOrder workOrder,PartListFragment.OnPartListFragmentInteractionListener listener, PartListFragment.OnPartListPartFragmentInteractionListener partListener) {
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
        this.workOrder=workOrder;
        if(workOrder.getPartList()!=null && !workOrder.getPartList().isEmpty()){
            for (WorkOrderPart workOrderPart:workOrder.getPartList()){
                partIdsInWorkOrder.add(workOrderPart.getPartId());
            }
        }
        originalList=dbHelper.GetAllParts();

    }
    Boolean isAllSelected=false;
    Boolean isUnSelected=false;
    public void selectAll(){

        isAllSelected=true;
        if(mParts!=null && !mParts.isEmpty()) {

            //WorkOrderPartHelper.getInstance().addPartList((ArrayList<Part>) mParts);
            for(Part p :mParts){
                workOrder.addWorkOrderPartToList(new WorkOrderPart(p,1,null));
            }
            dbHelper.SaveWorkOrderPartList(workOrder.getWorkOrderId(),workOrder.getPartList());

            PartListRecyclerViewAdapter.this.notifyDataSetChanged();
        }
    }


    public void unselectAll(){
        isAllSelected=false;
        isUnSelected=true;
        if(mParts!=null && !mParts.isEmpty()) {
           // WorkOrderPartHelper.getInstance().removePartList((ArrayList<Part>) mParts);
            for (Part p :mParts){
                workOrder.removeWorkOrderPartFromList(new WorkOrderPart(p,1,null));
            }
            dbHelper.SaveWorkOrderPartList(workOrder.getWorkOrderId(),workOrder.getPartList());

            PartListRecyclerViewAdapter.this.notifyDataSetChanged();
        }
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.part_list_item, parent, false);


        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        if(position%2==1){
            holder.mView.setBackgroundColor(0xFFCBC3C2);
        }else{
            holder.mView.setBackgroundColor( 0xFFF0F4F7);
        }

        if(isAllSelected){
            holder.isSelectedCheckbox.setChecked(true);
        }else if(isUnSelected==true){
            holder.isSelectedCheckbox.setChecked(false);
            if(position==getItemCount()) {
                isUnSelected = false;
            }
        }
        if(category!=null){
            if(mValues!=null && !mValues.isEmpty() && position<mValues.size()){
                holder.mItem = mValues.get(position);
                holder.categoryName.setText(holder.mItem.getName());
                holder.iconImageView.setImageResource(R.mipmap.ic_add);
                holder.isSelectedCheckbox.setVisibility(View.INVISIBLE);
            }else{
                if(mValues!=null && mParts!=null) {
                    holder.mPart = mParts.get(position - mValues.size());
                    holder.categoryName.setText(holder.mPart.getNumberAndDescription());
                    holder.iconImageView.setImageResource(R.mipmap.ic_wrench);
                    holder.partPrice.setText(Currency.getInstance(Locale.getDefault()).getSymbol() + holder.mPart.getPrice());
                    holder.isSelectedCheckbox.setVisibility(View.VISIBLE);
                    if(partIdsInWorkOrder.contains(holder.mPart.getPartId())){
                        holder.isSelectedCheckbox.setChecked(true);
                    }

                }
            }

        }else {
            if (mParts != null && !mParts.isEmpty()) {
                holder.mPart = mParts.get(position);
                holder.categoryName.setText(holder.mPart.getNumberAndDescription());
                holder.iconImageView.setImageResource(R.mipmap.ic_wrench);
                holder.partPrice.setText(Currency.getInstance(Locale.getDefault()).getSymbol()+holder.mPart.getPrice());
                holder.isSelectedCheckbox.setVisibility(View.VISIBLE);
                if(partIdsInWorkOrder.contains(holder.mPart.getPartId())){
                    holder.isSelectedCheckbox.setChecked(true);
                }

            } else {
                holder.iconImageView.setImageResource(R.mipmap.ic_add);
                holder.mItem = mValues.get(position);
                holder.categoryName.setText(holder.mItem.getName());
                holder.isSelectedCheckbox.setVisibility(View.INVISIBLE);
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
        holder.isSelectedCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    WorkOrderPartHelper.getInstance().addPart(holder.mPart);
                }else
                    WorkOrderPartHelper.getInstance().removePart(holder.mPart);
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

                FilterResults results = new FilterResults();
                if (constraint.length() == 0) {
                    filteredList=null;
                    results.values = dbHelper.GetPartCategoryList(-100);
                } else {
                    results.values = getFilteredResults(constraint.toString().toLowerCase());
                    filteredList=(ArrayList<Part>)results.values;
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





    public class ViewHolder extends RecyclerView.ViewHolder  {
        public final View mView;
//        public final TextView mIdView;
//        public final TextView mContentView;
        public PartCategory mItem;
        public final ListView listView;
        public Part mPart;
        public final ImageView iconImageView;
        public final TextView categoryName;
        public final TextView partPrice;
        public final CheckBox isSelectedCheckbox;


        public ViewHolder(View view) {
            super(view);
            mView = view;
            listView=(ListView)view.getParent();

            //view.setOnCreateContextMenuListener(this);
//            mIdView = (TextView) view.findViewById(R.id.id);
//            mContentView = (TextView) view.findViewById(R.id.content);
            iconImageView=(ImageView)view.findViewById(R.id.partCategoryImage);
            categoryName=(TextView)view.findViewById(R.id.categoryTextView);
            partPrice=(TextView)view.findViewById(R.id.partPriceTextView);
            isSelectedCheckbox=(CheckBox)view.findViewById(R.id.itemIsSelectedCheckBox);

        }



        @Override
        public String toString() {
            return super.toString() + " '" + categoryName.getText() + "'";
        }


    }
}
