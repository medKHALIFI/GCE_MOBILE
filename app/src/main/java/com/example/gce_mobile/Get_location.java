package com.example.gce_mobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Get_location extends AppCompatActivity implements LocationListener {


    Button getLocationBtn;
    TextView locationText;

    LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_location);



        getLocationBtn = (Button)findViewById(R.id.getLocationBtn);
        locationText = (TextView)findViewById(R.id.locationText);

        getLocationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLocation();
            }
        });


    }


    void getLocation() {
        try {
            Toast.makeText(Get_location.this, "test 1", Toast.LENGTH_SHORT).show();

            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5, this);
        }
        catch(SecurityException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onLocationChanged(Location location) {
        Toast.makeText(Get_location.this, "location changed", Toast.LENGTH_SHORT).show();

        locationText.setText("Current Location: " + location.getLatitude() + ", " + location.getLongitude());

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {
        Toast.makeText(Get_location.this, "Please Enable GPS and Internet", Toast.LENGTH_SHORT).show();


    }

    @Override
    public void onProviderDisabled(String s) {

    }
}
