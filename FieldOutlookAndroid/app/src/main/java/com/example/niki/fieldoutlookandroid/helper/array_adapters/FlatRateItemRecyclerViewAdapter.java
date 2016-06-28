package com.example.niki.fieldoutlookandroid.helper.array_adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.niki.fieldoutlookandroid.R;
import com.example.niki.fieldoutlookandroid.businessobjects.FlatRateItem;
import com.example.niki.fieldoutlookandroid.businessobjects.Part;
import com.example.niki.fieldoutlookandroid.businessobjects.PartCategory;
import com.example.niki.fieldoutlookandroid.businessobjects.Quote;
import com.example.niki.fieldoutlookandroid.businessobjects.QuotePart;
import com.example.niki.fieldoutlookandroid.businessobjects.WorkOrder;
import com.example.niki.fieldoutlookandroid.businessobjects.WorkOrderPart;
import com.example.niki.fieldoutlookandroid.fragment.FlatRateItemListFragment;
import com.example.niki.fieldoutlookandroid.fragment.dummy.DummyContent.DummyItem;
import com.example.niki.fieldoutlookandroid.helper.DBHelper;

import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Locale;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link com.example.niki.fieldoutlookandroid.fragment.FlatRateItemListFragment.OnFlatRateItemListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class FlatRateItemRecyclerViewAdapter extends RecyclerView.Adapter<FlatRateItemRecyclerViewAdapter.ViewHolder> implements Filterable {

    private List<FlatRateItem> mValues;
    private List<FlatRateItem> originalList;
    private final FlatRateItemListFragment.OnFlatRateItemListFragmentInteractionListener mListener;
    private Context context;
    private DBHelper dbHelper;
    private WorkOrder workOrder=null;
    private Quote quote=null;

    public FlatRateItemRecyclerViewAdapter(List<FlatRateItem> items, FlatRateItemListFragment.OnFlatRateItemListFragmentInteractionListener listener, Context context) {
        mValues = items;
        mListener = listener;
        this.context=context;
        dbHelper=new DBHelper(this.context);
        originalList=items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.flatrateitem_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mNameView.setText(mValues.get(position).getFlatRateName());
        holder.mDescriptionView.setText(mValues.get(position).getDescription());
        holder.mPriceView.setText(Currency.getInstance(Locale.getDefault()).getSymbol()+mValues.get(position).getPrice());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onFlatRateItemListFragmentInteraction(holder.mItem);
                }
            }
        });
    }
    Boolean isAllSelected=false;
    Boolean isUnSelected=false;
    public void selectAll(){

        isAllSelected=true;
        if(mValues!=null && !mValues.isEmpty()) {

            //WorkOrderPartHelper.getInstance().addPartList((ArrayList<Part>) mParts);
            for(FlatRateItem p :mValues){
                if(workOrder!=null) {
                    workOrder.addWorkOrderPartToList(new WorkOrderPart(null, 1, p));
                }else if(quote!=null){
                    if(quote.getParts()==null) quote.setParts(new ArrayList<QuotePart>());
                    quote.addWorkOrderPartToList(new QuotePart(null,1,p));
                }
            }
            if(workOrder!=null) {
                dbHelper.SaveWorkOrderPartList(workOrder.getWorkOrderId(), workOrder.getPartList());
            }else if(quote!=null){
                dbHelper.SaveQuotePartList(quote.getParts(),quote.getQuoteId());
            }

            FlatRateItemRecyclerViewAdapter.this.notifyDataSetChanged();
        }
    }


    public void unselectAll(){
        isAllSelected=false;
        isUnSelected=true;
        if(mValues!=null && !mValues.isEmpty()) {
            // WorkOrderPartHelper.getInstance().removePartList((ArrayList<Part>) mParts);
            for (FlatRateItem p :mValues){
                if(workOrder!=null)
                workOrder.removeWorkOrderPartFromList(new WorkOrderPart(null,1,p));
                if(quote!=null)
                    quote.removeWorkOrderPartFromList(new QuotePart(null,1,p));
            }
            dbHelper.SaveWorkOrderPartList(workOrder.getWorkOrderId(),workOrder.getPartList());

            FlatRateItemRecyclerViewAdapter.this.notifyDataSetChanged();
        }
    }
    @Override
    public Filter getFilter() {
        return new Filter() {
            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                // list = (List<Part>) results.values;
                //if(results!=null&&results.values!=null&&((ArrayList<?>)results.values).get(0) instanceof Part){
                    mValues=(ArrayList<FlatRateItem>)results.values;
                //}else{
               //     mParts=null;
                //    mValues=(ArrayList<PartCategory>)results.values;
              //  }
                FlatRateItemRecyclerViewAdapter.this.notifyDataSetChanged();
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                FilterResults results = new FilterResults();
                if (constraint.length() == 0) {
                    //filteredList=null;
                    results.values = dbHelper.GetFlatRateItems();
                } else {
                    results.values = getFilteredResults(constraint.toString().toLowerCase());
                   // filteredList=(ArrayList<Part>)results.values;
                }


                //results.values = filteredResults;

                return results;
            }
        };
    }
    protected List<FlatRateItem> getFilteredResults(String constraint) {
        List<FlatRateItem> results = new ArrayList<>();

        for (FlatRateItem item : originalList) {
            if(item.getDescription()==null ) {
                if ((item.getDescription()!=null && item.getDescription().toLowerCase().contains(constraint)) || (item.getFlatRateName()!=null &&item.getFlatRateName().toLowerCase().contains(constraint))) {
                    results.add(item);
                }
            }else if(item.getFlatRateName().toLowerCase().contains(constraint)){
                results.add(item);
            }
        }
        return results;
    }



    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mNameView;
        public final TextView mDescriptionView;
        public final TextView mPriceView;
        public FlatRateItem mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mNameView = (TextView) view.findViewById(R.id.flatRateItemName);
            mDescriptionView = (TextView) view.findViewById(R.id.flatRateItemDescription);
            mPriceView=(TextView)view.findViewById(R.id.flatRateItemPrice);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mNameView.getText() + "'";
        }
    }
}
