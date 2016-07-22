package com.example.niki.fieldoutlookandroid.helper.array_adapters;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.inputmethodservice.Keyboard;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.example.niki.fieldoutlookandroid.R;
import com.example.niki.fieldoutlookandroid.businessobjects.WorkOrder;
import com.example.niki.fieldoutlookandroid.fragment.AssignedJobFragment;
import com.example.niki.fieldoutlookandroid.fragment.AvailableJobFragment;
import com.example.niki.fieldoutlookandroid.fragment.TravelToFragment;
import com.example.niki.fieldoutlookandroid.helper.TimekeepingHelper;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Niki on 5/19/2016.
 */
public class WorkOrderArrayAdapter extends ArrayAdapter<WorkOrder> {
    private final Activity _context;
    private final ArrayList<WorkOrder> rows;
    private final AssignedJobFragment.OnFragmentInteractionListener mListener;
    private final AvailableJobFragment.OnFragmentInteractionListener mAvailableListener;
    private final TravelToFragment.OnTravelToFragmentInteractionListener travelListener;
    private final TimekeepingHelper.TimekeepingInteractionListener timekeepingInteractionListener;
    private boolean travelTo=false;



    // @Override
 //   public void onMapReady(GoogleMap googleMap) {

//        Geocoder geocoder = new Geocoder(_context);
//        if (geocoder.isPresent()) {
//            List<Address> addresses = geocoder.getFromLocationName(w.getPerson().getAddress().getPrintableAddress(), 1);
//            Log.d("Address",.getPerson().getAddress().getPrintableAddress());
//            if(!addresses.isEmpty()){
//                LatLng latLng=new LatLng(addresses.get(0).getLatitude(),addresses.get(0).getLongitude());
//                MarkerOptions marker=new MarkerOptions().position(latLng).title(w.getPerson().getFullName()+"- "+w.getDescription());
//               // markers.add(marker);
//                googleMap.addMarker(marker);
//
//                //mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
//            }
//        }
 //   }

//    @Override
//    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.workorder_cardview, parent, false);
//        return new ViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(ViewHolder holder, int position) {
//        holder.workOrder=rows.get(position);
//    }
//
//    @Override
//    public int getItemCount() {
//        return rows.size();
//    }

    public class ViewHolder
    {
        WorkOrder workOrder;
        TextView workOrderName;
        TextView workOrderCustomerName;
        TextView workOrderDate;
        TextView workOrderTime;
        CardView backgroundLayout;
        Button navTo;
        MapView cardMap;

        public ViewHolder(){

        }
        public ViewHolder(View convertView)  {
           // super(convertView);
            workOrderCustomerName = (TextView) convertView.findViewById(R.id.listItemWorkOrderCustomer);
            workOrderName = (TextView) convertView.findViewById(R.id.listItemWorkOrderName);
            workOrderDate=(TextView)convertView.findViewById(R.id.listItemDateOfWorkOrder);
            workOrderTime=(TextView)convertView.findViewById(R.id.listItemTimeOfWorkOrder);
            //holder.backgroundLayout=(LinearLayout)convertView.findViewById(R.id.workOrderListItemLinearLayout);
            backgroundLayout=(CardView)convertView.findViewById(R.id.card_view);
            navTo=(Button)convertView.findViewById(R.id.nextNavTo);
//            cardMap=(MapView)convertView.findViewById(R.id.card_map_view);
//            cardMap.onCreate(null);
//            cardMap.getMapAsync(this);
        }
//
//        @Override
//        public void onMapReady(GoogleMap googleMap) {
//            Geocoder geocoder = new Geocoder(_context);
//        if (geocoder.isPresent()) {
//            List<Address> addresses = geocoder.getFromLocationName(w.getPerson().getAddress().getPrintableAddress(), 1);
//            Log.d("Address",this.wor.getPerson().getAddress().getPrintableAddress());
//            if(!addresses.isEmpty()){
//                LatLng latLng=new LatLng(addresses.get(0).getLatitude(),addresses.get(0).getLongitude());
//                MarkerOptions marker=new MarkerOptions().position(latLng).title(w.getPerson().getFullName()+"- "+w.getDescription());
//               // markers.add(marker);
//                googleMap.addMarker(marker);
//
//                //mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
//            }
//        }
//        }
    }

    public WorkOrderArrayAdapter(AssignedJobFragment.OnFragmentInteractionListener mListener, TimekeepingHelper.TimekeepingInteractionListener timekeepingInteractionListener, Activity context, int workorder_list_item, ArrayList<WorkOrder> rows, boolean travelTo)
    {
        super(context, R.layout.workorder_cardview ,rows);
        this._context = context;
        this.rows = rows;
        this.mListener=mListener;
        mAvailableListener=null;
        travelListener=null;
        this.travelTo=travelTo;
        this.timekeepingInteractionListener=timekeepingInteractionListener;

    }
    public WorkOrderArrayAdapter(AvailableJobFragment.OnFragmentInteractionListener mListener, Activity context, int workorder_list_item, ArrayList<WorkOrder> rows)
    {
        super(context, R.layout.workorder_cardview ,rows);
        this._context = context;
        this.rows = rows;
        this.mAvailableListener=mListener;
        this.mListener=null;
        travelListener=null;
        this.timekeepingInteractionListener=null;
    }
    public WorkOrderArrayAdapter(TravelToFragment.OnTravelToFragmentInteractionListener mListener, Activity context, int workorder_list_item, ArrayList<WorkOrder> rows)
    {
        super(context, R.layout.workorder_cardview ,rows);
        this._context = context;
        this.rows = rows;
        this.mAvailableListener=null;
        this.mListener=null;
        travelListener=mListener;
        this.timekeepingInteractionListener=null;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent){
        ViewHolder holder = null;

        if(convertView == null)
        {
            LayoutInflater inflater = _context.getLayoutInflater();
            convertView = inflater.inflate(R.layout.workorder_cardview,parent,false);

            holder = new ViewHolder();
            holder.workOrderCustomerName = (TextView) convertView.findViewById(R.id.listItemWorkOrderCustomer);
            holder.workOrderName = (TextView) convertView.findViewById(R.id.listItemWorkOrderName);
            holder.workOrderDate=(TextView)convertView.findViewById(R.id.listItemDateOfWorkOrder);
            holder.workOrderTime=(TextView)convertView.findViewById(R.id.listItemTimeOfWorkOrder);
            //holder.backgroundLayout=(LinearLayout)convertView.findViewById(R.id.workOrderListItemLinearLayout);
            holder.backgroundLayout=(CardView)convertView.findViewById(R.id.card_view);
            holder.navTo=(Button)convertView.findViewById(R.id.nextNavTo);
//            holder.cardMap=(MapView)convertView.findViewById(R.id.card_map_view);
//            holder.cardMap.onCreate(null);
//            holder.cardMap.getMapAsync(this);



            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.backgroundLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(timekeepingInteractionListener!=null && travelTo==true){
                    timekeepingInteractionListener.onTimekeepingInteraction(rows.get(position));
                }
                else if(mListener!=null) {
                    mListener.onFragmentInteraction(String.valueOf(position));
                }else if(mAvailableListener!=null) mAvailableListener.onFragmentInteraction(String.valueOf(position));
                else if(travelListener!=null) travelListener.onTravelToFragmentInteraction(rows.get(position));
            }
        });
        if((position % 2) == 1){

           // holder.backgroundLayout.setBackgroundColor( getContext().getResources().getColor( R.color.themeBlue));
            holder.backgroundLayout.setBackgroundResource(R.drawable.card_background);

        }else{

            holder.backgroundLayout.setBackgroundColor(getContext().getResources().getColor(R.color.listItemBackground));
        }
        DateFormat dateFormat= new SimpleDateFormat("MM/dd");
        DateFormat timeFormat= new SimpleDateFormat("HH:mm");
        holder.workOrderName.setText(""+rows.get(position).getName()+"- "+rows.get(position).getDescription());
        if(rows.get(position).getPerson()!=null) {
            holder.workOrderCustomerName.setText(rows.get(position).getPerson().getFullName());
            holder.navTo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Uri gmmIntentUri= Uri.parse("google.navigation:q="+rows.get(position).getPerson().getAddress().getPrintableAddress().toString().replace(' ','+'));
                    Intent mapIntent=new Intent(Intent.ACTION_VIEW,gmmIntentUri);
                    mapIntent.setPackage("com.google.android.apps.maps");
                    //startActivity(mapIntent);
                    _context.startActivity(mapIntent);
                }
            });
        }
        if(rows.get(position).getArrivalTime()!=null) {
            holder.workOrderDate.setText(dateFormat.format(rows.get(position).getArrivalTimeDate()));
            holder.workOrderTime.setText(timeFormat.format(rows.get(position).getArrivalTimeDate()));
        }

        return convertView;
    }
}
