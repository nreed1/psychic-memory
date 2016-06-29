package com.example.niki.fieldoutlookandroid.fragment;

import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.niki.fieldoutlookandroid.R;
import com.example.niki.fieldoutlookandroid.businessobjects.WorkOrder;
import com.example.niki.fieldoutlookandroid.helper.DBHelper;
import com.example.niki.fieldoutlookandroid.helper.ExceptionHelper;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private DBHelper dbHelper;
    private boolean getAllAvailable=false;
    private Boolean isAssigned=false;
    private ArrayList<MarkerOptions> markers;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        isAssigned = getIntent().getExtras().getBoolean("IsAssigned");
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        try {
            mMap = googleMap;
            markers=new ArrayList<>();
            Geocoder geocoder = new Geocoder(getApplicationContext());
            if (geocoder.isPresent()) {
                //Log.d("Geocoder", "IsPresent");
                dbHelper = new DBHelper(getApplicationContext());
                ArrayList<WorkOrder> workOrders = dbHelper.GetWorkOrders();
                for (WorkOrder w : workOrders) {
                    if (w.getPerson() != null && w.getPerson().getAddress() != null) {
                        List<Address> addresses = geocoder.getFromLocationName(w.getPerson().getAddress().getPrintableAddress(), 1);
                        Log.d("Address",w.getPerson().getAddress().getPrintableAddress());
                        if(!addresses.isEmpty()){
                            LatLng latLng=new LatLng(addresses.get(0).getLatitude(),addresses.get(0).getLongitude());
                            MarkerOptions marker=new MarkerOptions().position(latLng).title(w.getPerson().getFullName()+"- "+w.getDescription());
                            markers.add(marker);
                            mMap.addMarker(marker);

                            //mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                        }

                    }
                }
                LatLngBounds.Builder builder=new LatLngBounds.Builder();
                for(MarkerOptions marker:markers){
                    builder.include(marker.getPosition());
                }
                LatLngBounds bounds=builder.build();
                int padding=0;
                CameraUpdate cameraUpdate=CameraUpdateFactory.newLatLngBounds(bounds,padding);
                mMap.animateCamera(cameraUpdate);

            }
            else throw new Exception("Geocoder unavailable");

//            // Add a marker in Sydney and move the camera
//            LatLng sydney = new LatLng(-34, 151);
//            mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//            mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        }catch (Exception ex){
            ExceptionHelper.LogException(getApplicationContext(),ex);
        }
    }
}
