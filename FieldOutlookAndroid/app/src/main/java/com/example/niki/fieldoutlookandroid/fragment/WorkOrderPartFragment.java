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
import com.example.niki.fieldoutlookandroid.businessobjects.Part;
import com.example.niki.fieldoutlookandroid.businessobjects.WorkOrder;
import com.example.niki.fieldoutlookandroid.helper.array_adapters.WorkOrderPartRecyclerViewAdapter;

import java.util.ArrayList;

import static com.example.niki.fieldoutlookandroid.R.menu.workorder_part_menu;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnWorkOrderPartListFragmentInteractionListener}
 * interface.
 */
public class WorkOrderPartFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    private static  final String ARG_SELECTED_WORKORDER="selectedWorkOrder";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnWorkOrderPartListFragmentInteractionListener mListener;
    private OnWorkOrderPartMenuItemInteractionListener menuItemListener;
    private WorkOrder selectedWorkOrder;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public WorkOrderPartFragment() {
    }


    @SuppressWarnings("unused")
    public static WorkOrderPartFragment newInstance(int columnCount) {
        WorkOrderPartFragment fragment = new WorkOrderPartFragment();
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
        this.setHasOptionsMenu(true);
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(workorder_part_menu,menu);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        menuItemListener.onWorkOrderPartMenuItemInteraction(selectedWorkOrder,item);

        return true;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_workorder_part_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            recyclerView.setAdapter(new WorkOrderPartRecyclerViewAdapter(selectedWorkOrder, mListener));
        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnWorkOrderPartListFragmentInteractionListener) {
            mListener = (OnWorkOrderPartListFragmentInteractionListener) context;
        }
        if(context instanceof OnWorkOrderPartMenuItemInteractionListener){
            menuItemListener=(OnWorkOrderPartMenuItemInteractionListener)context;
        }
        else {
            throw new RuntimeException(context.toString()
                    + " must implement OnWorkOrderPartListFragmentInteractionListener");
        }
    }
    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);
        if (activity instanceof OnWorkOrderPartListFragmentInteractionListener) {
            mListener = (OnWorkOrderPartListFragmentInteractionListener) activity;
        }
        if(activity instanceof OnWorkOrderPartMenuItemInteractionListener){
            menuItemListener=(OnWorkOrderPartMenuItemInteractionListener)activity;
        }
        else {
            throw new RuntimeException(activity.toString()
                    + " must implement OnWorkOrderPartListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        menuItemListener=null;
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
    public interface OnWorkOrderPartListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onWorkOrderPartListFragmentInteraction(Part item);
    }
    public interface OnWorkOrderPartMenuItemInteractionListener{
        void onWorkOrderPartMenuItemInteraction(WorkOrder selectedWorkOrder,MenuItem item);
    }
}
