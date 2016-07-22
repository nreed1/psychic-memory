package com.example.niki.fieldoutlookandroid.helper.array_adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.example.niki.fieldoutlookandroid.R;
import com.example.niki.fieldoutlookandroid.businessobjects.FlatRateItem;
import com.example.niki.fieldoutlookandroid.businessobjects.Part;
import com.example.niki.fieldoutlookandroid.businessobjects.Quote;
import com.example.niki.fieldoutlookandroid.businessobjects.QuotePart;
import com.example.niki.fieldoutlookandroid.businessobjects.WorkOrder;
import com.example.niki.fieldoutlookandroid.businessobjects.WorkOrderPart;
import com.example.niki.fieldoutlookandroid.fragment.QuoteFragment;
import com.example.niki.fieldoutlookandroid.fragment.WorkOrderPartFragment;
import com.example.niki.fieldoutlookandroid.helper.DBHelper;
import com.example.niki.fieldoutlookandroid.helper.ExceptionHelper;

import java.util.ArrayList;

/**
 * Created by Owner on 7/15/2016.
 */
public class QuotePartListArrayAdapter extends ArrayAdapter<QuotePart>  implements View.OnCreateContextMenuListener, View.OnLongClickListener, PopupMenu.OnMenuItemClickListener{
    private  Quote mValues;
    private  QuoteFragment.OnFragmentInteractionListener mListener;
    private Activity context;
    private int positio=0;

    public QuotePartListArrayAdapter(Context context, int resource) {
        super(context, resource);
    }


    public QuotePartListArrayAdapter(Quote items, QuoteFragment.OnFragmentInteractionListener listener, Activity context) {
        super(context, R.layout.workorder_part_list_item);

        mValues = items;
        mListener = listener;
        this.context=context;
    }

//    @Override
//    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.workorder_part_list_item, parent, false);
//        return new ViewHolder(view);
//    }
public View getView(final int position, View convertView, final ViewGroup parent) {
    ViewHolder holder=null;
    if(convertView == null)
    {
        this.positio=position;
        LayoutInflater inflater = context.getLayoutInflater();
        convertView = inflater.inflate(R.layout.workorder_cardview,parent,false);
        holder = new ViewHolder();
        holder.mView=convertView;
        holder.mNumberandDescription = (TextView) convertView.findViewById(R.id.workOrderPartListNameAndDescription);
          holder.  mQuantity = (NumberPicker) convertView.findViewById(R.id.workOrderPartListQuantity);
            //view.setOnCreateContextMenuListener(this);
            holder.mView.setOnLongClickListener(this);
        holder.mItem = mValues.getParts().get(position);
        convertView.setTag(holder);
    }else{
        holder = (ViewHolder) convertView.getTag();
    }

    if(mValues.getParts().get(position).getNumberAndDescription()!=null && !mValues.getParts().get(position).getNumberAndDescription().isEmpty()) {
        holder.mNumberandDescription.setText(mValues.getParts().get(position).getNumberAndDescription());
    }else{

        String partNumber=mValues.getParts().get(position).getPartNumber();
        if(partNumber!=null &&!partNumber.isEmpty()) {
            holder.mNumberandDescription.setText(mValues.getParts().get(position).getPartNumber() + "-" + mValues.getParts().get(position).getDescription());
        }else if(!mValues.getParts().get(position).getDescription().isEmpty()){
            holder.mNumberandDescription.setText( mValues.getParts().get(position).getDescription());
        }else if(mValues.getParts().get(position).getFlatRateItem()!=null){
            FlatRateItem flatRateItem=mValues.getParts().get(position).getFlatRateItem();
            holder.mNumberandDescription.setText(flatRateItem.getFlatRateName()+" - "+flatRateItem.getDescription());
        }
    }
    //holder.mQuantity.setDisplayedValues(null);
    holder.mQuantity.setMinValue(0);
    holder.mQuantity.setMaxValue(1000);
    holder.mQuantity.setFocusableInTouchMode(true);
    holder.mQuantity.setWrapSelectorWheel(true);

    holder.mQuantity.setValue(mValues.getParts().get(position).getQuantity());
    holder.mQuantity.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
        @Override
        public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
            //holder.mItem.setQuantity(newVal);
            mValues.getParts().get(position).setQuantity(newVal);
            DBHelper dbHelper=new DBHelper(context);
            //dbHelper.UpdateWorkOrderPartQuantity(mValues.getWorkOrderId(),holder.mItem.getPartId(),newVal);
        }
    });
    return convertView;
}
//    @Override
//    public void onBindViewHolder(final ViewHolder holder, int position) {
//        holder.mItem = mValues.getParts().get(position);
//        if(mValues.getParts().get(position).getNumberAndDescription()!=null && !mValues.getParts().get(position).getNumberAndDescription().isEmpty()) {
//            holder.mNumberandDescription.setText(mValues.getParts().get(position).getNumberAndDescription());
//        }else{
//
//            String partNumber=mValues.getParts().get(position).getPartNumber();
//            if(partNumber!=null &&!partNumber.isEmpty()) {
//                holder.mNumberandDescription.setText(mValues.getParts().get(position).getPartNumber() + "-" + mValues.getParts().get(position).getDescription());
//            }else if(!mValues.getParts().get(position).getDescription().isEmpty()){
//                holder.mNumberandDescription.setText( mValues.getParts().get(position).getDescription());
//            }else if(mValues.getParts().get(position).getFlatRateItem()!=null){
//                FlatRateItem flatRateItem=mValues.getParts().get(position).getFlatRateItem();
//                holder.mNumberandDescription.setText(flatRateItem.getFlatRateName()+" - "+flatRateItem.getDescription());
//            }
//        }
//        //holder.mQuantity.setDisplayedValues(null);
//        holder.mQuantity.setMinValue(0);
//        holder.mQuantity.setMaxValue(1000);
//        holder.mQuantity.setFocusableInTouchMode(true);
//        holder.mQuantity.setWrapSelectorWheel(true);
//
//        holder.mQuantity.setValue(mValues.getParts().get(position).getQuantity());
//        holder.mQuantity.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
//            @Override
//            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
//                holder.mItem.setQuantity(newVal);
//                DBHelper dbHelper=new DBHelper(context);
//                //dbHelper.UpdateWorkOrderPartQuantity(mValues.getWorkOrderId(),holder.mItem.getPartId(),newVal);
//            }
//        });

//        holder.mView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (null != mListener) {
//                    // Notify the active callbacks interface (the activity, if the
//                    // fragment is attached to one) that an item has been selected.
//                    //mListener.onWorkOrderPartListFragmentInteraction(holder.mItem);
//                }
//            }
//        });
//    }
    public void addItem(Part part){
        try {
            mValues.getParts().add(new QuotePart(part,1,null));
            DBHelper db=new DBHelper(context);
            db.SaveQuotePartList(mValues.getParts(),mValues.getQuoteId());
            db=null;
            QuotePartListArrayAdapter.this.notifyDataSetChanged();
        }catch (Exception ex){
            ExceptionHelper.LogException(context,ex);
        }
    }
    public void removeItem(QuotePart part){
        try {
            mValues.getParts().remove(part);
            DBHelper db=new DBHelper(context);
            db.SaveQuotePartList(mValues.getParts(),mValues.getQuoteId());
            db=null;
            QuotePartListArrayAdapter.this.notifyDataSetChanged();

        }catch (Exception ex){
            ExceptionHelper.LogException(context,ex);
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.setHeaderTitle("Select The Action");
        menu.add(0, v.getId(), 0, "Remove");//groupId, itemId, order, title

    }



//        @Override
//    public boolean onContextItemSelected(MenuItem item) {
//    }



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
        removeItem(this.mValues.getParts().get(positio));

        return true;
    }

//    @Override
//    public int getItemCount() {
//        if(mValues.getParts()==null) {
//            mValues.setParts(new ArrayList<QuotePart>());
//        }
//        return mValues.getParts().size();
//    }



    public class ViewHolder {
        public  View mView;
        public  TextView mNumberandDescription;
        public  NumberPicker mQuantity;
        public QuotePart mItem;

        public ViewHolder() {
           // super(view);
            //mView = view;

//            mNumberandDescription = (TextView) view.findViewById(R.id.workOrderPartListNameAndDescription);
//            mQuantity = (NumberPicker) view.findViewById(R.id.workOrderPartListQuantity);
//            //view.setOnCreateContextMenuListener(this);
//            view.setOnLongClickListener(this);


        }

        @Override
        public String toString() {
            return super.toString() + " '" + mNumberandDescription.getText() + "'";
        }

//        @Override
//        public void onCreateContextMenu(ContextMenu menu, View v,
//                                        ContextMenu.ContextMenuInfo menuInfo) {
//
//

//
//        }
    }
}
