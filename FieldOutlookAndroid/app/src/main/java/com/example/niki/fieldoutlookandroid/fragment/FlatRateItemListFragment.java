package com.example.niki.fieldoutlookandroid.fragment;

import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.niki.fieldoutlookandroid.R;
import com.example.niki.fieldoutlookandroid.businessobjects.FlatRateItem;
import com.example.niki.fieldoutlookandroid.helper.DBHelper;
import com.example.niki.fieldoutlookandroid.helper.array_adapters.FlatRateItemRecyclerViewAdapter;

import java.util.ArrayList;

import static com.example.niki.fieldoutlookandroid.R.menu.action_searchable_menu;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnFlatRateItemListFragmentInteractionListener}
 * interface.
 */
public class FlatRateItemListFragment extends Fragment implements SearchView.OnQueryTextListener{


    private static final String ARG_COLUMN_COUNT = "column-count";
    private static final String ARG_FLATRATE_LIST="flatratelist";

    private int mColumnCount = 1;
    private ArrayList<FlatRateItem> flatRateItems=new ArrayList<>();
    private OnFlatRateItemListFragmentInteractionListener mListener;
    private FlatRateItemRecyclerViewAdapter mAdapter;

    private DBHelper dbHelper;


    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public FlatRateItemListFragment() {
    }


    @SuppressWarnings("unused")
    public static FlatRateItemListFragment newInstance(int columnCount) {
        FlatRateItemListFragment fragment = new FlatRateItemListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbHelper=new DBHelper(getActivity());
        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
            flatRateItems=getArguments().getParcelableArrayList(ARG_FLATRATE_LIST);
        }
        if(flatRateItems==null ||flatRateItems.isEmpty()){

            flatRateItems=dbHelper.GetFlatRateItems();
            Log.d("FlatRateItems",String.valueOf(flatRateItems.size()));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_flatrateitem_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            mAdapter=new FlatRateItemRecyclerViewAdapter(flatRateItems, mListener,getActivity());
            if(flatRateItems!=null) {
                recyclerView.setAdapter(mAdapter);
            }
        }
        return view;
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(action_searchable_menu,menu);
        final MenuItem item = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(this);
    }
    @Override
    public boolean onQueryTextChange(String query) {
        // Here is where we are going to implement our filter logic
        mAdapter.getFilter().filter(query);
        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {

            case R.id.selectAllItems:
                // if(parts!=null && !parts.isEmpty()){
                mAdapter.selectAll();
                // }

                return true;
            case R.id.unselectAllItems:
                //if(parts!=null && !parts.isEmpty()){
                    mAdapter.unselectAll();
               // }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFlatRateItemListFragmentInteractionListener) {
            mListener = (OnFlatRateItemListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFlatRateItemListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFlatRateItemListFragmentInteractionListener {

        void onFlatRateItemListFragmentInteraction(FlatRateItem item);
    }
}
