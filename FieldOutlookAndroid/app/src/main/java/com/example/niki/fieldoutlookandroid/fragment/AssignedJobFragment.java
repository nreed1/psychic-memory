package com.example.niki.fieldoutlookandroid.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.niki.fieldoutlookandroid.R;
import com.example.niki.fieldoutlookandroid.businessobjects.Person;
import com.example.niki.fieldoutlookandroid.businessobjects.WorkOrder;
import com.example.niki.fieldoutlookandroid.dummy.DummyContent;
import com.example.niki.fieldoutlookandroid.helper.TimekeepingHelper;
import com.example.niki.fieldoutlookandroid.helper.UnassignAsyncTask;
import com.example.niki.fieldoutlookandroid.helper.array_adapters.WorkOrderArrayAdapter;

import java.util.ArrayList;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Large screen devices (such as tablets) are supported by replacing the ListView
 * with a GridView.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnFragmentInteractionListener}
 * interface.
 */
public class AssignedJobFragment extends Fragment implements AbsListView.OnItemClickListener {


    private OnFragmentInteractionListener mListener;
    private TimekeepingHelper.TimekeepingInteractionListener timekeepingInteractionListener;

    /**
     * The fragment's ListView/GridView.
     */
    private AbsListView mListView;

    /**
     * The Adapter which will be used to populate the ListView/GridView with
     * Views.
     */
    private WorkOrderArrayAdapter mAdapter;
    private boolean travelTo=false;
    public static AssignedJobFragment newInstance(String param1, String param2) {
        AssignedJobFragment fragment = new AssignedJobFragment();
        Bundle args = new Bundle();
       // args.putString(ARG_PARAM1, param1);
        //args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    ArrayList<Person> personList;
    ArrayList<WorkOrder> workOrders;
    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public AssignedJobFragment() {
        Bundle b =getArguments();
        if(b!=null && !b.isEmpty()){

        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            workOrders=getArguments().getParcelableArrayList("workOrders");
            travelTo=getArguments().getBoolean("TravelTo");
           // mParam2 = getArguments().getString(ARG_PARAM2);
        }

        if(workOrders!=null)
        mAdapter = new WorkOrderArrayAdapter(getActivity(),
                R.layout.workorder_list_item, workOrders);


        this.setHasOptionsMenu(true);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        MenuInflater inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.workorder_list_item_context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch(item.getItemId()) {
            case R.id.unassignWorkOrderContext:
                // add stuff here
                UnassignAsyncTask unassignAsyncTask=new UnassignAsyncTask(new UnassignAsyncTask.UnassignDelegate() {
                    @Override
                    public void processFinish(Boolean result) {
                        if(result==false){
                            Dialog dialog=new Dialog(getActivity());
                            dialog.setTitle("Unable to unassign");
                            //dialog.setDismissMessage("OK");
                            dialog.show();
                        }
                    }
                },getActivity());
                unassignAsyncTask.execute(workOrders.get(info.position).getWorkOrderId());
                mAdapter.remove(workOrders.get(info.position));
                mAdapter.notifyDataSetChanged();
                return true;

            default:
                return super.onContextItemSelected(item);
        }
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.workorder_menu, menu);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.gotoMaps:
                Intent mapsIntent=new Intent(this.getActivity(),MapsActivity.class);
                Bundle b=new Bundle();
                b.putBoolean("IsAssigned",true);
                mapsIntent.putExtra("IsAssigned",true);
                startActivity(mapsIntent);

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_job, container, false);

        // Set the adapter
        mListView = (AbsListView) view.findViewById(android.R.id.list);
        if(mAdapter==null) return view;
        ((AdapterView<ListAdapter>) mListView).setAdapter(mAdapter);

        // Set OnItemClickListener so we can be notified on item clicks
        mListView.setOnItemClickListener(this);
        mListView.setLongClickable(true);
        registerForContextMenu(mListView);
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
            timekeepingInteractionListener=(TimekeepingHelper.TimekeepingInteractionListener)activity;
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
        if (null != mListener && travelTo==false) {
            // Notify the active callbacks interface (the activity, if the
            // fragment is attached to one) that an item has been selected.
            mListener.onFragmentInteraction(Integer.toString( position));
        }else if(travelTo==true){
            timekeepingInteractionListener.onTimekeepingInteraction((WorkOrder)parent.getItemAtPosition(position));
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
