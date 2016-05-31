package com.example.niki.fieldoutlookandroid.fragment;

import android.app.FragmentManager;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.support.v7.widget.Toolbar;

import com.example.niki.fieldoutlookandroid.R;
import com.example.niki.fieldoutlookandroid.businessobjects.TimeEntryType;
import com.example.niki.fieldoutlookandroid.helper.DBHelper;
import com.example.niki.fieldoutlookandroid.helper.TimekeepingHelper;
import com.example.niki.fieldoutlookandroid.helper.singleton.Global;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link StartFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link StartFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StartFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    public StartFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment StartFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static StartFragment newInstance(String param1, String param2) {
        StartFragment fragment = new StartFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View view= inflater.inflate(R.layout.fragment_start, container, false);
        Button startDayButton=(Button)view.findViewById(R.id.start_day_button);
        if(Global.GetInstance().getIsDayStarted()==true){
            startDayButton.setText("Continue Day");
        }else {
            startDayButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    Toolbar toolbar=(Toolbar) v.getRootView().findViewById(R.id.toolbar);
                    toolbar.setTitle("Select Time Entry Type");
                    FragmentManager fragmentManager= getFragmentManager();
                    android.app.FragmentTransaction fragmentTransaction= fragmentManager.beginTransaction();
                    StartDayFragment startDayFragment= new StartDayFragment();
                    fragmentTransaction.replace(R.id.fragment_container, startDayFragment, "Start Day").addToBackStack("Time Entry Type Selection");
                    fragmentTransaction.commit();
                }
            });
        }
        Button viewAssignedJobsButton=(Button)view.findViewById(R.id.view_assigned_jobs);
        viewAssignedJobsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toolbar toolbar=(Toolbar) v.getRootView().findViewById(R.id.toolbar);
                toolbar.setTitle("Assigned Jobs");
                FragmentManager fragmentManager= getFragmentManager();
                android.app.FragmentTransaction fragmentTransaction= fragmentManager.beginTransaction();
                AssignedJobFragment assignedJobFragment=new AssignedJobFragment();
                fragmentTransaction.replace(R.id.fragment_container, assignedJobFragment, "Assigned Jobs").addToBackStack("Assigned Jobs");
                fragmentTransaction.commit();
            }
        });
        return view;
    }


    private void onStartDayClick(){
        if(Global.GetInstance().getIsDayStarted()==false){
            Global.GetInstance().setIsDayStarted(true);
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
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
