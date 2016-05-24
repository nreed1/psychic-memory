package com.example.niki.fieldoutlookandroid.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.niki.fieldoutlookandroid.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnStartDayFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link StartDayFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StartDayFragment extends Fragment {

    private OnStartDayFragmentInteractionListener mListener;

    public StartDayFragment() {
        // Required empty public constructor
    }
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnStartDayFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnStartDayFragmentInteractionListener");
        }
    }
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment StartDayFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static StartDayFragment newInstance(String param1, String param2) {
        StartDayFragment fragment = new StartDayFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
        this.setHasOptionsMenu(false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_start_day, container, false);
        Button travelTo=(Button) view.findViewById(R.id.travelToButton);
        travelTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnButtonPressed("TravelTo");
            }
        });

        Button atShop= (Button)view.findViewById(R.id.atShopButton);
        atShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnButtonPressed("AtShop");
            }
        });


        Button otherTask=(Button)view.findViewById(R.id.otherTaskButton);
        otherTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnButtonPressed("Other");
            }
        });

        Button endDay=(Button)view.findViewById(R.id.endDayButton);
        endDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnButtonPressed("EndDay");
            }
        });

        return view;
    }

    public void OnButtonPressed(String buttonPressed){
        if(mListener!=null){
            mListener.onStartDayFragmentInteraction(buttonPressed);
        }
    }
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onStartDayFragmentInteraction(uri.toString());
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnStartDayFragmentInteractionListener) {
            mListener = (OnStartDayFragmentInteractionListener) context;
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
    public interface OnStartDayFragmentInteractionListener {
        // TODO: Update argument type and name
        void onStartDayFragmentInteraction(String nextFragment);
       // void onFragmentInteraction(Uri uri);
    }
}
