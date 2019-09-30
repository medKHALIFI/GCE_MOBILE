package com.example.gce_mobile;

import androidx.fragment.app.FragmentActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.Feature;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.geojson.GeoJsonFeature;
import com.google.maps.android.geojson.GeoJsonLayer;
import com.google.maps.android.geojson.GeoJsonLineStringStyle;
import com.google.maps.android.geojson.GeoJsonPoint;



//


import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class MapsListAllPhoto extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    public GeoJsonLayer layer ;

    //
    // for location image
    ArrayList<String> mLocationImage = new ArrayList<String>();
    ArrayList<String> mTitle = new ArrayList<String>();
    ArrayList<String> mDescription = new ArrayList<String>();
    ArrayList<String> images =new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_list_all_photo);
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
        float lat = 0 ;
        float log = 0;




///******** add layers
        try {
            Log.d("layers","add layers");
//            layerCom = new GeoJsonLayer(gmap, R.raw.decoupage_admin, getActivity());
            layer = new GeoJsonLayer(mMap, R.raw.layer, getApplicationContext());
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText( getApplicationContext(), "Unable to read file", Toast.LENGTH_SHORT).show();
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Unable to parse file", Toast.LENGTH_SHORT).show();
        }


        // add layer to the map
        layer.addLayerToMap();


        // get data all point


        // test get data from database
        //get data from sqlite
        Cursor cursor =MainActivity.sqLiteHelper.getData("SELECT * FROM MISSION");

        //cursor.moveToFirst() ;
        Log.d("mylog","before while : ");

        while (cursor.moveToNext())
        //for(int i =0 ;i< 5;i++)
        {
            Log.d("mylog","inside while : ");
            //int id = cursor.getInt(0);
            String localisation =  cursor.getString(1);
            String name = cursor.getString(2);
            String mission = cursor.getString(3);
            String path = cursor.getString(4);
            //mTitle[i] = name ;


            mLocationImage.add(localisation);
            mTitle.add(name); //this adds an element to the list.
            mDescription.add(mission); //this adds an element to the list.
            images.add(path); //this adds an element to the list.


            //String latLog = mLocationImage.get(position) ;
            String[] parts = localisation.split(",");
            String part1 = parts[0]; // lat
            String part2 = parts[1]; // long


            lat = Float.parseFloat(part1) ;
            log = Float.parseFloat(part2) ;


            //
            // create geojson point
            GeoJsonPoint point = new GeoJsonPoint(new LatLng(lat, log));
            HashMap<String, String> properties = new HashMap<String, String>();
            properties.put("name", name);
            properties.put("mission", mission);
            properties.put("path", path);
            GeoJsonFeature pointFeature = new GeoJsonFeature(point, "name", properties, null);


            // add feature to layer
            layer.addFeature(pointFeature);


        }
        // adapt our camera
        // Add a marker in Sydney and move the camera
        if (lat == 0) {
            lat = (float) 33.972362;
            log = (float) -6.879582;

        }
        LatLng centrer = new LatLng(lat, log);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(centrer, 16.2f));
        //mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(centrer));

        // Set a listener for geometry clicked events.
        layer.setOnFeatureClickListener(new GeoJsonLayer.GeoJsonOnFeatureClickListener() {
            @Override
            public void onFeatureClick(GeoJsonFeature feature) {
               // Log.d("gce",feature.getProperty("id "));
                Log.d("gce",feature.getProperty("name"));
                // display data
                Toast toast = Toast.makeText(getApplicationContext(), feature.getProperty("name"), Toast.LENGTH_SHORT);
                toast.show();



                // StringBuffer buffer = new StringBuffer();
                // buffer.append("\n Code DU :" + feature.getProperty("name") + "\n");
               /* buffer.append("\n Code Zoning :" + feature.getProperty("Code_Zonin") + "\n");
                buffer.append("\n classe zoning :" + feature.getProperty("Classe_Zon") + "\n");
                buffer.append("\n Longueur :" + feature.getProperty("Shape_Leng") + "\n");
                buffer.append("\n Superficie :" + feature.getProperty("Shape_Area") + "\n");
                buffer.append("\n Resume_Zon :" + feature.getProperty("Resume_Zon") + "\n");
                buffer.append("\n orig_ogc_f :" + feature.getProperty("orig_ogc_f") + "\n");
*/
                // showMessage("Informations", buffer.toString());
            }
        });


    }
}
