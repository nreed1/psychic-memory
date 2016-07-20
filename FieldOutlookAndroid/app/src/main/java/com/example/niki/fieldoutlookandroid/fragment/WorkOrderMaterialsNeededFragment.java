package com.example.niki.fieldoutlookandroid.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.niki.fieldoutlookandroid.R;
import com.example.niki.fieldoutlookandroid.businessobjects.WorkOrder;
import com.example.niki.fieldoutlookandroid.businessobjects.WorkOrderMaterial;
import com.example.niki.fieldoutlookandroid.fragment.dummy.DummyContent;
import com.example.niki.fieldoutlookandroid.fragment.dummy.DummyContent.DummyItem;
import com.example.niki.fieldoutlookandroid.helper.DBHelper;
import com.example.niki.fieldoutlookandroid.helper.array_adapters.WorkOrderMaterialsNeededRecyclerViewAdapter;

/**
 * A fragment representing a list of Items.
 * <p>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class WorkOrderMaterialsNeededFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    private static final String ARG_SELECTED_WORKORDER="selectedworkorder";

    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;
    private OnAddWorkOrderMaterialInteractionListener materialInteractionListener;
    private DBHelper db;
    private WorkOrder selectedWorkOrder;
    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public WorkOrderMaterialsNeededFragment() {
    }


    @SuppressWarnings("unused")
    public static WorkOrderMaterialsNeededFragment newInstance(int columnCount) {
        WorkOrderMaterialsNeededFragment fragment = new WorkOrderMaterialsNeededFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
            selectedWorkOrder=getArguments().getParcelable(ARG_SELECTED_WORKORDER);
        }
        db=new DBHelper(getActivity());
        setHasOptionsMenu(true);
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.quote_list_menu, menu);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.addQuoteMenuItem:
               materialInteractionListener.onAddWorkOrderMaterialInteraction(selectedWorkOrder);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_workordermaterialsneeded_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }

            recyclerView.setAdapter(new WorkOrderMaterialsNeededRecyclerViewAdapter(db.GetWorkOrderMaterialsByWorkOrderId(selectedWorkOrder.getWorkOrderId()), mListener));
        }
        return view;
    }


    @Override
    public void onAttach(Activity context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        }
        if(context instanceof OnAddWorkOrderMaterialInteractionListener) {
            materialInteractionListener=(OnAddWorkOrderMaterialInteractionListener)context;
        }    else
         {
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
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(WorkOrderMaterial item);
    }
    public interface OnAddWorkOrderMaterialInteractionListener{
        void onAddWorkOrderMaterialInteraction(WorkOrder selectedWorkOrder);
    }
}
