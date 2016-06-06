package com.example.niki.fieldoutlookandroid.fragment;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.opengl.Visibility;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.niki.fieldoutlookandroid.R;
import com.example.niki.fieldoutlookandroid.businessobjects.WorkOrder;

import static com.example.niki.fieldoutlookandroid.R.menu.selected_workorder_menu;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SelectedWorkorderFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SelectedWorkorderFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SelectedWorkorderFragment extends Fragment {

    private static final String SELECTED_WORKORDER="workOrder";
    private static final String SHOW_TIMEKEEPING_INFORMATION="showTimekeepingInformation";

    private WorkOrder selectedWorkOrder;
    private OnFragmentInteractionListener mListener;
    private Boolean showTimekeepingInformation=false;

    public SelectedWorkorderFragment() {
        // Required empty public constructor
    }


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SelectedWorkorderFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SelectedWorkorderFragment newInstance(String param1, String param2) {
        SelectedWorkorderFragment fragment = new SelectedWorkorderFragment();
        Bundle args = new Bundle();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            selectedWorkOrder=getArguments().getParcelable(SELECTED_WORKORDER);
            showTimekeepingInformation=getArguments().getBoolean(SHOW_TIMEKEEPING_INFORMATION);
        }

        this.setHasOptionsMenu(true);

    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(selected_workorder_menu,menu);
        if(showTimekeepingInformation==true){
            menu.findItem(R.id.arrivedAtSiteItem).setVisible(true);
        }else{
            menu.findItem(R.id.arrivedAtSiteItem).setVisible(false);
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.arrivedAtSiteItem:

                return true;
            case R.id.viewPartsList:

                return true;
            case R.id.unassignWorkOrder:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_selected_workorder, container, false);
        TextView workOrderName=(TextView)view.findViewById(R.id.workOrderNameTextView);
        workOrderName.setText(selectedWorkOrder.getName());

        TextView workOrderDescription=(TextView)view.findViewById(R.id.descriptionTextView);
        workOrderDescription.setText(selectedWorkOrder.getDescription());
        TextView workOrderNotes=(TextView)view.findViewById(R.id.notesTextView);
        workOrderNotes.setText(selectedWorkOrder.getNotes());
        if(selectedWorkOrder.getPerson()!=null) {
            TextView workOrderCustomer = (TextView) view.findViewById(R.id.customerNameTextView);
            workOrderCustomer.setText(selectedWorkOrder.getPerson().getFullName());
            if(selectedWorkOrder.getPerson().getAddress()!=null) {
                TextView workOrderCustomerAddress = (TextView) view.findViewById(R.id.customerAddressTextView);
                workOrderCustomerAddress.setText(selectedWorkOrder.getPerson().getAddress().getPrintableAddress());
            }
        }

        ProgressBar progressBar =(ProgressBar)view.findViewById(R.id.progressBar);
        if(selectedWorkOrder.getTotalHoursForJob()==0){
            selectedWorkOrder.setTotalHoursForJob(100);
        }
        progressBar.setMax(selectedWorkOrder.getTotalHoursForJob());

        if(selectedWorkOrder.getHoursWorkedOnJob()==0){
            selectedWorkOrder.setHoursWorkedOnJob(40);
        }
        progressBar.setProgress(selectedWorkOrder.getHoursWorkedOnJob());

        TextView progressBarTextView=(TextView) view.findViewById(R.id.progressBarTextView);
        progressBarTextView.setText(selectedWorkOrder.getHoursWorkedOnJob()+" hours / "+selectedWorkOrder.getTotalHoursForJob()+" hours");
        if(selectedWorkOrder.getHoursWorkedOnJob()>selectedWorkOrder.getTotalHoursForJob()){
            //ColorStateList colorStateList=ColorStateList.valueOf(Color.argb(255,216,83,83));
            progressBar.getProgressDrawable().setColorFilter(Color.RED, PorterDuff.Mode.DARKEN);

        }
        else if(((double)selectedWorkOrder.getHoursWorkedOnJob()/selectedWorkOrder.getTotalHoursForJob())>=0.50){
            progressBar.getProgressDrawable().setColorFilter(Color.YELLOW, PorterDuff.Mode.DARKEN);
        }else {
            progressBar.getProgressDrawable().setColorFilter(Color.argb(255,154,219,233), PorterDuff.Mode.DARKEN);
        }
        return view;
    }


    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnStartDayFragmentInteractionListener");
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
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
