package com.example.niki.fieldoutlookandroid.fragment;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.niki.fieldoutlookandroid.R;
import com.example.niki.fieldoutlookandroid.businessobjects.WorkOrder;
import com.example.niki.fieldoutlookandroid.helper.DBHelper;
import com.example.niki.fieldoutlookandroid.helper.DateHelper;
import com.example.niki.fieldoutlookandroid.helper.NotificationHelper;
import com.example.niki.fieldoutlookandroid.helper.TimekeepingHelper;
import com.example.niki.fieldoutlookandroid.helper.UnassignAsyncTask;


import static com.example.niki.fieldoutlookandroid.R.menu.selected_workorder_menu;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnSelectedWorkOrderFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SelectedWorkorderFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SelectedWorkorderFragment extends Fragment {

    private static final String SELECTED_WORKORDER="workOrder";
    private static final String SHOW_TIMEKEEPING_INFORMATION="showTimekeepingInformation";

    private WorkOrder selectedWorkOrder;
    private OnSelectedWorkOrderFragmentInteractionListener mListener;
    private OnSelectedWorkOrderMenuItemInteractionListener menuItemInteractionListener;
    private OnWorkOrderMaterialsClickedListener materialsClickedListener;
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
                TimekeepingHelper timekeepingHelper=new TimekeepingHelper();

                timekeepingHelper.AddTimekeepingEntry(getActivity(),"arrived");
                return true;
            case R.id.viewPartsList:
                menuItemInteractionListener.onSelectedWorkOrderMenuItemInteraction(selectedWorkOrder);
               // onButtonPressed("ViewParts");
                return true;
            case R.id.unassignWorkOrder:
                if(selectedWorkOrder!=null) {
                    UnassignAsyncTask unassignAsyncTask = new UnassignAsyncTask(new UnassignAsyncTask.UnassignDelegate() {
                        @Override
                        public void processFinish(Boolean result) {
                            if (result) {
                                NotificationHelper.NotifyUser(getActivity(), "Unassign Successful");
                                DBHelper dbHelper=new DBHelper(getActivity());
                                dbHelper.DeleteWorkOrderById(selectedWorkOrder.getWorkOrderId());
                            }
                        }
                    }, getActivity());
                    unassignAsyncTask.execute(selectedWorkOrder.getWorkOrderId());

                }
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
        //WorkOrder Name
        TextView workOrderName=(TextView)view.findViewById(R.id.workOrderNameTextView);
        workOrderName.setText(selectedWorkOrder.getName());

        //Need Materials? Button
        Button materialsButton=(Button)view.findViewById(R.id.needMaterialsButton);
        materialsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                materialsClickedListener.onWorkOrderMaterialsClicked(selectedWorkOrder);

            }
        });

        //Description
        TextView workOrderDescription=(TextView)view.findViewById(R.id.descriptionTextView);
        workOrderDescription.setText(selectedWorkOrder.getDescription());
        EditText workOrderNotes=(EditText) view.findViewById(R.id.notesEditTextView);
        workOrderNotes.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                selectedWorkOrder.setNotes(s.toString());
                if(!s.toString().trim().equals("")) {
                    DBHelper dbHelper = new DBHelper(getActivity());
                    dbHelper.SaveWorkOrder(selectedWorkOrder);
                }
            }
        });
        workOrderNotes.setText(selectedWorkOrder.getNotes());
        if(selectedWorkOrder.getPerson()!=null) {
            TextView workOrderCustomer = (TextView) view.findViewById(R.id.customerNameTextView);
            workOrderCustomer.setText(selectedWorkOrder.getPerson().getFullName());
            if(selectedWorkOrder.getPerson().getAddress()!=null) {
                TextView workOrderCustomerAddress = (TextView) view.findViewById(R.id.customerAddressTextView);
                workOrderCustomerAddress.setText(selectedWorkOrder.getPerson().getAddress().getPrintableAddress());
            }
        }

        final CheckBox workOrderCompleted=(CheckBox)view.findViewById(R.id.workorderComplete);
        if(selectedWorkOrder.getIsReadyForInvoice()==1){//1==true as sqlite can't store booleans for some reason
            workOrderCompleted.setChecked(true);
        }else{ workOrderCompleted.setChecked(false);}
        workOrderCompleted.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    if(selectedWorkOrder.getIsReadyForInvoice()!=1) {
                        selectedWorkOrder.setIsReadyForInvoice(1);
                        selectedWorkOrder.setReadyForInvoiceDateTime(DateHelper.GetTodayDateAsString());
                        DBHelper dbHelper = new DBHelper(getActivity());
                        dbHelper.SaveWorkOrder(selectedWorkOrder);
                    }
                }else{
                    selectedWorkOrder.setIsReadyForInvoice(0);
                    selectedWorkOrder.setReadyForInvoiceDateTime(null);
                    DBHelper dbHelper=new DBHelper(getActivity());
                    dbHelper.SaveWorkOrder(selectedWorkOrder);
                }
            }
        });

        ProgressBar progressBar =(ProgressBar)view.findViewById(R.id.progressBar);
        if(selectedWorkOrder.getJobTime()!=null) {
            progressBar.setMax(selectedWorkOrder.getJobTime().getProjectedHours());
            progressBar.setProgress(selectedWorkOrder.getJobTime().getActualHours());

//        if(selectedWorkOrder.getTotalHoursForJob()==0){
//            selectedWorkOrder.setTotalHoursForJob(100);
//        }
//        progressBar.setMax(selectedWorkOrder.getTotalHoursForJob());

//        if(selectedWorkOrder.getHoursWorkedOnJob()==0){
//            selectedWorkOrder.setHoursWorkedOnJob(40);
//        }
//        progressBar.setProgress(selectedWorkOrder.getHoursWorkedOnJob());

            TextView progressBarTextView = (TextView) view.findViewById(R.id.progressBarTextView);
            progressBarTextView.setText(selectedWorkOrder.getJobTime().getActualHours() + " hours / " + selectedWorkOrder.getJobTime().getProjectedHours() + " hours");
            if (selectedWorkOrder.getJobTime().getActualHours() > selectedWorkOrder.getJobTime().getProjectedHours()) {
                //ColorStateList colorStateList=ColorStateList.valueOf(Color.argb(255,216,83,83));
                progressBar.getProgressDrawable().setColorFilter(Color.RED, PorterDuff.Mode.ADD);

            } else if (((double) selectedWorkOrder.getJobTime().getActualHours() / selectedWorkOrder.getJobTime().getProjectedHours()) >= 0.50) {
                progressBar.getProgressDrawable().setColorFilter(Color.YELLOW, PorterDuff.Mode.ADD);
            } else {
                progressBar.getProgressDrawable().setColorFilter(Color.argb(255, 154, 219, 233), PorterDuff.Mode.ADD);
            }
        }
        return view;
    }


    public void onButtonPressed(String uri) {
        if (mListener != null) {
            mListener.onSelectedWorkOrderFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnSelectedWorkOrderFragmentInteractionListener) {
            mListener = (OnSelectedWorkOrderFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnStartDayFragmentInteractionListener");
        }
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof OnSelectedWorkOrderFragmentInteractionListener) {
            mListener = (OnSelectedWorkOrderFragmentInteractionListener) activity;
        }
        if(activity instanceof OnSelectedWorkOrderMenuItemInteractionListener){
            menuItemInteractionListener=(OnSelectedWorkOrderMenuItemInteractionListener)activity;
        }if(activity instanceof OnWorkOrderMaterialsClickedListener){
            materialsClickedListener=(OnWorkOrderMaterialsClickedListener)activity;
        }
        else {
            throw new RuntimeException(activity.toString()
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
    public interface OnSelectedWorkOrderFragmentInteractionListener {
        // TODO: Update argument type and name
        void onSelectedWorkOrderFragmentInteraction(String uri);
    }
    public interface OnSelectedWorkOrderMenuItemInteractionListener{
        void onSelectedWorkOrderMenuItemInteraction(WorkOrder selectedWorkorder);
    }
    public interface  OnWorkOrderMaterialsClickedListener{
        void onWorkOrderMaterialsClicked(WorkOrder selectedWorkOrder);
    }
}
