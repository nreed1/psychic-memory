package com.example.niki.fieldoutlookandroid.fragment;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.niki.fieldoutlookandroid.R;
import com.example.niki.fieldoutlookandroid.businessobjects.Person;
import com.example.niki.fieldoutlookandroid.businessobjects.Quote;
import com.example.niki.fieldoutlookandroid.helper.DBHelper;
import com.example.niki.fieldoutlookandroid.helper.array_adapters.CustomerAutoCompleteArrayAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link QuoteFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link QuoteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QuoteFragment extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    private static final String ARG_SELECTED_QUOTE="selected-quote";


    private Quote selectedQuote;
    private Quote newQuote=new Quote();
    private AutoCompleteTextView customerName;
    private TextView customerAddress;
    private EditText description;
    private EditText notes;
    private ListView partList;

    private OnFragmentInteractionListener mListener;
    private OnAddPartsInteractionListener onAddPartsInteractionListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment QuoteFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static QuoteFragment newInstance(String param1, String param2) {
        QuoteFragment fragment = new QuoteFragment();
        Bundle args = new Bundle();

        return fragment;
    }

    public QuoteFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

            selectedQuote=getArguments().getParcelable(ARG_SELECTED_QUOTE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_quote, container, false);
        customerAddress=(TextView)view.findViewById(R.id.customerAddressQuote);
        description=(EditText)view.findViewById(R.id.descriptionQuote);
        notes=(EditText)view.findViewById(R.id.notesQuote);
        partList=(ListView)view.findViewById(R.id.partListQuote);
        customerName=(AutoCompleteTextView)view.findViewById(R.id.customerNameQuote);
        CustomerAutoCompleteArrayAdapter customerAutoCompleteArrayAdapter=new CustomerAutoCompleteArrayAdapter(getActivity(),new DBHelper(getActivity()).GetAllPersons());
        customerName.setAdapter(customerAutoCompleteArrayAdapter);
        customerName.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(selectedQuote==null) {
                    newQuote.setCustomer((Person) parent.getItemAtPosition(position));
                    customerAddress.setText(newQuote.getCustomer().getAddress().getPrintableAddress());
                }else {
                    selectedQuote.setCustomer((Person)parent.getItemAtPosition(position));
                    customerAddress.setText(selectedQuote.getCustomer().getAddress().getPrintableAddress());
                }
            }
        });


        Button addParts=(Button)view.findViewById(R.id.quoteAddPart);
        addParts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAddPartsInteractionListener.onAddPartsInteraction(selectedQuote);
            }
        });
        if(selectedQuote!=null){
            customerAddress.setText(selectedQuote.getCustomer().getAddress().getPrintableAddress());
            description.setText(selectedQuote.getDescription());
            notes.setText(selectedQuote.getNotes());

        }
        description.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(selectedQuote==null){
                    newQuote.setDescription(s.toString());
                }else{
                    selectedQuote.setDescription(s.toString());
                }
            }
        });

        notes.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(selectedQuote==null){
                    newQuote.setNotes(s.toString());
                }else{
                    selectedQuote.setNotes(s.toString());
                }
            }
        });

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
            if(activity instanceof OnAddPartsInteractionListener){
                onAddPartsInteractionListener=(OnAddPartsInteractionListener)activity;
            }
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
        public void onFragmentInteraction(Uri uri);
    }

    public interface  OnAddPartsInteractionListener{
        public void onAddPartsInteraction(Quote selectedQuote);
    }

}
