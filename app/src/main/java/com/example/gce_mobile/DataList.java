package com.example.gce_mobile;

/**
 * Created by ADJ on 8/8/2017.
 */



import android.app.ProgressDialog;
import android.os.Bundle;
// import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import static com.example.gce_mobile.Configuration.LIST_USER_URL;



public class DataList extends AppCompatActivity {





    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_list);




        listView = (ListView) findViewById(R.id.list_view);
        sendRequest();
    }

    private void sendRequest(){
        final ProgressDialog loading = ProgressDialog.show(this,"Download...","Please wait...",false,false);

        StringRequest stringRequest = new StringRequest(LIST_USER_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("myLog","reponse : "+response);
                        showJSON(response);

                        loading.dismiss();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(DataList.this,error.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });

        int socketTimeout = 30000; // 30 seconds. You can change it
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);

        stringRequest.setRetryPolicy(policy);


        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void showJSON(String json){
        JsonParser pj = new JsonParser(json);
        pj.parseJSON();
        Log.e("uName","ser image : "+pj.uImages[0]);
        DataListAdapter userListAdapter = new DataListAdapter(DataList.this, pj.uIds,pj.uNames,pj.uMissions,pj.uLocations,pj.uImages);
        listView.setAdapter(userListAdapter);
    }



}
