package com.example.niki.fieldoutlookandroid.helper.array_adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.niki.fieldoutlookandroid.R;
import com.example.niki.fieldoutlookandroid.businessobjects.Quote;
import com.example.niki.fieldoutlookandroid.fragment.QuoteListFragment;
import com.example.niki.fieldoutlookandroid.fragment.QuoteListFragment.OnQuoteListFragmentInteractionListener;
import com.example.niki.fieldoutlookandroid.fragment.dummy.DummyContent.DummyItem;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link QuoteListFragment.OnQuoteListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class QuoteRecyclerViewAdapter extends RecyclerView.Adapter<QuoteRecyclerViewAdapter.ViewHolder> {

    private final List<Quote> mValues;
    private final OnQuoteListFragmentInteractionListener mListener;

    public QuoteRecyclerViewAdapter(List<Quote> items, OnQuoteListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.quote_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.customerName.setText(mValues.get(position).getCustomer().getFullName());
        holder.description.setText(mValues.get(position).getDescription());
        holder.amount.setText("$"+String.valueOf(mValues.get(position).getAmount()));

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onQuoteListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if(mValues!=null && !mValues.isEmpty()) {
            return mValues.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public Quote mItem;

        public final TextView customerName;
        public final TextView description;
        public final TextView amount;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            customerName=(TextView)mView.findViewById(R.id.quoteListItemCustomerName);
            description=(TextView)mView.findViewById(R.id.quoteListItemDescription);
            amount=(TextView)mView.findViewById(R.id.quoteListItemAmount);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + description.getText() + "'";
        }
    }
}
