package com.example.niki.fieldoutlookandroid.helper.array_adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.niki.fieldoutlookandroid.R;
import com.example.niki.fieldoutlookandroid.businessobjects.FlatRateItem;
import com.example.niki.fieldoutlookandroid.fragment.FlatRateItemListFragment;
import com.example.niki.fieldoutlookandroid.fragment.dummy.DummyContent.DummyItem;

import java.util.Currency;
import java.util.List;
import java.util.Locale;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link com.example.niki.fieldoutlookandroid.fragment.FlatRateItemListFragment.OnFlatRateItemListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class FlatRateItemRecyclerViewAdapter extends RecyclerView.Adapter<FlatRateItemRecyclerViewAdapter.ViewHolder> {

    private final List<FlatRateItem> mValues;
    private final FlatRateItemListFragment.OnFlatRateItemListFragmentInteractionListener mListener;

    public FlatRateItemRecyclerViewAdapter(List<FlatRateItem> items, FlatRateItemListFragment.OnFlatRateItemListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
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
