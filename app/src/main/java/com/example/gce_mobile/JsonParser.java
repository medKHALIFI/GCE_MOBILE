package com.example.gce_mobile;

/**
 * Created by ADJ on 8/9/2017.
 */

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.example.gce_mobile.Configuration.KEY_ID;
import static com.example.gce_mobile.Configuration.KEY_IMAGE;
import static com.example.gce_mobile.Configuration.KEY_NAME;
import static com.example.gce_mobile.Configuration.KEY_MISSION;
import static com.example.gce_mobile.Configuration.KEY_USERS;
import static com.example.gce_mobile.Configuration.KEY_LOCATION;


public class JsonParser {
    public static String[] uIds;
    public static String[] uNames;
    public static String[] uMissions;
    public static String[] uLocations;
    public static String[] uImages;


    private JSONArray users = null;

    private String json;

    public JsonParser(String json){
        this.json = json;
    }

    protected void parseJSON(){
        JSONObject jsonObject=null;
        try {
            jsonObject = new JSONObject(json);
            users = jsonObject.getJSONArray(KEY_USERS);

            Log.e("json","users length : "+users.length());
            uIds = new String[users.length()];
            uNames = new String[users.length()];
            uMissions = new String[users.length()];
            uImages = new String[users.length()];
            uLocations = new String[users.length()];

            for(int i=0;i<users.length();i++){
                JSONObject jo = users.getJSONObject(i);
                uIds[i] = jo.getString(KEY_ID);
                uNames[i] = jo.getString(KEY_NAME);
                uMissions[i] = jo.getString(KEY_MISSION);
                uImages[i] = jo.getString(KEY_IMAGE);
                uLocations[i] = jo.getString(KEY_LOCATION);
                Log.e("json","users length : "+ jo.getString(KEY_ID));
            }

            // Log.e("uImage","ser image"+uImages[0]);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
