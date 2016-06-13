package com.example.niki.fieldoutlookandroid.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.niki.fieldoutlookandroid.R;
import com.example.niki.fieldoutlookandroid.businessobjects.Part;
import com.example.niki.fieldoutlookandroid.businessobjects.PartCategory;
import com.example.niki.fieldoutlookandroid.helper.DBHelper;
import com.example.niki.fieldoutlookandroid.helper.array_adapters.PartListRecyclerViewAdapter;

import java.util.ArrayList;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnPartListFragmentInteractionListener}
 * interface.
 */
public class PartListFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    private static final String ARG_CATEGORIES_GIVEN="categories-given";
    private static final String ARG_PARTS="parts";
    private static final String ARG_PART_CATEGORY="part-category";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnPartListFragmentInteractionListener mListener;
    private OnPartListPartFragmentInteractionListener mPartListener;
    private ArrayList<PartCategory> categories;
    private ArrayList<Part> parts;
    private PartCategory partCategory;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public PartListFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static PartListFragment newInstance(int columnCount) {
        PartListFragment fragment = new PartListFragment();
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
            categories=getArguments().getParcelableArrayList(ARG_CATEGORIES_GIVEN);
            parts=getArguments().getParcelableArrayList(ARG_PARTS);
            partCategory=getArguments().getParcelable(ARG_PART_CATEGORY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_partlist_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            mColumnCount=0;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            if(partCategory!=null){}

            else if(categories==null) {
                categories = new DBHelper(getActivity()).GetPartCategoryList(-100);
            }
            recyclerView.setAdapter(new PartListRecyclerViewAdapter(categories,parts,partCategory, mListener,mPartListener));
        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnPartListFragmentInteractionListener) {
            mListener = (OnPartListFragmentInteractionListener) context;

        } else if(context instanceof OnPartListPartFragmentInteractionListener) {
            mPartListener=(OnPartListPartFragmentInteractionListener) context;
        }
        else
         {
            throw new RuntimeException(context.toString()
                    + " must implement OnPartListFragmentInteractionListener");
        }
    }
    @Override
    public void onAttach(Activity context) {
        super.onAttach(context);
        if (context instanceof OnPartListFragmentInteractionListener) {
            mListener = (OnPartListFragmentInteractionListener) context;

        } else if(context instanceof OnPartListPartFragmentInteractionListener) {
            mPartListener=(OnPartListPartFragmentInteractionListener) context;
        }
        else
        {
            throw new RuntimeException(context.toString()
                    + " must implement OnPartListFragmentInteractionListener");
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
    public interface OnPartListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onPartListFragmentInteraction(PartCategory item);
    }
    public interface OnPartListPartFragmentInteractionListener{
        void onPartListPartInteraction(Part item);
    }
}
