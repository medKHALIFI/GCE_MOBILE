package com.example.gce_mobile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
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


import static android.os.Environment.getExternalStoragePublicDirectory;

public class MainActivity extends AppCompatActivity {

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
    Button btnSave, btnList ;

    String path = " ";

    final int REQUEST_CODE_GALLERY = 999;

    public static SQLiteHelper sqLiteHelper ;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        /// 2
        init();
        sqLiteHelper =new SQLiteHelper(this,"MissionDB.sqlite",null,1);
        sqLiteHelper.queryData("CREATE TABLE IF NOT EXISTS MISSION (Id INTEGER PRIMARY KEY AUTOINCREMENT , name VARCHAR, mission VARCHAR, image BLOG)");

        btnList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityCompat.requestPermissions(
                        MainActivity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_CODE_GALLERY
                ) ;
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"save button",Toast.LENGTH_SHORT).show();
                try{
                    sqLiteHelper.insertData(
                            editName.getText().toString().trim(),
                            editMission.getText().toString().trim(),
                            imageViewToByte(imageView)
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
                Intent intent = new Intent(MainActivity.this ,MissionList.class);
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
                Intent myIntent = new Intent(MainActivity.this, Get_location.class);
                startActivity(myIntent);

            }
        });


        btn_image = (Button) findViewById(R.id.btn_image);
        btn_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                path="storage/emulated/0/Pictures/201950186609127110602278187   .jpg";
                // test path image
                Bitmap bitmap = BitmapFactory.decodeFile(path);
                // ici on va savoire le chemain vers notre image
                Toast toast = Toast.makeText(getApplicationContext(), path, Toast.LENGTH_SHORT);

                toast.show();
                imageView.setImageBitmap(bitmap);
            }
        });


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
    }



    //

    private void init(){
        editName = (EditText)findViewById(R.id.editName);
        editMission = (EditText)findViewById(R.id.editMission);
        btnSave = (Button) findViewById(R.id.btnSave);
        btnList = (Button) findViewById(R.id.btnList);
        imageView = (ImageView) findViewById(R.id.image);
        btnTakePic =findViewById(R.id.btnTakePic);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data){

        super.onActivityResult(requestCode, resultCode, data);


        if(resultCode == RESULT_OK){
            if(requestCode == 1){
                Bitmap bitmap = BitmapFactory.decodeFile(pathToFile);
                // ici on va savoire le chemain vers notre image
                Toast toast = Toast.makeText(getApplicationContext(), pathToFile, Toast.LENGTH_SHORT);
                path = pathToFile ;
                toast.show();
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
            Toast toast = Toast.makeText(getApplicationContext(), "test ", Toast.LENGTH_SHORT);
            toast.show();
        } catch (IOException e) {
            Log.d("mylog","Error : "+ e.toString());

        }
        return  image ;

    }

}
