package com.example.niki.fieldoutlookandroid.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListAdapter;

import com.example.niki.fieldoutlookandroid.R;
import com.example.niki.fieldoutlookandroid.businessobjects.WorkOrder;
import com.example.niki.fieldoutlookandroid.helper.DBHelper;
import com.example.niki.fieldoutlookandroid.helper.array_adapters.WorkOrderArrayAdapter;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TravelToFragment.OnTravelToFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TravelToFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TravelToFragment extends Fragment implements AbsListView.OnItemClickListener {

    ArrayList<WorkOrder> workOrders;
    private OnTravelToFragmentInteractionListener mListener;
    private ListAdapter mAdapter;
    DBHelper dbHelper;
    private AbsListView mListView;
    public TravelToFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TravelToFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TravelToFragment newInstance(String param1, String param2) {
        TravelToFragment fragment = new TravelToFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
        workOrders = dbHelper.GetWorkOrders();

        mAdapter = new WorkOrderArrayAdapter(getActivity(),
                R.layout.workorder_list_item, workOrders);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_travel_to, container, false);
        // Set the adapter
        mListView = (AbsListView) view.findViewById(R.id.travelToListView);
        ((AdapterView<ListAdapter>) mListView).setAdapter(mAdapter);

        // Set OnItemClickListener so we can be notified on item clicks
        mListView.setOnItemClickListener(this);
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(WorkOrder workOrder) {
        if (mListener != null) {
            mListener.onTravelToFragmentInteraction(workOrder);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnTravelToFragmentInteractionListener) {
            mListener = (OnTravelToFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnTravelToFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
         this.onButtonPressed((WorkOrder) parent.getItemAtPosition(position) );
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
    public interface OnTravelToFragmentInteractionListener {
        // TODO: Update argument type and name
        void onTravelToFragmentInteraction(WorkOrder workOrder);
    }
}
