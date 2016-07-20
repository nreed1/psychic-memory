package com.example.niki.fieldoutlookandroid.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.niki.fieldoutlookandroid.R;
import com.example.niki.fieldoutlookandroid.businessobjects.OtherTask;
import com.example.niki.fieldoutlookandroid.helper.DBHelper;
import com.example.niki.fieldoutlookandroid.helper.singleton.Global;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnNewOtherTaskFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link NewOtherTaskFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewOtherTaskFragment extends Fragment {

    private OnNewOtherTaskFragmentInteractionListener mListener;
    private EditText name;
    private EditText description;
    private DBHelper dbHelper;

    public NewOtherTaskFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NewOtherTaskFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NewOtherTaskFragment newInstance(String param1, String param2) {
        NewOtherTaskFragment fragment = new NewOtherTaskFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
        this.setHasOptionsMenu(false);
        dbHelper=new DBHelper(this.getActivity().getApplicationContext());
       // setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_new_other_task, container, false);
        name=(EditText)view.findViewById(R.id.otherTaskNameEditText);
        description=(EditText)view.findViewById(R.id.otherTaskDescriptionEditText);

        Button saveOtherTask=(Button)view.findViewById(R.id.otherTaskSaveButton);

        saveOtherTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dbHelper.SaveOtherTask(new OtherTask(0, Global.GetInstance().getUser().GetUserId(),name.getText().toString(),description.getText().toString()));
                onButtonPressed("Back");
            }
        });
        return view;
    }


    public void onButtonPressed(String uri) {
        if (mListener != null) {
            mListener.onNewOtherTaskFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnNewOtherTaskFragmentInteractionListener) {
            mListener = (OnNewOtherTaskFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnNewOtherTaskFragmentInteractionListener");
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
    public interface OnNewOtherTaskFragmentInteractionListener {

        void onNewOtherTaskFragmentInteraction(String whatToDo);
    }
}
