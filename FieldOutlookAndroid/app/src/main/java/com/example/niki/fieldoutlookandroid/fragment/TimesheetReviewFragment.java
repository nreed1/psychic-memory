package com.example.niki.fieldoutlookandroid.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TimePicker;

import com.example.niki.fieldoutlookandroid.R;
import com.example.niki.fieldoutlookandroid.businessobjects.TimeEntry;
import com.example.niki.fieldoutlookandroid.helper.DBHelper;
import com.example.niki.fieldoutlookandroid.helper.array_adapters.TimeEntryReviewArrayAdapter;

import java.util.ArrayList;
import java.util.Date;

import static com.example.niki.fieldoutlookandroid.R.layout.timelinelayout;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TimesheetReviewFragment.OnTimesheetReviewFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TimesheetReviewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TimesheetReviewFragment extends Fragment {
    private ArrayList<TimeEntry> timeEntries;
    TimeEntryReviewArrayAdapter timeEntryReviewArrayAdapter;
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private DBHelper dbHelper;
    private OnTimesheetReviewFragmentInteractionListener mListener;

    public TimesheetReviewFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TimesheetReviewFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TimesheetReviewFragment newInstance(String param1, String param2) {
        TimesheetReviewFragment fragment = new TimesheetReviewFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_timesheet_review, container, false);

        Button acceptTimeEntriesButton=(Button)view.findViewById(R.id.acceptTimeEntriesButton);
        acceptTimeEntriesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder warn=new AlertDialog.Builder(getActivity());
                warn.setMessage("You cannot undo this action. \nAre you sure you wish to accept the time entries?");
                warn.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dbHelper.AcceptTimeEntryListForToday();
                    }
                });
                warn.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                warn.show();
            }
        });
        ListView timeLineList=(ListView)view.findViewById(R.id.timeLineListView);
        registerForContextMenu(timeLineList);
        if(dbHelper==null)dbHelper=new DBHelper(this.getActivity().getApplicationContext());
        timeEntries=dbHelper.GetTimeEntryListForToday();
        timeEntryReviewArrayAdapter=new TimeEntryReviewArrayAdapter(this.getActivity(),R.layout.timelinelayout, timeEntries);

        timeLineList.setAdapter(timeEntryReviewArrayAdapter);
        return view;
    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.timekeeping_context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        final AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item
                .getMenuInfo();

        switch (item.getItemId()) {
            case R.id.timekeeping_adjust:
                final Date newDate=new Date();
                final AlertDialog.Builder alert=new AlertDialog.Builder(getActivity());
                alert.setTitle("Update Time");
                LayoutInflater layoutInflater=getActivity().getLayoutInflater();
                View adjust=layoutInflater.inflate(R.layout.timekeeping_adjust_time_layout,null);
                final TimePicker timePicker=(TimePicker)adjust.findViewById(R.id.timekeepking_timePicker);
                timePicker.setCurrentHour(timeEntries.get(info.position).getStartDateTime().getHours());
                timePicker.setCurrentMinute(timeEntries.get(info.position).getStartDateTime().getMinutes());
                timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
                    @Override
                    public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                        newDate.setHours(hourOfDay);
                        newDate.setMinutes(minute);
                    }
                });
                alert.setView(adjust);


                alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        timeEntries.get(info.position).getStartDateTime().setHours(newDate.getHours());
                        timeEntries.get(info.position).getStartDateTime().setMinutes(newDate.getMinutes());
                        DBHelper dbHelper=new DBHelper(getActivity());
                        dbHelper.UpdateTimeEntry(timeEntries.get(info.position));
                        timeEntryReviewArrayAdapter.getItem(info.position).setStartDateTime(timeEntries.get(info.position).getStartDateTime());
                        timeEntryReviewArrayAdapter.notifyDataSetChanged();
                    }
                });
                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                alert.show();
                return true;
            case R.id.timekeeping_delete:
                DBHelper dbHelper=new DBHelper(getActivity());
                dbHelper.DeleteTimeEntry(timeEntries.get(info.position).getSqlId());
                timeEntryReviewArrayAdapter.remove(timeEntries.get(info.position));
                timeEntryReviewArrayAdapter.notifyDataSetChanged();
                //dbHelper
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onTimesheetReviewFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnTimesheetReviewFragmentInteractionListener) {
            mListener = (OnTimesheetReviewFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnTimesheetReviewFragmentInteractionListener");
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
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnTimesheetReviewFragmentInteractionListener {
        // TODO: Update argument type and name
        void onTimesheetReviewFragmentInteraction(Uri uri);
    }
}
