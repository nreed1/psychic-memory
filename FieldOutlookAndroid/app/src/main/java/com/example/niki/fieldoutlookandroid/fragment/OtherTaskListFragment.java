package com.example.niki.fieldoutlookandroid.fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListAdapter;

import com.example.niki.fieldoutlookandroid.R;
import com.example.niki.fieldoutlookandroid.businessobjects.OtherTask;
import com.example.niki.fieldoutlookandroid.fragment.dummy.DummyContent.DummyItem;
import com.example.niki.fieldoutlookandroid.helper.DBHelper;
import com.example.niki.fieldoutlookandroid.helper.TimekeepingHelper;
import com.example.niki.fieldoutlookandroid.helper.array_adapters.OtherTaskArrayAdapter;
import com.example.niki.fieldoutlookandroid.helper.singleton.Global;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnOtherTaskListFragmentInteractionListener}
 * interface.
 */
public class OtherTaskListFragment extends Fragment implements AdapterView.OnItemClickListener{

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnOtherTaskListFragmentInteractionListener mListener;
    DBHelper dbHelper;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public OtherTaskListFragment() {

    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static OtherTaskListFragment newInstance(int columnCount) {
        OtherTaskListFragment fragment = new OtherTaskListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setHasOptionsMenu(true);
        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
        dbHelper=new DBHelper(this.getActivity().getApplicationContext());

    }
    @Override
    public void onCreateOptionsMenu(
            Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.other_task_list_menu, menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_add_other_task:
                CreateNewOtherTask();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private void CreateNewOtherTask(){
        if (mListener != null) {
            mListener.onOtherTaskListFragmentInteraction(null);
        }
    }

    private AbsListView mListView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_othertask_list, container, false);

      //  View view = inflater.inflate(R.layout.fragment_job, container, false);
        OtherTaskArrayAdapter otherTaskArrayAdapter=new OtherTaskArrayAdapter(this.getActivity(),1,dbHelper.GetOtherTaskListByUserId(Global.GetInstance().getUser().GetUserId()));
        // Set the adapter
        mListView = (AbsListView) view.findViewById(R.id.otherTaskList);
        ((AdapterView<ListAdapter>) mListView).setAdapter(otherTaskArrayAdapter);

        // Set OnItemClickListener so we can be notified on item clicks
        mListView.setOnItemClickListener(this);


        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnOtherTaskListFragmentInteractionListener) {
            mListener = (OnOtherTaskListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnOtherTaskListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        TimekeepingHelper timekeepingHelper=new TimekeepingHelper();
        timekeepingHelper.AddOtherTaskTimekeepingEntry(getActivity(),"other",((OtherTask)parent.getItemAtPosition(position)).getId());
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
    public interface OnOtherTaskListFragmentInteractionListener {

        void onOtherTaskListFragmentInteraction(OtherTask item);
    }
}
