package com.example.gce_mobile;

import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivityViewPhoto extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_view_photo);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        // get location
        // getIntent() is a method from the started activity
        Intent myIntent = getIntent(); // gets the previously created intent
        String part1 = myIntent.getStringExtra("lat"); // will return "FirstKeyValue"
        String part2= myIntent.getStringExtra("log"); // will return "SecondKeyValue"



        float lat = Float.parseFloat(part1) ;
        float log = Float.parseFloat(part2) ;
        // Add a marker in Sydney and move the camera
        // 33.988345, -6.851919


        LatLng centrer = new LatLng(lat, log);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(centrer, 16.2f));
        mMap.addMarker(new MarkerOptions().position(centrer).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(centrer));
    }
}
