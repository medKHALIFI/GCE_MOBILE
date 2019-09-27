package com.example.gce_mobile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;


// chanel to  drive codes
// https://www.crazycodersclub.com/category/android/

import static android.os.Environment.getExternalStoragePublicDirectory;

public class MainActivity extends AppCompatActivity implements LocationListener {

    // get picture
    // https://www.youtube.com/watch?v=32RG4lR0PMQ
    // get location
    //https://www.youtube.com/watch?v=QNb_3QKSmMk
    // save image to db
    //https://www.youtube.com/watch?v=C21N5mLvQiU

    Button btnTakePic, btn_location , btn_image;
    ImageView imageView ;
    String pathToFile ;

    // info mission
    EditText editName, editMission ;
    Button btnSave, btnList, listMap , SyncData;

    String path = " ";

    final int REQUEST_CODE_GALLERY = 999;

    public static SQLiteHelper sqLiteHelper ;

    public  String locationImage = " ";
    LocationManager locationManager;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /// to get location
        getSupportActionBar().hide();

        // get permission to location
        if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION}, 101);

        }

        // call function to get current location
        getLocation();







        /// 2
        init();
        sqLiteHelper =new SQLiteHelper(this,"MissionDB.sqlite",null,1);
        sqLiteHelper.queryData("CREATE TABLE IF NOT EXISTS MISSION (Id INTEGER PRIMARY KEY AUTOINCREMENT , localisation VARCHAR, name VARCHAR, mission VARCHAR, path VARCHAR)");

    /*    btnList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityCompat.requestPermissions(
                        MainActivity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_CODE_GALLERY
                ) ;
            }
        });*/


    ////// ************* list all saved items in the map
        listMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(MainActivity.this ,MapsListAllPhoto.class);
                startActivity(intent);
            }
        });


        // **************  sync data to google sheets

        SyncData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(MainActivity.this ,SyncData.class);
                startActivity(intent);
            }
        });



        /// ********* save item in DB
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {

                Toast.makeText(getApplicationContext(),"here is your location : "+locationImage,Toast.LENGTH_SHORT).show();
                Toast.makeText(getApplicationContext(),"save button",Toast.LENGTH_SHORT).show();
                try{
                    sqLiteHelper.insertData(
                            locationImage.trim(),
                            editName.getText().toString().trim(),
                            editMission.getText().toString().trim(),
                            //imageViewToByte(imageView)
                            path.toString().trim()
                    );
                    Toast.makeText(getApplicationContext(),"Added successfuly",Toast.LENGTH_SHORT).show();

                    editName.setText("");
                    editMission.setText("");
                    imageView.setImageResource(R.mipmap.ic_launcher);
                    // todo change icon to image
                }
                catch (Exception e){
                    e.printStackTrace();
                }


            }
        });


        btnList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ActivityCompat.requestPermissions(
                        MainActivity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_CODE_GALLERY
                ) ;

                Intent intent = new Intent(MainActivity.this ,ViewData.class);
                startActivity(intent);
            }
        });



        if (Build.VERSION.SDK_INT >= 23){
            requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
        }

        // le traitement quand on clique sur le button
        btnTakePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dispatchPictureTakeAction();

            }
        });



        btn_location = (Button) findViewById(R.id.btn_location);

        btn_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ActivityCompat.requestPermissions(
                        MainActivity.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        REQUEST_CODE_GALLERY
                ) ;

                Intent myIntent = new Intent(MainActivity.this, Get_location.class);
                startActivity(myIntent);

            }
        });


        btn_image = (Button) findViewById(R.id.btn_image);
        btn_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                path="storage/emulated/0/Pictures/201947183795183217611228022.jpg";
                // test path image
                Bitmap bitmap = BitmapFactory.decodeFile(path);
                // ici on va savoire le chemain vers notre image
                //Toast toast = Toast.makeText(getApplicationContext(), path, Toast.LENGTH_SHORT);

                //toast.show();
                imageView.setImageBitmap(bitmap);
            }
        });


    }


    // to get current location function getLocation()
    void getLocation() {
        try {
            //Toast.makeText(this, "get location", Toast.LENGTH_SHORT).show();

            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5, this);
        }
        catch(SecurityException e) {
            e.printStackTrace();
        }
    }

    private byte[] imageViewToByte(ImageView image) {
        Bitmap bitmap = ((BitmapDrawable) image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream =new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray =stream.toByteArray();
        return  byteArray ;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        /*
        if(requestCode == REQUEST_CODE_GALLERY){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_CODE_GALLERY);
            }
            else {
                Toast.makeText(getApplicationContext(),"you don't have permission to access file location", Toast.LENGTH_SHORT).show();
            }
            return;
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    */
    }



    //

    private void init(){
        editName = (EditText)findViewById(R.id.editName);
        editMission = (EditText)findViewById(R.id.editMission);
        btnSave = (Button) findViewById(R.id.btnSave);
        btnList = (Button) findViewById(R.id.btnList);
        imageView = (ImageView) findViewById(R.id.image);
        btnTakePic =findViewById(R.id.btnTakePic);

        // list all in the map
        listMap = (Button) findViewById(R.id.btnListMap);

        // SyncData
        SyncData = (Button) findViewById(R.id.btnSyncData);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data){

        super.onActivityResult(requestCode, resultCode, data);


        if(resultCode == RESULT_OK){
            if(requestCode == 1){
                Bitmap bitmap = BitmapFactory.decodeFile(pathToFile);
                // ici on va savoire le chemain vers notre image
                //Toast toast = Toast.makeText(getApplicationContext(), pathToFile, Toast.LENGTH_SHORT);
                path = pathToFile ;
                //toast.show();
                imageView.setImageBitmap(bitmap);
            }
        }
    }




    private void dispatchPictureTakeAction(){
        Intent takePic = new Intent(MediaStore.ACTION_IMAGE_CAPTURE) ;
        if (takePic.resolveActivity(getPackageManager()) != null) {
            File photoFile = null ;

            photoFile = createPhotoFile();
            if (photoFile != null){
                pathToFile = photoFile.getAbsolutePath();
                Uri photoURI = FileProvider.getUriForFile(MainActivity.this, "com.thecodecity.cameraandroid.fileprovider",photoFile);
                takePic.putExtra(MediaStore.EXTRA_OUTPUT,photoURI);
                startActivityForResult(takePic, 1);



                }

        }
    }

    private File createPhotoFile(){
        String name = new SimpleDateFormat("yyyymmdd").format(new Date());
        File storageDir = getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File image = null ;
        try {
            image = File.createTempFile(name,".jpg",storageDir);
            //Toast toast = Toast.makeText(getApplicationContext(), "test ", Toast.LENGTH_SHORT);
            //toast.show();
        } catch (IOException e) {
            Log.d("mylog","Error : "+ e.toString());

        }
        return  image ;

    }

    @Override
    public void onLocationChanged(Location location) {

        //Toast.makeText(this, "location changed", Toast.LENGTH_SHORT).show();

        String temp = location.getLatitude() +","+ location.getLongitude();
        locationImage = temp ;
        //Toast.makeText(this, "Current location : "+locationImage, Toast.LENGTH_SHORT).show();

        try {
            //Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            //List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            //locationText.setText(locationText.getText() + "\n"+addresses.get(0).getAddressLine(0)+", "+addresses.get(0).getAddressLine(1)+", "+addresses.get(0).getAddressLine(2));
        }catch(Exception e)
        {

        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {
        //Toast.makeText(this, "Please Enable GPS and Internet", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText(this, "Please Enable GPS and Internet", Toast.LENGTH_SHORT).show();
    }
}
