package com.example.niki.fieldoutlookandroid.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.example.niki.fieldoutlookandroid.R;
import com.example.niki.fieldoutlookandroid.businessobjects.Person;
import com.example.niki.fieldoutlookandroid.businessobjects.WorkOrder;
import com.example.niki.fieldoutlookandroid.helper.array_adapters.WorkOrderArrayAdapter;
import com.example.niki.fieldoutlookandroid.helper.assigned_job_service.AssignedJobServiceHelper;
import com.example.niki.fieldoutlookandroid.helper.available_workorders.GetAvailableWorkOrdersAsyncTask;
import com.example.niki.fieldoutlookandroid.helper.dummy.DummyContent;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Large screen devices (such as tablets) are supported by replacing the ListView
 * with a GridView.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnFragmentInteractionListener}
 * interface.
 */
public class AvailableJobFragment extends Fragment implements AbsListView.OnItemClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    /**
     * The fragment's ListView/GridView.
     */
    private AbsListView mListView;

    /**
     * The Adapter which will be used to populate the ListView/GridView with
     * Views.
     */
    private ListAdapter mAdapter;
    private ProgressDialog progressDialog;

    // TODO: Rename and change types of parameters
    public static AvailableJobFragment newInstance(String param1, String param2) {
        AvailableJobFragment fragment = new AvailableJobFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    ArrayList<Person> personList;
    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public AvailableJobFragment() {
       // progressDialog=new ProgressDialog(getActivity());
        GetAvailableWorkOrdersAsyncTask getAvailableWorkOrdersAsyncTask=new GetAvailableWorkOrdersAsyncTask(new GetAvailableWorkOrdersAsyncTask.GetAvailableWorkOrdersDelegate() {
            @Override
            public void processFinish(ArrayList<WorkOrder> workOrders) {
                mAdapter=new WorkOrderArrayAdapter(getActivity(),R.layout.workorder_list_item,workOrders);

             //   progressDialog.dismiss();
            }
        });
       // progressDialog.show();
        getAvailableWorkOrdersAsyncTask.execute((Void)null);
//        try {
//           // wait(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        // personList= new ArrayList<Person>();

       // mAdapter = new WorkOrderArrayAdapter(getActivity(),R.layout.workorder_list_item,)
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_job_available, container, false);

        // Set the adapter
        while(mAdapter!=null) {
            mListView = (AbsListView) view.findViewById(android.R.id.list);
            ((AdapterView<ListAdapter>) mListView).setAdapter(mAdapter);
        }

        // Set OnItemClickListener so we can be notified on item clicks
      //  mListView.setOnItemClickListener(this);

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnStartDayFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (null != mListener) {
            // Notify the active callbacks interface (the activity, if the
            // fragment is attached to one) that an item has been selected.
            mListener.onFragmentInteraction(DummyContent.ITEMS.get(position).id);
        }
    }

    /**
     * The default content for this Fragment has a TextView that is shown when
     * the list is empty. If you would like to change the text, call this method
     * to supply the text it should use.
     */
    public void setEmptyText(CharSequence emptyText) {
        View emptyView = mListView.getEmptyView();

        if (emptyView instanceof TextView) {
            ((TextView) emptyView).setText(emptyText);
        }
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
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(String id);
    }

}
