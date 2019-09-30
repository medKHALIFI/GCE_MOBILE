package com.example.gce_mobile;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.android.volley.Response;

//import com.google.android.gms.common.api.Response;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.example.gce_mobile.Configuration.ADD_USER_URL;
import static com.example.gce_mobile.Configuration.KEY_ACTION;
import static com.example.gce_mobile.Configuration.KEY_ID;
import static com.example.gce_mobile.Configuration.KEY_IMAGE;
import static com.example.gce_mobile.Configuration.KEY_LOCATION;
import static com.example.gce_mobile.Configuration.KEY_MISSION;
import static com.example.gce_mobile.Configuration.KEY_NAME;

// java scripte google sheets
/*
function doGet(e) {

        var op = e.parameter.action;

        if(op=="insert")
        return insert_value(e);


        if(op=="readAll")
        return read_all_value(e);


        }

        function doPost(e) {

        var op = e.parameter.action;

        if(op=="insert")
        return insert_value(e);


        if(op=="readAll")
        return read_all_value(e);


        }



        var SCRIPT_PROP = PropertiesService.getScriptProperties();
// see: https://developers.google.com/apps-script/reference/properties/


        function setup() {
        var doc = SpreadsheetApp.getActiveSpreadsheet();
        SCRIPT_PROP.setProperty("key", doc.getId());
        }



        function insert_value(e) {

        var doc     = SpreadsheetApp.openById(SCRIPT_PROP.getProperty("key"));
        var sheet   = doc.getSheetByName('sheet1'); // select the responses sheet




        var uId = e.parameter.uId;
        var  uName= e.parameter.uName;
        var uImage = e.parameter.uImage;
        var uLocation = e.parameter.uLocation;
        var uMission = e.parameter.uMission;


        //var dropbox = "USERS IMAGE";
        var dropbox = uMission;
        var folder, folders = DriveApp.getFoldersByName(dropbox);

        if (folders.hasNext()) {
        folder = folders.next();
        } else {
        folder = DriveApp.createFolder(dropbox);
        }

        var fileName = uName+"profile_pic.jpg";


        var contentType = "image/jpg",
        bytes = Utilities.base64Decode(uImage),
        blob = Utilities.newBlob(bytes, contentType,fileName);
        var file = folder.createFile(blob);

        file.setSharing(DriveApp.Access.ANYONE_WITH_LINK,DriveApp.Permission.VIEW);
        var fileId=file.getId();

        var fileUrl = "https://drive.google.com/uc?export=view&id="+fileId;



        sheet.appendRow([uId,uLocation,uName,uMission,fileUrl]);

        return ContentService.createTextOutput("oky").setMimeType(ContentService.MimeType.JAVASCRIPT);

        }



        function read_all_value(request){

        var ss =SpreadsheetApp.openById(SCRIPT_PROP.getProperty("key"));

        var output  = ContentService.createTextOutput(),
        data    = {};
        //Note : here sheet is sheet name , don't get confuse with other operation
        var sheet="sheet1";

        data.records = readData_(ss, sheet);


        var callback = request.parameters.callback;

        if (callback === undefined) {
        output.setContent(JSON.stringify(data));
        } else {
        output.setContent(callback + "(" + JSON.stringify(data) + ")");
        }
        output.setMimeType(ContentService.MimeType.JAVASCRIPT);

        return output;
        }


        function readData_(ss, sheetname, properties) {

        if (typeof properties == "undefined") {
        properties = getHeaderRow_(ss, sheetname);
        properties = properties.map(function(p) { return p.replace(/\s+/g, '_'); });
        }

        var rows = getDataRows_(ss, sheetname),
        data = [];

        for (var r = 0, l = rows.length; r < l; r++) {
        var row     = rows[r],
        record  = {};

        for (var p in properties) {
        record[properties[p]] = row[p];
        }

        data.push(record);

        }
        return data;
        }



        function getDataRows_(ss, sheetname) {
        var sh = ss.getSheetByName(sheetname);

        return sh.getRange(2, 1, sh.getLastRow() - 1, sh.getLastColumn()).getValues();
        }


        function getHeaderRow_(ss, sheetname) {
        var sh = ss.getSheetByName(sheetname);

        return sh.getRange(1, 1, 1, sh.getLastColumn()).getValues()[0];

        }

*/

//



public class SyncData extends AppCompatActivity {

    Button btnSyncData, btnListData ;
    String userImage ;
    Bitmap rbitmap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sync_data);

        btnSyncData= (Button)findViewById(R.id.btnSyncData);

        btnListData =(Button)findViewById(R.id.btnListData);



        btnListData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),DataList.class);
                startActivity(intent);

            }
        });


        btnSyncData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertToSheet();
            }
        });

    }


    private void insertToSheet() {
        // some variable
        // for location image
        ArrayList<Integer> mIds = new ArrayList<Integer>();
        ArrayList<String> mLocationImage = new ArrayList<String>();
        ArrayList<String> mTitle = new ArrayList<String>();
        ArrayList<String> mDescription = new ArrayList<String>();
        ArrayList<String> images = new ArrayList<String>();

        // get data from database
        //get data from sqlite
        Cursor cursor = MainActivity.sqLiteHelper.getData("SELECT * FROM MISSION");

        //cursor.moveToFirst() ;
        Log.d("mylog", "before while : ");

        while (cursor.moveToNext())
        //for(int i =0 ;i< 5;i++)
        {
            Log.d("mylog", "inside while : ");
            //
            int id = cursor.getInt(0);
            Log.d("mylog", "inside while id : " + id);
            String localisation = cursor.getString(1);
            String name = cursor.getString(2);
            String mission = cursor.getString(3);
            String path = cursor.getString(4);
            //mTitle[i] = name ;


            mIds.add(id);
            mLocationImage.add(localisation);
            mTitle.add(name); //this adds an element to the list.
            mDescription.add(mission); //this adds an element to the list.
            images.add(path); //this adds an element to the list.

            // call add to google

            addUser(Integer.toString(id), name, path,mission,localisation);

        }
    }

    private void addUser(String id, String name, String pathFile,String mission, String location){



        final ProgressDialog loading = ProgressDialog.show(this,"Uploading...","Please wait...",false,false);
        final String userId ;
        final String userName ;
        final String usermission ;
        final String userlocation ;


// todo
            userId = id ;
            userName =name;
            userlocation = location;
            usermission = mission ;


// get the image

            // test path image
            Bitmap bitmap = BitmapFactory.decodeFile(pathFile);
            rbitmap = getResizedBitmap(bitmap,800);//Setting the Bitmap to ImageView
            userImage = getStringImage(rbitmap);

            Log.d("mylog","out while : ");
// *****************




            //Bitmap  rbitmap = getResizedBitmap(bitmap,500);

            //Log.e("null","values"+userImage);


            StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.POST,ADD_USER_URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            loading.dismiss();
                            Toast.makeText(SyncData.this,response,Toast.LENGTH_LONG).show();
                            Log.d("test_delete","response from the web : "+ response);
                            if(response.equals("oky")){
                                // delete element when we saved to the sheets
                                Log.d("test_delete","delete the ids : "+ userId);
                                MainActivity.sqLiteHelper.delete(Integer.parseInt(userId));

                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(SyncData.this,error.toString(),Toast.LENGTH_LONG).show();
                        }
                    }){
                @Override
                protected Map<String,String> getParams(){
                    Map<String,String> params = new HashMap<String, String>();
                    params.put(KEY_ACTION,"insert");
                    params.put(KEY_ID,userId);
                    params.put(KEY_NAME,userName);
                    params.put(KEY_MISSION,usermission);
                    params.put(KEY_LOCATION,userlocation);
                    params.put(KEY_IMAGE,userImage);

                    return params;
                }

            };

            int socketTimeout = 30000; // 30 seconds. You can change it
            RetryPolicy policy = new DefaultRetryPolicy(socketTimeout,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);

            stringRequest.setRetryPolicy(policy);


            RequestQueue requestQueue = Volley.newRequestQueue(this);

            requestQueue.add(stringRequest);


        }




    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);

        return encodedImage;
    }



    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float)width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);

    }
}
