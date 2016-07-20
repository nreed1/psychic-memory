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
import com.example.niki.fieldoutlookandroid.businessobjects.Quote;
import com.example.niki.fieldoutlookandroid.businessobjects.QuotePart;
import com.example.niki.fieldoutlookandroid.businessobjects.WorkOrder;
import com.example.niki.fieldoutlookandroid.businessobjects.WorkOrderMaterial;
import com.example.niki.fieldoutlookandroid.businessobjects.WorkOrderPart;
import com.example.niki.fieldoutlookandroid.fragment.PartListFragment;
import com.example.niki.fieldoutlookandroid.fragment.dummy.DummyContent.DummyItem;
import com.example.niki.fieldoutlookandroid.helper.DBHelper;
import com.example.niki.fieldoutlookandroid.helper.ExceptionHelper;
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
    private Quote quote;
    private boolean isWorkOrderMaterial=false;
    private ArrayList<Integer> partIdsInWorkOrder=new ArrayList<>();

    private ArrayList<Part> selectedParts=new ArrayList<>();


    public PartListRecyclerViewAdapter(Context context,List<PartCategory> items, List<Part> parts,PartCategory partCategory, WorkOrder workOrder,PartListFragment.OnPartListFragmentInteractionListener listener, PartListFragment.OnPartListPartFragmentInteractionListener partListener,Quote quote, boolean isWorkOrderMaterial) {
        this.isWorkOrderMaterial=isWorkOrderMaterial;
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
        this.quote=quote;
        if(workOrder!=null &&workOrder.getPartList()!=null && !workOrder.getPartList().isEmpty()&& isWorkOrderMaterial==false){
            for (WorkOrderPart workOrderPart:workOrder.getPartList()){
                partIdsInWorkOrder.add(workOrderPart.getPartId());
            }
            SelectedPartsSingleton.getInstance().addAll(workOrder.getPartList());
        }else if(workOrder!=null &&workOrder.getWorkOrderMaterials()!=null && !workOrder.getWorkOrderMaterials().isEmpty()&& isWorkOrderMaterial==true){
            for(WorkOrderMaterial workOrderMaterial: workOrder.getWorkOrderMaterials()){
                partIdsInWorkOrder.add(workOrderMaterial.getPartId());
            }
            SelectedPartsSingleton.getInstance().addAll(workOrder.getWorkOrderMaterials());
        }else if(quote!=null && quote.getParts()!=null && !quote.getParts().isEmpty()){
            for (QuotePart quotePart:quote.getParts()){
                partIdsInWorkOrder.add(quotePart.getPartId());
            }
            SelectedPartsSingleton.getInstance().addAll(quote.getParts());
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
                if(workOrder!=null) {
                    workOrder.addWorkOrderPartToList(new WorkOrderPart(p, 1, null));
                }else if(quote!=null){
                    if(quote.getParts()==null) quote.setParts(new ArrayList<QuotePart>());
                    quote.addWorkOrderPartToList(new QuotePart(p,1,null));
                }
            }
//            if(workOrder!=null && isWorkOrderMaterial==false) {
//                dbHelper.SaveWorkOrderPartList(workOrder.getWorkOrderId(), workOrder.getPartList());
//            }else if(workOrder!=null && isWorkOrderMaterial==true){
//                dbHelper.SaveWorkOrderMaterialsNeeded(workOrder.getWorkOrderId(),workOrder.getWorkOrderMaterials());
//            }else if(quote!=null){
//                dbHelper.SaveQuotePartList(quote.getParts(),quote.getQuoteId());
//            }

            PartListRecyclerViewAdapter.this.notifyDataSetChanged();
        }
    }


    public void unselectAll(){
        isAllSelected=false;
        isUnSelected=true;
        if(mParts!=null && !mParts.isEmpty()) {
//           // WorkOrderPartHelper.getInstance().removePartList((ArrayList<Part>) mParts);
//            if(workOrder!=null && isWorkOrderMaterial==false) {
//                for (Part p : mParts) {
//                    workOrder.removeWorkOrderPartFromList(new WorkOrderPart(p, 1, null));
//                }
//                dbHelper.SaveWorkOrderPartList(workOrder.getWorkOrderId(), workOrder.getPartList());
//            }else if(workOrder!=null && isWorkOrderMaterial==true){
//                for (Part p:mParts){
//                    workOrder.removeWorkOrderMaterialFromList(new WorkOrderMaterial(p,1));
//                }
//            }else if(quote!=null){
//                for (Part p:mParts){
//                    quote.removeWorkOrderPartFromList(new QuotePart(p,1,null));
//                }
//            }
            selectedParts=new ArrayList<>();
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
                holder.partPrice.setVisibility(View.INVISIBLE);
            }else{
                if(mValues!=null && mParts!=null) {
                    holder.mPart = mParts.get(position - mValues.size());
                    holder.categoryName.setText(holder.mPart.getNumberAndDescription());
                    holder.iconImageView.setImageResource(R.mipmap.ic_wrench);
                    holder.partPrice.setVisibility(View.VISIBLE);
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
                holder.partPrice.setVisibility(View.VISIBLE);
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
                holder.partPrice.setVisibility(View.INVISIBLE);
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
                    //selectedParts.add(holder.mPart);
                    boolean exists=false;
                    for(Part p :SelectedPartsSingleton.getInstance()) {
                        if(p.getPartId()==holder.mPart.getPartId()){
                            exists=true;
                            break;
                        }
                    }
                    if(!exists)
                        SelectedPartsSingleton.getInstance().add(holder.mPart);
                   // WorkOrderPartHelper.getInstance().addPart(holder.mPart);
//                    if(workOrder!=null && isWorkOrderMaterial==false){
//                        workOrder.addWorkOrderPartToList(new WorkOrderPart(holder.mPart,1,null));
//                    }else if(workOrder!=null && isWorkOrderMaterial==true){
//                        workOrder.addWorkOrderMaterialFromList(new WorkOrderMaterial(holder.mPart,1));
//                    }else if(quote!=null){
//                        quote.addWorkOrderPartToList(new QuotePart(holder.mPart,1,null));
//                    }
                }else {
                    //selectedParts.remove(holder.mPart);
                    SelectedPartsSingleton.getInstance().remove(holder.mPart);
                    //WorkOrderPartHelper.getInstance().removePart(holder.mPart);
//                    if(workOrder!=null && isWorkOrderMaterial==false){
//                        workOrder.removeWorkOrderPartFromList(new WorkOrderPart(holder.mPart,1,null));
//                    }else if(workOrder!=null && isWorkOrderMaterial==true){
//                        workOrder.removeWorkOrderMaterialFromList(new WorkOrderMaterial(holder.mPart,1));
//                    }else if(quote!=null){
//                        quote.removeWorkOrderPartFromList(new QuotePart(holder.mPart,1,null));
//                    }
                }
            }
        });
    }

    public void SaveSelection(){
        if(workOrder!=null && isWorkOrderMaterial==false){
            for (Part mPart:SelectedPartsSingleton.getInstance()) {
                workOrder.addWorkOrderPartToList(new WorkOrderPart(mPart, 1, null));
            }
           dbHelper.SaveWorkOrderPartList(workOrder.getWorkOrderId(),workOrder.getPartList());
        }else if(workOrder!=null && isWorkOrderMaterial==true){
            for (Part mPart:SelectedPartsSingleton.getInstance()) {
                workOrder.addWorkOrderMaterialFromList(new WorkOrderMaterial(mPart, 1));
            }
            dbHelper.SaveWorkOrderMaterialsNeeded(workOrder.getWorkOrderId(),workOrder.getWorkOrderMaterials());
        }else if(quote!=null){
            for (Part mPart:SelectedPartsSingleton.getInstance()) {
                quote.addWorkOrderPartToList(new QuotePart(mPart, 1, null));
            }
            dbHelper.SaveQuotePartList(quote.getParts(),quote.getQuoteId());
        }

    }

    public void addItem(Part part){
        try {
            mParts.add(part);
            PartListRecyclerViewAdapter.this.notifyDataSetChanged();
        }catch (Exception ex){
            ExceptionHelper.LogException(context,ex);
        }
    }

    @Override
    public int getItemCount() {
        if(category!=null) return mValues.size()+mParts.size();
        if(mParts!=null && !mParts.isEmpty()) return mParts.size();

        if(mValues==null) return 0;
        return mValues.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
               // list = (List<Part>) results.values;
                if(results!=null&&results.values!=null&&((ArrayList<?>)results.values).get(0) instanceof Part){
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
            if(item.getNumberAndDescription()==null) {
                if ((item.getPartNumber()!=null && item.getPartNumber().toLowerCase().contains(constraint)) || (item.getDescription()!=null &&item.getDescription().toLowerCase().contains(constraint))) {
                    results.add(item);
                }
            }else if(item.getNumberAndDescription().toLowerCase().contains(constraint)){
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
